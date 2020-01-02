package csr.dmt.zust.edu.cn.funjobapplication.view.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.zzhoujay.richtext.RichText;

import java.util.Formatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.module.database.helper.FunJobDbHelper;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.NoteApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.select.NoteSelectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.collect.TopicCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.collect.TopicCollectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.status.TopicCollectStatusResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;
import csr.dmt.zust.edu.cn.funjobapplication.view.JSBridge.WebViewActivity;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.NoteCreateActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String DETAIL_TOPIC_ID_KEY = "DETAIL_TOPIC_ID_KEY";
    private final String TAG = DetailActivity.class.getSimpleName();
    private String mTopicId;
    private Boolean isCollect = false;
    private UserLoginResModule mUserLoginResModule;
    private static final int DETAIL_NOTE_CREATE_REQUEST_CODE = 1;

    @BindView(R.id.tv_detail_browse_sum)
    TextView mTextViewBrowseSum;
    @BindView(R.id.tv_detail_collect_sum)
    TextView mTextViewCollectSum;
    @BindView(R.id.tc_detail_create_time)
    TextView mTextViewCreateTime;
    @BindView(R.id.tv_detail_content)
    TextView mTextViewContent;
    @BindView(R.id.linear_layout_detail)
    LinearLayout mLinearLayoutDetail;

    @BindView(R.id.iv_detail_head)
    ImageView mImageViewHead;
    @BindView(R.id.tv_dl_detail_create_note)
    TextView mTextViewDLCreateNote;
    @BindView(R.id.tv_dl_detail_all_note)
    TextView mTextViewDLAllNotes;
    @BindView(R.id.tv_dl_detail_collect)
    TextView mTextViewDLCollectNote;
    @BindView(R.id.tv_detail_answer)
    TextView mTextViewAnswer;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_NOTE_CREATE_REQUEST_CODE) {
            mLinearLayoutDetail.removeAllViews();
            getNoteByTopic(mTopicId, mUserLoginResModule.getId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mTopicId = (String) getIntent().getExtras().get(DETAIL_TOPIC_ID_KEY);
        getUserInfo();

        findViewById(R.id.btn_detail_edit).setOnClickListener(v -> createNoteHandler());

        getTopicDetailById(mTopicId, mUserLoginResModule.getId());
        getNoteByTopic(mTopicId, mUserLoginResModule.getId());
        getTopicCollectStatus(mTopicId, mUserLoginResModule.getId());
        initSidebar();
    }

    /**
     * 初始化侧边栏
     */
    private void initSidebar() {
        Glide.with(DetailActivity.this)
                .load(mUserLoginResModule.getHeadPortraitUrl())
                .placeholder(R.drawable.bg_load_default)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mImageViewHead);
        mTextViewDLCreateNote.setOnClickListener(v -> createNoteHandler());
        mTextViewDLAllNotes.setOnClickListener(v -> Toast.makeText(DetailActivity.this, "暂未开放", Toast.LENGTH_SHORT).show());
        mTextViewDLCollectNote.setOnClickListener(v -> {
            if (isCollect) {
                // 设置为为收藏
                cancelCollectTopic(mTopicId, mUserLoginResModule.getId());
            } else {
                // 收藏
                collectTopic(mTopicId, mUserLoginResModule.getId());
            }
        });
    }

    /**
     * 从数据库中获取用户信息
     */
    private void getUserInfo() {
        FunJobDbHelper funJobDbHelper = new FunJobDbHelper(DetailActivity.this);
        mUserLoginResModule = funJobDbHelper.getUserInfo(funJobDbHelper);
    }

    /**
     * 收藏主题
     *
     * @param topicId 主题id
     * @param userId  用户id
     */
    private void collectTopic(String topicId, String userId) {
        TopicApi.getInstance().collectTopic(new TopicCollectReqModule(userId, topicId),
                new IHttpCallBack<BaseResult<TopicCollectResModule>>() {
                    @Override
                    public void successCallBack(BaseResult<TopicCollectResModule> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            Toast.makeText(DetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                            setCollected();
                        } else {
                            Log.e(TAG, "collectTopic was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "collectTopic was error:::" + msg);
                    }
                });
    }

    /**
     * 取消收藏主题
     *
     * @param topicId 主题id
     * @param userId  用户id
     */
    private void cancelCollectTopic(String topicId, String userId) {
        TopicApi.getInstance().cancelCollectTopic(new TopicCancelCollectReqModule(userId, topicId),
                new IHttpCallBack<BaseResult<TopicCancelCollectResModule>>() {
                    @Override
                    public void successCallBack(BaseResult<TopicCancelCollectResModule> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            Toast.makeText(DetailActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                            setUnCollect();
                        } else {
                            Log.e(TAG, "cancelCollectTopic was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "cancelCollectTopic was error:::" + msg);
                    }
                });
    }

    /**
     * 设置收藏图片为 已收藏
     */
    private void setCollected() {
        Drawable drawable = getDrawable(R.drawable.ic_collected);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTextViewDLCollectNote.setCompoundDrawables(
                drawable, null, null, null);
        mTextViewDLCollectNote.setText("取消收藏");
        isCollect = true;
    }

    /**
     * 设置收藏图片为 为收藏
     */
    private void setUnCollect() {
        Drawable drawable = getDrawable(R.drawable.ic_un_collect);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTextViewDLCollectNote.setCompoundDrawables(
                drawable, null, null, null);
        mTextViewDLCollectNote.setText("加入收藏");
        isCollect = false;
    }

    /**
     * 查询主题收藏状态
     *
     * @param topicId 主题id
     * @param userId  用户id
     */
    private void getTopicCollectStatus(String topicId, String userId) {
        TopicApi.getInstance().selectTopicCollectStatus(userId, topicId,
                new IHttpCallBack<BaseResult<TopicCollectStatusResModule>>() {
                    @Override
                    public void successCallBack(BaseResult<TopicCollectStatusResModule> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            // 判断是否已经收藏
                            if (data.getData().getStatus() > 0) {
                                setCollected();
                            }
                        } else {
                            Log.e(TAG, "getTopicCollectStatus was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "getTopicCollectStatus was error:::" + msg);
                    }
                });
    }

    /**
     * 跳转创建笔记页面
     */
    private void createNoteHandler() {
        Intent intent = NoteCreateActivity.newIntent(this, mTopicId);
        startActivityForResult(intent, DETAIL_NOTE_CREATE_REQUEST_CODE);
    }

    /**
     * 获得intent
     *
     * @param context Context
     * @param topicId 主题id
     * @return intent
     */
    public static Intent newIntent(Context context, String topicId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DETAIL_TOPIC_ID_KEY, topicId);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * 根据topicId和userId获取主题详情
     *
     * @param topicId 主题id
     * @param userId  用户id
     */
    private void getTopicDetailById(String topicId, String userId) {
        TopicApi.getInstance().getTopicById(topicId, userId, new IHttpCallBack<BaseResult<TopicInfoModule>>() {
            @Override
            public void successCallBack(BaseResult<TopicInfoModule> data) {
                if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                    setContent(data.getData());
                } else {
                    Toast.makeText(DetailActivity.this, R.string.app_error, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "getTopicById was error:::" + data.getMsg());
                }
            }

            @Override
            public void errorCallBack(String msg) {
                Log.e(TAG, "getTopicById was error:::" + msg);
            }
        });
    }

    /**
     * 获取该主题下自己的笔记
     *
     * @param topicId 主题id
     * @param userId  用户id
     */
    private void getNoteByTopic(String topicId, String userId) {
        NoteApi.getInstance().selectMyNote(topicId, userId, new IHttpCallBack<BaseResult<List<NoteSelectResModule>>>() {
            @Override
            public void successCallBack(BaseResult<List<NoteSelectResModule>> data) {
                if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                    for (NoteSelectResModule noteSelectResModule : data.getData()) {
                        addNewNoteView(noteSelectResModule);
                    }
                } else {
                    Toast.makeText(DetailActivity.this, R.string.app_error, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "selectMyNote was error:::" + data.getMsg());
                }
            }

            @Override
            public void errorCallBack(String msg) {
                Log.e(TAG, "selectMyNote was error:::" + msg);
            }
        });
    }

    /**
     * 设置内容
     *
     * @param topicInfoModule 设置的内容
     */
    private void setContent(TopicInfoModule topicInfoModule) {
        mTextViewBrowseSum.setText(new Formatter().format("阅读 %s", topicInfoModule.getBrowseSum()).toString());
        mTextViewCollectSum.setText(new Formatter().format("收藏 %s", topicInfoModule.getCollectSum()).toString());
        mTextViewCreateTime.setText(topicInfoModule.getCreateTime());
        mTextViewContent.setText(topicInfoModule.getContent());

        // 查看答案
        mTextViewAnswer.setOnClickListener(v -> {
            PanterDialog panterDialog = new PanterDialog(DetailActivity.this)
                    .setHeaderBackground(R.drawable.pattern_bg_blue)
                    .withAnimation(Animation.POP)
                    .setMessage("问题比答案更重要")
                    .isCancelable(false);
            panterDialog.setPositive("前往查看", viewDialog -> {
                panterDialog.dismiss();
                Intent intent = WebViewActivity.newIntent(DetailActivity.this, topicInfoModule.getAnswerUrl(),
                        topicInfoModule.getTitle());
                startActivity(intent);
            });
            panterDialog.show();
        });

        mTextViewAnswer.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

        int labelIcon;
        switch (topicInfoModule.getLabel()) {
            case "1":
                labelIcon = R.drawable.ic_vue;
                break;
            default:
                labelIcon = R.drawable.ic_back;
                break;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(topicInfoModule.getTitle());
            // 设置返回按钮样式
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable upArrow = ContextCompat.getDrawable(this, labelIcon);
            if (upArrow != null) {
                upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
                actionBar.setHomeAsUpIndicator(upArrow);
            }
        }

    }

    /**
     * 添加笔记
     *
     * @param noteSelectResModule 笔记的内容
     */
    private void addNewNoteView(NoteSelectResModule noteSelectResModule) {
        View view = View.inflate(this, R.layout.item_detail_note, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);//重新指定LayoutParams。
        params.setMargins(10, 100, 10, 10);

        TextView textViewContent = view.findViewById(R.id.tv_detail_note_content);
        TextView textViewCreateTime = view.findViewById(R.id.tv_detail_note_create_time);
        ImageView textViewDelete = view.findViewById(R.id.iv_detail_note_delete);
        TextView textViewWeather = view.findViewById(R.id.tv_detail_weather);
        LinearLayout linearLayoutPictures = view.findViewById(R.id.view_detail_note_pictures);

        RichText.fromMarkdown(noteSelectResModule.getContent()).into(textViewContent);
        textViewCreateTime.setText(noteSelectResModule.getCreateTime());
        textViewWeather.setText(noteSelectResModule.getWeather());

        textViewDelete.setOnClickListener(v -> {
            // 删除操作
            PanterDialog panterDialog = new PanterDialog(DetailActivity.this)
                    .setHeaderBackground(R.drawable.pattern_bg_orange)
                    .withAnimation(Animation.POP)
                    .setTitle("删除确认")
                    .setNegative("返回")
                    .setMessage("删除笔记后不可恢复，你确定要删除笔记嘛？")
                    .isCancelable(false);
            panterDialog.setPositive("确定删除", viewDialog -> {
                panterDialog.dismiss();
                deleteNote(noteSelectResModule.getNoteId(), view);
            });
            panterDialog.show();
        });

        // 动态添加图片
        int picturesNumber = noteSelectResModule.getPictures().size();

        // 新建一个linearLayout,用于存放三个图片
        LinearLayout itemLinearLayoutPictures = null;

        for (int i = 0; i < picturesNumber; i++) {
            // 每3个一个LinearLayout
            if (i % 3 == 0) {
                if (i != 0) {
                    linearLayoutPictures.addView(itemLinearLayoutPictures);
                }
                itemLinearLayoutPictures = new LinearLayout(DetailActivity.this);
                itemLinearLayoutPictures.setLayoutParams(
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                itemLinearLayoutPictures.setOrientation(LinearLayout.HORIZONTAL);
            }
            // 创建图片
            ImageView noteImageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 200, 0);
            layoutParams.setMargins(5, 5, 5, 5);
            noteImageView.setLayoutParams(layoutParams);
            noteImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Glide.with(this)
                    .load(noteSelectResModule.getPictures().get(i))
                    .override(250, 200)
                    .placeholder(R.drawable.bg_load_default)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(noteImageView);

            // 向LinearLayout中添加图片
            itemLinearLayoutPictures.addView(noteImageView);
        }
        if (itemLinearLayoutPictures != null) {
            linearLayoutPictures.addView(itemLinearLayoutPictures);
        }

        mLinearLayoutDetail.addView(view, params);
    }

    /**
     * 删除笔记
     */
    private void deleteNote(String noteId, View view) {
        NoteApi.getInstance().deleteNote(new NoteDeleteReqModule(noteId),
                new IHttpCallBack<BaseResult<NoteDeleteResModule>>() {
                    @Override
                    public void successCallBack(BaseResult<NoteDeleteResModule> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            mLinearLayoutDetail.removeView(view);
                            Toast.makeText(DetailActivity.this, "删除笔记成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, R.string.app_error, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "deleteNote was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "deleteNote was error:::" + msg);
                    }
                });
    }
}
