package csr.dmt.zust.edu.cn.funjobapplication.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.UserApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterResModule;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.et_register_account)
    EditText mEditTextAccount;
    @BindView(R.id.iv_register_clear_account)
    ImageView mImageViewClearAccount;
    @BindView(R.id.et_register_password)
    EditText mEditTextPassword;
    @BindView(R.id.iv_register_clear_password)
    ImageView mImageViewClearPassword;
    @BindView(R.id.btn_register)
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    /**
     * get intent
     *
     * @param context Context
     * @return intent
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    /**
     * 初始化
     */
    private void init() {
        mImageViewClearAccount.setOnClickListener(v -> mEditTextAccount.setText(""));
        mImageViewClearPassword.setOnClickListener(v -> mEditTextPassword.setText(""));
        mButtonRegister.setOnClickListener(v -> {
            String account = mEditTextAccount.getText().toString();
            String password = mEditTextPassword.getText().toString();
            final int PHONE_LENGTH = 11;
            final int PASSWORD_LENGTH = 8;
            if (account.length() != PHONE_LENGTH) {
                Toast.makeText(RegisterActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            } else if (password.length() < PASSWORD_LENGTH) {
                Toast.makeText(RegisterActivity.this, "密码需大于8位", Toast.LENGTH_SHORT).show();
            } else {
                registerHandler(new UserRegisterReqModule(account, password));
            }
        });
    }

    /**
     * 用户注册接口
     *
     * @param userRegisterReqModule 用户注册数据
     */
    private void registerHandler(UserRegisterReqModule userRegisterReqModule) {
        UserApi.getInstance().registerUser(userRegisterReqModule,
                new IHttpCallBack<BaseResult<UserRegisterResModule>>() {
                    @Override
                    public void SuccessCallBack(BaseResult<UserRegisterResModule> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            setAccountBack(mEditTextAccount.getText().toString());
                        } else {
                            Toast.makeText(RegisterActivity.this, R.string.app_error, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "registerUser was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void ErrorCallBack(String msg) {
                        Log.e(TAG, "registerUser was error:::" + msg);
                    }
                });
    }

    /**
     * 返回登录，并携带账号
     *
     * @param account 账号
     */
    private void setAccountBack(String account) {
        Intent intent = LoginActivity.newRequestIntent(account);
        setResult(RESULT_OK, intent);
        finish();
    }
}
