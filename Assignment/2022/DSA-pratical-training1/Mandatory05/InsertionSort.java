
package m05;

//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;



//問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
//以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
//難易度：4
//感想：括弧を閉じる位置を間違えるなどのミスが多くなってしまった。



public class InsertionSort {
	
	public static void main(String[] args)	{
		
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
	}
	
	//*x=1からx=numまで、配列ar1のインデックスが0から(x－1)の要素で、
	//*ar1[x]以下の値を持つものの中でインデックスが最大のものををar1[k]とする。
	//*ar1[k*1]にar1[x]の値を代入する。配列ar1のインデックスがkから(x－1)のすべての要素ar[j]について
	//*その値をインデックスがそのインデックスよりも1大きい要素に代入する。
	//*ar1[k]が存在しない時はar1[0]にar1[x]の値を代入する。
	private static void insertionsort(int[] ar1, int num1) {
		int y=0;
		int j;
		
	    for(int x=1;x<num1+1;x++) {
	    	y=ar1[x];
	    	for( j=x-1;j>=0;j=j-1) {
		if(ar1[j]<=y) {
			ar1[j+1]=y;
			break;
		}
		else
			ar1[j+1]=ar1[j];
		
	    	}
	    
	    if(j==-1)
	    	ar1[0]=y;
	    	
	    }
	    }
	    
	
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		//test1
		int[] testar1 = {5, 0, 3, 1, 4, 2};
		int[] testres1 = {0, 1, 2, 3, 4, 5};
		insertionsort(testar1, testar1.length-1);
		//Arrays.equalsは2つの配列を引数に取るメソッド。
		//2つの配列が同じ値を格納しているならtrueを、そうでないならfalseを返す。
		System.out.println(Arrays.equals(testar1, testres1));
		//System.out.println(Arrays.toString(testar1));//insertionsortの結果（testar1）を表示する
		//test2
		int[] testar2 = {5, 0, 3, 1, 4, 2};
		int[] testres2 = {0, 1, 3, 5, 4, 2};
		insertionsort(testar2, 3);
		System.out.println(Arrays.equals(testar2, testres2));
		//System.out.println(Arrays.toString(testar2));//insertionsortの結果（testar2）を表示する
		//test3
		int[] testar3 = {5, 0, 3, 1, 4, 2};
		int[] testres3 = {0, 5, 3, 1, 4, 2};
		insertionsort(testar3, 1);
		System.out.println(Arrays.equals(testar3, testres3));
		//System.out.println(Arrays.toString(testar3));//insertionsortの結果（testar3）を表示する
		
		
		
	}
}
	
	