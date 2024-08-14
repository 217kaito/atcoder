package m09;

import java.util.ArrayList;
//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;
import java.util.Collections;

// 問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
// 以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
// 難易度：4
// 感想：ジェネリクスなどの様々な機能があって、それを使い分けるのが大変だった。



public class GrandchildList {
	
	public static void main(String[] args)	{
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドは正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
	}
	
	
	private static ArrayList<Integer> getGrandchildren(ArrayList<ArrayList<Integer>> list_adjlist, int node) {

		ArrayList<Integer> list1,list2;
		ArrayList<Integer> list3 =new ArrayList<Integer>();
		int x,y,size1,size2;
		
		//* list1にnodeの子のリストを入れる。
		list1 = list_adjlist.get(node);
		
		//* size1にlist1の大きさを入れる。
		 size1 = list1.size();
		
	//* list3にnodeの孫をすべて入れる。
	     for(int i=0;i<size1;i++) {
	    //* xにlist1、つまりnodeの子のリストのi番目の要素を入れる。
	    	 x=list1.get(i);
	    //* list2にnodeのi番目の子の子のリストを入れる。
	     list2=list_adjlist.get(x);
	    //+ size2にlist2の大きさ入れる。
	     size2=list2.size();
	    //*　list3にnodeのi番目の子の子をすべて入れる。
	     for(int j=0;j<size2;j++) {
	    //* yにnodeのi番目の子の子のリストのj番目の要素を入れる。
	    	 y=list2.get(j);
	    //* list3のリストにyを入れる。
	    	 list3.add(y);
	     }
	     }
	     
	     return list3;
	     
	}
	
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		
		//test1
		ArrayList<ArrayList<Integer>> list_adjlisttest1 = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1, 2));
		list_adjlisttest1.add(list1);
		list1 = new ArrayList<Integer>();
		list_adjlisttest1.add(list1);
		list1 = new ArrayList<Integer>(Arrays.asList(3, 4));
		list_adjlisttest1.add(list1);
		list1 = new ArrayList<Integer>();
		list_adjlisttest1.add(list1);
		list1 = new ArrayList<Integer>();
		list_adjlisttest1.add(list1);
		//System.out.println(list_adjlisttest1);//[[1, 2], [], [3, 4], [], []]
		ArrayList<Integer> list_grandchildtest1_res1 = getGrandchildren(list_adjlisttest1, 0);
		Collections.sort(list_grandchildtest1_res1);
		ArrayList<Integer> list_grandchildtest1_ans1 = new ArrayList<Integer>(Arrays.asList(3, 4));
		//System.out.println(list_grandchildtest1_res1 + " " + list_grandchildtest1_ans1);
		System.out.println(list_grandchildtest1_res1.equals(list_grandchildtest1_ans1));
		
		//test2
		ArrayList<ArrayList<Integer>> list_adjlisttest2 = new ArrayList<ArrayList<Integer>>();
		list1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		list_adjlisttest2.add(list1);
		list1 = new ArrayList<Integer>(Arrays.asList(4, 5));
		list_adjlisttest2.add(list1);
		list1 = new ArrayList<Integer>(Arrays.asList(6, 7, 8, 9));
		list_adjlisttest2.add(list1);
		list1 = new ArrayList<Integer>();
		list_adjlisttest2.add(list1);
		list1 = new ArrayList<Integer>();
		list_adjlisttest2.add(list1);
		list1 = new ArrayList<Integer>(Arrays.asList(10));
		list_adjlisttest2.add(list1);
		for(int a1 = 0; a1 < 5; a1++){
			list1 = new ArrayList<Integer>();
			list_adjlisttest2.add(list1);
		}
		//System.out.println(list_adjlisttest2);//[[1, 2, 3], [4, 5], [6, 7, 8, 9], [], [], [10], [], [], [], [], []]
		ArrayList<Integer> list_grandchildtest2_res1 = getGrandchildren(list_adjlisttest2, 0);
		Collections.sort(list_grandchildtest2_res1);
		ArrayList<Integer> list_grandchildtest2_ans1 = new ArrayList<Integer>(Arrays.asList(4, 5, 6, 7, 8, 9));
		//System.out.println(list_grandchildtest2_res1 + " " + list_grandchildtest2_ans1);
		System.out.println(list_grandchildtest2_res1.equals(list_grandchildtest2_ans1));
		
	}
	
}
	
	