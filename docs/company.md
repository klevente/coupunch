# Company Plans

## Security

* Separate frontend application, for company management and redeeming
* After login, the backend sends back the following:
  * userId
  * username
  * scopes: `employee, companyName` - this ensures proper authorization
* A master account is needed for user management, whose scope is `admin`
  * For now, this will be: `admin, admin` and is created upon app startup
  * Later, this can be an inactive admin account, for which a password needs to be set using an emailed link
  * This account can create new users, and edit them - a similar password creation process can be implemented

## Routing

* Upon instance startup, the backend notifies the gateway that a new routing entry is required - via message queue
* The gateway will contact Consul and update its KV-store, adding the new instance
* Then the gateway will refresh its configuration to load the new routing table
* This way, the routing table is accessible to the admins and can be modified during runtime easily
* Some kind of health checking could be implemented, where an inactive service could be removed from the routing table
* For starters, this can also work manually

## Frontend Routes

Base URL: `company.coupunch.xyz`

* `/`: Home landing page, with basic info about the service for companies
* `/login`: Login page
* `/company1`: Home page for *company1* with links to use cases
* `/company1/usersearch`: User selector screen for redemption
* `/company1/redeem/username`: Redeem screen for user with `username`
* `/company1/coupons`: Coupon editor page
* `/company1/products`: Product editor page
* `/company1/reports`: Reports page
* `/company1/settings`: Company-specific settings page

## Product Management

Employees can manage products using the provided interface.

### Product Groups

On the left, there is an editable list of product groups. Each item can be edited or removed. New items can be added using the button at the top. A group row displays its name, how many products are in it, and how many coupons use it as a requirement and how many use it as a reward.

The first item is a hardwired "All products" group, which lists every product without filter. Upon clicking on a group, only the relevant products show up in the middle.

Removing a product group is only enabled when it contains no items.

On the backend, a "default" group is readily available to place any items which fit nowhere else.

#### Product Group Edit Form

This modal exposes the following data to edit a group: name.

### Products

In the middle, a searchable table shows products based on the current search term and selected group. Each row displays the product name, price and icon. It also displays how many coupons use it as a requirement and how many use it as a reward. Each item can be edited or removed. Removal is only enabled when there are no coupons referring this product.

#### Product Edit Form

This modal exposes the following data to edit a group: name, price, icon, product group. If no product group choice is made, the "default" one will be used.

## User Selection

Employees have 2 ways of selecting a user upon checkout:

### Manual Selection

A list of already known customers is shown, with the names of customers. Upon clicking on one of them, the app navigates to the redemption page with the user's ID forwarded as a query parameter.

For a new user, the employee must use add them manually using the button. This opens a dialog where they can search for the user by their username/email, which pops up potential results, where the employee can select one. Upon a successful transaction, the customer will show up in the list during the next purchase.

### QR-based Selection

After pressing the *Scan QR Code* button, the device's camera pops up, which will scan the customer's barcode. The data it extracts is then sent to the server, where it is identified and verified that it is valid. If everything checks out, the server sends back the ID of the user, which can be used to navigate to the redemption page.

The QR-code contains a randomly generated UUID that identifies the user. If the user wants to generate a new QR-code, a new UUID will be generated.

Verification is achieved by comparing the request with the stored QR data in the database, and in case of a match, the username will be returned. For this, the server checks if there is a user that posesses the supplied UUID. If anything goes wrong, the server sends back an error.

If the user was not added to the company's user list, the backend will do that automatically before sending back the response, which indicates if this was the case.

## Checkout

After selecting a user for checkout, the employee must select the purchased items and any eligible coupons which the customer wants to redeem. This interface is structured as follows:

### Basket

The basket stores the items which are being purchased by the customer. It holds both normal purchases and items which are acquired using coupons.

#### Normal Purchase

This row displays the product name, quantity and unit price. It also displays a plus and minus icon for altering quantity. Additionally, the whole row can be discarded by clicking the cross icon

#### Coupon row

This row displays the product name, quantity, unit price and discounted price, with a different background color. It also displays a cross icon for discarding the redeemed coupon.

The basket is always visible on the right side of the screen.

### Selections

Selecting products and coupons requires more space, so their respective elements live on different tabs. The default tab is products, as it is reasonable to assume that employees want to select those first before coupons.

#### Products

A searchable table displaying every product with its name and price. Clicking one will add it to the basket; repeatedly clicking on it will increase the amount. 

This view is only visible when its tab is selected.

#### Coupons

A searchable table displaying every *redeemable* coupon the user has at the moment of purchase. Clicking one will redeem it, adding the respective reward to the basket as a new row with a different style. If a coupon offers a reward for a product group, a selector for the individual product from that group will open so the employee can select the appropriate product.

This view is only visible when its tab is selected.

#### Product Selector

For easy reward selection for coupons with product groups or multiple products as rewards, this modal will offer a searchable table with the products in it, from which the employee can choose the required product. Upon choosing, the modal will close, and the respective product will be added to the basket as a new row.

### Redeeming

After everything has been selected and verified, the employee clicks the *Finish* button, which will send the request to the server. The response will contain the coupons which have just been redeemed with the exact reward information: what product, with what discount. For extra ease, a modal with this information will be displayed to the employee, so they can review it once more while they add the info to the cash register.

Clicking the *Finish* button on the modal will close it and end the checkout process, redirecting the interface to the main page.