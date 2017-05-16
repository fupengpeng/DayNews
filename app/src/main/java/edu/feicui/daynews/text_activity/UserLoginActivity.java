package edu.feicui.daynews.text_activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import edu.feicui.daynews.R;
import edu.feicui.daynews.activity.BaseActivity;

/**
 * Created by Administrator on 16-10-8.
 */
public abstract class UserLoginActivity extends BaseActivity implements TextWatcher {
    TextInputLayout mTextInputLayout;
    TextInputEditText mTextInputEditTextAccount;
    TextInputEditText mTextInputEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
//        mTextInputLayout= (TextInputLayout) findViewById(R.id.til_login);
        mTextInputEditTextAccount= (TextInputEditText) findViewById(R.id.tiet_login_account);
        mTextInputEditTextPassword= (TextInputEditText) findViewById(R.id.tiet_login_password);
        mTextInputEditTextAccount.addTextChangedListener(this);
//        限制输入框的字符串长度
        mTextInputEditTextPassword.addTextChangedListener(this);//文本变化的监听  实现TextWatcher接口

//        mTextInputEditTextAccount.getText().toString();//获取输入的信息
    }

    /**
     * 文本改变前
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.e("aaaaa", "beforeTextChanged: s"+s+"    star=="+start+"---count=="+count+"---after=="+after);
    }

    /**
     * 文本改变时
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("aaaaa", "onTextChanged: s"+s+"    star=="+start+"---count=="+count);
    }

    /**
     * 文本改编后
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        Log.e("aaaaa", "afterTextChanged: s=="+s);
        if(mTextInputEditTextPassword.length()>3){
            mTextInputEditTextPassword.setError(null);
        }else {
            mTextInputEditTextPassword.setError("输入错误，必须大于三位");
        }
    }
}
