// うまくいってない場所があるので時間があるときに修正

package m11;

//グラフ描画用
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
//ファイル操作用
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
//以下のimportの内容について理解できなくても課題を解くのに支障はありません
//テスト用
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;





// 問題の難易度評価： この問の難易度を5段階（1:簡単、2:やや簡単、3:普通、4:やや難しい、5:難しい）で評価して下さい。（次回以降の課題の難易度の調整に使います） また、解答するのにかかった時間や感想などがあれば適宜記載して下さい。
// 以下の行の「難易度：」と「感想：」という文字を削除してはいけません。（難易度と感想は提出後のファイルから機械的に収集しています。）
// 難易度：3
// 感想：前回とほぼ同じだと気づいたらすぐ終わったのでびっくりした。



public class DepthFirstSearch extends Frame {
	private static final String str_datafile = "src/m11/ex11_data_list_TokugawaFamilyTree.csv";
	private static final String str_datafile2 = "src/m11/ex11_data_list_TokugawaFamilyName.csv";
	private static final String str_datafile3 = "src/m11/ex11_data_list_TokugawaFamilyPos.csv";
	
	public static void main(String[] args)	{
		
		
		// 作成したメソッドのテストを行うメソッドです。
		// このメソッドがfalseを出力した場合、解答のメソッドは正しく設計されていません。
		// ただし、falseが出力されなかったとしても正解とは限りません。
		test();
		
		
		// 実データの木描画
		new DepthFirstSearch();
		
	}
	
	private static ArrayList<Integer> search(ArrayList<ArrayList<Integer>> list_adjlist, int root, int nodenum) {
		Stack<Integer> stack1 = new Stack<>();
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> list1;
		int y=0;
		int size1,size2;
		//*スタックstack1にrootを挿入する。
		stack1.add(root);
		
		while(true) {
		//*スタックstack1の要素数を変数size1に代入する。
	    size1=stack1.size();	
	  //*スタックstack1が空の場合、探索を終了して、深さ優先探索を実行して前順に頂点を格納したリストであるresultを返す。
		if(size1==0)
			return result;
		//*スタックstack1の先頭の要素を削除する。
		int i=stack1.pop();
		//*上の行で削除した要素iをresultに挿入する。
		result.add(i);
		//*探索を行った木構造の頂点数を調べるための変数yの値を１増やす。
		y++;
		//*探索を行った木構造の頂点数がnodenum以上になった場合、結果を返すリストであるresultを返す。
		if(y>=nodenum)
			return result;
		//*list1に削除した要素iの子のリストを入れる。
		list1=list_adjlist.get(i);
		//*list1の要素数を変数size2に代入する。
		size2=list1.size();
		//*削除した要素iの各子どもをスタックstack1に挿入する。
		for(int j=0;j<size2;j++) {
			int x=list1.get(j);
		  stack1.add(x);
		}
		}
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
		ArrayList<Integer> list_test1_res1 = search(list_adjlisttest1, 0, list_adjlisttest1.size());
		ArrayList<Integer> list_test1_ans1 = new ArrayList<Integer>(Arrays.asList(0, 2, 4, 3, 1));
		//System.out.println(list_test1_res1 + " " + list_test1_ans1);
		System.out.println(list_test1_res1.equals(list_test1_ans1));
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
		ArrayList<Integer> list_test2_res1 = search(list_adjlisttest2, 0, list_adjlisttest2.size());
		ArrayList<Integer> list_test2_ans1 = new ArrayList<Integer>(Arrays.asList(0, 3, 2, 9, 8, 7, 6, 1, 5, 10, 4));
		//System.out.println(list_test2_res1 + " " + list_test2_ans1);
		System.out.println(list_test2_res1.equals(list_test2_ans1));
		
		
		
		//実データを用いたテスト
		test2();
		
		
	}
	
	//実データを用いたテスト
	private static void test2() {
		System.out.println("Wikipediaから抽出した徳川家の家系図の実データを用いたテスト：");
		//データ呼び出し
		ArrayList<ArrayList<Integer>> list_adjlist = getData();
		ArrayList<String> list_name = getData2();
		//
		ArrayList<Integer> list_res = search(list_adjlist, 0, list_adjlist.size());
		ArrayList<Integer> list_res2 = new ArrayList<Integer>();
		//将軍
		HashSet<String> hset1 = new HashSet<String>(Arrays.asList("徳川家康","徳川秀忠","徳川家光","徳川家綱","徳川綱吉","徳川家宣","徳川家継","徳川吉宗","徳川家重","徳川家治","徳川家斉","徳川家慶","徳川家定","徳川家茂","徳川慶喜"));
		//
		for(int a1 = 0; a1 < list_res.size(); a1++){
			String str_name = list_name.get(list_res.get(a1));
			if(hset1.contains(str_name)){
				list_res2.add(a1);
				System.out.println(str_name + ": " + a1 + "番目");
			}
		}
		//
		ArrayList<Integer> list_ans1 = new ArrayList<Integer>(Arrays.asList(0, 121, 228, 258, 291, 293, 295, 331, 333, 355, 377, 378, 384, 386, 392));
		//System.out.println(list_res2 + " " + list_ans1);
		System.out.println(list_res2.equals(list_ans1));
		
	}
	
	
	
