package edu.feicui.daynews.text_activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import edu.feicui.daynews.R;
import edu.feicui.daynews.activity.BaseActivity;

/**
 * Created by Administrator on 16-10-10.
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener {
    ImageView mImageView;
    LinearLayout mLLCamera;
    PopupWindow popupWindow;
    View mView;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mView=getLayoutInflater().inflate(R.layout.view,null);
        mLLCamera= (LinearLayout) findViewById(R.id.ll_camera);
        mLLCamera.setOnClickListener(this);
        mImageView= (ImageView) findViewById(R.id.iv_photo);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  1.获取PopupWindow对象
                popupWindow=new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //  2.设置外部可以点击
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                //  3.进行展示(基于屏幕展示)
                popupWindow.showAsDropDown(mImageView,0,0);
            }
        });
    }



    @Override
    public void onClick(View v) {
        Toast.makeText(this,"相机",Toast.LENGTH_SHORT).show();
        popupWindow.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"遮挡",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initView() {

    }
}
