PARWorks Documentation
=========

The PARWorks Augmented Reality API allows you to quickly and easily develop applications with advanced scene recognition and pose estimation features. We're excited to share this advanced technology with you, and we can't wait to see what you create. This technology is in public beta, and we encourage you to submit feedback, bugs, and feature ideas to us at support@parworks.com.

Getting Started
--------

Using the PARWorks API is a two step process. First, you need to upload a set of images of an object of interest (a building, for example) to our database. In the PARWorks system, these image sets are collectively called "sites" and the images themselves are called “base images.” You can use the PARWorks developer portal, the API, our Android library, or our iOS SDK to create a site and upload photos to the database.  If you're using our Android library, you can use the com.parworks.androidlibrary.ar.ARSites class to create a site by calling the synchronous or asynchronous create() method. That method will return an ARSite object, from which you can then call addBaseImage() to upload a base image.
Note: If you're leveraging the models created by another group and placed in the public domain in our model database, you can skip this step. 

After you upload site images, you need to add metadata overlays to them. Unlike other augmented reality systems, overlays created in PARWorks aren't just tied to a geographical coordinate. Since our technology leverages advanced image recognition techniques, overlays can be tied to a specific window on the side of a building, a particular knob on a car dashboard, and more. As long as your images are sufficiently detailed, overlays can be placed with a high degree of accuracy anywhere on your model.

Even though overlays are attached to the 3D model of your site, you create them by annotating one or more of your site images. You only need to add an annotation once for each overlay. It is not necessary to add an overlay in each image it appears in. Using our Android library, create an Overlay object and then call ARSite.addOverlay(Overlay).

When you're done adding overlays, you need to process the images. The PARWorks API generates a 3D model of the scene in your photos and attaches the overlays you specified directly onto the model. Using our Android library, call processBaseImages() on your ARSite.

Once you've created a site or found an existing one to use, you can continue to submit new photos of the site that were not in the original set. These might include photos taken by your mobile app, photos found on Google Image search, or photos that users submit to your system. The PARWorks API will match the photos against the models you specify and return information about overlays present in the image. The overlay data is returned as JSON, and each overlay includes geometry that corresponds to locations on the image you submitted.


Desirable Image Properties:
--------

- The API works best when images are more than 2000x2000px. 
- Sharp, detailed images work best. Overlays may be missing or misaligned if images are blurry.
- Sites should be photographed under optimal real-world conditions. Images will still augment correctly in different lighting, but we suggest making two versions (night and day) for each site if you intend to augment images after dark.
- Images of highly reflective surfaces, such as glass buildings and mirrors, are often difficult to augment because images of these materials vary considerably based on perspective and environment. Textured surfaces, such as brick and wood, and detailed or multifaceted objects, such as bookshelves and city streets are often easiest to augment.


Welcome to the PARWorks Android library.
=========
Our Android library provides a native Java interface to the PARWorks API, allowing you to use the API without worrying about network requests.  Our library provides both Asynchronous and Synchronous methods.

Getting Started with the Android library
=========

Create an ARSites object and pass in your apikey and secretkey as strings.

	ARSites mySites = new ARSites(apiKey,secretKey);

Create a new site synchronously by calling create:

	ARSite mySite = mySites.create(siteId, description, channel);


Or Asynchronously:

	mySite.create(siteId, description, channel, new ARListener<ARSite>() {
		@Override
		public void handleResponse(ARResponse<ARSite> resp) {
			ARSite mySite = resp.getPayload();
			//do something with the site...
		}
	}

All server requests can be made synchronously or asynchronously. We will show synchronous requests for brevity.

Next, add base images to the site:

	BaseImageInfo baseImage = mySite.addBaseImage(filename, imageInputStream);

After adding enough base images, process the base images:

	State siteState = mySite.processBaseImages();

To add overlays, create a list of vertices, an overlay object, and then call addOverlay. An overlay must have at least three vertices in order to be added.

	List<Vertex> vertices = new ArrayList<Vertex>();
	vertices.add(new Vertex(xCoord, yCoord));
	…
	…

	Overlay myOverlay = new OverlayImpl(imageId, name, description, List<Vertex> vertices);
	OverlayResponse newOverlay = mySite.addOverlay(myOverlay);

	
Next, augment an image.

	AugmentedData augmentedImage = mySite.add(imageInputStream);

Use the augmentedImage to do cool stuff with the image!!

ARSite has a state attribute. A site's state begins in NEEDS_MORE_BASE_IMAGES and transitions to NEEDS_BASE_IMAGE_PROCESSING after the appropriate number of base images have been added. After base image processing completes, the site changes to NEEDS_OVERLAYS and finally to READY_TO_AUGMENT_IMAGES.

ARSites and ARSite have many more helpful methods, such as near(),update(),delete(), etc.

Exceptions
=========
Our Android library throws the unchecked exception ARException when things go wrong!


Other Useful Components
=========

ARSite, Overlay, and AugmentedData are interfaces which can be implemented to provide custom functionality.

If you want to use the REST API directly, you can use the classes in com.parworks.androidlibrary.response to parse the json server responses.   

	ARResponseHandler responseHandler = new ARResponseHandlerImpl();
	AddBaseImageResponse baseImage = responseHandler.handleResponse(httpResponse, AddBaseImageResponse.class);


Have Fun!
=========
Please email us with feedback, bugs, and suggestions! :)