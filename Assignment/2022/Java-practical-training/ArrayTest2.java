/* • ArrayTestで、weekstr()をstaticメソッドとして定義していました。これを、通常のメンバメソッドに変更して、同じ出力を出してください。
    • パッケージをjavalec3、クラス名をArrayTest2 に変更してください
    • weekstr()で配列を使うと思います。この配列をArrayTest2のメンバ変数として定義し、コンストラクタで初期化して使ってください。
    • 他はArrayTestと同じです*/

  //Sun
//Mon
//Tue
//Wed
//Thu
//Fri
//Sat




package javalec3;

public class ArrayTest2 {
	int x=0;
	public ArrayTest2(int i) {
		x=i;
		}
	
	public String weekstr(){
		String [] a;
		a=new String[7];
		
		a[0]="Sunday";
		a[1]="Monday";
		a[2]="Tuesday";
		a[3]="Wednesday";
		a[4]="Thursday";
		a[5]="Friday";
		a[6]="Saturday";
		
		
	return a[x];
	
	}
public static void main(String[] args) {
	int i;
	
	for(i=0;i<7;i++) {
		ArrayTest2 a=new ArrayTest2(i);
		System.out.println(a.weekstr().substring(0,3));
	}
}
}



