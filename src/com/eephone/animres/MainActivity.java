package com.eephone.animres;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.eephone.animres.R.id;
import com.eephone.animres.progress.CircleExpandFullProgressView;
import com.eephone.animres.progress.CircleExpandProgressView;
import com.eephone.animres.progress.CircleProgressView;

public class MainActivity extends Activity {

	private LinearLayout mContainer;

	private Animation pinShowAnim;
	private Animation pinGoneAnim;

	private CircleProgressView cpView;
	private CircleExpandProgressView cepView;
	private CircleExpandFullProgressView cefpView;

	private ImageView imgViewShow;
	private ImageView imgViewGone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pinShowAnim = AnimationUtils.loadAnimation(this, R.anim.pin_show);
		pinGoneAnim = AnimationUtils.loadAnimation(this, R.anim.pin_gone);

		LayoutParams params = new LayoutParams(96, 96);
		cepView = new CircleExpandProgressView(this);
		cepView.setLayoutParams(params);

		cpView = new CircleProgressView(this);
		cpView.setLayoutParams(params);

		cefpView = new CircleExpandFullProgressView(this);
		cefpView.setLayoutParams(params);

		imgViewShow = new ImageView(this);
		imgViewShow.setLayoutParams(params);
		imgViewShow.setVisibility(View.INVISIBLE);
		imgViewShow.setImageResource(R.drawable.ic_launcher);

		imgViewGone = new ImageView(this);
		imgViewGone.setLayoutParams(params);
		imgViewGone.setImageResource(R.drawable.ic_launcher);

		mContainer = (LinearLayout) findViewById(id.container);
		mContainer.addView(cepView);
		mContainer.addView(cpView);
		mContainer.addView(cefpView);

		mContainer.addView(imgViewShow);
		mContainer.addView(imgViewGone);

		imgViewShow.startAnimation(pinShowAnim);
		imgViewShow.setVisibility(View.VISIBLE);

		imgViewGone.startAnimation(pinGoneAnim);
		imgViewGone.setVisibility(View.INVISIBLE);

	}
}
