package edu.feicui.daynews.text_activity;

/**
 * Created by Administrator on 16-10-20.
 */
public class ShareSDKActivity {
    /*
    ShareSDK
        融云  环信  即时聊天
        友盟  统计---->用户量，用户存留时间等的统计

     */

     /*
    SlidingPaneLayout
        1.只支持左滑
        2.布局中左侧控件必须放前面
    SlidingMenu    第三方  导入jar包
        支持左右侧滑   可将主页面内容推动
        1.初始化SlidingMenu
            SlidingMenu  menu=new SlidingMenu(this);//初始化SlidingMenu
        2.关联当前Activity
            menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);//关联当前Activity
            menu.attachToActivity(this,SlidingMenu.SLIDING_WINDI);//关联当前Activity
        3.设置左边  需要左边布局View
            LayoutInflater inflater=getLayoutInflater();//需要View   需要布局填充器
            View leftView=inflater.inflate(R.layout.view_left,null);//获取view
            ListView  mListView;//获取布局中的控件
            mListView=(ListView)left.findViewId();
            menu.setMenu();//设置左边  需要左边布局View
            menu.setSecondaryMenu()://设置右边
        4.设置显示方式  控制两边显示方式（左显示还是右显示，还是左右都显示）
            menu.setMode//设置显示方式
        5.设置边界距离    防止全部覆盖页面，不能再滑回
            menu.setBehindOffset(150);
        6.设置左右页面的触摸方式，触摸滑回主页面
            menu.setTouchModeAbove();//三个参数表示触摸模式
        7.可以设置标题左右上侧按钮点击是否进入左右侧页面
            三个方法
     */

    /*
    版本更新
        原理：
            1.检查当前应用是否是最新版本
                a.获取当前版本号  PackageInfo
                b.和服务器最新版本对比（联网对比）
            2.服务端下载最新应用APK  通过网络请求和IO流
            3.下载链接   并且提示安装apk
                a.请求下载链接下载APK
                    方式：
                        1.使用流自己进行读写
                        2.调用系统的下载管理器（使用DownloadManager类）
                            a.获取DownloadManager对象，需要一个参数Context.DOWNLOADMANAGER
                            b.需要创建一个下载请求。使用DownloadManager类的内部类Request对象。需要参数一个Uri对象（下载地址链接）
                            c.设置  网络类型  Request类的方法setAllowedNetworkTypes选择网络类型
                                    通知栏  setShowRunningNotification(true);
                                            setDestinationInExternalFilesDir（this,null,下载路径);
                            d.提示用户是否下载（默认下载）  manager.enqueue(request);0
            4.监听下载完成  并且打开此文件(隐式意图)
                1.系统下载完成后发广播，新建Reciver广播管理器接收系统所发下载完成的广播（注册，反注册）获取下载完成触发事件
                2.打开下载的APK  使用Intent跳转新的    intent.setDataAndType(Uri.fromFile(new File("文件路径")),"路径  app；alsdadakdflkajhdal");
     */
}
