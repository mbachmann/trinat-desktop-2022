package ch.zhaw.grouping.evento;

import java.io.IOException;
import java.util.List;

public class CreateOlatMemberImport {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		EventoExcelImportToOlatConverter converter = new EventoExcelImportToOlatConverter("../SWE-Documents/SEPS/2013/IT12a.txt");
		converter.convert();
		List<String> result = converter.getResult();
		for(int i = 0; i < result.size(); i++){
			System.out.println(result.get(i));
		}
//
//		try (BufferedReader reader = new BufferedReader(new FileReader("../SWE-Documents/SEPS/2013/IT12a.txt"))){
//			String line = reader.readLine();
//			while (line != null){
//				String[] components = line.split("\t");
////				System.out.println("--" + line);
////				for(int i = 0; i < components.length; i++){
////					System.out.println(components[i]);
////				}
//				String email = components[4];
//				String[] emailComponents = email.split("@");
//				System.out.println(emailComponents[0]);
//				line = reader.readLine();
//			}
//		}
	}

}
