/*
 *    Copyright 2017 Edu Graciano
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gemapps.saidit.networking;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.gemapps.saidit.networking.request.RedditContract;
import com.gemapps.saidit.networking.request.TopListingRequest;
import com.gemapps.saidit.ui.paginator.PaginationManager;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

/**
 * Created by edu on 3/21/17.
 */

public class RedditListingManager implements RedditContract.Manager {
    private static final String TAG = "RedditListingManager";

    private static RedditListingManager mInstance;

    public static RedditListingManager getInstance(){
        if(mInstance == null) mInstance = new RedditListingManager();
        return mInstance;
    }

    private RedditContract.OnInteractionListener mInteractionListener;

    private RedditListingManager(){
        mInteractionListener = new RedditPresenter(this);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setInstance(RedditContract.OnInteractionListener listener){
        mInteractionListener = listener;
    }


    @Override
    public RedditListingManager init(Realm realm){
        mInteractionListener.setRealm(realm);
        mInteractionListener.findBearer();
        return mInstance;
    }

    @Override
    public void authenticate(){
        if (needToRefreshToken()){
            Log.d(TAG, "authenticate: ");
            mInteractionListener.findBearerAsync();
            mInteractionListener.doAuthentication();
        }
    }

    public void getTopListing(EventBus bus, String query,
                              @PaginationManager.PaginationType int pagType){
        if(!needToRefreshToken()){
            Log.d(TAG, "getTopListing: "+query);
            new TopListingRequest(NetInjector.getClientAsync(), bus)
                    .getTopListing(query, pagType);
        }else{
            authenticate();
        }
    }

    private boolean needToRefreshToken(){
        return !isAuthenticated() || !mInteractionListener.isBearerValid();
    }

    @Override
    public boolean isAuthenticated(){
        return mInteractionListener.isAuthenticated();
    }

    @Override
    public String getBearerToken() {
        return mInteractionListener.getToken();
    }

    @Override
    public Realm getRealm() {
        return mInteractionListener.getRealm();
    }
}
