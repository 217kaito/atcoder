package p04;

//グラフ描画用
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


//問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
//以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
//難易度：3
//感想：配列ar1の要素を決めるのが難しかった。

//* interpolationsearchが返す値を5にするために、val1=ar1[4]とし、ar1のインデックス0から、インデックス3の要素にはできるだけ小さい値を、インデックス5の要素には6の倍数で val1より大きな値を入れた。
public class InterpolationSearch extends Frame {
	
	public static void main(String[] args)	{
		
		//互いに異なる整数を格納した配列ar1に対して整数val1を探して補間探索を実行します。
		//補間探索の手順2（配布pdf参照）が5回だけ実行される様にar1とval1を定義して下さい。
		//ただし、ar1の要素数は6とする必要があります。
		//この2つの値はtestメソッドの引数となります。
		int[] ar1 = {0,1,2,3,8,48};
		int val1 = 8;
		
		
		// 作成したメソッドのテストを行うメソッドを呼び出します。
		// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test(ar1, val1);
		
		// interpolationsearchを完成させ、第3回任意課題のbinarysearchメソッドをコピー＆ペーストして以下のメソッドを実行すると
		// 両者の返り値の大きさを比較する折れ線グラフを表示することが出来ます。
		// このグラフは、第3回任意課題とほぼ同様に整数を格納した10通りの大きさの配列を用意し、
		// ランダムに作成した値を用いて探索を行ったときに、
		// interpolationsearch（とbinarysearch）の返り値の大きさ（の平均）を比較する為のものです。
		// binarysearchメソッドをコピー＆ペーストしなくても課題の評価には影響ありません。
		new InterpolationSearch();
		
	}
	
	//* 内挿探索の手順2で探索終了とならないことを条件とするwhile文内で、手順3の操作をさせる。
	//以下のメソッドを完成させること
	private static int interpolationsearch(int[] ar1, int val1, int left, int right) {
		int mid=0,i=0,x=0,y=0,z=0;
		
		left=0;
		right=ar1.length-1;
		
		while(left<=right&&val1>=ar1[left]&&val1<=ar1[right]) {
			i++;
			
			x=right-left;
			y=val1-ar1[left];
			z=ar1[right]-ar1[left];
			mid=left+x*y/z;
			
			if(ar1[mid]==val1)
				return i;
			else if(ar1[mid]<val1)
				left=mid+1;
			else
				right=mid-1;
			
		}
		
		return i;
		
		
	}
	
	//以下のメソッドは完成させなくても良い
	private static int binarysearch(int[] ar1, int val1, int left, int right) {
		
	}
	
	// 作成したメソッドのテストを行うメソッドです。
	// このメソッドがfalseを出力した場合、解答のメソッドが正しく設計されていません。
	// ただし、falseが出力されなかったとしても正解とは限りません。
	private static void test(int testar0[], int testval0) {
		
		//test0
		//配列の大きさ
		boolean testres01 = (testar0.length == 6);
		//ソート済み
		int[] testar0copy = Arrays.copyOf(testar0, testar0.length);
		Arrays.sort(testar0copy);
		boolean testres02 = Arrays.equals(testar0, testar0copy);
		//互いに異なる値
		boolean testres03 = true;
		for(int a1 = 0; a1 < testar0.length-1; a1++){
			if(testar0[a1] == testar0[a1+1]){
				testres03 = false;
				break;
			}
		}
		boolean testres04 = (5 == interpolationsearch(testar0, testval0, 0, testar0.length-1));
		System.out.println(testres01 + " " + testres02 + " " + testres03+ " " + testres04);
		
		//test1
		int[] testar1 = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
		boolean testres11 = (1 == interpolationsearch(testar1, 4, 0, testar1.length-1));
		boolean testres12 = (1 == interpolationsearch(testar1, 2, 0, testar1.length-1));
		boolean testres13 = (1 == interpolationsearch(testar1, 1, 0, testar1.length-1));
		System.out.println(testres11 + " " + testres12 + " " + testres13);
		//test2
		int[] testar2 = {2, 14, 16, 25, 40, 51, 54, 66, 82, 120};
		boolean testres21 = (2 == interpolationsearch(testar2, 20, 0, testar2.length-1));
		boolean testres22 = (3 == interpolationsearch(testar2, 53, 0, testar2.length-1));
		boolean testres23 = (1 == interpolationsearch(testar2, 2, 0, testar2.length-1));
		System.out.println(testres21 + " " + testres22 + " " + testres23);
	}
	
	
	//以下は2つの探索を比較するのに用います。
	//内容について理解できなくても課題を解くのに支障はありません。
	//グラフ描画用
	//ウインドウ
	int g_WinWidth = 1000;
	int g_WinHeight = 700;
	Graphics BufGrph;
	Image BufImg;
	//グラフ描画用クラス
	static MyChart Chart1;
	
