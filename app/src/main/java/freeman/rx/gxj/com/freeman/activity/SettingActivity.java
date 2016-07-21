package freeman.rx.gxj.com.freeman.activity;

import freeman.rx.gxj.com.freeman.commutil.AppUtils;
import freeman.rx.gxj.com.freeman.parent.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * 设置
 * @author freeman
 */
public class SettingActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setting);

		initView();
	}

	protected void initView() {
		ToggleButton prompt__not_wifi = (ToggleButton) findViewById(R.id.prompt__not_wifi);
		TextView clear_cache = (TextView) findViewById(R.id.clear_cache);
		TextView feedback = (TextView) findViewById(R.id.feedback);
		TextView remmend_firend = (TextView) findViewById(R.id.remmend_firend);
		TextView assess = (TextView) findViewById(R.id.assess);
		logout = (Button) findViewById(R.id.logout);
		ToggleButton auto_play = (ToggleButton) findViewById(R.id.auto_play);


		prompt__not_wifi.setChecked(false);

		auto_play.setOnCheckedChangeListener(this);
		prompt__not_wifi.setOnCheckedChangeListener(this);
		clear_cache.setOnClickListener(this);
		feedback.setOnClickListener(this);
		remmend_firend.setOnClickListener(this);
		assess.setOnClickListener(this);
		logout.setOnClickListener(this);
	}



	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.clear_cache:
			// 清除缓存
			Toast.makeText(SettingActivity.this,"清除缓存",Toast.LENGTH_SHORT).show();
			break;
		case R.id.feedback:
			Toast.makeText(SettingActivity.this,"反馈意见",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(SettingActivity.this,SelectPictureActivity.class);
			startActivity(intent);
			//反馈意见
			break;
		case R.id.remmend_firend:
			// 推荐好友
			Toast.makeText(this, "推荐好友", Toast.LENGTH_SHORT).show();
			break;
		case R.id.assess:
			// 赏个好评
			Toast.makeText(this, "赏个好评", Toast.LENGTH_SHORT).show();
			AppUtils.goMarket(SettingActivity.this);
			break;
		case R.id.logout:


			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {


	}


	private Button logout;
}
