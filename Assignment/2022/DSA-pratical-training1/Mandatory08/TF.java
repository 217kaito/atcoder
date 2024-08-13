package m08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;



// 問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
// 以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
// 難易度：4
// 感想：リストやハッシュマップなど使用法が似たようなものがあって紛らわしかった。



public class TF {
	private static final String str_datafile = "src/m08/ex8_data_words.csv";
	
	public static void main(String[] args)	{
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドは正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
	}
	
	//* 二つ目のfor文でlist_word内の各文字の個数を調べ、三つ目のfor文で各単語のtf値を求める。
	//+ 
	
	private static HashMap<String, Double> getTF(ArrayList<String> list_word) {
		
		HashMap<String,Double> map1=new HashMap<String,Double>();
		int size=list_word.size();
		
		//* 計算量はO(2*size)
		for(int i=0;i<size;i++) 
			map1.put(list_word.get(i),0.0);
		
		//*計算量はO(4*size)
		for(int i=0;i<size;i++) { 
			double j=map1.get(list_word.get(i))+1.0;
		map1.put(list_word.get(i),j);
		}
		
		//*map1の要素数をsize1とすると計算量はO(3＊size1)
		for(String str1: map1.keySet()) {
			double j=map1.get(str1)/size;
			map1.put(str1, j);
		}
				
		return map1;
				}
			

	
	
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		//test1
		ArrayList<String> testlist1 = new ArrayList<String>(Arrays.asList("A", "B", "A", "C"));
		HashMap<String, Double> testres1 = getTF(testlist1);
		HashMap<String, Double> testans1 = new HashMap<String, Double>();
		testans1.put("A", 0.5);
		testans1.put("B", 0.25);
		testans1.put("C", 0.25);
		//System.out.println(testres1);
		System.out.println(testres1.equals(testans1));
		//test2
		ArrayList<String> testlist2 = new ArrayList<String>(Arrays.asList("A", "B", "A", "C", "B", "A", "A", "D"));
		HashMap<String, Double> testres2 = getTF(testlist2);
		HashMap<String, Double> testans2 = new HashMap<String, Double>();
		testans2.put("A", 0.5);
		testans2.put("B", 0.25);
		testans2.put("C", 0.125);
		testans2.put("D", 0.125);
		//System.out.println(testres2);
		System.out.println(testres2.equals(testans2));
		
		//実データを用いたテスト
		test2();
		
		
	}
	
	//実データを用いたテスト
	private static void test2() {
		System.out.println("夏目漱石『坊ちゃん』の実データを用いたテスト：");
		//文書データ呼び出し
		ArrayList<ArrayList<String>> list_testdata = getData();
		/*
		//データの出力
		for(String str1: list_testdata.get(0)) {
			System.out.print(str1);
		}
		*/
		//
		ArrayList<String> testres10 = new ArrayList<String>();
		for(int a1 = 0; a1 < list_testdata.size(); a1++){
			System.out.println((a1+1) + "章");
			HashMap<String, Double> testres = getTF(list_testdata.get(a1));
			//値の降順でソート
			ArrayList<Entry<String, Double>> list1 = new ArrayList<Entry<String, Double>>(testres.entrySet());
			Collections.sort(list1, new Comparator<Entry<String, Double>>(){
				public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2){
					return obj2.getValue().compareTo(obj1.getValue());
            	}
        	});
			//章毎に降順でtf値トップ5の単語を出力
			int cnt = 0;
			for(Entry<String, Double> entry1 : list1) {
				if(cnt == 2){
					testres10.add(entry1.getKey());
				}
				System.out.println(entry1.getKey() + " : " + entry1.getValue());
				if(cnt++ >= 4){break;}
			}
		}
		ArrayList<String> testans10 = new ArrayList<String>(Arrays.asList("いる","云う","ある","云う","いる","おれ","なる","の","いる","いる","云う"));
		System.out.println(testres10.equals(testans10));
	}
	
	//csvファイルの実データ呼び出し
	private static ArrayList<ArrayList<String>> getData() {
		//
		String str_filename = str_datafile; 
		ArrayList<ArrayList<String>> list_data = new ArrayList<ArrayList<String>>();
		//
		BufferedReader br = null;
		try {
  			File file = new File(str_filename);
			br = new BufferedReader(new FileReader(file));
 			String line;
			String[] ar1;
			ArrayList<String> list1;
 			while ((line = br.readLine()) != null) {
				ar1 = line.split(",");
				//
				list1 = new ArrayList<String>();
				for(int a1 = 0; a1 < ar1.length; a1++){
					list1.add(ar1[a1]);
				}
				list_data.add(list1);
			}
		} catch (Exception e) {
  			System.out.println(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
  		return list_data;
	}
	
}