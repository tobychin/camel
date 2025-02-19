/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.camel.component.aws2.kinesis.consumer;

import org.apache.camel.component.aws2.kinesis.Kinesis2Configuration;
import software.amazon.awssdk.services.kinesis.model.GetShardIteratorRequest;
import software.amazon.awssdk.services.kinesis.model.ShardIteratorType;

public class KinesisUserConfigurationResumeAdapter implements KinesisResumeAdapter {
    private final Kinesis2Configuration configuration;
    private GetShardIteratorRequest.Builder resumable;

    public KinesisUserConfigurationResumeAdapter(Kinesis2Configuration configuration) {
        this.configuration = configuration;
    }

    private boolean hasSequenceNumber() {
        return !configuration.getSequenceNumber().isEmpty()
                && (configuration.getIteratorType().equals(ShardIteratorType.AFTER_SEQUENCE_NUMBER)
                        || configuration.getIteratorType().equals(ShardIteratorType.AT_SEQUENCE_NUMBER));
    }

    @Override
    public void setRequestBuilder(GetShardIteratorRequest.Builder resumable) {
        this.resumable = resumable;
    }

    @Override
    public void resume() {
        if (hasSequenceNumber()) {
            resumable.startingSequenceNumber(configuration.getSequenceNumber());
        }
    }
}
