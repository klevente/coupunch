const path = require('path');
const sveltePreprocess =  require('svelte-preprocess');
const makeAttractionsImporter = require('attractions/importer.js');

module.exports = {
    preprocess: [
        sveltePreprocess({
            scss: {
                importer: makeAttractionsImporter({
                    // specify the path to your theme file, relative to this file
                    themeFile: path.join(__dirname, 'static/css/theme.scss'),
                }),
                // not mandatory but nice to have for concise imports
                includePaths: [path.join(__dirname, './static/css')]
            }
        })
    ],
};