	private static int[] getNaturalNumberArray(int size) {
		int[] ar1 = new int[size];
		for(int a1 = 0; a1 < size; a1++){
			ar1[a1] = a1;
		}
		return ar1;
	}
	
	private static int[] getRandomNumberArray(int size, int maxnum, int seed) {
		int[] ar1 = new int[size];
		Random rand = new Random();
		rand.setSeed(seed);
	    for(int a1 = 0; a1 < size; a1++){
			ar1[a1] = rand.nextInt(maxnum);
		}
		return ar1;
	}
	
	private static int[] getRandomNumberArray2(int size, int maxnum, int seed) {
		//探索対象を集合で作成する
		Random rand = new Random();
		rand.setSeed(seed);
		Set<Integer> set1 = new HashSet();
	    while(set1.size() < size){
			int a1 = rand.nextInt(maxnum);
			set1.add(a1);
		}
		//配列に移す
		int[] ar1 = new int[size];
		int cnt = 0;
		for(int a1: set1){
			ar1[cnt] = a1;
			cnt++;
		}
		Arrays.sort(ar1);
		
		//System.out.println(Arrays.toString(ar1));
		
		return ar1;
	}
	
	
	//int trialnum: 試行回数
	//int arrayunitsize: 最小単位の配列の大きさ
	//int numpertrial: 1回辺りの試行回数
	private static int[][] compare(int trialnum, int arrayunitsize, int numpertrial) {
		//探索される値を格納した配列を作成する
		int[][] ar_sparse = new int[trialnum][];
		int[][] ar_int = new int[trialnum][];
		for(int a1 = 0; a1 < trialnum; a1++){
			int arraysize = arrayunitsize * (a1+1);
			int maxnum = 2 * arrayunitsize * (a1+1);//最大値何も考えずに決めている
			//ランダムな値を格納した配列
			ar_sparse[a1] = getRandomNumberArray2(arraysize, maxnum, a1+trialnum);
			//自然数を格納した配列（値が一様に分布している配列）
			ar_int[a1] = getNaturalNumberArray(arraysize);
			
			//System.out.println(Arrays.toString(ar_sparse[a1]));
		}
		//探索する値を格納した配列を作成する
		int[][] ar_trial = new int[trialnum][];
		for(int a1 = 0; a1 < trialnum; a1++){
			int maxnum = 2 * arrayunitsize * (a1+1);//最大値何も考えずに決めている
			ar_trial[a1] = getRandomNumberArray(numpertrial, maxnum, a1);
			
			//System.out.println(Arrays.toString(ar_trial[a1]));
		}
		
		//探索
		int searchnum = 4;
		int[][] ar_res = new int[searchnum][];
		for(int a0 = 0; a0 < searchnum; a0++){
			ar_res[a0] = new int[trialnum];
			for(int a1 = 0; a1 < trialnum; a1++){
				int res = 0;
				int arraysize = arrayunitsize * (a1+1);
				
				for(int a2 = 0; a2 < numpertrial; a2++){
					//ランダムな値を格納した配列
					if(a0 == 0){
						res += binarysearch(ar_sparse[a1], ar_trial[a1][a2], 0, ar_sparse[a1].length-1);
					}
					if(a0 == 1){
						res += interpolationsearch(ar_sparse[a1], ar_trial[a1][a2], 0, ar_sparse[a1].length-1);
					}
					//自然数を格納した配列
					if(a0 == 2){
						res += binarysearch(ar_int[a1], ar_trial[a1][a2], 0, ar_sparse[a1].length-1);
					}
					if(a0 == 3){
						res += interpolationsearch(ar_int[a1], ar_trial[a1][a2], 0, ar_sparse[a1].length-1);
					}
					
				}
				ar_res[a0][a1] = res;
			}
		}
		return ar_res;
	}
	
	
	//
	private void drawSearchComparison(){
		//探索
		int trialnum = 10;//試行回数
		int arrayunitsize = 400;//最小単位の配列の大きさ//大きくしすぎるとint型の最大数を超える
		int numpertrial = 10000;//1回辺りの試行回数
		int[][] ar_res = compare(trialnum, arrayunitsize, numpertrial);
		
		
		//折れ線グラフ描画
		//軸描画
		Chart1.drawChartAxis(Color.BLACK, new Color(230, 230, 230), "Array Size", "Step3", 
			400, 1, 400, 1);
		
		//折れ線グラフ描画
		//点の大きさ
		int recsize = 3;
		
		//
		Color Col_Binary = Color.BLUE;
		Color Col_Interpolation = Color.GREEN;
		Color Col_BinaryN = Color.PINK;
		Color Col_InterpolationN = Color.ORANGE;
		Color ar_ColA[] = {
			Col_Binary, Col_Interpolation,
			Col_BinaryN, Col_InterpolationN
		};
		//凡例
		String ar_StrA[] = {
			"Binary (Random)", "Interpolation (Random)", 
			"Binary (Uniform)", "Interpolation (Uniform)"	
		};
		Chart1.drawLegend(Color.BLACK, ar_StrA, ar_ColA, recsize*2);
		
		//
		int searchnum = 4;
		for(int a0 = 0; a0 < searchnum; a0++){
			//折れ線
			//
			DoublePos[] ar_DPos = new DoublePos[trialnum];
			for(int a1 = 0; a1 < trialnum; a1++){
				int arraysize = arrayunitsize * (a1+1);
				ar_DPos[a1] = new DoublePos(arraysize, (double)ar_res[a0][a1]/(double)numpertrial);
			}
			//折れ線描画
			Chart1.drawChart(ar_ColA[a0], ar_DPos, recsize);
		}
		
		//
		repaint();
	}
	
