/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.utils;

import java.util.HashMap;
import java.util.Map;

public class Maps<K,V> {

	public static <K1,V1> Maps<K1,V1> newMap(){
		return new Maps<K1,V1>(false);
	}
	
//	public static <K1,V1> Maps<K1,V1> newImmutableMap(){
//		return new Maps<K1,V1>(true);
//	}
	
	
	private boolean immutable_;
	
	private Map<K, V> map_ = new HashMap<K, V>();
	
	private Maps(boolean im){
		immutable_ = im;
	}
	
	public Maps with(K k, V v){
		map_.put(k, v);
		return this;
	}
	
	public Map<K,V> build(){
		return map_;
	}
}
