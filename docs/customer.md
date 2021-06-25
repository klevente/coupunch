# Customer Plans

## Security

* Separate frontend application, for customers, where they can track their coupon progress and various other things
* After login, the backend sends back the following:
  * userId
  * username
  * scopes: `user` - this ensures proper authorization
* Later, an `admin` scope can be created, who can manage the whole site

## Frontend Routing

Base URL: `coupunch.xyz`

* `/`: Home landing page, with basic info about the service and the QR code
* `/login`: Login page
* `/register`: Customer registration page
* `/home`: Logged in landing page, with news and other things
* `/companies`: List of companies the user has interacted with
* `/company1`: List of coupons for *company1* with progress for each of them
* `/company1/coupon1`: Page detailing coupon data
* `/profile`: User profile settings
* `/qr`: The personal QR code of the logged in user for redemption

## Company Retrieval

TODO how does the user service collects/stores the user's companies to display them in the list