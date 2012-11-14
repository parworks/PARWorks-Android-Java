package com.parworks.androidlibrary.examples;

import com.parworks.androidlibrary.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ViewImageActivity extends Activity {

	private ImageView mImageView;
	private Bitmap mImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_image);

		Intent extras = getIntent();

		mImage = (Bitmap) extras.getParcelableExtra("BitmapImage");

		mImageView = (ImageView) findViewById(R.id.image);

		mImageView.setImageBitmap(mImage);

		View overlay = findViewById(R.id.overlay);
		int opacity = 200;
		overlay.setBackgroundColor(opacity * 0x1000000);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT, 100);
		params.gravity = Gravity.BOTTOM;
		overlay.setLayoutParams(params);
		overlay.invalidate();

	}
}
