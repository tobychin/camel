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
package org.apache.camel.builder.endpoint.dsl;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;
import javax.annotation.Generated;
import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.AbstractEndpointBuilder;

/**
 * Interact with Apache Kudu, a free and open source column-oriented data store
 * of the Apache Hadoop ecosystem.
 * 
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.EndpointDslMojo")
public interface KuduEndpointBuilderFactory {


    /**
     * Builder for endpoint for the Kudu component.
     */
    public interface KuduEndpointBuilder extends EndpointProducerBuilder {
        /**
         * Whether the producer should be started lazy (on the first message).
         * By starting lazy you can use this to allow CamelContext and routes to
         * startup in situations where a producer may otherwise fail during
         * starting and cause the route to fail being started. By deferring this
         * startup to be lazy then the startup failure can be handled during
         * routing messages via Camel's routing error handlers. Beware that when
         * the first message is processed then creating and starting the
         * producer may take a little time and prolong the total processing time
         * of the processing.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: producer
         * 
         * @param lazyStartProducer the value to set
         * @return the dsl builder
         */
        default KuduEndpointBuilder lazyStartProducer(boolean lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
        /**
         * Whether the producer should be started lazy (on the first message).
         * By starting lazy you can use this to allow CamelContext and routes to
         * startup in situations where a producer may otherwise fail during
         * starting and cause the route to fail being started. By deferring this
         * startup to be lazy then the startup failure can be handled during
         * routing messages via Camel's routing error handlers. Beware that when
         * the first message is processed then creating and starting the
         * producer may take a little time and prolong the total processing time
         * of the processing.
         * 
         * The option will be converted to a &lt;code&gt;boolean&lt;/code&gt;
         * type.
         * 
         * Default: false
         * Group: producer
         * 
         * @param lazyStartProducer the value to set
         * @return the dsl builder
         */
        default KuduEndpointBuilder lazyStartProducer(String lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
        /**
         * Operation to perform.
         * 
         * The option is a:
         * &lt;code&gt;org.apache.camel.component.kudu.KuduOperations&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param operation the value to set
         * @return the dsl builder
         */
        default KuduEndpointBuilder operation(
                org.apache.camel.component.kudu.KuduOperations operation) {
            doSetProperty("operation", operation);
            return this;
        }
        /**
         * Operation to perform.
         * 
         * The option will be converted to a
         * &lt;code&gt;org.apache.camel.component.kudu.KuduOperations&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param operation the value to set
         * @return the dsl builder
         */
        default KuduEndpointBuilder operation(String operation) {
            doSetProperty("operation", operation);
            return this;
        }
    }

    public interface KuduBuilders {
        /**
         * Kudu (camel-kudu)
         * Interact with Apache Kudu, a free and open source column-oriented
         * data store of the Apache Hadoop ecosystem.
         * 
         * Category: database,iot,cloud
         * Since: 3.0
         * Maven coordinates: org.apache.camel:camel-kudu
         * 
         * Syntax: <code>kudu:host:port/tableName</code>
         * 
         * Path parameter: host
         * Host of the server to connect to
         * 
         * Path parameter: port
         * Port of the server to connect to
         * 
         * Path parameter: tableName
         * Table to connect to
         * 
         * @param path host:port/tableName
         * @return the dsl builder
         */
        default KuduEndpointBuilder kudu(String path) {
            return KuduEndpointBuilderFactory.endpointBuilder("kudu", path);
        }
        /**
         * Kudu (camel-kudu)
         * Interact with Apache Kudu, a free and open source column-oriented
         * data store of the Apache Hadoop ecosystem.
         * 
         * Category: database,iot,cloud
         * Since: 3.0
         * Maven coordinates: org.apache.camel:camel-kudu
         * 
         * Syntax: <code>kudu:host:port/tableName</code>
         * 
         * Path parameter: host
         * Host of the server to connect to
         * 
         * Path parameter: port
         * Port of the server to connect to
         * 
         * Path parameter: tableName
         * Table to connect to
         * 
         * @param componentName to use a custom component name for the endpoint
         * instead of the default name
         * @param path host:port/tableName
         * @return the dsl builder
         */
        default KuduEndpointBuilder kudu(String componentName, String path) {
            return KuduEndpointBuilderFactory.endpointBuilder(componentName, path);
        }
    }
    static KuduEndpointBuilder endpointBuilder(String componentName, String path) {
        class KuduEndpointBuilderImpl extends AbstractEndpointBuilder implements KuduEndpointBuilder {
            public KuduEndpointBuilderImpl(String path) {
                super(componentName, path);
            }
        }
        return new KuduEndpointBuilderImpl(path);
    }
}