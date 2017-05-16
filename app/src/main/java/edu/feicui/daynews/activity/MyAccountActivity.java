package edu.feicui.daynews.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.feicui.daynews.R;
import edu.feicui.daynews.ServerUrl;
import edu.feicui.daynews.adapter.LoginLogAdapter;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.LoginLog;
import edu.feicui.daynews.entity.UserArray;
import edu.feicui.daynews.net.MyHttp;
import edu.feicui.daynews.net.OnResultFinishListener;
import edu.feicui.daynews.net.Response;

/**
 * 我的帐号
 * Created by Administrator on 16-10-17.
 */
public class MyAccountActivity extends BaseActivity implements View.OnClickListener {
    static final int L=200;//初始化请求码
    static final int PERMISSION_CAMERA=201;//初始化相机权限的请求码
    ImageView mIVALPortrait;//用户图像
    TextView mTVMANickname;//用户名
    TextView mTVMAIntegral;//积分
    TextView mTVMAComment;//跟帖数
    TextView mTVMALog;//登录日志
    RecyclerView mRVMALog;//登录日志展示
    Button mBtnQuit;//退出登录
    UserArray userArray;//用户中心的信息


    LoginLogAdapter loginLogAdapter;//登录日志列表的适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_my);
    }

    @Override
    protected void initView() {
        mTvTittle.setText("我的账号");
        mImgRight.setVisibility(View.GONE);
        mImgLeft.setImageResource(R.mipmap.back);
        mIVALPortrait= (ImageView) findViewById(R.id.iv_activity_account_my_portrait);//用户图像
        mTVMANickname= (TextView) findViewById(R.id.tv_activity_account_my_nickname);//用户名
        mTVMAIntegral= (TextView) findViewById(R.id.tv_activity_account_my_integral);//积分
        mTVMAComment= (TextView) findViewById(R.id.tv_activity_account_my_comment);//跟帖数
        mTVMALog= (TextView) findViewById(R.id.tv_activity_account_my_log);//登录日志
        mRVMALog= (RecyclerView) findViewById(R.id.rv_activity_account_my_log);//登录日志展示
        mBtnQuit= (Button) findViewById(R.id.btn_activity_account_my_quit);//退出登录
        mIVALPortrait.setOnClickListener(this);//用户图像
        mBtnQuit.setOnClickListener(this);//退出
        mImgLeft.setOnClickListener(this);//返回
        getHttpDataUserHome();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_activity_account_my_portrait:
                Toast.makeText(this,"跳转相机====",Toast.LENGTH_SHORT).show();
                Intent intentCamera=new Intent();//22C.使用Intent跳转相机  需要权限
//                intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//22C-1.跳转相机意图
//                startActivityForResult(intentCamera,CHOOSE_PEO);//22.跳转至intent  请求码  需要结果
                //Builder
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前sdk版本是否大于等于23
                    if(checkSelfPermission(android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){//判断是否拥有此权限
                        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//22C-1.跳转相机意图
                        startActivityForResult(intentCamera,L);//22.跳转至intent  请求码  需要结果
                    }else{//没有权限，获取权限
                        //请求权限，new String []{Manifest.permission.CAMERA} 请求权限的数组，可请求多个权限
                        //          PERMISSION_CAMERA  请求码
                        requestPermissions(new String []{android.Manifest.permission.CAMERA},PERMISSION_CAMERA);
                    }
                }else{//当前sdk版本小于等于23
                    intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//22C-1.跳转相机意图
                    startActivityForResult(intentCamera,L);//22.跳转至intent  请求码  需要结果
                }
                break;
            case R.id.btn_activity_account_my_quit:
                Toast.makeText(this,"退出====",Toast.LENGTH_SHORT).show();
                Intent intentQuit=new Intent(this,HomeActivity.class);//跳转到新闻首页
                startActivity(intentQuit);
                AppInfo.isLogin=false;//登录状态改变

                break;
            case R.id.iv_base_left:
                Toast.makeText(this,"返回====",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,HomeActivity.class);//跳转到新闻首页
                startActivity(intent);
                break;
        }
    }

    /**
     * 请求权限结果
     * @param requestCode  请求码
     * @param permissions  所请求的权限
     * @param grantResults  所请求权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){//根据请求码区分请求
            case PERMISSION_CAMERA://相机请求
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){//判断是否获取到权限  同意相机权限
                    Intent intentCamera=new Intent();//22C.使用Intent跳转相机  需要权限
                    startActivity(intentCamera);
                }else {//没有获取相机权限
                    Toast.makeText(this,"未获取相机权限，无法启动相机拍照",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /**
     * 用户中心
     * user_home?ver=版本号&imei=手机标识符&token=用户令牌
     */
    public void getHttpDataUserHome(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000");//版本
        params.put("imei","868564020877953");//手机标识符  从手机获取的  在拨号处  打出  *#06# 的字样  会拿自动弹出MEID和IMEI
        params.put("token", AppInfo.login.token);//用户令牌   登陆的时候的参数 直接拿来使用（从LoginFragment传递过来）
        MyHttp.get(this, ServerUrl.USER_HOME, params,
                new OnResultFinishListener() {
                    @Override
                    public void success(Response response) {//获取用户信息成功
                        Gson gson=new Gson();
                        userArray=gson.fromJson((String) response.result,UserArray.class);
                        Toast.makeText(MyAccountActivity.this,userArray.message, Toast.LENGTH_SHORT).show();
                        AppInfo.user=userArray.data;//赋值
                        if (AppInfo.isLogin){
                            userArray.data=AppInfo.user;
                        }
                        mTVMANickname.setText(userArray.data.uid);//将我的账户名展示出来
                        mTVMAIntegral.setText("积分："+userArray.data.integration);//将积分展示出来
                        mTVMAComment.setText("跟帖统计数："+userArray.data.comnum);//将跟帖数量展示出来
                        ArrayList<LoginLog> list=userArray.data.loginlog;//数据源
                        mRVMALog.setLayoutManager(new LinearLayoutManager(MyAccountActivity.this));//给RecyclerView列表设置布局管理器
                        loginLogAdapter=new LoginLogAdapter(list,MyAccountActivity.this);//初始化登陆日志RecyclerView列表的 适配器
                        mRVMALog.setAdapter(loginLogAdapter);//绑定适配器
                    }
                    @Override
                    public void failed(Response response) {//获取用户信息失败
                        Toast.makeText(MyAccountActivity.this,"获取用户中心失败",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
