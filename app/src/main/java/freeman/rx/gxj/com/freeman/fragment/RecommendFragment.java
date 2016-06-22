package freeman.rx.gxj.com.freeman.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.commutil.LogUtils;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

/**
 * Created by gxj on 2016/5/23.
 */
public class RecommendFragment extends BaseFragment{

    public static final String TAG = "RecommendFragment";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_recommend, null);
            initView(rootView);// 控件初始化
        }

        return rootView;
    }

    private void initView(View view){

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        LogUtils.Log(TAG, ">>>>onHiddenChanged>>>>>" + hidden);
        super.onHiddenChanged(hidden);
    }
}
