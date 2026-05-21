module com.example.assignment3 {
    requires java.sql;
    requires java.desktop;
    requires java.logging;

    exports presentation;
    exports model;
    exports businessLogic;
    exports dataAccess;
    exports connection;
}