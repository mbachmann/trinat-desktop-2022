package ch.zhaw.grouping.evento;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.grouping.domain.Student;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.grouping.domain.EventoExcelImportSession;

public class EventoExcelImportSessionTest {
	private List<Student> students;
	private EventoExcelImportSession session;

	@Before
	public void setUp() throws Exception {
		String tempInputFile = EventoExportTextFileProvider.createEventExportTextFile();
		students = new ArrayList<>();
		session = new EventoExcelImportSession(tempInputFile, students);
	}

	@Test
	public void testCorrectLine() throws IOException {
		session.convert();
		assertEquals(1, students.size());
		assertEquals("Name", students.get(0).getName());
		assertEquals("Vorname1 Vorname2", students.get(0).getFirstName());
		assertEquals("email@students.zhaw.ch", students.get(0).getEmail());
	}

}
