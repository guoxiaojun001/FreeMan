package freeman.rx.gxj.com.freeman.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import freeman.rx.gxj.com.freeman.activity.MainActivity;
import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.commutil.LogUtils;
import freeman.rx.gxj.com.freeman.menu.ExpandableItem;
import freeman.rx.gxj.com.freeman.menu.ExpandableSelector;
import freeman.rx.gxj.com.freeman.menu.OnExpandableItemClickListener;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

/**
 * Created by gxj on 2016/5/23.
 */
public class HomeFragment extends BaseFragment{

    // 资讯标识常量 Start
    public static final int TYPE_NEWS = 1;// 业界资讯标识
    public static final int TYPE_MOBILE = 2;// 移动资讯标识
    public static final int TYPE_CLOUD = 3;// 云计算资讯标识
    public static final int TYPE_SD = 4;// 软件研发资讯标识
    public static final int TYPE_PROGRAMMER = 5;// 程序员资讯标识
    public static final String TAG = "HomeFragment";
    private View rootView;
    private TextView textView;
    MainActivity mainActivity;

    ExpandableSelector es_menu;//菜单

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
            initializeSizesExpandableSelector(rootView);
        }

        return rootView;
    }

    @Override
    public void onAttach(Context activity) {
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


    private void initializeSizesExpandableSelector(View view) {
        es_menu = (ExpandableSelector) view.findViewById(R.id.es_menu);
        List<ExpandableItem> expandableItems = new ArrayList<>();
        expandableItems.add(new ExpandableItem("业界", TYPE_NEWS));
        expandableItems.add(new ExpandableItem("移动", TYPE_MOBILE));
        expandableItems.add(new ExpandableItem("云", TYPE_CLOUD));
        expandableItems.add(new ExpandableItem("软件", TYPE_SD));
        es_menu.showExpandableItems(expandableItems);
        es_menu.setOnExpandableItemClickListener(new OnExpandableItemClickListener() {
            @Override
            public void onExpandableItemClickListener(int index, View view) {
                final ExpandableItem item = es_menu.getExpandableItem(index);
                swipeFirstItem(index, item);
                if(index!=0){
                    new Handler().postDelayed(new Runnable() {//延时300ms执行，否则会卡顿
                        @Override
                        public void run() {


                        }
                    },300);
                }
                es_menu.collapse();
            }

            private void swipeFirstItem(int position, ExpandableItem clickedItem) {
                ExpandableItem firstItem = es_menu.getExpandableItem(0);
                es_menu.updateExpandableItem(0, clickedItem);
                es_menu.updateExpandableItem(position, firstItem);
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
        LogUtils.Log(TAG, ">>>>onHiddenChanged>>>>>" + hidden);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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
