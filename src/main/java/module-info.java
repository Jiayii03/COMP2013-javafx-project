module com.snakegame.chaujiayi_source_code_javafx_mvn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jlayer;

    requires org.testfx.junit5;
    requires javafx.media;
//    requires org.junit.jupiter.api;

    opens com.snakegame.chaujiayi_source_code_javafx_mvn to javafx.fxml, org.testfx.junit5;
    exports com.snakegame.chaujiayi_source_code_javafx_mvn;
}