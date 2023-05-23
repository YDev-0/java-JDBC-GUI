module ma.ydev0.dbguiproject.javajdbcgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;


    opens ma.ydev0.javajdbcgui to javafx.fxml;
    opens ma.ydev0.javajdbcgui.entities to javafx.base;
    exports ma.ydev0.javajdbcgui;
}