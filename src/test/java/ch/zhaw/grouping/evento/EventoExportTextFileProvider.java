package ch.zhaw.grouping.evento;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class EventoExportTextFileProvider {

	public static String createEventExportTextFile()
	throws IOException {
		File tempInputFile = File.createTempFile("EventoExcelImportToOlatConverterTest", "OLAT");
		PrintWriter writer = new PrintWriter(tempInputFile);
		writer.println("1 	Herr 	Name 	Vorname1 Vorname2 	12-938-155 	jA.Immatrikuliert 	T.BA.IT.13/13.1.16HS.taWIN 	email@students.zhaw.ch");

//		writer.println("1 	Herr 	Name	Vorname			08-xxx-xxx 	jA.Immatrikuliert	T.BA.IT.10/10.1.11HS.ta 	email@students.zhaw.ch");
		writer.close();
		return tempInputFile.getCanonicalPath();
	}
}
