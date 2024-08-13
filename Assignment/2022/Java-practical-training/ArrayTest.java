/* • javalec2パッケージ内に、ArrayTestクラスを作り、以下のメソッドを実装してください。
        • 0から6までの数字を引数に与えると、”Sunday”,“Monday”のように対応する曜日の英単語を（短縮せずに）返すメソッドweekstr()を作ってください。
            • 返り値はString、引数はintが1つです。
            • 引数が0のとき日曜日、6のとき土曜日とします。
            • staticなメソッドとして定義してください。
        • mainメソッドで、0から6までの引数でweekstr()を呼び出し、返された曜日の英単語の先頭3文字を1行に1つずつ出力してください。
    • 実行し、出力した結果をソースの先頭にコメントとして貼ってください。*/



//Sun
//Mon
//Tue
//Wed
//Thu
//Fri
//Sat


package javalec2;

public class ArrayTest {
	public static String weekstr(int i){

		String [] a;
		a= new String[7];
		
	a[0]="Sunday";
	a[1]="Monday";
	a[2]="Tuesday";
	a[3]="Wednesday";
	a[4]="Thursday";
	a[5]="Friday";
	a[6]="Saturday";
	
	return a[i];
	
	}
	public static void main(String[] args) {
		int i;
		
		
		for(i=0;i<7;i++)
			System.out.println(weekstr(i).substring(0,3));
	}
	
}
