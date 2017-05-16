package edu.feicui.daynews.test_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.daynews.R;
import edu.feicui.daynews.text_activity.ChooseActivity;

/**
 * Created by Administrator on 16-10-17.
 */
public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.MyHolder>{
    ArrayList<String> mList;
    Context mContext;
    LayoutInflater mInflater;//转子条目  否则不能获取到子条目
    public ChooseAdapter(ArrayList<String> mList, Context mContext){//构造方法   传数据进来
        this.mContext=mContext;//将传进来的数据赋值给成员变量
        this.mList=mList;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {//1.创建ViewHolder之前创建一个子条目View
        View view=mInflater.inflate(R.layout.item_choose,parent,false);//3.加载子条目View
        return new MyHolder(view);//4.创建一个ViewHolder
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.mTvName.setText(mList.get(position));//7.通过holder渲染
        //11.选择状态（图片选择器）（防止乱序）
        holder.mIvChoose.setSelected(ChooseActivity.mChooseArray[position]);//12.点击position条目改变选择状态
        holder.itemView.setOnClickListener(new MyClickClass(holder,position));//19.给holder绑定点击事件，将需要的holder和position传递进来
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{//RecyclerView.Adapter的泛型
        TextView mTvName;//5.声明子条目控件
        ImageView mIvChoose;
        View itemView;//13.声明子条目View
        public MyHolder(View itemView) {//构造方法  传数据
            super(itemView);
            this.itemView=itemView;//将传过来的数据赋值给子条目View
            mTvName= (TextView) itemView.findViewById(R.id.tv_test_name);//6.通过itemView获取子条目控件
            mIvChoose = (ImageView) itemView.findViewById(R.id.iv_test_choose);
        }
    }
    public class MyClickClass implements View.OnClickListener{//14.通过内部类实现点击事件
        MyHolder holder;//16.声明接收的成员变量
        int position;
        public MyClickClass(MyHolder holder,int position){//17.构造方法，将参数holder，position传进来
            this.holder=holder;
            this.position=position;//18.接收传递过来的数据
        }
        @Override
        public void onClick(View v) {//15.重写onClick方法
            mOnItemClistener.onItemClick(holder,position);//20.响应点击事件，使用mOnItemClistener将holder，position弄出去

        }
    }
    public void setOnItemclickListener(OnItemClistener mOnItemclickListener){//20-4.
        this.mOnItemClistener=mOnItemclickListener;
    }
    OnItemClistener mOnItemClistener;//20-3.声明回调接口
    public interface  OnItemClistener{//20-1.写回调接口
        void onItemClick(MyHolder holder,int position);//20-2.提供相应方法
    }
}
