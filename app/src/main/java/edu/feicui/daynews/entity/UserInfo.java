package edu.feicui.daynews.entity;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * 用户信息
 * Created by Administrator on 16-10-24.
 */
public class UserInfo {
    public String uid;//用户id
    public String  portrait;//用户头像
    public int integration;//用户积分
    public int comnum;//跟帖数量
    public ArrayList<LoginLog> loginlog;//登陆详情
}
