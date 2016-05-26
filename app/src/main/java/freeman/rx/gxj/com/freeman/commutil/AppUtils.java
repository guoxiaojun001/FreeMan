package freeman.rx.gxj.com.freeman.commutil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import freeman.rx.gxj.com.freeman.parent.BaseActivity;

public class AppUtils {
	public static void goMarket(BaseActivity mActivity) {
		Uri uri = Uri
				.parse("market://details?id=" + mActivity.getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mActivity.startActivity(intent);
	}

	public static int isWifi(BaseActivity mActivity) {
		ConnectivityManager connectMgr = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null) {
			// 没网
			return 0;
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			return 1;
		} else {
			return 2;
		}
	}
}
