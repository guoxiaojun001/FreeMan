package freeman.rx.gxj.com.freeman.recycleractivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ldzs.recyclerlibrary.Mode;
import com.ldzs.recyclerlibrary.PullToRefreshRecyclerView;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.adapter.SimpleAdapter;
import freeman.rx.gxj.com.freeman.data.Date;

/**
 * 1:示例添加头,添加信息,以及自定义的Adapter使用.
 * 2:示例底部加载情况,加载中/加载异常/加载完毕
 */
public class PullToRefreshActivity extends AppCompatActivity {
    private PullToRefreshRecyclerView mRecyclerView;
    private SimpleAdapter mAdapter;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setTitle(getIntent().getStringExtra("title"));
        mRecyclerView = (PullToRefreshRecyclerView) this.findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setOnItemClickListener((v, position) ->
                Toast.makeText(getApplicationContext(), "Click:" + position, Toast.LENGTH_SHORT).show());
        View headerView = getHeaderView();
        headerView.setBackgroundColor(Color.BLUE);
        mRecyclerView.addHeaderView(headerView);


        //下拉加载
        mRecyclerView.setOnPullUpToRefreshListener(() -> {
            times = 0;
            mRecyclerView.postDelayed(() -> {
                mAdapter.addItems(Date.createItems(this, 2), 0);
                mRecyclerView.onRefreshComplete();
            }, 0);
        });
        //上拉刷新
        mRecyclerView.setOnPullDownToRefreshListener(() -> {
            if (times < 2) {
                mRecyclerView.postDelayed(() -> {
                    mAdapter.addItems(Date.createItems(this, 4));
                    mRecyclerView.onRefreshComplete();
                }, 1000);
            } else if (times < 4) {
                mRecyclerView.postDelayed(() -> {
                    mRecyclerView.setFooterRetryListener(v -> {
                        mAdapter.addItems(Date.createItems(this, 4));
                        mRecyclerView.onRefreshComplete();
                    });
                }, 0);
            } else {
                mRecyclerView.postDelayed(() -> mRecyclerView.setFooterComplete(), 0);
            }
            times++;
        });
        //初始设置2个,考虑其不满一屏加载状态
        mAdapter = new SimpleAdapter(this, Date.createItems(this, 2));
        mRecyclerView.setAdapter(mAdapter);
        /**
         可以指定下拉 上拉 或者都要，默认都有
         Mode.PULL_FROM_END,Mode.PULL_FROM_START,Mode.BOTH
         也可以在xml中的属性配置
         */
        //mRecyclerView.setRefreshMode(Mode.PULL_FROM_END);
    }

    /**
     * 获得一个顶部控件
     */
    public View getHeaderView() {
        int textColor = Date.getRandomColor();
        View header = LayoutInflater.from(this).inflate(
                R.layout.recyclerview_header1,
                (ViewGroup) findViewById(android.R.id.content), false);
        TextView headerView = (TextView) header;
        headerView.setTextColor(textColor);
        headerView.setText("HeaderView:" + mRecyclerView.getHeaderViewCount());
        headerView.setOnClickListener(v -> mRecyclerView.addHeaderView(getHeaderView()));
        return headerView;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            mAdapter.addItem("Header" + mAdapter.getItemCount(), 0);
            return true;
        } else if (id == R.id.action_remove) {
            mAdapter.remove(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
