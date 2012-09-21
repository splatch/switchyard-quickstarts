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

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.processor.interceptor.TraceFormatter;
import org.apache.camel.processor.interceptor.TraceInterceptor;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.log4j.Logger;

/**
 * Simple tracer which around call on given processor node.
 */
public class AuditingTracerInterceptor extends TraceInterceptor {

    private Logger _logger = Logger.getLogger(AuditingTracerInterceptor.class);

    public AuditingTracerInterceptor(ProcessorDefinition<?> node, Processor target, TraceFormatter formatter, Tracer tracer) {
        super(node, target, formatter, tracer);
    }

    @Override
    protected Object traceExchangeIn(Exchange exchange) throws Exception {
        _logger.warn("Received exchange " + exchange.getIn() + ", before processing by " + getNode());
        return null;
    }

    @Override
    protected void traceExchangeOut(Exchange exchange, Object traceState) throws Exception {
        _logger.warn("Received exchange " + exchange.getOut() + ", after processing by " + getNode());
    }

    @Override
    protected void logExchange(Exchange exchange) {
        // skip logging done by default Tracer
    }

}
