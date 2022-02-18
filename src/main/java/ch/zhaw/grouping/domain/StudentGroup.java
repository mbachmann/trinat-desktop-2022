package ch.zhaw.grouping.domain;

import java.io.PrintStream;

/**
 * Extends StudentList with a name.
 * @author fer
 *
 */
public class StudentGroup extends StudentList {
	private String name;

	public StudentGroup(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "StudentGroup [name=" + name + "]";
	}

	public void receiveStudentVisitor(StudentVisitor visitor){
		visitor.visitStudentGroup(this);
		super.receiveStudentVisitor(visitor);
		visitor.visitStudentGroupEnd(this);
	}

	public void printGroup(PrintStream out){
		out.println("Gruppe " + getName());
		printStudentList(out);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
