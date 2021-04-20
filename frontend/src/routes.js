import { Router } from '@beyonk/sapper-rbac';

export default new Router()
    .restrict('/home', ['USER'])
    .unrestrict('/.*')
    .build();
