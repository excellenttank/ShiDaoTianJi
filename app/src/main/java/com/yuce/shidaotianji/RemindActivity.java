package com.yuce.shidaotianji;

import com.umeng.analytics.MobclickAgent;
import com.yuce.application.YcApplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

public class RemindActivity extends Activity {

	private RadioButton nextRemindOrNot;
	private Button btn_confirm;
	private SharedPreferences remindShow;
	private Editor remindShowEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		YcApplication.getInstance().addActivity(this);
		setContentView(R.layout.remind_layout);
		initView();
		remindShow = getSharedPreferences("remindShow", MODE_PRIVATE);
		remindShowEdit = remindShow.edit();
	}

	private void initView() {
		nextRemindOrNot = (RadioButton) findViewById(R.id.nextRemindOrNot);

		btn_confirm = (Button) findViewById(R.id.btn_confirm);

		btn_confirm.setOnClickListener(new OnClick());
	}

	private class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_confirm:

				if (nextRemindOrNot.isChecked()) {
					remindShowEdit.putBoolean("remindShow", false);
					remindShowEdit.commit();
				}

				Intent intent = new Intent(RemindActivity.this, MainActivity.class);
				startActivity(intent);

				break;

			default:
				break;
			}
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
