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
package org.apache.camel.jaxb;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.BindToRegistry;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.example.Address;
import org.apache.camel.example.Order;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
public class JaxbMarshalNamespacePrefixMapperTest extends CamelTestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(JaxbMarshalNamespacePrefixMapperTest.class);

    @BindToRegistry("myPrefix")
    public Map<String, String> addMap() {
        Map<String, String> map = new HashMap<>();
        map.put("http://www.camel.apache.org/jaxb/example/order/1", "o");
        map.put("http://www.camel.apache.org/jaxb/example/address/1", "a");

        return map;
    }

    @Test
    public void testNamespacePrefix() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);

        Order order = new Order();
        order.setId("1");
        Address address = new Address();
        address.setStreet("Main Street");
        address.setStreetNumber("3a");
        address.setZip("65843");
        address.setCity("Sulzbach");
        order.setAddress(address);

        template.sendBody("direct:start", order);

        assertMockEndpointsSatisfied();

        String xml = mock.getExchanges().get(0).getIn().getBody(String.class);
        LOG.info(xml);

        assertTrue(xml.contains("xmlns:a=\"http://www.camel.apache.org/jaxb/example/address/1\""));
        assertTrue(xml.contains("xmlns:o=\"http://www.camel.apache.org/jaxb/example/order/1\""));
        assertTrue(xml.contains("<o:id>1</o:id>"));
        assertTrue(xml.contains("<a:street>Main Street</a:street>"));
        assertTrue(xml.contains("</o:order>"));
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                JaxbDataFormat df = new JaxbDataFormat();
                df.setContextPath("org.apache.camel.example");
                df.setNamespacePrefixRef("myPrefix");

                from("direct:start")
                        .marshal(df)
                        .to("mock:result");

            }
        };
    }
}
