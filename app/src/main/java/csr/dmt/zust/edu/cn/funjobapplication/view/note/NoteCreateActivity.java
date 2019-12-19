package csr.dmt.zust.edu.cn.funjobapplication.view.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.service.upload.UploadPicture;
import csr.dmt.zust.edu.cn.funjobapplication.service.upload.UploadPictureCall;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures.Picture;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures.PictureShowFragment;

public class NoteCreateActivity extends AppCompatActivity
        implements NoteMarkdownFragment.IFragmentInteraction, PictureShowFragment.ISelectedPictureChange {

    private FragmentManager mFragmentManager;
    private Fragment mFragmentMarkdown;
    private Fragment mFragmentShow;
    private Button mBtnPreview;
    private String mMarkdownText;
    private int mShowStatus = 0; // 0 编辑，1 预览
    private Timer mTimer = new Timer();
    private int mTimeOutFlag; // 0正常，1超时
    private static final int TIME_OUT = 1;
    private static final int TIME_NORMAL = 0;
    private static final String FRAGMENT_MARKDOWN = "FRAGMENT_MARKDOWN";
    private static final String FRAGMENT_TEXT_SHOW = "FRAGMENT_TEXT_SHOW";
    private static final String FRAGMENT_PICTURES_SHOW = "FRAGMENT_PICTURES_SHOW";
    private ArrayList<Picture> mSelectPictures = new ArrayList<>();
    private ArrayList<String> mSuccessPictureUrls = new ArrayList<>(); // 成功上传图片的路由数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        initMarkdownFragment(); // 初始化fragment
        initPreviewButton(); // 初始化预览按钮
        textUpload();
    }

    private void textUpload() {
        // TODO 测试上传
        findViewById(R.id.btn_select).setOnClickListener(v -> {
            mSuccessPictureUrls.clear();
            mTimeOutFlag = TIME_NORMAL;
            for (Picture picture : mSelectPictures) {
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
        });
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
            System.out.println("上传成功");
            Toast.makeText(this, "shangchuangchengong", Toast.LENGTH_SHORT).show();
        } else if (mTimeOutFlag == TIME_OUT) {
            System.out.println("超时");
            Toast.makeText(this, "chaoshi", Toast.LENGTH_SHORT).show();
        }
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
                    .add(R.id.fragment_note_container_pictures, new PictureShowFragment())
                    .commit();
        }
    }

    /**
     * 初始化预览按钮
     */
    private void initPreviewButton() {
        mBtnPreview = findViewById(R.id.btn_note_preview);
        mBtnPreview.setOnClickListener(v -> {
            if (mShowStatus == 0) {
                String str = getMarkdownText();
                if (str == null) {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                mShowStatus = 1;
                mFragmentShow = NoteTextShowFragment.getInstance(getMarkdownText());
                replaceFragment(mFragmentMarkdown, mFragmentShow, FRAGMENT_TEXT_SHOW);
            } else {
                mShowStatus = 0;
                mFragmentMarkdown = NoteMarkdownFragment.getInstance();
                replaceFragment(mFragmentShow, mFragmentMarkdown, FRAGMENT_MARKDOWN);
            }
        });
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

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NoteCreateActivity.class);
        return intent;
    }
}
