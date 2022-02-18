package ch.zhaw.grouping.ui.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GroupingJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Grouping-JavaFx.fxml"));
		    Pane root = (Pane) loader.load();
		    MainPaneController controller = loader.getController();
		    controller.setPrimaryStage(primaryStage);
//			BorderPane root = new BorderPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    primaryStage.setTitle("Gruppen Auslosung (SWEN1 V-2022)");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
