package com.parworks.androidlibrary.utils;

import android.os.AsyncTask;


public class AsyncGenericTask<T> extends AsyncTask<Void, Void, AsyncGenericTask.GenericResult<T>>{
	
	static class GenericResult<T> {
		public T result = null;
		public Exception error = null;
	}
	
	public interface GenericCallback<T> {
		public T toCall();
		public void onComplete(T result);
		public void onError(Exception error);
	}

	private GenericCallback<T> mCallback;
	
	public AsyncGenericTask(GenericCallback<T> callback) {
		mCallback = callback;
	}

	@Override
	protected GenericResult<T> doInBackground(Void... params) {
		GenericResult<T> genericResult = new GenericResult<T>();
		try {
			genericResult.result = mCallback.toCall();
		} catch(Exception error) {
			genericResult.error = error ;
		}
		return genericResult;
	}
	
	@Override
	protected void onPostExecute(GenericResult<T> genericResult) {
		super.onPostExecute(genericResult);
		if(genericResult.result != null ) {
			mCallback.onComplete(genericResult.result);
		} else {
			mCallback.onError(genericResult.error);
		}
		
	}
}
