package ch.zhaw.grouping.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GroupingSessionTest {
	private Student student0;
	private Student student1;
	private Student student2;
	private Student student3;
	private Student student4;
	private StudentList inputList;
	private List<StudentGroup> groupList;
	private List<Integer> maxGroupSizes;
	private GroupingSession session;

	@Before
	public void setUp() throws Exception {
		student0 = new Student("Name0", "Vorname0", "email0@zhaw.ch");
		student1 = new Student("Name1", "Vorname1", "email1@zhaw.ch");
		student2 = new Student("Name2", "Vorname2", "email2@zhaw.ch");
		student3 = new Student("Name3", "Vorname3", "email3@zhaw.ch");
		student4 = new Student("Name4", "Vorname4", "email4@zhaw.ch");
		inputList = new StudentList();
		groupList = new ArrayList<>();
		maxGroupSizes = new ArrayList<>();
		session = new GroupingSession(maxGroupSizes, 3, inputList, groupList);
	}

	@Test(expected=Exception.class)
	public void notCorrectSizesOfGroup() throws Exception {
		inputList.addStudent(student0);
		maxGroupSizes.add(3);
		session.createGroups();
		assertEquals(1, groupList.size());
		StudentGroup group = groupList.get(0);
		assertEquals(1, group.getStudents().size());
		assertEquals(student0, group.getStudents().get(0));
	}

	@Test
	public void group1Student() throws Exception {
		inputList.addStudent(student0);
		maxGroupSizes.add(1);
		session.createGroups();
		assertEquals(1, groupList.size());
		StudentGroup group = groupList.get(0);
		assertEquals(1, group.getStudents().size());
		assertEquals(student0, group.getStudents().get(0));
	}

	@Test
	public void group2Students() throws Exception {
		inputList.addStudent(student0);
		inputList.addStudent(student1);
		maxGroupSizes.add(2);
		session.createGroups();
		assertEquals(1, groupList.size());
		StudentGroup group = groupList.get(0);
		assertEquals(2, group.getStudents().size());
		assertNotNull(group.getStudents().get(0));
		assertNotNull(group.getStudents().get(1));
		assertTrue(group.getStudents().get(0) != group.getStudents().get(1));
	}

	@Test
	public void group3Students() throws Exception {
		inputList.addStudent(student0);
		inputList.addStudent(student1);
		inputList.addStudent(student2);
		maxGroupSizes.add(2);
		maxGroupSizes.add(1);
		session.createGroups();

		assertEquals(2, groupList.size());
		StudentGroup group0 = groupList.get(0);
		assertEquals("3", group0.getName());
		assertEquals(2, group0.getStudents().size());
		assertNotNull(group0.getStudents().get(0));
		assertNotNull(group0.getStudents().get(1));
		assertTrue(group0.getStudents().get(0) != group0.getStudents().get(1));

		StudentGroup group1 = groupList.get(1);
		assertEquals("4", group1.getName());
		assertEquals(1, group1.getStudents().size());
		assertNotNull(group1.getStudents().get(0));
	}

	@Test
	public void group5Students() throws Exception {
		inputList.addStudent(student0);
		inputList.addStudent(student1);
		inputList.addStudent(student2);
		inputList.addStudent(student3);
		inputList.addStudent(student4);
		maxGroupSizes.add(2);
		maxGroupSizes.add(3);
		session.createGroups();

		assertEquals(2, groupList.size());
		StudentGroup group0 = groupList.get(0);
		assertEquals("3", group0.getName());
		assertEquals(2, group0.getStudents().size());
		assertNotNull(group0.getStudents().get(0));
		assertNotNull(group0.getStudents().get(1));
		assertTrue(group0.getStudents().get(0) != group0.getStudents().get(1));
		group0.printGroup(System.out);

		StudentGroup group1 = groupList.get(1);
		assertEquals("4", group1.getName());
		assertEquals(3, group1.getStudents().size());
		assertNotNull(group1.getStudents().get(0));
		assertNotNull(group1.getStudents().get(1));
		assertNotNull(group1.getStudents().get(2));
		group1.printGroup(System.out);
	}

}
