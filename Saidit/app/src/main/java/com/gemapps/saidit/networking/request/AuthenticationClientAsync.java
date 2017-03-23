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

package com.gemapps.saidit.networking.request;

import com.gemapps.saidit.busitem.OauthEventBridge;
import com.gemapps.saidit.networking.RedditAPI;
import com.gemapps.saidit.networking.RedditListingManager;
import com.gemapps.saidit.networking.model.Bearer;
import com.gemapps.saidit.util.Util;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by edu on 3/21/17.
 */

public class AuthenticationClientAsync extends BaseHttpClient implements RedditAPI {

    private static final String TAG = "AuthenticationClientAsy";
    private static boolean mRequestingAuthentication;

    public void authenticate(String url){

        if(!mRequestingAuthentication) {
            mRequestingAuthentication = true;
            Headers.Builder builder = new Headers.Builder();
            builder.add("Authorization", Credentials.basic(REDDIT_CLIENT_ID, REDDIT_PASSWORD));
            RequestBody body = new FormBody.Builder()
                    .add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE)
                    .add(DEVICE_ID_KEY, Util.getRandomID()).build();

            doPost(url, builder.build(), body);
        }
    }

    @Override
    protected void onSuccess(final String body, int tag) {
        mRequestingAuthentication = false;
        RedditListingManager.getInstance()
                .getRealm()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Bearer bearer = new Gson().fromJson(body, Bearer.class);
                        realm.insertOrUpdate(bearer);
                    }
                });
        EventBus.getDefault().post(new OauthEventBridge(OauthEventBridge.SUCCESS));
    }

    @Override
    protected void onFail() {
        mRequestingAuthentication = false;
        EventBus.getDefault().post(new OauthEventBridge(OauthEventBridge.FAIL));
    }
}
