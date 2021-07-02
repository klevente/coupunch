# Frontend State Management

As there is no default way of handling application state in a large application, a custom solution has to be defined in order to properly scale the project.

## Data Update Methodologies

There are multiple ways of handling data coming from a server, depending on the project's needs and its specification.

### Partial Updates

When a modification operation takes place, the frontend fires the appropriate request to the backend, waits for a response, then updates its local store accordingly, without re-querying the data from the server. This results in lower network usage, but the resulting state might not be the most recent, as others might have updated the same resource in that timeframe.

### Full Updates

When a modification operation takes place, the frontend fires the appropriate request to the backend, waits for a response, then re-queries the whole, now updated resource from the server. While this results in higher network bandwidth, the client can be sure that the resulting state matches perfectly with the server one, as it received every other change that might have occured during the timeframe of the modification.

## Data Storage Methodologies

With larger applications, it is also worth considering the different scopes for data storage.

### Global State

Storing data globally means that every component in the application can access it freely. Usually, this means that data gets loaded on application initialization or when the first component needing it triggers a lazy load. While this approach saves bandwidth, the data can also get out of sync if a substantial amount of time passes between two explicit reloads, which can lead to confusion or bugs.

### Local State

As opposed to a global, persistend state, storing locally means that the required data is only available to a subset of components, while those components are active. This usually means that the component which requires the data creates a storage component and fills it up with information from the backend. When the component is detached, the storage component is also destroyed, releasing the data in it. This way, when the component is reinitialized, the data gets queried again implicitly.

## Architecture

After listing the different approaches available for data storage and management, the paragraphs below detail how I chose to implement state management in the application.

### Services

A service implements data access operations for a given entity in the application. It exposes CRUD-like operations which are needed by one or more components in the application. They are structured as singleton classes, with methods that implement the required operations:

```javascript
// entity-service.js
class EntityService {
   async fetch() {
       // GET /api/entities
       return await Promise.resolve([{ id: 1, name: 'John'}]);
   }
   // add, update, delete, ...
}

export default new EntityService();
```

### Viewmodels

A viewmodel abstracts data access for a given component by publishing only related information and operations which that component needs. A viewmodel is only valid when its component is mounted, which makes it a local state management solution. This way, the required data is always up-to-date upon component initialization.

#### Data State

For keeping track of data state changes, a viewmodel always stores its data in a helper class aptly named Data, that holds state information next to the possible available value in it:

```javascript
export const Status = {
    Loading: 'LOADING',
    HasData: 'HASDATA',
    Error: 'ERROR'
}

export class Data {
    status = Status.Loading;
    data = null;
    error = null;

    // setters, accessors ...
}
```

In the future, this Data class could be altered to work like a Svelte store, which would improve usage and reduce code by combining status management with auto-subscriptions.

#### Full Update Implementation

 A viewmodel looks like this:

```javascript
// entity-list-viewmodel.js
export default class EntityListViewModel {
    entities = writable(new Data());

    async constructor() {
        await fetch();
    }

    async fetch() {
        this.entities.update(entities => entities.setData(await entityService.fetch()));
    }

    async add(entity) {
        try {
            await entityService.add(entity);
            await fetch();
        } catch(e) {
            this.entities.update(entities => entities.setError(e));
        }
    }
    // other required operations ...
}
```

As seen above, viewmodels implement the *full update* strategy detailed above, by fetching the data source again after a successful operation on the backend. As this can get quite out of hand when multiple viewmodels work with the same data set, this functionality might get moved to the service layer in the future - this would also mean that the strategy is strictly enforced throughout the application, as there is no way to modify data without utilizing the service layer.

#### Mixed Strategy Implementation

As an optimization strategy, a mix of full and partial updating can also be utilized in the following way:

1. Component calls viewmodel modification method
2. Viewmodel calls the service method
3. If there is no error, the viewmodel updates its local store with the data supplied from the component
4. A full data refresh is launched, which updates the local store upon completion

