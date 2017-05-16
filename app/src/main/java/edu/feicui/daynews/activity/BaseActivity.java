package edu.feicui.daynews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.feicui.daynews.R;

/**
 * Created by Administrator on 16-10-8.
 */
public abstract class BaseActivity extends FragmentActivity {
    private static final String LogWapper = null;
    //获取当前Activity的类名 getSimpleName() getName();
    public  String TAG=this.getClass().getSimpleName();
    LinearLayout mLLContent;//内容
    LayoutInflater mInflater;
    ImageView mImgLeft;
    TextView mTvTittle;
    ImageView mImgRight;
    /**
     * 1.base的onCreate 加载了布局base_layout
     *
     * 2.将传入的布局加载到
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout);
        mLLContent = (LinearLayout) findViewById(R.id.ll_base_content);
        mImgLeft=(ImageView) findViewById(R.id.iv_base_left);
        mImgRight=(ImageView) findViewById(R.id.iv_base_right);
        mTvTittle=(TextView) findViewById(R.id.tv_base_tittle);
        mInflater=getLayoutInflater();




    }

    /**
     * 模板设计  在加载布局之后 自动调用 initView();
     */
    public void setContentView(int id){
        //将子Activity  传入的布局加载到base_layout的Context
        mInflater.inflate(id, mLLContent);//将id的布局加载到mRelContent
        initView();
    }
    /**
     * 用于子类初始化工作
     */
    protected abstract void initView();
    /**
     *
     * @param listener   绑定的监听器
     * @param views    所要绑定点击事件的View
     */
    protected void setOnClickListeners(View.OnClickListener listener, View...views){
        //给每一个View绑定点击事件
        if(listener==null){//判断listener是否为空
            return;
        }
        for(View view:views){
            view.setOnClickListener(listener);
        }
    }
    /**
     *
     * @param listener   绑定的监听器
     * @param ids    所要绑定点击事件的View的id
     */
    protected void setOnClickListeners(View.OnClickListener listener, int...ids){
        //给每一个View绑定点击事件
        if(listener==null){//判断listener是否为空
            return;
        }
        for(int id:ids){
            findViewById(id).setOnClickListener(listener);
        }
    }


}
