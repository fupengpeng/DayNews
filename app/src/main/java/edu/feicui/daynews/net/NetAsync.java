package edu.feicui.daynews.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/9/22.
 */
public class NetAsync extends AsyncTask<Request,Object,Response> {
    ProgressDialog mDialog;

    OnResultFinishListener mListener;
    public NetAsync(Context context,OnResultFinishListener mListener){//构造方法传值
        mDialog=ProgressDialog.show(context,"","加载中...");//初始化加载框
        this.mListener=mListener;
    }

    @Override
    protected Response doInBackground(Request... params) {
        Request request=params[0];//***TODO
        Response response=new Response();//实例化响应信息
        response.result="";//***TODO
        HttpURLConnection httpConnection=null;
        try {
            URL url=new URL(request.url);//实例化URL
            httpConnection = (HttpURLConnection) url.openConnection();//打开连接
            httpConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);//设置超时的时间量
            httpConnection.setReadTimeout(Constants.READ_TIMEOUT);//设置读取超时的时间量
            if(request.type==Constants.GET){ //判断是何种方式的请求数据
                httpConnection.setRequestMethod("GET");
            }else{
                httpConnection.setRequestMethod("POST");
                httpConnection.setDoOutput(true);
                OutputStream ou=httpConnection.getOutputStream();
                ou.write(Utils.getUrl(request.params,Constants.POST).getBytes());
            }

            int code=httpConnection.getResponseCode();
            response.code=code;
            if(code==HttpURLConnection.HTTP_OK){
                InputStream in=httpConnection.getInputStream();
                byte[] bytes=new byte[1024];
                int len;
                StringBuffer buff=new StringBuffer();
                while ((len=in.read(bytes))!=-1){
                    buff.append(new String(bytes,0,len));
                }
                //拿到了结果
                response.result=buff.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpConnection!=null){
                httpConnection.disconnect();
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response o) {
        super.onPostExecute(o);
        //拿到结果
        mDialog.dismiss();
        Response response= o;
        if(o.code!=HttpURLConnection.HTTP_OK){ //失败
            mListener.failed(response);
        }else{ //成功

           mListener.success(response);
        }
    }
}

