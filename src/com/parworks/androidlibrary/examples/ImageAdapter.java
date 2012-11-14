package com.parworks.androidlibrary.examples;

import java.util.List;

import com.parworks.androidlibrary.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	private List<Bitmap> mImages;

	public ImageAdapter(Context context, List<Bitmap> images) {
		super();
		mContext = context;
		mImages = images;

		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return mImages.size();
	}

	@Override
	public Object getItem(int position) {
		return mImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ThumbnailView thumbnailView;
		if (convertView == null) {
			thumbnailView = new ThumbnailView();
			convertView = mLayoutInflater.inflate(R.layout.gallery_thumbnail,
					null);
			thumbnailView.imageView = (ImageView) convertView
					.findViewById(R.id.imageViewThumbnail);
			convertView.setTag(thumbnailView);

		} else {
			thumbnailView = (ThumbnailView) convertView.getTag();
		}

		thumbnailView.imageView.setImageBitmap(mImages.get(position));

		return convertView;
	}

	private class ThumbnailView {
		private ImageView imageView;
	}
}
