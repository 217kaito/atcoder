package m03;


//問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
//難易度：3
//感想：一見して簡単だと思い油断していたら、while文の繰り返し条件の不等号を間違えてしまっていて時間がかかってしまったので、意味を考えながらプログラムするようにしていきたいと思いました。


public class BinarySearch {
	
	public static void main(String[] args)	{
		
		
		// 作成したメソッドのテストを行うメソッドを呼び出します。
		// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
	}
	
	//*while文内ではleftの値がrightの値以下ならばmidにleftとrightの値の平均値の絶対値を代入し、配列ar1の要素番号がmidの要素がvallに等しければmidを返し、vallより大きければrightに（mid-1）を、小さければleftに(mid+1)を代入し、while文の先頭に戻す、そしてleftの値がrightの値より大きくなったら配列ar1の要素にはvallはないとして―1を返す動きをする。
	
	private static int binarysearch(int[] ar1, int val1, int left, int right) {

		int mid=0;
		
		while(left<=right) {
			
		    mid=(left+right)/2;
		
		if(ar1[mid]==val1)
			return mid;
		else if(ar1[mid]>val1)
			right=mid-1;
		else
			left=mid+1;
		}
		return -1;
		
	}
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		int[] testar1 = {0, 1, 2, 3, 4, 5};
		boolean testres11 = (binarysearch(testar1, 3, 0, 5) == 3);
		boolean testres12 = (binarysearch(testar1, 0, 0, 5) == 0);
		boolean testres13 = (binarysearch(testar1, -1, 0, 5) == -1);
		boolean testres14 = (binarysearch(testar1, 3, 2, 4) == 3);
		System.out.println(testres11 + " " + testres12 + " " + testres13 + " " + testres14);
		
	}
	
}
	
	

	
	