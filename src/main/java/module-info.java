module com.university {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.naming;


    requires mysql.connector.j;

    // Required for Java SQL APIs like Connection, SQLException, etc.
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens com.university.model to org.hibernate.orm.core, javafx.base;
    opens com.university to javafx.fxml;
    exports com.university;
    exports com.university.ui;
    opens com.university.ui to javafx.fxml;
}