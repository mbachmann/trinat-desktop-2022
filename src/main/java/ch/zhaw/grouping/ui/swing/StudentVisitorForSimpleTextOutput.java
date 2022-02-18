package ch.zhaw.grouping.ui.swing;

import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.domain.StudentVisitorAbstract;

public class StudentVisitorForSimpleTextOutput extends StudentVisitorAbstract {
	private StringBuilder target;

	public StudentVisitorForSimpleTextOutput() {
		super();
		this.target = new StringBuilder();
	}

	public StudentVisitorForSimpleTextOutput(StringBuilder target) {
		super();
		this.target = target;
	}

	@Override
	public void visitStudent(Student student) {
		target.append(student.getName()).append(" ")
			.append(student.getFirstName()).append(" ").append(student.getEmail())
			.append("\n");
	}

	@Override
	public void visitStudentGroup(StudentGroup group) {
		target.append("Gruppe ").append(group.getName()).append("\n");
	}

	@Override
	public void visitStudentGroupEnd(StudentGroup group) {
		target.append("\n");
	}

	public String getResult(){
		return target.toString();
	}
}
