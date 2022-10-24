module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.example;
    exports com.example.ui;
    opens com.example.ui to javafx.graphics, javafx.fxml;
    opens com.example to javafx.fxml, javafx.graphics;

}
