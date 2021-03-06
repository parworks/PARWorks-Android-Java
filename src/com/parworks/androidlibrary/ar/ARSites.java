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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.parworks.androidlibrary.response.ARResponseHandler;
import com.parworks.androidlibrary.response.ARResponseHandlerImpl;
import com.parworks.androidlibrary.response.ARResponseUtils;
import com.parworks.androidlibrary.response.AugmentImageGroupResponse;
import com.parworks.androidlibrary.response.AugmentImageResultResponse;
import com.parworks.androidlibrary.response.BasicResponse;
import com.parworks.androidlibrary.response.GetSiteInfoResponse;
import com.parworks.androidlibrary.response.ListUserSitesResponse;
import com.parworks.androidlibrary.response.NearbySitesResponse;
import com.parworks.androidlibrary.response.OverlayAugmentResponse;
import com.parworks.androidlibrary.response.SiteImageBundle;
import com.parworks.androidlibrary.response.SiteInfo;
import com.parworks.androidlibrary.response.SiteInfoOverview;
import com.parworks.androidlibrary.response.StringListResponse;
import com.parworks.androidlibrary.response.TrendingSitesResponse;
import com.parworks.androidlibrary.utils.GenericAsyncTask;
import com.parworks.androidlibrary.utils.GenericAsyncTask.GenericCallback;
import com.parworks.androidlibrary.utils.HMacShaPasswordEncoder;
import com.parworks.androidlibrary.utils.HttpUtils;

/**
 * Used for Synchronously and Asynchronously finding, managing, and creating
 * ARSites
 * 
 * @author Jules White, Adam Hickey, Yu Sun
 * 
 */
public class ARSites {

	private String mApiKey;
	private String mSignature;
	private String mTime;
	
	private static final String TAG = ARSites.class.getName();

	public ARSites(String apiKey, String secretKey) {

		mApiKey = apiKey;

		HMacShaPasswordEncoder encoder = new HMacShaPasswordEncoder(256, true);
		mTime = "" + System.currentTimeMillis();
		mSignature = encoder.encodePassword(secretKey, mTime);
	}
	
	/**
	 * Synchronously augment an image towards a list of sites
	 * 
	 * @param image
	 *            an inputstream containing the image
	 * @return the augmented data
	 */
	public AugmentedData augmentImageGroup(List<String> sites, InputStream image) {
		Map<String, String> params = new HashMap<String, String>();
		
		MultipartEntity imageEntity = new MultipartEntity();
		InputStreamBody imageInputStreamBody = new InputStreamBody(image,
				"image");
		imageEntity.addPart("image", imageInputStreamBody);
		
		for(String siteId : sites) {
			try {
				imageEntity.addPart("site", new StringBody(siteId));
			} catch (UnsupportedEncodingException e) {
				throw new ARException(e);
			}
		}

		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse = httpUtils.doPost(
				HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.AUGMENT_IMAGE_GROUP_PATH,
				imageEntity, params);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AugmentImageGroupResponse augmentImageGroupResponse = responseHandler
				.handleResponse(serverResponse, AugmentImageGroupResponse.class);
		
		if (augmentImageGroupResponse.getSuccess() == false) {
			throw new ARException(
					"Successfully communicated with the server, failed to augment the image. Perhaps the site does not exist or has no overlays.");
		}
		
//		List<AugmentedData> result = new ArrayList<AugmentedData>();
//		for(SiteImageBundle bundle : augmentImageGroupResponse.getCandidates()) {
//			AugmentedData augmentedImage = null;
//			while (augmentedImage == null) {
//				augmentedImage = getAugmentResult(bundle.getSite(), bundle.getImgId());
//			}
//			result.add(augmentedImage);
//		}
		List<AugmentedData> result = getAugmentedImageGroupResult(augmentImageGroupResponse.getSitesToCheck(),augmentImageGroupResponse.getImgId());
		
		// combine different results
		AugmentedData finalData = null;
		for(AugmentedData data : result) {
			if (data.isLocalization()) {
				if (finalData == null) {
					finalData = data;
				} else {
					finalData.getOverlays().addAll(data.getOverlays());
				}
			}
		}
		
		return finalData;
	}
	
