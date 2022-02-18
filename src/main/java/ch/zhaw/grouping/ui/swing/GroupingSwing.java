package ch.zhaw.grouping.ui.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import ch.zhaw.grouping.domain.ModulExecution;
import ch.zhaw.grouping.domain.export.ExportHelper;

public class GroupingSwing {
	public static final int STUDENT_FONT_SIZE = 32;
	public static final int GROUP_FONT_SIZE = 48;
	public static final int ANIMATION_DURATION_MILLI = 1200;

	private ModulExecution modul = new ModulExecution();
	private JFrame frmGruppeneinteilungVornehmen;
	private JTextField textFieldEventoExcelExport;
	private JTable studentTable;
	private JTextField textFieldGroupSize;
//	private Timer resultTimer;
	private JLabel lblCurrentResult;
	private TableModelForStudentList studentTableModel;
	private JTabbedPane tabbedPaneResult;
	private JTextPane textPaneResultArea;
	private JTextField textFieldStudentSize;
	private Style emailStyle;
//	private String excelImportFile;
	private Style regularStyle;
	private Style groupStyle;
	private String inputFileAbsolutePath;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupingSwing window = new GroupingSwing();
					window.frmGruppeneinteilungVornehmen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GroupingSwing() {
		setNimusLookAndFeel();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGruppeneinteilungVornehmen = new JFrame();
		frmGruppeneinteilungVornehmen.setTitle("Gruppeneinteilung vornehmen");
		frmGruppeneinteilungVornehmen.setBounds(100, 100, 600, 546);
		frmGruppeneinteilungVornehmen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new EmptyBorder(3, 3, 3, 3));
		frmGruppeneinteilungVornehmen.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelStudents = new JPanel();
		tabbedPane.addTab("Studierende", null, panelStudents, null);
		GridBagLayout gbl_panelStudents = new GridBagLayout();
		gbl_panelStudents.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_panelStudents.rowWeights = new double[]{0.0, 1.0, 1.0};
		panelStudents.setLayout(gbl_panelStudents);

		JLabel lblEventoExcelExportFile = new JLabel("Evento-Excel-Export Datei");
		GridBagConstraints gbc_lblEventoExcelExportFile = new GridBagConstraints();
		gbc_lblEventoExcelExportFile.anchor = GridBagConstraints.WEST;
		gbc_lblEventoExcelExportFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblEventoExcelExportFile.gridx = 0;
		gbc_lblEventoExcelExportFile.gridy = 0;
		panelStudents.add(lblEventoExcelExportFile, gbc_lblEventoExcelExportFile);

