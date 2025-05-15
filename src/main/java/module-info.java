module com.example.financemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.financemanager to javafx.fxml;
    exports com.example.financemanager;
    exports com.example.financemanager.classe;
    opens com.example.financemanager.classe to javafx.fxml;
    exports com.example.financemanager.controller;
    opens com.example.financemanager.controller to javafx.fxml;
    exports com.example.financemanager.repository;
    opens com.example.financemanager.repository to javafx.fxml;
}