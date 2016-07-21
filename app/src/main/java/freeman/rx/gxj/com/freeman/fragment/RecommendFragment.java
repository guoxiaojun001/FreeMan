package freeman.rx.gxj.com.freeman.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.adapter.FaceGVAdapter;
import freeman.rx.gxj.com.freeman.adapter.FaceVPAdapter;
import freeman.rx.gxj.com.freeman.commutil.LogUtils;
import freeman.rx.gxj.com.freeman.emoji.AnimatedGifDrawable;
import freeman.rx.gxj.com.freeman.emoji.AnimatedImageSpan;
import freeman.rx.gxj.com.freeman.parent.BaseFragment;

/**
 * Created by gxj on 2016/5/23.
 */
public class RecommendFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "RecommendFragment";
    private View rootView;

    //表情图标每页6列4行
    private int columns = 6;
    private int rows = 4;
    //每页显示的表情view
    private List<View> views = new ArrayList<View>();
    //表情列表
    private List<String> staticFacesList;


    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private EditText input;
    private TextView send;
    private LinearLayout chat_face_container, chat_add_container;
    private ImageView image_face;//表情图标
    private ImageView image_add;//更多图标
    private ImageView image_voice;//语音
    private TextView tv_weather,//图片
            tv_xingzuo,//拍照
            tv_joke,//笑话
            tv_loc,//位置
            tv_gg,//帅哥
            tv_mm,//美女
            tv_music;//歌曲

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_recommend, null);
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticFacesList = initStaticFaces(mActivity);
    }


    /**
     * 初始化表情列表staticFacesList
     */
    public  List<String> initStaticFaces(Context context) {
        List<String> facesList=null;
        try {
            facesList = new ArrayList<String>();
            String[] faces = context.getAssets().list("p");
            //将Assets中的表情名称转为字符串一一添加进staticFacesList
            for (int i = 0; i < faces.length; i++) {
                facesList.add(faces[i]);
            }
            //去掉删除图片
            facesList.remove("del.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facesList;
    }

    private void initView(View view){
        image_face = (ImageView) view.findViewById(R.id.image_face); //表情图标
        image_add = (ImageView) view.findViewById(R.id.image_add);//更多图标
        image_voice = (ImageView) view.findViewById(R.id.image_voice);//语音
        chat_face_container = (LinearLayout) view.findViewById(R.id.chat_face_container);//表情布局
        chat_add_container = (LinearLayout) view.findViewById(R.id.chat_add_container);//更多

        mViewPager = (ViewPager) view.findViewById(R.id.face_viewpager);
        mViewPager.addOnPageChangeListener(new PageChange());
        //表情下小圆点
        mDotsLayout = (LinearLayout) view.findViewById(R.id.face_dots_container);
        input = (EditText) view.findViewById(R.id.input_sms);
        send = (TextView) view.findViewById(R.id.send_sms);
        input.setOnClickListener(this);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    send.setVisibility(View.VISIBLE);
                    image_voice.setVisibility(View.GONE);
                } else {
                    send.setVisibility(View.GONE);
                    image_voice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        image_face.setOnClickListener(this);//表情按钮
        image_add.setOnClickListener(this);//更多按钮
        image_voice.setOnClickListener(this);//语音按钮
        send.setOnClickListener(this); // 发送


        //初始化表情
        initViewPager();
    }

    /**
     * 初始化表情
     */
    private void initViewPager() {
        int pagesize =  staticFacesList.size() %
                (columns * rows - 1) == 0 ? staticFacesList.size() / (columns * rows - 1):
                staticFacesList.size() / (columns * rows - 1) + 1;
        // 获取页数
        for (int i = 0; i < pagesize; i++) {
            views.add(viewPagerItem(mActivity, i, staticFacesList, columns, rows, input));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(16, 16);
            mDotsLayout.addView(dotsItem(i), params);
        }
        FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
        mViewPager.setAdapter(mVpAdapter);
        mDotsLayout.getChildAt(0).setSelected(true);
    }


    public View viewPagerItem(final Context context, int position, List<String> staticFacesList, int columns, int rows, final EditText editText) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.face_gridview, null);//表情布局
        GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
        /**
         * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
         * */
        List<String> subList = new ArrayList<String>();
        subList.addAll(staticFacesList
                .subList(position * (columns * rows - 1),
                        (columns * rows - 1) * (position + 1) > staticFacesList
                                .size() ? staticFacesList.size() : (columns
                                * rows - 1)
                                * (position + 1)));
        /**
         * 末尾添加删除图标
         * */
        subList.add("_del.png");
        FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, context);
        gridview.setAdapter(mGvAdapter);
        gridview.setNumColumns(columns);
        // 单击表情执行的操作
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                try {
                    String png = ((TextView) ((LinearLayout) view).getChildAt(1)).
                            getText().toString();
                    if (!png.contains("_del")) {// 如果不是删除图标
                        insert(editText,getFace(context,png));
                    } else {
                        delete(editText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return gridview;
    }


    public SpannableStringBuilder getFace(Context mContext, String png) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            /**
             * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，
             * 获取到輸入框的内容是tempText的值而不是png
             * 所以这里对这个tempText值做特殊处理
             * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
             * */
            String tempText = "[" + png + "]";
            sb.append(tempText);
            sb.setSpan(
                    new ImageSpan(mContext, BitmapFactory
                            .decodeStream(mContext.getAssets().open(png))), sb.length()
                            - tempText.length(), sb.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb;
    }

    //向输入框里添加表情
    public void insert(EditText input,CharSequence text) {
        int iCursorStart = Selection.getSelectionStart((input.getText()));
        int iCursorEnd = Selection.getSelectionEnd((input.getText()));
        if (iCursorStart != iCursorEnd) {
            ((Editable) input.getText()).replace(iCursorStart, iCursorEnd, "");
        }
        int iCursor = Selection.getSelectionEnd((input.getText()));
        ((Editable) input.getText()).insert(iCursor, text);
    }

    /**
     * 删除图标执行事件
     * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，
     * 所以必需一次性删除掉tempText，才能将图片删除
     * */
    public void delete(EditText input) {
        if (input.getText().length() != 0) {
            int iCursorEnd = Selection.getSelectionEnd(input.getText());
            int iCursorStart = Selection.getSelectionStart(input.getText());
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(input,iCursorEnd)) {
                        String st = "[p/_000.png]";
                        ((Editable) input.getText()).delete(
                                iCursorEnd - st.length(), iCursorEnd);
                    } else {
                        ((Editable) input.getText()).delete(iCursorEnd - 1,
                                iCursorEnd);
                    }
                } else {
                    ((Editable) input.getText()).delete(iCursorStart,
                            iCursorEnd);
                }
            }
        }
    }

    /**
     * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
     * **/
    public boolean  isDeletePng(EditText input,int cursor) {
        String st = "[p/_000.png]";
        String content = input.getText().toString().substring(0, cursor);
        if (content.length() >= st.length()) {
            String checkStr = content.substring(content.length() - st.length(),content.length());
            String regex = "\\[[^\\]]+\\]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;
    }

    /**
     * 表情页切换时，底部小圆点
     *
     * @param position
     * @return
     */
    private ImageView dotsItem(int position) {
        View layout = inflater.inflate(R.layout.dot_image, null);
        ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
        iv.setId(position);
        return iv;
    }


    class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
                mDotsLayout.getChildAt(i).setSelected(false);
            }
            mDotsLayout.getChildAt(arg0).setSelected(true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtils.Log(TAG, ">>>>onHiddenChanged>>>>>" + hidden);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_sms://发送
                String content = input.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                //sendMsgText(content, true);
                break;
            case R.id.input_sms://点击输入框
                if (chat_face_container.getVisibility() == View.VISIBLE) {
                    chat_face_container.setVisibility(View.GONE);
                }
                if (chat_add_container.getVisibility() == View.VISIBLE) {
                    chat_add_container.setVisibility(View.GONE);
                }
                break;
            case R.id.image_face://点击表情按钮
                hideSoftInputView();//隐藏软键盘
                if (chat_add_container.getVisibility() == View.VISIBLE) {
                    chat_add_container.setVisibility(View.GONE);
                }
                if (chat_face_container.getVisibility() == View.GONE) {
                    chat_face_container.setVisibility(View.VISIBLE);
                } else {
                    chat_face_container.setVisibility(View.GONE);
                }
                break;
            case R.id.image_add://点击加号按钮
                hideSoftInputView();//隐藏软键盘
                if (chat_face_container.getVisibility() == View.VISIBLE) {
                    chat_face_container.setVisibility(View.GONE);
                }
                if (chat_add_container.getVisibility() == View.GONE) {
                    chat_add_container.setVisibility(View.VISIBLE);
                } else {
                    chat_add_container.setVisibility(View.GONE);
                }
                break;
            case R.id.image_voice://点击语音按钮
                /*if (!TextUtils.isEmpty(voice_type) && voice_type.equals("1")) {//以语音形式发送
                    speechRecognizerUtil.say(input, false);
                } else {//以文本形式发送
                    speechRecognizerUtil.say(input, true);
                }*/
                break;
            case R.id.tv_weather:
                //sendMsgText(PreferencesUtils.getSharePreStr(this, Const.CITY) + "天气", true);
                break;
            case R.id.tv_xingzuo:
                input.setText("星座#");
                input.setSelection(input.getText().toString().length());//光标移至最后
                //changeList(Const.MSG_TYPE_TEXT, "请输入星座#您的星座查询");
                chat_add_container.setVisibility(View.GONE);
                showSoftInputView(input);
                break;
            case R.id.tv_joke:
                //sendMsgText("笑话", true);
                break;
            case R.id.tv_loc:
                /*sendMsgText("位置", false);
                if (TextUtils.isEmpty(lat)) {
                    lat = "116.404,39.915";//北京
                }
                //传入地图（图片）路径
                changeList(Const.MSG_TYPE_LOCATION, Const.LOCATION_URL_S +
                        lat + "&markers=|" + lat + "&markerStyles=l,A,0xFF0000");*/
                break;
            case R.id.tv_gg:
                //sendMsgText("帅哥", true);
                break;
            case R.id.tv_mm:
                //sendMsgText("美女", true);
                break;
            case R.id.tv_music:
                input.setText("歌曲##");
                input.setSelection(input.getText().toString().length() - 1);
                //changeList(Const.MSG_TYPE_TEXT, "请输入：歌曲#歌曲名#演唱者");
                chat_add_container.setVisibility(View.GONE);
                showSoftInputView(input);
                break;
        }
    }
}
