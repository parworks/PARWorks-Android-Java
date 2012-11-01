/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.parworks.androidlibrary.ar;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

import android.os.AsyncTask;

import com.parworks.androidlibrary.response.ARResponseHandler;
import com.parworks.androidlibrary.response.ARResponseHandlerImpl;
import com.parworks.androidlibrary.response.AddBaseImageResponse;
import com.parworks.androidlibrary.response.AddSaveOverlayResponse;
import com.parworks.androidlibrary.response.AugmentImageResultResponse;
import com.parworks.androidlibrary.response.BasicResponse;
import com.parworks.androidlibrary.response.GetSiteInfoResponse;
import com.parworks.androidlibrary.response.InitiateBaseImageProcessingResponse;
import com.parworks.androidlibrary.response.SiteInfo;
import com.parworks.androidlibrary.response.SiteInfo.BimState;
import com.parworks.androidlibrary.response.SiteInfo.SiteState;
import com.parworks.androidlibrary.utils.AsyncHttpUtils;
import com.parworks.androidlibrary.utils.HttpCallback;
import com.parworks.androidlibrary.utils.HttpUtils;

public class ARSiteImpl implements ARSite {
	
	private String mId;
	private String mApiKey;
	private String mSignature;
		
	public ARSiteImpl(String siteId, String apiKey, String signature) {
		mId = siteId;
		mApiKey = apiKey;
		mSignature = signature;
	}

	
	
	/*
	 * 
	 *
	 * Async
	 * 
	 * 
	 */
	
	
	
	@Override
	public void getSiteInfo(final ARListener<SiteInfo> listener) {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.GET_SITE_INFO_PATH, params, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<SiteInfo> extractor = new PayloadExtractor<SiteInfo>() {

					@Override
					public SiteInfo extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						GetSiteInfoResponse siteInfoResponse = responseHandler.handleResponse(callbackResponse, GetSiteInfoResponse.class);
						if(siteInfoResponse.getSuccess() == true ) {
							return siteInfoResponse.getSite();
						} else {
							throw new ARException("Successfully communicated with the server but failed to get info.  Perhaps the site was deleted.");
						}
					}
					
				};
				ARResponse<SiteInfo> infoResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(infoResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
	}
	
	/**
	 * Makes a call to the asynchronous getState() method, then throws an ARException if the state is not the required state
	 * @param siteId the id of the site
	 * @param requiredState
	 */
	private void handleStateAsync(String siteId, State requiredState) {
		handleStateAsync(siteId,requiredState,null);
	}
	/**
	 * Makes a call to the asynchronous getState() method, then throws an ARException if the state is not the required state
	 * @param siteId
	 * @param firstPossibleState
	 * @param secondPossibleState
	 */
	private void handleStateAsync(String siteId, final State firstPossibleState, final State secondPossibleState) {
		getState(new ARListener<State>() {

			@Override
			public void handleResponse(ARResponse<State> resp) {
				State siteState = resp.getPayload();
				if( (siteState != firstPossibleState)||(siteState != secondPossibleState) ) {
					throw new ARException("State must be " + firstPossibleState + " or " + secondPossibleState);
				}
				
			}
			
		});
	}
	
