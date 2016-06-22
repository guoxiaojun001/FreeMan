package freeman.rx.gxj.com.freeman.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.adapter.MyRecyclerAdapter;
import freeman.rx.gxj.com.freeman.adapter.RecycleViewDivider;
import freeman.rx.gxj.com.freeman.commutil.LogUtils;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

/**
 * Created by gxj on 2016/5/23.
 */
public class PersonalFragment extends BaseFragment{

    public static final String TAG = "PersonalFragment";
    private View rootView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

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

        recyclerView= (RecyclerView) view.findViewById(R.id.rv_behavior);

        //设置固定大小
        recyclerView.setHasFixedSize(true);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(mActivity);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);

        //设置分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.VERTICAL));

//        recyclerView.addItemDecoration(new RecycleViewDivider( mActivity,
//                LinearLayoutManager.VERTICAL,5,getResources().getColor(R.color.devide_line)));

//        recyclerView.addItemDecoration(new RecycleViewDivider(
//                mActivity, LinearLayoutManager.VERTICAL, R.mipmap.ic_split));

        mAdapter = new MyRecyclerAdapter(mActivity);

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        LogUtils.Log(TAG, ">>>>onHiddenChanged>>>>>" + hidden);
        super.onHiddenChanged(hidden);
    }
    
}
