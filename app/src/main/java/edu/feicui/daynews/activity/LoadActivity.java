package edu.feicui.daynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import edu.feicui.daynews.R;

/**
 * Created by Administrator on 16-10-19.
 */
public class LoadActivity extends Activity {
    /**
     * logo图标
     */
    ImageView mIVLoadLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        mIVLoadLogo= (ImageView) findViewById(R.id.iv_load_logo);
        mIVLoadLogo.setImageResource(R.mipmap.logo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.logo_aphla_animation);
        mIVLoadLogo.startAnimation(animation);
        CountDownTimer timer=new CountDownTimer(3000,1000) {//三秒之后，进入主界面
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                Intent intent=new Intent();
                intent.setClass(LoadActivity.this,HomeActivity.class);
                intent.putExtra("int",2);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
