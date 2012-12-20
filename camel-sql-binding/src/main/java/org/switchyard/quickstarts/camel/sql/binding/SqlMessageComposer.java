package org.switchyard.quickstarts.camel.sql.binding;

import org.apache.camel.Message;
import org.apache.camel.component.sql.SqlConstants;
import org.switchyard.Exchange;
import org.switchyard.component.camel.common.composer.CamelBindingData;
import org.switchyard.component.camel.common.composer.CamelMessageComposer;

public class SqlMessageComposer extends CamelMessageComposer {

    @Override
    public CamelBindingData decompose(Exchange exchange, CamelBindingData target) throws Exception {
        CamelBindingData decompose = super.decompose(exchange, target);
        Message message = decompose.getMessage();
        SqlOperation payload = message.getBody(SqlOperation.class);

        // flip the sqlOperation data to message body
        message.setBody(payload.getData());
        // and pass query to header to override SELECT 1 set in configuration
        String query = payload.getQuery();
        if (query != null) {
            message.setHeader(SqlConstants.SQL_QUERY, query);
        }
        return decompose;
    }

}
