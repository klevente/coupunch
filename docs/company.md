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

## User Selection

Employees have 2 ways of selecting a user upon checkout:

### Manual Selection

A list of already known customers is shown, with the names of customers. Upon clicking on one of them, the app navigates to the redemption page with the user's ID forwarded as a query parameter.

For a new user, the employee must use add them manually using the button. This opens a dialog where they can search for the user by their username/email, which pops up potential results, where the employee can select one. Upon a successful transaction, the customer will show up in the list during the next purchase.

### QR-based Selection

After pressing the *Scan QR Code* button, the device's camera pops up, which will scan the customer's barcode. The data it extracts is then sent to the server, where it is identified and verified that it is valid. If everything checks out, the server sends back the ID of the user, which can be used to navigate to the redemption page.

The QR-code contains the user's ID and a randomly generated string, encoded in base64 - this makes it impossible to try and guess another user's QR based on their ID alone.

Verification is achieved by comparing the request with the stored QR data in the database, and in case of a match, the ID will be returned. For this, the request is first parsed for extracting the user's ID. If anything goes wrong, the server sends back an error.