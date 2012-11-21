
package UI;
import java.awt.EventQueue;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import raw.ASTStart;
import raw.LittleJavaFacade;
import raw.LittleJavaNode;
import raw.SimpleNode;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;


public class GUI extends JFrame implements ActionListener, OnTabRemovedListener, WindowListener{

	/**
	 * Member Decl
	 */
	private String context;
	private JFileChooser chooser;
	private JToolBar toolBar;
	private JCheckBox lexBox, syntaxBox, semanticBox, codeGeneBox;
	private JButton btnGo;
	private STabbedPane input;
	private STabbedPane compilerInfo;
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
		
		compilerInfo = new STabbedPane(false);
		compilerInfo.setBounds(400, 43, 350, 500);
		getContentPane().add(compilerInfo);
	
		lexjsp = new JScrollPane();
		syntaxjsp = new JScrollPane();
		semantic = new JTextArea();
		semanticjsp = new JScrollPane(semantic);
		
		codeGene = new JTextArea();
		codeGenejsp = new JScrollPane(codeGene);
		
		input = new STabbedPane(false);
		input.addTabRemovedListener(this);
		input.setBounds(20, 43, 350, 500);
		getContentPane().add(input);
		
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//设置选择模式，既可以选择文件又可以选择文件夹
		
		parser = new LittleJavaFacade();
		this.addWindowListener(this);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	private boolean displayLexInfo(){
		ArrayList<String[]> result = parser.getLexOutput();
		if(result.get(0)[0] == "Error"){
			JTextArea lex = new JTextArea();
			lex.setText(result.get(0)[1]);
			lex.setEditable(false);
			lexjsp.setViewportView(lex);
			lexjsp.repaint();
			compilerInfo.addTab("Lex", null, lexjsp, "Lex", true);
			return false;
		}
		else{
			String[][] danteng = new String[result.size()][2];
			if(result.get(0)[0] != "Blank"){
				for(int i = 0; i < result.size(); i++){
					danteng[i] = result.get(i);
				}
			}
			String[] headers = {"Type", "Token Image"};
			DefaultTableModel model = new DefaultTableModel(danteng, headers){
				private static final long serialVersionUID = 1L;
				public boolean isCellEditable(int row, int column){
					return false;
				}
			};
			JTable table = new JTable(model);
			table.getTableHeader().setReorderingAllowed(false);
			lexjsp.setViewportView(table);
			lexjsp.repaint();
			compilerInfo.addTab("lex", null, lexjsp, "lex", true);
			return true;
		}
	}
	
	private void displaySyntaxInfo(){
		Object[] info = new Object[1];
		info[0] = "no exception";
		ASTStart n = parser.GetSyntaxOutput(info);
		if(info[0].toString() == "no exception"){
			DefaultMutableTreeNode root = createTree(n);
			JTree syntaxTree = new JTree(root);
			syntaxTree.setRootVisible(true);
			syntaxjsp.setViewportView(syntaxTree);
			syntaxjsp.repaint();
			compilerInfo.addTab("Syntax", null, syntaxjsp, "Syntax", true);
		}
		else{
			JTextArea syntax = new JTextArea();
			syntax.setText(info[0].toString());
			syntax.setEditable(false);
			syntaxjsp.setViewportView(syntax);
			syntaxjsp.repaint();
			compilerInfo.addTab("Syntax", null, syntaxjsp, "Syntax", true);
		}
	}
	
	public DefaultMutableTreeNode createTree(LittleJavaNode n){
		DefaultMutableTreeNode result;
		if(n.GetValue() == null){
			result = new DefaultMutableTreeNode(n.toString());
		}
		else{
			result = new DefaultMutableTreeNode(n.toString() + ":" + n.GetValue());
		}
		if(n.jjtGetNumChildren() != 0){
			for(int i = 0; i < n.jjtGetNumChildren(); ++i){
				LittleJavaNode tmp = (LittleJavaNode) n.jjtGetChild(i);
				if(tmp != null){
					result.add(createTree(tmp));
				}
			}
		}
		return result;
	}
	private void displaySemanticInfo(){
		compilerInfo.addTab("Semantic", null, semanticjsp, "Semantic", true);
	}
	
	private void displayCodeGeneInfo(){
		compilerInfo.addTab("CodeGeneration", null, codeGenejsp, "Code Generation", true);
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
	
	public boolean onTabRemoved(ActionEvent e){
		int result = checkSave();
		if(result != 0 && result != 1)
			return false;
		else
			return true;
	}
	
	private void compileCode(){
		int result = checkSave();
		if(result == 0){
			compilerInfo.removeAll();
			parser.Compile(((WorkspaceTextPane) input.getSelectedComponent()).getPath());
			boolean flag = true;
			if(lexBox.isSelected())
				flag = displayLexInfo();
			if(syntaxBox.isSelected() && flag)
				displaySyntaxInfo();
			if(semanticBox.isSelected() && flag)
				displaySemanticInfo();
			if(codeGeneBox.isSelected() && flag)
				displayCodeGeneInfo();
		}
	}
	
	
	private void newFile(){
		WorkspaceTextPane tmp = new WorkspaceTextPane("");
		input.addTab("untitled", null, tmp, null, true);
		input.setSelectedIndex(input.getTabCount() - 1);
	}
	
	private void openFile(){
		WorkspaceTextPane current = new WorkspaceTextPane("");
		int retval;
		retval = chooser.showOpenDialog(this);//显示"保存文件"对话框
		if(retval == JFileChooser.APPROVE_OPTION) {//若成功打开
			File file = chooser.getSelectedFile();//得到选择的文件名
			try {
				// What to do with the file, e.g. display it in a TextArea
				current.read(new FileReader(file.getAbsolutePath()));
				current.setPath(file.getAbsolutePath());
				input.addTab(file.getName(), null, current, null, true);
				input.setSelectedIndex(input.getTabCount() - 1);
			} catch (IOException ex) {
				System.out.println("problem accessing file"
						+ file.getAbsolutePath());
			}
		}
	}
	
	private int saveFile(){
		try{
			FileWriter fw = null;
			WorkspaceTextPane current = (WorkspaceTextPane) input.getSelectedComponent();
			if(!current.hasFile()){
				int retval;
				File file = new File("untitled.lj");
				chooser.setSelectedFile(file);
				retval = chooser.showSaveDialog(this);
				if(retval == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					fw = new FileWriter(file);
					fw.write(current.getText());
					fw.close();
					current.setPath(file.getAbsolutePath());
					return 1;
				}
				else
					return 0;
			}
			else{
				File file = new File(current.getPath());
				fw = new FileWriter(file);
				fw.write(current.getText());
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
		if(input.getComponentCount() == 0)
			return -1;
		String temp = ((WorkspaceTextPane) input.getSelectedComponent()).getText();
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
	
	public void test(){
		System.out.println("Close");
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		if(input.getTabCount() != 0){
			while(input.getTabCount() != 0)
				if(!input.removeTab(0))
					return;
		}
		this.dispose();
		System.exit(0);
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}