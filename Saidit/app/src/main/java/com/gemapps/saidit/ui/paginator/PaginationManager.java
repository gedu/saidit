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

package com.gemapps.saidit.ui.paginator;

import android.support.annotation.IntDef;

import com.gemapps.saidit.busitem.PaginationStateBridge;
import com.gemapps.saidit.networking.RedditListingManager;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.gemapps.saidit.busitem.PaginationStateBridge.AFTER_OFF;
import static com.gemapps.saidit.busitem.PaginationStateBridge.AFTER_ON;
import static com.gemapps.saidit.busitem.PaginationStateBridge.BEFORE_OFF;
import static com.gemapps.saidit.busitem.PaginationStateBridge.BEFORE_ON;

/**
 * Created by edu on 3/22/17.
 */

public class PaginationManager {

    private static final String TAG = "PaginationManager";
    private static final String BASIC_QUERY_PARAMS = "?limit=%s&count=%s";
    private static final String QUERY_WITH_BEFORE = BASIC_QUERY_PARAMS+"&before=%s";
    private static final String QUERY_WITH_AFTER = BASIC_QUERY_PARAMS+"&after=%s";

    @IntDef(flag=true, value={START, PREV, NEXT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PaginationType{}
    public static final int START = 0;
    public static final int PREV = 1;
    public static final int NEXT = 1 << 1;

    private static PaginationManager mInstance;

    public static PaginationManager getInstance(){
        if (mInstance == null) mInstance = new PaginationManager();
        return mInstance;
    }

    private int PAG_LIMIT = 10;
    private int mNumOfItems = 0;

    private EventBus mBus;
    private String mBefore = "";
    private String mAfter = "";

    public void addEventBus(EventBus bus){
        mBus = bus;
    }

    public void onStart(){
        RedditListingManager.getInstance().getTopListing(mBus, getFirstTimeQuery(), START);
    }

    public void onPrev(){
        RedditListingManager.getInstance().getTopListing(mBus, getBeforeQuery(), PREV);
    }

    public void onNext(){
        RedditListingManager.getInstance().getTopListing(mBus, getAfterQuery(), NEXT);
    }

    private String getFirstTimeQuery(){
        return String.format(BASIC_QUERY_PARAMS, PAG_LIMIT, mNumOfItems);
    }

    private String getBeforeQuery(){
        return String.format(QUERY_WITH_BEFORE, PAG_LIMIT, mNumOfItems, mBefore);
    }

    private String getAfterQuery(){
        return String.format(QUERY_WITH_AFTER, PAG_LIMIT, mNumOfItems, mAfter);
    }

    public PaginationManager updateCount(int pagType){
        if(pagType == PREV) mNumOfItems -= PAG_LIMIT;
        else mNumOfItems += PAG_LIMIT;
        return mInstance;
    }

    public PaginationManager setBefore(String before) {
        mBefore = before;
        mBus.post(new PaginationStateBridge(mBefore.length() > 0 ? BEFORE_ON : BEFORE_OFF));
        return mInstance;
    }

    public PaginationManager setAfter(String after) {
        mAfter = after;
        mBus.post(new PaginationStateBridge(mAfter.length() > 0 ? AFTER_ON : AFTER_OFF));
        return mInstance;
    }
}
