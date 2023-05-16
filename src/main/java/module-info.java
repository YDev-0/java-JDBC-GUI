module ma.ydev0.dbguiproject.javajdbcgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;


    opens ma.ydev0.javajdbcgui to javafx.fxml;
    exports ma.ydev0.javajdbcgui;
}