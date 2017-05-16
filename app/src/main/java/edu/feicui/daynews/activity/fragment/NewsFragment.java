package edu.feicui.daynews.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.feicui.daynews.R;
import edu.feicui.daynews.ServerUrl;
import edu.feicui.daynews.activity.NewsDetailsActivity;
import edu.feicui.daynews.adapter.NewsListAdapter;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.NewsArray;
import edu.feicui.daynews.entity.NewsInfo;
import edu.feicui.daynews.entity.NewsSortArray;
import edu.feicui.daynews.net.MyHttp;
import edu.feicui.daynews.net.OnResultFinishListener;
import edu.feicui.daynews.net.Response;
import edu.feicui.daynews.view.XListView;

/**
 * Created by Administrator on 16-10-10.
 */
public class NewsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener, View.OnClickListener {
    /**
     * XListView
     * 步骤使用：
     *     1.导入XListView所需代码
     *     2.布局中使用代替列表
     *     3.代码中进行设置
     *         设置可以进行上拉加载
     *         可以进行下拉刷新
     *     4.绑定  刷新 加载  事件监听
     *         实现  刷新以及加载方法
     *     注意：刷新或者加载结束   需要停止刷新以及加载
     */
    FragmentActivity activity;
    View mView;
    XListView mXListViewNews;
    NewsListAdapter mXListAdapterNews;
    Button mBtnHSociety;//社会
    Button mBtnHMilitary;//军事
    Button mBtnHFund;//基金
    Button mBtnHPhone;//手机
    Button mBtnHEPL;//英超
    Button mBtnHNBA;//NBA
    Button mBtnHStock;//股票
    ImageView mIVHUnfold;//下一页
    int dir=1;//第一条新闻的id   用于下拉刷新
    int nid=1;//最后一条新闻的id   用于上拉加载
    int subid;//分类
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }
public View findViewById(int id){
    return mView.findViewById(id);
}
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView=getView();//获取
        activity=getActivity();
        mXListViewNews = (XListView) findViewById(R.id.xlv_fragment_news);//获取控件
        mXListAdapterNews =new NewsListAdapter(activity,null,R.layout.item_news);//初始化分类列表
        mXListViewNews.setAdapter(mXListAdapterNews);//绑定适配器
        mXListViewNews.setPullLoadEnable(true);//是否可以进行上拉加载
        mXListViewNews.setPullRefreshEnable(true);//是否可以进行下拉刷新
        mXListViewNews.setXListViewListener(this);//上下拉监听
        mXListViewNews.setOnItemClickListener(this);
        getHttp();

        mBtnHSociety = (Button) mView.findViewById(R.id.btn_home_society);//社会  获取控件
        mBtnHMilitary = (Button) findViewById(R.id.btn_home_military);//军事  获取控件
        mBtnHFund= (Button) findViewById(R.id.btn_home_fund);//基金
        mBtnHPhone= (Button) findViewById(R.id.btn_home_stock);//手机
        mBtnHEPL= (Button) findViewById(R.id.btn_home_epl);//英超
        mBtnHNBA= (Button) findViewById(R.id.btn_home_nba);//NBA
        mBtnHStock= (Button) findViewById(R.id.btn_home_stock);//股票
        mIVHUnfold= (ImageView) findViewById(R.id.iv_home_unfold);//下一页  获取控件
        mBtnHSociety.setOnClickListener(this);
        mBtnHMilitary.setOnClickListener(this);
        mBtnHFund.setOnClickListener(this);//基金
        mBtnHPhone.setOnClickListener(this);//手机
        mBtnHEPL.setOnClickListener(this);//英超
        mBtnHNBA.setOnClickListener(this);//NBA
        mBtnHStock.setOnClickListener(this);//股票
        mIVHUnfold.setOnClickListener(this);
    }

    /**
     * 获取新闻列表
     */
    public void getHttp(){
        Map<String,String> params=new HashMap<>();//网络地址 集合
        params.put("ver","0000000");//集合对象params增加地址组成
        params.put("subid","1");//分类号
        params.put("dir","1");//方向
        params.put("nid","1");//
        params.put("stamp","20140321000000");//
        params.put("cnt","20");//更新条目数
        MyHttp.get(activity, ServerUrl.NEWS_LIST, params, new OnResultFinishListener() {//请求网络
            @Override
            public void success(Response response) {//加载成功
                Gson gson=new Gson();
                NewsArray newsArray=gson.fromJson(response.result.toString(),NewsArray.class);
                if(newsArray.data!=null&&newsArray.data.size()>0){//有数据  进行添加  并且刷新
                    mXListAdapterNews.mList.addAll(newsArray.data);//修改数据
                    mXListAdapterNews.notifyDataSetChanged();//刷新数据列表
                }
                if (dir==1){
                    Date date=new Date();//本次刷新时间
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间样式
                    mXListViewNews.setRefreshTime(format.format(date));//刷新时间
                }
                mXListViewNews.stopLoadMore();//关闭上拉刷新
                mXListViewNews.stopRefresh();//关闭下拉加载

            }
            @Override
            public void failed(Response response) {
                Toast.makeText(activity,"加载失败",Toast.LENGTH_SHORT);
                mXListViewNews.stopLoadMore();//关闭上拉刷新
                mXListViewNews.stopRefresh();//关闭下拉加载
            }
        });

    }
    @Override
    public void onRefresh() {//进行下拉刷新
//        Toast.makeText(activity,"onRefresh",Toast.LENGTH_SHORT).show();
        dir=1;
        mXListAdapterNews.mList.clear();//清空之前的数据
        getHttp();
    }

    @Override
    public void onLoadMore() {//上拉加载
//        Toast.makeText(activity,"onLoadMore",Toast.LENGTH_SHORT).show();
        dir = 2;
        //拿到最后一条的id
        if (mXListAdapterNews.mList.size() > 0) {
            NewsInfo news = mXListAdapterNews.mList.get(mXListAdapterNews.mList.size()-1);//拿到当前的最后一条id
            nid = news.nid;//将拿到的nid给nid
        }
        //再调用获取新闻列表的方法
        getHttp();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(activity, NewsDetailsActivity.class);//用Intent传递News的对象news在新闻详情页面获取数据
        NewsInfo news= mXListAdapterNews.mList.get(position-1);//获取数据
        intent.putExtra("news",news);//传递数据
        startActivity(intent);
        AppInfo.news = news;//将新闻id保存在静态常量中
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home_society:
                Toast.makeText(activity,"社会===",Toast.LENGTH_SHORT).show();
                subid=1;
                //清空之前的数据
                mXListAdapterNews.mList.clear();
                //调用获取新闻列表的方法
                break;
            case R.id.btn_home_military:
                Toast.makeText(activity,"军事===",Toast.LENGTH_SHORT).show();
                subid=2;
                //清空之前的数据
                mXListAdapterNews.mList.clear();
                //调用获取新闻列表的方法
                break;
            case R.id.btn_home_stock:
                Toast.makeText(activity,"股票===",Toast.LENGTH_SHORT).show();
                subid=3;
                //清空之前的数据
                mXListAdapterNews.mList.clear();
                //调用获取新闻列表的方法
                break;
            case R.id.btn_home_fund:
                Toast.makeText(activity,"基金===",Toast.LENGTH_SHORT).show();
                subid=4;
                //清空之前的数据
                mXListAdapterNews.mList.clear();
                //调用获取新闻列表的方法
                break;
            case R.id.btn_home_epl:
                Toast.makeText(activity,"英超===",Toast.LENGTH_SHORT).show();
                subid=5;
                //清空之前的数据
                mXListAdapterNews.mList.clear();
                //调用获取新闻列表的方法
                break;
            case R.id.btn_home_phone:
                Toast.makeText(activity,"手机===",Toast.LENGTH_SHORT).show();
                subid=6;
                //清空之前的数据
                mXListAdapterNews.mList.clear();
                //调用获取新闻列表的方法
                break;
            case R.id.btn_home_nba:
                Toast.makeText(activity,"NAB===",Toast.LENGTH_SHORT).show();
                subid=7;
                //清空之前的数据
                mXListAdapterNews.mList.clear();
                //调用获取新闻列表的方法
                break;
            case R.id.iv_home_unfold:
                Toast.makeText(activity,"下一页===",Toast.LENGTH_SHORT).show();
                break;
        }
    }



    /**
     * news_sort?ver=版本号&imei=手机标识符
     * 新闻分类
     */
    public void getHttpDataNewsSort() {
        final Map<String, String> params = new HashMap<>();
        params.put("ver", "0000000");//版本号
        params.put("imei", "868564020877953");//用户标识符
        MyHttp.get(activity, ServerUrl.NEWS_LIST, params, new OnResultFinishListener() {//接口回调
                    @Override
                    public void success(Response response) {//成功
                        //解析  给适配器添加数据  刷新界面
                        Gson gson = new Gson();
                        NewsSortArray newsSortArray = gson.fromJson((String) response.result, NewsSortArray.class);
                    }

                    @Override
                    public void failed(Response response) {//失败
                        Toast.makeText(activity, "加载失败！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}


