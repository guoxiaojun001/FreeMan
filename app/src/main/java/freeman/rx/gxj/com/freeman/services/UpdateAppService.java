package freeman.rx.gxj.com.freeman.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import freeman.rx.gxj.com.freeman.activity.MainActivity;
import freeman.rx.gxj.com.freeman.activity.R;

public class UpdateAppService extends Service {

	private String apkurl =
			"http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk";
	private String apkPath;
	private String apkName;
	private boolean canceled = false;
	private NotificationManager manager;
	private Notification notification;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			apkPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/update";
			apkName = "3GQQ.apk";
			registerBroader();
			setUpNotifiction();
			new Thread(new DownApkRunnable()).start();
		} else {
			Toast.makeText(UpdateAppService.this, "SD卡不存在", Toast.LENGTH_SHORT)
					.show();
		}
	}

	//创建通知
	private void setUpNotifiction() {
		manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
		int icon = R.mipmap.ic_launcher;
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		notification = new Notification(icon, tickerText, when);

		// 放置在"正在运行"栏目中
		notification.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.notify_update_layout);
		contentView.setTextViewText(R.id.name, "3gqq正在下载中");

		Intent canceledIntent = new Intent("canceled");
		canceledIntent.putExtra("canceled", "canceled");
		PendingIntent canceledPendingIntent = PendingIntent.getBroadcast(
				UpdateAppService.this, 1, canceledIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		contentView.setOnClickPendingIntent(R.id.cancle, canceledPendingIntent);
		notification.contentView = contentView;

		Intent intent = new Intent(UpdateAppService.this, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(
				UpdateAppService.this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		//notification.contentIntent = contentIntent;

		manager.notify(0, notification);// 发送通知
	}

	/**
	 * 取消接收者
	 *
	 */
	class CanceledReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if ("canceled".equals(intent.getStringExtra("canceled"))) {
				canceled = true;
				manager.cancel(0);
				stopSelf();
			}
		}

	}

	//注册广播
	public void registerBroader() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("canceled");
		registerReceiver(new CanceledReceiver(), filter);
	}

	//下载apk
	class DownApkRunnable implements Runnable {

		@Override
		public void run() {
			downloadApk();
		}

	}

	private int laterate = 0;

	private void downloadApk() {
		try {
			URL url = new URL(apkurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int length = conn.getContentLength();
			int count = 0;
			File apkPathFile = new File(apkPath);
			if (!apkPathFile.exists()) {
				apkPathFile.mkdir();
			}
			File apkFile = new File(apkPath, apkName);
			InputStream in = conn.getInputStream();
			FileOutputStream os = new FileOutputStream(apkFile);
			byte[] buffer = new byte[1024];
			do {
				int numread = in.read(buffer);
				count += numread;
				int progress = (int) (((float) count / length) * 100);// 得到当前进度
				// 只有当前进度比上一次进度大于等于1，才可以更新进度
				if (progress >= laterate + 1) {
					laterate = progress;
					Message msg = new Message();
					msg.what = 1;
					msg.arg1 = progress;
					handler.sendMessage(msg);
				}
				if (numread <= 0) {// 下载完毕
					handler.sendEmptyMessage(2);
					canceled = true;
					break;
				}
				os.write(buffer, 0, numread);
			} while (!canceled);// 如果没有被取消
			in.close();
			os.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.toString();
			e.printStackTrace();
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:// 更新进度
					int progress = msg.arg1;
					if (progress < 100) {
						RemoteViews contentView = notification.contentView;
						contentView.setTextViewText(R.id.tv_progress, progress
								+ "%");
						contentView.setProgressBar(R.id.progressbar, 100, progress,
								false);
					} else {// 下载完成，停止服务
						stopSelf();
					}
					manager.notify(0, notification);
					break;
				case 2:// 安装apk
					manager.cancel(0);
					installApk();
					break;

				default:
					break;
			}
		}
	};

	/**
	 * 安装apk
	 */
	private void installApk() {
		File apkFile = new File(apkPath, apkName);
		if (!apkFile.exists()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + apkFile.toString()),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
}
