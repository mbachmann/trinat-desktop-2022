package ch.zhaw.grouping.evento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventoExcelImportToOlatConverter extends EventoExcelImportParserAbstract {
	private List<String> result = new ArrayList<>();

	public EventoExcelImportToOlatConverter(String inputFileName) {
		super(inputFileName);
	}

	@Override
	protected void addStudentLineComponents(int lineNumber, String[] components)
	throws IOException {
		if (components.length != 8){
			throw new IOException("Parsing error in line'" + lineNumber + "': Found "
					+ components.length + " tabs instead of 6");
		}
		String email = null;
		for(int i = 0; i < components.length; i++){
			if (components[i].contains("@")){
				email = components[i];
			}
		}
		String[] emailComponents = email.split("@");
		result.add(emailComponents[0]);
	}

	public List<String> getResult() {
		return result;
	}
}
