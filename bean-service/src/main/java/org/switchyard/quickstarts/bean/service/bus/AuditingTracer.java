/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.switchyard.quickstarts.bean.service.bus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.model.ProcessDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.processor.interceptor.TraceFormatter;
import org.apache.camel.processor.interceptor.TraceInterceptorFactory;
import org.apache.camel.processor.interceptor.Tracer;

/**
 * Extension of tracer which allows to inject custom logic before/after processing
 * of exchanges.
 */
@Named("auditing-tracer")
public class AuditingTracer extends Tracer {

    /**
     * Tracer instance used by default.
     */
    private Tracer _bogus = new Tracer();

    /**
     * Processor names ignored by interceptor.
     */
    private List<String> _ignoredNames = Arrays.asList(
        "domain-handlers",
        "addressing",
        "transaction-handler",
        "generic-policy",
        "validation",
        "transformation"
    );

    public AuditingTracer() {
        setTraceOutExchanges(true); // we want see output after processing
        _bogus.setLogLevel(LoggingLevel.OFF);
        _bogus.setEnabled(false); // disable output produced by default tracer

        setTraceInterceptorFactory(new TraceInterceptorFactory() {
            @Override
            public Processor createTraceInterceptor(ProcessorDefinition<?> node, Processor target, TraceFormatter formatter, Tracer tracer) {
                if (node instanceof ProcessDefinition) {
                    // we track only process definitions, where SwitchYard logic occurs
                    ProcessDefinition definition = (ProcessDefinition) node;
                    // catch provider processor for IN-ONLY and consumer processor for IN-OUT exchanges
                    if (definition.getRef() == null || !_ignoredNames.contains(definition.getRef())) {
                        return new AuditingTracerInterceptor(node, target, formatter, tracer);
                    }
                }

                // produce bogus trace interceptor which is disabled by default
                return _bogus.getTraceInterceptorFactory().createTraceInterceptor(node, target, formatter, _bogus);
            }
        });
    }

}
