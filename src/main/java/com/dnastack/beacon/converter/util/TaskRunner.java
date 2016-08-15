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

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class for executing generic command line tasks in the local environment.
 *
 * @author patmagee
 */
public class TaskRunner {

    /**
     * Execute a command as defined by the task object and captures the standard output as a string
     *
     * @param task An object defining the task to perform
     * @return Instance of TaskOutput
     * @throws IOException
     */
    public TaskOutput exec(Task task) throws IOException {
        CommandLine cmd = CommandLine.parse(task.getCommand());
        OutputStream outputStream = task.getOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);
        executor.setStreamHandler(streamHandler);
        executor.execute(cmd);

        //The input task also defines the output object that it expects
        return task.generateOutput(outputStream);
    }

}
