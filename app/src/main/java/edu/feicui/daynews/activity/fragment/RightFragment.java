package edu.feicui.daynews.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.feicui.daynews.R;
import edu.feicui.daynews.activity.HomeActivity;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.UserInfo;

/**
 * Created by Administrator on 16-10-9.
 */
public class RightFragment extends Fragment{
    HomeActivity homeActivity;//声明获取主页面的activity
    View mView;
    ImageView mIVRIsLogin;//登录状态右边用户头像展示
    TextView mTVRIsLogin;//登录装填右边用户昵称展示
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_right,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView=getView();//获取View
        mIVRIsLogin= (ImageView) findViewById(R.id.iv_right_info_register);
        mTVRIsLogin= (TextView) findViewById(R.id.tv_right_register);
        if(AppInfo.isLogin==true){
            mIVRIsLogin.setImageResource(R.mipmap.center_top_bg);
            Log.e("aaaa", "onViewCreated:------------------------ "+AppInfo.user.uid );
            mTVRIsLogin.setText(AppInfo.user.uid);
        }

    }
    public View findViewById(int id){
        return mView.findViewById(id);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeActivity= (HomeActivity) getActivity();
    }
}
