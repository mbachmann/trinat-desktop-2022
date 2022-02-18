package ch.zhaw.grouping.domain;

import java.io.PrintStream;

/**
 * Relevant information for one student.
 * @author fer
 *
 */
public class Student {
	private String name;
	private String firstName;
	private String email;

	public Student(String name, String firstName, String email) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.email = email;
	}

	public Student() {
		this("","","");
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", firstName=" + firstName
				+ ", email=" + email + "]";
	}

	public void receiveStudentVisitor(StudentVisitor visitor){
		visitor.visitStudent(this);
	}

	public void printStudent(PrintStream out){
		out.println(getName() + " " + getFirstName() + " " + getEmail());
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
