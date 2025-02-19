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
package org.apache.camel.component.kamelet;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class KameletEipFilterTest extends CamelTestSupport {

    @Test
    public void testFilter() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived(5, 6, 7, 8, 9, 10);

        for (int i = 0; i < 20; i++) {
            template.sendBody("direct:start", i);
        }

        assertMockEndpointsSatisfied();
    }

    // **********************************************
    //
    // test set-up
    //
    // **********************************************

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                routeTemplate("filter")
                        .from("kamelet:source")
                        .filter().simple("${body} range '5..10'")
                        .to("log:filter")
                        .to("kamelet:sink");

                from("direct:start")
                        .kamelet("filter")
                        .to("mock:result");
            }
        };
    }
}
