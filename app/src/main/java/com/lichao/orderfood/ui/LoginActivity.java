package com.lichao.orderfood.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lichao.orderfood.R;
import com.lichao.orderfood.utils.SMSUtil;
import com.mob.MobSDK;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017-11-17.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_user_back)
    ImageView ivUserBack;
    @BindView(R.id.iv_user_password_login)
    TextView ivUserPasswordLogin;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.tv_user_code)
    TextView tvUserCode;
    @BindView(R.id.et_user_code)
    EditText etUserCode;
    @BindView(R.id.login)
    TextView login;

    private static final int KEEP_TIME_MIN = 100;
    private static final int RESET_TIME = 101;
    //发送验证码成功
    private static final int SEND_CODE_SUCCESS = 102;
    //发送验证码失败
    private static final int SEND_CODE_FAIL = 103;
    //检测验证码和手机能够匹配
    private static final int CHECK_CODE_SUCCESS = 104;
    //检测验证码和手机不能匹配
    private static final int CHECK_CODE_FAIL = 105;
    //倒计时60S
    private int time = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        MobSDK.init(this, "2271f16c7ece6", "a9494347e9c76baa217decee7c721796");
        //要让afterEvent方法生效,必须在此次做事件监听
        SMSSDK.registerEventHandler(eventHandler);

    }

    @OnClick({R.id.tv_user_code,R.id.login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_user_code:
                sendCode();
                break;
            case R.id.login:
                //用户是否输入的账号(手机号)+密码+验证码
                //用户的验证码+手机号码是否匹配
                //checkLogin();
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEEP_TIME_MIN:
                    time--;
                    tvUserCode.setText("稍后再发("+time+")");
                    break;
                case RESET_TIME:
                    time = 60;
                    //时间更新在页面上
                    tvUserCode.setText("重新获取验证码");
                    break;
                case SEND_CODE_SUCCESS:
                    Toast.makeText(LoginActivity.this,"验证码下发成功",Toast.LENGTH_SHORT).show();
                    break;
                case SEND_CODE_FAIL:
                    Toast.makeText(LoginActivity.this,"验证码下发失败",Toast.LENGTH_SHORT).show();
                    break;
                case CHECK_CODE_SUCCESS:
                    Toast.makeText(LoginActivity.this,"验证码验证通过",Toast.LENGTH_SHORT).show();
                    //在此处可以做用户的注册,登录
                    //向服务器发送了一个post请求,服务器指定的字段
                    login();
                    break;
                case CHECK_CODE_FAIL:
                    Toast.makeText(LoginActivity.this,"验证码验证失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object o) {
            //此方法用于接受发送短信后验证码发送成功或者失败的结果
            //1.根据result是否获取成功的结果了
            if (event == SMSSDK.RESULT_COMPLETE) {
                //成功
                //2.此成功的结果，是否是下发验证码成功的结果
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //给指定手机下发短信验证码的这个事件是成功的
                    handler.sendEmptyMessage(SEND_CODE_SUCCESS);
                }
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证码和手机号码是匹配的,做用户的注册和登录
                    handler.sendEmptyMessage(CHECK_CODE_SUCCESS);
                }
            } else {
                //失败
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //给指定手机下发短信验证码的这个事件是失败的
                    handler.sendEmptyMessage(SEND_CODE_FAIL);
                }
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证码和手机号码是不匹配的
                    handler.sendEmptyMessage(CHECK_CODE_FAIL);
                }
            }
            super.afterEvent(event, result, o);
        }
    };

    private void login() {

    }

    private void sendCode() {
        String phone = etUserPhone.getText().toString().trim();
        //正则表达式
        boolean mobileNO = SMSUtil.isMobileNO(phone);
        if (mobileNO) {
            //sharesdk平台,给指定手机发送验证码次数,1天10个
            SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
                @Override
                public boolean onSendMessage(String s, String s1) {
                    return false;
                }
            });

            //倒计时  timerTask  handler
            new Thread() {
                @Override
                public void run() {
                    //每个1秒钟减少数组1
                    while (time > 0) {
                        //通过hanlder机制,告知主线程更新时间,更新时间周期,1秒钟更新一次
                        handler.sendEmptyMessage(KEEP_TIME_MIN);
                        try {
                            Thread.sleep(999);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //在60秒的计时过程中没有获取到验证码,重新获取验证码
                    handler.sendEmptyMessage(RESET_TIME);
                }
            }.start();
        }
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eventHandler);
        super.onDestroy();
    }
}
