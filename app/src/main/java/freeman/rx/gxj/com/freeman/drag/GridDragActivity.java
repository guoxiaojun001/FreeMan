package freeman.rx.gxj.com.freeman.drag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.ldzs.recyclerlibrary.DragRecyclerView;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.adapter.SimpleAdapter;
import freeman.rx.gxj.com.freeman.data.Date;

/**
 * Created by cz on 16/1/25.
 */
public class GridDragActivity extends AppCompatActivity {
    private DragRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        setTitle(getIntent().getStringExtra("title"));
        mRecyclerView = (DragRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new SimpleAdapter(this, R.layout.grid_text_item, Date.createItems(this, 100)));
        mRecyclerView.setLongPressDrawEnable(true);
        mRecyclerView.setOnDragItemEnableListener(position -> true);
    }

}
