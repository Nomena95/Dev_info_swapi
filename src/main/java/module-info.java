module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json.simple;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    exports com.data.demo1;
    opens com.data.demo1 to javafx.fxml;
}