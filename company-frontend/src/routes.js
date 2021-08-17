import { Router } from '@beyonk/sapper-rbac';

export default new Router()
    .restrict('/home', ['COMPANY_USER'])
    .restrict('/products', ['COMPANY_USER'])
    .restrict('/checkout', ['COMPANY_USER'])
    .unrestrict('/.*')
    .build();
