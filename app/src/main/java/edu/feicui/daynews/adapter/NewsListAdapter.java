package edu.feicui.daynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.feicui.daynews.R;
import edu.feicui.daynews.entity.NewsInfo;

/**
 * Created by Administrator on 16-10-10.
 */
public class NewsListAdapter extends MyBaseAdapter<NewsInfo>{

    public NewsListAdapter(Context mContext, ArrayList<NewsInfo> mList, int layoutId) {
        super(mContext, mList, layoutId);
    }

    @Override
    public void putView(Holder holder, View convertView, int position, NewsInfo news) {
        TextView tittle= (TextView) convertView.findViewById(R.id.tv_item_news_title);
        tittle.setText(news.title);
        TextView details= (TextView) convertView.findViewById(R.id.tv_item_news_details);
        details.setText(news.summary);
        TextView time= (TextView) convertView.findViewById(R.id.tv_item_news_time);
        time.setText(news.stamp);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.iv_item_news_pic);
        Glide.with(mContext).//初始化一个Glide对象
                load(news.icon).//加载String型图片链接
                into(imageView);//给imageView加载图片
    }


}
