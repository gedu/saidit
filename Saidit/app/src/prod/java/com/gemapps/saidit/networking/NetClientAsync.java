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

import com.gemapps.saidit.busitem.EntryResponseBridge;
import com.gemapps.saidit.networking.inject.NetBridge;
import com.gemapps.saidit.networking.request.BaseHttpClient;
import com.gemapps.saidit.ui.model.TopEntries;
import com.gemapps.saidit.ui.toplisting.PaginationManager;
import com.gemapps.saidit.util.GsonUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by edu on 3/21/17.
 */

public class NetClientAsync extends BaseHttpClient
        implements NetBridge {
    private static final String TAG = "NetClientAsync";
    private EventBus mBus;

    @Override
    public void doGet(EventBus eventBus, String url) {
        mBus = eventBus;
        doGet(url);
    }

    @Override
    protected void onSuccess(String body) {
//        Log.d(TAG, "onSuccess() called with: body = <" + body + ">");
        TopEntries topEntries = GsonUtil.TOP_ENTRY_GSON.fromJson(body, TopEntries.class);
        Log.d(TAG, "onSuccess: "+topEntries.getEntries().size());
        PaginationManager.getInstance()
                .setAfter(topEntries.getAfter())
                .setBefore(topEntries.getBefore());
        mBus.post(new EntryResponseBridge(topEntries.getEntries()));
    }

    @Override
    protected void onFail() {
        // TODO: 3/21/17  handle this error
        Log.w(TAG, "onFail: ");
    }
}
