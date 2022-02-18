package ch.zhaw.grouping.domain;

/**
 * Implementation of StudentVisitor which does nothing
 * @author fer
 *
 */
public class StudentVisitorAbstract implements StudentVisitor {

	@Override
	public void visitStudent(Student student) {
	}

	@Override
	public void visitStudentList(StudentList list) {
	}

	@Override
	public void visitStudentGroup(StudentGroup group) {
	}

	@Override
	public void visitStudentGroupEnd(StudentGroup group) {
	}

	@Override
	public void visitModulExecution(ModulExecution execution) {
	}

	@Override
	public void visitModulExecutionEnd(ModulExecution execution) {
	}
}
