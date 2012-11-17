package CompilerForeEnd;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LittleJavaFacade {
	private LittleJava parser = null;
	private boolean canUse = false;
	private String path;
	FileInputStream fis;
//	public static void main(String[] args){
//		String path = "D:\\Workspace\\Project\\Java\\LittleJava\\sample\\first.java";
//		Compile(path);
//		System.out.println(GetLexOutput());
//	}
	public LittleJavaFacade(){
	
	}
	
	public void Compile(String init_path){
		path = init_path;
		try {
			if(null != fis)
				fis.close();
			fis = new FileInputStream(path);
			if(null != parser)
				parser.ReInit(fis);
			else
				parser = new LittleJava(fis);
			canUse = true;
			//fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String GetLexOutput(){
		if(!canUse){
			try{
				parser.ReInit(fis);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		String text = "";
		try{
			for(Token token = parser.getNextToken(); token.kind != 0; token = parser.getNextToken()){
				text += token.toString();
				text += "\n";
			}
			canUse = false;
			return text;
		} catch (TokenMgrError e){
			return e.getMessage();
		}
		
	}
	
	public ASTStart GetSyntaxOutput(Object[] info){
		if(!canUse){
			try{
				FileInputStream fis = new FileInputStream(path);
				parser.ReInit(fis);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		canUse = false;
		
		try {
			return parser.Start();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			info[0] = e.getMessage();
			return null;
		}		
	}
}
