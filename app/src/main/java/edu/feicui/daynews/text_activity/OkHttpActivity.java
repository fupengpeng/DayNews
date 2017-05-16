package edu.feicui.daynews.text_activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import edu.feicui.daynews.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OkHttp 网络请求
 * Created by Administrator on 16-10-17.
 */
public class OkHttpActivity extends Activity{
    /*
    OkHttp  网络请求框架
        1.实例化OkHttpclient对象
        2.新建一个请求
        3.加入请求
        4.执行请求

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        get();
    }
    public void get(){
        //1.实例化OkHttpclient对象
        OkHttpClient client=new OkHttpClient.Builder()//实例化子类Builder的对象
            .connectTimeout(3000, TimeUnit.MILLISECONDS)//返回值是Builder，是Builder的方法
            .build();//真正创建Builder对象
        //2.新建一个请求
        Request request=new Request.Builder()
                .url("http://www.wycode.cn/api/movie/getMovies?size=10")
                .get()
                .build();//
        //3.加入请求
        Call call=client.newCall(request);//新建请求
        //4.执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {//失败
                Log.e("aaaa", "onFailure: 失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {//成功
                ResponseBody body=response.body();//返回结果体
                String str=body.string();
//                body.byteStream();//使用流读取成想要的。。。
                Log.e("aaaa", "onResponse: 成功"+str);
            }
        });
    }
    public void post(){
        //1.实例化OkHttpclient对象
        OkHttpClient client=new OkHttpClient.Builder()//实例化子类Builder的对象
                .connectTimeout(3000, TimeUnit.MILLISECONDS)//返回值是Builder，是Builder的方法
                .build();//真正创建Builder对象
        //2.新建一个请求
        FormBody body=new FormBody.Builder()
                .add("","")//参数名，参数
                .build();
        Request req=new Request.Builder()
                .url("")
                .post(body)
                .build();
        Call call=client.newCall(req);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body=response.body();
                String str=body.string();
            }
        });
    }
}
