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

package com.gemapps.saidit.networking.deserializer;

import android.util.Log;

import com.gemapps.saidit.ui.model.TopEntries;
import com.gemapps.saidit.ui.model.TopListingItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by edu on 3/21/17.
 */

public class TopEntriesDeserializer implements JsonDeserializer<TopEntries> {
    private static final String TAG = "TopEntriesDeserializer";
    @Override
    public TopEntries deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context) throws JsonParseException {
        JsonElement topData = json.getAsJsonObject().get("data");
        JsonElement before = topData.getAsJsonObject().get("before");
        JsonElement after = topData.getAsJsonObject().get("after");
        Log.d(TAG, "before: "+before);
        Log.d(TAG, "after: "+after);
        JsonArray entries = topData.getAsJsonObject().get("children").getAsJsonArray();

        TopEntries topEntries = new TopEntries();
        topEntries.setBefore(!before.isJsonNull() ? before.getAsString() : "");
        topEntries.setAfter(!after.isJsonNull() ? after.getAsString() : "");
        Gson gson = new Gson();
        for (JsonElement element : entries) {
            JsonElement entryData = element.getAsJsonObject().get("data");
            JsonElement imagesPreview = entryData.getAsJsonObject().get("preview");
            JsonArray sourceImages = imagesPreview.getAsJsonObject().get("images").getAsJsonArray();
            JsonElement mainImage = sourceImages.get(0).getAsJsonObject().get("source");
            JsonElement url = mainImage.getAsJsonObject().get("url");

            TopListingItem topItem = gson.fromJson(entryData, TopListingItem.class);
            topItem.setPictureUrl(url.isJsonNull() ? "" : url.getAsString());
            topEntries.setEntry(topItem);
        }
        return topEntries;
    }
}
