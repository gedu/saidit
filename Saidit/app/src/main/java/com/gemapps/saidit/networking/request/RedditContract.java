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

import com.gemapps.saidit.networking.RedditListingManager;

import io.realm.Realm;

/**
 * Created by edu on 3/22/17.
 */

public interface RedditContract {

    interface Manager {
        RedditListingManager init(Realm realm);
        void authenticate();
        boolean isAuthenticated();
        String getBearerToken();
        Realm getRealm();
    }

    interface OnInteractionListener {
        void setRealm(Realm realm);
        void findBearer();
        void findBearerAsync();
        void doAuthentication();
        boolean isBearerValid();
        boolean isAuthenticated();
        String getToken();
        Realm getRealm();
    }
}
