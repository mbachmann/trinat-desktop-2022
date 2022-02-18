package ch.zhaw.grouping.ui.swing;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.domain.StudentVisitorAbstract;

public class StudentVisitorForOutputToStyledDocument extends StudentVisitorAbstract {
	private StyledDocument document;
	private AttributeSet regularStyle;
	private AttributeSet emailStyle;
	private AttributeSet groupStyle;

	public StudentVisitorForOutputToStyledDocument(StyledDocument document,
			AttributeSet regularStyle, AttributeSet emailStyle, AttributeSet groupStyle) {
		super();
		this.document = document;
		this.regularStyle = regularStyle;
		this.emailStyle = emailStyle;
		this.groupStyle = groupStyle;
	}

	@Override
	public void visitStudent(Student student) {
		String text = new StringBuilder().append(student.getName()).append("\t")
			.append(student.getFirstName()).append("\t").toString();
		try {
			document.insertString(document.getLength(), text, regularStyle);
			document.insertString(document.getLength(), student.getEmail(), emailStyle);
			document.insertString(document.getLength(), "\n", regularStyle);
		} catch (BadLocationException e) {
			// ignore
		}
	}

	@Override
	public void visitStudentGroup(StudentGroup group) {
		String text = new StringBuilder().append("Gruppe ").append(group.getName())
				.append("\n").toString();
		try {
			document.insertString(document.getLength(), text, groupStyle);
		} catch (BadLocationException e) {
			// ignore
		}
	}

	@Override
	public void visitStudentGroupEnd(StudentGroup group) {
		try {
			document.insertString(document.getLength(), "\n", groupStyle);
		} catch (BadLocationException e) {
			// ignore
		}
	}

}
