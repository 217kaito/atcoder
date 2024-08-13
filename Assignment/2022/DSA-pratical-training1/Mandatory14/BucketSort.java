package m14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;





// 問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
// 以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
// 難易度：5
// 感想：配列Bに格納する待ち行列をm個用意する必要があることになかなか気づけなかった。



public class BucketSort{
	private static final String str_datafile = "src/m14/ex14_data_words.csv";
	
	public static void main(String[] args)	{
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドは正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
	}
	
	
	private static ArrayList<Queue<String>> bucketsort(String[] wordlist, int[] wordnum, int m, int k) {
		ArrayList<Queue<String>> B = new ArrayList<>();
		
		//*リストBのz番目のセルをB[z]と表す
		
		//*大きさ m の配列 B を作成し、各 j (= 0, 1, …, m-1) に対してB[j] に格納する待ち行列を初期化する
		for(int j = 0;j < m;j++) {
			Queue<String> A = new LinkedList<>();
			B.add(j,A);
		}
		
		//*配列wordlistの大きさをnとして、各 i (= 0, 1, …, n-1) に対して、wordlist[i] の値を B[wordnum[i]] に格納した待ち行列に挿入する
		for(int j = 0;j < m;j++)
			for(int i = 0;i < wordnum.length;i++) 
				if(wordnum[i] == j)
					B.get(j).add(wordlist[i]);
		
		int y = 0;
         
		//*xが0からm－1まで、それぞれこのfor文内を実行する
		for(int x = 0;x < m;x++) 
		//*	B[x] の待ち行列が空でないかつ、yがk以下ならば値を取り出して、 wordlist[y] に代入してyを1増やす
			for(;B.get(x).size() != 0&&y < k;y++) 
				wordlist[y] = B.get(x).poll();
		
		return B;
	}
	
	
	
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test() {
		//test1
		String[] test1wordlist1 = {"A", "B", "C", "D", "E", "F", "G", "H"};
		int[] test1wordnum1 = {2, 1, 3, 2, 4, 2, 2, 3};
		int test1m = 5;
		int test1k = test1wordlist1.length;
		ArrayList<Queue<String>> list_buckettest1res1 = bucketsort(test1wordlist1, test1wordnum1, test1m, test1k);
		Queue<String> que1;
		boolean f1 = true;
		for(int a1 = 0; a1 < test1m; a1++){
			que1 = list_buckettest1res1.get(a1);
			f1 &= que1.equals(new LinkedList<String>());
		}
		//System.out.println(Arrays.toString(test1wordlist1));
		String[] test1ans1 = {"B", "A", "D", "F", "G", "C", "H", "E"};
		System.out.println(Arrays.equals(test1wordlist1, test1ans1) + " " + f1);
		//test2
		String[] test2wordlist1 = {"A", "B", "C", "D", "E", "F", "G", "H"};
		int[] test2wordnum1 = {2, 1, 3, 2, 4, 2, 2, 3};
		int test2m = 5;
		int test2k = 5;
		ArrayList<Queue<String>> list_buckettest2res1 = bucketsort(test2wordlist1, test2wordnum1, test2m, test2k);
		f1 = true;
		for(int a1 = 0; a1 < test1m; a1++){
			que1 = list_buckettest2res1.get(a1);
			//System.out.println(que1);
			switch(a1){
				case 0:
				case 1:
				case 2:
					f1 &= que1.equals(new LinkedList<String>());
					break;
				case 3:
					f1 &= que1.equals(new LinkedList<String>(Arrays.asList("C", "H")));
					break;
				case 4:
					f1 &= que1.equals(new LinkedList<String>(Arrays.asList("E")));
					break;
			}
		}
		String[] test2res1 = Arrays.copyOfRange(test2wordlist1, 0, test2k);
		//System.out.println(Arrays.toString(test2res1));
		String[] test1ans2 = {"B", "A", "D", "F", "G"};
		System.out.println(Arrays.equals(test2res1, test1ans2) + " " + f1);
		
		
		
		//実データを用いたテスト
		test2();
		
		
	}
	
	
	//実データを用いたテスト
	private static void test2() {
		System.out.println("太宰治『走れメロス』の実データを用いたテスト：");
		//文書データ呼び出し
		ArrayList<TmpData> list_testdata = getData4();
		String[] test1wordlist1 = new String[list_testdata.size()];
		int[] test1wordnum1 = new int[list_testdata.size()];
		int cnt = 0;
		int test1m = -1;
		int test1k = list_testdata.size();
		for(TmpData tdata1: list_testdata) {
			//System.out.println(tdata1.word + " " + tdata1.value);
			test1wordlist1[cnt] = tdata1.word;
			test1wordnum1[cnt] = tdata1.value;
			//
			if(test1m < tdata1.value){
				test1m = tdata1.value;
			}
			
			cnt++;
		}
		test1m++;
		
		//
		bucketsort(test1wordlist1, test1wordnum1, test1m, test1k);
		
		//test
		/*
		for(int a1 = 0; a1 < test1wordlist1.length; a1++){
			System.out.println(a1 + " " +  test1wordlist1[a1]);
		}*/
		
		System.out.println(test1wordlist1[245].equals("おめでとう") + " " + test1wordlist1[1027].equals("セリヌンティウス") + " " + test1wordlist1[1036].equals("走る") + " " + test1wordlist1[1043].equals("メロス"));
	}
	
	
	private static ArrayList<TmpData> getData4() {
		//
		ArrayList<TmpData> list_data = new ArrayList<TmpData>();
		//
		BufferedReader br = null;
		try {
  			File file = new File(str_datafile);
			br = new BufferedReader(new FileReader(file));
 			String line;
			String[] ar1;
			while ((line = br.readLine()) != null) {
				//System.out.print(line);
				//
				ar1 = line.split(",");
				//
				String str1 = ar1[0];
				int val1 = 	Integer.parseInt(ar1[1]);
				TmpData tmpdata1 = new TmpData(val1, str1);
				
				//
				list_data.add(tmpdata1);
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
		
		//System.out.println(list_data);
		
		return list_data;
  	}
	
	
}

class TmpData{
	int value;
	String word;
	
	public TmpData(){
	}
	
	public TmpData(int val1, String str1){
		value = val1;
		word = str1;
	}
}




