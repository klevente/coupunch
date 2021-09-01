import { sleepRandom } from 'frontend-library/util/sleep';

let dummyCustomers = [
    {
        id: 1,
        name: 'John Doe',
        username: 'johndoe12'
    },
    {
        id: 2,
        name: 'Bill Smith',
        username: 'billysmith98'
    },
    {
        id: 3,
        name: 'Jane Hoynen',
        username: 'jane_h'
    }
];

let dummyExtraCustomers = [
    {
        id: 4,
        name: 'Robert Wright',
        username: 'robbw1'
    },
    {
        id: 5,
        name: 'Siobhan Hoover',
        username: 'shivvih'
    },
    {
        id: 6,
        name: 'Alex Hendricks',
        username: 'ahend__98'
    }
]

export default class CustomerService {
    static async get() {
        await sleepRandom();
        return [...dummyCustomers];
    }

    static async searchCustomers(keyword) {
        await sleepRandom();
        console.log(keyword);
        if (!keyword) {
            return [];
        }
        return dummyExtraCustomers
            .filter(({ username }) => username.toLowerCase().includes(keyword.toLowerCase()))
            .filter(foundCustomer => !dummyCustomers.includes(foundCustomer));
    }

    static async addToCompany({ id: userId, username }) {
        await sleepRandom();
        const newCustomer = dummyExtraCustomers.find(({ id }) => id === userId);
        if (!newCustomer) {
            throw new Error(`User not found: ${username}`);
        }
        dummyCustomers.push(newCustomer);
        console.log('added new customer: ', newCustomer);
    }

    static async validateQrCode(uuid) {
        await sleepRandom();
        const pattern = /userid=\d+$/;
        if (!uuid.match(pattern)) {
            throw new Error('Invalid QR code!');
        }
        const userId = parseInt(uuid.split('=')[1]);
        let user = dummyCustomers.find(({ id }) => id === userId);
        let newlyAdded = false;
        if (!user) {
            user = dummyExtraCustomers.find(({ id }) => id === userId);
            newlyAdded = true;
        }

        if (!user) {
            throw new Error('User does not exist!');
        }

        return { ...user, newlyAdded };
    }
}
