### 简介
本项目封装了百度人脸在线api的主要接口,并提供了配置appId/apiKey等的方法, 供服务器端使用. 可以独立运行, 也可以通过maven引入到spring boot项目中.

### 使用方法
本项目使用spring boot开发, 独立运行的方法和配置请参见spring boot官方文档. 下面主要介绍在已有spring boot web项目中使用的方法.

在pom.xml中加入如下依赖即可

    <groupId>com.github.charleslzq</groupId>
    <artifactId>baidu-face-server</artifactId>
    <version>1.0.0-RC1</version>
    
在配置好的spring boot项目中, 引入该库即在spring的容器中添加了名为baiduFaceApi类型为BaiduFaceApi的bean以供注入.
在需要禁用该bean(比如在测试时提供mock的api时)可以在对应配置文件中将charleslzq.baidu.aipface.client.autoImport置为false.

与此同时, 该库自带一些controller, 提供通过rest接口调用baiduFaceApi的功能. 需要时在生效的配置类上加上EnableBaiduFaceController注解即可.
这些controller的url前缀是可配置的, 默认为/baidu-faces. 比如获取用户组列表的接口, url相对路径是/groups, 那么完整的路径就是
{主机地址}/{url前缀}/groups. 可通过配置项charleslzq.baidu.aipface.basePath来改变url前缀.

##### 配置
除了用于指定是否自动导入配置类及指定url前缀的配置外, 其它配置都是提供给百度人脸的java sdk的. 配置前缀为charleslzq.baidu.aipface.client, 各配置项
跟百度官方文档所提到的配置项一一对应.  
