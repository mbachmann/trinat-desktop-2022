package ch.zhaw.grouping.domain.export;

import java.io.FileWriter;
import java.io.IOException;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.domain.StudentVisitorAbstract;

public class StudentVisitorForExcelTextImportPsit3 extends StudentVisitorAbstract {
	private StringBuilder target;
	private int lineNbr;
	private int groupLineNbr;

	public StudentVisitorForExcelTextImportPsit3() {
		super();
		this.target = new StringBuilder();
	}

	public StudentVisitorForExcelTextImportPsit3(StringBuilder target) {
		super();
		this.target = target;
	}

	@Override
	public void visitModulExecution(ModulExecution execution) {
		//            a b c  d   e           f
		target.append("\t\t\tPL\tPS Technik\tPS Sprache")
		     //        g              h         i               j
			.append("\tL-Architektur\tResultat\tProjektbericht\tBedienungsanleitung")
			//         k     l           m           n     o
			.append("\tTeam\tIndividual\tTeambeitrag\tNote\tNote(gerundet)")
			.append("\n");
		lineNbr = 2;
	}

	@Override
	public void visitStudent(Student student) {
		target.append(student.getName()).append("\t")   // a
			.append(student.getFirstName()).append("\t") // b
			.append(student.getEmail()).append("\t") // c
			.append("\t") // d
			.append("\t") // e
			.append("\t") // f
			.append("\t") // g
			.append("\t") // h
			.append("\t") // i
			.append("\t") // j
			.append(String.format("=K$%s", groupLineNbr)).append("\t") // k
			.append("0.00\t") // l individual
			.append("0.00\t") // m Teambeitrag
			.append(String.format("=K$%s+L$%s+M$%s", lineNbr, lineNbr, lineNbr)).append("\t") // n, =K6+L6+M6
			.append(String.format("=RUNDEN(N$%s*2;0)/2", lineNbr)) // n
			.append("\n");
		lineNbr++;
	}

	@Override
	public void visitStudentGroup(StudentGroup group) {
		target.append("Gruppe ")
			.append(group.getName())
			.append("\t") // a
			.append("\t") // b
			.append("\t") // c
			.append("\t") // d
			.append("1.00\t") // e
			.append("1.00\t") // f
			.append("1.00\t") // g
			.append("1.00\t") // h
			.append("1.00\t") // i
			.append("1.00\t") // j
			.append(String.format("=(E%1$s+F%1$s+G%1$s*3+H%1$s*3+I%1$s+J%1$s)/10", lineNbr)).append("\t") // k
			.append("\t") // l
			.append("\t") // m
			.append("\t") // n
			.append("\n"); // o
		groupLineNbr = lineNbr;
		lineNbr++;
	}

	@Override
	public void visitStudentGroupEnd(StudentGroup group) {
		target.append("\n");
		lineNbr++;
	}

	public String getResult(){
		return target.toString();
	}

	public static void printGroupsToExcelFile(String excelImportFile, ModulExecution modul){
		if (excelImportFile != null){
			StudentVisitorForExcelTextImportPsit3 visitor = new StudentVisitorForExcelTextImportPsit3();
			modul.receiveStudentVisitor(visitor);
			try (FileWriter writer = new FileWriter(excelImportFile, false)) {
				writer.append(visitor.getResult());
			} catch (IOException e) {
				// ignore
			}
		}
	}

}
