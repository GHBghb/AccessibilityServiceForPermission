# AccessibilityServiceForPermission

AccessibilityService是辅助权限，是Google专门为有障碍人士准备的功能，但是却被国内的一些开发者用来
进行一些其他操作：权限开启、抢红包、获取隐私等。本项目主要是本着学习的精神来学习使用该功能。
如图：运行后点击打开辅助权限，用户手动打开权限后返回点击辅助设置，之后app会进行自行跳转到浮窗权限设置界面，打开浮窗权限，跳回后再次跳转到自启动权限界面
打开自启动功能，之后是受保护授权。

工程中使用了josn配置，对不同手机、不同机型的跳转、按钮的id做了相应配置来最大限度适应不同系统
![image](https://github.com/GHBghb/AccessibilityServiceForPermission/blob/master/shot.png)
