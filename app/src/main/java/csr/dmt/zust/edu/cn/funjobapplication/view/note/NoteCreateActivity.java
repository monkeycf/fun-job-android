package csr.dmt.zust.edu.cn.funjobapplication.view.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class NoteCreateActivity extends AppCompatActivity implements NoteMarkdownFragment.FragmentInteraction {

    private FragmentManager mFragmentManager;
    private Fragment mFragmentMarkdown;
    private Fragment mFragmentShow;
    private Button mBtnPreview;
    private String mMarkdownText;
    private int mShowStatus = 0; // 0 编辑，1 预览
    private static final String FRAGMENT_MARKDOWN = "fragmentMarkdown";
    private static final String FRAGMENT_TEXT_SHOW = "fragmentTextShow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        initMarkdownFragment(); // 初始化fragment
        initPreviewButton(); // 初始化预览按钮
    }

    /**
     * FragmentInteraction 接口实现
     *
     * @param str 输入框的字符串
     */
    @Override
    public void getMarkdownText(String str) {
        setMarkdownText(str);
    }

    /**
     * 初始化fragment
     */
    private void initMarkdownFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentShow = mFragmentMarkdown =
                mFragmentManager.findFragmentById(R.id.fragment_container_text_markdown);

        if (mFragmentMarkdown == null) {
            mShowStatus = 0;
            mFragmentMarkdown = NoteMarkdownFragment.getInstance();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container_text_markdown,
                            mFragmentMarkdown,
                            FRAGMENT_MARKDOWN)
                    .commit();
        }
    }

    /**
     * 初始化预览按钮
     */
    private void initPreviewButton() {
        mBtnPreview = findViewById(R.id.btn_note_preview);
        mBtnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShowStatus == 0) {
                    mShowStatus = 1;
                    mFragmentShow = NoteTextShowFragment.getInstance(getMarkdownText());
                    replaceFragment(mFragmentMarkdown, mFragmentShow, FRAGMENT_TEXT_SHOW);
                } else {
                    mShowStatus = 0;
                    mFragmentMarkdown = NoteMarkdownFragment.getInstance();
                    replaceFragment(mFragmentShow, mFragmentMarkdown, FRAGMENT_MARKDOWN);
                }
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
                    .add(R.id.fragment_container_text_markdown, toFragment, name)
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
}
