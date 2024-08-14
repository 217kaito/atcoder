package m13;

import java.util.ArrayList;
//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;





// 問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
// 以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
// 難易度：3
// 感想：ArrayListのaddの働き方を勘違いしていて時間がかかってしまった。



public class BinaryHeap {
	
	public static void main(String[] args)	{
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドは正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
	}
	
	
	private static int extractMin(ArrayList<Integer> list_heap){
	    //*変数resultにminヒープH list_heapの最小値を代入する。
		int result = list_heap.get(0);
		//*変数sizeにminヒープH list_heapの大きさを入れる。
		int size = list_heap.size();
		//*minヒープH list_heapの最後尾の頂点vを新しい根とする。
        list_heap.add(0,list_heap.get(size-1));
        //*minヒープH list_heapの最小値の頂点を削除する。
        list_heap.remove(1);
        //*minヒープH list_heapの最後尾の頂点を削除する。
        list_heap.remove(size-1);
        //*頂点vを下移動する。
        movedownward(list_heap,0);
                
        	return result;
	}
	
	
	private static void insert(ArrayList<Integer> list_heap, int val1){
		//*変数sizeにminヒープH list_heapの大きさを入れる。
		int size = list_heap.size();
		//*minヒープH list_heapの最後尾の頂点vに整数val1を入れる。
		list_heap.add(size,val1);
		//*minヒープH list_heapの頂点vを上移動する。
		moveupward(list_heap,size);
	}
	
	
	
	//下移動
	//引数：ArrayList<Integer> list_heap ヒープを表すリスト
	//引数：int index1 下移動させる頂点のインデックス
	private static void movedownward(ArrayList<Integer> list_heap, int index1){
		//要素が存在しない場合
		if(list_heap.size() == 0){
			return;
		}
		//下移動させる頂点（インデックスindex1に格納されている頂点）に格納されている値を取得する
		int val1 = list_heap.get(index1);
		//インデックスindex1の頂点の子供（ただし、2つ子供がある場合は、より小さい値を格納している子供）のインデックスを取得する
		int childindex = getChildIndex(list_heap, index1);
		//子供がいなくなるまで頂点を下に移動させる（下移動）
		while(childindex != -1){
			//下に移動させている頂点に格納されている値とその子供に格納されている値を比較し、
			//下に移動が必要ない場合は下移動を終了する
			if(compare(val1, list_heap.get(childindex)) == true){
				break;
			}
			//index1の頂点に子供の値を格納する
			list_heap.set(index1, list_heap.get(childindex));
			//次に下に移動させる頂点のインデックス（childindex）を代入する
			index1 = childindex;
			//次に下に移動させる頂点の子供（ただし、2つ子供がある場合は、より小さい値を格納している子供）のインデックスを取得する
			childindex = getChildIndex(list_heap, index1);
		}
		//下移動完了時に、移動が完了した頂点に移動させた頂点の値（メソッド呼び出しときのindex1の値）を代入する
		list_heap.set(index1, val1);
		//
		return;
	}
	
	//上移動
	//引数：ArrayList<Integer> list_heap ヒープを表すリスト
	//引数：int index1 上移動させる頂点のインデックス
	private static void moveupward(ArrayList<Integer> list_heap, int index1){
		//要素が存在しない場合
		if(list_heap.size() == 0){
			return;
		}
		//上移動させる頂点（インデックスindex1に格納されている頂点）に格納されている値を取得する
		int val1 = list_heap.get(index1);
		//インデックスindex1の頂点の親のインデックスを取得する
		int parentindex = getParentIndex(index1);
		
		//親がいなくなるまで頂点を上に移動させる（上移動）
		while(parentindex != -1){
			//上に移動させている頂点に格納されている値とその親に格納されている値を比較し、
			//上に移動が必要ない場合は上移動を終了する
			if(compare(list_heap.get(parentindex), val1) == true){
				 break;
			}
			
			//index1の頂点に親の値を格納する
			list_heap.set(index1, list_heap.get(parentindex));
			
			//次に上に移動させる頂点のインデックス（parentindex）を代入する
			index1 = parentindex;
			//次に上に移動させる頂点の親のインデックスを取得する
			parentindex = getParentIndex(index1);
		}
		//上移動完了時に、移動が完了した頂点に移動させた頂点の値（メソッド呼び出しときのindex1の値）を代入する
		list_heap.set(index1, val1);
		//
		return;
	}
	
