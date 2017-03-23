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

package com.gemapps.saidit.networking.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edu on 3/21/17.
 */
public class Bearer extends RealmObject {

    private static final String TAG = "Bearer";
    @PrimaryKey
    private String mId;
    @SerializedName("access_token")
    private String mToken;
    @SerializedName("device_id")
    private String mDeviceId;
    @SerializedName("expires_in")
    private int mExpirationTime;
    private long mSavedTime;

    public Bearer() {
        mId = "Bearer";
        mSavedTime = Calendar.getInstance().getTimeInMillis();
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public int getExpirationTime() {
        return mExpirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        mExpirationTime = expirationTime;
    }

    public long getSavedTime() {
        return mSavedTime;
    }

    public void setSavedTime(long savedTime) {
        mSavedTime = savedTime;
    }

    public boolean isBearerValid() {
        Log.d(TAG, "isBearerValid: "+mExpirationTime);
        return ((Calendar.getInstance().getTimeInMillis() - mSavedTime)/1000) < mExpirationTime;
    }

}
