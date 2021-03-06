package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.DialogCallback;
import org.lntu.online.model.entity.ErrorInfo;
import org.lntu.online.model.entity.LoginInfo;
import org.lntu.online.model.entity.UserType;
import org.lntu.online.model.storage.LoginShared;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;

public class LoginActivity extends StatusBarActivity {

    @BindView(R.id.edt_user_id)
    protected MaterialEditText edtUserId;

    @BindView(R.id.edt_pwd)
    protected MaterialEditText edtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    protected void onBtnLoginClick() {
        if (edtUserId.getText().length() < 10) {
            edtUserId.setError("学号长度为10位");
            edtUserId.requestFocus();
        } else if (edtPwd.getText().length() <= 0) {
            edtPwd.setError("密码不能为空");
            edtPwd.requestFocus();
        } else {
            loginAsyncTask(edtUserId.getText().toString(), edtPwd.getText().toString());
        }
    }

    private void loginAsyncTask(String userId, String password) {
        ApiClient.service.login(userId, password, new DialogCallback<LoginInfo>(this) {

            @Override
            public void handleSuccess(LoginInfo loginInfo, Response response) {
                if (loginInfo.getUserType() == UserType.STUDENT) {
                    LoginShared.login(LoginActivity.this, loginInfo);
                    ToastUtils.with(LoginActivity.this).show("登录成功");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    edtUserId.setError("账户未通过验证");
                    edtUserId.requestFocus();
                }
            }

            @Override
            public void handleFailure(ErrorInfo errorInfo) {
                switch (errorInfo.getErrorCode()) {
                    case PASSWORD_ERROR:
                        edtPwd.setError("密码未通过验证");
                        edtPwd.requestFocus();
                        break;
                    default:
                        super.handleFailure(errorInfo);
                        break;
                }
            }

        });
    }

    @OnClick(R.id.btn_tos)
    protected void onBtnAgreementClick() {
        startActivity(new Intent(this, TermsOfServiceActivity.class));
    }

}
