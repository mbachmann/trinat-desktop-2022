package ch.zhaw.grouping.domain;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class GroupingSession {
	private List<Integer> maxGroupSizes;
	private int startGroupNbr;
	private StudentList students;
	private List<StudentGroup> groups;

	public GroupingSession(List<Integer> maxGroupSizes, int startGroupNbr,
			StudentList students, List<StudentGroup> groups) {
		super();
		this.maxGroupSizes = maxGroupSizes;
		this.startGroupNbr = startGroupNbr;
		this.students = students;
		this.groups = groups;
	}

	public void createGroups() throws Exception{
		int sumOfMaxGroupSizes = 0;
		for(Integer groupSize : maxGroupSizes){
			sumOfMaxGroupSizes = sumOfMaxGroupSizes + groupSize;
		}
		if (sumOfMaxGroupSizes != students.getStudents().size()){
			throw new Exception(String.format("Summe der Gruppengrössen (%s) ist ungleich der Anzahl Teilnehmer (%s)",
					sumOfMaxGroupSizes, students.getStudents().size()));
		}
		Random random = new SecureRandom();
		StudentList remainingStudents = new StudentList(students);
		StudentGroup currentGroup = new StudentGroup(Integer.toString(startGroupNbr++));
		groups.add(currentGroup);
		while (remainingStudents.isNotEmpty()){
//			int index = random.nextInt(remainingStudents.size());
//			Student currentStudent = remainingStudents.remove(index);
			int maxGroupSize = maxGroupSizes.get(groups.size()-1);
			if (currentGroup.getStudents().size() >= maxGroupSize){
				currentGroup = new StudentGroup(Integer.toString(startGroupNbr++));
				groups.add(currentGroup);
			}
			remainingStudents.moveRandomIndexToGroup(random, currentGroup);
//			currentGroup.addStudent(currentStudent);
		}
	}
}
