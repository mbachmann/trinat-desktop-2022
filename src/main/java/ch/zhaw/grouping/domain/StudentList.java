package ch.zhaw.grouping.domain;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * List of students with some application specific methods.
 * @author fer
 *
 */
public class StudentList {
	private List<Student> students;

	public StudentList() {
		super();
		students = new ArrayList<>();
	}

	public StudentList(StudentList studentList) {
		super();
		students = new ArrayList<>(studentList.getStudents());
	}

	public void receiveStudentVisitor(StudentVisitor visitor){
		for(Student student : students){
			student.receiveStudentVisitor(visitor);
		}
	}

	public void printStudentList(PrintStream out){
		for(Student student : students){
			student.printStudent(out);
		}
	}

	public boolean isEmpty(){
		return students.isEmpty();
	}

	public boolean isNotEmpty(){
		return !students.isEmpty();
	}

	public List<Student> getStudents() {
		return students;
	}

	public void addStudent(Student student){
		students.add(student);
	}

	public Student addStudent(String name, String firstName, String email){
		Student student = new Student(name, firstName, email);
		students.add(student);
		return student;
	}

	protected void moveRandomIndexToGroup(Random random, StudentList otherGroup){
		int index = random.nextInt(students.size());
		moveToGroup(index, otherGroup);
	}

	protected void moveToGroup(int studentIndex, StudentList otherGroup){
		Student student = students.remove(studentIndex);
		otherGroup.addStudent(student);
	}

}
