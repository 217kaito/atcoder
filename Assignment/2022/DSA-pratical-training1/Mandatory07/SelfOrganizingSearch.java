
package m07;

//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;
import java.util.LinkedList;



// 問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
// 以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
// 難易度：3
// 感想：ArrayListとLinkedListのどちらが適しているかを考えるのが大変だった。



public class SelfOrganizingSearch {
	
	public static void main(String[] args)	{
		
		
		// インデックスがindex1の要素をlist1の先頭に挿入する際にArrayListではインデックスが０から(index1-1)の要素の値を一つずつ後方にずらす必要があるが、LinkedListではその必要がないため。
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドは正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
	}
	
	/* 
	private static int get(ArrayList<Integer> list1, int index1) {
		
	}
	
	private static boolean search(ArrayList<Integer> list1, int val1) {
		
	}
	*/
	 
	//*list1の、インデックスがindex1の要素をlist1の先頭に移動させ、その要素の値を返す。
	private static int get(LinkedList<Integer> list1, int index1) {
		int x=list1.get(index1);
		
		list1.remove(index1);
		
		list1.add(0,x);
		
		return x;
	}
		
	//*list1の要素で、val1と同じ値を持つものの中で最小のインデックスのものをlist1の先頭に移動させ、trueを返す。
	//*list1の要素で、val1と同じ値を持つものがない場合はfalseを返す。
		private static boolean search(LinkedList<Integer> list1, int val1) {
		for(int i=0;i<list1.size()-1;i++)
			if(list1.get(i)==val1) {
				int x=list1.get(i);
				list1.remove(i);
				list1.add(0,x);
				return true;
			}
		return false;
	}
		
	

	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		/* 
		// test for ArrayList
		System.out.println("tests for get:");
		ArrayList<Integer> testlist1 = new ArrayList<Integer>(Arrays.asList(0, 10, 20, 30, 40, 50));
		int resval1 = get(testlist1, 3);
		ArrayList<Integer> reslist1 = new ArrayList<Integer>(Arrays.asList(30, 0, 10, 20, 40, 50));
		System.out.println((resval1 == 30) + " " + testlist1.equals(reslist1));//同じリストになっているか判定する
		//System.out.println(testlist1 + " " + resval1 + " "+ reslist1);
		 resval1 = get(testlist1, 5);
		reslist1 = new ArrayList<Integer>(Arrays.asList(50, 30, 0, 10, 20, 40));
		System.out.println((resval1 == 50) + " " + testlist1.equals(reslist1));//同じリストになっているか判定する
		//System.out.println(testlist1 + " " + reslist1);
		
		System.out.println("tests for search:");
		ArrayList<Integer> testlist2 = new ArrayList<Integer>(Arrays.asList(0, 10, 20, 30, 40, 50));
		boolean resf2 = search(testlist2, 40);
		ArrayList<Integer> reslist2 = new ArrayList<Integer>(Arrays.asList(40, 0, 10, 20, 30, 50));
		System.out.println((resf2 == true) + " " + testlist2.equals(reslist2));//同じリストになっているか判定する
		resf2 = search(testlist2, 100);
		System.out.println((resf2 == false) + " " + testlist2.equals(reslist2));//同じリストになっているか判定する
		
		ArrayList<Integer> testlist3 = new ArrayList<Integer>(Arrays.asList(0, 10, 40, 30, 40, 20));
		boolean resf3 = search(testlist3, 40);
		ArrayList<Integer> reslist3 = new ArrayList<Integer>(Arrays.asList(40, 0, 10, 30, 40, 20));
		System.out.println((resf3 == true) + " " + testlist3.equals(reslist3));//同じリストになっているか判定する
		*/
		
		
		// test for LinkedList
		System.out.println("tests for get:");
		LinkedList<Integer> testlist1 = new LinkedList<Integer>(Arrays.asList(0, 10, 20, 30, 40, 50));
		int resval1 = get(testlist1, 3);
		LinkedList<Integer> reslist1 = new LinkedList<Integer>(Arrays.asList(30, 0, 10, 20, 40, 50));
		System.out.println((resval1 == 30) + " " + testlist1.equals(reslist1));//同じリストになっているか判定する
		//System.out.println(testlist1 + " " + resval1 + " "+ reslist1);
		 resval1 = get(testlist1, 5);
		reslist1 = new LinkedList<Integer>(Arrays.asList(50, 30, 0, 10, 20, 40));
		System.out.println((resval1 == 50) + " " + testlist1.equals(reslist1));//同じリストになっているか判定する
		//System.out.println(testlist1 + " " + reslist1);
		
		System.out.println("tests for search:");
		LinkedList<Integer> testlist2 = new LinkedList<Integer>(Arrays.asList(0, 10, 20, 30, 40, 50));
		boolean resf2 = search(testlist2, 40);
		LinkedList<Integer> reslist2 = new LinkedList<Integer>(Arrays.asList(40, 0, 10, 20, 30, 50));
		System.out.println((resf2 == true) + " " + testlist2.equals(reslist2));//同じリストになっているか判定する
		resf2 = search(testlist2, 100);
		System.out.println((resf2 == false) + " " + testlist2.equals(reslist2));//同じリストになっているか判定する
		
		LinkedList<Integer> testlist3 = new LinkedList<Integer>(Arrays.asList(0, 10, 40, 30, 40, 20));
		boolean resf3 = search(testlist3, 40);
		LinkedList<Integer> reslist3 = new LinkedList<Integer>(Arrays.asList(40, 0, 10, 30, 40, 20));
		System.out.println((resf3 == true) + " " + testlist3.equals(reslist3));//同じリストになっているか判定する
		
		
	}
}
