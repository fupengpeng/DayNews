package edu.feicui.daynews.net;

import android.content.Context;

import java.util.Map;

/**
 * 网络请求类
 * Created by Administrator on 2016/9/22.
 */
public class MyHttp {
    public static void get(Context context,String url, Map<String,String> prams,
                           OnResultFinishListener mListener){
         //进行网络请求
        Request request=new Request();
        request.params=prams;
        request.type=Constants.GET;
        request.url=url+Utils.getUrl(prams,Constants.GET);
//        url   拼接好的       type get    params--->Map参数
       //请求
        NetAsync async=new NetAsync(context,mListener);
        async.execute(request);
    }

    public static void post(Context context,String url, Map<String,String> prams,
                            OnResultFinishListener mListener){
        Request request=new Request();
        request.params=prams;
        request.type=Constants.POST;
        request.url=url;
        NetAsync async=new NetAsync(context,mListener);
        async.execute(request);

    }
}
