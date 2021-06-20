import { Router } from '@beyonk/sapper-rbac';

export default new Router()
    .restrict('/home', ['COMPANY_USER'])
    .restrict('/products', ['COMPANY_USER'])
    .restrict('/redeem', ['COMPANY_USER'])
    .unrestrict('/.*')
    .build();
