# crash
自定义异常捕捉库
#### 使用
```gradle
  //添加依赖
  implementation 'com.github.bingoloves:crash:1.0.2'
```
```java
  //在Application中初始化即可，默认不配置就可以使用，一般线上设为false则不再拦截奔溃异常
  //CrashHelper.init(true,0,null);
```