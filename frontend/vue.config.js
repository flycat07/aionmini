module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8081'
            }
        }
    },
    filenameHashing:false,
    transpileDependencies: [
      'vuetify', 'copy-text-to-clipboard'
    ]

};
