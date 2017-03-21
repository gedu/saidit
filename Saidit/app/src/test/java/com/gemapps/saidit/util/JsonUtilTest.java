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

package com.gemapps.saidit.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by edu on 3/21/17.
 */
public class JsonUtilTest {
    private static final String TAG = "JsonUtilTest";
    private String mJson;
    @Before
    public void setup(){
        mJson = getJson();
    }

    @Test
    public void getJsonFromFileLikeString_notEmpty(){
        assertTrue(mJson.length() > 0);
    }

    @Test
    public void getJsonFromLikeLikeString_convertToJsonObject() throws JSONException {
        JSONObject obj = new JSONObject(mJson);
        assertNotNull(obj);
    }

    @Test
    public void getKindFromJsonObject_isListingKind() {
        JsonElement element = getJsonLikeElement();
        String kind = element.getAsJsonObject().get("kind").getAsString();
        assertTrue(kind.equals("Listing"));
    }

    @Test
    public void getKindFromJsonObject_isNotListingKind() {
        JsonElement element = getJsonLikeElement();
        String kind = element.getAsJsonObject().get("kind").getAsString();
        assertFalse(kind.equals("NotListing"));
    }

    private String getJson(){
        JsonUtil jsonUtil = new JsonUtil();
        return jsonUtil.loadJsonFromResources();
    }

    private JsonElement getJsonLikeElement(){
        Gson gson = new Gson();
        return gson.fromJson(mJson, JsonElement.class);
    }
}
