package com.android.jd.module.portlet;

import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ViewFlipper;

import com.android.jd.R;
import com.android.jd.base.SimpleActivity;


public class GuideActivity extends SimpleActivity implements OnGestureListener, OnTouchListener {
	
	private ViewFlipper flipperGuideContainer;
	private ImageButton btnEnter;
	
	private Handler mainHandler = null;
	private GestureDetector gestureDetector;

	private boolean isFirst;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_guide;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		flipperGuideContainer = (ViewFlipper)findViewById(R.id.flipper_function_guide_container);
		btnEnter = (ImageButton)findViewById(R.id.btn_function_guide_enter);

		loadGuideImages();
		setActionListener();

		mainHandler = new Handler();
	}

	@Override
	protected void initListener() {

	}

	private void loadGuideImages(){
		int[] guideImages = new int[]{
			R.mipmap.guide_01,
			R.mipmap.guide_02,
			R.mipmap.guide_03,
			R.mipmap.guide_04,
			R.mipmap.guide_05
		};
		
		for(int i=0; i<guideImages.length; i++){
			ImageView guideImageView = new ImageView(this);
			guideImageView.setScaleType(ScaleType.CENTER_CROP);
			guideImageView.setImageDrawable(getResources().getDrawable(guideImages[i]));
			flipperGuideContainer.addView(guideImageView);
		}
	}
	
	private void setActionListener(){
		flipperGuideContainer.setOnTouchListener(this);

		
		btnEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isFirst) {
					setResult(GuideActivity.RESULT_OK);
					finish();
				} else {
					finish();
				}
			}
		});
		
		gestureDetector = new GestureDetector(this, this);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1.getX() > e2.getX()) {
			if (isFirst) {
				if (flipperGuideContainer.getDisplayedChild() + 1 >= flipperGuideContainer.getChildCount()) {
					mainHandler.post(new Runnable() {
						public void run() {
							setResult(GuideActivity.RESULT_OK);
							finish();
						}
					});
					return false;
				}
			}

			if(flipperGuideContainer.getDisplayedChild() + 2 >= flipperGuideContainer.getChildCount()){
				mainHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						btnEnter.setVisibility(View.VISIBLE);
					}
				}, 210);
			}
			
			if (flipperGuideContainer.getDisplayedChild() + 1 >= flipperGuideContainer.getChildCount()) {
				return true;
			}
			
			flipperGuideContainer.setInAnimation(GuideActivity.this, R.anim.push_left_in);
			flipperGuideContainer.setOutAnimation(GuideActivity.this, R.anim.push_left_out);
			flipperGuideContainer.showNext();

		} else if (e2.getX() > e1.getX()) {
			btnEnter.setVisibility(View.GONE);
			
			if (flipperGuideContainer.getDisplayedChild() == 0) {
				return true;
			}
			flipperGuideContainer.setInAnimation(GuideActivity.this, R.anim.push_right_in);
			flipperGuideContainer.setOutAnimation(GuideActivity.this, R.anim.push_right_out);
			flipperGuideContainer.showPrevious();
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		if (v == flipperGuideContainer) {
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

}
