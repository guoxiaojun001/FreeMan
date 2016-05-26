package freeman.rx.gxj.com.freeman.parent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.commutil.LogUtils;
import freeman.rx.gxj.com.freeman.commutil.PreferencepUtils;

/**
 * Created by gxj on 2016/5/23.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String Tag = "BaseActivity";
    protected PreferencepUtils preferencepUtils;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        preferencepUtils = new PreferencepUtils(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public void int4Right() {
        overridePendingTransition(R.anim.new_dync_in_from_right,
                R.anim.new_dync_no);
    }

}
