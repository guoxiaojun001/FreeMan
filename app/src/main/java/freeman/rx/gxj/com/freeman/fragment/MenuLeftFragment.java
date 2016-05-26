package freeman.rx.gxj.com.freeman.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.activity.SettingActivity;
import freeman.rx.gxj.com.freeman.commutil.CircleImageLoader;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

public class MenuLeftFragment extends BaseFragment implements View.OnClickListener{

	String url = "http://img1.imgtn.bdimg.com/it/u=2215239751,3809037166&fm=21&gp=0.jpg";
	private View rootView;
	private ImageView headImage;

	private RelativeLayout ge_setting;//设置
	private RelativeLayout me;//我的头像

	private Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)	{

		if (null != rootView) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (null != parent) {
				parent.removeView(rootView);
			}
		} else {
			rootView = inflater.inflate(R.layout.layout_menu_left, null);
			setUI(rootView);// 控件初始化
		}

		return rootView;
	}

	private void setUI(View rootView) {

		headImage = (ImageView) rootView.findViewById(R.id.head_image);
		CircleImageLoader.loadHeadImage(url,headImage);
		ge_setting = (RelativeLayout) rootView.findViewById(R.id.go_setting);
		ge_setting.setOnClickListener(this);

		me = (RelativeLayout) rootView.findViewById(R.id.me);
		me.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.go_setting :
				intent = new Intent(mActivity, SettingActivity.class);
				startActivity(intent);
				break;

			case R.id.me :
				Toast.makeText(mActivity,"qqqqqq",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}

	}

}
