/*• 数字をたくさん文字列で読み込んだ
    • ファイルに書いてあったと思ってください
• PersonData.persons に数字の文字列が配列で入っている
    • persons = {“357”, “25849”,… }; みたいに
• この数字の平均を求めたい
• Integer.parseIntを使ってそれぞれの文字列をintに直し、平均を計算しようと思う
• ところが！
• どこかの文字列に変な文字がくっついていて、parseIntがエラーを吐いている
    • 試してみてください
 */

//Error in: 18
//Error in: 20
//45515.2


package javalec7;

public class ExceptionTest {
public static void main(String[] args) {
	int num = PersonData.persons.length;
	int [] person = new int[PersonData.persons.length];
	int j=0;
	int k=0;
	int i=0;
	double sum=0.0;
	double x=0.0;
	
	for(i=0;i<num;i++) {
		if(j<PersonData.persons.length) {
	try {
		double a=Integer.parseInt(PersonData.persons[j]);
	}
	catch(NumberFormatException a) {
		num--;
		i--;
		int h=j+1;
		System.out.println("Error in: " + h );
		k++;
	}
	if(k==0)
		person[i]=Integer.parseInt(PersonData.persons[j]);
		j++;
		k=0;
	}
}
	for(int l=0;l<num;l++) 
		sum=sum+person[l];
	
	x=sum/i;
	System.out.println(x);
}
}