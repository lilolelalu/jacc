package UI;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class WorkspaceTextPane extends JScrollPane{
	private JTextPane mTextPane;
	private String path;
	private boolean hasFile = false;
	public WorkspaceTextPane(String initpath){
		super();
		mTextPane = new JTextPane();
		setViewportView(mTextPane);
		repaint();
		setPath(initpath);
	}
	
	public String getPath(){
		return path;
	}
	
	public boolean hasFile(){
		return hasFile;
	}
	
	public void setPath(String initpath){
		path = initpath;
		if(path != "")
			hasFile = true;
	}
	
	public String getText(){
		return mTextPane.getText();
	}
	
	public void read(Reader reader) throws IOException{
		mTextPane.read(reader, null);
	}
}
