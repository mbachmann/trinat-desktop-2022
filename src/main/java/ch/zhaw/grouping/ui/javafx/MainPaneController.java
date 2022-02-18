
package ch.zhaw.grouping.ui.javafx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.export.ExportHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


public class MainPaneController {
	public static final int STUDENT_FONT_SIZE = 32;
	public static final int GROUP_FONT_SIZE = 48;
	private ModulExecution modul = new ModulExecution();
	private Stage primaryStage;
//	private String excelImportFileSeps;
//	private String excelImportFilePsit3;
//	private String excelImportFileTxt;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> studentTableNameColumn;

    @FXML
    private TableColumn<Student, String> studentTableFirstNameColumn;

    @FXML
    private TableColumn<Student, String> studentTableEmailColumn;

    @FXML
    private TextField textFieldEventoExcelExport;

    @FXML
    private TextField textFieldGroupSize;

    @FXML
    private TextField textFieldStudentSize;

    @FXML
    private Button btnStartGrouping;

    @FXML
    private Label animationLabel;

    @FXML
    private TextArea resultTextArea;

	private ObservableList<Student> studentTableModel;
	private String inputFileAbsolutePath;

    @FXML
    void selectInputFile(ActionEvent event) {
		String currentDirectory = System.getProperty("user.dir");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(currentDirectory));
		File file = fileChooser.showOpenDialog(primaryStage);
		if (file != null){
			inputFileAbsolutePath = file.getAbsolutePath();
			textFieldEventoExcelExport.setText(inputFileAbsolutePath);
			inputFileNameSet(null);
//			int dotIndex = absolutePath.indexOf('.');
//			String fileNameStart;
//			if (dotIndex >= 0){
//				fileNameStart = absolutePath.substring(0, dotIndex);
//			} else {
//				fileNameStart = absolutePath;
//			}
//			excelImportFileSeps = fileNameStart + "-Excel-SEPS.txt";
//			excelImportFilePsit3 = fileNameStart + "-Excel-PSIT3.txt";
//			excelImportFileTxt = fileNameStart + "-Gruppen.txt";
		}
    }

    @FXML
    void inputFileNameSet(ActionEvent event) {
		try {
			modul.addEventoExcelImport(textFieldEventoExcelExport.getText());
			initializeStudentTableModel();
		} catch (IOException e) {
			ModalDialog.show(primaryStage, "Fehler", e.toString());
			return;
		}
    }

    private void initializeStudentTableModel() {
        studentTableModel = FXCollections.observableList(modul.getStudents().getStudents());
        studentTable.setItems(studentTableModel);
        studentSizeChanged();
    }


	private void studentSizeChanged() {
		int studentCount = studentTableModel.size();
        textFieldStudentSize.setText(Integer.toString(studentCount));
	    btnStartGrouping.setDisable(studentCount <= 0);
	}

    @FXML
    void startGrouping(ActionEvent event) {
		try {
			ArrayList<Integer> groupSizes = new ArrayList<>();
			String[] groupSizesString = textFieldGroupSize.getText().trim().split(",");
			if (groupSizesString.length == 0){
				ModalDialog.show(primaryStage, "Eingabefehler", "Eingabefeld für Gruppengrösse hat keinen Inhalt");
				return;
			}
			for(String groupSizeString : groupSizesString){
				int groupSize = Integer.parseInt(groupSizeString);
				groupSizes.add(groupSize);
			}
			modul.executeGrouping(groupSizes, 1);
			ExportHelper.exportModuleExcecution(inputFileAbsolutePath, modul);
			startResultShowSession();
		} catch (NumberFormatException e) {
			ModalDialog.show(primaryStage, "Eingabefehler", "Eingabefeld für Gruppengrösse hat ungültiges Format. Nur Zahlen mit Komma getrennt sind erlaubt.");
			return;
		} catch (Exception e) {
			ModalDialog.show(primaryStage, "Fehler", e.toString());
			return;
		}
   }

    private void startResultShowSession() {
    	AnimationFX animation = new AnimationFX(modul, GROUP_FONT_SIZE, STUDENT_FONT_SIZE,
    			animationLabel, resultTextArea);
    	animation.start();
	}

	@FXML
	protected void removeAllStudents() {
		studentTableModel.clear();
        studentSizeChanged();
	}

    @FXML
	protected void removeCurrentStudent() {
		TableViewSelectionModel<Student> selectionModel = studentTable.getSelectionModel();
		if (!selectionModel.isEmpty()){
			int index = selectionModel.getSelectedIndex();
			studentTableModel.remove(index);
		}
		studentSizeChanged();
	}

    @FXML
	protected void addEmptyStudentToStudentList() {
		int index = studentTableModel.size();
		TableViewSelectionModel<Student> selectionModel = studentTable.getSelectionModel();
		if (selectionModel.getSelectedIndex() >= 0){
			index = selectionModel.getSelectedIndex() + 1;
		}
		studentTableModel.add(index, new Student("Name", "Vorname", "e-mail"));
		studentTable.scrollTo(index);
		selectionModel.select(index);
		studentSizeChanged();
	}

    @FXML
 	protected void editStudentNameCommit(CellEditEvent<Student, String> event) {
        Student student = ((Student) event.getTableView().getItems().get(
        		event.getTablePosition().getRow()));
        student.setName(event.getNewValue());
    }

    @FXML
 	protected void editStudentFirstNameCommit(CellEditEvent<Student, String> event) {
        Student student = ((Student) event.getTableView().getItems().get(
        		event.getTablePosition().getRow()));
        student.setFirstName(event.getNewValue());
    }

    @FXML
 	protected void editStudentEmailCommit(CellEditEvent<Student, String> event) {
        Student student = ((Student) event.getTableView().getItems().get(
        		event.getTablePosition().getRow()));
        student.setEmail(event.getNewValue());
    }

    @FXML
    void initialize() {
        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFactory =
                new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
                    public TableCell<Student, String> call(TableColumn<Student, String> p) {
                       return new EditingCell();
                    }
                };
    	studentTableNameColumn.setCellFactory(cellFactory);
    	studentTableNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("name"));
    	studentTableNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<Student,String>>() {
			@Override
			public void handle(CellEditEvent<Student, String> event) {
				editStudentNameCommit(event);
			}
		});
    	studentTableFirstNameColumn.setCellFactory(cellFactory);
    	studentTableFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName"));
    	studentTableFirstNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<Student,String>>() {
			@Override
			public void handle(CellEditEvent<Student, String> event) {
				editStudentFirstNameCommit(event);
			}
		});
    	studentTableEmailColumn.setCellFactory(cellFactory);
    	studentTableEmailColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("email"));
    	studentTableEmailColumn.setOnEditCommit(new EventHandler<CellEditEvent<Student,String>>() {
			@Override
			public void handle(CellEditEvent<Student, String> event) {
				editStudentEmailCommit(event);
			}
		});
    	initializeStudentTableModel();
    }

    class EditingCell extends TableCell<Student, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
