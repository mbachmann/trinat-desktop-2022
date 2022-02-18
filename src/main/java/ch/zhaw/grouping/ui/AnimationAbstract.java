package ch.zhaw.grouping.ui;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;

public abstract class AnimationAbstract {
	private ModulExecution modul;
	private int currentGroupIndex;
	private StudentGroup currentGroup;
	private Student currentStudent;
	private int studentIndexInCurrentGroup = -1;

	public AnimationAbstract(ModulExecution modul) {
		super();
		this.modul = modul;
	}

	protected void reset(){
		currentGroupIndex = 0;
		if (modul.getGroups().size() > 0){
			currentGroup = modul.getGroups().get(currentGroupIndex);
		} else {
			currentGroup = null;
		}
		studentIndexInCurrentGroup = -1;
		currentStudent = null;
	}

	public void start(){
		reset();
	}

	protected void proceed() {
		if (currentStudent != null){
			endStudent(currentStudent);
			currentStudent = null;
		}
		if (currentGroup == null){
			finished();
		} else {
			if (studentIndexInCurrentGroup < 0){
				startGroup(currentGroup);
			} else {
				currentStudent = currentGroup.getStudents().get(studentIndexInCurrentGroup);
				showStudent(currentStudent);
			}
			studentIndexInCurrentGroup++;
			if (studentIndexInCurrentGroup >= currentGroup.getStudents().size()){
				endGroup(currentGroup);
				currentGroupIndex++;
				studentIndexInCurrentGroup = -1;
				if (currentGroupIndex >= modul.getGroups().size()){
					currentGroup = null;
				} else {
					currentGroup = modul.getGroups().get(currentGroupIndex);
				}
			}
		}
	}

	protected abstract void endStudent(Student student);

	protected abstract void endGroup(StudentGroup currentGroup);

	protected abstract void showStudent(Student student);

	protected abstract void startGroup(StudentGroup currentGroup);

	protected abstract void finished();

}
