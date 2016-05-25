package freeman.rx.gxj.com.freeman.commutil;

import android.util.Log;

public class LogUtils {

	public static boolean isTarget = true;//控制log输入的全局开关
	

	public static void Log(String msg) {
		if (isTarget) {

			Log("info", msg);

		}
	}
	public static void Log(String tag, String msg) {
		if (isTarget) {
			Log.i(tag, msg);
		}
	}

	public static void LogErr(String tag, String msg) {
		if (isTarget) {
			Log.e(tag, msg);
		}
	}

	public static void LogErr(String msg) {
		if (isTarget) {
			LogErr("error", msg);
		}
	}

}
