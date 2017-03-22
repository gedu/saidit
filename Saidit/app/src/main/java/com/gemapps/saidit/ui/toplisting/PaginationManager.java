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

package com.gemapps.saidit.ui.toplisting;

import com.gemapps.saidit.networking.RedditListingManager;

/**
 * Created by edu on 3/22/17.
 */

public class PaginationManager {

    private static final String BASIC_QUERY_PARAMS = "?limit=%s&count=%s";
    private static final String QUERY_WITH_BEFORE = BASIC_QUERY_PARAMS+"&before=%s";
    private static final String QUERY_WITH_AFTER = BASIC_QUERY_PARAMS+"&after=%s";

    private static PaginationManager mInstance;

    public static PaginationManager getInstance(){
        if (mInstance == null) mInstance = new PaginationManager();
        return mInstance;
    }

    private int PAG_LIMIT = 10;
    private int mNumOfItems = 0;

    private String mBefore = "";
    private String mAfter = "";

    public void onStart(){
        RedditListingManager.getInstance().getTopListing(getFirstTimeQuery());
    }

    public void onPrev(){
        RedditListingManager.getInstance().getTopListing(getBeforeQuery());
    }

    public void onNext(){
        RedditListingManager.getInstance().getTopListing(getAfterQuery());
    }

    private String getFirstTimeQuery(){
        return String.format(BASIC_QUERY_PARAMS, PAG_LIMIT, mNumOfItems);
    }

    private String getBeforeQuery(){
        return String.format(QUERY_WITH_AFTER, PAG_LIMIT, mNumOfItems, mBefore);
    }

    private String getAfterQuery(){
        return String.format(QUERY_WITH_AFTER, PAG_LIMIT, mNumOfItems, mAfter);
    }

    public PaginationManager setBefore(String before) {
        mBefore = before;
        return mInstance;
    }

    public PaginationManager setAfter(String after) {
        mAfter = after;
        return mInstance;
    }
}
