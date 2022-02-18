package ch.zhaw.grouping.domain;

import java.io.IOException;
import java.util.List;

public class EventoExcelAndGroupImportSession extends EventoExcelImportSession {
	private List<StudentGroup> groups;
	private StudentGroup currentGroup;

	public EventoExcelAndGroupImportSession(String inputFileName,
			List<Student> result, List<StudentGroup> groups) {
		super(inputFileName, result);
		this.groups = groups;
	}

	@Override
	protected void addStudent(Student student) {
		super.addStudent(student);
		if (currentGroup == null){
			throw new IllegalStateException("No group defined");
		}
		currentGroup.addStudent(student);
	}

	@Override
	protected void addGroupLineComponents(int lineNumber, String group)
	throws IOException {
		int groupNumber;
		try {
			int firstBlankIndex = group.indexOf(' ');
			if (firstBlankIndex > 0){
				group = group.substring(0, firstBlankIndex);
			}
			groupNumber = Integer.parseInt(group);
		} catch (NumberFormatException e) {
			throw new IOException("Illegeal number format on line " + lineNumber + " : " + group);
		}
		if (groupNumber < 1){
			throw new IOException("Group number must be greater than 0 on line " + lineNumber + " : " + group);
		}
		while (groupNumber > groups.size()){
			groups.add(new StudentGroup(Integer.toString(groups.size()+1)));
		}
		currentGroup = groups.get(groupNumber-1);
	}

}
