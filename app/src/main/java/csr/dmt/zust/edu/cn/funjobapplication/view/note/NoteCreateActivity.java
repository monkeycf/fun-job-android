package csr.dmt.zust.edu.cn.funjobapplication.view.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.module.database.helper.FunJobDbHelper;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.NoteApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.upload.UploadPictureCall;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures.Picture;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures.PictureShowFragment;

public class NoteCreateActivity extends AppCompatActivity
        implements NoteMarkdownFragment.IFragmentInteraction,
        PictureShowFragment.ISelectedPictureChange,
        com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener {

    private final String TAG = NoteCreateActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private Fragment mFragmentMarkdown;
    private Fragment mFragmentShow;
    private PictureShowFragment mPictureShowFragment = new PictureShowFragment();
    private String mMarkdownText;
    private String mTopicId;
    private MenuItem menuItemPreview;
    private int mShowStatus = 0; // 0 编辑，1 预览
    private Timer mTimer = new Timer();
    private int mTimeOutFlag; // 0正常，1超时
    private static final int TIME_OUT = 1;
    private static final int TIME_NORMAL = 0;
    private static final String FRAGMENT_MARKDOWN = "FRAGMENT_MARKDOWN";
    private static final String FRAGMENT_TEXT_SHOW = "FRAGMENT_TEXT_SHOW";
    private static final String DETAIL_NOTE_CREATE_TOPIC_KEY = "DETAIL_NOTE_CREATE_TOPIC_KEY";
    private ArrayList<Picture> mSelectPictures = new ArrayList<>();
    private ArrayList<String> mSuccessPictureUrls = new ArrayList<>(); // 成功上传图片的路由数组
    private TextView mTextViewWeather;
    private ProgressBar mProgressBar;
    private UserLoginResModule mUserLoginResModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);

        mTextViewWeather = findViewById(R.id.tv_weather);
        mProgressBar = findViewById(R.id.spin_kit);
        mTopicId = (String) getIntent().getExtras().get(DETAIL_NOTE_CREATE_TOPIC_KEY);

        initMarkdownFragment(); // 初始化fragment
        initActionBar();
        initWeather();
        getUserInfo();

        // 设置loading
        Sprite Wave = new Wave();
        mProgressBar.setIndeterminateDrawable(Wave);
    }

    /**
     * 从数据库中获取用户信息
     */
    private void getUserInfo() {
        FunJobDbHelper funJobDbHelper = new FunJobDbHelper(NoteCreateActivity.this);
        mUserLoginResModule = funJobDbHelper.getUserInfo(funJobDbHelper);
    }

    /**
     * 利用高德地图，查询天气
     */
    private void initWeather() {
        final String cityName = "杭州";
        WeatherSearchQuery weatherQuery = new WeatherSearchQuery(
                cityName,
                WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch weatherSearch = new WeatherSearch(NoteCreateActivity.this);
        weatherSearch.setQuery(weatherQuery);
        weatherSearch.setOnWeatherSearchListener(NoteCreateActivity.this);
        weatherSearch.searchWeatherAsyn();
    }

    /**
     * 发起上传图片
     */
    private void uploadNote() {
        if (getMarkdownText() == null) {
            Toast.makeText(NoteCreateActivity.this, "先写点什么吧", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        if (mSelectPictures.size() == 0) {
            crateNote();
            return;
        }

        // 上传图片
        mSuccessPictureUrls.clear();
        mTimeOutFlag = TIME_NORMAL;
        for (Picture picture : mSelectPictures) {
            // 每张图片一次请求
            UploadPictureCall uploadPictureCall = new UploadPictureCall(NoteCreateActivity.this);
            uploadPictureCall.getInstance(picture.getPath());
        }
        // 10s主动判断
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkImageUpload();
                mTimeOutFlag = TIME_OUT;
            }
        }, 10000);
    }

    /**
     * 增加成功上传图片数量
     */
    public void addSuccessPicture(String url) {
        mSuccessPictureUrls.add(url);
        checkImageUpload();
    }

    /**
     * 判断图片是否上传完成
     */
    public void checkImageUpload() {
        // 数量相等上传成功
        if (mSuccessPictureUrls.size() == mSelectPictures.size()) {
            mTimer.cancel();
            crateNote();
        } else if (mTimeOutFlag == TIME_OUT) {
            mProgressBar.setVisibility(View.GONE);
            Log.e(TAG, "image upload was time out...");
            Toast.makeText(this, "上传图片超时", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建笔记
     */
    public void crateNote() {
        NoteApi.getInstance().createNote(
                new NoteCreateReqModule(mUserLoginResModule.getId(), mTopicId, mMarkdownText,
                        mSuccessPictureUrls, mTextViewWeather.getText().toString()),
                new IHttpCallBack<BaseResult<NoteCreateResModule>>() {
                    @Override
                    public void successCallBack(BaseResult<NoteCreateResModule> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            Toast.makeText(NoteCreateActivity.this, "上传笔记成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(NoteCreateActivity.this, R.string.app_error, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "createNote was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "createNote was error:::" + msg);
                    }
                });
    }

    /**
     * 重定义onRequestPermissionsResult方法，fix在fragment中不生效
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 获取到Activity下的Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() == 0) {
            return;
        }
        // 查找在Fragment中onRequestPermissionsResult方法并调用
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                // 这里就会调用我们Fragment中的onRequestPermissionsResult方法
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    /**
     * IFragmentInteraction 接口实现
     *
     * @param str 输入框的字符串
     */
    @Override
    public void getMarkdownText(String str) {
        setMarkdownText(str);
    }

    /**
     * PictureShowFragment 接口实现
     */
    @Override
    public void onSelectedPictureChangeHandler(ArrayList<Picture> selectPictures) {
        mSelectPictures = selectPictures;
    }

    /**
     * 初始化fragment
     */
    private void initMarkdownFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentShow = mFragmentMarkdown =
                mFragmentManager.findFragmentById(R.id.fragment_note_container_text_markdown);

        if (mFragmentMarkdown == null) {
            mShowStatus = 0;
            mFragmentMarkdown = NoteMarkdownFragment.getInstance();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_note_container_text_markdown,
                            mFragmentMarkdown,
                            FRAGMENT_MARKDOWN)
                    .add(R.id.fragment_note_container_pictures, mPictureShowFragment)
                    .commit();
        }
    }

    /**
     * 预览,编辑切换
     */
    private void previewHandler() {
        if (mShowStatus == 0) {
            String str = getMarkdownText();
            if (str == null) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            mShowStatus = 1;
            mFragmentShow = NoteTextShowFragment.getInstance(getMarkdownText());
            menuItemPreview.setTitle("编辑");
            replaceFragment(mFragmentMarkdown, mFragmentShow, FRAGMENT_TEXT_SHOW);
        } else {
            mShowStatus = 0;
            mFragmentMarkdown = NoteMarkdownFragment.getInstance();
            menuItemPreview.setTitle("预览");
            replaceFragment(mFragmentShow, mFragmentMarkdown, FRAGMENT_MARKDOWN);
        }
    }

    /**
     * 切换fragment
     *
     * @param fromFragment 原fragment
     * @param toFragment   目标fragment
     */
    private void replaceFragment(Fragment fromFragment, Fragment toFragment, String name) {
        // 判断
        if (!toFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .hide(fromFragment)
                    .add(R.id.fragment_note_container_text_markdown, toFragment, name)
                    .commit();
        } else {
            mFragmentManager.beginTransaction()
                    .hide(fromFragment)
                    .show(toFragment)
                    .commit();
        }
    }

    public String getMarkdownText() {
        return mMarkdownText;
    }

    public void setMarkdownText(String markdownText) {
        mMarkdownText = markdownText;
    }

    /**
     * 获取intent实例
     *
     * @param context Context
     * @param topicId 主题id
     * @return intent实例
     */
    public static Intent newIntent(Context context, String topicId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DETAIL_NOTE_CREATE_TOPIC_KEY, topicId);
        Intent intent = new Intent(context, NoteCreateActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * 初始化顶部导航栏
     */
    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        menuItemPreview = menu.findItem(R.id.action_preview);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 左侧按钮
            case android.R.id.home:
                finish();
                break;
            // 发表
            case R.id.action_save:
                uploadNote();
                break;
            // 预览
            case R.id.action_preview:
                previewHandler();
                break;
            // 相册选择
            case R.id.action_select_picture:
                mPictureShowFragment.verifyExternalPermission();
                break;
            // 拍照
            case R.id.action_took_photo:
                mPictureShowFragment.verifyCameraPermission();
                break;
        }
        return true;
    }

    /**
     * 通过反射，设置menu显示icon
     *
     * @param view View
     * @param menu Menu
     * @return boolean
     */
    @Override
    protected boolean onPrepareOptionsPanel(@Nullable View view, @NonNull Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    /**
     * 实况天气查询回调
     *
     * @param localWeatherLiveResult 天气
     * @param i                      状态码
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000) {
            LocalWeatherLive liveWeather = localWeatherLiveResult.getLiveResult();
            mTextViewWeather.setText((new Formatter().format("%s %s° %s", liveWeather.getCity(),
                    liveWeather.getTemperature(),
                    liveWeather.getWeather())).toString());
        } else {
            Log.e(TAG, "查询天气失败");
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}
