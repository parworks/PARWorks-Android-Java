package com.parworks.androidlibrary;

import java.util.List;

import com.parworks.androidlibrary.ar.ARListener;
import com.parworks.androidlibrary.ar.ARResponse;
import com.parworks.androidlibrary.ar.ARSite;
import com.parworks.androidlibrary.ar.ARSite.State;
import com.parworks.androidlibrary.ar.ARSiteImpl;
import com.parworks.androidlibrary.ar.ARSites;
import com.parworks.androidlibrary.response.SiteInfo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Context mContext;
	List<ARSite> mNearbySites = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mContext = this;
        
        ARSite arSite = new ARSiteImpl(new SiteInfo(),"w","s","s");
        
        State siteState = arSite.processBaseImages();
        
        
        ARSites sites = new ARSites("someuser","somepass");
        
        sites.near(0, 0, 10, 100, new ARListener<List<ARSite>>() {

			@Override
			public void handleResponse(ARResponse<List<ARSite>> resp) {
				mNearbySites = resp.getPayload();
				Toast.makeText(mContext, "size is: " + mNearbySites.size(), Toast.LENGTH_LONG).show();
				
			}
        	
        });
        
        sites.getExisting("foo",new ARListener<ARSite>() {

			@Override
			public void handleResponse(ARResponse<ARSite> resp) {
				ARSite fooSite = resp.getPayload();
				SiteInfo fooInfo = fooSite.getSiteInfo();
				Toast.makeText(mContext, "Id is: " + fooInfo.getId(), Toast.LENGTH_LONG).show();
				
			}
        	
        });

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
