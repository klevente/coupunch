import sirv from 'sirv';
import polka from 'polka';
import compression from 'compression';
import * as sapper from '@sapper/server';
import { createProxyMiddleware } from 'http-proxy-middleware';
import cookieParser from 'cookie-parser';
import { guard } from '@beyonk/sapper-rbac';
import { initApi } from './services/api';
import routes from './routes';

const { PORT, NODE_ENV } = process.env;
const dev = NODE_ENV === 'development';
const backend = 'http://localhost:8000/';

initApi();

polka() // You can also use Express
	.use('/api', createProxyMiddleware({ target: backend }))
	.use(
		compression({ threshold: 0 }),
		sirv('static', { dev }),
		cookieParser(),
		(req, res, next) => {
			const sessionCookie = req.cookies['SESSION'];
			const authenticated = !!sessionCookie;
			const user = {
				authenticated,
				scope: authenticated ? ['COMPANY_USER'] : [],
				id: null,
				username: null,
				email: null,
			};

			const options = {
				routes,
				deny: () => res.writeHead(302, { Location: 'login' }).end(),
				grant: () => sapper.middleware({
					session: () => ({ user })
				})(req, res, next),
			}
			return guard(req.path, user, options);
		},
		sapper.middleware()
	)
	.listen(PORT, err => {
		if (err) console.log('error', err);
	});
