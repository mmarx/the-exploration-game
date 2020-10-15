const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: '/',
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
      }
    }
  },
}
