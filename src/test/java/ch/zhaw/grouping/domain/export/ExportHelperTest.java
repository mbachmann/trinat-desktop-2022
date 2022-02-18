package ch.zhaw.grouping.domain.export;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ExportHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void createExcelPsit3FileName() {
		assertEquals("C:\\Folder\\filename-Excel-PSIT3.txt",
				ExportHelper.createExcelPsit3FileName("C:\\Folder\\filename.txt"));
		assertEquals("C:\\Folder\\filename.huhp-Excel-PSIT3.txt",
				ExportHelper.createExcelPsit3FileName("C:\\Folder\\filename.huhp.txt"));
	}

	@Test
	public void createGroupFileName() {
		assertEquals("C:\\Folder\\filename-Gruppen.txt",
				ExportHelper.createGroupFileName("C:\\Folder\\filename.txt"));
		assertEquals("C:\\Folder\\filename.huhp-Gruppen.txt",
				ExportHelper.createGroupFileName("C:\\Folder\\filename.huhp.txt"));
	}
}
