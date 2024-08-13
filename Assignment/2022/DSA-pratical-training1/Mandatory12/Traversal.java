package m12;

import java.util.ArrayList;
//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;





// 問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
// 以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
// 難易度：5
// 感想：再帰関数を使うことになかなか気づけなかった。


class BinaryTreeNode{
	int value;
	BinaryTreeNode left;
	BinaryTreeNode right;
	
	public BinaryTreeNode(){
		value = -1;
		left = null;
		right = null;
	}
	
	public BinaryTreeNode(int val1, BinaryTreeNode left1, BinaryTreeNode right1){
		value = val1;
		left = left1;
		right = right1;
	}
}


public class Traversal {
	
	public static void main(String[] args)	{
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドは正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
		
	}
	
	
	private static void preordertraverse(BinaryTreeNode root, ArrayList<Integer> list_node) {
      
		//*二分木が空ではないか調べる。
		if(root!=null) {
			//*二分木の根をリストに加える。
			list_node.add(root.value);
			//*二分木の左分木の前順を求める。
			preordertraverse(root.left,list_node);
			//*二分木の右分木の前順を求める。
			preordertraverse(root.right,list_node);
		}
       
	}
	
	private static void postordertraverse(BinaryTreeNode root, ArrayList<Integer> list_node) {
		
		//*二分木が空ではないか調べる。
		if(root!=null) {
			//*二分木の左分木の後順を求める。
			postordertraverse(root.left,list_node);
			//*二分木の右分木の後順を求める。
			postordertraverse(root.right,list_node);
			//*二分木の根をリストに加える。
			list_node.add(root.value);
		}
	}
	
	private static void inordertraverse(BinaryTreeNode root, ArrayList<Integer> list_node) {
		
		//*二分木が空ではないか調べる。
		if(root!=null) {
			//*二分木の左分木の中順を求める。
			inordertraverse(root.left,list_node);
			//*二分木の根をリストに加える。
			list_node.add(root.value);
			//*二分木の右分木の中順を求める。
			inordertraverse(root.right,list_node);
		}
	}
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		//test1
		BinaryTreeNode nodeGtest1 = new BinaryTreeNode(38, null, null);
		BinaryTreeNode nodeFtest1 = new BinaryTreeNode(9, nodeGtest1, null);
		BinaryTreeNode nodeEtest1 = new BinaryTreeNode(34, null, null);
		BinaryTreeNode nodeDtest1 = new BinaryTreeNode(41, null, null);
		BinaryTreeNode nodeCtest1 = new BinaryTreeNode(22, nodeEtest1, nodeFtest1);
		BinaryTreeNode nodeBtest1 = new BinaryTreeNode(10, nodeDtest1, null);
		BinaryTreeNode nodeAtest1 = new BinaryTreeNode(17, nodeBtest1, nodeCtest1);
		//test1-1
		ArrayList<Integer> list_nodetest11 = new ArrayList<Integer>();
		preordertraverse(nodeAtest1, list_nodetest11);
		//System.out.println(list_nodetest11);
		ArrayList<Integer> list_test11ans = new ArrayList<Integer>(Arrays.asList(17, 10, 41, 22, 34, 9, 38));
		System.out.println(list_nodetest11.equals(list_test11ans));
		//test1-2
		ArrayList<Integer> list_nodetest12 = new ArrayList<Integer>();
		postordertraverse(nodeAtest1, list_nodetest12);
		//System.out.println(list_nodetest12);
		ArrayList<Integer> list_test12ans = new ArrayList<Integer>(Arrays.asList(41, 10, 34, 38, 9, 22, 17));
		System.out.println(list_nodetest12.equals(list_test12ans));
		//test1-3
		ArrayList<Integer> list_nodetest13 = new ArrayList<Integer>();
		inordertraverse(nodeAtest1, list_nodetest13);
		//System.out.println(list_nodetest13);
		ArrayList<Integer> list_test13ans = new ArrayList<Integer>(Arrays.asList(41, 10, 17, 34, 22, 38, 9));
		System.out.println(list_nodetest13.equals(list_test13ans));
		
		
	}
	
	
}



