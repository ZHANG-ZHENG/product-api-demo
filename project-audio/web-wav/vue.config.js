module.exports = {
  //publicPath:'./',//根路径
  //outputDir:'web-manage',//打包的时候生成的一个文件名
  //publicPath: process.env.NODE_ENV === 'production'?'./':'/web-manage', 


  devServer: {
    port: 8081,
    proxy: { 
      // 设置代理
      '/service-wav': {
        //target: 'http://db.zhost.top/',
        target: 'http://localhost:8888/',
        changeOrigin: true,
        pathRewrite: {
          '^/service-wav': '/service-wav'
        }
      },                 
    }
  },
}