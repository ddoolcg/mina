package com.lcggame.pvp;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lcggame.pvp.net.MinaClient;
import com.potter.HData;
import com.potter.bean.UserInfor;

public class MainActivity extends Activity implements OnClickListener {
	private EditText et_username, et_password;
	private Button btn_regist, btn_login;
	private TextView tv;
	private String username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_regist = (Button) findViewById(R.id.btn_regist);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_regist.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		tv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tv) {
			EventBus.getDefault().post(new HData(HData.TYPE_MSG, "您好服务器！"),
					"send");
		} else {
			username = et_username.getText().toString();
			password = et_password.getText().toString();
			if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
				return;
			}
			btn_regist.setEnabled(false);
			btn_login.setEnabled(false);
			MinaClient.creat(v == btn_regist, username, password);
		}
	}

	@Subscriber(tag = "login", mode = ThreadMode.MAIN)
	private void postLoginResult(UserInfor infor) {
		if (infor.getId() != -1) {
			et_username.setEnabled(false);
			et_password.setEnabled(false);
			Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
		} else {
			btn_regist.setEnabled(true);
			btn_login.setEnabled(true);
			Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
		}
	}

	@Subscriber(tag = "regist", mode = ThreadMode.MAIN)
	private void postRegistResult(UserInfor infor) {
		if (infor.getId() != -1) {
			et_username.setEnabled(false);
			et_password.setEnabled(false);
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
		} else {
			btn_regist.setEnabled(true);
			btn_login.setEnabled(true);
			Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
		}
	}

	long time = 0;

	@Subscriber(tag = "heart_time", mode = ThreadMode.MAIN)
	private void heart(int code) {
		time = SystemClock.uptimeMillis();
	}

	@Subscriber(tag = "post_heart_time", mode = ThreadMode.MAIN)
	private void postHeartResult(int count) {
		tv.setText("心跳：" + count + " 延时：" + (SystemClock.uptimeMillis() - time)
				+ "ms");
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		EventBus.getDefault().post(true, "close_session");
		super.onDestroy();
	}
}
