import CompanyUrlService from './companyurl-service';
import sleep from '../util/sleep';

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

export default class UserService extends CompanyUrlService {
    static async get() {
        await sleep();
        return [...dummyCustomers];
    }

    static async searchCustomers(keyword) {
        await sleep();
        console.log(keyword);
        if (!keyword) {
            return [];
        }
        return dummyExtraCustomers
            .filter(({ username }) => username.toLowerCase().includes(keyword.toLowerCase()))
            .filter(foundCustomer => !dummyCustomers.includes(foundCustomer));
    }

    static async addToCompany(customer) {
        await sleep();
        dummyCustomers.push(customer);
    }
}
