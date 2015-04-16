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
 * 
 * @author Eephone Xu
 * 
 */
public class CircleExpandFullProgressView extends View {

	// Base View Size
	private float mHeight, mWidth;
	private float mCenterX, mCenterY;

	// Max Circle Bound
	private float mMaxRadius;

	// Center Circle Inside
	private float mInnerRadius;

	// Drawing Circle
	private float mDrawingRadius;

	// Draw Paint
	private Paint mPaint;

	// Draw Control
	private int mRunSpeed;
	private boolean mThreadFlag;

	// Draw Color
	private int mDrawingColor;

	public CircleExpandFullProgressView(Context context) {
		super(context);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setAntiAlias(true);
		new DrawThread().start();
		mThreadFlag = true;
		mRunSpeed = 30;
		mDrawingColor = Color.rgb(0, 170, 150);
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

	public void setDrawingColor(int r, int g, int b) {
		mDrawingColor = Color.rgb(r, g, b);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Get View Width and Height
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);
		// Find the Base Circle Size
		mCenterX = mWidth / 2;
		mCenterY = mHeight / 2;
		mMaxRadius = mWidth > mHeight ? mHeight / 3 : mWidth / 3;
		mInnerRadius = mMaxRadius / 6;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Clean Draw
		canvas.drawARGB(0, 255, 255, 255);
		// Draw Base Circle
		mPaint.setStyle(Style.FILL_AND_STROKE);
		mPaint.setColor(mDrawingColor);
		canvas.drawCircle(mCenterX, mCenterY, mDrawingRadius, mPaint);
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

	private class DrawThread extends Thread {

		@Override
		public void run() {
			while (mThreadFlag) {
				// Reset
				mDrawingRadius = mInnerRadius;
				float speed = 0;
				// Expand
				while (mDrawingRadius <= mMaxRadius) {
					speed = speed + 0.2f;
					mDrawingRadius = mDrawingRadius + speed;
					try {
						mDrawHandler.sendEmptyMessage(0);
						Thread.sleep(mRunSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (mDrawingRadius >= mMaxRadius) {
						break;
					}
				}
				// Shrink
				while (mDrawingRadius >= mInnerRadius) {
					speed = speed - 0.2f;
					mDrawingRadius = mDrawingRadius - speed;
					try {
						mDrawHandler.sendEmptyMessage(0);
						Thread.sleep(mRunSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (speed <= 0 || mDrawingRadius <= mInnerRadius) {
						break;
					}
				}
			}
		}
	}
}
