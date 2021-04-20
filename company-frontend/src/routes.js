import { Router } from '@beyonk/sapper-rbac';

export default new Router()
    .restrict('/home', ['COMPANY_USER'])
    .unrestrict('/.*')
    .build();
