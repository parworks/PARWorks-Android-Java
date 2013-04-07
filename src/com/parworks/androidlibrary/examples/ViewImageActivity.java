/*******************************************************************************
 * Copyright 2013 PAR Works, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
		setContentView(R.layout.activity_view_image_parworksandroidlibrary);

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
