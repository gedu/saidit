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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by edu on 3/21/17.
 */

public class JsonUtil {

    private static final String JSON_EXAMPLE_NAME = "top.json";

    public String loadJsonFromResources(){

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(JSON_EXAMPLE_NAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return readFrom(reader);
    }

    private String readFrom(BufferedReader reader) {
        String json;
        StringBuilder builder = new StringBuilder();
        try {
            while ((json = reader.readLine()) != null) builder.append(json);
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
