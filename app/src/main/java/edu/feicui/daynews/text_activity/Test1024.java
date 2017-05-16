package edu.feicui.daynews.text_activity;

/**
 * 10月24日  ContentProvider
 * Created by Administrator on 16-10-24.
 */
public class Test1024 {
    /*
    内容提供者
        提供者    使用者
        跨应用共享数据
        A数据库
            将数据通过提供者提供出去
        B通过提供者
            操作数据
        通信软件
            读系统的   通过提供者   通信录，通话记录
    ContentProvider  四大组件之一    使跨应用数据共享成为可能
        使用：
            1.新建类继承ContentProvider，重写抽象方法（增删改查等方法）
                找到其他应用的提供者，通过Uri寻找提供者
            2.注册----><provider>标签
                android: authorities="包名.类名"  权限  其他应用通过此参数定位此Provider
            3.Uri
                其他应用通过Uri定位此Provider
                    Uri.parse
                    Uri.fromFile
                Uri:统一资源定位符
                Url：统一资源标识符
                标准URI：协议://用户名：密码@主机名：端口号/路径/文件?参数1=
                "content://authorities参数/具体路径"
                提供者的应用：
                    接收访问者传递的Uri  用来确定访问者想要访问那个具体的数据
                    对访问者提供的Uri匹配
                        1.添加所有可以匹配的Uri
                        2.匹配  获取对应的Uri的 code
                        3.通过code去操作相应的数据
                        UriMatcher类（可直接new）
                            UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
                            uriMatcher.addURI();//参数1：authorities权限参数，参数2：操作文件路径，# 匹配所有数字 * 匹配全部，参数3：匹配码
                            uriMatcher.match(提供者提供的Uri);//返回匹配成功的匹配码，匹配失败返回-1
                访问者的应用：
                    向提供者传递想要访问数据的Uri
                        Uri.parse("");//获取Uri传递给提供者
            4.
     */
}
