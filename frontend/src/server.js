import sirv from 'sirv';
import polka from 'polka';
import compression from 'compression';
import * as sapper from '@sapper/server';
import { createProxyMiddleware, Filter, Options, RequestHandler } from 'http-proxy-middleware';
import cookieParser from 'cookie-parser';
import Api from '@beyonk/sapper-httpclient'
import routes from './routes';
import { guard } from "@beyonk/sapper-rbac";


const { PORT, NODE_ENV } = process.env;
const dev = NODE_ENV === 'development';

Api.configure({ baseUrl: '/api', parseErrors: false });

polka() // You can also use Express
    .use('/api', createProxyMiddleware({ target: 'http://localhost:8000/' }))
    .use(
        compression({ threshold: 0 }),
        sirv('static', { dev }),
        cookieParser(),
        (req, res, next) => {
            const sessionCookie = req.cookies['SESSION'];
            const authenticated = !!sessionCookie;
            const user = {
                authenticated,
                scope: authenticated ? ['USER'] : [],
                id: undefined,
                username: undefined,
                email: undefined,
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
