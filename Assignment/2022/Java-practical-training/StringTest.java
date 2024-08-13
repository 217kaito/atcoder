/*• 文字列をため込んでいるクラスPartsがある
    • 文字列の個数はint Parts.NUM
    • 文字列を取り出すにはString Parts.item(int i)
• この文字列から、任意の2つを取り出してくっつけた文字列を作る
    • 同じ文字列をくっつける場合も含むことにする
• くっつけた文字列がParts.NUM * Parts.NUM個できる
• くっつけた文字列(Parts.NUM * Parts.NUM個)の中で、同じ文字列になっている（＝文字の並びが同じ）ものはあるか？あれば、1行に1つずつ表示せよ
    • ただし、3回以上重複していることはないとする
    • 文字列の全体が一致することが必要。一部が一致するものは含まない*/


//きのこのこ
//のこのこのこ

package javalec5;

public class StringTest {
	public static void main(String[] args) {
	StringTest.main();
	}
	public static void main()
	{String[] a=new String[Parts.NUM*Parts.NUM];
		int k=0;
		
		for(int i=0;i<Parts.NUM;i++)
	       for(int j=0;j<Parts.NUM;j++) {
             a[k]=Parts.item(i)+Parts.item(j);
             k++;
	       }
		
		for(int l=0;l<Parts.NUM*Parts.NUM;l++)
			for(int m=l+1;m<Parts.NUM*Parts.NUM;m++)
				if(a[l].equals(a[m]))
					System.out.println(a[l]);
	}	
}