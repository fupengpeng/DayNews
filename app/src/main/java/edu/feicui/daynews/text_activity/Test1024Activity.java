package edu.feicui.daynews.text_activity;

import android.app.Activity;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import javax.security.auth.login.LoginException;

import edu.feicui.daynews.R;

/**
 * Created by Administrator on 16-10-24.
 */
public class Test1024Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1024_provider);
//        Uri uri=Uri.parse("content://edu.feicui.daynews.provider.Test1024Provider/stu");//访问者
//        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);//提供者
//        uriMatcher.addURI("edu.feicui.daynews.provider.Test1024Provider","stu",23);
//        int code=uriMatcher.match(uri);
//        Log.e("aaaa", "onCreate: code=="+code);
    }
}
