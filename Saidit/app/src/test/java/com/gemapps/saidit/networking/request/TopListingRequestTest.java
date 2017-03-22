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

package com.gemapps.saidit.networking.request;

import com.gemapps.saidit.busitem.EntryResponseBridge;
import com.gemapps.saidit.ui.model.TopListingItem;
import com.gemapps.saidit.networking.NetInjector;
import com.gemapps.saidit.ui.toplisting.presenter.FragmentContract;
import com.gemapps.saidit.ui.toplisting.presenter.FragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by edu on 3/21/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TopListingRequestTest {

    private FragmentPresenter mPresenter;

    ArgumentCaptor<EntryResponseBridge> mResponseCaptor = ArgumentCaptor.forClass(EntryResponseBridge.class);

    @Mock
    EventBus mMockEventBus;

    @Mock
    FragmentContract.View mView;

    @Mock
    TopListingItem mMocListing;

    @Before
    public void setup() {
        mPresenter = new FragmentPresenter(mView);
    }

    @Test
    public void getTopListing() {
        mPresenter.onEventBusSubscribe(mMockEventBus);

        TopListingRequest topListing = new TopListingRequest(NetInjector.getClientAsync(), mMockEventBus);
        topListing.getTopListing("");
        verify(mMockEventBus).post(mResponseCaptor.capture());
        EntryResponseBridge responseBridge = mResponseCaptor.getValue();

        assertEquals(responseBridge.getItems().size(), 25);
    }
}
