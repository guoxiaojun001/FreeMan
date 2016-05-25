package freeman.rx.gxj.com.freeman.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

/**
 * Created by gxj on 2016/5/23.
 */
public class PersonalFragment extends BaseFragment{

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
            rootView = inflater.inflate(R.layout.fragment_personal, null);
            initView(rootView);// 控件初始化
        }

        return rootView;
    }

    private void initView(View view){

    }
    
}
