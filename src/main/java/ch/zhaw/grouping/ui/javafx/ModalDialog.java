package ch.zhaw.grouping.ui.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalDialog {
	private Button btn;
	private Stage stage;

	public ModalDialog(final Stage stg, String title, String text) {
		stage = new Stage();
		// Initialize the Stage with type of modal
		stage.initModality(Modality.APPLICATION_MODAL);
		// Set the owner of the Stage
		stage.initOwner(stg);
		stage.setTitle(title);

		BorderPane border = new BorderPane();
		Scene scene = new Scene(border);
		Label textLabel = new Label(text);
	    AnchorPane anchorpane = new AnchorPane();
	    anchorpane.getChildren().add(textLabel);
	    AnchorPane.setBottomAnchor(textLabel, 25.0);
	    AnchorPane.setRightAnchor(textLabel, 15.0);
	    AnchorPane.setLeftAnchor(textLabel, 15.0);
	    AnchorPane.setTopAnchor(textLabel, 25.0);
		border.setCenter(anchorpane);

	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
		border.setBottom(hbox);

		btn = new Button();
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				stage.hide();
			}
		});
		btn.setText("OK");
		hbox.getChildren().add(btn);

		stage.setScene(scene);
    }

	public void show(){
		stage.show();
	}

	public static void show(Stage stage, String title, String text){
    	ModalDialog dialog = new ModalDialog(stage, title, text);
    	dialog.show();
	}
}