	private List<AugmentedData> getAugmentedImageGroupResult(final List<String> sites, String imageId) {
		Log.d("PARWORKS ANDROID LIBRARY","getAugmentedImageGroupResult");
		Map<String, String> params = new HashMap<String, String>();
		params.put("imgId", imageId);
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse = httpUtils.doGetWithSiteArray(HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.AUGMENT_IMAGE_GROUP_RESULT_PATH,sites,params);
		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());
		List<AugmentImageResultResponse> augmentedImageResults = new ArrayList<AugmentImageResultResponse>();
		try {
		  JsonFactory f = new JsonFactory();
		  JsonParser jp = f.createJsonParser(ARResponseUtils.convertHttpResponseToString(serverResponse));
		  // advance stream to START_ARRAY first:
		  jp.nextToken();
		  // and then each time, advance to opening START_OBJECT
		  while (jp.nextToken() == JsonToken.START_OBJECT) {
			  Log.d(TAG,"Parsing json token");
			  ARResponseHandler responseHandler = new ARResponseHandlerImpl();
				AugmentImageResultResponse augmentImageResult = responseHandler
						.handleResponse(jp, AugmentImageResultResponse.class);
			  augmentedImageResults.add(augmentImageResult);
		  }
		} catch(Exception e) {
			throw new ARException("Failed to parse json.",e);
		}
		
