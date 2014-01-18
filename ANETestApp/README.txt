/*****************************************************************************
* 这个描述文件主要是关于ANE使用的项目，正式发布apk时需要的特殊处理操作*******
* 作者:wanglailai(a)gmail.com                                        ********
* 如果您在使用ANE或AIR发布apk存在疑惑时，请与我联系，大家可以一下研究一下****
*****************************************************************************/
因为FB打包不会将其他apk文件包含进去的，所以apk文件需要通过命令(可以写一个处理脚本来处理的)
1. 添加apk素材到导出的新包中（aapt命令在android sdk中可以找到）
   aapt add release/ANETestApp.apk assets/assets_wdj347.apk
2. 重新使用证书签名（jarsigner是JDK包含的命令）
   jarsigner -verbose -keystore ane4wdj.p12 -storetype pkcs12 -storepass 111111 -sigfile CERT release/ANETestApp.apk 1
3. 对齐apk文件，生成最终发布的apk（zipalign命令在android sdk中可以找到）
  zipalign -f -v 4 release/ANETestApp.apk release/ANETestApp-aligned.apk

注:我测试使用的ane4wdj.p12证书是临时生成测试的，证书密码：111111 证书别名：1
