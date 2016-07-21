package freeman.rx.gxj.com.freeman.parent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import freeman.rx.gxj.com.freeman.commutil.PreferenceUtils;

/**
 * Created by gxj on 2016/5/23.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String Tag = "BaseActivity";
    protected PreferenceUtils preferencepUtils;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        preferencepUtils = new PreferenceUtils(this);
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


}
