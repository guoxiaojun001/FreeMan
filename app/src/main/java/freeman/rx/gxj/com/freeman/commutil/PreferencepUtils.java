package freeman.rx.gxj.com.freeman.commutil;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gxj on 2016/5/23.
 */
public class PreferencepUtils {

    private Context mContext;
    private String fileName = "FreeMan";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private final String firstUse = "firstUse";//是否是第一次使用

    public PreferencepUtils(Context context){
        this.mContext = context;
        if(sharedPreferences == null || editor == null){
            sharedPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public void setFirstUse(boolean isFirst){
        editor.putBoolean(firstUse,isFirst);
        editor.commit();
    }


    public boolean isFirstUse(){
        boolean isFirst = sharedPreferences.getBoolean(firstUse,true);
        return isFirst;
    }
}
