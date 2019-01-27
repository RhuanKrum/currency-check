module logic {
    requires gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    exports com.currencycheck.factory;
    exports com.currencycheck.service;
    exports com.currencycheck.util;
    exports com.currencycheck.model;
}