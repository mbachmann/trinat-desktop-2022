package ch.zhaw.grouping.evento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class EventoExcelImportParserAbstract {
	public static final int COLUMN_FIRST_NAME = 3;
	public static final int COLUMN_LAST_NAME = 2;
	public static final int COLUMN_EMAIL = 7;

	private String inputFileName;

	public EventoExcelImportParserAbstract(String inputFileName) {
		super();
		this.inputFileName = inputFileName;
	}

	public void convert()  throws IOException {
		int lineNumber = 1;
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))){
			String line = reader.readLine();
			while (line != null){
				line = line.trim();
				if (line.length() > 0){
					if (line.charAt(0) == '#'){
						addGroupLineComponents(lineNumber, line.substring(1));
					} else {
						String[] components = splitEventoImportLine(line);
						addStudentLineComponents(lineNumber, components);
					}
				}
				line = reader.readLine();
				lineNumber++;
			}
		}
	}

	protected String[] splitEventoImportLine(String line) {
		String[] components = line.split("\t");
		String[] result = new String[components.length];
		for(int i = 0; i < components.length; i++) {
			result[i] = components[i].trim();
		}
		return result;
	}

	protected void addGroupLineComponents(int lineNumber,
			String group) throws IOException{
	}


	protected abstract void addStudentLineComponents(int lineNumber,
			String[] components) throws IOException;

}
