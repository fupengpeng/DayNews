package edu.feicui.daynews.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import edu.feicui.daynews.R;
import edu.feicui.daynews.ServerUrl;
import edu.feicui.daynews.activity.HomeActivity;
import edu.feicui.daynews.activity.MyAccountActivity;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.LoginArray;
import edu.feicui.daynews.entity.UserInfo;
import edu.feicui.daynews.net.MyHttp;
import edu.feicui.daynews.net.OnResultFinishListener;
import edu.feicui.daynews.net.Response;

/**
 * Created by Administrator on 16-10-11.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, TextWatcher {
    boolean isLogin;//登陆状态
    Button mBtnEnroll;//注册
    Button mBtnForgetPassword;//忘记密码
    Button mBtnLogin;//登录
    HomeActivity homeActivity;//声明获取主页面的activity
    View mView;
    TextInputLayout mTILNikename;//登录昵称  布局
    TextInputEditText mTIETNikename;//登录昵称  输入框
    TextInputLayout mTILPassword;//登录密码  布局
    TextInputEditText mTIETPassword;//登录密码  输入框


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView=getView();//获取View
        mBtnEnroll= (Button) findViewById(R.id.btn_right_login_enroll);//右边图片立刻登录后  注册点击事件控件获取
        mBtnForgetPassword= (Button) findViewById(R.id.btn_right_login_password);//右边图片立刻登录后  忘记密码点击事件控件获取
        mBtnLogin = (Button) findViewById(R.id.btn_right_login_login);//右边图片立刻登录后  登录点击事件控件获取
        mBtnEnroll.setOnClickListener(this);//右边图片立刻登录后  注册点击事件监听
        mBtnForgetPassword.setOnClickListener(this);//右边图片立刻登录后  忘记密码点击事件监听
        mBtnLogin.setOnClickListener(this);//右边图片立刻登录后  登录点击事件监听

        mTILNikename= (TextInputLayout) findViewById(R.id.til_right_login_nikename);//登录昵称  布局  获取控件
        mTIETNikename= (TextInputEditText) findViewById(R.id.tiet_right_login_nikename);//登录昵称  输入框  获取控件
        mTILPassword= (TextInputLayout) findViewById(R.id.til_right_login_password);//登录密码  布局  获取控件
        mTIETPassword= (TextInputEditText) findViewById(R.id.tiet_right_login_password);//登录密码  输入框  获取控件
        mTIETNikename.addTextChangedListener(this);//昵称  文本改变监听
        mTIETPassword.addTextChangedListener(this);//密码  文本改变监听
    }
    public View findViewById(int id){
        return mView.findViewById(id);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeActivity= (HomeActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_right_login_enroll:
                Toast.makeText(homeActivity,"注册====",Toast.LENGTH_SHORT).show();
                /*在碎片中的点击控件跳转碎片
                    1.在碎片调用onViewCreated方法，在onViewCreated方法中获取控件、监听、实现onClick方法。
                    2.在碎片中调用onActivityCreated方法，在方法中获取页面Activity对象。
                    3.在Activity中获取碎片，使用碎片管理器获取碎片对象，使用碎片对象开启事物，并将碎片添加到页面布局中。
                    4.在Activity中写一个切换碎片的方法，用来隐藏页面原来的碎片，展示添加的需要的碎片，并提交。
                    5.在碎片中onClick方法中，使用Activity对象调用页面切换碎片的方法，实现碎片中点击跳转碎片
                    */
                homeActivity.getLoginFragment(0);
                break;
            case R.id.btn_right_login_password:
                Toast.makeText(homeActivity,"忘记密码====",Toast.LENGTH_SHORT).show();
                homeActivity.getLoginFragment(1);
                break;
            case R.id.btn_right_login_login:
                Toast.makeText(homeActivity,"登录====",Toast.LENGTH_SHORT).show();
                if (mTIETNikename.getText().toString().equals("")||
                        mTIETPassword.getText().toString().equals("")){  //判断如果密码或者用户名为空，则不让其点击登陆按钮
                    Toast.makeText(homeActivity, "密码/账号不能为空", Toast.LENGTH_SHORT).show();
                    homeActivity.getLoginFragment(2);//通知HomeActivity  打开登陆的碎片(在此界面的效果是 只弹出吐丝窗口)
                }else{//不为空
                    getHttpDataLogin();//调用登陆的接口
                    break;
                }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mTIETNikename.hasFocus()){//判断选定的输入框的焦点是否在此
            if (mTIETNikename.length()>=6&&mTIETNikename.length()<=24){//判断昵称是否符合6--24位的要求
                mTIETNikename.setError(null);//符合要求  继续输入密码
            }else {
                mTIETNikename.setError("昵称格式不正确，请输入正确格式的昵称");
            }
        }else if (mTIETPassword.hasFocus()){//判断选定的输入框的焦点是否在此
            Pattern pattern=Pattern.compile("[a-zA-Z0-9_]{6,24}");//使用正则确定密码格式
            //将输入字符串s与确定的密码格式所能产生的结果匹配是否符合 返回布尔值
            boolean b=pattern.matcher(s).matches();//Matcher m=pattern.matcher(s);  boolean b=m.matches();
            if (!b){
                mTIETPassword.setError("密码格式不正确，请重新输入");
            }else {
                mTIETPassword.setError(null);
            }
        }
    }
    /**
     * user_login?ver=版本号&uid=用户名&pwd=密码&device=0
     * 登陆
     */
    public void getHttpDataLogin(){
        Map<String,String> params=new HashMap<>();//请求格式集合
        params.put("ver","0000000");//版本
        params.put("uid",mTIETNikename.getText().toString());//用户名---用户昵称
        params.put("pwd",mTIETPassword.getText().toString());//密码
        params.put("device","0");//登陆设备   0为手机客户端   1为PC网页端
        MyHttp.get(homeActivity, ServerUrl.USER_LOGIN, params,
                new OnResultFinishListener() {
                    @Override
                    public void success(Response response) {
                        Gson gson=new Gson();
                        LoginArray loginArray=gson.fromJson((String) response.result,LoginArray.class);
                        AppInfo.login =loginArray.data;//将登陆的信息保存在静态类中
                        if (AppInfo.login.token!=""){//如果有了用户令牌
                            isLogin=true;//将登陆状态置为true
                            AppInfo.isLogin=isLogin;//将登录状态保存
                        }
                        Intent intent=new Intent(homeActivity,MyAccountActivity.class);//跳转到我的账户中心
                        startActivity(intent);
                        Toast.makeText(homeActivity,loginArray.data.explain,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void failed(Response response) {//登陆失败
                        Toast.makeText(homeActivity,"登陆失败",Toast.LENGTH_SHORT).show();
                    }


                });
    }



}
