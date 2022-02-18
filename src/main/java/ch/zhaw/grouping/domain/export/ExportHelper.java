package ch.zhaw.grouping.domain.export;

import ch.zhaw.grouping.domain.ModulExecution;

public class ExportHelper {

	public static void exportModuleExcecution(String absolutePath, ModulExecution modul){
	    if (absolutePath != null) {
            String excelImportFilePM3 = createExcelPM3FileName(absolutePath);
    //		String excelImportFilePsit3 = createExcelPsit3FileName(absolutePath);
    		String excelImportFileTxt = createGroupFileName(absolutePath);
            StudentVisitorForExcelTextImportPM3.printGroupsToExcelFile(excelImportFilePM3, modul);
    //		StudentVisitorForExcelTextImportPsit3.printGroupsToExcelFile(excelImportFilePsit3, modul);
    		StudentVisitorForText.printGroups(excelImportFileTxt, modul);
	    }
	}

    public static String createExcelPM3FileName(String absolutePath){
        String fileNameStart = extractFileNameStart(absolutePath);
        String excelPsit3FileName = fileNameStart + "-Excel-PM3.txt";
        return excelPsit3FileName;
    }

	public static String createExcelPsit3FileName(String absolutePath){
		String fileNameStart = extractFileNameStart(absolutePath);
		String excelPsit3FileName = fileNameStart + "-Excel-PSIT3.txt";
		return excelPsit3FileName;
	}

	public static String createGroupFileName(String absolutePath){
		String fileNameStart = extractFileNameStart(absolutePath);
		String groupFileName = fileNameStart + "-Gruppen.txt";
		return groupFileName;
	}

	protected static String extractFileNameStart(String absolutePath) {
		int dotIndex = absolutePath.lastIndexOf('.');
		String fileNameStart;
		if (dotIndex >= 0){
			fileNameStart = absolutePath.substring(0, dotIndex);
		} else {
			fileNameStart = absolutePath;
		}
		return fileNameStart;
	}
}
