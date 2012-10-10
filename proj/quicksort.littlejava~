public class first{
  	public static void swap(int[]a,int x,int y){
	  	int tmp=a[x];
	  	a[x]=a[y];
	  	a[y]=tmp;
  	}
  	public static void quicksort(int[] a,int begin,int end){
	  	if(begin>=end)
		  	return ;
	  	int mid_num=a[begin];
	  	int mid_pos=begin;
	  	for(int i=begin+1;i<=end;i++){
		  	if(a[i]<mid_num){
			  	swap(a,mid_pos+1,i);
			  	mid_pos++;
		  	}
	  	}
	  	swap(a,mid_pos,begin);
	  	quicksort(a,begin,mid_pos-1);
	  	quicksort(a,mid_pos+1,end);
  }
  /** Main entry point. */
  public static void main(){
	int arr[]=new int[10];
	for(int i=0;i<10;i++)
	{
		arr[i]=i*i%10;
	}
	quicksort(arr,0,9); 
  	}
}