package freeman.rx.gxj.com.freeman.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

public class MenuLeftFragment extends BaseFragment{

	private View rootView;
	private ImageView headImage;
	String url = "http://img1.imgtn.bdimg.com/it/u=2215239751,3809037166&fm=21&gp=0.jpg";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)	{


		if (null != rootView) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (null != parent) {
				parent.removeView(rootView);
			}
		} else {
			rootView = inflater.inflate(R.layout.layout_menu_left, null);
			setUI(rootView);// 控件初始化
		}


		return rootView;
	}

	private void setUI(View rootView) {

		headImage = (ImageView) rootView.findViewById(R.id.head_image);

		ImageLoader.getInstance().loadImage(url,
              	// mImageSize,
				options, new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {  }

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason){}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
						Bitmap localBitmap = Bitmap.createBitmap(loadedImage.getWidth(),
								loadedImage.getHeight(), localConfig);

						int height = localBitmap.getHeight() / 2;
						int width = localBitmap.getWidth() / 2;
						int radius = Math.min(height, width);
						int strokeWidth = 10;

						Paint mBitmapPaint = new Paint();
						Paint mBorderPaint = new Paint();

						//CLAMP 拉伸   REPEAT 重复  MIRROR 镜像
						BitmapShader mBitmapShader = new BitmapShader(loadedImage,
								Shader.TileMode.CLAMP , Shader.TileMode.CLAMP);
						mBitmapPaint.setShader(mBitmapShader);
						mBitmapPaint.setAntiAlias(true);
						mBitmapPaint.setStyle(Paint.Style.FILL);

						mBorderPaint.setStyle(Paint.Style.STROKE);
						mBorderPaint.setAntiAlias(true);
						mBorderPaint.setColor(Color.GREEN);
						mBorderPaint.setStrokeWidth(strokeWidth);

						Canvas canvas = new Canvas(localBitmap);
						//绘制圆形图片
						canvas.drawCircle(width, height, radius-strokeWidth , mBitmapPaint);
						//绘制外环
						canvas.drawCircle(width, height, radius-strokeWidth/2, mBorderPaint);

						headImage.setImageBitmap(localBitmap);

						//如果在地图中也可是使用，显示用户圆形头像
                        /*markerOption.icon(
                                BitmapDescriptorFactory.fromBitmap(localBitmap));
                        markerOptionlst.add(markerOption);
                        markerlst = aMap.addMarkers(markerOptionlst, true);
                        marker2 = markerlst.get(0);*/
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {

					}
				});


	}


}
