# Coupons

For a given transaction, coupons can be awarded paralelly, meaning that a single item can increase progress in multiple coupons.

## Buy X Get One Free

Buying `x` amount of a product/product group will yield one free product/discount.

Examples:
* Buy 10 coffees, the 11th one is on us! (any type of coffee)
* Buy 5 croissants, at the 5th, a free coffee is awarded
* Buy 10 coffees, get a bagel for 50% off

This coupon can have multiple levels as well - they can be accumulated until an end level is reached:
* For 5 coffees, get an espresso, for 10, get a croissant

## Buy for X Price and Get Something For Free

After reaching a defined cumulative total sum for multiple transactions, a free product/discount can be redeemed.

Examples:
* After each $20, you get a free drink
* After each $50, your final total is reduced by 10%
* After each $10, you get a 5% discount on a sandwich.

This coupon can have multiple levels: they can be accumulated until an end price is reached:
* For $20, get a 5% discount for a coffee pack, collect until $50 for a 50% discount for a french press

## Buy Items For Points, Get Something For Free

Each item is worth a specific amount of points, which can be redeemed for a free product/discount after a threshold.

Examples:
* Espresso is 2 points, Bagel is 4 points, when reaching 20 points, you get a free cake
* Bread is 3 points, Whole-grain bread is 5 points, get 20% off your total when reaching 30 points

This coupon can have multiple levels as well - they can be accumulated until an end level is reached:
* For 10 points, get a free bread, for 20, get 20% off for a ground coffee pack

## Getting Points

1. At the counter, the customer informs the cashier that they want to get points for this transaction.
2. The cashier asks the customer to authenticate either by their name or by their personal QR-code, scanned with the device running the software
3. The cashier then can:
   * See the current coupons of the customer, and a product list
   * Input the purchased items to the product list - these get listed in the "bought items" section
   * Redeem coupons which are already completed - the rewards get added to the "bought items" section
4. The app displays info which items need to be handed over freely/at a discount
5. The cashier's app resets to the main screen, ready for another transaction
6. The user can check their updated points on their own device

This could be automated in the future by connecting the system to the cash register, which could send the required data to the system, which could populate the "bought items" list automatically. Though how couponed items and discounts could be wired up is questionable.

## More Coupon Types - Future

* First `x` customers get a reward, after that, it vanishes
* Coupons that have no prerequisites - to boost purchasing
* Timed coupons: available for a week, `x` days, etc.

## Coupon Structure

Coupons can be of 2 types: point based/price based and can have multiple levels. For ease of access, a product group consisting of all products is automatically created and maintained.

### Point-based

Specific products/product groups are assigned `x` number of points. Reward levels consist of:
* Point threshold: how many points are needed for this reward
* Reward type: discount/free product
  * In case of discount: percentage/fix reduction and its quantity
* Reward: a product/product group eligible for redemption

### Price-based

Specific products/product groups are counted in an internal counter. Levels consist of:
* Price threshold: how much currency needs to be accumulated for this reward
* Reward type: discount/free product
  * In case of discount: percentage/fix reduction and its quantity
* Reward: a product/product group eligible for redemption

# Redeeming Rewards

All types of coupons can be redeemed at the subsequent transaction after getting the required points. This is explained above, but basically:
1. The cashier selects which coupons the customer wants to redeem
2. These get added to the "bought items" section - for free or at a discount rate
3. These are displayed at large after clicking OK, to instruct the cashier what items are need to be handled differently