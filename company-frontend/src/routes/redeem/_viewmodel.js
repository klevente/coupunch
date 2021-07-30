import BaseViewmodel from '../../viewmodel/base-viewmodel';
import { action, dataStore, stateStore } from '../../viewmodel';
import { searchStore, sortByStore } from '../../viewmodel/transformations/stores';
import { filtered, sorted } from '../../viewmodel/transformations';
import UserService from '../../services/user-service';

export default class Viewmodel extends BaseViewmodel {
    #users = dataStore();
    state = stateStore();

    searchTerm = searchStore();
    sortBy = sortByStore();

    #filteredUsers = filtered({
        dataStore: this.#users,
        searchTerm: this.searchTerm,
        searchProperty: 'name'
    });
    displayedUsers = sorted({
        dataStore: this.#filteredUsers,
        sortBy: this.sortBy,
    });

    #actions = {
        get: action(UserService.get)
    }

    constructor() {
        super(UserService);
    }

    async get() {
        await this.load({
            action: this.#actions.get
        });
    }

    get _resource() {
        return this.#users;
    }

    get _state() {
        return this.state;
    }
}
