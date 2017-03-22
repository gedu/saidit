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

package com.gemapps.saidit.busitem;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by edu on 3/22/17.
 */

public class PaginationStateBridge implements BusBridge {

    @IntDef(flag=true, value={BEFORE_ON, AFTER_ON, BEFORE_OFF, AFTER_OFF})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PaginationState{}
    public static final int BEFORE_ON = 0;
    public static final int AFTER_ON = 1;
    public static final int BEFORE_OFF = 1<<1;
    public static final int AFTER_OFF = 1<<2;

    private int mState;

    public PaginationStateBridge(@PaginationState int state) {
        mState = state;
    }

    public @PaginationState int getState() {
        return mState;
    }

    @Override
    public int getBridgeType() {
        return PAGINATION;
    }
}
