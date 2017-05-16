package edu.feicui.daynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.feicui.daynews.R;
import edu.feicui.daynews.entity.CommentListInfo;

/**
 * Created by Administrator on 16-10-10.
 */
public class CommentListAdapter extends MyBaseAdapter<CommentListInfo>{


    public CommentListAdapter(Context mContext, ArrayList<CommentListInfo> mList, int layoutId) {
        super(mContext, mList, layoutId);
    }

    @Override
    public void putView(Holder holder, View convertView, int position, CommentListInfo commentListInfo) {
        TextView nickname= (TextView) convertView.findViewById(R.id.tv_comment_nickname);//用户名称
        nickname.setText(commentListInfo.uid);
        TextView content= (TextView) convertView.findViewById(R.id.tv_comment_content);//评论内容
        content.setText(commentListInfo.content);
        TextView time= (TextView) convertView.findViewById(R.id.tv_comment_time);//评论时间
        time.setText(commentListInfo.stamp);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.iv_comment_image);
        Glide.with(mContext).load(commentListInfo.portrait).into(imageView);//给imageView加载图片

    }
}
