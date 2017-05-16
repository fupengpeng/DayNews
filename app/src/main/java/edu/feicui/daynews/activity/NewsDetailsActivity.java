package edu.feicui.daynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.feicui.daynews.R;
import edu.feicui.daynews.ServerUrl;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.CommentNumInfo;
import edu.feicui.daynews.entity.NewsInfo;
import edu.feicui.daynews.net.MyHttp;
import edu.feicui.daynews.net.OnResultFinishListener;
import edu.feicui.daynews.net.Response;

/**
 * 新闻详情页面
 * Created by Administrator on 16-10-12.
 */
public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener{
    View mView;
    WebView mWVDatails;//
    TextView mTVComment;//
    ImageView mIVFavorite;//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_datails);
    }

    @Override
    protected void initView() {
        mImgLeft.setImageResource(R.mipmap.back);
        mImgRight.setVisibility(View.GONE);
        mTVComment= (TextView) findViewById(R.id.tv_base_comment);
        mIVFavorite= (ImageView) findViewById(R.id.iv_base_favorite);
        mTVComment.setVisibility(View.VISIBLE);
        mIVFavorite.setVisibility(View.VISIBLE);
        mImgLeft.setOnClickListener(this);
        mTVComment.setOnClickListener(this);
        mIVFavorite.setOnClickListener(this);

        //使用布局填充器 将popup布局加载进来
//        mView=getLayoutInflater().inflate(R.layout.view_detailsnews_add_favorite_popup,null);

        mWVDatails= (WebView) findViewById(R.id.wv_news_datails);
        Intent intent=getIntent();//获取Intent对象
        NewsInfo news= (NewsInfo) intent.getSerializableExtra("news");//接收Intent传递过来的对象
        mWVDatails.loadUrl(news.link);//加载传递过来的链接
        WebSettings settings=mWVDatails.getSettings();//添加一些设置
        settings.getJavaScriptEnabled();//支持JavaScript写的网页
        settings.setUseWideViewPort(true);//
        settings.setLoadWithOverviewMode(true);//支持按比例缩放
        settings.setSupportZoom(true);//支持缩放功能
        settings.setBuiltInZoomControls(true);//显示缩放视图
        mWVDatails.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_base_comment://点击跟帖跳转至评论页面
                Intent intent=new Intent(this, CommentActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_base_favorite://点击选择加入收藏

                break;
            case R.id.iv_base_left://点击返回新闻页面
                finish();
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//返回键的监听
        if (keyCode==KeyEvent.KEYCODE_BACK&&mWVDatails.canGoBack()){//返回之前网页
            mWVDatails.goBack();//返回
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * cmt_num?ver=版本号& nid=新闻编号
     * 跟贴数量
     */
    public void getHttpDataCommentNum(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000");//版本号
        params.put("nid", AppInfo.news.nid+"");//新闻编号
        MyHttp.get(this, ServerUrl.COMMENT_NUMBER, params,
                new OnResultFinishListener() {
                    @Override
                    public void success(Response response) {//请求成功
                        Gson gson=new Gson();
                        CommentNumInfo commentNumInfo=gson.fromJson((String) response.result,CommentNumInfo.class);
                        Log.e("aaa", "success: "+commentNumInfo.data);
                        AppInfo.commentNum=commentNumInfo.data;
                    }
                    @Override
                    public void failed(Response response) {//请求失败

                    }
                });
    }
}
