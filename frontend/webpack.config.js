const webpack = require('webpack');
const path = require('path');

module.exports = {
    entry: './src/index.js',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './build/resources/static/js/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
        ]
    }
}
