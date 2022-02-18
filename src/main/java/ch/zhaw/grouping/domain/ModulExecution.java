package ch.zhaw.grouping.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModulExecution {
	private StudentList students = new StudentList();
	private List<StudentGroup> groups = new ArrayList<>();

	public ModulExecution() {
		super();
	}

	public void addEventoExcelImport(String eventoFileName) throws IOException {
		EventoExcelImportSession importer = new EventoExcelImportSession(eventoFileName, students.getStudents());
		importer.convert();
	}

	public void addEventoExcelAndGroupImport(String eventoFileName) throws IOException {
		EventoExcelAndGroupImportSession importer = new EventoExcelAndGroupImportSession(
				eventoFileName, students.getStudents(), groups);
		importer.convert();
	}

	public void executeGrouping(List<Integer> maxGroupSizes, int startGroupNbr)
	throws Exception{
		groups = new ArrayList<>();
		GroupingSession session = new GroupingSession(maxGroupSizes, startGroupNbr,
				students, groups);
		session.createGroups();
	}

//	public void printGroups(){
//		printGroups(System.out);
//	}
//
//	public void printGroups(PrintStream out){
//		boolean ignoreNewLine = true;
//		for(StudentGroup group : groups){
//			if (ignoreNewLine){
//				ignoreNewLine = false;
//			} else {
//				out.println();
//			}
//			group.printGroup(out);
//		}
//	}
//
//	public String createGroupsString(){
//		StringBuilder sb = new StringBuilder();
//		for(StudentGroup group : groups){
//			sb.append("Gruppe ").append(group.getName()).append("\n");
//			for(Student student : group.getStudents()){
//				sb.append(" - ").append(student.getName()).append(" ")
//					.append(student.getFirstName()).append(" ").append(student.getEmail())
//					.append("\n");
//			}
//		}
//		return sb.toString();
//	}

	public StudentList getStudents() {
		return students;
	}

	public List<StudentGroup> getGroups() {
		return groups;
	}

	public void receiveStudentVisitor(StudentVisitor visitor){
		visitor.visitModulExecution(this);
		for(StudentGroup group : groups){
			group.receiveStudentVisitor(visitor);
		}
		visitor.visitModulExecutionEnd(this);
	}
}
