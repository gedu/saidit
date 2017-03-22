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

import android.util.Log;

import com.gemapps.saidit.networking.model.Bearer;
import com.gemapps.saidit.networking.request.AuthenticationClientAsync;
import com.gemapps.saidit.networking.request.TopListingRequest;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

/**
 * Created by edu on 3/21/17.
 */

public class RedditListingManager {
    private static final String TAG = "RedditListingManager";
    private static RedditListingManager mInstance;

    public static RedditListingManager getInstance(){
        if(mInstance == null) mInstance = new RedditListingManager();
        return mInstance;
    }

    private Bearer mBearer;
    private Realm mRealm;

    private RedditListingManager(){
        mRealm = Realm.getDefaultInstance();
        mBearer = mRealm.where(Bearer.class).findFirst();
    }

    public void authenticate(){
        Log.d(TAG, "authenticate() called");
        if (!isAuthenticated()){
            Log.d(TAG, "authenticate: ");
            mBearer = mRealm.where(Bearer.class).findFirstAsync();
            new AuthenticationClientAsync().authenticate(RedditAPI.ACCESS_TOKEN_REDDIT_URL);
        }
    }

    public void getTopListing(String query){
        if(isAuthenticated()){
            new TopListingRequest(NetInjector.getClientAsync(), EventBus.getDefault())
                    .getTopListing(query);
        }
    }

    public boolean isAuthenticated(){
        return mBearer != null && mBearer.isLoaded() && mBearer.isValid();
    }

    public String getBearerToken() {
        return mBearer.getToken();
    }

    public Realm getRealm() {
        return mRealm;
    }
}
