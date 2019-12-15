package csr.dmt.zust.edu.cn.funjobapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.api.NoteApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.UserApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.select.NoteSelectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectResModule;

public class MainActivity extends AppCompatActivity {

    private TopicApi mTopicApi;
    private UserApi mUserApi;
    private NoteApi mNoteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BottomNavigationBar bottomNavigationBar =
//                (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
//        BottomNavigationFragment.InitBottomNavigationBar(bottomNavigationBar);

        mTopicApi = new TopicApi();

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopicApi.getAllTopic(new IHttpCallBack<BaseResult<List<TopicInfoModule>>>() {

                    @Override
                    public void SuccessCallBack(BaseResult<List<TopicInfoModule>> data) {
                        if (data.getCode() == 1) {
                            Toast.makeText(MainActivity.this, data.getCode() + "",
                                    Toast.LENGTH_LONG).show();
                        }
                        System.out.println(data.getCode());
                    }

                    @Override
                    public void ErrorCallBack(String msg) {
                        System.out.println(msg);
                    }
                });
            }
        });

        findViewById(R.id.btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopicApi.cancelCollectTopic(new TopicCancelCollectReqModule("19002", "2"),
                        new IHttpCallBack<BaseResult<TopicCancelCollectResModule>>() {
                            @Override
                            public void SuccessCallBack(BaseResult<TopicCancelCollectResModule> data) {
                                System.out.println(data);
                            }

                            @Override
                            public void ErrorCallBack(String msg) {
                                System.out.println(msg);
                            }
                        });
            }
        });
        mNoteApi = new NoteApi();
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoteApi.selectMyNote("1", "19002",
                        new IHttpCallBack<BaseResult<List<NoteSelectResModule>>>() {
                            @Override
                            public void SuccessCallBack(BaseResult<List<NoteSelectResModule>> data) {
                                System.out.println(data);
                            }

                            @Override
                            public void ErrorCallBack(String msg) {
                                System.out.println(msg);
                            }
                        });
            }
        });
    }
}
