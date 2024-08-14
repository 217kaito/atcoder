package m06;

//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;



//問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
//以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
//難易度：4
//感想：問題の理解に時間がかかってしまった。


public class QuickSort {
	
	public static void main(String[] args)	{
		
		
		
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
	}
	
	//*right<=leftではない条件で関数quicksortを再帰的に呼び出すことでクイックソートを実現する。
	private static void quicksort(int[] ar1, int left, int right) {
		if(right<=left)
			return;
		
		partition(ar1,left,right,pickPivot(ar1,left,right));
		quicksort(ar1,left,partition(ar1,left,right,pickPivot(ar1,left,right)));
		quicksort(ar1,partition(ar1,left,right,pickPivot(ar1,left,right))+1,right);
	}
	
	// このコメントとpartitionの内部のコメントは自由に改変して構いません。
	//メソッド内の「...」が必ずしも1行で記述できる内容を表すわけではありません。
	//*ar1を分割した配列を入れるための大きさが(right-left）の配列ar2を用意する。
	//*一つ目のfor文では、配列ar1の要素でpivot未満のものを配列ar1でのインデックスが昇順になるように配列ar2のインデックス０の要素から順番に代入していく。代入後の配列ar2の要素でpivot未満のもののうち、最大のインデックスを持つもののインデックスをｎとする。
	//*また、pivot以上のものを配列ar2でのインデックスが降順になるように配列ar2のインデックス（ｎ∔1）の要素から順番に代入していく。
	//*二つ目のfor文では配列ar1のインデックスleftからrightまでに、それを分割した状態になるように配列ar2を代入していく。
	//*最後に配列ar1でpivotと同じ値を持つ要素のインデックスを返す。
	private static int partition(int[] ar1, int left, int right, int pivot) {
		// 分割の途中経過を保存する為の配列ar2の作成
		int[] ar2 = new int[ar1.length];
		// 
		int tmpleft = 0;
		int tmpright = right-left;
		//
		for(int i = left; i<right+1;i++){
			//ar1[i] < pivotなら 
			if(ar1[i]<pivot) {
				ar2[tmpleft]=ar1[i];
				tmpleft++;
			}
			//ar1[i] >= pivotなら 
			if(ar1[i]>=pivot) {
				ar2[tmpright]=ar1[i];
				tmpright=tmpright-1;
			}
				
		}
		
		//ar2の結果をar1にコピーする
        for(int i=0;i<right-left+1;i++) {
			
			ar1[i+left]=ar2[i];
		}
		
		//
		return left+tmpleft-1;
	}
	
	
	// ピボット選択
	//* インデックスleftから順にar1の値を調べて、
	//* 最初に得られた異なる値2つの値の大きい方をピボットとして選択する
	private static int pickPivot(int[] ar1, int left, int right) {
		if(ar1[left] < ar1[left+1]){
			return ar1[left+1];
		}
		return ar1[left];
	}
	
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		
		System.out.println("tests for partition:");
		//test1
		int[] testar1 = {5, 0, 3, 1, 2, 4};
		int[] testres1 = {0, 3, 1, 2, 4, 5};
		int testnewright1 = partition(testar1, 0, 5, 5);
		//Arrays.equalsは2つの配列を引数に取るメソッド。
		//2つの配列が同じ値を格納しているならtrueを、そうでないならfalseを返す。
		System.out.println(Arrays.equals(testar1, testres1) + " " + (testnewright1==4));
		//System.out.println(Arrays.toString(testar1) + " " + testnewright1);
		//test2
		int[] testar2 = {0, 3, 1, 2, 4, 5};
		int[] testres2 = {0, 1, 2, 4, 3, 5};
		int testnewright2 = partition(testar2, 1, 4, 3);
		System.out.println(Arrays.equals(testar2, testres2) + " " + (testnewright2==2));
		//System.out.println(Arrays.toString(testar2) + " " + testnewright2);
		//test3
		int[] testar3 = {0, 3, 1, 2, 4};
		int[] testres3 = {0, 1, 2, 4, 3};
		int testnewright3 = partition(testar3, 0, 4, 3);
		System.out.println(Arrays.equals(testar3, testres3) + " " + (testnewright3==2));
		//System.out.println(Arrays.toString(testar3) + " " + testnewright3);
		//test4
		int[] testar4 = {0, 3, 1, 2, 4};
		int[] testres4 = {0, 3, 1, 2, 4};
		int testnewright4 = partition(testar4, 3, 4, 3);
		System.out.println(Arrays.equals(testar4, testres4) + " " + (testnewright4==3));
		//System.out.println(Arrays.toString(testar4) + " " + testnewright4);
		
		
		System.out.println("tests for quicksort:");
		//test1
		int[] test2ar1 = {5, 0, 3, 1, 2, 4};
		int[] test2res1 = {0, 1, 2, 3, 4, 5};
		quicksort(test2ar1, 0, test2ar1.length-1);
		System.out.println(Arrays.equals(test2ar1, test2res1));
		//System.out.println(Arrays.toString(test2ar1));
		//test2
		int[] test2ar2 = {5, 0, 3, 1, 2, 4};
		int[] test2res2 = {0, 1, 3, 5, 2, 4};
		quicksort(test2ar2, 0, 3);
		System.out.println(Arrays.equals(test2ar2, test2res2));
		//System.out.println(Arrays.toString(test2ar2));
		//test3
		int[] test2ar3 = {5, 0, 3, 1, 2, 4};
		int[] test2res3 = {5, 0, 1, 2, 3, 4};
		quicksort(test2ar3, 2, 4);
		System.out.println(Arrays.equals(test2ar3, test2res3));
		//System.out.println(Arrays.toString(test2ar3));
		
	}
	
}
	
	