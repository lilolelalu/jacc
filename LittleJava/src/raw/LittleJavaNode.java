package raw;

public class LittleJavaNode extends SimpleNode{
	String value;
	
	public void SetValue(String v){
		value = v;
	}
	
	public String GetValue(){
		return value;
	}
	
	public LittleJavaNode(int i) {
		super(i);
		value = null; 
	}
	
	public LittleJavaNode(LittleJava p, int i) {
		super(p, i);
		value = null;
	}
	
	public void dump(String prefix){
		if (this.value == null){
			System.out.println(toString(prefix));
		} else {
			System.out.println(toString(prefix) + ":" +  this.value);
		}
		if (children != null) {
			for (int i = 0; i < children.length; ++i) {
				SimpleNode n = (SimpleNode) children[i];
				if (n != null) {
					n.dump(prefix + " ");
				}
			}
		}
	}
	
}