	public InterpolationSearch(){
		//
		this.setSize(g_WinWidth, g_WinHeight);
		this.setVisible(true);
		//
		BufImg = createImage(getSize().width, getSize().height);
		BufGrph = BufImg.getGraphics();
		
		//グラフ作成
		Chart1 = new MyChart(
			BufGrph, BufImg, 
			1000, 700, 
			0, 0, 4400, 15, 
			125, 125, 125, 125);
		
		
		//描画本体呼び出し
		drawSearchComparison();
	}
	
	public void paint(Graphics g) {
		g.drawImage(BufImg, 0, 0, this);
	}
	
	
}

//折れ線グラフ描画用クラス
class MyChart{
	//ウインドウサイズ
	int WinWidth;// 
	int WinHeight;//
	//描画初期化
	double BaseX;// 
	double TopX;// 
	double BaseY;// 
	double TopY;// 
	//描画領域
	int LeftMargin;// = 125;
	int RightMargin;// = 125;
	int TopMargin;// = 125;
	int BottomMargin;// = 125;
	//
	int BodyWidth;// 
	int BodyHeight;// 
	//
	Graphics BufGrph;
	Image BufImg;
	
	MyChart(){}
	
	MyChart(
		Graphics n_BufGrph, Image n_BufImg, 
		int n_WinWidth, int n_WinHeight, 
		double n_BaseX, double n_BaseY, 
		double n_TopX, double n_TopY, 
		int n_LeftMargin, int n_RightMargin, int n_TopMargin, int n_BottomMargin
	){
		//
		BufGrph = n_BufGrph;
		BufImg = n_BufImg;
		//ウインドウサイズ
		WinWidth = n_WinWidth;
		WinHeight = n_WinHeight;
		//描画初期化
		BaseX = n_BaseX;
		TopX = n_TopX;
		BaseY = n_BaseY;
		TopY = n_TopY;
		//描画領域
		LeftMargin = n_LeftMargin;
		RightMargin = n_RightMargin;
		TopMargin = n_TopMargin;
		BottomMargin = n_BottomMargin;
		//
		BodyWidth = n_WinWidth - n_LeftMargin - n_RightMargin;
		BodyHeight = n_WinHeight - n_TopMargin - n_BottomMargin;
	}
	
