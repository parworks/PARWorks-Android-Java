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
package com.parworks.androidlibrary.utils;

import android.os.AsyncTask;


public class GenericAsyncTask<T> extends AsyncTask<Void, Void, GenericAsyncTask.GenericResult<T>>{
	
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
	
	public GenericAsyncTask(GenericCallback<T> callback) {
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
