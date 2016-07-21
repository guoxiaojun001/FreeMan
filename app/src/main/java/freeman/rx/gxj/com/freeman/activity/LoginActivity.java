package freeman.rx.gxj.com.freeman.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import freeman.rx.gxj.com.freeman.commutil.RegexUtil;
import freeman.rx.gxj.com.freeman.parent.BaseActivity;
import freeman.rx.gxj.com.freeman.view.MyEditText;


public class LoginActivity extends BaseActivity implements OnClickListener {
    private Button btn_login;
    private MyEditText et_account, et_pwd;
    private String account = null,
            account_bak = null,
            password = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    public void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);//登录键
        et_account = (MyEditText) findViewById(R.id.et_account);//账号
        et_pwd = (MyEditText) findViewById(R.id.et_pwd);//密码

        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            default:
                break;
        }
    }

    private void doLogin() {
        account = et_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            return;
        }
        if (!RegexUtil.checkMobile(account)) {
            return;
        }
        password = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        if (password.length() < 6 || password.length() > 16) {
            return;
        }

    }


}
