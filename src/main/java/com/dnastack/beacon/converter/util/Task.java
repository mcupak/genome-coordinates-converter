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

import java.io.OutputStream;

/**
 * @author patmagee
 */
public interface Task<Output extends TaskOutput, Stream extends OutputStream> {

    /**
     * Create the command string containing the full command to be run
     *
     * @return command string
     */
    String getCommand();

    /**
     * Create an implementation specific TaskOutput Object that depends on the specific task. Take the output from
     * the task and perform operations defined
     *
     * @param output output from the task
     * @return
     */
    Output generateOutput(Stream output);


    /**
     * get a new instance of the output stream to store the task output in
     *
     * @return
     */
    Stream getOutputStream();
}
