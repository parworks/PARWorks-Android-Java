package com.parworks.androidlibrary.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.parworks.androidlibrary.response.BaseImageInfo;

public class BitmapUtils {

	private final int BITMAP_SAMPLE_SIZE = 8;

	public InputStream getImageStream(String url) {
		try {
			return getImageStream(new URL(url));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public InputStream getImageStream(URL stringUrl) {
		InputStream input = null;
		try {
			input = stringUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return input;
	}

	public Bitmap getBitmap(InputStream in) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = BITMAP_SAMPLE_SIZE;
		return BitmapFactory.decodeStream(in, null, options);
	}

	public Bitmap getBitmap(String url) {
		InputStream imageStream = getImageStream(url);
		return getBitmap(imageStream);
	}

	public InputStream convertBitmap(Bitmap image) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		image.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();
		return new ByteArrayInputStream(bitmapdata);
	}

	public void getBitmap(final String url, final GetBitmapListener<Bitmap> listener) {
		new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... arg0) {
				InputStream imageStream = getImageStream(url);
				return getBitmap(imageStream);	
				
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				listener.onResponse(result);
			}

		}.execute();

	}

	public enum ImageSize {
		Content, Gallery, Full

	}

	public interface GetBitmapListener<T> {
		public void onResponse(T bitmaps);
	}

	public void getBitmapList(List<BaseImageInfo> baseImages, ImageSize size,
			GetBitmapListener<List<Bitmap>> listener) {
		getBitmapList(baseImages, size, null, listener);
	}

	public void getBitmapList(final List<BaseImageInfo> baseImages,
			final ImageSize size, final Integer max,
			final GetBitmapListener<List<Bitmap>> listener) {

		final int numImagesToDownload;
		if (max == null) {
			numImagesToDownload = baseImages.size();
		} else {
			numImagesToDownload = max;
		}
		new AsyncTask<Void, Void, List<Bitmap>>() {

			@Override
			protected List<Bitmap> doInBackground(Void... arg0) {

				List<Bitmap> bitMaps = new ArrayList<Bitmap>();

				for (int i = 0; i < numImagesToDownload; ++i) {
					String url = getImageUrl(baseImages.get(i), size);
					InputStream inputStream = getImageStream(url);
					Bitmap bitMap = getBitmap(inputStream);
					bitMaps.add(bitMap);

				}
				return bitMaps;
			}

			@Override
			protected void onPostExecute(List<Bitmap> result) {
				listener.onResponse(result);
			}

		}.execute();
	}

	public String getImageUrl(BaseImageInfo info, ImageSize size) {
		if (size == ImageSize.Content) {
			return info.getContentSize();
		} else if (size == ImageSize.Full) {
			return info.getFullSize();
		} else {
			return info.getGallerySize();
		}
	}

}
