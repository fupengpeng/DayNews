package edu.feicui.daynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.feicui.daynews.R;
import edu.feicui.daynews.activity.fragment.CommtentFragment;
import edu.feicui.daynews.activity.fragment.EnrollFragment;
import edu.feicui.daynews.activity.fragment.FavoriteFragment;
import edu.feicui.daynews.activity.fragment.ForgetPasswordFragment;
import edu.feicui.daynews.activity.fragment.LocalFragment;
import edu.feicui.daynews.activity.fragment.NewsFragment;
import edu.feicui.daynews.activity.fragment.PhotoFragment;
import edu.feicui.daynews.activity.fragment.LoginFragment;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.UserInfo;
import edu.feicui.daynews.view.XListView;

/**
 * Created by Administrator on 16-10-8.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    NewsFragment mNewsFragment=new NewsFragment();//新闻列表的Fragment.
    FavoriteFragment mFavoriteFragment=new FavoriteFragment();//收藏列表的Fragment.
    CommtentFragment mCommtentFragment=new CommtentFragment();//跟帖列表的Fragment.
    PhotoFragment mPhotoFragment=new PhotoFragment();//图片列表的Fragment.
    LocalFragment mLocalFragment=new LocalFragment();//本地列表的Fragment.

    LoginFragment mLoginFragment =new LoginFragment();//右边图片登录的Fragment

    EnrollFragment mEnrollFragment=new EnrollFragment();//右边登录碎片的注册按钮点击后的碎片
    ForgetPasswordFragment mForgetPasswordFragment=new ForgetPasswordFragment();//右边登录碎片的忘记密码按钮点击后的碎片

    DrawerLayout mDLHome;//抽屉
    LinearLayout mLinearLayoutBase;//资讯标题

    RelativeLayout mRLLNews;//新闻列表布局
    RelativeLayout mRLLFavorites;//收藏布局
    RelativeLayout mRLLLocals;//本地布局
    RelativeLayout mRLLComments;//跟帖布局
    RelativeLayout mRLLPhotos;//图片布局

    ImageView mIVRLogin;//右边图片登录
    TextView mTVRLogin;//右边字体登录
    TextView mTVRUpdate;//右边更新版本
    ImageView mIVRWachat;//右边微信
    ImageView mIVRQQ;//右边qq
    ImageView mIVRFriends;//右边朋友圈
    ImageView mIVRMicroblog;//右边新浪微博

    FragmentManager fragmentManager = getSupportFragmentManager();//获取FragmentManager对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);//加载home布局
        Toast.makeText(HomeActivity.this,"进入主页面",Toast.LENGTH_SHORT).show();
        Log.e("aaa", "----HomeActivity类----onCreate----");
        ShareSDK.initSDK(this,"181ee2f219337");
    }
    @Override
    protected void initView() {
        mDLHome = (DrawerLayout) findViewById(R.id.dl_home);//
        mImgLeft=(ImageView) findViewById(R.id.iv_base_left);//
        mImgRight=(ImageView) findViewById(R.id.iv_base_right);
        mTvTittle=(TextView) findViewById(R.id.tv_base_tittle);
        mImgRight.setOnClickListener(this);//点击左图片监听
        mImgLeft.setOnClickListener(this);//点击右图片监听

        Log.e("aaa", "----HomeActivity类----initView----");
        mDLHome.setDrawerListener(new DrawerLayout.DrawerListener() {//fragment监听
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                        mLinearLayoutBase.setVisibility(View.GONE);//隐藏资讯标题栏
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                        mLinearLayoutBase.setVisibility(View.VISIBLE);//显示资讯标题栏
                mTvTittle.setText("资讯");
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });


        mLinearLayoutBase= (LinearLayout) findViewById(R.id.ll_base_activity);//获取标题控件

        mRLLNews= (RelativeLayout) findViewById(R.id.rl_left_news);//获取新闻列表控件
        mRLLFavorites= (RelativeLayout) findViewById(R.id.rl_left_favorites);
        mRLLLocals= (RelativeLayout) findViewById(R.id.rl_left_locals);
        mRLLComments= (RelativeLayout) findViewById(R.id.rl_left_comments);
        mRLLPhotos= (RelativeLayout) findViewById(R.id.rl_left_photos);
        mRLLNews.setOnClickListener(this);//监听
        mRLLFavorites.setOnClickListener(this);
        mRLLLocals.setOnClickListener(this);
        mRLLComments.setOnClickListener(this);
        mRLLPhotos.setOnClickListener(this);

        FragmentTransaction NewsTransaction = fragmentManager.beginTransaction();//开启事物
        NewsTransaction.add(R.id.ll_home_content,mNewsFragment )//给ll_home_content容器里面装左边列表所要展示的所有的Fragment碎片
        .add(R.id.ll_home_content,mFavoriteFragment)
        .add(R.id.ll_home_content,mLocalFragment)
        .add(R.id.ll_home_content,mCommtentFragment)
        .add(R.id.ll_home_content,mPhotoFragment)
                .add(R.id.ll_home_content, mLoginFragment)//给ll_home_content容器里面装右边图片  立刻登录点击事件所要展示的Fragment碎片
                .add(R.id.ll_home_content,mEnrollFragment)//给ll_home_content容器里面装立刻登录  注册点击事件所要展示的Fragment碎片
                .add(R.id.ll_home_content,mForgetPasswordFragment)//给ll_home_content容器里面装立刻登录  忘记密码点击事件所要展示的Fragment碎片
                .show(mNewsFragment)//展示新闻列表
        .commit();//提交

        mIVRLogin= (ImageView) findViewById(R.id.iv_right_info_register);//右边图片登录
        mTVRLogin= (TextView) findViewById(R.id.tv_right_register);
        mTVRUpdate= (TextView) findViewById(R.id.tv_right_update);
        mIVRWachat= (ImageView) findViewById(R.id.iv_right_wechat);
        mIVRQQ= (ImageView) findViewById(R.id.iv_right_qq);
        mIVRFriends= (ImageView) findViewById(R.id.iv_right_friends);
        mIVRMicroblog= (ImageView) findViewById(R.id.iv_right_microblog);
        mIVRLogin.setOnClickListener(this);//右边图片登录监听
        mTVRLogin.setOnClickListener(this);
        mTVRUpdate.setOnClickListener(this);
        mIVRWachat.setOnClickListener(this);
        mIVRQQ.setOnClickListener(this);
        mIVRFriends.setOnClickListener(this);
        mIVRMicroblog.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_base_left:
                mDLHome.openDrawer(Gravity.LEFT);//点击切换左边fragment
                mLinearLayoutBase.setVisibility(View.GONE);//隐藏标题栏
                Log.e("aaa","----HomeActivity----onClick----点击切换至左边fragment----");
                break;
            case R.id.iv_base_right:
                mDLHome.openDrawer(Gravity.RIGHT);//点击切换右边fragment
                mLinearLayoutBase.setVisibility(View.GONE);
                Log.e("aaa","----HomeActivity----onClick----点击切换至右边fragment----");
                break;
            case R.id.rl_left_news:
                FragmentTransaction NewsTransaction = fragmentManager.beginTransaction();//开启事物
                Toast.makeText(this,"新闻===",Toast.LENGTH_SHORT).show();
                NewsTransaction.hide(mFavoriteFragment)
                        .hide(mLocalFragment)
                        .hide(mCommtentFragment)
                        .hide(mPhotoFragment)
                        .hide(mEnrollFragment)
                        .hide(mForgetPasswordFragment)
                        .hide(mLoginFragment)
                        .show(mNewsFragment)
                        .commit();//提交
                Log.e("aaa","----HomeActivity----onClick----点击显示新闻列表----");
                mDLHome.closeDrawer(Gravity.LEFT);
                break;
            case R.id.rl_left_favorites:
                Log.e(TAG, "----HomeActivity----onClick: "+AppInfo.user.uid );
                FragmentTransaction FavoritesTransaction = fragmentManager.beginTransaction();//开启事物
                Toast.makeText(this,"收藏====",Toast.LENGTH_SHORT).show();
                FavoritesTransaction.hide(mNewsFragment)
                        .hide(mLocalFragment)
                        .hide(mCommtentFragment)
                        .hide(mPhotoFragment)
                        .hide(mEnrollFragment)
                        .hide(mForgetPasswordFragment)
                        .hide(mLoginFragment)
                        .show(mFavoriteFragment)
                        .commit();//提交
                mDLHome.closeDrawer(Gravity.LEFT);
                break;
            case R.id.rl_left_locals:
                FragmentTransaction localsTransaction = fragmentManager.beginTransaction();//开启事物
                Toast.makeText(this,"本地====",Toast.LENGTH_SHORT).show();
                localsTransaction.hide(mNewsFragment)
                        .hide(mFavoriteFragment)
                        .hide(mCommtentFragment)
                        .hide(mPhotoFragment)
                        .hide(mEnrollFragment)
                        .hide(mForgetPasswordFragment)
                        .hide(mLoginFragment)
                        .show(mLocalFragment)
                        .commit();//提交
                mDLHome.closeDrawer(Gravity.LEFT);
                break;
            case R.id.rl_left_comments:
                FragmentTransaction commentsTransaction = fragmentManager.beginTransaction();//开启事物
                Toast.makeText(this,"跟帖====",Toast.LENGTH_SHORT).show();
                commentsTransaction.hide(mNewsFragment)
                        .hide(mLocalFragment)
                        .hide(mFavoriteFragment)
                        .hide(mPhotoFragment)
                        .hide(mEnrollFragment)
                        .hide(mForgetPasswordFragment)
                        .hide(mLoginFragment)
                        .show(mCommtentFragment)
                        .commit();//提交
                mDLHome.closeDrawer(Gravity.LEFT);
                break;
            case R.id.rl_left_photos:
                FragmentTransaction photosTransaction = fragmentManager.beginTransaction();//开启事物
                Toast.makeText(this,"图片====",Toast.LENGTH_SHORT).show();
                photosTransaction.hide(mNewsFragment)
                        .hide(mLocalFragment)
                        .hide(mCommtentFragment)
                        .hide(mFavoriteFragment)
                        .hide(mEnrollFragment)
                        .hide(mForgetPasswordFragment)
                        .hide(mLoginFragment)
                        .show(mPhotoFragment)
                        .commit();//提交
                mDLHome.closeDrawer(Gravity.LEFT);
                break;
            case R.id.iv_right_info_register:
                Toast.makeText(this,"用户登录====",Toast.LENGTH_SHORT).show();
                if (edu.feicui.daynews.entity.AppInfo.isLogin){//判断是否已经登录

                    Intent intent=new Intent(this,MyAccountActivity.class);
                    startActivity(intent);
                }else {
                    FragmentTransaction registerTransaction=fragmentManager.beginTransaction();
                    registerTransaction.show(mLoginFragment)
                            .hide(mNewsFragment)
                            .hide(mLocalFragment)
                            .hide(mCommtentFragment)
                            .hide(mFavoriteFragment)
                            .hide(mEnrollFragment)
                            .hide(mForgetPasswordFragment)
                            .hide(mPhotoFragment)
                            .commit();
                    mTvTittle.setText("用户登录");
                    mDLHome.closeDrawer(Gravity.LEFT);
                    mDLHome.closeDrawer(Gravity.RIGHT);
                }

                break;
            case R.id.tv_right_register:
                Toast.makeText(this,"用户立刻登录====",Toast.LENGTH_SHORT).show();
                if(edu.feicui.daynews.entity.AppInfo.isLogin){
                    Intent intent=new Intent(this,MyAccountActivity.class);
                    startActivity(intent);
                }else{
                    FragmentTransaction TvResgisterTransaction=fragmentManager.beginTransaction();
                    TvResgisterTransaction.show(mLoginFragment)
                            .hide(mNewsFragment)
                            .hide(mLocalFragment)
                            .hide(mCommtentFragment)
                            .hide(mFavoriteFragment)
                            .hide(mEnrollFragment)
                            .hide(mForgetPasswordFragment)
                            .hide(mPhotoFragment)
                            .commit();
                    mTvTittle.setText("用户登录");
                    mDLHome.closeDrawer(Gravity.LEFT);
                    mDLHome.closeDrawer(Gravity.RIGHT);
                }

                break;
            case R.id.tv_right_update:
                Toast.makeText(this,"当前已是最新版本，更新不了，请返回",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xlv_fragment_news:
                Intent intent=new Intent(this, NewsDetailsActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    public void getLoginFragment(int flag){
        switch (flag){
            case 0:
                Toast.makeText(this,"注册====",Toast.LENGTH_SHORT).show();
                FragmentTransaction EnrollTransaction=fragmentManager.beginTransaction();
                EnrollTransaction.show(mEnrollFragment)
                        .hide(mNewsFragment)
                        .hide(mLocalFragment)
                        .hide(mCommtentFragment)
                        .hide(mFavoriteFragment)
                        .hide(mForgetPasswordFragment)
                        .hide(mLoginFragment)
                        .hide(mPhotoFragment)
                        .commit();
                mTvTittle.setText("用户注册");
                break;
            case 1:
                Toast.makeText(this,"忘记密码====",Toast.LENGTH_SHORT).show();
                FragmentTransaction ForgetPasswordTransaction=fragmentManager.beginTransaction();
                ForgetPasswordTransaction.show(mForgetPasswordFragment)
                        .hide(mNewsFragment)
                        .hide(mLocalFragment)
                        .hide(mCommtentFragment)
                        .hide(mFavoriteFragment)
                        .hide(mEnrollFragment)
                        .hide(mLoginFragment)
                        .hide(mPhotoFragment)
                        .commit();
                mTvTittle.setText("忘记密码");
                break;
            case 2:
                Toast.makeText(this,"用户立刻登录====",Toast.LENGTH_SHORT).show();
                FragmentTransaction TvResgisterTransaction=fragmentManager.beginTransaction();
                TvResgisterTransaction.show(mLoginFragment)
                        .hide(mNewsFragment)
                        .hide(mLocalFragment)
                        .hide(mCommtentFragment)
                        .hide(mFavoriteFragment)
                        .hide(mEnrollFragment)
                        .hide(mForgetPasswordFragment)
                        .hide(mPhotoFragment)
                        .commit();
                mDLHome.closeDrawer(Gravity.LEFT);
                mDLHome.closeDrawer(Gravity.RIGHT);
                break;
        }
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(this);
    }
}
