package freeman.rx.gxj.com.freeman.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import freeman.rx.gxj.com.freeman.activity.MainActivity;
import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.commutil.LogUtils;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

/**
 * Created by gxj on 2016/5/23.
 */
public class HomeFragment extends BaseFragment{

    public static final String TAG = "HomeFragment";
    private View rootView;
    private TextView textView;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initView(rootView);// 控件初始化
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity)activity;
    }
    private void initView(View view){

        textView = (TextView) view.findViewById(R.id.home);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.OpenRightMenu();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.Log(TAG, ">>>>onActivityCreated>>>>>");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.Log(TAG, ">>>>onStart>>>>>");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.Log(TAG, ">>>>onResume>>>>>");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.Log(TAG, ">>>>onPause>>>>>");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.Log(TAG, ">>>>onStop>>>>>");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        LogUtils.Log(TAG, ">>>>onHiddenChanged>>>>>" + hidden);
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.Log(TAG, ">>>>onDestroyView>>>>>");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.Log(TAG, ">>>>onDestroy>>>>>");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.Log(TAG, ">>>>onDetach>>>>>");
    }

}
