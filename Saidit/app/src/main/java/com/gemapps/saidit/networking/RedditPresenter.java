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

import com.gemapps.saidit.networking.model.Bearer;
import com.gemapps.saidit.networking.request.AuthenticationClientAsync;
import com.gemapps.saidit.networking.request.RedditContract;

import io.realm.Realm;

/**
 * Created by edu on 3/22/17.
 */

public class RedditPresenter implements RedditContract.OnInteractionListener {
    private static final String TAG = "RedditPresenter";
    private RedditContract.Manager mManager;
    private Realm mRealm;
    private Bearer mBearer;

    public RedditPresenter(RedditContract.Manager manager) {
        mManager = manager;
    }

    @Override
    public void setRealm(Realm realm) {
        mRealm = realm;
    }

    @Override
    public void findBearer() {
        mBearer = mRealm.where(Bearer.class).findFirst();
    }

    @Override
    public void findBearerAsync() {
        mBearer = mRealm.where(Bearer.class).findFirstAsync();
    }

    @Override
    public void doAuthentication() {
        new AuthenticationClientAsync()
                .authenticate(RedditAPI.ACCESS_TOKEN_REDDIT_URL);
    }

    @Override
    public boolean isBearerValid() {
        return mBearer.isBearerValid();
    }

    @Override
    public boolean isAuthenticated() {
        return mBearer != null && mBearer.isLoaded() && mBearer.isValid();
    }

    @Override
    public String getToken() {
        return mBearer.getToken();
    }

    @Override
    public Realm getRealm() {
        return mRealm;
    }
}
