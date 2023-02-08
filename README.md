
# gptchat-wx
java1.8版本
springboot2.4.0+springframework5.3.1+com.theokanning.openai-gpt3-java0.9.0
适配微信公众号自动回复，可采用gptchat openai回复
支持微信公众号令牌和openai令牌、openai model配置文件设置

微信公众号服务器URL配置步骤


##1.配置工程参数
![image](https://user-images.githubusercontent.com/26601855/217408368-12184a48-7974-4292-abb9-d6b4a9d1638e.png)

##2.启动工程服务
可直接打包成gptchat-wx.jar，根据服务器的情况放到相应的位置；测试位置是放在/www/wwwroot/wx/，可自行调整
/usr/bin/java -jar -Xmx1024M -Xms256M  /www/wwwroot/wx/gptchat-wx.jar --server.port=80

若是服务器上有宝塔的，可直接配置
![image](https://user-images.githubusercontent.com/26601855/217409565-74bb184b-f38c-4f4c-ae50-d281e1b99670.png)

##3.微信公众号平台配置
访问https://mp.weixin.qq.com/
在设置与开发-基本配置
![image](https://user-images.githubusercontent.com/26601855/217407635-d10c94e4-110d-41d9-97c7-e9ba2a087234.png)
编辑服务器地址、令牌（可随意填写，不过要与配置工程参数my.wx.wxToken一致）、生成消息加解密密钥后提交； 微信公众号平台会GET调用服务器URL地址进行令牌验证，验证通过后才能保存成功。

##4.验证
微信公众号平台配置和启用后，稍微一分钟开始在您自己的公众号发送消息即可验证；
不过在微信公众号里面发送的消息较为简单的时候，openai会快速反应；若是遇到复杂问题，其实你的后端openai还在响应和整理结果，不过微信公众号平台调用你的服务会超时，届时公众号会提示“该公众号提供的服务出现故障，请稍后再试”；这个地方可根据您自身的情况配置openai的model或者其他参数来调整。

##既然看完了感谢您对作者的支持，烦请右上角点亮五角星Star

##若有疑问可与作者联系
