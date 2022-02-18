package ch.zhaw.grouping.ui.javafx;


import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.ui.AnimationAbstract;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class AnimationFX extends AnimationAbstract {
	private int groupFontSize;
	private int studentFontSize;
    private Label animationLabel;
    private TextArea resultTextArea;

    public AnimationFX(ModulExecution modul, int groupFontSize,
                       int studentFontSize, Label animationLabel, TextArea resultTextArea) {
		super(modul);
		this.groupFontSize = groupFontSize;
		this.studentFontSize = studentFontSize;
		this.animationLabel = animationLabel;
		this.resultTextArea = resultTextArea;
	}

	@Override
	protected void reset() {
    	super.reset();
    	resultTextArea.setText("");
    	resultTextArea.setOpacity(0.2);
    	animationLabel.setText("");
    	animationLabel.setOpacity(0);
    	animationLabel.setTextFill(Color.BLACK);
	}

	@Override
	public void start() {
		super.start();
        Platform.runLater(AnimationFX.this::proceed);
	}

	@Override
	protected void startGroup(final StudentGroup currentGroup) {
        final String text = "Gruppe " + currentGroup.getName();
        animationLabel.setText(text);
		animationLabel.setFont(new Font("Tahoma", groupFontSize));

		FadeTransition showingTransition = new FadeTransition(Duration.millis(700), animationLabel);
        showingTransition.setFromValue(0.0);
        showingTransition.setToValue(1.0);
//        showingTransition.setCycleCount(1);
//        showingTransition.setAutoReverse(false);

		FadeTransition hidingTransition = new FadeTransition(Duration.millis(200), animationLabel);
		hidingTransition.setDelay(Duration.millis(500));
		hidingTransition.setFromValue(1.0f);
		hidingTransition.setToValue(0.0f);

		SequentialTransition sequence = new SequentialTransition(animationLabel, showingTransition,
				new PauseTransition(Duration.millis(1000)), hidingTransition);
		sequence.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        String resultText = resultTextArea.getText() + "\n" + "\n" + text;
		        resultTextArea.setText(resultText);
		        Platform.runLater(AnimationFX.this::proceed);
			}
		});
		sequence.play();
	}

	@Override
	protected void showStudent(Student student) {
		double duration = 2500;
		Font font = new Font("Tahoma", studentFontSize);
		animationLabel.setFont(font);
		FadeTransition showingTransition = new FadeTransition(Duration.millis(duration), animationLabel);
        showingTransition.setFromValue(0.2);
        showingTransition.setToValue(1.0);
        showingTransition.setCycleCount(1);
        showingTransition.setAutoReverse(false);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(duration/5), animationLabel);
        rotateTransition.setFromAngle(0.0);
        rotateTransition.setToAngle(0.0);
        rotateTransition.setCycleCount(5);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), animationLabel);
        scaleTransition.setFromX(0.1);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToY(1);
        scaleTransition.setFromZ(1);
        scaleTransition.setToZ(1);

//        Timeline fontSizeTimeline = new Timeline();
//        fontSizeTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration),
//        		new KeyValue(font.getSize(), studentFontSize)));

        final String text = student.getFirstName() + " " + student.getName() + " " + student.getEmail();
        animationLabel.setOpacity(0.0d);
        animationLabel.setText(text);

		FadeTransition hidingTransition = new FadeTransition(Duration.millis(200), animationLabel);
//		hidingTransition.setDelay(Duration.millis(500));
		hidingTransition.setFromValue(1.0);
		hidingTransition.setToValue(0.0);
		ParallelTransition parallelTransition = new ParallelTransition(animationLabel, scaleTransition, showingTransition, rotateTransition);
		SequentialTransition transition = new SequentialTransition(animationLabel,
				parallelTransition, new PauseTransition(Duration.millis(1500)), hidingTransition);

        transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        animationLabel.setOpacity(0.0d);
//				proceed();
		        resultTextArea.appendText("\n" + text);
//		        String resultText = resultTextArea.getText() + "\n" + text;
//		        resultTextArea.setText(resultText);
		        Platform.runLater(AnimationFX.this::proceed);
			}
		});
		transition.play();
	}

	@Override
	protected void endStudent(Student student) {
	}

	@Override
	protected void endGroup(StudentGroup currentGroup) {
	}

	@Override
	protected void finished() {
    	resultTextArea.setOpacity(1);
//    	resultTextArea.setStyle("fx-font: 20px");//Font(new Font("Tahoma", groupFontSize));
        animationLabel.setText("");
        animationLabel.setOpacity(0.0);
	}

}
