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
        return parse(json);
    }

    private TopEntries parse(JsonElement json){
        JsonElement topData = json.getAsJsonObject().get("data");
        JsonArray entries = topData.getAsJsonObject().get("children").getAsJsonArray();

        TopEntries topEntries = buildEntries(topData);
        Gson gson = new Gson();
        for (JsonElement entryElement : entries) {
            TopListingItem topItem = buildFrom(gson, entryElement);
            topEntries.setEntry(topItem);
        }
        return topEntries;
    }

    private TopEntries buildEntries(JsonElement topData){
        TopEntries topEntries = new TopEntries();

        String before = getBeforeTag(topData);
        String after = getAfterTag(topData);

        topEntries.setBefore(before);
        topEntries.setAfter(after);

        return topEntries;
    }

    private String getBeforeTag(JsonElement data){
        JsonElement before = data.getAsJsonObject().get("before");
        return !before.isJsonNull() ? before.getAsString() : "";
    }

    private String getAfterTag(JsonElement data){
        JsonElement after = data.getAsJsonObject().get("after");
        return !after.isJsonNull() ? after.getAsString() : "";
    }

    private TopListingItem buildFrom(Gson gson, JsonElement element){
        JsonElement entryData = element.getAsJsonObject().get("data");
        TopListingItem topItem = gson.fromJson(entryData, TopListingItem.class);
        topItem.setPictureUrl(getPictureUrl(entryData));
        return topItem;
    }

    private String getPictureUrl(JsonElement entryData){
        JsonElement imagesPreview = entryData.getAsJsonObject().get("preview");
        if(imagesPreview != null && !imagesPreview.isJsonNull()) {
            JsonElement image = getFirstImage(imagesPreview);
            JsonElement url = image.getAsJsonObject().get("url");
            return url.isJsonNull() ? "" : url.getAsString();
        }
        return "";
    }

    private JsonElement getFirstImage(JsonElement entryImages){
        JsonArray imageSource = entryImages.getAsJsonObject().get("images").getAsJsonArray();
        return imageSource.get(0).getAsJsonObject().get("source");
    }
}
