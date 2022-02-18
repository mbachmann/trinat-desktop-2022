package ch.zhaw.grouping.excel;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.domain.export.StudentVisitorForExcelTextImportSeps;
import org.junit.Before;
import org.junit.Test;

public class ExcelTextImportTest {
	private Student student0;
	private Student student1;
	private Student student2;
	private Student student3;
	private Student student4;
	private ModulExecution modul;

	@Before
	public void setUp() throws Exception {
		student0 = new Student("Name0", "Vorname0", "email0@zhaw.ch");
		student1 = new Student("Name1", "Vorname1", "email1@zhaw.ch");
		student2 = new Student("Name2", "Vorname2", "email2@zhaw.ch");
		student3 = new Student("Name3", "Vorname3", "email3@zhaw.ch");
		student4 = new Student("Name4", "Vorname4", "email4@zhaw.ch");
	}

	@Test
	public void excelOutputSimple() throws Exception {
		modul = new ModulExecution();
		StudentGroup group0 = new StudentGroup("1");
		group0.addStudent(student0);
		modul.getGroups().add(group0);
		StudentVisitorForExcelTextImportSeps visitor = new StudentVisitorForExcelTextImportSeps();
		modul.receiveStudentVisitor(visitor);
	}

	@Test
	public void excelOutput2() throws Exception {
		modul = new ModulExecution();
		StudentGroup group0 = new StudentGroup("1");
		group0.addStudent(student0);
		group0.addStudent(student1);
		group0.addStudent(student2);
		modul.getGroups().add(group0);
		StudentGroup group1 = new StudentGroup("2");
		modul.getGroups().add(group1);
		group1.addStudent(student3);
		group1.addStudent(student4);
		StudentVisitorForExcelTextImportSeps visitor = new StudentVisitorForExcelTextImportSeps();
		modul.receiveStudentVisitor(visitor);
	}
}
