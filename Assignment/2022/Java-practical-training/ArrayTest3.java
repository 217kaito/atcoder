/*
• 数字を表した文字列の配列がある
    • “19”, “3”, “0000470” みたいに
• これを「辞書順」と「整数だと思った時の数字の小さい順」の2通りでソートして表示しよう
• ソートアルゴリズムを「使う」練習です

要求
• ArrayDat.numstrings にStringの配列が入っている
• これを使って、配列をソートして1行1つずつデータを表示する。
• 2通りのソート結果を、どちらのソート結果なのか一行説明付けて、続けて表示する。
*/

//<辞書順ソート>
//00030
//0042
//10003
//109
//119
//19
//9
//<数としてソート>
//9
//19
//00030
//0042
//109
//119
//10003

package javalec9;

import java.util.Arrays;
import java.util.Comparator;


public class ArrayTest2 {

  public static void main(String[] args) {
	String [] a = ArrayDat.numstrings;
	
	  System.out.println("<辞書順ソート>");
	  Arrays.sort(a,null);
	  for(int i=0;i<a.length;i++) {
		  System.out.println(a[i]);
	  }
	  
	  System.out.println("<数としてソート>");
  
class Cmp implements Comparator{
	public int compare(Object l, Object r) {
		String l1 = String.valueOf(l);
		String r1 = String.valueOf(r);
		return Integer.valueOf(l1).compareTo(Integer.valueOf(r1));
	}
};

Cmp cmp = new Cmp();

Arrays.sort(a,cmp);
for(int i=0;i<a.length;i++) {
	  System.out.println(a[i]);
}
}
}