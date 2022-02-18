package ch.zhaw.grouping.domain;

public interface StudentVisitor {
	void visitStudent(Student student);
	void visitStudentList(StudentList list);

	void visitStudentGroup(StudentGroup group);
	void visitStudentGroupEnd(StudentGroup group);

	void visitModulExecution(ModulExecution execution);
	void visitModulExecutionEnd(ModulExecution execution);
}
