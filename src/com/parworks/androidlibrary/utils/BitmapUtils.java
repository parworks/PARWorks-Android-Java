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

/**
 * A class for manipulating bitmaps.
 * @author Adam Hickey
 *
 */
public class BitmapUtils {

	private final int BITMAP_SAMPLE_SIZE = 8;

	/**
	 * Gets an image from a url and returns it as an inputstream.
	 * @param url The absolute url of the image
	 * @return an inputstream containing the image
	 */
	public InputStream getImageStream(String url) {
		try {
			return getImageStream(new URL(url));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Gets an image from a url and returns it as an inputstream.
	 * @param url The absolute url of the image
	 * @return an inputstream containing the image
	 */
	public InputStream getImageStream(URL stringUrl) {
		InputStream input = null;
		try {
			input = stringUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return input;
	}

	/**
	 * Converts an inputstream into a bitmap using the BITMAP_SAMPLE_SIZE;
	 * @param in the inputstream containing a bitmap
	 * @return the bitmap
	 */
	public Bitmap convertBitmap(InputStream in) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = BITMAP_SAMPLE_SIZE;
		return BitmapFactory.decodeStream(in, null, options);
	}
	
	/**
	 * Takes a url and returns the bitmap at that url
	 * @param url absolute url on the web
	 * @return the bitmap
	 */
	public Bitmap getBitmap(String url) {
		InputStream imageStream = getImageStream(url);
		return convertBitmap(imageStream);
	}

	/**
	 * Converts a bitmap into an inputstream
	 * @param image the bitmap
	 * @return the inputstream
	 */
	public InputStream convertBitmap(Bitmap image) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		image.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();
		return new ByteArrayInputStream(bitmapdata);
	}

	/**
	 * Asynchronously get a bitmap from a url
	 * @param url the absolute url on the web
	 * @param listener the callback listener
	 */
	public void getBitmap(final String url, final GetBitmapListener<Bitmap> listener) {
		new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... arg0) {
				InputStream imageStream = getImageStream(url);
				return convertBitmap(imageStream);	
				
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				listener.onResponse(result);
			}

		}.execute();

	}

	/**
	 * Enum to select the size of the image to download from the parworks api
	 * @author adam
	 *
	 */
	public enum ImageSize {
		Content, Gallery, Full

	}

	/**
	 * Listener for getBitmap
	 * @author adam
	 *
	 * @param <T>
	 */
	public interface GetBitmapListener<T> {
		public void onResponse(T bitmaps);
	}

	/**
	 * Takes a list of BaseImageInfo objects and returns a list of bitmaps. 
	 * @param baseImages The list of BaseImageInfo objects
	 * @param size an enum determining which size of bitmap to download
	 * @param listener callback listener
	 */
	public void getBitmapList(List<BaseImageInfo> baseImages, ImageSize size,
			GetBitmapListener<List<Bitmap>> listener) {
		getBitmapList(baseImages, size, null, listener);
	}

	
	/**
	 * Takes a list of BaseImageInfo objects and returns a list of bitmaps. 
	 * @param baseImages the list of BaseImageInfo objects
	 * @param size an enum determing which size of bitmap to download
	 * @param max optional parameter. The number of bitmaps to download. If max is null, all the base images will be downloaded.
	 * @param listener callback listener
	 */
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
					Bitmap bitMap = convertBitmap(inputStream);
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

	/**
	 * Returns the url of the base image with the given size
	 * @param info the base image info
	 * @param size the size to retrieve
	 * @return absolute url
	 */
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
