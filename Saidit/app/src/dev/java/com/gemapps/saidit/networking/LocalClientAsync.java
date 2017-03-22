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

import com.gemapps.saidit.busitem.EntryResponseBridge;
import com.gemapps.saidit.networking.inject.NetBridge;
import com.gemapps.saidit.ui.model.TopEntries;
import com.gemapps.saidit.ui.paginator.PaginationManager;
import com.gemapps.saidit.util.GsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.gemapps.saidit.util.JsonUtil.readFrom;

/**
 * Created by edu on 3/21/17.
 */

public class LocalClientAsync implements NetBridge {

    private static final String JSON_EXAMPLE_NAME = "top.json";

    @Override
    public void doGet(EventBus eventBus, String url, int tag) {
        String json = loadJsonFromResources();
        TopEntries topEntries = GsonUtil.TOP_ENTRY_GSON.fromJson(json, TopEntries.class);
        updatePaginationManagerWithValidContent(topEntries, tag);
        eventBus.post(new EntryResponseBridge(topEntries.getEntries()));
    }

    private void updatePaginationManagerWithValidContent(TopEntries topEntries, int tag){
        PaginationManager.getInstance()
                .setAfter(topEntries.getAfter())
                .setBefore(topEntries.getBefore())
                .updateCount(tag);
    }

    private String loadJsonFromResources(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(JSON_EXAMPLE_NAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return readFrom(reader);
    }
}