	public void setColor(int r1, int b1, int g1){
		BufGrph.setColor(new Color(r1, b1, g1));
	}
	
	public void setColor(Color Col1){
		BufGrph.setColor(Col1);
	}
	
	public IntPos convertPos(DoublePos DPos1){
		double rx1 = (DPos1.PosX - BaseX) / (TopX - BaseX) * (double)BodyWidth;
		double ry1 = (DPos1.PosY - BaseY) / (TopY - BaseY) * (double)BodyHeight;
		
		IntPos PosR = new IntPos(LeftMargin + (int)rx1, TopMargin + BodyHeight - (int)ry1);
		
		return PosR;
	}
	
	//
	public void drawChart(Color ColChart, DoublePos[] ar_DPos, int recsize){
		
		//折れ線グラフ描画
		BufGrph.setColor(ColChart);
		//点の大きさ
		DoublePos DPos1, DPos2, DPos3, DPos4;
		IntPos Pos1, Pos2, Pos3, Pos4;
		for(int a1 = 0; a1 < ar_DPos.length-1; a1++){
			//
			DPos1 = new DoublePos(ar_DPos[a1].PosX, ar_DPos[a1].PosY);
			DPos2 = new DoublePos(ar_DPos[a1+1].PosX, ar_DPos[a1+1].PosY);
			Pos1 = convertPos(DPos1);
			Pos2 = convertPos(DPos2);
			BufGrph.drawLine(Pos1.PosX, Pos1.PosY, Pos2.PosX, Pos2.PosY);
			//点表示
			BufGrph.fillRect(Pos1.PosX-recsize/2, Pos1.PosY-recsize/2, recsize, recsize);
			
			
			//最後の点表示
			if(a1 >= ar_DPos.length-2){
				BufGrph.setColor(ColChart);
				BufGrph.fillRect(Pos2.PosX-recsize/2, Pos2.PosY-recsize/2, recsize, recsize);
			}
		}
		
	}
	
