module ch.zhaw.grouping {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports ch.zhaw.grouping;
    exports ch.zhaw.grouping.ui;
    opens ch.zhaw.grouping.ui to javafx.graphics, javafx.fxml;
    opens ch.zhaw.grouping.ui.javafx to javafx.graphics, javafx.fxml;
    opens ch.zhaw.grouping.domain to javafx.base;
    opens ch.zhaw.grouping to javafx.fxml, javafx.graphics;
}
