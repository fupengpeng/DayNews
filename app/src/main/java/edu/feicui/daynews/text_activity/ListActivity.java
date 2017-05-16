package edu.feicui.daynews.text_activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import edu.feicui.daynews.R;
import edu.feicui.daynews.activity.BaseActivity;

/**
 * Created by Administrator on 16-10-26.
 */
public class ListActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        FragmentManager fm=getSupportFragmentManager();//1.获取Fragment管理器
        FragmentTransaction transaction=fm.beginTransaction();//2.开启事物
        /*
        1.获取Fragment管理器
        2.开启事物
        3.给事物控件装Fragment---->需要一个Fragment   初始化一个Fragment
        4.提交事物

         */
        /*
        1.声明ListView
        2.获取ListView--->getView（）。声明mView，getView（），findViewById方法获取ListView
        3.适配器
         */
    }
}
