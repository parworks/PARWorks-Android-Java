/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

import java.util.List;
/**
 * Returned after augmenting an image. Contains overlay information for a particular augmented image.
 * @author Jules White
 *
 */
public interface ARData {

	public float getCameraFocalLength();
	
	public List<Overlay> getOverlays();
	
}
