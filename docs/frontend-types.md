# Frontend Types

Data representation on the frontend should focus on enabling ways for easy information display and aiding simple backend communication. While the project in itself is written in JavaScript, this document details the required types using TypeScript for easier understanding of the used type hierarchy.

## Coupons

### Company Coupon Management

Below are the types required to handle coupon management.

```typescript
    type CouponType = 'point' | 'price';

    interface Coupon {
        id: number;
        name: string;
        type: CouponType;
        eligibleItems: Items;
        rewards: Reward[]; // ordered list of reward levels: the thresholds must be increasing throughout
    }

    interface Items {
        products: EligibleProduct[];
        productGroups: EligibleProductGroup[];
    }

    interface EligibleProduct {
        id: number;
        name: string;
        price: number;
        points?: number; // only present in a point-based coupon
    }

    interface EligibleProductGroup {
        id: number;
        name: string;
        points?: number; // only present in a point-based coupon
    }

    type DiscountType = 'fixed' | 'percentage';

    interface Reward {
        threshold: number, // represents the point/price threshold required to redeem this reward
        products: ProductReward[];
        productGroups: ProductGroupReward[];
        discount: number; // contains a price reduction or percentage reduction depending on the reward type
        discountType: DiscountType;
    }

    interface ProductReward {
        id: number;
        name: string;
        amount: number;
        originalPrice: number;
        discountedPrice: number;
    }

    interface ProductGroupReward {
        id: number;
        name: string;
        amount: number;
        products: ProductGroupRewardProduct[];
    }

    interface ProductGroupRewardProduct {
        id: number;
        name: string;
        originalPrice: number;
        discountedPrice: number;
    }
```

### Company Coupon Redeeming

Below are the types required for handling products during redeeming.

```typescript
    interface Product {
        id: number;
        name: string;
        price: number;
        productGroup: ProductGroup;
    }

    interface ProductGroup {
        id: number;
        name: string;
    }
```

Below are the types required for handling coupons during redeeming.

```typescript
    type CouponType = 'point' | 'price';

    interface Coupon {
        id: number;
        name: string;
        type: CouponType;
        rewards: Reward[]; // ordered list of reward levels: the thresholds must be increasing throughout
        currentStanding: number; // stores the accumulated points or price the customer has acquired up to this point
    }

    type DiscountType = 'fixed' | 'percentage';

    interface Reward {
        threshold: number, // represents the point/price threshold required to redeem this reward
        products: ProductReward[];
        productGroups: ProductGroupReward[];
        discount: number; // contains a price reduction or percentage reduction depending on the reward type
        discountType: DiscountType;
    }

    interface ProductReward {
        id: number;
        name: string;
        amount: number;
        originalPrice: number;
        discountedPrice: number;
    }

    interface ProductGroupReward {
        id: number;
        name: string;
        amount: number;
        products: ProductGroupRewardProduct[];
    }

    interface ProductGroupRewardProduct {
        id: number;
        name: string;
        originalPrice: number;
        discountedPrice: number;
    }
```

### Company Product Management

Below are the types required to handle product management.

```typescript
    interface Product {
        id: number;
        name: string;
        price: number;
        group: ProductGroup; // a one-way association is sufficient, as the client fetches all products and filters them on the UI only
    }

    interface ProductGroup {
        id: number;
        name: string;
    }
```