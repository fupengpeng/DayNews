package edu.feicui.daynews.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import edu.feicui.daynews.R;
import edu.feicui.daynews.ServerUrl;
import edu.feicui.daynews.activity.HomeActivity;
import edu.feicui.daynews.entity.AppInfo;
import edu.feicui.daynews.entity.EnrollArray;
import edu.feicui.daynews.net.MyHttp;
import edu.feicui.daynews.net.OnResultFinishListener;
import edu.feicui.daynews.net.Response;

/**
 * 注册
 * Created by Administrator on 16-10-12.
 */
public class EnrollFragment extends Fragment implements View.OnClickListener, TextWatcher {
    CheckBox mCBEnroll;//立即注册  同意条款选框
    TextInputLayout mTILEMail;//立即注册邮箱地址  布局
    TextInputEditText mTIETEMail;//立即注册邮箱地址  输入框
    TextInputLayout mTILEENikename;//立即注册昵称  布局
    TextInputEditText mTIETEENikename;//立即注册昵称  输入框
    TextInputLayout mTILEEPassword;//立即注册密码  布局
    TextInputEditText mTIETEEPassword;//立即注册密码  输入框
    Button mBtnEnrollEnroll;//立即注册
    View mView;
    HomeActivity homeActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enroll,container,false);
    }
    public View findViewById(int id){
        return mView.findViewById(id);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView=getView();//获取View
        mTILEMail= (TextInputLayout) findViewById(R.id.til_right_login_enroll_emill);//立即注册邮箱  布局  获取控件
        mTILEENikename= (TextInputLayout) findViewById(R.id.til_right_login_enroll_nickname);//立即注册昵称  布局  获取控件
        mTILEEPassword= (TextInputLayout) findViewById(R.id.til_right_login_enroll_password);//立即注册密码  布局  获取控件
        mTIETEMail= (TextInputEditText) findViewById(R.id.tiet_right_login_enroll_emill);//立即注册邮箱地址  输入框
        mTIETEENikename= (TextInputEditText) findViewById(R.id.tiet_right_login_enroll_nickname);//立即注册昵称  输入框
        mTIETEEPassword= (TextInputEditText) findViewById(R.id.tiet_right_login_enroll_password);//立即注册密码  输入框
        mTIETEMail.addTextChangedListener(this);
        mTIETEENikename.addTextChangedListener(this);
        mTIETEEPassword.addTextChangedListener(this);
        mCBEnroll= (CheckBox) findViewById(R.id.ch_right_login_enroll);
        mBtnEnrollEnroll= (Button) findViewById(R.id.btn_right_login_enroll_enroll);//立即注册  获取控件
        mBtnEnrollEnroll.setOnClickListener(this);//立即注册  监听
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeActivity= (HomeActivity) getActivity();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_right_login_enroll_enroll:
                Toast.makeText(homeActivity,"立即注册====",Toast.LENGTH_SHORT).show();
                if (mCBEnroll.isChecked()){
                    if (mTIETEMail.getText().toString().equals("")||
                            mTIETEENikename.getText().toString().equals("")||
                            mTIETEEPassword.getText().toString().equals("")){
                        //三个均不为空，有一个为空则不进行操作
                        Toast.makeText(homeActivity, "邮箱/密码/昵称不能为空，请输入", Toast.LENGTH_SHORT).show();
                        homeActivity.getLoginFragment(0);//通知HomeActivity 打开注册的碎片
                    }else {
                        Toast.makeText(homeActivity, "邮箱/密码/昵称格式输入正确，注册中，请稍后", Toast.LENGTH_SHORT).show();
                        getHttpDataEnroll();
                    }
                }else {
                    Toast.makeText(homeActivity, "请选择同意服务条款", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 立即注册各输入框输入字符格式判断
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (mTIETEMail.hasFocus()){//判断选定的输入框的焦点是否在此
            if (mTIETEMail.hasFocus()){//判断是否符合邮箱格式
                Pattern pattern=Pattern.compile
                        ("([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
                boolean b=pattern.matcher(s).matches();
                mTIETEMail.setError(null);//符合要求  继续输入密码
            }else {
                mTIETEMail.setError("邮箱格式不正确，请输入正确格式的邮箱");
            }
        }else if (mTIETEENikename.hasFocus()){//判断选定的输入框的焦点是否在此
            if (mTIETEENikename.length()>=6&&mTIETEENikename.length()<=24){//判断昵称是否符合6--24位的要求
                mTIETEENikename.setError(null);//符合要求  继续输入密码
            }else {
                mTIETEENikename.setError("昵称格式不正确，请输入正确格式的昵称");
            }
        }else if (mTIETEEPassword.hasFocus()){//判断选定的输入框的焦点是否在此
            Pattern pattern=Pattern.compile("[a-zA-Z0-9_]{6,24}");//使用正则确定密码格式
            //将输入字符串s与确定的密码格式所能产生的结果匹配是否符合 返回布尔值
            boolean b=pattern.matcher(s).matches();//Matcher m=pattern.matcher(s);  boolean b=m.matches();
            if (!b){
                mTIETEEPassword.setError("密码格式不正确，请重新输入");
            }else {
                mTIETEEPassword.setError(null);
            }
        }
    }
    /**
     * 注册
     * user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
     */
    public void getHttpDataEnroll(){
        Map<String, String> params = new HashMap<>();
        params.put("ver","0000000");//版本号
        params.put("uid",mTIETEENikename.getText().toString());//用户名
        params.put("email",mTIETEMail.getText().toString());//邮箱
        params.put("pwd",mTIETEEPassword.getText().toString());//密码
        MyHttp.get(homeActivity, ServerUrl.USER_ENROLL, params,
                new OnResultFinishListener() {
                    @Override
                    public void success(Response response) {//成功
                        Gson gson = new Gson();
                        EnrollArray enrollArray= gson.fromJson((String) response.result, EnrollArray.class);
                        AppInfo.enroll=enrollArray.data;
                        if (enrollArray.status==0){//判断是否正常响应
                            Toast.makeText(homeActivity,enrollArray.data.explain,Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void failed(Response response) {//失败
                        Toast.makeText(homeActivity,"注册失败",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
