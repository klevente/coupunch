import CompanyUrlService from './companyurl-service';
import sleep from '../util/sleep';

let dummyUsers = [
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

export default class UserService extends CompanyUrlService {
    static async get() {
        await sleep();
        return [...dummyUsers];
    }
}
