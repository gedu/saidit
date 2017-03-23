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

package com.gemapps.saidit;

import android.app.Application;
import android.util.Log;

import com.gemapps.saidit.networking.RedditListingManager;
import com.gemapps.saidit.ui.paginator.PaginationManager;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

/**
 * Created by edu on 3/22/17.
 */

public class SaiditApplication extends Application {
    private static final String TAG = "SaiditApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        Realm.init(this);
        EventBus.builder()
                .logNoSubscriberMessages(false)
                .installDefaultEventBus();
        PaginationManager.getInstance().addEventBus(EventBus.getDefault());
        RedditListingManager.getInstance()
                .init(Realm.getDefaultInstance())
                .authenticate();
        Picasso.with(this).setLoggingEnabled(true);
    }
}
