package org.switchyard.quickstarts.camel.sql.binding;

public interface StoreService {

    <T> T execute(SqlOperation operation);

}
