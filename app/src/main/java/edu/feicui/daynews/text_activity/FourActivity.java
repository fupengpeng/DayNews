package edu.feicui.daynews.text_activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Administrator on 16-10-18.
 */
public class FourActivity extends Activity{
    String[] data={"1","2","3","4","5"};
    String TAG;
    int a;
    int b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn=new Button(this);
        setContentView(btn);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        testDebug();
    }

    public void testDebug(){
        for (int i = 0; i < data.length; i++) {
            String str=data[i];
            Log.e(TAG, "testDebug: "+str );
            a++;
            test();
        }
    }
    public void test(){
        b++;
    }
}
