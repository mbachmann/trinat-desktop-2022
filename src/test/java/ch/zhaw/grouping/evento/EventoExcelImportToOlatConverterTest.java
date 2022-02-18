package ch.zhaw.grouping.evento;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EventoExcelImportToOlatConverterTest {
	private EventoExcelImportToOlatConverter converter;

	@Before
	public void setUp() throws Exception {
		String tempInputFile = EventoExportTextFileProvider.createEventExportTextFile();
		converter = new EventoExcelImportToOlatConverter(tempInputFile);
	}

	@Test
	public void test() throws IOException {
		converter.convert();
		List<String> result = converter.getResult();
		assertEquals(1, result.size());
		assertEquals("email", result.get(0));
	}

}
