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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import edu.feicui.daynews.R;
import edu.feicui.daynews.ServerUrl;
import edu.feicui.daynews.activity.HomeActivity;
import edu.feicui.daynews.entity.FindPasswordArray;
import edu.feicui.daynews.net.MyHttp;
import edu.feicui.daynews.net.OnResultFinishListener;
import edu.feicui.daynews.net.Response;

/**
 * Created by Administrator on 16-10-12.
 */
public class ForgetPasswordFragment extends Fragment implements TextWatcher, View.OnClickListener {
    TextInputLayout mTILForgetPassword;//忘记密码邮箱地址  布局
    TextInputEditText mTIETForgetPassword;//忘记密码邮箱地址  输入框
    Button mBtnPasswordAffirm;
    View mView;
    HomeActivity homeActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password,container,false);
    }
    public View findViewById(int id){
        return mView.findViewById(id);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeActivity= (HomeActivity) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView=getView();
        mTILForgetPassword= (TextInputLayout) findViewById(R.id.til_right_login_forget_password_email);//忘记密码 输入邮箱修改密码
        mTIETForgetPassword= (TextInputEditText) findViewById(R.id.tiet_right_login_forget_password_email);//
        mTIETForgetPassword.addTextChangedListener(this);
        mBtnPasswordAffirm= (Button) findViewById(R.id.btn_right_login_password_affirm);
        mBtnPasswordAffirm.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
            if (mTIETForgetPassword.hasFocus()){//判断是否符合邮箱格式
                Pattern pattern=Pattern.compile
                        ("([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
                boolean b=pattern.matcher(s).matches();
                mTIETForgetPassword.setError(null);//符合要求  继续输入密码
            }else {
                mTIETForgetPassword.setError("邮箱格式不正确，请输入正确格式的邮箱");
            }

    }

    @Override
    public void onClick(View v) {
        if (mTIETForgetPassword.getText().toString().equals("")){//判断  邮箱是否为空
            Toast.makeText(homeActivity, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            homeActivity.getLoginFragment(2);//通知HomeActivity  打开找回密码的碎片(在此界面的效果是 只弹出吐丝窗口)
        }else{//不为空  则调用方法
            getHttpDataSendEmail();//调用发送邮箱的方法
        }
    }
    /**
     * 忘记密码 发送邮箱
     * user_forgetpass?ver=版本号&email=邮箱
     */
    public void getHttpDataSendEmail(){
        Map<String,String> pamars=new HashMap<>();
        pamars.put("ver","0000000");//版本号
        pamars.put("email",mTIETForgetPassword.getText().toString());//邮箱
        MyHttp.get(homeActivity, ServerUrl.USER_FORGETPASSWORD, pamars,
                new OnResultFinishListener() {
                    @Override
                    public void success(Response response) {//发送成功
                        Gson gson=new Gson();
                        FindPasswordArray findPasswordArray=gson.fromJson((String) response.result,FindPasswordArray.class);
                        Toast.makeText(homeActivity,findPasswordArray.data.explain,Toast.LENGTH_SHORT).show();
                        //切换Fragment
                    }
                    @Override
                    public void failed(Response response) {//发送失败
                        Toast.makeText(homeActivity,"发送失败",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
