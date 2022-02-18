package ch.zhaw.grouping.evento;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class CreateOlatMemberImportSwing {

	private JFrame frame;
	private JTextField inputFileName;
	private final JButton button = new JButton("...");
	private JLabel lblResult;
	private JTextPane resultTextPane;
	private JScrollPane scrollPane;
	private JLabel lblGugus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateOlatMemberImportSwing window = new CreateOlatMemberImportSwing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateOlatMemberImportSwing() {
		frame = new JFrame();
		frame.setBounds(100, 100, 539, 344);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);

		JLabel lblEventoExcelExport = new JLabel("Evento Excel Export");
		GridBagConstraints gbc_lblEventoExcelExport = new GridBagConstraints();
		gbc_lblEventoExcelExport.insets = new Insets(0, 0, 5, 5);
		gbc_lblEventoExcelExport.anchor = GridBagConstraints.WEST;
		gbc_lblEventoExcelExport.gridx = 0;
		gbc_lblEventoExcelExport.gridy = 0;
		mainPanel.add(lblEventoExcelExport, gbc_lblEventoExcelExport);

		inputFileName = new JTextField();
		inputFileName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputFileNameSet();
			}
		});
		GridBagConstraints gbc_inputFileName = new GridBagConstraints();
		gbc_inputFileName.weightx = 100.0;
		gbc_inputFileName.insets = new Insets(0, 0, 5, 5);
		gbc_inputFileName.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputFileName.gridx = 1;
		gbc_inputFileName.gridy = 0;
		mainPanel.add(inputFileName, gbc_inputFileName);
		inputFileName.setColumns(10);

		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.anchor = GridBagConstraints.BASELINE;
		gbc_button.gridx = 2;
		gbc_button.gridy = 0;
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectInputFile();
			}
		});
		mainPanel.add(button, gbc_button);

		lblResult = new JLabel("Result");
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.insets = new Insets(0, 0, 5, 5);
		gbc_lblResult.gridwidth = 2;
		gbc_lblResult.anchor = GridBagConstraints.WEST;
		gbc_lblResult.gridx = 0;
		gbc_lblResult.gridy = 1;
		mainPanel.add(lblResult, gbc_lblResult);

		lblGugus = new JLabel("gugus");
		GridBagConstraints gbc_lblGugus = new GridBagConstraints();
		gbc_lblGugus.insets = new Insets(0, 0, 5, 0);
		gbc_lblGugus.gridx = 2;
		gbc_lblGugus.gridy = 1;
		mainPanel.add(lblGugus, gbc_lblGugus);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		mainPanel.add(scrollPane, gbc_scrollPane);

		resultTextPane = new JTextPane();
		scrollPane.setViewportView(resultTextPane);
	}

	protected void selectInputFile() {
		String currentDirectory = System.getProperty("user.dir");
		JFileChooser fileChooser = new JFileChooser(currentDirectory);
		int accept = fileChooser.showOpenDialog(inputFileName);
		if (accept == JFileChooser.APPROVE_OPTION){
			inputFileName.setText(fileChooser.getSelectedFile().getAbsolutePath());
			inputFileNameSet();
		}
	}

	protected void inputFileNameSet() {
		EventoExcelImportToOlatConverter converter = new EventoExcelImportToOlatConverter(inputFileName.getText());
		try {
			converter.convert();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, e.toString(), "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		List<String> result = converter.getResult();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < result.size(); i++){
			sb.append(result.get(i)).append('\n');
		}
		resultTextPane.setText(sb.toString());
	}

}
