# 企业微信app自动化测试
## 获取企业微信的app包名
```shell
#获取第三方包名
adb shell pm list packages -3
```
`com.tencent.wework`
## 获取企业微信的appActivity页面
```shell
#获取打开app的首页
adb shell monkey -p  <package_name> -vvv 1
```

`adb shell monkey -p  com.tencent.wework -vvv 1`
- `cmp=com.tencent.wework/.launch.LaunchSplashActivity`