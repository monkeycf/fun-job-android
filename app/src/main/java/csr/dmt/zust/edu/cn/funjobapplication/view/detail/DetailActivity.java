package csr.dmt.zust.edu.cn.funjobapplication.view.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Formatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.NoteApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.select.NoteSelectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.NoteCreateActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String DETAIL_TOPIC_ID_KEY = "DETAIL_TOPIC_ID_KEY";
    private String topicId;

    @BindView(R.id.tv_detail_title)
    TextView mTextViewTitle;
    @BindView(R.id.tv_detail_browse_sum)
    TextView mTextViewBrowseSum;
    @BindView(R.id.tv_detail_collect_sum)
    TextView mTextViewCollectSum;
    @BindView(R.id.tc_detail_create_time)
    TextView mTextViewCreateTime;
    @BindView(R.id.tv_detail_content)
    TextView mTextViewContent;
    @BindView(R.id.linear_layout_detail)
    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        topicId = (String) getIntent().getExtras().get(DETAIL_TOPIC_ID_KEY);

        findViewById(R.id.btn_detail_edit).setOnClickListener(v -> {
            Intent intent = NoteCreateActivity.newIntent(this, topicId);
            startActivity(intent);
        });

        getTopicDetailById(topicId, "19002");
        getNoteByTopic(topicId, "19002");
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
            public void SuccessCallBack(BaseResult<TopicInfoModule> data) {
                setContent(data.getData());
            }

            @Override
            public void ErrorCallBack(String msg) {
                System.out.println(msg);
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
            public void SuccessCallBack(BaseResult<List<NoteSelectResModule>> data) {
                System.out.println(data.getCode());
                for (NoteSelectResModule noteSelectResModule : data.getData()) {
                    addNewNoteView(noteSelectResModule);
                }
            }

            @Override
            public void ErrorCallBack(String msg) {
                System.out.println(msg);
            }
        });
    }

    /**
     * 设置内容
     *
     * @param topicInfoModule 设置的内容
     */
    private void setContent(TopicInfoModule topicInfoModule) {
        mTextViewTitle.setText(topicInfoModule.getTitle());
        mTextViewBrowseSum.setText(topicInfoModule.getBrowseSum());
        mTextViewCollectSum.setText(topicInfoModule.getCollectSum());
        mTextViewCreateTime.setText(topicInfoModule.getCreateTime());
        mTextViewContent.setText(topicInfoModule.getContent());
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

        TextView textViewContent = view.findViewById(R.id.tv_detail_note_content);
        TextView textViewCreateTime = view.findViewById(R.id.tv_detail_note_create_time);
        TextView textViewDelete = view.findViewById(R.id.tv_detail_note_delete);
        TextView textViewEdit = view.findViewById(R.id.tv_detail_note_edit);
        LinearLayout linearLayoutPictures = view.findViewById(R.id.view_detail_note_pictures);

        textViewContent.setText(noteSelectResModule.getContent());
        textViewCreateTime.setText(noteSelectResModule.getCreateTime());

        textViewDelete.setOnClickListener(v -> {
            // 删除操作
            deleteNote(noteSelectResModule.getNoteId(), view);
        });

        textViewEdit.setOnClickListener(v -> {
            // 编辑操作
        });

        // 动态添加图片
        int picturesNumber = noteSelectResModule.getPictures().size();
        for (int i = 0; i < picturesNumber; i++) {
            ImageView noteImageView = new ImageView(this);
            noteImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));  //设置图片宽高

            linearLayoutPictures.addView(noteImageView);
            Glide.with(this)
                    .load(noteSelectResModule.getPictures().get(i))
                    .into(noteImageView);
        }

        mLinearLayout.addView(view, params);
    }

    /**
     * 删除笔记
     */
    private void deleteNote(String noteId, View view) {
        NoteApi.getInstance().deleteNote(new NoteDeleteReqModule(noteId),
                new IHttpCallBack<BaseResult<NoteDeleteResModule>>() {
                    @Override
                    public void SuccessCallBack(BaseResult<NoteDeleteResModule> data) {
                        mLinearLayout.removeView(view);
                        Toast.makeText(DetailActivity.this, "删除笔记成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void ErrorCallBack(String msg) {
                        System.out.println(new Formatter().format("deleteNote is error:::%s", msg));
                    }
                });
    }
}
