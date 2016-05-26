package freeman.rx.gxj.com.freeman.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.effect.Shimmer;
import freeman.rx.gxj.com.freeman.effect.ShimmerTextView;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

/**
 * Created by gxj on 2016/5/23.
 */
public class DiscoveryFragment extends BaseFragment{


    private View rootView;
    private ShimmerTextView shimmer_tv;
    private Shimmer shimmer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_discovery, null);
            initView(rootView);// 控件初始化
        }

        return rootView;
    }

    private void initView(View view){
        //shimmer = new Shimmer();//默认参数
        shimmer = new Shimmer(-1, 3000,500,1);
        shimmer_tv = (ShimmerTextView) view.findViewById(R.id.shimmer_tv);
        shimmer_tv.setReflectionColor(Color.BLUE);//设置闪烁颜色

        shimmer.start(shimmer_tv);
        //shimmer.cancel();//取消闪烁

    }
    
}
