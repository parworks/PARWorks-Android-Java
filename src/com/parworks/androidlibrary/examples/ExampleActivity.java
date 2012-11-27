package com.parworks.androidlibrary.examples;

import java.util.List;

import com.parworks.androidlibrary.R;
import com.parworks.androidlibrary.ar.ARListener;
import com.parworks.androidlibrary.ar.ARSite;
import com.parworks.androidlibrary.ar.ARSites;
import com.parworks.androidlibrary.response.BaseImageInfo;
import com.parworks.androidlibrary.utils.BitmapUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class ExampleActivity extends Activity {

	private ARSite mDemoSite;

	private List<BaseImageInfo> mBaseImages;
	private List<Bitmap> mBaseImageBitmaps;

	public static final String API_KEY = "e6f5c802-de04-40e2-9b98-77c84abeca4e";
	public static final String SECRET_KEY = "fa9be0a6-53c8-4bd9-9e2b-24033382ea39";

	private GridView mBaseImageView;

	private int mNumImagesToRetrieve = 5;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_site_parworksandroidlibrary);
		mContext = this;

		mBaseImageView = (GridView) findViewById(R.id.gridViewImage);

		ARSites sites = new ARSites(API_KEY, SECRET_KEY);
		sites.getExisting("FirstSite", new ARListener<ARSite>() {

			@Override
			public void handleResponse(ARSite resp) {
				gotDemoSite(resp);

			}

		});
		mBaseImageView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				startViewImageActivity(position);

			}

		});

	}

	private void startViewImageActivity(int position) {

		Bitmap bitmap = mBaseImageBitmaps.get(position);

		Intent intent = new Intent(ExampleActivity.this,
				ViewImageActivity.class);
		intent.putExtra("BitmapImage", bitmap);
		startActivity(intent);
	}

	public void gotDemoSite(ARSite demo) {
		mDemoSite = demo;
		mDemoSite.getBaseImages(new ARListener<List<BaseImageInfo>>() {

			@Override
			public void handleResponse(List<BaseImageInfo> resp) {
				gotBaseImages(resp);

			}

		});
	}

	public void gotBaseImages(List<BaseImageInfo> baseImages) {
		mBaseImages = baseImages;
		populateGridView(mBaseImages);
	}

	public void populateGridView(List<BaseImageInfo> baseImages) {
		mBaseImages = baseImages;
		BitmapUtils bitmapUtils = new BitmapUtils();
		bitmapUtils.getBitmapList(mBaseImages, BitmapUtils.ImageSize.Full,
				mNumImagesToRetrieve, new BitmapUtils.GetBitmapListener<List<Bitmap>>() {

					@Override
					public void onResponse(List<Bitmap> bitmaps) {
						mBaseImageBitmaps = bitmaps;
						mBaseImageView.setAdapter(new ImageAdapter(mContext,
								mBaseImageBitmaps));
						ProgressBar bar = (ProgressBar) findViewById(R.id.progressBarCircle);
						bar.setVisibility(ProgressBar.INVISIBLE);
					}
				});

	}

}
