
package UI;
import java.awt.EventQueue;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import CompilerForeEnd.ASTStart;
import CompilerForeEnd.LittleJavaFacade;
import CompilerForeEnd.SimpleNode;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;


public class GUI extends JFrame implements ActionListener{

	/**
	 * Member Decl
	 */
	private String path = "";
	private String context;
	private JFileChooser chooser;
	private JToolBar toolBar;
	private JCheckBox lexBox, syntaxBox, semanticBox, codeGeneBox;
	private JButton btnGo;
	private JTabbedPane compilerInfo; 
	private JTextArea input;
	private JTextArea lex;
	private JTextArea semantic;
	private JTextArea codeGene;
	private JButton newBtn;
	private JButton openBtn;
	private JButton saveBtn;
	private JScrollPane lexjsp;
	private JScrollPane syntaxjsp;
	private JScrollPane semanticjsp;
	private JScrollPane codeGenejsp;
	
	
	private LittleJavaFacade parser;
	private boolean hasFile = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		  try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
	                    .getInstalledLookAndFeels()) {
	                if ("Windows".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(
	                    java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(
	                    java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(
	                    java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(
	                    java.util.logging.Level.SEVERE, null, ex);
	        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Little Java");
		//path = "D:\\Workspace\\Project\\Java\\LittleJava\\sample\\first.java";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		
		toolBar = new JToolBar();
		toolBar.setBounds(20, 10, 600, 23);
		toolBar.setFloatable(false);
		getContentPane().add(toolBar);
		
		newBtn = new JButton("\u65B0\u5EFA");
		newBtn.addActionListener(this);
		toolBar.add(newBtn);
		
		openBtn = new JButton("\u6253\u5F00");
		openBtn.addActionListener(this);
		toolBar.add(openBtn);
		
		saveBtn = new JButton("\u4FDD\u5B58");
		saveBtn.addActionListener(this);
		toolBar.add(saveBtn);
		
		lexBox = new JCheckBox("\u8BCD\u6CD5\u5206\u6790");
		toolBar.add(lexBox);
		
		syntaxBox = new JCheckBox("\u8BED\u6CD5\u5206\u6790");
		toolBar.add(syntaxBox);
		
		semanticBox = new JCheckBox("\u8BED\u4E49\u68C0\u67E5");
		toolBar.add(semanticBox);
		
		codeGeneBox = new JCheckBox("\u4EE3\u7801\u751F\u6210");
		toolBar.add(codeGeneBox);
		
		btnGo = new JButton("Go");
		btnGo.addActionListener(this);

		toolBar.add(btnGo);
		
		compilerInfo = new JTabbedPane(JTabbedPane.TOP);
		compilerInfo.setBounds(400, 43, 350, 500);
		getContentPane().add(compilerInfo);
		
		
		lex = new JTextArea();
		lexjsp = new JScrollPane(lex);
		syntaxjsp = new JScrollPane();
		semantic = new JTextArea();
		semanticjsp = new JScrollPane(semantic);
		
		codeGene = new JTextArea();
		codeGenejsp = new JScrollPane(codeGene);
		
		input = new JTextArea();		
		input.setBounds(20, 43, 350, 500);
		JScrollPane jsp = new JScrollPane(input);
		jsp.setBounds(20, 43, 350, 500);
		getContentPane().add(jsp);
		
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//设置选择模式，既可以选择文件又可以选择文件夹
		
		parser = new LittleJavaFacade();
		context = input.getText();
	}
	private void displayLexInfo(){
		String text = parser.GetLexOutput();
		lex.setText(text);
		compilerInfo.addTab("Lex", lexjsp);
	}
	
	private void displaySyntaxInfo(){
		Object[] info = new Object[1];
		info[0] = "no exception";
		ASTStart n = parser.GetSyntaxOutput(info);
		if(info[0].toString() == "no exception"){
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
			createTree(n, root);
			JTree syntaxTree = new JTree(root);
			syntaxTree.setRootVisible(false);
			syntaxjsp.setViewportView(syntaxTree);
			syntaxjsp.repaint();
			compilerInfo.addTab("Syntax", syntaxjsp);
		}
		else{
			JTextArea syntax = new JTextArea();
			syntax.setText(info[0].toString());
			syntaxjsp.setViewportView(syntax);
			syntaxjsp.repaint();
			compilerInfo.addTab("Syntax",syntax);
		}
	}
	
	public void createTree(SimpleNode n, DefaultMutableTreeNode node){
		DefaultMutableTreeNode son = new DefaultMutableTreeNode(n.toString());
		node.add(son);
		if(n.jjtGetNumChildren() != 0){
			for(int i = 0; i < n.jjtGetNumChildren(); ++i){
				SimpleNode tmp = (SimpleNode) n.jjtGetChild(i);
				if(tmp != null)
					createTree(tmp, son);
			}
		}
	}
	private void displaySemanticInfo(){
		compilerInfo.addTab("Semantic", semanticjsp);
	}
	
	private void displayCodeGeneInfo(){
		compilerInfo.addTab("CodeGeneration", codeGenejsp);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnGo)
			compileCode();
		if(e.getSource() == newBtn)
			newFile();
		if(e.getSource() == openBtn)
			openFile();
		if(e.getSource() == saveBtn)
			saveFile();
	}
	private void compileCode(){
		saveFile();
		compilerInfo.removeAll();
		parser.Compile(path);
		if(lexBox.isSelected())
			displayLexInfo();
		if(syntaxBox.isSelected())
			displaySyntaxInfo();
		if(semanticBox.isSelected())
			displaySemanticInfo();
		if(codeGeneBox.isSelected())
			displayCodeGeneInfo();
	}
	
	
	private void newFile(){
		int result = checkSave();
		if(result != 2)
			reset();
	}
	private void reset(){
		input.setText(null);
		hasFile = false;
		compilerInfo.removeAll();
		path = "";
		context = null;
	}
	
	private void openFile(){
		int n = checkSave();
		if(n != 2){
			int retval;
			retval = chooser.showOpenDialog(this);//显示"保存文件"对话框
			if(retval == JFileChooser.APPROVE_OPTION) {//若成功打开
				File file = chooser.getSelectedFile();//得到选择的文件名
				path = file.getAbsolutePath();
				try {
					// What to do with the file, e.g. display it in a TextArea
					input.read(new FileReader(file.getAbsolutePath()), null);
					hasFile = true;
					context = input.getText();
				} catch (IOException ex) {
					System.out.println("problem accessing file"
							+ file.getAbsolutePath());
	            }
	        }
		}
	}
	
	private int saveFile(){
		try{
			FileWriter fw = null;
			if(!hasFile){
				int retval;
				File file = new File("untitled.lj");
				chooser.setSelectedFile(file);
				retval = chooser.showSaveDialog(this);
				if(retval == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					fw = new FileWriter(file);
					fw.write(input.getText());
					fw.close();
					hasFile = true;
					path = file.getPath();
					context = input.getText();
					return 1;
				}
				else
					return 0;
			}
			else{
				File file = new File(path);
				fw = new FileWriter(file);
				fw.write(input.getText());
				fw.close();
				return 1;
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	private int checkSave(){
		String temp = input.getText();
		if(context != temp){
			int n = JOptionPane.showConfirmDialog(this, "Save?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
			switch(n){
			case JOptionPane.YES_OPTION:
				int tmp = saveFile();
				if(tmp == 1)
					return 0;
				else	
					return 2;
			case JOptionPane.NO_OPTION:
				return 1;
			case JOptionPane.CANCEL_OPTION:
				return 2;
			default:
				return 2;
			}
		}
		else
			return 0;
	}
}