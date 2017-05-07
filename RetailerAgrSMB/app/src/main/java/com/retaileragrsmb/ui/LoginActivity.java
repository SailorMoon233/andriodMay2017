package com.retaileragrsmb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.retaileragrsmb.R;
import com.retaileragrsmb.common.Constant;
import com.retaileragrsmb.common.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.retaileragrsmb.ui.DashboardActivity.KEY_USER_TYPE;

/**
 * Created by hitesh on 5/6/2017.
 */

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_login)
    public void login(Button button) {

        if(tvUsername.getText() != null && tvUsername.length() < 10){
            Toast.makeText(this, "Enter a valid username", Toast.LENGTH_SHORT).show();
        } else if(tvPassword.getText() != null && tvPassword.length() < 4){
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
        } else {
            //Login webservice call
            String userType = null;
            if(tvUsername.getText().toString().trim().equals("1234567891")){
                userType = Constant.USER_DISTRIBUTOR;
            } else if(tvUsername.getText().toString().trim().equals("1234567892")){
                userType = Constant.USER_OPERATOR;
            }else if(tvUsername.getText().toString().trim().equals("1234567893")){
                userType = Constant.USER_RETAILER;
            } else {
                Toast.makeText(this, "Enter a valid user type.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra(KEY_USER_TYPE, userType);
//            Bundle bundle = new Bundle();
//            bundle.putString(KEY_USER_TYPE, userType);
//            intent.putExtras(bundle);
            startActivity(intent);
            Utils.setPrefrence(this, Constant.KEY_IS_LOGIN, true);
            Utils.setPrefrence(this, Constant.KEY_LOGIN_USER_TYPE, userType);
            finish();
        }

    }




}
