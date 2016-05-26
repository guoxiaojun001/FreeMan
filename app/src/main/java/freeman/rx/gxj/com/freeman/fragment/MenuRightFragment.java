package freeman.rx.gxj.com.freeman.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

public class MenuRightFragment extends BaseFragment {

	private View rootView;
	ImageView talk;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (null != rootView) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (null != parent) {
				parent.removeView(rootView);
			}
		} else {
			rootView = inflater.inflate(R.layout.layout_menu_right, null);
			setUI(rootView);// 控件初始化
		}

		return rootView;
	}


	private void setUI(View rootView) {
		talk = (ImageView) rootView.findViewById(R.id.talk);
		talk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mActivity,"",Toast.LENGTH_SHORT).show();
			}
		});
	}
}