		textFieldEventoExcelExport = new JTextField();
		textFieldEventoExcelExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputFileNameSet();
			}
		});
		GridBagConstraints gbc_textFieldEventoExcelExport = new GridBagConstraints();
		gbc_textFieldEventoExcelExport.weightx = 100.0;
		gbc_textFieldEventoExcelExport.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEventoExcelExport.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEventoExcelExport.gridx = 1;
		gbc_textFieldEventoExcelExport.gridy = 0;
		panelStudents.add(textFieldEventoExcelExport, gbc_textFieldEventoExcelExport);
		textFieldEventoExcelExport.setColumns(10);

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectInputFile();
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 2;
		gbc_button.gridy = 0;
		panelStudents.add(button, gbc_button);

		JLabel lblStudentSize = new JLabel("Anzahl");
		GridBagConstraints gbc_lblStudentSize = new GridBagConstraints();
		gbc_lblStudentSize.anchor = GridBagConstraints.WEST;
		gbc_lblStudentSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentSize.gridx = 0;
		gbc_lblStudentSize.gridy = 1;
		panelStudents.add(lblStudentSize, gbc_lblStudentSize);

		textFieldStudentSize = new JTextField();
		textFieldStudentSize.setText("0");
		textFieldStudentSize.setEditable(false);
		GridBagConstraints gbc_textFieldStudentSize = new GridBagConstraints();
		gbc_textFieldStudentSize.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldStudentSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldStudentSize.gridx = 1;
		gbc_textFieldStudentSize.gridy = 1;
		panelStudents.add(textFieldStudentSize, gbc_textFieldStudentSize);
		textFieldStudentSize.setColumns(10);

		JPanel panelBtns = new JPanel();
		panelBtns.setBorder(new EmptyBorder(5, 5, 0, 5));
		GridBagConstraints gbc_panelBtns = new GridBagConstraints();
		gbc_panelBtns.gridwidth = 3;
		gbc_panelBtns.insets = new Insets(0, 0, 5, 0);
		gbc_panelBtns.fill = GridBagConstraints.BOTH;
		gbc_panelBtns.gridx = 0;
		gbc_panelBtns.gridy = 3;
		panelStudents.add(panelBtns, gbc_panelBtns);
		GridBagLayout gbl_panelBtns = new GridBagLayout();
		gbl_panelBtns.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelBtns.rowHeights = new int[]{0, 0};
		gbl_panelBtns.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBtns.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelBtns.setLayout(gbl_panelBtns);

		JLabel labelFilling = new JLabel(" ");
		GridBagConstraints gbc_labelFilling = new GridBagConstraints();
		gbc_labelFilling.weightx = 100.0;
		gbc_labelFilling.insets = new Insets(0, 0, 0, 5);
		gbc_labelFilling.gridx = 0;
		gbc_labelFilling.gridy = 0;
		panelBtns.add(labelFilling, gbc_labelFilling);

		JButton btnAddEmptyRow = new JButton("Leeren Eintrag hinzuf\u00FCgen");
		btnAddEmptyRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addEmptyStudentToStudentList();
			}
		});
		GridBagConstraints gbc_btnAddEmptyRow = new GridBagConstraints();
		gbc_btnAddEmptyRow.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddEmptyRow.gridx = 1;
		gbc_btnAddEmptyRow.gridy = 0;
		panelBtns.add(btnAddEmptyRow, gbc_btnAddEmptyRow);

		JButton btnNewButton = new JButton("Eintrag l\u00F6schen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeCurrentStudent();
			}
		});

		JButton btnRemoveAll = new JButton("Alle Eintr\u00E4ge l\u00F6schen");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllStudents();
			}
		});
		GridBagConstraints gbc_btnRemoveAll = new GridBagConstraints();
		gbc_btnRemoveAll.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoveAll.gridx = 2;
		gbc_btnRemoveAll.gridy = 0;
		panelBtns.add(btnRemoveAll, gbc_btnRemoveAll);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 0;
		panelBtns.add(btnNewButton, gbc_btnNewButton);

		JScrollPane scrollPaneStudents = new JScrollPane();
		GridBagConstraints gbc_scrollPaneStudents = new GridBagConstraints();
		gbc_scrollPaneStudents.weighty = 100.0;
		gbc_scrollPaneStudents.gridwidth = 3;
		gbc_scrollPaneStudents.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneStudents.gridx = 0;
		gbc_scrollPaneStudents.gridy = 2;
		panelStudents.add(scrollPaneStudents, gbc_scrollPaneStudents);

		studentTableModel = new TableModelForStudentList(modul.getStudents());
		studentTable = new JTable(studentTableModel);
		studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		float newFontSize = studentTable.getFont().getSize() * 2;
		Font newFont = studentTable.getFont().deriveFont(newFontSize);
		studentTable.setFont(newFont);
		studentTable.setRowHeight(studentTable.getRowHeight() * 2);
		scrollPaneStudents.setViewportView(studentTable);

		JPanel panelGrouping = new JPanel();
		tabbedPane.addTab("Gruppeneinteilung", null, panelGrouping, null);
		GridBagLayout gbl_panelGrouping = new GridBagLayout();
		gbl_panelGrouping.columnWidths = new int[]{0, 0, 0};
		gbl_panelGrouping.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelGrouping.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelGrouping.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelGrouping.setLayout(gbl_panelGrouping);

		JLabel lblGroupSize = new JLabel("Gruppengr\u00F6sse");
		GridBagConstraints gbc_lblGroupSize = new GridBagConstraints();
		gbc_lblGroupSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblGroupSize.anchor = GridBagConstraints.WEST;
		gbc_lblGroupSize.gridx = 0;
		gbc_lblGroupSize.gridy = 0;
		panelGrouping.add(lblGroupSize, gbc_lblGroupSize);

		textFieldGroupSize = new JTextField();
		GridBagConstraints gbc_textFieldGroupSize = new GridBagConstraints();
		gbc_textFieldGroupSize.weightx = 100.0;
		gbc_textFieldGroupSize.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldGroupSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldGroupSize.gridx = 1;
		gbc_textFieldGroupSize.gridy = 0;
		panelGrouping.add(textFieldGroupSize, gbc_textFieldGroupSize);
		textFieldGroupSize.setColumns(10);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGrouping();
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.insets = new Insets(5, 5, 5, 5);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 1;
		panelGrouping.add(btnStart, gbc_btnStart);

		tabbedPaneResult = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPaneResult = new GridBagConstraints();
		gbc_tabbedPaneResult.gridwidth = 2;
		gbc_tabbedPaneResult.fill = GridBagConstraints.BOTH;
		gbc_tabbedPaneResult.gridx = 0;
		gbc_tabbedPaneResult.gridy = 2;
		panelGrouping.add(tabbedPaneResult, gbc_tabbedPaneResult);

		JPanel currentLabelPanel = new JPanel();
		currentLabelPanel.setLayout(new BorderLayout(0, 0));
		tabbedPaneResult.addTab("Ziehung", null, currentLabelPanel, null);

		lblCurrentResult = new JLabel("  ");
		lblCurrentResult.setHorizontalAlignment(SwingConstants.CENTER);
		currentLabelPanel.add(lblCurrentResult, BorderLayout.CENTER);

		JPanel resultListPanel = new JPanel();
		tabbedPaneResult.addTab("Resultat", null, resultListPanel, null);
		resultListPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneResultArea = new JScrollPane();
		resultListPanel.add(scrollPaneResultArea, BorderLayout.CENTER);

		textPaneResultArea = new JTextPane();
		scrollPaneResultArea.setViewportView(textPaneResultArea);
		addStylesToDocumentOfResultArea();
	}

	protected void addStylesToDocumentOfResultArea(){
		StyledDocument document = textPaneResultArea.getStyledDocument();

		// default style
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);
        // set tabs
        TabStop tabStop1 = new TabStop(200);
        TabStop tabStop2 = new TabStop(400);
        TabSet tabSet = new TabSet(new TabStop[]{tabStop1, tabStop2});
        AttributeSet paraSet = StyleContext.getDefaultStyleContext().addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabSet);
		textPaneResultArea.setParagraphAttributes(paraSet, false);

        StyleConstants.setFontFamily(def, "SansSerif");
        StyleConstants.setFontSize(def, 24);
        // regular style
        regularStyle = document.addStyle("regular", def);
        // group style
        groupStyle = document.addStyle("groupStyle", regularStyle);
        StyleConstants.setBold(groupStyle, true);
        StyleConstants.setFontSize(groupStyle, 28);
        // emailStyle style
        emailStyle = document.addStyle("emailStyle", regularStyle);
        StyleConstants.setItalic(groupStyle, true);
        StyleConstants.setAlignment(emailStyle, StyleConstants.ALIGN_RIGHT);
	}

	protected void removeAllStudents() {
		studentTableModel.removeAll();
	}

	protected void removeCurrentStudent() {
		if (studentTable.getSelectedRow() >= 0){
			int index = studentTable.getSelectedRow();
			studentTableModel.removeIndex(index);
		}
		updateStudentSize();
	}

	protected void addEmptyStudentToStudentList() {
		int index = modul.getStudents().getStudents().size();
		if (studentTable.getSelectedRow() >= 0){
			index = studentTable.getSelectedRow() + 1;
		}
		studentTableModel.addEmptyStudent(index);
		studentTable.scrollRectToVisible(studentTable.getCellRect(index, 0, true));
		studentTable.getSelectionModel().setSelectionInterval(index, index);
		updateStudentSize();
	}

	protected void inputFileNameSet() {
		try {
			modul.addEventoExcelImport(textFieldEventoExcelExport.getText());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frmGruppeneinteilungVornehmen, e.toString(), "IO Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmGruppeneinteilungVornehmen, e.toString(), "Format Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		studentTableModel = new TableModelForStudentList(modul.getStudents());
		studentTable.setModel(studentTableModel);
		updateStudentSize();
	}

	private void updateStudentSize() {
		textFieldStudentSize.setText(Integer.toString(studentTableModel.getRowCount()));
	}

	protected void selectInputFile() {
		String currentDirectory = System.getProperty("user.dir");
		JFileChooser fileChooser = new JFileChooser(currentDirectory);
		int accept = fileChooser.showOpenDialog(textFieldEventoExcelExport);
		if (accept == JFileChooser.APPROVE_OPTION){
			inputFileAbsolutePath = fileChooser.getSelectedFile().getAbsolutePath();
			textFieldEventoExcelExport.setText(inputFileAbsolutePath);
			inputFileNameSet();
		}
	}

	protected void startGrouping() {
		try {
			ArrayList<Integer> groupSizes = new ArrayList<>();
			String[] groupSizesString = textFieldGroupSize.getText().trim().split(",");
			for(String groupSizeString : groupSizesString){
				int groupSize = Integer.parseInt(groupSizeString);
				groupSizes.add(groupSize);
			}
			tabbedPaneResult.setSelectedIndex(0);
			modul.executeGrouping(groupSizes, 1);
			printResultToTextPane();
			printResultToExcelFile();
			startResultShowSession();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmGruppeneinteilungVornehmen, e.toString(), "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	protected void printResultToExcelFile(){
		ExportHelper.exportModuleExcecution(inputFileAbsolutePath, modul);
	}

	protected void printResultToTextPane(){
//		StudentVisitorForSimpleTextOutput visitor = new StudentVisitorForSimpleTextOutput();
//		modul.receiveStudentVisitor(visitor);
//		textPaneResultArea.setText(visitor.getResult());

		textPaneResultArea.setText("");
		StudentVisitorForOutputToStyledDocument visitor = new StudentVisitorForOutputToStyledDocument(
				textPaneResultArea.getStyledDocument(), regularStyle, emailStyle, groupStyle);
		modul.receiveStudentVisitor(visitor);
	}


	private void startResultShowSession() {
		AnimationSwing animation = new AnimationSwing(modul, ANIMATION_DURATION_MILLI,
				GROUP_FONT_SIZE, STUDENT_FONT_SIZE, lblCurrentResult, tabbedPaneResult);
		animation.start();
	}

//	/* Variable used for animation of grouping result */
//	private int currentGroupIndex;
//	private StudentGroup currentGroup;
//	private int studentIndexInCurrentGroup;
//
//	private void startResultShowSession() {
//		lblCurrentResult.setText(" ");
//		if (modul.getGroups().size() > 0){
//			currentGroupIndex = 0;
//			currentGroup = modul.getGroups().get(currentGroupIndex);
//			studentIndexInCurrentGroup = -1;
//			resultTimer = new Timer(ANIMATION_DURATION_MILLI, new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent arg0) {
//					resultTimerEvent();
//				}
//			});
//			resultTimer.start();
//		}
//	}
//
//
//	protected void resultTimerEvent() {
//		if (currentGroup == null){
//			resultTimer.stop();
//			resultTimer = null;
//			tabbedPaneResult.setSelectedIndex(1);
//		} else {
//			if (studentIndexInCurrentGroup < 0){
//				lblCurrentResult.setFont(new Font("Tahoma", Font.PLAIN, GROUP_FONT_SIZE));
//				lblCurrentResult.setText("Gruppe " + currentGroup.getName());
//			} else {
//				Student student = currentGroup.getStudents().get(studentIndexInCurrentGroup);
//				lblCurrentResult.setFont(new Font("Tahoma", Font.PLAIN, STUDENT_FONT_SIZE));
//				lblCurrentResult.setText(student.getName() + "  " + student.getFirstName() + "    " + student.getEmail());
//			}
//			studentIndexInCurrentGroup++;
//			if (studentIndexInCurrentGroup >= currentGroup.getStudents().size()){
//				currentGroupIndex++;
//				studentIndexInCurrentGroup = -1;
//				if (currentGroupIndex >= modul.getGroups().size()){
//					currentGroup = null;
//				} else {
//					currentGroup = modul.getGroups().get(currentGroupIndex);
//				}
//			}
//		}
//	}

	protected void setNimusLookAndFeel() {
		findLookAndFeelForName("Nimbus");
	}

	protected void setMotiveLookAndFeel() {
		findLookAndFeelForName("Motif");
	}

	protected void setWindowsLookAndFeel() {
		findLookAndFeelForName("Windows");
	}

	protected void setSystemLookAndFeel() {
		setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

	protected void findLookAndFeelForName(String lookAndFeelName) {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	        if (lookAndFeelName.equals(info.getName())) {
	            setLookAndFeel(info.getClassName());
	            break;
	        }
	    }
	}

	protected void setLookAndFeel(String lookAndFeelClassName) {
		try {
			UIManager.setLookAndFeel(lookAndFeelClassName);
		} catch (Exception e) {
		    // Just ignore setting it
			System.err.println("Setting look and Feel " + lookAndFeelClassName + " failed");
		}
	}
}
