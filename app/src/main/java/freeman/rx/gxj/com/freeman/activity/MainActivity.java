package freeman.rx.gxj.com.freeman.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import freeman.rx.gxj.com.freeman.fragment.DiscoveryFragment;
import freeman.rx.gxj.com.freeman.fragment.HomeFragment;
import freeman.rx.gxj.com.freeman.fragment.PersonalFragment;
import freeman.rx.gxj.com.freeman.fragment.SettingFragment;
import freeman.rx.gxj.com.freeman.parent.BaseActivity;
import freeman.rx.gxj.com.freeman.tab.FragmentTabHost;
import freeman.rx.gxj.com.freeman.tab.Tab;


public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private ArrayList<Tab> mTabs = new ArrayList<Tab>(4);
    private Tab[] tabs;

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        preferencepUtils.setFirstUse(true);
        initView();
    }

    private void initView() {

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);

        Tab num1 = new Tab(R.string.app_home, R.drawable.selector_icon_num1, HomeFragment.class);
        Tab num2 = new Tab(R.string.app_discovery, R.drawable.selector_icon_num2, DiscoveryFragment.class);
        Tab num3 = new Tab(R.string.app_setting, R.drawable.selector_icon_num3, SettingFragment.class);
        Tab num4 = new Tab(R.string.app_personal, R.drawable.selector_icon_num4, PersonalFragment.class);

        mTabs.add(num1);
        mTabs.add(num2);
        mTabs.add(num4);
        mTabs.add(num3);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
            }
        });

        //去掉纵向分割线 SHOW_DIVIDER_NONE
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        mTabHost.setCurrentTab(0);

        initDrawerLayout();
    }

    public void OpenRightMenu()  {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.RIGHT);
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {   }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)  {
                View mContent = mDrawerLayout.getChildAt(3);//此处是确定 哪个子view需要位移效果
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else  {
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView){  }

            @Override
            public void onDrawerClosed(View drawerView)  {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }

}