	//
	public void drawChartAxis(Color ColAxis, Color ColSub, String str_LabelX, String str_LabelY, int StepX, int StepY, int LabelStepX, int LabelStepY){
		//軸平行線
		BufGrph.setColor(ColSub);
		//Y軸目盛（X軸平行線）
		DoublePos DPosAX1;
		DoublePos DPosAX2;
		IntPos PosAX1;
		IntPos PosAX2;
		for(int a1 = (int)BaseY+StepY; a1 < TopY; a1+= StepY){
			DPosAX1 = new DoublePos(BaseX, a1);
			DPosAX2 = new DoublePos(TopX, a1);
			PosAX1 = convertPos(DPosAX1);
			PosAX2 = convertPos(DPosAX2);
			BufGrph.drawLine(PosAX1.PosX, PosAX1.PosY, PosAX2.PosX, PosAX2.PosY);
		}
		//X軸目盛（Y軸平行線）
		for(int a1 = (int)BaseX+StepX; a1 < TopX; a1+= StepX){
			DPosAX1 = new DoublePos(a1, BaseY);
			DPosAX2 = new DoublePos(a1, TopY);
			PosAX1 = convertPos(DPosAX1);
			PosAX2 = convertPos(DPosAX2);
			BufGrph.drawLine(PosAX1.PosX, PosAX1.PosY, PosAX2.PosX, PosAX2.PosY);
		}
		
		//軸描画
		BufGrph.setColor(ColAxis);
		//X軸
		DPosAX1 = new DoublePos(BaseX, BaseY);
		DPosAX2 = new DoublePos(BaseX, TopY);
		PosAX1 = convertPos(DPosAX1);
		PosAX2 = convertPos(DPosAX2);
		BufGrph.drawLine(PosAX1.PosX, PosAX1.PosY, PosAX2.PosX, PosAX2.PosY);
		//Y軸
		DoublePos DPosAY1 = new DoublePos(BaseX, BaseY);
		DoublePos DPosAY2 = new DoublePos(TopX, BaseY);
		IntPos PosAY1 = convertPos(DPosAY1);
		IntPos PosAY2 = convertPos(DPosAY2);
		BufGrph.drawLine(PosAY1.PosX, PosAY1.PosY, PosAY2.PosX, PosAY2.PosY);
		//X軸ラベル
		DoublePos DPosAXL = new DoublePos(TopX, BaseY);
		IntPos PosAXL = convertPos(DPosAXL);
		BufGrph.drawString(str_LabelX, PosAXL.PosX, PosAXL.PosY+15);
		//Y軸ラベル
		DoublePos DPosAYL = new DoublePos(BaseX, TopY);
		IntPos PosAYL = convertPos(DPosAYL);
		BufGrph.drawString(str_LabelY, PosAYL.PosX-40, PosAYL.PosY-3);
		//X軸目盛りラベル
		DoublePos DPosOXL;
		IntPos PosOXL;
		for(int a1 = (int)BaseX; a1 < TopX; a1+= LabelStepX){
			DPosOXL = new DoublePos(a1, BaseY);
			PosOXL = convertPos(DPosOXL);
			BufGrph.drawString((int)a1 + "", PosOXL.PosX-5, PosOXL.PosY+15);
		}
		//Y軸目盛りラベル
		DoublePos DPosOYL;
		IntPos PosOYL;
		for(int a1 = (int)BaseY; a1 < TopY; a1+= LabelStepY){
			DPosOYL = new DoublePos(BaseX, a1);
			PosOYL = convertPos(DPosOYL);
			BufGrph.drawString(a1 + "", PosOYL.PosX-30, PosOYL.PosY);
		}
		
	}
	
	//凡例（雑）
	public void drawLegend(Color ColString, String[] StrA, Color[] ColA, int recsize){
		//
		DoublePos DPos1;
		IntPos Pos1;
		for(int a1 = 0; a1 < StrA.length; a1++){
			// 説明
			DPos1 = new DoublePos(BaseX, BaseY);
			Pos1 = convertPos(DPos1);
			Pos1 = new IntPos(Pos1.PosX, Pos1.PosY +40 + 15 * a1);
			BufGrph.setColor(ColString);
			BufGrph.drawString(StrA[a1], Pos1.PosX, Pos1.PosY);
			// 凡例
			BufGrph.setColor(ColA[a1]);
			BufGrph.fillRect(Pos1.PosX-recsize/2-10, Pos1.PosY-recsize/2-5, recsize, recsize);
			
		}
	}
	
}

class IntPos{
	int PosX;
	int PosY;
	
	IntPos(){}
	
	IntPos(int PosX1, int PosY1){
		PosX = PosX1;
		PosY = PosY1;
	}
}

class DoublePos{
	double PosX;
	double PosY;
	
	DoublePos(){}
	
	DoublePos(double PosX1, double PosY1){
		PosX = PosX1;
		PosY = PosY1;
	}
}
