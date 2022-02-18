package ch.zhaw.grouping.domain;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.grouping.evento.EventoExcelImportParserAbstract;

public class EventoExcelImportSession extends EventoExcelImportParserAbstract {
	private List<Student> result = new ArrayList<>();

	public EventoExcelImportSession(String inputFileName, List<Student> result) {
		super(inputFileName);
		this.result = result;
	}

	@Override
	protected void addStudentLineComponents(int lineNumber, String[] components) {
	    Student student;
        if (components.length == 1) {
            student = new Student("","",components[0]);
        } else if (components.length == 3) {
            student = new Student(components[0],components[1],components[2]);
	    } else {
	        student = new Student(components[COLUMN_LAST_NAME],
				components[COLUMN_FIRST_NAME], components[COLUMN_EMAIL]);
	    }
		addStudent(student);
	}

	protected void addStudent(Student student) {
		result.add(student);
	}
}
