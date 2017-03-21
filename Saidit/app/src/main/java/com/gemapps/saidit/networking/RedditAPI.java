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

import com.gemapps.saidit.BuildConfig;

/**
 * Created by edu on 3/21/17.
 */

public interface RedditAPI {
    String BASE_REDDIT_URL = "https://www.reddit.com";
    String BASE_O_REDDIT_URL = BASE_REDDIT_URL+"/api/v1/access_token";
    String LIMIT = "limit";
    String BEFORE = "before";
    String AFTER = "after";
    String SUBREDDIT = "subreddit";
    String CLIENT_ID = "client_id";
    String GRANT_TYPE_KEY = "grant_type";
    String GRANT_TYPE_VALUE = "https://oauth.reddit.com/grants/installed_client";
    String DEVICE_ID_KEY = "device_id";

    String REDDIT_PASSWORD = BuildConfig.REDDIT_PASSWORD;
    String REDDIT_CLIENT_ID = BuildConfig.REDDIT_CLIENT_ID;
}
