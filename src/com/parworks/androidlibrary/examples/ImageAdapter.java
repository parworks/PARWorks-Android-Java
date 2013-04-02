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
			convertView = mLayoutInflater.inflate(
					R.layout.gallery_thumbnail_parworksandroidlibrary, null);
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
