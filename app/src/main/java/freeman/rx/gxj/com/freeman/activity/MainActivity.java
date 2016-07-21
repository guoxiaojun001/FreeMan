package freeman.rx.gxj.com.freeman.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;
import freeman.rx.gxj.com.freeman.commutil.CircleImageLoader;
import freeman.rx.gxj.com.freeman.drag.CustomDragActivity;
import freeman.rx.gxj.com.freeman.drag.DynamicDragActivity;
import freeman.rx.gxj.com.freeman.drag.GridDragActivity;
import freeman.rx.gxj.com.freeman.drag.LinearDragActivity;
import freeman.rx.gxj.com.freeman.fragment.DiscoveryFragment;
import freeman.rx.gxj.com.freeman.fragment.HomeFragment;
import freeman.rx.gxj.com.freeman.fragment.PersonalFragment;
import freeman.rx.gxj.com.freeman.fragment.RecommendFragment;
import freeman.rx.gxj.com.freeman.parent.BaseActivity;
import freeman.rx.gxj.com.freeman.recycleractivity.GridPullToRefreshActivity;
import freeman.rx.gxj.com.freeman.recycleractivity.PullToRefreshActivity;
import freeman.rx.gxj.com.freeman.recycleractivity.PullToRefreshExpandActivity;
import freeman.rx.gxj.com.freeman.recycleractivity.TreeAdapterViewActivity;
import freeman.rx.gxj.com.freeman.services.UpdateAppService;
import freeman.rx.gxj.com.freeman.tab.FragmentTabHost;
import freeman.rx.gxj.com.freeman.tab.Tab;
import product.finance.xmqq.com.livedetectlibrary.XMActivity;

import android.support.design.widget.NavigationView;
import android.widget.Toast;


public class MainActivity extends BaseActivity {
    String url = "http://img1.imgtn.bdimg.com/it/u=2215239751,3809037166&fm=21&gp=0.jpg";
    String url2 = "http://img3.imgtn.bdimg.com/it/u=2851316075,3722502482&fm=11&gp=0.jpg";

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private ArrayList<Tab> mTabs = new ArrayList<Tab>(4);
    private Tab[] tabs;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView , mNavigationView2;
    private View headerView ,headerView2;

    private ImageView head_image ,head_image2;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencepUtils.setFirstUse(true);
        initView();

        updateVersion();
    }

    /**
     * 获取本地版本号
     * @return
     */
    public int getlocalVersion(){
        int localversion = 0;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            localversion = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localversion;
    }

    /**
     * 获取服务器版本号
     * @return
     */
    public int getServiceVersion(){
        int serviceversion = 2;//随便写一个测试
        return serviceversion;
    }

    /**
     * 版本更新
     * @param
     */
    public void updateVersion(){
        if(getServiceVersion() > getlocalVersion()){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("检查到新版本");
            builder.setMessage("是否更新");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startService(new Intent(MainActivity.this, UpdateAppService.class));
                }
            });
            builder.create().show();
        }
    }




    private void initView() {

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);

        Tab num1 = new Tab(R.string.app_home, R.drawable.selector_icon_num1, HomeFragment.class);
        Tab num2 = new Tab(R.string.app_discovery, R.drawable.selector_icon_num2, DiscoveryFragment.class);
        Tab num3 = new Tab(R.string.app_recommend, R.drawable.selector_icon_num3, RecommendFragment.class);
        Tab num4 = new Tab(R.string.app_personal, R.drawable.selector_icon_num4, PersonalFragment.class);

        mTabs.add(num1);
        mTabs.add(num2);
        mTabs.add(num3);
        mTabs.add(num4);

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
        //设置菜单事件处理
        setupDrawerContent(mNavigationView);

        setupDrawerContent(mNavigationView2);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            //**************左边菜单
            case R.id.go_home_fragment:
                Toast.makeText(MainActivity.this,"000",Toast.LENGTH_SHORT).show();
//                intent = new Intent(MainActivity.this,PullToRefreshActivity.class);
                intent = new Intent(MainActivity.this,XMActivity.class);

                startActivity(intent);
                break;
            case R.id.go_discovery_fragment:
                Toast.makeText(MainActivity.this,"111",Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this,GridPullToRefreshActivity.class);
                startActivity(intent);
                break;
            case R.id.go_recommend_fragment:
                Toast.makeText(MainActivity.this,"222",Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this,PullToRefreshExpandActivity.class);

                startActivity(intent);
                break;
            case R.id.go_personal_fragment:
                Toast.makeText(MainActivity.this,"333",Toast.LENGTH_SHORT).show();

                break;
            case R.id.go_setting:
                Toast.makeText(MainActivity.this,"444",Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                break;

            //**************右边菜单
            case R.id.go_scanner:
                intent = new Intent(MainActivity.this,LinearDragActivity.class);
                startActivity(intent);
                break;

            case R.id.go_sports:
                intent = new Intent(MainActivity.this,GridDragActivity.class);
                startActivity(intent);
                break;

            case R.id.go_test:
                intent = new Intent(MainActivity.this,DynamicDragActivity.class);
                startActivity(intent);
                break;

            case R.id.go_talk:
                intent = new Intent(MainActivity.this,CustomDragActivity.class);
                startActivity(intent);
                break;


            case R.id.go_others:
                intent = new Intent(MainActivity.this,TreeAdapterViewActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        //mDrawerLayout.closeDrawers();
    }


    public void OpenRightMenu()  {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,Gravity.RIGHT);
    }

    private void initDrawerLayout() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = mNavigationView.getHeaderView(0);
        head_image = (ImageView) headerView.findViewById(R.id.head_image);
        CircleImageLoader.loadHeadImage(url,head_image);

        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"ppppp",Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this,/*QQTabActivity*/NotificationUpdateActivity.class);
                startActivity(intent);
            }
        });

        mNavigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        headerView2 = mNavigationView2.getHeaderView(0);
        head_image2 = (ImageView) headerView2.findViewById(R.id.head_image);
        CircleImageLoader.loadHeadImage(url2,head_image2);

        head_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"3wwww",Toast.LENGTH_SHORT).show();
//                intent = new Intent(MainActivity.this, NotificationUpdateActivity.class);
//                startActivity(intent);

                mDrawerLayout.closeDrawers();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置固定右边
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {   }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)  {
                View mContent = mDrawerLayout.getChildAt(1);//此处是确定 哪个子view需要动画效果
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("RIGHT")) {
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
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,Gravity.RIGHT);
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
