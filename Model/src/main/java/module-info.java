module org.example {
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires java.sql;

    exports org.example;
    exports org.example.exceptions;
}