package freeman.rx.gxj.com.freeman.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import freeman.rx.gxj.com.freeman.commutil.ValidateCode;
import freeman.rx.gxj.com.freeman.parent.BaseActivity;


public class LoginActivity extends BaseActivity implements View.OnClickListener,TextWatcher {

    private ImageView code_image;//验证码图片
    private EditText code;//验证码
    private ValidateCode validateCode;
    private LinearLayout codeLayout;//验证码LinearLayout
    private Bitmap bitmap;//验证码图片
    private Button forgetPass;//忘记密码
    private Button login;//登录
    private Button regist;//注册
    private TextView wrongInfo;//错误提示信息
    private EditText phoneNum;
    private EditText password;
    private int wrongNum = 0;//输错次数，当输错次数大于3次时，显示验证码布局
    /**
     * 注册请求码
     */
    public static final int REQUST_REGIST = 100;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_login);
        validateCode = ValidateCode.getInstance();
        bitmap = validateCode.createBitmap();
        setUI();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("position", 0);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    protected void setUI() {
        code_image = getView(R.id.activity_login_code_image);
        code_image.setImageBitmap(bitmap);
        code_image.setOnClickListener(this);

        code = getView(R.id.activity_login_validate);
        codeLayout = getView(R.id.activity_login_validate_layout);

        forgetPass = getView(R.id.activity_login_forget_password);
        forgetPass.setOnClickListener(this);

        login = getView(R.id.activity_login_login_btn);
        login.setOnClickListener(this);

        regist = getView(R.id.activity_login_register_btn);
        regist.setOnClickListener(this);

        phoneNum = getView(R.id.activity_login_username);
        password = getView(R.id.activity_login_password);
        wrongInfo = getView(R.id.activity_login_wrong_info);

        code.addTextChangedListener(this);
        phoneNum.addTextChangedListener(this);
        password.addTextChangedListener(this);
    }


    /**
     * 判断验证码是否正确
     * @param code
     * @return
     */
    private boolean isCodeRight(String code){
        if(code.equalsIgnoreCase(validateCode.getCode())){
            return true;
        }
        return false;
    }

    /**
     * 设置验证码图片
     */
    private void setCodeImage() {
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap = validateCode.createBitmap();
        code_image.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterTextChanged(Editable s) {
		Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);//获取图片资源
	    drawable.setBounds(0, 0, 72, 72);
	    phoneNum.setError("请输入手机号", drawable);
		password.setError("请输入密码");
    }

}

