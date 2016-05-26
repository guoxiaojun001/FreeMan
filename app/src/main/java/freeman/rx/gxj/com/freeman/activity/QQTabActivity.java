package freeman.rx.gxj.com.freeman.activity;

import freeman.rx.gxj.com.freeman.parent.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * Created by gxj on 2016/5/26.
 */
public class QQTabActivity extends BaseActivity {
    private RelativeLayout layout;

    private RelativeLayout layout1;
    private RelativeLayout layout2;
    private RelativeLayout layout3;

    private TextView text;
    private ImageView tab1;
    private ImageView tab2;
    private ImageView tab3;
    private ImageView first;
    private int current = 1; // 默认选中第一个，可以动态的改变此参数值
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        initUI();
    }
    private void initUI(){
        layout =  (RelativeLayout) findViewById(R.id.root);
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        layout3 = (RelativeLayout) findViewById(R.id.layout3);
        tab1  = (ImageView) findViewById(R.id.tab1);
        tab2  = (ImageView) findViewById(R.id.tab2);
        tab3  = (ImageView) findViewById(R.id.tab3);
        text = (TextView) findViewById(R.id.text);
        tab1.setOnClickListener(listener);
        tab2.setOnClickListener(listener);
        tab3.setOnClickListener(listener);
        RelativeLayout.LayoutParams rl  =  new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        first = new ImageView(this);
        first.setTag("first");
        first.setImageResource(R.drawable.topbar_selector);
        switch (current) {
            case 1:
                layout1.addView(first,rl);
                current = R.id.tab1;
                text.setText("最近联系人");
                break;
            case 2:
                layout2.addView(first,rl);
                current = R.id.tab2;
                text.setText("我的联系人");
                break;
            case 3:
                layout3.addView(first,rl);
                current = R.id.tab3;
                text.setText("我的QQ群联系人");
                break;

            default:
                break;
        }
    }
    private boolean isAdd = false;// 是否添加过 top_select
    private int select_width;   // top_select_width
    private int select_height;  // top_select_height
    private int firstLeft;    // 第一次添加后的左边距*****
    private int startLeft;   // 起始左边距
    // 添加一个view，移除一个view
    private void replace(){
        switch (current) {
            case R.id.tab1:
                changeTop(layout1);
                break;
            case R.id.tab2:
                changeTop(layout2);
                break;
            case R.id.tab3:
                changeTop(layout3);
                break;
            default:
                break;
        }
    }
    private void changeTop(RelativeLayout relativeLayout){
        ImageView old = (ImageView) relativeLayout.findViewWithTag("first");
        select_width   =old.getWidth();
        select_height = old.getHeight();
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(select_width, select_height);
        rl.leftMargin = old.getLeft()+((RelativeLayout)old.getParent()).getLeft();
        rl.topMargin = old.getTop()+((RelativeLayout)old.getParent()).getTop();
        // 获取起始位置
        firstLeft = old.getLeft()+((RelativeLayout)old.getParent()).getLeft();
        ImageView iv = new ImageView(this);
        iv.setTag("move");
        iv.setImageResource(R.drawable.topbar_selector);
        layout.addView(iv,rl);
        relativeLayout.removeView(old);
    }
    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(!isAdd){
                replace();// // 初次使用移除old 添加新的top_select为RelativeLayout所使用
                isAdd = true;
            }
            ImageView top_select = (ImageView) layout.findViewWithTag("move");
            int tabLeft = 0;
            int endLeft=0;
            boolean run =  false;
            switch (v.getId()) {
                case R.id.tab1:
                    if(current!=R.id.tab1)
                    {// 中心位置
                        tabLeft = ((RelativeLayout)tab1.getParent()).getLeft()+tab1.getLeft()+tab1.getWidth()/2;
                        // 最终位置
                        endLeft = tabLeft-select_width/2;
                        current = R.id.tab1;
                        run = true;
                        text.setText("最近联系人");
                    }
                    break;
                case R.id.tab2:
                    if(current!=R.id.tab2){
                        // 中心位置
                        tabLeft = ((RelativeLayout)tab2.getParent()).getLeft()+tab2.getLeft()+tab2.getWidth()/2;
                        // 最终位置
                        endLeft = tabLeft-select_width/2;
                        current = R.id.tab2;
                        run = true;
                        text.setText("我的联系人");
                    }
                    break;
                case R.id.tab3:
                    if(current!=R.id.tab3){
                        // 中心位置
                        tabLeft = ((RelativeLayout)tab3.getParent()).getLeft()+tab3.getLeft()+tab3.getWidth()/2;
                        // 最终位置
                        endLeft = tabLeft-select_width/2;
                        current = R.id.tab3;
                        run = true;
                        text.setText("我的QQ群联系人");
                    }
                    break;
                default:
                    break;
            }
            if(run){
                TranslateAnimation animation =  new TranslateAnimation(startLeft, endLeft-firstLeft, 0, 0);//水平移动
                startLeft = endLeft  -firstLeft;//// 重新设定起始位置
                animation.setDuration(400);
                animation.setFillAfter(true);
                top_select.bringToFront();
                top_select.startAnimation(animation);
            }
        }
    };
}
