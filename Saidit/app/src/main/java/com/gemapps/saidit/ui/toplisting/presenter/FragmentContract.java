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

import com.gemapps.saidit.ui.model.TopListingItem;
import com.gemapps.saidit.ui.toplisting.TopListingAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by edu on 3/21/17.
 */

public interface FragmentContract {

    interface View {
        void onPopulateList(List<TopListingItem> listingItems);
        void hideEmptyView();
        void showEmptyView();
    }

    interface OnInteractionListener {
        void onEventBusSubscribe(EventBus bus);
        void onEventBusUnSubscribe(EventBus bus);
        void addAdapter(TopListingAdapter adapter);
        void updateListingView();
    }
}
