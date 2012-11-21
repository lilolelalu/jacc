package raw;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Vector;


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
	
	public ArrayList<String[]> getLexOutput(){
		if(!canUse){
			try{
				parser.ReInit(fis);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		ArrayList<String[]> result = new ArrayList<String[]>();
		try{
			for(Token token = parser.getNextToken(); token.kind != 0; token = parser.getNextToken()){
				String[] tmp = new String[2];
				tmp[0] = LittleJavaConstants.tokenImage[token.kind];
				tmp[1] = token.toString();
				result.add(tmp);
			}
			canUse = false;
			if(result.size() == 0){
				String[] tmp = {"Blank", ""};
				result.add(tmp);
			}
			return result;
		} catch (TokenMgrError e){
			result.clear();
			String[] tmp = new String[2];
			tmp[0] = "Error";
			tmp[1] = e.getMessage();
			result.add(tmp);
			return result;
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
