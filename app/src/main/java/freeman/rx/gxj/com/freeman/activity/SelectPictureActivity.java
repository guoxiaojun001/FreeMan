package freeman.rx.gxj.com.freeman.activity;

import freeman.rx.gxj.com.freeman.commutil.Bimp;
import freeman.rx.gxj.com.freeman.commutil.FileUtils;
import freeman.rx.gxj.com.freeman.parent.BaseActivity;
import freeman.rx.gxj.com.freeman.view.MeterailEditText;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by gxj on 2016/6/17.
 */
public class SelectPictureActivity extends BaseActivity implements OnItemClickListener {

    private GridView gridview;
    private GridAdapter adapter;

    private TextView activity_selectimg_send;
    private ImageView back;
    private MeterailEditText comment_content;
    private String temp;
    private Button selectimg_bt_content_type, selectimg_bt_search;
//    private LinearLayout selectimg_relativeLayout_below;
    private ScrollView activity_selectimg_scrollView;
    private HorizontalScrollView selectimg_horizontalScrollView;

    private List<String> categoryList;

    private float dp;

    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public List<String> drr = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectimg);

        Init();
    }

    List<String> urList = new ArrayList<String>();

    public void Init() {
        dp = getResources().getDimension(R.dimen.dp);
        comment_content = (MeterailEditText) findViewById(R.id.comment_content);
        comment_content.setFocusable(true);
        comment_content.setFocusableInTouchMode(true);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SelectPictureActivity.this.finish();
            }
        });

        selectimg_horizontalScrollView = (HorizontalScrollView) findViewById(R.id.selectimg_horizontalScrollView);
        gridview = (GridView) findViewById(R.id.noScrollgridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridviewInit();

        activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
        activity_selectimg_send.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (bmp.size() < 1) {
                    Toast.makeText(getApplicationContext(), "至少需要一张图片",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(SelectPictureActivity.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                String content = comment_content.getText().toString().trim();
                if (content.equals("")) {
                    Toast.makeText(getApplicationContext(), "发布的内容不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < drr.size(); i++) {
                    urList.add(drr.get(i));
                }
                activity_selectimg_send.setEnabled(false);

                // 图片地址 urList；
            }
        });

//        selectimg_relativeLayout_below = (LinearLayout) findViewById(R.id.selectimg_relativeLayout_below);
        activity_selectimg_scrollView = (ScrollView) findViewById(R.id.activity_selectimg_scrollView);
        activity_selectimg_scrollView.setVerticalScrollBarEnabled(false);

        final View decorView = getWindow().getDecorView();
        final WindowManager wm = this.getWindowManager();

        decorView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        @SuppressWarnings("deprecation")
                        int displayheight = wm.getDefaultDisplay().getHeight();
                        Rect rect = new Rect();
                        decorView.getWindowVisibleDisplayFrame(rect);
                        int dynamicHight = rect.bottom - rect.top;
                        float ratio = (float) dynamicHight
                                / (float) displayheight;

                        if (ratio > 0.2f && ratio < 0.6f) {
//                            selectimg_relativeLayout_below.setVisibility(View.VISIBLE);
                            activity_selectimg_scrollView.scrollBy(0,
                                    activity_selectimg_scrollView.getHeight());
                        } else {
//                            selectimg_relativeLayout_below.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void gridviewInit() {
        adapter = new GridAdapter(this);
        adapter.setSelectedPosition(0);
        int size = 0;
        if (bmp.size() < 6) {
            size = bmp.size() + 1;
        } else {
            size = bmp.size();
        }
        LayoutParams params = gridview.getLayoutParams();
        final int width = size * (int) (dp * 9.4f);
        params.width = width;
        gridview.setLayoutParams(params);
        gridview.setColumnWidth((int) (dp * 9.4f));
        gridview.setStretchMode(GridView.NO_STRETCH);
        gridview.setNumColumns(size);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(this);

        selectimg_horizontalScrollView.getViewTreeObserver()
                .addOnPreDrawListener(// 绘制完毕
                        new OnPreDrawListener() {
                            public boolean onPreDraw() {
                                selectimg_horizontalScrollView.scrollTo(width,
                                        0);
                                selectimg_horizontalScrollView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                return false;
                            }
                        });
    }

    protected void onPause() {
        // TODO Auto-generated method stub
        // temp=comment_content.getText().toString().trim();
        super.onPause();
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater listContainer;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public class ViewHolder {
            public ImageView image;
            public Button bt;
        }

        public GridAdapter(Context context) {
            listContainer = LayoutInflater.from(context);
        }

        public int getCount() {
            if (bmp.size() < 6) {
                return bmp.size() + 1;
            } else {
                return bmp.size();
            }
        }

        public Bitmap getItem(int arg0) {

            return bmp.get(arg0);
        }

        public long getItemId(int arg0) {

            return arg0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int sign = position;
            // 自定义视图
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                // 获取list_item布局文件的视图

                convertView = listContainer.inflate(
                        R.layout.item_published_grida, null);

                // 获取控件对象
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                holder.bt = (Button) convertView
                        .findViewById(R.id.item_grida_bt);
                // 设置控件集到convertView
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.icon_addpic_unfocused));
                holder.bt.setVisibility(View.GONE);
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(bmp.get(position));
                holder.bt.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        PhotoBrowserActivity.bitmap.remove(sign);
                        bmp.get(sign).recycle();
                        bmp.remove(sign);
                        drr.remove(sign);

                        gridviewInit();
                    }
                });
            }

            return convertView;
        }
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_2));

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(
                            // 相册
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CUT_PHOTO_REQUEST_CODE = 2;
    private static final int SELECTIMG_SEARCH = 3;
    private String path = "";
    private Uri photoUri;

    public void photo() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = android.os.Environment
                    .getExternalStorageDirectory().getPath() + "/tempImage/";
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                // 有sd卡，是否有myImage文件夹
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                // 是否有headImg文件
                file = new File(sdcardPathDir + System.currentTimeMillis()
                        + ".JPEG");
            }
            if (file != null) {
                path = file.getPath();
                photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (drr.size() < 6 && resultCode == -1) {// 拍照
                    startPhotoZoom(photoUri);
                }
                break;
            case RESULT_LOAD_IMAGE:
                if (drr.size() < 6 && resultCode == RESULT_OK && null != data) {// 相册返回
                    Uri uri = data.getData();
                    if (uri != null) {
                        startPhotoZoom(uri);
                    }
                }
                break;
            case CUT_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK && null != data) {// 裁剪返回
                    Bitmap bitmap = Bimp.getLoacalBitmap(drr.get(drr.size() - 1));
                    PhotoBrowserActivity.bitmap.add(bitmap);
                    bitmap = Bimp.createFramedPhoto(480, 480, bitmap,
                            (int) (dp * 1.6f));
                    bmp.add(bitmap);
                    gridviewInit();
                }
                break;
            case SELECTIMG_SEARCH:
                if (resultCode == RESULT_OK && null != data) {
                    String text = "#" + data.getStringExtra("topic") + "#";
                    text = comment_content.getText().toString() + text;
                    comment_content.setText(text);

                    comment_content.getViewTreeObserver().addOnPreDrawListener(
                            new OnPreDrawListener() {
                                public boolean onPreDraw() {
                                    comment_content.setEnabled(true);
                                    // 设置光标为末尾
                                    CharSequence cs = comment_content.getText();
                                    if (cs instanceof Spannable) {
                                        Spannable spanText = (Spannable) cs;
                                        Selection.setSelection(spanText,
                                                cs.length());
                                    }
                                    comment_content.getViewTreeObserver()
                                            .removeOnPreDrawListener(this);
                                    return false;
                                }
                            });

                }

                break;
        }
    }

    private void startPhotoZoom(Uri uri) {
        try {
            // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyyMMddhhmmss");
            String address = sDateFormat.format(new java.util.Date());
            if (!FileUtils.isFileExist("")) {
                FileUtils.createSDDir("");

            }
            drr.add(FileUtils.SDPATH + address + ".JPEG");
            Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
                    + ".JPEG");

            final Intent intent = new Intent("com.android.camera.action.CROP");

            // 照片URL地址
            intent.setDataAndType(uri, "image/*");

            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 480);
            intent.putExtra("outputY", 480);
            // 输出路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 输出格式
            intent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            // 不启用人脸识别
            intent.putExtra("noFaceDetection", true);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {

        FileUtils.deleteDir(FileUtils.SDPATH);
        FileUtils.deleteDir(FileUtils.SDPATH1);
        // 清理图片缓存
        for (int i = 0; i < bmp.size(); i++) {
            bmp.get(i).recycle();
        }
        for (int i = 0; i < PhotoBrowserActivity.bitmap.size(); i++) {
            PhotoBrowserActivity.bitmap.get(i).recycle();
        }
        PhotoBrowserActivity.bitmap.clear();
        bmp.clear();
        drr.clear();
        super.onDestroy();
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SelectPictureActivity.this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        if (arg2 == bmp.size()) {
            String sdcardState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                new PopupWindows(SelectPictureActivity.this, gridview);
            } else {
                Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(SelectPictureActivity.this,
                    PhotoBrowserActivity.class);

            intent.putExtra("ID", arg2);
            startActivity(intent);
        }
    }


}
