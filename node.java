//////in first file!
public class first{
    public static void main(String args[]) throws ParseException {
	   node root=new node(5);
	   node l=new node(1);
	   node r=new node(9);
	   root.addChild(l, true);
	   root.addChild(r, false);
	   System.out.print(root.left.num);
    }
}

////////// in second file!
public class node{
    int num;
    node parent=null;
    node left=null;
    node right=null;
    public node(int n){
        this.num =n; 
    }
    public void addChild(node l,boolean isleft){
        if(l==null)
            return ;
        if(isleft && this.left==null){
            this.left=l;
            l.parent=this;
        }
        else if(!isleft && this.right==null){
            this.right=l;
            l.parent=this;
        }
    }
}