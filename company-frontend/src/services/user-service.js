import { create } from '@beyonk/sapper-httpclient';
import { derived, writable } from 'svelte/store';
import { Data } from './data';
import sleep from '../util/sleep';

class UserService {
    users = writable(new Data());
    selectedUser = writable(new Data());

    u = [
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

    async fetch(companyUrl) {
        const api = create();
        // await sleep();
        this.users.update(users => users.setData(this.u));
    }

    async fetchByUsername(companyUrl, username) {
        // await sleep();
        const user = this.u.find(u => u.username === username);
        this.selectedUser.update(selectedUser => selectedUser.setData(user));
    }
}

export default new UserService();
