package csr.dmt.zust.edu.cn.funjobapplication.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.UserApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_login_account)
    EditText mEditTextAccount;
    @BindView(R.id.et_login_password)
    EditText mEditTextPassword;
    @BindView(R.id.iv_login_clear_account)
    ImageView mImageViewClearAccount;
    @BindView(R.id.iv_login_clear_password)
    ImageView mImageViewClearPassword;
    @BindView(R.id.iv_login_open_eye)
    CheckBox mCheckBoxEye;
    @BindView(R.id.btn_login)
    Button mButtonLogin;
    @BindView(R.id.tv_login_forget_password)
    TextView mTextViewForgetPassword;
    @BindView(R.id.tv_login_register_account)
    TextView mTextViewRegister;
    @BindView(R.id.ll_login_service_layout)
    LinearLayout mLinerLayoutTerms;
    @BindView(R.id.tv_login_service)
    TextView mTextViewTerms;
    @BindView(R.id.rl_login_password_layout)
    RelativeLayout mRelativeLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mEditTextPassword.setLetterSpacing(0.2f);

        // 清空
        mImageViewClearAccount.setOnClickListener(v -> mEditTextAccount.setText(""));

        mImageViewClearPassword.setOnClickListener(v -> mEditTextPassword.setText(""));

        // 设置查看密码
        mCheckBoxEye.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                mEditTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            else
                mEditTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        });

        //当账号栏正在输入状态时，密码栏的清除按钮和眼睛按钮都隐藏
        mEditTextAccount.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mImageViewClearPassword.setVisibility(View.INVISIBLE);
                mCheckBoxEye.setVisibility(View.INVISIBLE);
            } else {
                mImageViewClearPassword.setVisibility(View.VISIBLE);
                mCheckBoxEye.setVisibility(View.VISIBLE);
            }
        });

        //当密码栏为正在输入状态时，账号栏的清除按钮隐藏
        mEditTextPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mImageViewClearAccount.setVisibility(View.INVISIBLE);

            else mImageViewClearAccount.setVisibility(View.VISIBLE);
        });

        // 修改密码
        mTextViewForgetPassword.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "sorry 暂未开放", Toast.LENGTH_SHORT).show());

        mTextViewTerms.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "MIT", Toast.LENGTH_SHORT).show());

        mTextViewRegister.setOnClickListener(v -> {
            // 注册
        });

        mButtonLogin.setOnClickListener(v -> {
            // 登录
            loginHandler(new UserLoginReqModule(mEditTextAccount.getText().toString(),
                    mEditTextPassword.getText().toString()));
        });
    }

    private void loginHandler(UserLoginReqModule userLoginReqModule) {
        UserApi.getInstance().LoginUser(userLoginReqModule, new IHttpCallBack<BaseResult<UserLoginResModule>>() {
            @Override
            public void SuccessCallBack(BaseResult<UserLoginResModule> data) {
                Log.e(LoginActivity.class.getSimpleName(), data.getCode() + "");
                if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // TODO 登录成功数据处理
                } else {
                    Toast.makeText(LoginActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    System.out.println(data.getMsg());
                }
            }

            @Override
            public void ErrorCallBack(String msg) {
                System.out.println(msg);
            }
        });
    }
}
