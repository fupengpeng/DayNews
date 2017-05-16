package edu.feicui.daynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;

import edu.feicui.daynews.activity.BaseActivity;

/**
 * Created by Administrator on 16-10-8.
 */
public abstract class MyBaseAdapter<T> extends android.widget.BaseAdapter{
    Context mContext;
    public ArrayList<T> mList;
    LayoutInflater mInflate;
    int mLayoutId;
public MyBaseAdapter(Context mContext,ArrayList<T> mList,int layoutId){
    this.mContext=mContext;
    this.mList=mList;
    mLayoutId=layoutId;
    mInflate= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (this.mList==null){
        this.mList=new ArrayList<>();
    }
}
    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){
            convertView=mInflate.inflate(mLayoutId,null);
            holder=new Holder();
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        putView(holder,convertView,position,mList.get(position));
        return convertView;
    }
    public abstract void putView(Holder holder,View convertView,int position,T t);
    class Holder{

    }
}