```javascript
class EntityListViewModel {
    async add(entity) {
        try {
            const newEntity = await entityService.add(entity);
            this.entities.update(entities => entities.setData([...entities.data, newEntity]));
            fetch();
        } catch(e) {
            ...
        }
    }
}
```

### Components

The final pieces of the puzzle are the Svelte components that render content to the screen, with which the user can interact to use the other two elements. Using a viewmodel in a component is straightforward:

```html
<script>
    // entity-list.svelte
    const viewmodel = new EntityListViewModel();
    const { entities } = viewmodel;
</script>

{#if $entities.isLoading()}
    <Loading />
{/if}
{#if $entities.hasData()}
    {#each $entities.data as entity}
        <p>{entity.name}</p>
    {/each}
{/if}
{#if $entities.hasError()}
    <p>$entities.error</p>
{/if}

<button on:click={() => viewmodel.add({ name: 'Test' })}>Add</button>
```

### Possible Service Implementations for Different Strategies

This version of a service aims to provide a solution for handling data updating mechanics inside of it, making the strategy mandatory inside the project.

#### Full Strategy

This approach returns the updated data fetched from the server after completing the initial operation. Its only drawback that the viewmodel might need another view of the data, which is hardcoded in the service.

```javascript
// entity-service.js
class EntityService {
   async fetch() {
       // GET /api/entities
       return await Promise.resolve([{ id: 1, name: 'John'}]);
   }
   
   async add(entity) {
       // POST /api/entities
       const entity = await Promise.resolve({id: 2, name: 'Jack'});
       return await fetch();
   }
}
```

#### Mixed Strategy

```javascript
// entity-service.js
class EntityService {
   async fetch() {
       // GET /api/entities
       return await Promise.resolve([{ id: 1, name: 'John'}]);
   }
   
   async add(entity, cb) {
       // POST /api/entities
       const entity = await Promise.resolve({id: 2, name: 'Jack'});
       fetch().then(cb);
       return entity;
   }
}

// entity-list-viewmodel.js
class EntityListViewModel {
    async add(entity) {
        const newEntity = await entityService.add(entity, serverEntities => {
            this.entities.update(entities => entities.setData(serverEntities));
        });
        // TODO: only update locally if server has not responded yet
        this.entities.update(entities => entities.setData([...entities, newEntity]));
    }
}
```

This approach yields increased amount of complexity, but still achieves a rather clean solution, where the service is mostly in charge of data updating. Before returning from the service method, it starts a full data fetch, which is handled by the provided callback, where the viewmodel updates its local store fully. The service method only returns the updated resource, which the viewmodel can insert into its local store to make in visible for the user instantly. After the network call has finished, the interface updates silently, bringing in up-to-date information.

The only caveat with this solution is that the local update can only be executed if the remote update has not succeded yet, as it would lead to data corruption. As of now, I do not have a solution for preventing that.

A solution might be for this is adding a boolean that checks whether the provided closure has been successfully executed, which can signal whether the local update should be run or not.

```javascript
class EntityListViewModel {
    async add(entity) {
        let shouldUpdateLocally = true;
        const newEntity = await entityService.add(entity, serverEntities => {
            this.entities.update(entities => entities.setData(serverEntities));
            shouldUpdateLocally = false;
        });
        if (shouldUpdateLocally) {
            this.entities.update(entities => entities.setData([...entities, newEntity]));
        }
    }
}
```

This can be generalized using the following function:

```javascript
function x({serviceCall, serviceParams, serviceCallback}) {
    let shouldUpdateLocally = true;
    const res = await serviceCall(...serviceParams, (...args) => {
        serviceCallback(args);
        shouldUpdateLocally = false;
    });
    if (shouldUpdateLocally) {
        localCallback(res);
    }
}

class EntityListViewModel {
    async add(entity) {
        x({
            serviceCall: entityService.add,
            serviceParams: [entity],
            serviceCallback: serverEntities => this.entities.update(entities => entities.setData(serverEntities)),
            localCallback: newEntity => this.entities.update(entities => entities.setData([...entities, newEntity]))
        })
    }
}
```