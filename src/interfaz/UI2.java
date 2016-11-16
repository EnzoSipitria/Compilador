package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import assembler.Assembler;
import parser.Parser;
import structures.AuxGenerator;
import structures.Terceto;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class UI2 extends JFrame {

	private static File file;
	private JTextField txtPath;
	private JTextArea sourceCodeEditor;
	private Parser parser;
	private JTabbedPane tabBottom;
	private JTabbedPane tabRight;
	private String pathFile;
	private JButton btnCompile;
	private JButton btnSaveFile;
	private JButton btnLoadFile;
	private JTextArea txtTokenList;
	private Assembler assembler;
	
	public static JTextArea txtSymbolsTable;
	public static JTextArea txtReservedWords;
	public JTextArea txtTercetos;
	public static JTextArea txtConsole;
	public static JTextArea txtDebug;
	

	public UI2() {
		setStyle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setVisible(true);



		setBounds(0, 0,1280,728);

		tabBottom = new JTabbedPane(JTabbedPane.TOP);
		setResizable(true);

		txtConsole = new JTextArea();
		txtConsole.setEditable(false);
		txtConsole.setBounds(0, 0, 1239, 169);

		txtDebug = new JTextArea();
		txtDebug.setEditable(false);
		txtDebug.setBounds(0, 0, 1239, 169);

		JScrollPane tabDebug = new JScrollPane(txtDebug);
		JScrollPane tabConsole = new JScrollPane(txtConsole);


		tabBottom.addTab("Debug", null, tabDebug, null);
		tabBottom.addTab("Console", null, tabConsole, null);


		sourceCodeEditor = new JTextArea();
		sourceCodeEditor.setBounds(0, 0, 833, 457);

		JScrollPane scrollSourceCode = new JScrollPane(sourceCodeEditor);
		scrollSourceCode.setBorder(new TitledBorder(null, "Source Code", TitledBorder.LEFT, TitledBorder.TOP, null, null));

		TextLineNumber sourceLineNumber = new TextLineNumber(sourceCodeEditor);
		scrollSourceCode.setRowHeaderView(sourceLineNumber);
		

		tabRight = new JTabbedPane(JTabbedPane.TOP);

		txtSymbolsTable = new JTextArea();
		txtSymbolsTable.setEditable(false);
		txtSymbolsTable.setText("tabla de simbolos");

		txtReservedWords = new JTextArea();
		txtReservedWords.setEditable(false);

		/*
		 * ocultar esta pestaña
		 */
		txtTokenList = new JTextArea();
        txtTokenList.setEditable(false); 

    	txtTercetos = new JTextArea();
		txtTercetos.setEditable(false);
		txtTercetos.setText("Tercetos");
		
		JScrollPane scrollSymbolTable = new JScrollPane(txtSymbolsTable);
		tabRight.addTab("Symbols Table", null, scrollSymbolTable, null);


		JScrollPane scrollReservedWords = new JScrollPane(txtReservedWords);
		tabRight.addTab("Reserved Words", null, scrollReservedWords, null);
		
		JScrollPane scrollTokenList = new JScrollPane(txtTokenList);
		tabRight.addTab("Token List", null, scrollTokenList, null);

		JScrollPane scrollTercetos = new JScrollPane(txtTercetos);
		tabRight.addTab("Tercetos", null, scrollTercetos, null);
		
		JPanel panelPath = new JPanel();
		panelPath.setLayout(null);

		txtPath = new JTextField();
		txtPath.setEditable(false);
		txtPath.setBounds(0, 0, 373, 23);
		panelPath.add(txtPath);
		txtPath.setColumns(10);
		
		/**
		 * BOTON LOAD FILE: abrir dialogo choose file, cargar el archivo(nuevo metodo a implementar) ==> ==>implementar un metodo private que muestre el archivo en el panel correspondiente(mudar esa parte del codigo al nuevo metodo)
		 * 
		 */



		btnLoadFile = new JButton("Cargar Archivo");
		btnLoadFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
	
				txtDebug.setText("");
				txtSymbolsTable.setText("");
				txtReservedWords.setText("");
				txtTokenList.setText("");
				txtConsole.setText("");
				
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Codigo Fuente");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.showOpenDialog(null);
				file = chooser.getSelectedFile();

//				System.out.println("Path:"+txtPath.getText());
				if(file!=null){
					pathFile = file.getAbsolutePath();
					txtPath.setText(pathFile);
					viewSourceCode();
					btnCompile.setEnabled(true);
					btnSaveFile.setEnabled(true);
				}}
		});


		/**
		 * BOTON SAVE FILE: guardar el archivo editado y dejar el parser listo para ejecutarse nuevamente, 
		 */
		btnSaveFile = new JButton("Guardar Como");
		btnSaveFile.setEnabled(false);
		btnSaveFile.addMouseListener(new MouseAdapter() {
			private File fileSaved;

			@Override
			public void mouseClicked(MouseEvent arg0) {  
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Guardar Como");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int selection = chooser.showSaveDialog(null);
				if (selection == JFileChooser.APPROVE_OPTION)
					file = chooser.getSelectedFile();
				
				FileWriter out = null;
				BufferedWriter bufferwriter = null;
				pathFile = file.getAbsolutePath();
				txtPath.setText(pathFile);
				try {
					out = new FileWriter(file);
					bufferwriter = new BufferedWriter(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String[] textSplit = sourceCodeEditor.getText().split("\n");
				for (String line : textSplit) {
					try {
						bufferwriter.write(line+"\n");
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				try {
					bufferwriter.close();
					JOptionPane.showMessageDialog(null, "Archivo Guardado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				viewSourceCode();
				txtDebug.setText("");
				txtSymbolsTable.setText("");
				txtReservedWords.setText("");
				txtTokenList.setText("");
				txtConsole.setText("");
				btnCompile.setEnabled(true);
			}
		});





		btnCompile = new JButton("Compilar");
		btnCompile.setEnabled(false);
		btnCompile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (file!=null){
					JOptionPane.showMessageDialog(null, file.getAbsolutePath(), pathFile, JOptionPane.INFORMATION_MESSAGE);
					addText(txtDebug, "ERRORES SINTACTICOS: "+"\n");
					parser = new Parser(pathFile);
					parser.run();
					tabBottom.setSelectedIndex(1);
					tabRight.setSelectedIndex(2);	
				} 
				
				btnCompile.setEnabled(false);
				addText(txtSymbolsTable,parser.getLexicalAnalizer().getSymbolTable().toString());
				addText(txtDebug, "ERRORES LEXICOS: "+"\n");
				addText(txtDebug,parser.getLexicalAnalizer().viewErrors());
//				addText(txtTokenList, parser.getLexicalAnalizer().viewTokenList());
				viewEstructuras();	
				viewTercetos();
				assembler = new Assembler(parser.getTercetos(),parser.getLexicalAnalizer().getSymbolTable());
//				addText(txtTokenList, assembler.getCodigo());
				assembler.initAuxGenerator();// esto reinicia el contador del aux generator cada vez que se compila
				addText(txtSymbolsTable,parser.getLexicalAnalizer().getSymbolTable().toString());
				
				/**
				 * pedir lista de tercetos tecorrerla y por cadaelemento generar el codigo assembler 
				 * 
				 */
//				System.out.println("Voy a escribir el codigo assembler"+"\n");
				writeAssemblerFile(assembler.getCodigo());
				
//				Set<String> keys = parser.getLexicalAnalizer().getSymbolTable().getTokenList().keySet();
//				for (String string : keys) {
//					addText(txtDebug,string+"\n");
//				}
				
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(panelPath, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
									.addGap(10)
									.addComponent(btnLoadFile, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
									.addGap(6)
									.addComponent(btnSaveFile, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
									.addGap(10)
									.addComponent(btnCompile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(145))
								.addComponent(scrollSourceCode, GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabRight, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
						.addComponent(tabBottom, GroupLayout.DEFAULT_SIZE, 1158, Short.MAX_VALUE))
					.addGap(96))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panelPath, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLoadFile)
								.addComponent(btnSaveFile)
								.addComponent(btnCompile))
							.addGap(11)
							.addComponent(scrollSourceCode, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(tabRight, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabBottom, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
					.addGap(1))
		);
		getContentPane().setLayout(groupLayout);


	}

	public void getPath(){
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Codigo Fuente");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		file=chooser.getSelectedFile();
		if(file!=null){
			file.getAbsolutePath();
			txtPath.setText(file.getAbsolutePath());   
		}
	}

	private void viewTercetos() {
		txtTercetos.setText("");
		for (Terceto terceto: parser.getTercetos()){
			addText(txtTercetos, terceto.getPosition()+". "+terceto.toString()+"\n");
		}
	}
	
	private void viewEstructuras (){
		Collections.sort(parser.getEstructuras());
		addText(txtConsole, "Estructuras encontradas\n");
		for (String estructura : parser.getEstructuras()) {
			addText(txtConsole, estructura);
		}
	}
	

	
	private void viewSourceCode() {
		sourceCodeEditor.setText("");
		txtDebug.setText("");
		txtSymbolsTable.setText("");
		txtReservedWords.setText("");
		tabRight.setSelectedIndex(1);
		addText(txtDebug, "lexical analyzer inicialized\n");				  
		String line="";   
		String text="";		
//		int lineNumber = 1;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader buffercodigo=new BufferedReader(fileReader);
		try {
			while(buffercodigo.ready()){
//				System.out.println("while reAady");
				line = buffercodigo.readLine();
//				System.out.println("linea: "+line);
				text=line+ "\n";
				sourceCodeEditor.setText(sourceCodeEditor.getText()+text);
				sourceCodeEditor.repaint();
//				lineNumber++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			buffercodigo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeAssemblerFile(String code){
		FileWriter out = null;
		BufferedWriter bufferwriter = null;
//		System.out.println(file.getAbsolutePath());
		try {
			out = new FileWriter("D:\\Compartida Virtual\\Workspace\\Compilador\\assembler.txt");
			bufferwriter = new BufferedWriter(out);
			bufferwriter.write(code);
			bufferwriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void addText(JTextArea write,String text){
		write.append(text);
		write.repaint();
	}


	private void setStyle(){
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(UI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(UI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(UI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(UI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI2 frame = new UI2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
}
