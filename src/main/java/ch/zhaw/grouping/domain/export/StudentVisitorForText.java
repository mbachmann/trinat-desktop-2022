package ch.zhaw.grouping.domain.export;

import java.io.FileWriter;
import java.io.IOException;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.domain.StudentVisitorAbstract;

public class StudentVisitorForText extends StudentVisitorAbstract {
	private StringBuilder target;

	public StudentVisitorForText() {
		super();
		this.target = new StringBuilder();
	}

	public StudentVisitorForText(StringBuilder target) {
		super();
		this.target = target;
	}

	@Override
	public void visitStudent(Student student) {
		target.append(student.getName()).append("\t")   // a
			.append(student.getFirstName()).append("\t")
			.append(student.getEmail()).append("\n");
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

	public static void printGroups(String textFile, ModulExecution modul){
		if (textFile != null){
			StudentVisitorForText visitor = new StudentVisitorForText();
			modul.receiveStudentVisitor(visitor);
			try (FileWriter writer = new FileWriter(textFile, false)) {
				writer.append(visitor.getResult());
			} catch (IOException e) {
				// ignore
			}
		}
	}

}