	//インデックスindex1の頂点の（より小さい値をもつ）子供のインデックスを取得する
	private static int getChildIndex(ArrayList<Integer> list_heap,  int index1){
		//左の子供のインデックスを取得する
		int leftchildindex = getLeftChildIndex(index1);
		//ヒープの大きさ
		int length = list_heap.size();
		// 左の子供が存在しない（＝子供が存在しない）場合
		if(length <= leftchildindex){
			return -1;
		}
		//右の子供のインデックスを取得する
		int rightchildindex = getRightChildIndex(index1);
		// 左の子供のみ存在する場合
		if(length <= rightchildindex){
			//左の子供のインデックスを返す
			return leftchildindex;
		}
		// 左と右の子供のうち小さい値を格納している子供のインデックスを返す
		if(compare(list_heap.get(leftchildindex), list_heap.get(rightchildindex)) == true){
			return leftchildindex;
		}
		return rightchildindex;
	}
	
	//2つの子供・親子に格納される値の大小関係を調べる
	private static boolean compare(int val1, int val2){
		return (val1 <= val2);
	}
	
	//インデックスindex1の頂点の親のインデックスを取得する
	private static int getParentIndex(int index1){
    	if(index1 <= 0){
        	return -1;
    	}
    	return (int)((index1-1) / 2);
	}
	
	//インデックスindex1の頂点の左の子のインデックスを取得する
	private static int getLeftChildIndex(int index1){
    	return 2 * index1 + 1;
	}
	
	//インデックスindex1の頂点の右の子のインデックスを取得する
	private static int getRightChildIndex(int index1){
	   return 2 * index1 + 2;
	}
	
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		//test1
		System.out.println("insert tests:");
		//test1-1
		ArrayList<Integer> test1list1 = new ArrayList<Integer>(Arrays.asList(8, 15, 34, 37, 23, 58));
		insert(test1list1, 4);
		//System.out.println(test1list1);
		ArrayList<Integer> test1ans1 = new ArrayList<Integer>(Arrays.asList(4, 15, 8, 37, 23, 58, 34));
		System.out.println(test1list1.equals(test1ans1));
		//test1-2
		insert(test1list1, 20);
		//System.out.println(test1list1);
		ArrayList<Integer> test1ans2 = new ArrayList<Integer>(Arrays.asList(4, 15, 8, 20, 23, 58, 34, 37));
		System.out.println(test1list1.equals(test1ans2));
		//test2
		System.out.println("extractMin tests:");
		//test2-1
		ArrayList<Integer> test2list1 = new ArrayList<Integer>(Arrays.asList(5, 33, 53, 97, 49, 65));
		int test2res1 = extractMin(test2list1);
		//System.out.println(test2res1 + " " + test2list1);
		ArrayList<Integer> test2ans1 = new ArrayList<Integer>(Arrays.asList(33, 49, 53, 97, 65));
		System.out.println((test2res1 == 5) + " " + test2list1.equals(test2ans1));
		//test2-2
		int test2res2 = extractMin(test2list1);
		//System.out.println(test2res2 + " " + test2list1);
		ArrayList<Integer> test2ans2 = new ArrayList<Integer>(Arrays.asList(49, 65, 53, 97));
		System.out.println((test2res2 == 33) + " " + test2list1.equals(test2ans2));
		
		
	}
	
	
}

