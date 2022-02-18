package ch.zhaw.grouping.domain.export;

import java.io.FileWriter;
import java.io.IOException;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentGroup;
import ch.zhaw.grouping.domain.StudentVisitorAbstract;

public class StudentVisitorForExcelTextImportSeps extends StudentVisitorAbstract {
	private StringBuilder target;
	private int lineNbr;
	private int groupLineNbr;

	public StudentVisitorForExcelTextImportSeps() {
		super();
		this.target = new StringBuilder();
	}

	public StudentVisitorForExcelTextImportSeps(StringBuilder target) {
		super();
		this.target = target;
	}

	@Override
	public void visitModulExecution(ModulExecution execution) {
		//                   d   e              f        g       h        i          j     k    l               m             n        o      p           q     r
		target.append("\t\t\tPL\tProjektskizze\tAnalyse\tDesign\tSchluss\tProj.Mgmt\tTeam\tLog\tBed. Anleitung\tPr�sentation\tNoTechS\tTotal\tIndividual\tNote\tNote(gerundet)")
			.append("\n");
		lineNbr = 2;
	}

	@Override
	public void visitStudent(Student student) {
		target.append(student.getName()).append("\t")   // a
			.append(student.getFirstName()).append("\t") // b
			.append(student.getEmail()).append("\t") // c
			.append("\t").append("\t").append("\t").append("\t").append("\t").append("\t") // d, e, f, g, h, i
			.append(String.format("=J$%s", groupLineNbr)).append("\t") // j
			.append("1.00\t") // k
			.append(String.format("=L$%s", groupLineNbr)).append("\t") // l
			.append(String.format("=M$%s", groupLineNbr)).append("\t") // m
			.append(String.format("=(K$%s+L$%s+M$%s)/3", lineNbr, lineNbr, lineNbr)).append("\t")  // n, =(K6+L6+M6)/3
			.append(String.format("=(J$%s*3+N$%s)/4", lineNbr, lineNbr)).append("\t") // o, =(J6*3+N6) / 4
			.append("0.00\t") // p, individual
			.append(String.format("=O$%s+P$%s", lineNbr, lineNbr)).append("\t") // q, =o6+p6
			.append(String.format("=RUNDEN(Q$%s*2;0)/2", lineNbr))
			.append("\n");
		lineNbr++;
	}

	@Override
	public void visitStudentGroup(StudentGroup group) {
		target.append("Gruppe ").append(group.getName()).append("\t")
			.append("\t").append("\t").append("\t").append("1.00\t").append("1.00\t").append("1.00\t").append("1.00\t").append("1.00\t")
			.append(String.format("=(E%1$s+F%1$s+G%1$s+H%1$s+I%1$s)/5", lineNbr)).append("\t") // j
			.append("\t") // k
			.append("1.00\t") // l
			.append("1.00\t") // m
			.append("\n");
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
			StudentVisitorForExcelTextImportSeps visitor = new StudentVisitorForExcelTextImportSeps();
			modul.receiveStudentVisitor(visitor);
			try (FileWriter writer = new FileWriter(excelImportFile, false)) {
				writer.append(visitor.getResult());
			} catch (IOException e) {
				// ignore
			}
		}
	}

}