	@Override
	public void addBaseImage(String filename, InputStream image, final ARListener<BaseImageInfo> listener) {
		handleStateAsync(mId,State.NEEDS_BASE_IMAGE_PROCESSING,State.NEEDS_BASE_IMAGE_PROCESSING);
	
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("filename", filename);
		
		MultipartEntity imageEntity = new MultipartEntity();
		InputStreamBody imageInputStreamBody = new InputStreamBody(image,filename);
		imageEntity.addPart("image", imageInputStreamBody);
		
		httpUtils.doPost(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.ADD_BASE_IMAGE_PATH, params, imageEntity, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<BaseImageInfo> extractor = new PayloadExtractor<BaseImageInfo>() {

					@Override
					public BaseImageInfo extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						AddBaseImageResponse addBaseImageResponse = responseHandler.handleResponse(callbackResponse, AddBaseImageResponse.class);
						if(addBaseImageResponse.getSuccess() == true ) {
							return new BaseImageInfo(addBaseImageResponse.getId());
						} else {
							throw new ARException("Successfully communicated with the server but failed to add the base image. Perhaps the site was deleted, or there was a problem with the image.");
						}
					}
					
				};
				ARResponse<BaseImageInfo> addArSiteResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(addArSiteResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
		
		
		
	}

	@Override
	public void processBaseImages(final ARListener<String> listener) {
		handleStateAsync(mId,State.NEEDS_BASE_IMAGE_PROCESSING);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.INITIATE_BASE_IMAGE_PROCESSING_PATH, params, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<String> extractor = new PayloadExtractor<String>() {

					@Override
					public String extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						InitiateBaseImageProcessingResponse processBaseImageResponse = responseHandler.handleResponse(callbackResponse, InitiateBaseImageProcessingResponse.class);
						if(processBaseImageResponse.getSuccess() == true ) {
							return new String(processBaseImageResponse.getJobId());
						} else {
							throw new ARException("Successfully communicated with the server but failed to initiate image processing. Perhaps the site was deleted.");
						}
					}
					
				};
				ARResponse<String> processImagesResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(processImagesResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
	}

	@Override
	public void getState(final ARListener<State> listener) {		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.GET_SITE_INFO_PATH, params, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<State> extractor = new PayloadExtractor<State>() {

					@Override
					public State extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						GetSiteInfoResponse siteInfoResponse = responseHandler.handleResponse(callbackResponse, GetSiteInfoResponse.class);
						if(siteInfoResponse.getSuccess() == true ) {
							SiteInfo siteInfo = siteInfoResponse.getSite();
							return determineSiteState(siteInfo.getBimState(), siteInfo.getSiteState());
						} else {
							throw new ARException("Successfully communicated with the server but failed to get state. Perhaps the site was deleted.");
						}
					}
					
				};
				ARResponse<State> getStateResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(getStateResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
	}

	@Override
	public void addOverlay(Overlay overlay, final ARListener<OverlayResponse> listener) {
		handleStateAsync(mId, State.NEEDS_OVERLAYS,State.READY_TO_AUGMENT_IMAGES);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("imgId", overlay.getImageId());
		params.put("name", overlay.getName());
		params.put("content", overlay.getDescription());
		for(Vertex v : overlay.getVertices()) {
			params.put("v", v.getxCoord()+","+v.getyCoord());
		}
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.ADD_OVERLAY_PATH, params, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<OverlayResponse> extractor = new PayloadExtractor<OverlayResponse>() {

					@Override
					public OverlayResponse extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						AddSaveOverlayResponse addOverlayResponse = responseHandler.handleResponse(callbackResponse, AddSaveOverlayResponse.class);
						if(addOverlayResponse.getSuccess() == true ) {
							return new OverlayResponse(addOverlayResponse.getId());
						} else {
							throw new ARException("Successfully communicated with the server but failed to add overlay.  Perhaps the site was deleted.");
						}
					}
					
				};
				ARResponse<OverlayResponse> overlayResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(overlayResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
	}

	@Override
	public void updateOverlay(String id, Overlay overlay, final ARListener<OverlayResponse> listener) {
		handleStateAsync(mId, State.READY_TO_AUGMENT_IMAGES);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("id", id);
		params.put("imgId", overlay.getImageId());
		params.put("name", overlay.getName());
		params.put("content", overlay.getDescription());
		for(Vertex v : overlay.getVertices()) {
			params.put("v", v.getxCoord()+","+v.getyCoord());
		}
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.SAVE_OVERLAY_PATH, params, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<OverlayResponse> extractor = new PayloadExtractor<OverlayResponse>() {

					@Override
					public OverlayResponse extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						AddSaveOverlayResponse saveOverlayResponse = responseHandler.handleResponse(callbackResponse, AddSaveOverlayResponse.class);
						if(saveOverlayResponse.getSuccess() == true ) {
							return new OverlayResponse(saveOverlayResponse.getId());
						} else {
							throw new ARException("Successfully communicated with the server but failed to update overlay.  Perhaps the site was deleted.");
						}
					}
					
				};
				ARResponse<OverlayResponse> overlayResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(overlayResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
	}

	@Override
	public void deleteOverlay(String id, final ARListener<Boolean> listener) {
		handleStateAsync(mId, State.READY_TO_AUGMENT_IMAGES);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("id", id);
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.REMOVE_OVERLAY_PATH, params, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<Boolean> extractor = new PayloadExtractor<Boolean>() {

					@Override
					public Boolean extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						BasicResponse deletedOverlayResponse = responseHandler.handleResponse(callbackResponse, BasicResponse.class);
						if(deletedOverlayResponse.getSuccess() == true ) {
							return true;
						} else {
							throw new ARException("Successfully communicated with the server but failed to delete overlay. Perhaps the site was deleted.");
						}
					}
					
				};
				ARResponse<Boolean> overlayResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(overlayResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
	}
	
	
	private void pollAugmentImageResult(final String imageId) {
		
		new AsyncTask<Void, Void, HttpResponse>() {

			@Override
			protected HttpResponse doInBackground(Void... arg0) {
				boolean finished = false;
				while(!finished) {
					Map<String,String> params = new HashMap<String,String>();
					params.put("site", mId);
					params.put("imgId", imageId);
					
					HttpUtils httpUtils = new HttpUtils();
					HttpResponse serverResponse = httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.AUGMENT_IMAGE_RESULT_PATH, params);
					
					HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
					
					ARResponseHandler responseHandler = new ARResponseHandlerImpl();
					AugmentImageResultResponse resultResponse = responseHandler.handleResponse(serverResponse, AugmentImageResultResponse.class);
					
					
					
				}
				
				
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected void onPostExecute(HttpResponse result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
		};
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		
	}

	@Override
	public void augmentImage(InputStream image, ARListener<AugmentedData> listener) {
		handleStateAsync(mId, State.READY_TO_AUGMENT_IMAGES);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		MultipartEntity imageEntity = new MultipartEntity();
		InputStreamBody imageInputStreamBody = new InputStreamBody(image,"image");
		imageEntity.addPart("image", imageInputStreamBody);
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doPost(mApiKey,mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.AUGMENT_IMAGE_PATH, params, imageEntity, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				
				
				
				
				
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);
				
			}
			
		});
		
		
	}

	@Override
	public void augmentImage(InputStream in, double lat, double lon,
			double compass, ARListener<AugmentedData> listener) {
		handleStateAsync(mId, State.READY_TO_AUGMENT_IMAGES);
		
		
	}

	@Override
	public void delete(final ARListener<Boolean> listener) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		AsyncHttpUtils httpUtils = new AsyncHttpUtils();
		httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.REMOVE_SITE_PATH, params, new HttpCallback() {

			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<Boolean> extractor = new PayloadExtractor<Boolean>() {

					@Override
					public Boolean extract(HttpResponse callbackResponse) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						BasicResponse deleteSiteResponse = responseHandler.handleResponse(callbackResponse, BasicResponse.class);
						if(deleteSiteResponse.getSuccess() == true ) {
							return true;
						} else {
							throw new ARException("Successfully communicated with the server but failed to get info.  Perhaps the site no longer exists.");
						}
					}
					
				};
				ARResponse<Boolean> deleteResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(deleteResponse);
			}

			@Override
			public void onError(Exception e) {
				throw new ARException(e);				
			}
			
		});
		
	}
	
	
	
	/*
	 * 
	 * 
	 * Sync
	 * 
	 * 
	 */

	@Override
	public BaseImageInfo addBaseImage(String filename, InputStream image) {
		handleStateSync(mId, State.NEEDS_MORE_BASE_IMAGES, State.NEEDS_BASE_IMAGE_PROCESSING);
		//make httputils
		HttpUtils httpUtils = new HttpUtils();
		
		//make query string
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("filename", filename);
		
		//make entity
		MultipartEntity imageEntity = new MultipartEntity();
		InputStreamBody imageInputStreamBody = new InputStreamBody(image,filename);
		imageEntity.addPart("image", imageInputStreamBody);
		
		//do post
		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.ADD_BASE_IMAGE_PATH, imageEntity, params);
		
		//handle status code
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		//parse response
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AddBaseImageResponse addBaseImageResponse = responseHandler.handleResponse(serverResponse, AddBaseImageResponse.class);
		
		//return baseimageinfo
		if(addBaseImageResponse.getSuccess() == true) {
			return new BaseImageInfo(addBaseImageResponse.getId());
		} else {
			throw new ARException("Successfully communicated with the server but failed to add the base image. Perhaps the site does not exist, or there is a problem with the image.");
		}
			
	}

	@Override
	public State processBaseImages() {
		handleStateSync(mId, State.NEEDS_BASE_IMAGE_PROCESSING);
		HttpUtils httpUtils = new HttpUtils();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
				
		HttpResponse serverResponse = httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.INITIATE_BASE_IMAGE_PROCESSING_PATH , params);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		InitiateBaseImageProcessingResponse initProcessingResponse = responseHandler.handleResponse(serverResponse, InitiateBaseImageProcessingResponse.class);
		
		if( initProcessingResponse.getSuccess() == true) {
			return State.NEEDS_OVERLAYS;
		} else {
			throw new ARException("Successfully communicated with the server but failed to process the base images. Perhaps the site was deleted.");
		}
	}

	@Override
	public State getState() {
		//make httputils
		HttpUtils httpUtils = new HttpUtils();
		
		//make query string
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
			
		//do post
		HttpResponse serverResponse = httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.GET_SITE_INFO_PATH,params);
		
		//handle status code
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		//parse response
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		GetSiteInfoResponse siteInfoResponse = responseHandler.handleResponse(serverResponse, GetSiteInfoResponse.class);
		
		//return state
		if(siteInfoResponse.getSuccess() == true) {
			SiteInfo siteInfo = siteInfoResponse.getSite();
			return determineSiteState(siteInfo.getBimState(),siteInfo.getSiteState());			
			
		} else {
			throw new ARException("Successfully communicated with the server but failed to get site info. Perhaps the site no longer exists.");
		}
		
	}
	
	private State determineSiteState(BimState bimState, SiteState siteState) {
		
		return null;
	}

	@Override
	public OverlayResponse addOverlay(Overlay overlay) {
		handleStateSync(mId,State.NEEDS_OVERLAYS, State.READY_TO_AUGMENT_IMAGES);
		HttpUtils httpUtils = new HttpUtils();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("imgId", overlay.getImageId());
		params.put("name", overlay.getName());
		params.put("content", overlay.getDescription());
		
		List<Vertex> vertices = overlay.getVertices();
		MultipartEntity entity = new MultipartEntity();
		
		for(Vertex currentVertex : vertices) {
			try {
				entity.addPart("v", new StringBody(currentVertex.getxCoord() +","+currentVertex.getyCoord()));
			} catch (UnsupportedEncodingException e) {
				throw new ARException(e);
			}
		}
		
		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.ADD_OVERLAY_PATH, entity, params);
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AddSaveOverlayResponse addOverlayResponse = responseHandler.handleResponse(serverResponse, AddSaveOverlayResponse.class);
		
		if(addOverlayResponse.getSuccess() == true) {
			return new OverlayResponse(addOverlayResponse.getId());
		} else {
			throw new ARException("Successfully communicated with the server, but failed to add the overlay. Perhaps the site no longer exists, or there was a problem with the overlay.");
		}
	}

	@Override
	public OverlayResponse updateOverlay(OverlayResponse overlayToUpdate, Overlay newOverlay) {
		handleStateSync(mId,State.READY_TO_AUGMENT_IMAGES);
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("id", overlayToUpdate.getOverlayId());
		params.put("imgId", newOverlay.getImageId());
		params.put("name", newOverlay.getName());
		params.put("content", newOverlay.getDescription());
		
		
		List<Vertex> vertices = newOverlay.getVertices();
		MultipartEntity entity = new MultipartEntity();
		
		for(Vertex currentVertex : vertices) {
			try {
				entity.addPart("v",new StringBody(currentVertex.getxCoord()+","+currentVertex.getyCoord()));
			} catch (UnsupportedEncodingException e) {
				throw new ARException(e);
			}
		}
		
		HttpUtils httpUtils = new HttpUtils();
		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.SAVE_OVERLAY_PATH, entity, params);
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AddSaveOverlayResponse saveOverlayResponse = responseHandler.handleResponse(serverResponse, AddSaveOverlayResponse.class);
		
		if(saveOverlayResponse.getSuccess() == true) {
			return new OverlayResponse(saveOverlayResponse.getId());
		} else {
			throw new ARException("Successfully communicated with the server, but failed to update the overlay. Perhaps the site no longer exists, or there was a problem with the overlay.");
		}
	}

	@Override
	public void deleteOverlay(String id) {
		handleStateSync(mId,State.READY_TO_AUGMENT_IMAGES);
		HttpUtils httpUtils = new HttpUtils();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		params.put("id", id);
		
		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.REMOVE_OVERLAY_PATH, params);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		BasicResponse deleteOverlayResponse = responseHandler.handleResponse(serverResponse, BasicResponse.class);
		
		if(deleteOverlayResponse.getSuccess() == false) {
			throw new ARException("Successfully communicated with the server, but the overlay was not deleted. Perhaps it does not exist.");
		} 
		
	}

	@Override
	public AugmentedData augmentImage(InputStream in) {
		handleStateSync(mId,State.READY_TO_AUGMENT_IMAGES);
		return null;
//		HttpUtils httpUtils = new HttpUtils();
//		
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("site", mSiteInfo.getId());
//		
//		MultipartEntity imageEntity = new MultipartEntity();
//		InputStreamBody imageInputStreamBody = new InputStreamBody(in,"image");
//		imageEntity.addPart("image", imageInputStreamBody);
//		
//		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.AUGMENT_IMAGE_PATH, imageEntity, params);
//		
//		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
//		
//		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
//		AugmentImageResponse augmentImageResponse = responseHandler.handleResponse(serverResponse, AugmentImageResponse.class);
//		
//		if(augmentImageResponse.getSuccess() == false) {
//			throw new ARException("Successfully communicated with the server but failed to augment the image.");
//		}
//		
//		String imgId = augmentImageResponse.getImgId();
		
		
		
		
	}

	@Override
	public AugmentedData augmentImage(InputStream in, long lat, long lon) {
		
		handleStateSync(mId,State.READY_TO_AUGMENT_IMAGES);
		
		return null;
	}

	@Override
	public void delete() {
		HttpUtils httpUtils = new HttpUtils();
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		HttpResponse serverResponse = httpUtils.doGet(mApiKey, mSignature, HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.REMOVE_SITE_PATH, params);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		BasicResponse deleteSiteResponse = responseHandler.handleResponse(serverResponse, BasicResponse.class);
		
		if(deleteSiteResponse.getSuccess() == false) {
			throw new ARException("Successfully communicated with the server, but was unable to delete the site. Perhaps the site no longer exists.");
		}
		
	}



	@Override
	public SiteInfo getSiteInfo() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mId);
		
		HttpUtils httpUtils = new HttpUtils();
		HttpResponse serverResponse = httpUtils.doGet(mApiKey,mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.GET_SITE_INFO_PATH, params);
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		GetSiteInfoResponse getSiteInfoResponse = responseHandler.handleResponse(serverResponse, GetSiteInfoResponse.class);
		
		if(getSiteInfoResponse.getSuccess() == true) {
			SiteInfo siteInfo = getSiteInfoResponse.getSite();
			return siteInfo;
		} else {
			throw new ARException("Successfully communicated with the server, but was unable to get site info. Perhaps the site no longer exists.");
		}
		
	}
	
	/**
	 * Makes a call to the synchronous getState() method, then throws an ARException if the state is not the required state
	 * @param siteId the id of the site
	 * @param requiredState
	 */
	private void handleStateSync(String siteId, State requiredState) {
		handleStateSync(siteId,requiredState,null);
	}
	/**
	 * Makes a call to the synchronous getState() method, then throws an ARException if the state is not the required state
	 * @param siteId
	 * @param firstPossibleState
	 * @param secondPossibleState
	 */
	private void handleStateSync(String siteId, final State firstPossibleState, final State secondPossibleState) {
		State siteState = getState();
		if( (siteState != firstPossibleState)||(siteState != secondPossibleState) ) {
			throw new ARException("State must be " + firstPossibleState + " or " + secondPossibleState);
		}
	}



	
	

	
	
	



	

}
