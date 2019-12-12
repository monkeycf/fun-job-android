package csr.dmt.zust.edu.cn.funjobapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.bottomNavigationBar.BottomNavigation;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.UserApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.UserRegister.userRegisterReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.UserRegister.UserRegisterResModule;

public class MainActivity extends AppCompatActivity {

    private TopicApi mTopicApi;
    private UserApi mUserApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationBar bottomNavigationBar =
                (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        BottomNavigation.InitBottomNavigationBar(bottomNavigationBar);

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
                mTopicApi.getTopicById("10", "19002",
                        new IHttpCallBack<BaseResult<TopicInfoModule>>() {
                            @Override
                            public void SuccessCallBack(BaseResult<TopicInfoModule> data) {
                                System.out.println(data);
                            }

                            @Override
                            public void ErrorCallBack(String msg) {
                                System.out.println(msg);
                            }
                        });
            }
        });
        mUserApi = new UserApi();
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserApi.RegisterUser(new userRegisterReqModule("12588890147", "134564"),
                        new IHttpCallBack<BaseResult<UserRegisterResModule>>() {
                            @Override
                            public void SuccessCallBack(BaseResult<UserRegisterResModule> data) {
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
