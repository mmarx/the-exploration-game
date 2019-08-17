const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: (process.env.NODE_ENV === 'production'
               ? '/teg/'
               : '/'),
  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: false
    }
  },
  chainWebpack: (config) => {
    config.resolve.alias.set('~', resolve('src'))
    config.resolve.alias.set('@', resolve('node_modules/sqid/src'))
  },
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:4223',
        pathRewrite: {'^/teg/api' : ''}
      }
    }
  },
}
