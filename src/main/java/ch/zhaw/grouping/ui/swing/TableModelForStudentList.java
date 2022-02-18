package ch.zhaw.grouping.ui.swing;

import javax.swing.table.AbstractTableModel;

import ch.zhaw.grouping.domain.Student;
import ch.zhaw.grouping.domain.StudentList;

/**
 * Implementation of TableModel which inherits from AbstractTableModel and delegates
 * to a StudentList object.
 * @author fer
 *
 */
public class TableModelForStudentList extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private StudentList studentList;

	public TableModelForStudentList(StudentList studentList) {
		super();
		this.studentList = studentList;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return studentList.getStudents().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Student student = studentList.getStudents().get(row);
		switch(column){
		case 0:
			return student.getName();
		case 1:
			return student.getFirstName();
		case 2:
			return student.getEmail();
		default:
			return "";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public String getColumnName(int column) {
		switch(column){
		case 0:
			return "Name";
		case 1:
			return "Vorname";
		case 2:
			return "E-Mail";
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Student student = studentList.getStudents().get(rowIndex);
		switch(columnIndex){
		case 0:
			student.setName(aValue.toString());
			break;
		case 1:
			student.setFirstName(aValue.toString());
			break;
		case 2:
			student.setEmail(aValue.toString());
			break;
		}
	}

	public void removeIndex(int index){
		studentList.getStudents().remove(index);
		fireTableRowsDeleted(index, index);
	}

	public void addEmptyStudent(int index){
		Student student = new Student("", "", "");
		studentList.getStudents().add(index, student);
		fireTableRowsInserted(index, index);
	}

	public void removeAll() {
		int size = studentList.getStudents().size();
		if (size > 0){
			studentList.getStudents().clear();
			fireTableRowsDeleted(0, size-1);
		}
	}
}
