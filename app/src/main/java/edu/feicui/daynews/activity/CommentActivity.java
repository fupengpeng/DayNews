package edu.feicui.daynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.feicui.daynews.R;
import edu.feicui.daynews.ServerUrl;
import edu.feicui.daynews.activity.BaseActivity;
import edu.feicui.daynews.adapter.CommentListAdapter;
import edu.feicui.daynews.adapter.NewsListAdapter;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.CommentArray;
import edu.feicui.daynews.entity.CommentListArray;
import edu.feicui.daynews.entity.CommentListInfo;
import edu.feicui.daynews.net.MyHttp;
import edu.feicui.daynews.net.OnResultFinishListener;
import edu.feicui.daynews.net.Response;
import edu.feicui.daynews.view.XListView;

/**
 * Created by Administrator on 16-10-12.
 */
public class CommentActivity extends BaseActivity implements XListView.IXListViewListener, AdapterView.OnItemClickListener, View.OnClickListener {
    EditText mETComment;//评论
    ImageView mIVComment;//提交评论
    XListView mXLVComment;//评论列表
    CommentListAdapter mXListAdapterComment;//适配器
    int dir = 1;//dir=1  刷新   dir=2  加载
    int cid = 1;//最后一条的id   用于上拉加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }

    @Override
    protected void initView() {
        mImgLeft.setImageResource(R.mipmap.back);
        Log.e("aaaa", "initView: 01" );
        mTvTittle.setText("评论");
        Log.e("aaaa", "initView: 02" );
        mImgRight.setVisibility(View.GONE);
        Log.e("aaaa", "initView: 03" );
        mXLVComment= (XListView) findViewById(R.id.xlv_comment);Log.e("aaaa", "initView: 04" );
        mXListAdapterComment=new CommentListAdapter(this,null,R.layout.item_comment);Log.e("aaaa", "initView: 05" );
        mXLVComment.setAdapter(mXListAdapterComment);//绑定适配器
        mXLVComment.setPullLoadEnable(true);//是否可以进行上拉加载
        mXLVComment.setPullRefreshEnable(true);//是否可以进行下拉刷新
        mXLVComment.setXListViewListener(this);//上下拉监听
        mXLVComment.setOnItemClickListener(this);//评论列表
        Log.e("aaaa", "initView: 06" );
        mETComment= (EditText) findViewById(R.id.et_comment);
        mIVComment= (ImageView) findViewById(R.id.iv_comment);
        mXLVComment= (XListView) findViewById(R.id.xlv_comment);Log.e("aaaa", "initView: 07" );
        mETComment.setOnClickListener(this);//评论内容
        mIVComment.setOnClickListener(this);//提交评论
        mImgLeft.setOnClickListener(this);//点击返回上一页面
        Log.e("aaaa", "initView: 08" );
        getHttpDataCommentList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    /**
     * 显示评论
     * cmt_list ?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
     */
    public void getHttpDataCommentList(){
        Log.e("aaaa", "initView: 09" );
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000");//版本号
        params.put("nid", AppInfo.news.nid+"");//新闻id  在新闻列表的子条目点击事件中保存在静态类AppInfo中  这里可以直接用类名拿到
        params.put("type","1");
        params.put("stamp","20140321000000");//评论时间
        params.put("cid",""+cid);//评论id
        params.put("dir",""+dir);//刷新列表的方向
        params.put("cnt","20");//数目
        MyHttp.get(this, ServerUrl.COMMENT_LIST, params,
                new OnResultFinishListener() {
                    @Override
                    public void success(Response response) {//获取评论列表成功
                        Gson gson = new Gson();
                        Log.e("aaaa", "initView: 10" );
                        CommentListArray commentListArray = gson.fromJson((String) response.result, CommentListArray.class);
                        if (commentListArray.status==0){//判断是否正常响应
                            /**
                         * 有数据  进行添加并且刷新
                         */
                            if (commentListArray.data != null && commentListArray.data.size() > 0) {
                                mXListAdapterComment.mList.addAll(commentListArray.data);
                                mXListAdapterComment.notifyDataSetChanged();//刷新数据
                            }
                        }
                        Log.e("aaaa", "initView: 11" );
                        Toast.makeText(CommentActivity.this,"加载成功！",Toast.LENGTH_SHORT).show();
                        //关闭上拉加载以及下拉刷新框
                        mXLVComment.stopLoadMore();
                        mXLVComment.stopRefresh();
                    }
                    @Override
                    public void failed(Response response) {//获取评论列表失败
                        Toast.makeText(CommentActivity.this,"加载失败！",Toast.LENGTH_SHORT).show();
                        //关闭上拉加载以及下拉刷新框
                        mXLVComment.stopLoadMore();
                        mXLVComment.stopRefresh();
                    }
                });
    }
    /**
     * 发布评论
     * cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
     */
    public void getHttpDataComment(){
        Log.e("aaaa", "initView: 12" );
        Map<String,String> pamars=new HashMap<>();
        pamars.put("ver","0000000");//版本号
        pamars.put("nid", AppInfo.news.nid+"");//新闻编号
        pamars.put("token",AppInfo.login.token);//用户令牌
        pamars.put("imei","868564020877953");//用户标识符
        pamars.put("ctx",mETComment.getText().toString());//评论内容
        Log.e("aaaa", "initView: 13" );
        MyHttp.get(this, ServerUrl.PUBLISH_COMMENT, pamars,
                new OnResultFinishListener() {
                    @Override
                    public void success(Response response) {//发布评论成功
                        Gson gson = new Gson();
                        CommentArray CommentArray = gson.fromJson((String) response.result, CommentArray.class);
                        Toast.makeText(CommentActivity.this,CommentArray.message, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void failed(Response response) {//发布评论失败
                        Toast.makeText(CommentActivity.this,"发布失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onRefresh() {
        Log.e("aaaa", "initView: 14" );
        //进行下拉刷新操作
        dir = 1;
        //清空之前的数据
        mXListAdapterComment.mList.clear();
        //再调用获取评论列表的方法
        getHttpDataCommentList();
        Log.e("aaaa", "initView: 15" );

    }
    @Override
    public void onLoadMore() {
        Log.e("aaaa", "initView: 16" );
        //上拉加载
        dir = 2;
        //拿到最后一条的id
        if (mXListAdapterComment.mList.size() > 0) {
            Log.e("aaaa", "initView: 17" );
            CommentListInfo comments = mXListAdapterComment.mList.get(mXListAdapterComment.mList.size()-1);//拿到当前的最后一条id
            cid = comments.cid;//将拿到的cid给cid
        }
        //再调用获取评论列表的方法
        getHttpDataCommentList();
        Log.e("aaaa", "initView: 18" );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_base_left://返回上一页面
                Log.e("aaaa", "initView: 19" );
                finish();
                break;
            case R.id.et_comment:
                finish();
                break;
            case R.id.iv_comment://提交评论
                Log.e("aaaa", "initView: 20" );
                if(AppInfo.isLogin==true){
                    getHttpDataComment();
                }else{
                    Intent intentLogin=new Intent(this, HomeActivity.class);
                    startActivity(intentLogin);
                }
                break;
            case R.id.xlv_comment://评论列表
                Log.e("aaaa", "initView: 21" );
                finish();
                break;
        }
    }
}
