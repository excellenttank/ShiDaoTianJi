package com.yuce.shidaotianji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.yuce.application.YcApplication;

public class Splash extends Activity {

	private YcApplication ycApp;
	private ImageView splashImage;
	private AlphaAnimation alphaAnimation;
	private SharedPreferences remindShow;
	private boolean remindShowOrNot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		ycApp = YcApplication.getInstance();
		ycApp.addActivity(this);

		splashImage = (ImageView) findViewById(R.id.splashImg);

		alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(3000);
		splashImage.setAnimation(alphaAnimation);

		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = null;

				remindShow = getSharedPreferences("remindShow", MODE_PRIVATE);

				// 默认是显示
				remindShowOrNot = remindShow.getBoolean("remindShow", true);

				if (remindShowOrNot) {

					intent = new Intent(Splash.this, RemindActivity.class);

				} else {

					intent = new Intent(Splash.this, MainActivity.class);

				}

				startActivity(intent);

				finish();
			}
		});

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
