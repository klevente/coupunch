import * as sapper from '@sapper/app';
import * as yup from 'yup';
import 'tippy.js/dist/tippy.css';
import Api from "@beyonk/sapper-httpclient";

yup.setLocale({
	mixed: {
		default: "Not valid",
		required: "Must not be empty",
	},
	string: {
		default: "Must be a string",
		email: "Must be a valid email",
	}
});

Api.configure({ baseUrl: '/api', parseErrors: false });

sapper.start({
	target: document.querySelector('#sapper')
});
