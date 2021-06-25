# API

## User

* Register: email, username, password
* Login
* Update User: email, username, password

## Functions for Companies

### Companies

* Edit Company Data: name, theme, other config stuff

### User

* List Users the Company Has Interacted With
* Authenticate Based on Username: user (either from the list or by explicitly searching for the name if new)
* Authenticate Based on QR-Code: qr-code - returns information about user if valid

### Products

* Get Product List
* Get Product Group List
* Add/Edit New Product: name, price, icon, groups
* Add/Edit New Product Group: Name, Items
* Delete Product Group (does not delete products)
  * When a coupon has only this as a prerequisite, it deletes the coupon (or asks for a change)
  * When a coupon only has this as a reward, deletes the coupon (or asks for a change)
* Delete Products
  * When a coupon has only this as a prerequisite, it deletes the coupon
  * When a coupon only has this as a reward, deletes the coupon (or asks for a change)

### Coupons

* Get Coupons For Company
* Add/Edit Coupon: name, type, eligible products/groups (with points if applicable), reward milestone products/groups (with points/price)
* Delete Coupons
* Redeem Coupon/Rewards For User: user, purchased items, redeemed coupons - returns discounts and updates coupon status

### Reports (Best If 3rd Party Implementation)

* Get Reports
* Get Dashboard (this also executes the reports in the dashboard with specified params)
* Add/Update Report
* Delete Report
* Execute Report: params
* Export Report: params 

## Functions for Users

### Companies

* Get Companies Where User Has Coupons
* Remove Company From Account

### User

* Get Current QR-Code
* Update QR-Code
* Export QR-Code

### Coupons

* Get Coupons With Progress for User
