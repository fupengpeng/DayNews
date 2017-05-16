package edu.feicui.daynews.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 16-10-14.
 */
public class LoginInfo implements Serializable{
    public int result;//登陆状态  0：正常登陆
    public String explain;//登陆说明
    public String token;//用户令牌
}
