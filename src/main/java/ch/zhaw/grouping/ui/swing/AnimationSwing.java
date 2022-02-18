package ch.zhaw.grouping.ui.swing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.ui.AnimationAbstract;

public class AnimationSwing extends AnimationAbstract {
	private Timer timer;
	private int groupFontSize;
	private int studentFontSize;
	private JLabel lblCurrentResult;
	private JTabbedPane tabbedPaneResult;

	public AnimationSwing(ModulExecution modul, int animationIntervall,
                          int groupFontSize, int studentFontSize,
                          JLabel lblCurrentResult, JTabbedPane tabbedPaneResult) {
		super(modul);
		this.groupFontSize = groupFontSize;
		this.studentFontSize = studentFontSize;
		this.lblCurrentResult = lblCurrentResult;
		this.tabbedPaneResult = tabbedPaneResult;
		timer = new Timer(animationIntervall, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				proceed();
			}
		});
	}

	@Override
	public void start() {
		super.start();
		timer.start();
	}

	@Override
	protected void reset() {
		super.reset();
		tabbedPaneResult.setSelectedIndex(0);
		lblCurrentResult.setText(" ");
	}

	@Override
	protected void startGroup(StudentGroup currentGroup) {
		lblCurrentResult.setFont(new Font("Tahoma", Font.PLAIN, groupFontSize));
		lblCurrentResult.setText("Gruppe " + currentGroup.getName());
	}

	@Override
	protected void showStudent(Student student) {
		lblCurrentResult.setFont(new Font("Tahoma", Font.PLAIN, studentFontSize));
		lblCurrentResult.setText(student.getName() + "  " + student.getFirstName() + "    " + student.getEmail());
	}

	@Override
	protected void endStudent(Student student) {
	}

	@Override
	protected void endGroup(StudentGroup currentGroup) {
	}

	@Override
	protected void finished() {
		timer.stop();
		timer = null;
		tabbedPaneResult.setSelectedIndex(1);
	}
}
