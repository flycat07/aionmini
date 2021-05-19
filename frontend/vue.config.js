module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8081'
            }
        }
    },
    publicPath: process.env.NODE_ENV === 'production' ? '/aionmini/' : '/',
    filenameHashing:false,
    transpileDependencies: [
      'vuetify', 'copy-text-to-clipboard'
    ]

};
