/*
 * Copyright 2016 DNAstack
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.dnastack.beacon.converter.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.OutputStream;

/**
 * Abstract TaskOutput object to store the output of a single task
 * @author
 */
public abstract class TaskOutput<Stream extends OutputStream> {

    private Stream output;

    public TaskOutput(Stream output) {
        this.output = output;
    }

    /**
     * Get the raw TaskOutput
     * @return
     */
    public Stream getOutput() {
        return output;
    }

    public <T> T toObject(JSONObject object, Class<T> clazz) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(object.toJSONString(), clazz);
    }
}
