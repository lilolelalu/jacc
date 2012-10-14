class hello{
	int i;
	static int j;
	static void main(){
		int i = 1;
		int s = 0;
		while (i <= 10){
			if ( i % 2 == 0)
				if (s == 0)
					s = s + i;
				else i = i + 1;
			i = i + 1;
		}
	}
	void test(){
	}
}
