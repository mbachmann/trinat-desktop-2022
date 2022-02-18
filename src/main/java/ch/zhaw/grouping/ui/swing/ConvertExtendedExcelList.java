package ch.zhaw.grouping.ui.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
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

public class ConvertExtendedExcelList {
	public static final int STUDENT_FONT_SIZE = 32;
	public static final int GROUP_FONT_SIZE = 48;
	public static final int ANIMATION_DURATION_MILLI = 1200;

	private ModulExecution modul = new ModulExecution();
	private JFrame frmGruppeneinteilungVornehmen;
	private JTextField textFieldEventoExcelExport;
	private JTextPane textPaneResultArea;
	private Style emailStyle;
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
					ConvertExtendedExcelList window = new ConvertExtendedExcelList();
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
	public ConvertExtendedExcelList() {
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

		JPanel panelStudents = new JPanel();
		GridBagLayout gbl_panelStudents = new GridBagLayout();
		gbl_panelStudents.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_panelStudents.rowWeights = new double[]{0.0, 1.0, 1.0};
		panelStudents.setLayout(gbl_panelStudents);

		JLabel lblEventoExcelExportFile = new JLabel("Event-Excel-Export Datei");
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


		JScrollPane scrollPaneStudents = new JScrollPane();
		GridBagConstraints gbc_scrollPaneStudents = new GridBagConstraints();
		gbc_scrollPaneStudents.weighty = 100.0;
		gbc_scrollPaneStudents.gridwidth = 3;
		gbc_scrollPaneStudents.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneStudents.gridx = 0;
		gbc_scrollPaneStudents.gridy = 2;
		panelStudents.add(scrollPaneStudents, gbc_scrollPaneStudents);

		JPanel resultListPanel = new JPanel();
		resultListPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneResultArea = new JScrollPane();
		resultListPanel.add(scrollPaneResultArea, BorderLayout.CENTER);

		textPaneResultArea = new JTextPane();
		scrollPaneStudents.setViewportView(textPaneResultArea);
		addStylesToDocumentOfResultArea();

		frmGruppeneinteilungVornehmen.getContentPane().add(panelStudents, BorderLayout.CENTER);

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

	protected void inputFileNameSet() {
		try {
			modul.addEventoExcelAndGroupImport(textFieldEventoExcelExport.getText());
			printResultToTextPane();
			printResultToExcelFile();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frmGruppeneinteilungVornehmen, e.toString(), "IO Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmGruppeneinteilungVornehmen, e.toString(), "Format Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}


	protected void printResultToExcelFile(){
		ExportHelper.exportModuleExcecution(inputFileAbsolutePath, modul);
	}

	protected void printResultToTextPane(){
		textPaneResultArea.setText("");
		StudentVisitorForOutputToStyledDocument visitor = new StudentVisitorForOutputToStyledDocument(
				textPaneResultArea.getStyledDocument(), regularStyle, emailStyle, groupStyle);
		modul.receiveStudentVisitor(visitor);
	}

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
