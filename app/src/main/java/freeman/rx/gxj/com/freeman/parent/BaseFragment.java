package freeman.rx.gxj.com.freeman.parent;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.commutil.LogUtils;

/**
 * Created by gxj on 2016/5/23.
 */
public class BaseFragment<T extends Activity> extends Fragment {
    public static final String Tag = "BaseFragment";

    protected T mActivity = null ;
    private int screenHeight;
    private int screenWidth;
    protected DisplayImageOptions options;
    protected LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//如果只是简单显示圆形，直接用下面这句话就可以
                //.displayer(new RoundedBitmapDisplayer(DensityUtil.dip2px(this, 50)))
                .build();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (T) getActivity();
        DisplayMetrics outMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenHeight = outMetrics.heightPixels;
        screenWidth = outMetrics.widthPixels;

        inflater = (LayoutInflater)
                mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (mActivity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mActivity.getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(mActivity.getCurrentFocus().
                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 弹出输入法窗口
     */
    public void showSoftInputView(final EditText et) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) et.getContext().getSystemService(Service.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }

}