	//csvファイルの実データ呼び出し
	private static ArrayList<ArrayList<Integer>> getData() {
		//
		ArrayList<ArrayList<Integer>> list_data = new ArrayList<ArrayList<Integer>>();
		//
		BufferedReader br = null;
		try {
  			File file = new File(str_datafile);
			br = new BufferedReader(new FileReader(file));
 			String line;
			String[] ar1;
			ArrayList<Integer> list1;
 			while ((line = br.readLine()) != null) {
				ar1 = line.split(",");
				//
				list1 = new ArrayList<Integer>();
				for(int a1 = 0; a1 < ar1.length; a1++){
					int a2 = Integer.parseInt(ar1[a1]);
					if(a2 == -1){
						break;
					}
					list1.add(a2);
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
	
	private static ArrayList<String> getData2() {
		//
		ArrayList<String> list_data = new ArrayList<String>();
		//
		BufferedReader br = null;
		try {
  			File file = new File(str_datafile2);
			br = new BufferedReader(new FileReader(file));
 			String line;
			while ((line = br.readLine()) != null) {
				list_data.add(line);
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
	
	
	private static ArrayList<Double> getData3() {
		//
		ArrayList<Double> list_data = new ArrayList<Double>();
		//
		BufferedReader br = null;
		try {
  			File file = new File(str_datafile3);
			br = new BufferedReader(new FileReader(file));
 			String line;
			String[] ar1;
			while ((line = br.readLine()) != null) {
				
				//System.out.println(line);
				
				ar1 = line.split(",");
				//
				for(int a1 = 0; a1 < ar1.length; a1++){
					double a2 = Double.parseDouble(ar1[a1]);
					list_data.add(a2);
					
					
					//System.out.println(a2 + " ");
				}
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
	
	
	
	//グラフ描画用
	//ウインドウ
	int g_WinWidth = 1000;
	int g_WinHeight = 700;
	Graphics BufGrph;
	Image BufImg;
	//グラフ描画用クラス
	static MyChart Chart1;
	
	//
	private void drawBreadthFirstSearch(){
		//
		//データ呼び出し
		ArrayList<ArrayList<Integer>> list_adjlist = getData();
		ArrayList<String> list_name = getData2();
		ArrayList<Double> list_pos = getData3();
		
		//
		ArrayList<Integer> list_edge = new ArrayList<Integer>();
		
		//
		int vtxnum = list_adjlist.size();
		DoublePos[] ar_VtxDPos = new DoublePos[vtxnum];
		double tmpmaxX = -100, tmpmaxY = -100, tmpminX = 100, tmpminY = 100;
		for(int a1 = 0; a1 < vtxnum; a1++){
			//頂点データ
			ar_VtxDPos[a1] = new DoublePos(list_pos.get(2*a1), list_pos.get(2*a1+1));
			//System.out.println(a1 + " " + ar_VtxDPos[a1].PosX + " " + ar_VtxDPos[a1].PosY );
			
			//枝データ
			for(Integer a2: list_adjlist.get(a1)){
				list_edge.add(a1);
				list_edge.add(a2);
				//System.out.println(a1 + " " + a2);
			}
			
			
			//最大値を求める
			if(tmpmaxY < ar_VtxDPos[a1].PosY){
				tmpmaxY = ar_VtxDPos[a1].PosY;
			}
			if(tmpmaxX < ar_VtxDPos[a1].PosX){
				tmpmaxX = ar_VtxDPos[a1].PosX;
			}
			//最小値を求める
			if(tmpminY > ar_VtxDPos[a1].PosY){
				tmpminY = ar_VtxDPos[a1].PosY;
			}
			if(tmpminX > ar_VtxDPos[a1].PosX){
				tmpminX = ar_VtxDPos[a1].PosX;
			}
		}
		//
		Chart1.setTopX(tmpmaxX*1);
		Chart1.setTopY(tmpmaxY*1);
		Chart1.setBaseX(tmpminX*1);
		Chart1.setBaseY(tmpminY*1);
		
		//将軍一覧
		HashSet<String> hset1 = new HashSet<String>(Arrays.asList("徳川家康","徳川秀忠","徳川家光","徳川家綱","徳川綱吉","徳川家宣","徳川家継","徳川吉宗","徳川家重","徳川家治","徳川家斉","徳川家慶","徳川家定","徳川家茂","徳川慶喜"));
		//名前描画
		Color ColStr = new Color(200, 200, 200);
		for(int a1 = 0; a1 < ar_VtxDPos.length; a1++){
			String str_name = list_name.get(a1);
			if(hset1.contains(str_name) == false){
				continue;
			}
			DoublePos VtxDPos = new DoublePos(ar_VtxDPos[a1].PosX, ar_VtxDPos[a1].PosY);
			Chart1.drawString(ColStr, str_name.substring(2), VtxDPos, 10);
		}
		
		//枝描画
		Chart1.drawEdgeBatch(new Color(150, 150, 150), ar_VtxDPos, list_edge);
		
		//頂点描画
		Color ColVtx;
		int size = 7;
		for(int a1 = 0; a1 < ar_VtxDPos.length; a1++){
			String str_name = list_name.get(a1);
			if(hset1.contains(str_name)){
				ColVtx = new Color(255, 150, 150);
				size = 10;
			}else{
				ColVtx = new Color(200, 255, 200);
				size = 6;
			}
			DoublePos VtxDPos = new DoublePos(ar_VtxDPos[a1].PosX, ar_VtxDPos[a1].PosY);
			Chart1.drawVertex(ColVtx, VtxDPos, size, true, true);
		}
		
		//
		repaint();
	}
	
	public DepthFirstSearch(){
		//
		this.setSize(g_WinWidth, g_WinHeight);
		this.setVisible(true);
		//
		BufImg = createImage(getSize().width, getSize().height);
		BufGrph = BufImg.getGraphics();
		
		//グラフ作成
		Chart1 = new MyChart(
			BufGrph, BufImg, 
			g_WinWidth, g_WinHeight, 
			50, 0, 1050, 550, 
			20, 20, 50, 20);
		
		
		//描画本体呼び出し
		drawBreadthFirstSearch();
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
	
	public void setBaseX(double n_BaseX){
		BaseX = n_BaseX;
	}
	
	public void setBaseY(double n_BaseY){
		BaseY = n_BaseY;
	}
	
	public void setTopX(double n_TopX){
		TopX = n_TopX;
	}
	
	public void setTopY(double n_TopY){
		TopY = n_TopY;
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
	public void drawEdgeBatch(Color ColEdge, DoublePos[] ar_VtxDPos, int[] ar_edge){
		//
		BufGrph.setColor(ColEdge);
		//
		DoublePos DPos1, DPos2;
		IntPos Pos1, Pos2;
		for(int a1 = 0; a1 < ar_edge.length-1; a1+=2){
			//枝を構成する点
			int v1 = ar_edge[2*a1];
			int v2 = ar_edge[2*a1+1];
			//
			DPos1 = new DoublePos(ar_VtxDPos[v1].PosX, ar_VtxDPos[v1].PosY);
			Pos1 = convertPos(DPos1);
			DPos2 = new DoublePos(ar_VtxDPos[v2].PosX, ar_VtxDPos[v2].PosY);
			Pos2 = convertPos(DPos2);
			//
			BufGrph.drawLine(Pos1.PosX, Pos1.PosY, Pos2.PosX, Pos2.PosY);
			
		}
		
	}
	
	//
	public void drawEdgeBatch(Color ColEdge, DoublePos[] ar_VtxDPos, ArrayList<Integer> list_edge){
		//
		BufGrph.setColor(ColEdge);
		//
		DoublePos DPos1, DPos2;
		IntPos Pos1, Pos2;
		for(int a1 = 0; a1 < list_edge.size(); a1+=2){
			//枝を構成する点
			int v1 = list_edge.get(a1);
			int v2 = list_edge.get(a1+1);
			//
			DPos1 = new DoublePos(ar_VtxDPos[v1].PosX, ar_VtxDPos[v1].PosY);
			Pos1 = convertPos(DPos1);
			DPos2 = new DoublePos(ar_VtxDPos[v2].PosX, ar_VtxDPos[v2].PosY);
			Pos2 = convertPos(DPos2);
			//
			BufGrph.drawLine(Pos1.PosX, Pos1.PosY, Pos2.PosX, Pos2.PosY);
			
		}
		
	}
	
	//
	public void drawVertexBatch(Color ColVertex, DoublePos[] ar_VtxDPos, int size, boolean f_Circle, boolean f_fill){
		//
		BufGrph.setColor(ColVertex);
		//
		DoublePos DPos1;
		IntPos Pos1;
		for(int a1 = 0; a1 < ar_VtxDPos.length; a1++){
			DPos1 = new DoublePos(ar_VtxDPos[a1].PosX, ar_VtxDPos[a1].PosY);
			Pos1 = convertPos(DPos1);
			//点描画
			int dx1 = Pos1.PosX-size/2;
			int dy1 = Pos1.PosY-size/2;
			if(f_Circle){
				if(f_fill){
					BufGrph.fillOval(dx1, dy1, size, size);
				}else{
					BufGrph.drawOval(dx1, dy1, size, size);
				}
			}else{
				if(f_fill){
					BufGrph.fillRect(dx1, dy1, size, size);
				}else{
					BufGrph.drawRect(dx1, dy1, size, size);
				}
			}
		}
		
	}
	
	//
	public void drawVertex(Color ColVertex, DoublePos VtxDPos, int size, boolean f_Circle, boolean f_fill){
		//
		BufGrph.setColor(ColVertex);
		//
		DoublePos DPos1 = new DoublePos(VtxDPos.PosX, VtxDPos.PosY);
		IntPos Pos1 = convertPos(DPos1);
		//点描画
		int dx1 = Pos1.PosX-size/2;
		int dy1 = Pos1.PosY-size/2;
		if(f_Circle){
			if(f_fill){
				BufGrph.fillOval(dx1, dy1, size, size);
			}else{
				BufGrph.drawOval(dx1, dy1, size, size);
			}
		}else{
			if(f_fill){
				BufGrph.fillRect(dx1, dy1, size, size);
			}else{
				BufGrph.drawRect(dx1, dy1, size, size);
			}
		}
	}
	
	//
	public void drawString(Color ColStr, String str1, DoublePos DPos1, int fontsize){
		//
		Font currentFont = BufGrph.getFont();
		Font newFont = currentFont.deriveFont(fontsize);
		BufGrph.setFont(newFont);
		//
		IntPos Pos1 = convertPos(DPos1);
		//
		BufGrph.drawString(str1, Pos1.PosX, Pos1.PosY);
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
	public void drawChartAxis(Color ColAxis, Color ColSub, String str_LabelX, String str_LabelY, double StepX, double StepY, double LabelStepX, double LabelStepY){
		//軸平行線
		BufGrph.setColor(ColSub);
		//Y軸目盛（X軸平行線）
		DoublePos DPosAX1;
		DoublePos DPosAX2;
		IntPos PosAX1;
		IntPos PosAX2;
		for(double a1 = BaseY+StepY; a1 < TopY; a1+= StepY){
			DPosAX1 = new DoublePos(BaseX, a1);
			DPosAX2 = new DoublePos(TopX, a1);
			PosAX1 = convertPos(DPosAX1);
			PosAX2 = convertPos(DPosAX2);
			BufGrph.drawLine(PosAX1.PosX, PosAX1.PosY, PosAX2.PosX, PosAX2.PosY);
		}
		//X軸目盛（Y軸平行線）
		for(double a1 = (int)BaseX+StepX; a1 < TopX; a1+= StepX){
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
		BufGrph.drawString(str_LabelX, PosAXL.PosX, PosAXL.PosY-8);
		//Y軸ラベル
		DoublePos DPosAYL = new DoublePos(BaseX, TopY);
		IntPos PosAYL = convertPos(DPosAYL);
		BufGrph.drawString(str_LabelY, PosAYL.PosX-40, PosAYL.PosY-10);
		//X軸目盛りラベル
		DoublePos DPosOXL;
		IntPos PosOXL;
		for(double a1 = BaseX; a1 < TopX; a1+= LabelStepX){
			DPosOXL = new DoublePos(a1, BaseY);
			PosOXL = convertPos(DPosOXL);
			BufGrph.drawString(a1 + "", PosOXL.PosX-5, PosOXL.PosY+15);
		}
		//Y軸目盛りラベル
		DoublePos DPosOYL;
		IntPos PosOYL;
		//最も長いラベル（最大値）を探す：雑
		int diff = 0;
		for(double a1 = BaseY; a1 < TopY; a1+= LabelStepY){
			String str1 = String.valueOf(a1);
			int a2 = (int)((double)str1.length() * 6.6);
			if(diff < a2){
				diff = a2;
			}
		}
		
		//System.out.println(diff + "<<" + str1.length() + " " + str1);
		for(double a1 = BaseY; a1 < TopY; a1+= LabelStepY){
			DPosOYL = new DoublePos(BaseX, a1);
			PosOYL = convertPos(DPosOYL);
			BufGrph.drawString(a1 + "", PosOYL.PosX-diff, PosOYL.PosY);
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
			
			//System.out.println(Pos1.PosX + " " + Pos1.PosY);
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


