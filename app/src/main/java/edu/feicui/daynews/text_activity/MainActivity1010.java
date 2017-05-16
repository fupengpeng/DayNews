package edu.feicui.daynews.text_activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.feicui.daynews.R;

/**
 * Created by Administrator on 16-10-17.
 */
public class MainActivity1010 extends Activity implements View.OnClickListener {
    static final int CHOOSE_PEO=200;//初始化请求码
    static final int PERMISSION_CAMERA=201;//初始化相机权限的请求码
    LinearLayout mllChoose;
    TextView mTvChoosePeople;//25-4.声明展示回传数据的TextView
    LinearLayout mllChooseCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1010);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mllChoose = (LinearLayout) findViewById(R.id.ll_test_go_1010);
        mTvChoosePeople= (TextView) findViewById(R.id.tv_test_choose_people);//25-5.获取展示回传的数据的控件
        mllChoose.setOnClickListener(this);//条目点击事件监听

        mllChooseCamera= (LinearLayout) findViewById(R.id.ll_test_camera_1010);
        mllChooseCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_test_go_1010:
                Intent intent=new Intent(this,ChooseActivity.class);//22.使用Intent跳转ChooseActivity
//                startActivity(intent);//22.跳转
                startActivityForResult(intent,CHOOSE_PEO);//22.跳转至intent  请求码  需要结果
                break;
            case R.id.ll_test_camera_1010:
                Intent intentCamera=new Intent();//22C.使用Intent跳转相机  需要权限
//                intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//22C-1.跳转相机意图
//                startActivityForResult(intentCamera,CHOOSE_PEO);//22.跳转至intent  请求码  需要结果
                //Builder
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前sdk版本是否大于等于23
                    if(checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){//判断是否拥有此权限
                        startActivityForResult(intentCamera,CHOOSE_PEO);//22.跳转至intent  请求码  需要结果
                    }else{//没有权限，获取权限
                        //请求权限，new String []{Manifest.permission.CAMERA} 请求权限的数组，可请求多个权限
                        //          PERMISSION_CAMERA  请求码
                        requestPermissions(new String []{Manifest.permission.CAMERA},PERMISSION_CAMERA);
                    }
                }else{
                    startActivityForResult(intentCamera,CHOOSE_PEO);//22.跳转至intent  请求码  需要结果
                }
                break;
        }
    }

    /**
     * 25.获取回传数据的结果
     * @param requestCode  请求码  区分返回结果的请求
     * @param resultCode  结果码  用来表示区分结果是否成功
     * @param data  回传的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){//25-1.区分请求码
            case CHOOSE_PEO://人员选择
                //25-2.判断是否请求成功
                if(resultCode==RESULT_OK){//成功，有数据
                    ArrayList<String> list=data.getStringArrayListExtra("data");//25-3.接收数据
                    StringBuffer buffer=new StringBuffer();//25-6.初始化一个字符串生成器
                    for (String name:list) {//25-7.遍历回传的数据list
                        buffer.append(name).append("   ");//25-8.拼接回传的数据
                        buffer.deleteCharAt(buffer.length()-1);//25-9.删除最后一个逗号
                        mTvChoosePeople.setText(buffer.toString());//25-10.在mTvChoosePeople中展示获取到的回传数据字符串
                    }

                }
                break;
        }
    }

    /**
     * 请求权限结果
     * @param requestCode  请求码
     * @param permissions  所请求的权限
     * @param grantResults  所请求权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){//根据请求码区分请求
            case PERMISSION_CAMERA://相机请求
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){//判断是否获取到权限  同意相机权限
                    Intent intentCamera=new Intent();//22C.使用Intent跳转相机  需要权限
                    startActivity(intentCamera);
                }else {//没有获取相机权限
                    Toast.makeText(this,"未获取相机权限，无法启动相机拍照",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
