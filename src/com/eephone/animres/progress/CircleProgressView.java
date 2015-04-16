package com.eephone.animres.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.View;

/**
 * Simulation of Huawei EMUI3.0 progress
 * 
 * @author Eephone Xu
 * 
 */
public class CircleProgressView extends View {

	// Base Circle
	private float mHeight, mWidth;
	private float mCenterX, mCenterY;
	private float mRadius;

	// Run Circle
	private float mCenterXRun, mCenterYRun;
	private float mCenterXOffset, mCenterYOffset;
	private float mRadiusRun;

	// Draw Paint
	private Paint mPaint;

	// Draw Control
	private int mRunSpeed;
	private boolean mThreadFlag;

	// Draw Color
	private int mRunColor;
	private int mBaseColor;

	public CircleProgressView(Context context) {
		super(context);
		mThreadFlag = true;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setAntiAlias(true);
		new DrawThread().start();
		mRunSpeed = 25;
		mRunColor = Color.rgb(0, 170, 150);
		mBaseColor = Color.GRAY;
	}

	public enum RunSpeed {
		Low, Mid, High
	};

	public void setRunSpeed(RunSpeed speed) {
		switch (speed) {
		case Low:
			mRunSpeed = 30;
			break;
		case Mid:
			mRunSpeed = 25;
			break;
		case High:
			mRunSpeed = 20;
			break;
		default:
			mRunSpeed = 25;
			break;
		}
	}

	public void setRunColor(int r, int g, int b) {
		mRunColor = Color.rgb(r, g, b);
	}

	public void setBaseColor(int r, int g, int b) {
		mBaseColor = Color.rgb(r, g, b);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Get View Width and Height
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);
		// Find the Base Circle Size
		mCenterX = mWidth / 2;
		mCenterY = mHeight / 2;
		mRadius = mWidth > mHeight ? mHeight / 3 : mWidth / 3;
		// Find the Run Circle Size
		mCenterXOffset = mCenterX;
		mCenterYOffset = mCenterY;
		mCenterXRun = 0;
		mCenterYRun = -mRadius;
		mRadiusRun = mWidth > mHeight ? mHeight / 12 : mWidth / 12;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Clean Draw
		canvas.drawARGB(0, 255, 255, 255);
		// Draw Base Circle
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(mBaseColor);
		canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
		// Draw Run Circle
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(mRunColor);
		canvas.drawCircle(mCenterXRun + mCenterXOffset, mCenterYRun
				+ mCenterYOffset, mRadiusRun, mPaint);
	}

	@Override
	protected void onDetachedFromWindow() {
		mThreadFlag = false;
		super.onDetachedFromWindow();
	}

	@SuppressLint("HandlerLeak")
	private Handler mDrawHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			invalidate();
		}
	};

	// Part A,B,C,D like this:
	//
	// ---* * *
	// --* D A *
	// -* * * * *
	// --* C B *
	// ---* * *
	//

	private class DrawThread extends Thread {

		@Override
		public void run() {
			while (mThreadFlag) {
				float speed = 0;
				mCenterXRun = 0;
				mCenterYRun = -mRadius;
				// X part A
				while (mCenterXRun <= mRadius) {
					speed = speed + 0.2f;
					mCenterXRun = mCenterXRun + speed;
					mCenterYRun = -(float) Math.sqrt((mRadius * mRadius)
							- ((mCenterXRun) * (mCenterXRun)));
					try {
						mDrawHandler.sendEmptyMessage(0);
						Thread.sleep(mRunSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (mCenterXRun >= mRadius) {
						break;
					}
				}
				// X part B
				while (mCenterXRun >= 0) {
					speed = speed + 0.2f;
					mCenterXRun = mCenterXRun - speed;
					mCenterYRun = (float) Math.sqrt((mRadius * mRadius)
							- ((mCenterXRun) * (mCenterXRun)));
					try {
						mDrawHandler.sendEmptyMessage(0);
						Thread.sleep(mRunSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (mCenterXRun <= 0) {
						break;
					}
				}
				// X part C
				while (mCenterXRun >= -mRadius) {
					speed = speed - 0.2f;
					mCenterXRun = mCenterXRun - speed;
					mCenterYRun = (float) Math.sqrt((mRadius * mRadius)
							- ((mCenterXRun) * (mCenterXRun)));
					try {
						mDrawHandler.sendEmptyMessage(0);
						Thread.sleep(mRunSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (mCenterXRun <= -mRadius) {
						break;
					}
				}
				// X part D
				while (mCenterXRun <= 0) {
					speed = speed - 0.2f;
					mCenterXRun = mCenterXRun + speed;
					mCenterYRun = -(float) Math.sqrt((mRadius * mRadius)
							- ((mCenterXRun) * (mCenterXRun)));
					try {
						mDrawHandler.sendEmptyMessage(0);
						Thread.sleep(mRunSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (speed <= 0 || mCenterXRun >= 0) {
						break;
					}
				}
			}
		}
	}
}
