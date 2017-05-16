package edu.feicui.daynews.text_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.feicui.daynews.test_adapter.ChooseAdapter;


/**
 * Created by Administrator on 16-10-17.
 */
public class ChooseActivity extends Activity implements ChooseAdapter.OnItemClistener, View.OnClickListener {
    ChooseAdapter adapter;
    RecyclerView mRecyclerView;
    public static boolean[] mChooseArray;//9.数组
    Button mBtnOk;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout lin=new LinearLayout(this);//23.按钮点击确定返回选择结果//23-1.新建一个LinearLayout布局
        lin.setOrientation(LinearLayout.VERTICAL);//23-4.设置LinearLayout布局的方向
        mRecyclerView=new RecyclerView(this);//初始化RecyclerView
        mBtnOk =new Button(this);
        mBtnOk.setText("确认");//23-5.设置按钮mBtnOk的显示字
        lin.addView(mRecyclerView);//23-2.将RecyclerView控件增加到LinearLayout布局
        lin.addView(mBtnOk);//23-3.将Button控件增加到LinearLayout布局
        setContentView(lin);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //8-1.数据源
        list=new ArrayList<>();
        list.add("zhangsan");
        list.add("lisi");
        list.add("san");
        list.add("zhang");
        list.add("hsan");
        mChooseArray =new boolean[list.size()];//10.默认状态
        //8-2.布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //8-3.适配器
        adapter= new ChooseAdapter(list,this);
        //8-4.绑定适配器
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemclickListener(this);//21.绑定子条目点击事件
        mBtnOk.setOnClickListener(this);//23-6.按钮点击事件

    }

    /**
     *
     * @param holder  所点击的View
     * @param position  所点击的position条
     */
    @Override
    public void onItemClick(ChooseAdapter.MyHolder holder, int position) {
        //21-1修改数组内容
        mChooseArray[position]=!mChooseArray[position];//21-1.点击position个view后，给position个选定重新赋值
        adapter.notifyItemChanged(position);//21-2.刷新适配器   所点击的position个
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> resultData=new ArrayList<>();//24-3.初始化一个数组，装下标选定的数据
        for (int i = 0; i < mChooseArray.length; i++) {//24-1.遍历所有的数据
            if (mChooseArray[i]){//24-2.判断看那个下标的数据选定
                resultData.add(list.get(i));//24-4.将选定的数据装入数组
            }
        }
        //24.确定选择   需要将选中数据传递给上一级Activity  方法1.回调  方法2.下方示范
        Intent intent=new Intent();//24-5.初始化一个Intent对象，通过Intent对象传递返回的数据
        intent.putExtra("data",resultData);//24-6.将选定的数据放入到Intent对象中
        //24-7.使用setResult将结果码成功的Intent对象返回
        setResult(RESULT_OK,intent);//从谁那儿跳转的就回传给谁  参数1：resultCode  结果码（区分是谁的结果0）区分此次操作是否成功  参数2：Intent
        finish();//24-8.关闭此Activity
    }
}
