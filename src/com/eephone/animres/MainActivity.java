package com.eephone.animres;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.eephone.animres.R.id;
import com.eephone.animres.progress.CircleExpandFullProgressView;
import com.eephone.animres.progress.CircleExpandProgressView;
import com.eephone.animres.progress.CircleProgressView;

public class MainActivity extends Activity {

	private LinearLayout mContainer;

	private CircleProgressView cpView;
	private CircleExpandProgressView cepView;
	private CircleExpandFullProgressView cefpView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LayoutParams params = new LayoutParams(96, 96);
		cepView = new CircleExpandProgressView(this);
		cepView.setLayoutParams(params);

		cpView = new CircleProgressView(this);
		cpView.setLayoutParams(params);

		cefpView = new CircleExpandFullProgressView(this);
		cefpView.setLayoutParams(params);

		mContainer = (LinearLayout) findViewById(id.container);
		mContainer.addView(cepView);
		mContainer.addView(cpView);
		mContainer.addView(cefpView);

	}
}