		List<AugmentedData> allAugmentedData = new ArrayList<AugmentedData>();
		for(AugmentImageResultResponse response : augmentedImageResults) {
			allAugmentedData.add(convertAugmentResultResponse(imageId, response));
		}
		return allAugmentedData;
	}
	
	/**
	 * Synchronously augment an image towards a list of sites
	 * 
	 * @param image
	 *            an inputstream containing the image
	 * @return the augmented data
	 */
	public void augmentImageGroup(final List<String> sites, final InputStream image, 
			final ARListener<AugmentedData> listener, final ARErrorListener onErrorListener) {
		GenericCallback<AugmentedData> genericCallback = new GenericCallback<AugmentedData>() {
			@Override
			public AugmentedData toCall() {
				return augmentImageGroup(sites, image);
			}

			@Override
			public void onComplete(AugmentedData result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}
		};
		
		GenericAsyncTask<AugmentedData> asyncTask = new GenericAsyncTask<AugmentedData>(genericCallback);
		asyncTask.execute();
	}
	
	public AugmentedData getAugmentResult(String mId, String imgId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("imgId", imgId);
		params.put("site", mId);

		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse = httpUtils.doGet(
				HttpUtils.PARWORKS_API_BASE_URL
						+ HttpUtils.AUGMENT_IMAGE_RESULT_PATH, params);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		if (serverResponse.getStatusLine().getStatusCode() == 204) {
			return null;
		}

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AugmentImageResultResponse result = responseHandler.handleResponse(
				serverResponse, AugmentImageResultResponse.class);

		return convertAugmentResultResponse(imgId, result);
	}
	
	private AugmentedData convertAugmentResultResponse(String imgId,
			AugmentImageResultResponse result) {
		List<OverlayAugmentResponse> overlayResponses = result.getOverlays();
		List<Overlay> overlays = new ArrayList<Overlay>();

		for (OverlayAugmentResponse overlayResponse : overlayResponses) {
			overlays.add(makeOverlay(overlayResponse, imgId));
		}

		AugmentedData augmentedData = new AugmentedData(result.getFov(),
				result.getFocalLength(), result.getScore(),
				result.isLocalization(), overlays);
		return augmentedData;
	}
	
	private Overlay makeOverlay(OverlayAugmentResponse overlayResponse,
			String imgId) {
		Overlay overlay = new OverlayImpl(imgId, overlayResponse.getName(),
				overlayResponse.getDescription(),
				parseVertices(overlayResponse.getVertices()));
		return overlay;

	}
	
	private List<Vertex> parseVertices(String serverOutput) {
		String[] points = serverOutput.split(",");

		List<Vertex> vertices = new ArrayList<Vertex>();
		for (int i = 0; i < points.length; i += 3) {
			float xCoord = Float.parseFloat(points[i]);
			float yCoord = Float.parseFloat(points[i + 1]);
			float zCoord = Float.parseFloat(points[i + 2]);
			vertices.add(new Vertex(xCoord, yCoord, zCoord));
		}
		return vertices;
	}

	/**
	 * Asynchronously create an ARSite
	 * 
	 * @param id
	 *            the id of the site. Will be used for all site accesses
	 * @param name
	 *            the name of the site
	 * @param lon
	 *            longitude of the site
	 * @param lat
	 *            latitude of the site
	 * @param desc
	 *            site description
	 * @param feature
	 *            site feature
	 * @param channel
	 *            site channel
	 * @param listener
	 *            callback that provides an ARSite object when the call
	 *            completes
	 */
	public void create(final String id, final String name, final double lon,
			final double lat, final String desc, final String feature,
			final String channel, final ARListener<ARSite> listener, final ARErrorListener onErrorListener) {
		
		GenericCallback<ARSite> genericCallback = new GenericCallback<ARSite>() {
			@Override
			public ARSite toCall() {
				return create(id, name, lon, lat, desc, feature, channel);
			}

			@Override
			public void onComplete(ARSite result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				onErrorListener.handleError(error);			
			}			
		};
		
		GenericAsyncTask<ARSite> asyncTask = new GenericAsyncTask<ARSite>(genericCallback);
		asyncTask.execute();
	}

	/**
	 * Asynchronously create an ARSite
	 * 
	 * @param id
	 *            the id of the site. Will be used for all site accesses
	 * @param desc
	 *            site description
	 * @param channel
	 *            site channel
	 * @param listener
	 *            callback that provides an ARSite object when the call
	 *            completes
	 */
	public void create(final String id, final String desc,
			final String channel, final ARListener<ARSite> listener,
			final ARErrorListener onErrorListener) {
		GenericCallback<ARSite> genericCallback = new GenericCallback<ARSite>() {
			@Override
			public ARSite toCall() {
				return create(id, desc, channel);
			}

			@Override
			public void onComplete(ARSite result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				onErrorListener.handleError(error);			
			}			
		};
		
		GenericAsyncTask<ARSite> asyncTask = new GenericAsyncTask<ARSite>(genericCallback);
		asyncTask.execute();
	}

	/**
	 * Asynchronously finds the site nearest a given latitude and longitude.
	 * This method does not specify the maximum number of sites, and so the
	 * default is one. Use near(double, double, int, double, ARListener) to
	 * retrieve multiple sites.
	 * 
	 * @param lat
	 *            latitude
	 * @param lon
	 *            longitude
	 * @param sites
	 *            the callback which provides a list of sites nearest the
	 *            coordinates
	 */
	public void near(double lat, double lon, ARListener<List<ARSite>> sites, ARErrorListener onErrorListener) {
		near(Double.toString(lat), Double.toString(lon), "", "", sites, onErrorListener);
	}

	/**
	 * Asynchronously finds the sites nearest a set of coordinates
	 * 
	 * @param lat
	 *            latitude
	 * @param lon
	 *            longitude
	 * @param max
	 *            the maximum number of sites to return.
	 * @param radius
	 *            the radius in which to search
	 * @param sites
	 *            the callback which provides a list of the nearest ARSites
	 */
	public void near(double lat, double lon, int max, double radius,
			ARListener<List<ARSite>> sites, ARErrorListener onErrorListener) {
		near(Double.toString(lat), Double.toString(lon), Integer.toString(max),
				Double.toString(radius), sites, onErrorListener);
	}

	private void near(final String lat, final String lon, final String max, final String radius,
			final ARListener<List<ARSite>> listener, final ARErrorListener onErrorListener) {		
		GenericCallback<List<ARSite>> genericCallback = new GenericCallback<List<ARSite>>() {
			@Override
			public List<ARSite> toCall() {
				return near(lat, lon, max, radius);
			}

			@Override
			public void onComplete(List<ARSite> result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<List<ARSite>> asyncTask = new GenericAsyncTask<List<ARSite>>(genericCallback);
		asyncTask.execute();
	}
	
	
	public void nearInfo(double lat, double lon, int max, double radius,
			ARListener<List<SiteInfo>> sites, ARErrorListener onErrorListener) {
		nearInfo(Double.toString(lat), Double.toString(lon), Integer.toString(max),
				Double.toString(radius), sites, onErrorListener);
	}

	private void nearInfo(final String lat, final String lon, final String max, final String radius,
			final ARListener<List<SiteInfo>> listener, final ARErrorListener onErrorListener) {		
		GenericCallback<List<SiteInfo>> genericCallback = new GenericCallback<List<SiteInfo>>() {
			@Override
			public List<SiteInfo> toCall() {
				return nearInfo(lat, lon, max, radius);
			}

			@Override
			public void onComplete(List<SiteInfo> result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<List<SiteInfo>> asyncTask = new GenericAsyncTask<List<SiteInfo>>(genericCallback);
		asyncTask.execute();
	}

	/**
	 * Asynchronously get a previously created site
	 * 
	 * @param id
	 *            the id of the site
	 * @param listener
	 *            the callback which provides the ARSite once the call completes
	 */
	public void getExisting(final String id, final ARListener<ARSite> listener,
			final ARErrorListener onErrorListener) {
		GenericCallback<ARSite> genericCallback = new GenericCallback<ARSite>() {
			@Override
			public ARSite toCall() {
				return getExisting(id);
			}

			@Override
			public void onComplete(ARSite result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<ARSite> asyncTask = new GenericAsyncTask<ARSite>(genericCallback);
		asyncTask.execute();
	}
	
	/**
	 * Asynchronously get a SiteInfo
	 * 
	 * @param id
	 *            the id of the site
	 * @param listener
	 *            the callback which provides the ARSite once the call completes
	 */
	public void getSiteInfo(final String id, final ARListener<SiteInfo> listener,
			final ARErrorListener onErrorListener) {
		GenericCallback<SiteInfo> genericCallback = new GenericCallback<SiteInfo>() {
			@Override
			public SiteInfo toCall() {
				return getSiteInfo(id);
			}

			@Override
			public void onComplete(SiteInfo result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<SiteInfo> asyncTask = new GenericAsyncTask<SiteInfo>(genericCallback);
		asyncTask.execute();
	}
	
	/**
	 * Asynchronously get all sites owned by the users
	 *
	 * @param listener
	 *            the callback which provides all the ARSite once the call completes
	 */
	public void getUserSites(final ARListener<List<ARSite>> listener, final ARErrorListener onErrorListener) {
		GenericCallback<List<ARSite>> genericCallback = new GenericCallback<List<ARSite>>() {
			@Override
			public List<ARSite> toCall() {
				return getUserSites();
			}

			@Override
			public void onComplete(List<ARSite> result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<List<ARSite>> asyncTask = new GenericAsyncTask<List<ARSite>>(genericCallback);
		asyncTask.execute();
	}
	
	/*
	 * synchronous methods
	 */
	
	/**
	 * Synchronously get all sites owned by the users
	 * 
	 * @return the list of ARSite 
	 */
	public List<ARSite> getUserSites() {
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse;
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.USER_SITE_LIST_PATH);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		ListUserSitesResponse listUserSitesResponse = responseHandler.handleResponse(
				serverResponse, ListUserSitesResponse.class);
		
		if (listUserSitesResponse.getSites() != null) {
			List<ARSite> userSites = new ArrayList<ARSite>();
			for(String siteId : listUserSitesResponse.getSites()) {
				ARSite newSite = new ARSiteImpl(siteId,
						mApiKey, mTime, mSignature);
				userSites.add(newSite);
			}
			return userSites;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but failed to get the user's sites. The most likely cause is that the given credentials does not exist.");
		}
	}
	
	/**
	 * Synchronously get a previously created site
	 * 
	 * @param id
	 *            site id
	 * @return the ARSite
	 */
	public ARSite getExisting(String id) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("site", id);

		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse;
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.GET_SITE_INFO_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		GetSiteInfoResponse getSiteResponse = responseHandler.handleResponse(
				serverResponse, GetSiteInfoResponse.class);

		if (getSiteResponse.getSuccess() == true) {
			ARSite newSite = new ARSiteImpl(getSiteResponse.getSite().getId(),
					mApiKey, mTime, mSignature);
			return newSite;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but failed to get siteinfo. The most likely cause is that a site with the specificed ID does not exist.");
		}
	}
	
	/**
	 * Synchronously get the SiteInfo
	 * 
	 * @param id
	 *            site id
	 * @return the ARSite
	 */
	public SiteInfo getSiteInfo(String id) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("site", id);

		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse;
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.GET_SITE_INFO_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		GetSiteInfoResponse getSiteResponse = responseHandler.handleResponse(
				serverResponse, GetSiteInfoResponse.class);

		if (getSiteResponse.getSuccess() == true) {
			return getSiteResponse.getSite();
		} else {
			throw new ARException(
					"Successfully communicated with the server, but failed to get siteinfo. The most likely cause is that a site with the specificed ID does not exist.");
		}
	}

	/**
	 * Synchronously create an ARSite
	 * 
	 * @param id
	 *            the site id. Will be used for accessing the site.
	 * @param desc
	 *            the site description
	 * @param channel
	 *            the site channel
	 * @return the newly created ARSite
	 */
	public ARSite create(String id, String desc, String channel) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("id", id);
		parameterMap.put("description", desc);
		parameterMap.put("channel", channel);

		return create(id, parameterMap);
	}

	/**
	 * Synchronously create an ARSite
	 * 
	 * @param id
	 *            the site id. Will be used for accessing the site.
	 * @param name
	 *            the name of the site
	 * @param lon
	 *            longitude
	 * @param lat
	 *            latitude
	 * @param desc
	 *            description
	 * @param feature
	 *            site feature
	 * @param channel
	 *            site channel
	 * @return the newly created ARSite
	 */
	public ARSite create(String id, String name, double lon, double lat,
			String desc, String feature, String channel) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("id", id);
		parameterMap.put("name", name);
		parameterMap.put("lon", Double.toString(lon));
		parameterMap.put("lat", Double.toString(lat));
		parameterMap.put("description", desc);
		parameterMap.put("feature", feature);
		parameterMap.put("channel", channel);

		return create(id, parameterMap);
	}

	private ARSite create(String id, Map<String, String> parameterMap) {
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse;
		parameterMap.put("id", id);
		serverResponse = httpUtils.doPost(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.ADD_SITE_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		BasicResponse addSiteResponse = responseHandler.handleResponse(
				serverResponse, BasicResponse.class);

		if (addSiteResponse.getSuccess() == true) {
			return new ARSiteImpl(id, mApiKey, mTime, mSignature);
		} else {
			throw new ARException(
					"Successfully communicated with the server, but failed to create a new site. The site id could already be in use, or a problem occurred.");
		}
	}

	/**
	 * Synchronously finds the site closest to the given latitude and longitude.
	 * 
	 * @param lat
	 *            latitude
	 * @param lon
	 *            longitude
	 * @return a list containing the closest ARSite to the given coordinates
	 */
	public List<ARSite> near(double lat, double lon) {
		return near(Double.toString(lat), Double.toString(lon), "", "");
	}

	/**
	 * Synchronously finds the closest sites to the given coordinates.
	 * 
	 * @param lat
	 *            latitude
	 * @param lon
	 *            longitude
	 * @param max
	 *            the maximum number of sites to return
	 * @param radius
	 *            the distance from the coordinates in which to search
	 * @return a list of the nearby sites
	 */
	public List<ARSite> near(double lat, double lon, int max, double radius) {
		return near(Double.toString(lat), Double.toString(lon),
				Integer.toString(max), Double.toString(radius));
	}

	private List<ARSite> near(String lat, String lon, String max, String radius) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("lat", lat);
		parameterMap.put("lon", lon);
		parameterMap.put("max", max);
		parameterMap.put("radius", radius);

		HttpResponse serverResponse;
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.NEARBY_SITE_PATH, parameterMap);
		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		NearbySitesResponse nearbySites = responseHandler.handleResponse(
				serverResponse, NearbySitesResponse.class);

		if (nearbySites.getSuccess() == true) {
			List<SiteInfo> sitesInfo = nearbySites.getSites();
			List<ARSite> nearbySitesList = new ArrayList<ARSite>();
			for (SiteInfo info : sitesInfo) {
				nearbySitesList.add(new ARSiteImpl(info.getId(), mApiKey,
						mTime, mSignature));
			}
			return nearbySitesList;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but the server was unsuccessful in handling the request.");
		}

	}
	
	public List<SiteInfo> nearInfo(double lat, double lon, int max, double radius) {
		return nearInfo(Double.toString(lat), Double.toString(lon),
				Integer.toString(max), Double.toString(radius));
	}
	private List<SiteInfo> nearInfo(String lat, String lon, String max, String radius) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("lat", lat);
		parameterMap.put("lon", lon);
		parameterMap.put("max", max);
		parameterMap.put("radius", radius);

		HttpResponse serverResponse;
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.NEARBY_SITE_PATH, parameterMap);
		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		NearbySitesResponse nearbySites = responseHandler.handleResponse(
				serverResponse, NearbySitesResponse.class);

		if (nearbySites.getSuccess() == true) {
			List<SiteInfo> sitesInfo = nearbySites.getSites();
			return sitesInfo;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but the server was unsuccessful in handling the request.");
		}
	}
	
	/**
	 * Synchronously get all tags available 
	 */
	public List<String> getAllTags() {
		HttpResponse serverResponse;
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.LIST_ALL_TAGS_PATH);
		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		StringListResponse allTagsResponse = responseHandler.handleResponse(
				serverResponse, StringListResponse.class);

		if (allTagsResponse != null) {			
			return allTagsResponse;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but the server was unsuccessful in handling the request.");
		}
	}
	
	/**
	 * Asynchronously get all tags available 
	 */
	public void getAllTags(final ARListener<List<String>> listener, final ARErrorListener onErrorListener) {
		GenericCallback<List<String>> genericCallback = new GenericCallback<List<String>>() {
			@Override
			public List<String> toCall() {
				return getAllTags();
			}

			@Override
			public void onComplete(List<String> result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<List<String>> asyncTask = new GenericAsyncTask<List<String>>(genericCallback);
		asyncTask.execute();
	}
	
	/**
	 * Send a metrics when a user clicks an overlay 
	 * 
	 * @param siteId
	 * @param overlayName
	 * @param userId
	 */
	public void addOverlayClick(String siteId, String overlayName, String userId) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("site", siteId);
		parameterMap.put("overlayName", overlayName);
		parameterMap.put("userId", userId);
		
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse;
		serverResponse = httpUtils.doPost(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.ADD_OVERLAY_CLICK_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		BasicResponse addSiteResponse = responseHandler.handleResponse(
				serverResponse, BasicResponse.class);

		if (!addSiteResponse.getSuccess()) {
			throw new ARException(
					"Successfully communicated with the server, but failed to create a new site. The site id could already be in use, or a problem occurred.");
		}
	}
	
	public void addOverlayClick(final String siteId, final String overlayName, final String userId,
			final ARListener<Void> listener, final ARErrorListener onErrorListener) {
		GenericCallback<Void> genericCallback = new GenericCallback<Void>() {

			@Override
			public Void toCall() {
				addOverlayClick(siteId, overlayName, userId);
				return null;
			}

			@Override
			public void onComplete(Void result) {
				if (result != null) {
					listener.handleResponse(result);				
				}
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}
		};
		
		GenericAsyncTask<Void> asyncTask = new GenericAsyncTask<Void>(genericCallback);
		asyncTask.execute();
	}
	
	/**
	 * Synchronously get all suggested tags 
	 */
	public List<String> getSuggestedTags() {
		HttpResponse serverResponse;
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.LIST_SUGGESTED_TAGS_PATH);
		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		StringListResponse allTagsResponse = responseHandler.handleResponse(
				serverResponse, StringListResponse.class);

		if (allTagsResponse != null) {			
			return allTagsResponse;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but the server was unsuccessful in handling the request.");
		}
	}
	
	/**
	 * Asynchronously get all tags available 
	 */
	public void getSuggestedTags(final ARListener<List<String>> listener, final ARErrorListener onErrorListener) {
		GenericCallback<List<String>> genericCallback = new GenericCallback<List<String>>() {
			@Override
			public List<String> toCall() {
				return getSuggestedTags();
			}

			@Override
			public void onComplete(List<String> result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<List<String>> asyncTask = new GenericAsyncTask<List<String>>(genericCallback);
		asyncTask.execute();
	}
	
	public List<String> searchSitesByTag(String tag) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("tag", tag);
		
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse;
		serverResponse = httpUtils.doPost(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.SEARCH_TAG_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		StringListResponse stringListResponse = responseHandler.handleResponse(
				serverResponse, StringListResponse.class);

		if (stringListResponse != null) {			
			return stringListResponse;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but the server was unsuccessful in handling the request.");
		}
	}
	
	public void searchSitesByTag(final String tag, 
			final ARListener<List<String>> listener, final ARErrorListener onErrorListener) {
		GenericCallback<List<String>> genericCallback = new GenericCallback<List<String>>() {
			@Override
			public List<String> toCall() {
				return searchSitesByTag(tag);
			}

			@Override
			public void onComplete(List<String> result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<List<String>> asyncTask = new GenericAsyncTask<List<String>>(genericCallback);
		asyncTask.execute();
	}
	
	public List<SiteInfoOverview> getTrendingSites() {
		Map<String, String> parameterMap = new HashMap<String, String>();		
		
		HttpUtils httpUtils = new HttpUtils(mApiKey, mTime, mSignature);
		HttpResponse serverResponse;
		serverResponse = httpUtils.doPost(HttpUtils.PARWORKS_API_BASE_URL
				+ HttpUtils.LIST_TRENDING_SITES_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		TrendingSitesResponse trendingSitesResponse = responseHandler.handleResponse(
				serverResponse, TrendingSitesResponse.class);

		if (trendingSitesResponse != null) {			
			return trendingSitesResponse;
		} else {
			throw new ARException(
					"Successfully communicated with the server, but the server was unsuccessful in handling the request.");
		}
	}
	
	public void getTrendingSites(final ARListener<List<SiteInfoOverview>> listener, final ARErrorListener onErrorListener) {
		GenericCallback<List<SiteInfoOverview>> genericCallback = new GenericCallback<List<SiteInfoOverview>>() {
			@Override
			public List<SiteInfoOverview> toCall() {
				return getTrendingSites();
			}

			@Override
			public void onComplete(List<SiteInfoOverview> result) {
				listener.handleResponse(result);				
			}

			@Override
			public void onError(Exception error) {
				if (onErrorListener != null) {
					onErrorListener.handleError(error);
				}
			}			
		};
		
		GenericAsyncTask<List<SiteInfoOverview>> asyncTask = new GenericAsyncTask<List<SiteInfoOverview>>(genericCallback);
		asyncTask.execute();		
	}
}
