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

package com.gemapps.saidit.ui.toplisting.presenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by edu on 3/22/17.
 */

public interface ActivityContract {

    interface View {
        void onDisablePrevButton();
        void onDisableNextButton();
        void onEnablePrevButton();
        void onEnableNextButton();
    }

    interface OnInteractionListener {
        void onEventBusSubscribe(EventBus bus);
        void onEventBusUnSubscribe(EventBus bus);
        void onPaginationStateChanged(int state);
        void onPrevClicked();
        void onNextClicked();
    }
}
