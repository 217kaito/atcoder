/*
凸多角形の最小重み三角化の２回目です。
厳密な最適解を求めるための動的計画法アルゴリズムを実装
します。今回は、動的計画法の表を作成するところまでを実装します。作成し
た表から、最適な三角化の重みを読み取ることができます。
前回のTriangulationGreedy クラスをコピーして、TriangulationDP に改名し、貪
欲法と動的計画法の両方を実行できるようにしてください。

課題は以下の通りです。
1. 「DP」ボタンを追加し、このボタンを押したときには動的計画法によっ
て、部分問題の最適値の表（int[][] opt) と、最適な分割の情報を表す
表 (int[][] bestDivider) を作成するようにする。また、messageLabel に
optimal weight = *** という形式で、求まった最適値を表示するように
する。さらに、Hints.java の printTables メソッドにより、辺の重みと
作成した表の内容をコンソールに出力する。（printTables メソッドは、
テキストとしてコピー・貼り付けしてください。）
2. 次項の実行結果解析に使う多角形データを個人ごとに固定するために、
TriangulationGreedy から引き継いだ変数 INITIAL SEED の値を自分
の組番号と学生番号をつなげたものに設定する。例えば、14 組 71 番な
らば 1471 とする。
3. プログラムの実行開始の後、すぐに頂点数を６に設定し、第一回目の実
行データに対する動的計画法の実行結果を次の容量で解析する。コン
ソールに出力された表をコピーし、提出プログラムの冒頭にコメント
として貼り付ける。その上で、その表の各欄の値がなぜその値になるか
を実際の数値に即し、かつ動的計画法アルゴリズムに基づいて自分の
言葉で説明する。　どの欄について詳しく説明し、どの欄について「同
様に」というような省略をするかの判断も採点の対象となる。
*/

package task13;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/*
対角線の重み
       i  | j           0       1       2       3       4       5
       0                              621     683     933
       1                                      616    1145    1167
       2                                              820     866
       3                                                      737
最適値の表
       i  | j           0       1       2       3       4       5
       0                        0       0     616    1299    2036
       1                                0       0     616    1353
       2                                        0       0     737
       3                                                0       0
最適分割頂点の表
       i  | j           0       1       2       3       4       5
       0                                1       1       3       3
       1                                        2       3       3
       2                                                3       3
       3                                                        4
*/

//* 対角線の重みについて
//* |i-j|<=1 の組み合わせとi=0,j=5の欄については同一の点または元の凸多角形の辺であり、
//* 対角線ではないので記載されない
//* そのほかの欄については対角線の重みがそのまま記載される

//* 最適値の表について
//* 1<=|i-j|<=2 の組み合わせについては二角形、または三角形であるので定義より最適値は0となる
//* |i-j|=3 の組み合わせについては四角形であるので2本の対角線のうち、
//* 重みの小さいほうの値が最適値となる
//* 例えばi=0,j=3の欄は頂点0,2の対角線の重み621と頂点1,3の対角線の重み616の小さいほう
//* である616が最適値となっている
//* |i-j|=4 の組み合わせについては五角形であるので三角化の条件に合う2本の対角線の組み合わせが
//* 5通りあり、その中で2本の対角線の重みの合計値が最も小さい組み合わせが最適値となる
//* 例えばi=0,j=4の欄は頂点0,3の対角線の重み683と頂点1,3の対角線の重み616の合計値1299が
//* 5通りの組み合わせのそれぞれ重さの合計値の中で最も小さいため最適値となっている
//* |i-j|=5　の欄については|i-j|=4の2つ組み合わせの欄の値と、そのそれぞれの場合で
//* 三角化の条件に合う対角線の重みを足した値のうち最も小さいものを最適値とする
//* i=0,j=5の欄はi=0,j=4の欄の値1299とi=1,j=5の欄の値1353と、そのそれぞれの場合で
//* 三角化の条件に合う対角線の重みを足した値のうち最も小さい2036を最適解とする

//* 最適分割頂点の表について
//* |i-j|<=1 の組み合わせについては点、または二角形であるので記載されない
//* |i-j|=2 の組み合わせについは三角形であるのでi,j以外の頂点が記載される
//* |i-j|>=3 の組み合わせについては、例えばi=0,i=3の欄では凸多角形p[0],p[1],p[2],p[3]
//* の最少重み三角化の条件に合う対角線を引ける頂点が最適分割頂点となる
//* そして、その対角線は頂点1,3を両端点とするものであるから、頂点3が最適分割頂点となる
//* ほかの場合についても同様である

public class TriangulationDP extends Frame
implements ActionListener {
  // フレームの幅と高さ
  private static final int WIDTH = 900;
  private static final int HEIGHT = 700;

  // デフォールトの頂点数
  private static final int NP_DEFAULT = 10;

  // 乱数の初期シード
  private static final int INITIAL_SEED = 1456;

  // 描画領域の余白
  private static final int LEFT_MARGIN = 100;
  private static final int RIGHT_MARGIN = 100;
  private static final int TOP_MARGIN = 150; // ボタン配置のための余裕
  private static final int BOTTOM_MARGIN = 50;
  private static final int NET_WIDTH = WIDTH - LEFT_MARGIN - RIGHT_MARGIN;
  private static final int NET_HEIGHT = HEIGHT - TOP_MARGIN - BOTTOM_MARGIN;

  // 点描画のための円盤の半径
  private static final int RADIUS = 3;

  // 表示座標の縮尺
  private static final int SCALE = 2;

  // 	多角形頂点の個数
  private int np;

  // 頂点の配列
  private Point[] point;

  // 対角線の本数
  private int nd;

  // 対角線の配列
  private Diagonal[] diagonal;

  // 点数選択欄
  private Choice chooseN;

  // メッセージ用ラベル
  private Label messageLabel;

  // 乱数生成器
  private Random random;
  
  // 部分問題の最適値の表を表す2次元配列を参照する
  private int[][] opt;
  
  // 部分問題の最適分割頂点の表を表す2次元配列を参照する。
  private int[][] bestDivider;

  public TriangulationDP() {
    // このフレームの大きさを設定する。
    setSize(WIDTH, HEIGHT);

    // 配置の方式をFlowLayout に設定
    setLayout(new FlowLayout());

    // 数入力
    Label label1 = new Label("Polygon size:");
    add(label1);

    chooseN = new Choice();
    chooseN.addItem("4");
    chooseN.addItem("5");
    chooseN.addItem("6");
    chooseN.addItem("7");
    chooseN.addItem("8");
    chooseN.addItem("9");
    chooseN.addItem("10");
    chooseN.addItem("15");
    chooseN.addItem("20");
    chooseN.addItem("30");
    chooseN.addItem("70");
    chooseN.addItem("100");
    add(chooseN);

    // 多角形生成
    Button generatePolygon = new Button("Generate Polygon");
    add(generatePolygon);
    generatePolygon.addActionListener(this);

    // 貪欲法三角化
    Button greedyButton = new Button("Greedy");
    add(greedyButton);
    greedyButton.addActionListener(this);
    
    //* 動的計画法三角化
    Button dpButton = new Button("DP");
    add(dpButton);
    dpButton.addActionListener(this);

    // クリアボタン
    Button clearButton = new Button("Clear");
    add(clearButton);
    clearButton.addActionListener(this);

    // 終了ボタン
    Button closeButton = new Button("Close");
    add(closeButton);
    closeButton.addActionListener(this);

    // メッセージ用のラベル
    messageLabel = new Label(
        "message is displayed here .........");
    add(messageLabel);

    // フレームを可視化する
    setVisible(true);

    // 乱数生成器
    // デバッグのため、初期シードを指定
    random = new Random(INITIAL_SEED);
  }

  // フレームの描画が必要になったときに呼ばれる。
  // 多角形や、対角線の描画をここで行う。
  public void paint(Graphics g) {
    // 点の描画。
    for (int i = 0; i < np; i++) {
      drawPoint(i, g);
    }

    // 辺の描画
    for (int i = 0; i < np; i++) {
      drawEdge(i, (i + 1) % np, g);
    }

    // 対角線の描画
    if (diagonal != null) {
      for (int i = 0; i < nd; i++)
        if (diagonal[i] != null) {
          drawEdge(diagonal[i].i1, diagonal[i].i2, g);
        }
    }
  }

  private void drawPoint(int i, Graphics g) {
    if (i == 0) {
      g.setColor(Color.green);
    }
    else {
      g.setColor(Color.blue);
    }
    g.fillOval(point[i].xInFrame() - RADIUS,
        point[i].yInFrame() - RADIUS,
        RADIUS * 2, RADIUS * 2);
  }

  private void drawEdge(int i, int j,
      Graphics g) {
    g.setColor(Color.red);
    g.drawLine(
        point[i].xInFrame(),
        point[i].yInFrame(),
        point[j].xInFrame(),
        point[j].yInFrame()
        );
  }

  public void actionPerformed(ActionEvent ae) {
    String commandName = ae.getActionCommand();
    if (commandName.equals("Generate Polygon")) {
      // 三角化のクリア
      clear();
      // 多角形の生成
      generate();
    }

    else if (commandName.equals("Greedy")) {
      // 三角化のクリア
      clear();
      // 貪欲法三角化アルゴリズムの呼び出し
      greedy();
    }

    else if(commandName.equals("DP")) {
    	//* 三角化のクリア
    	clear();
    	//* 動的計画法三角化アルゴリズムの呼び出し
    	dp();
    }
    else if (commandName.equals("Clear")) {
      // 三角化のクリア
      clear();
    }

    else if (commandName.equals("Close")) {
      System.exit(0);
    }

    // フレームの再描画を要求する。
    repaint();
  }

  // 凸多角形の生成（余裕のある人以外は読まなくてよい）
  private void generate() {
    String polygonSizeString = chooseN.getSelectedItem();

    try {
      np = Integer.parseInt(polygonSizeString);

    }
    catch (Exception e) {
      // 頂点数入力欄が空欄などの場合：頂点数のデフォールト
      np = NP_DEFAULT;
    }

    int xMin = 0;
    int xMax = NET_WIDTH * SCALE;
    int yMin = 0;
    int yMax = NET_HEIGHT * SCALE;

    point = new Point[np];

    point[0] = new Point(5 * xMax / 6, yMax / 3);
    point[1] = new Point(xMax / 2, 5 * yMax / 6);
    point[2] = new Point(xMax / 6, yMax / 3);

    int k = 3;

    ArrayList<Point> candidates = new ArrayList<Point>();

    for (int x = xMin; x <= xMax; x++) {
      for (int y = yMin; y <= yMax; y++) {
        candidates.add(new Point(x, y));
      }
    }

    while (k < np) {
      ArrayList<Point> candidates1 = new ArrayList<Point>();
      for (Point p: candidates) {
        if (p.isInsertable(point, k)) {
          candidates1.add(p);
        }
      }
      candidates = candidates1;
      if (candidates.isEmpty()) {
        np = k;
        System.out.println("Cannot extend keeping convexity, np = " + np);
        break;
      }
      int r = random.nextInt(candidates.size());
      Point p = candidates.get(r);
      p.insertInto(point, k);
      k++;
    }
  }

  private void clear() {
    diagonal = null;
    messageLabel.setText("");
  }

  // 貪欲法による三角化
  // 凸多角形の頂点数は大域変数 np で、
  // 頂点の列（反時計回り）は 大域配列 point で与えられる
  
  //* 対角線の重さの小さい順に並べて、その順番で
  //* これまでに採用された度の対角線とも交差しないならば採用する
  //* という動作を繰り返す
  private void greedy() {
    // すべての対角線の本数naを求める
    /** 右辺を正しい式に直してください。コメントによる説明が必要です。 **/

	 //* 一つの頂点から引ける対角線の本数は自らと、隣り合う頂点を引いた(np-3)本
	 //* すべての頂点から(np-3)本の対角線が引けるのでn*(np-3)本
	 //* この考え方では一つの対角線につき2回数えてしまっているので2で割ると
	 //* すべての対角線の本数naは以下のように求まる
    int na = np*(np-3)/2;

    // すべての対角線を入れる配列を用意する
    Diagonal[] all = new Diagonal[na];

    // すべての対角線を生成して配列に格納する
    // ２重の for 文になる。
    // Diagonal クラスを見て、コンストラクタの呼び出し方を確認する必要がある
    // 特に、Diagonal オブジェクトは、両端点の頂点をその番号（point 配列中の添え字）によって
    // 憶えることに注意する。
    // 隣接した点を結ぶ辺は対角線ではないことに注意する。
    // 特に、0 番の頂点と np - 1 番の頂点も隣接していることに注意！

    /** 上記の仕事をするコードを書いてください。**/
    /** コードの各部に自分の言葉による説明が必要です。**/
    
    //* 格納した対角線の本数を数える変数
    int c = 0;
    
    //* 同じ対角線を何回も配列に格納することを防ぐために変数i,jの範囲を設定する
    for(int i = 0;i < np-2;i++)
    	for(int j = i+2;j < np;j++) {
    		//* 例外的な辺である両端点が0番の頂点とnp-1番の頂点の辺を対角線として格納しないために
    		//* iが0ではない、またはjがnp-1でない場合に対角線の格納を行う
    		if(i != 0||j != np-1)
    			//* 両端点がi番の頂点とj番の頂点の辺を対角線として配列に格納する
    			all[c++] = new Diagonal(i,j);
    	}

    // 配列allの要素がすべてセットされたかをチェックし、
    // もし、nullの要素があれば、例外を発生する
    for (int i = 0; i < na; i++) {
      if(all[i] == null) {
        throw new RuntimeException("配列allの第" + i + "要素がnullのままです。");
      }
    }

    // 対角線を軽い順に整列する
    Arrays.sort(all, new WeightComparator());
  
    // 三角化で採用する対角線の本数
    /** 右辺を正しい式に直してください。説明が必要です。**/
    
   //* 一つの頂点から引ける対角線の本数が三角化の条件を満たす対角線の本数である
    nd = np-3;

    // 採用した対角線を入れる配列

    diagonal = new Diagonal[nd];

    // 貪欲法の本体
    /** diagonal 配列の中に採用した対角線をしまっていく貪欲法を実装してください**/
    /** 対角線が採用可能かを判定するために、DiagonalクラスのcrossedNoneメソッドを利用できます**/
    
    //* 採用した対角線の本数を数える変数
    int count = 0;
    
    for(int i = 0;i < na;i++) {
    	//* all[i]が採用可能ならば配列diagonalに格納する
    	if(all[i].crossesNone(diagonal,count))
    		diagonal[count++] = all[i];
    	//* 採用した対角線の本数が三角化で採用する対角線の本数になったら
    	//* ループから抜け出す
    	if(count == nd)
    		break;
    }

    // 求まった重みを表示
    int weight = 0; // 仮に重みの合計は 0としておく

    /** 重み和を求める コードを書いてください。説明が必要です。**/
    
    //* 採用した対角線の重みを順番に加算していく
    for(int i = 0;i < nd;i++)
    	weight += diagonal[i].weight;

    messageLabel.setText("greedy weight = " + weight);
  }

  // 頂点iと頂点j を結ぶ対角線の重み
  //（長さの切捨て)
  private int weight(int i, int j) {
	    int dx = point[i].x - point[j].x;
	    int dy = point[i].y - point[j].y;
	    return (int) Math.sqrt(dx * dx + dy * dy);
	  }
  
//* kを分割頂点に指定したときの最適値を求めるメソッド
  private int optimum(int i,int j,int k) {
	  //* 頂点i,kを両端点とする線分、頂点k,jを両端点とする線分が
	  //* それぞれ元の凸多角形の辺でない時に以下で計算に用いる
	  //* 変数weight1(頂点i,kを両端点とする対角線の重み),
	  //* 変数weight2(頂点k,jを両端点とする対角線の重み)
	  int weight1 = weight(i,k);
	  int weight2 = weight(k,j);
	  
	  if(k>i+1&&j>k+1) {
		  return opt[i][k] + opt[k][j] + weight1 + weight2;
	  }
	  //* 頂点i,kを両端点とする線分のみが元の凸多角形の辺であるとき
	  else if(k==i+1&&j>k+1){
		  return opt[i][k] + opt[k][j] + 0 + weight2;
	  }
	  //* 頂点k,jを両端点とする線分のみが元の凸多角形の辺であるとき
	  else if(k>i+1&&j==k+1) {
		  return opt[i][k] + opt[k][j] + weight1 + 0;
	  }
	  //* 頂点i,kを両端点とする線分と頂点k,jを両端点とする線分の両方が元の凸多角形の辺であるとき
	  else if(k==i+1&&j==k+1) {
		  return opt[i][k] + opt[k][j] + 0 + 0;
	  }
	  else {
		  return 0;
	  }
  }
	  

  //* 動的計画法による最少重み三角化
  private void dp() {
	  // まず最適値の表を作成する。
	  opt = new int[np][np];
	  bestDivider = new int[np][np];
	
	  // opt[i][j] （0 <= i < j < np) は、頂点i, i + 1, ..., j
      // からなる凸多角形の最小重み三角化の重みを表す。
      // 三角化の重みは、使用した対角線の重みの和として定義される。
      // j = i + 2 （三角形）のときは定義よりopt[i][j] = 0である。さらに、
      // j = i + 1 （二角形）のときも、opt[i][j] = 0とする。
      // j >= i + 2のとき、頂点i, i + 1, ..., jからなる 凸多角形の三角化において、
      // 辺(i, j) を一辺とする三角形のもうひとつの頂点を、
      // その三角化の「分割頂点」と呼ぶ。
      // bestDivider[i][j] は、頂点i, i + 1, ..., jからなる 凸多角形の
      // 最小重み三角化の分割頂点を表す。

      /** i = 0, ..., np-2に対してopt[i][i + 1] の表を埋めるコードを
                      作成してください**/                
      /** コードに即した自分の言葉によるコメントが必要です。**/
	  for(int i = 0;i<np-1;i++) {
		  //* 二角形の場合は定義よりopt[i][j] = 0とする
		  opt[i][i+1] = 0;
	  }

      // d = j - i =2, 3, ... の順に表を埋める
      for (int d = 2; d < np; d++) {
          for (int i = 0; i < np - d; i++) {
              int j = i + d;

              // k = i + 1, ..., j - 1 に対して
              // k を分割頂点に指定したときの最適値を次々に求め
              // その中での最小値をopt[i][j]、
              // またその最小値を与えるkをbestDivider[i][j]に記録する。
              // kを分割頂点に指定したときの最適値を求めるために、適切な補助メソッドを
              // 定義して使用すると良い

              /** 上のコメントに基づいてコードを作成してください。**/
              /** コードに即した自分の言葉によるコメントが必要です。**/
              
              //* まずopt[i][j],bestDivider[i][j]にそれぞれk=i+1の場合、kを分割頂点に指定したときの
              //* 最適値,分割頂点を入れておく
              opt[i][j] = optimum(i,j,i+1);
              bestDivider[i][j] = i+1;
              
              for(int k = i+2;k<j;k++) {
            	  //* 変数optimum_valueにkを分割頂点に指定したときの最適値を代入する
            	  int optimum_value = optimum(i,j,k);
            	  //* kを分割頂点に指定したときの最適値がopt[i][j]の値よりも小さい場合、
            	  //* 最小値と最少重み三角化の分割頂点を更新する
            	  if(optimum_value<opt[i][j]) {
            		  opt[i][j] = optimum_value;
            		  bestDivider[i][j] = k;
            	  }
              }
              //* 求まった最適値を表示
              messageLabel.setText("optimal weight = " + opt[i][j]);
          }
      }
      printTables();
  }
  
  // 重みの表と動的計画法の表のプリント
  private void printTables(){
      int w = 8;
      System.out.println("対角線の重み");
      System.out.print("       i  | j    ");
      for (int j = 0; j < np; j++) {
          printWithWidth(j, w);
      }
      System.out.println();
      for (int i = 0; i < np - 2; i++) {
          printWithWidth(i, w);
          printSpaces((i + 2) * w + 9);
          for (int j = i + 2; j < np; j++) {
            if (i != 0 || j != np - 1) {
              printWithWidth(weight(i, j), w);
            }
          }
          System.out.println();
      }

      System.out.println("最適値の表");
      System.out.print("       i  | j    ");
      for (int j = 0; j < np; j++) {
          printWithWidth(j, w);
      }
      System.out.println();
      for (int i = 0; i < np - 2; i++) {
          printWithWidth(i, w);
          printSpaces((i + 1) * w + 9);
          for (int j = i + 1; j < np; j++) {
            printWithWidth(opt[i][j], w);
          }
          System.out.println();
      }
      System.out.println("最適分割頂点の表");
      System.out.print("       i  | j    ");
      for (int j = 0; j < np; j++) {
          printWithWidth(j, w);
      }
      System.out.println();
      for (int i = 0; i < np - 2; i++) {
          printWithWidth(i, w);
          printSpaces((i + 2) * w + 9);
          for (int j = i + 2; j < np; j++) {
            printWithWidth(bestDivider[i][j], w);
          }
          System.out.println();
      }

  }
  
  // 空白をw字分プリントする。w <= 0 ならば何もしない。
  private void printSpaces(int w) {
      for (int i = 0; i < w; i++)
          System.out.print(" ");
  }

  // 整数nをプリントする。
  // 直前のスペースの個数で、可能ならば幅がwになるように調節する。
  private void printWithWidth(int n, int w) {
      String s = Integer.toString(n);
      printSpaces(w - s.length());
      System.out.print(s);
  }

  // ２次元平面上の点を表すクラス
  private class Point {
    // 論理的な座標（フレームで表示される座標とは
    // 縮尺、上下反転などを通して対応）
    int x;
    int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    // フレーム上の表示場所の x 座標
    private int xInFrame() {
      return x / SCALE + LEFT_MARGIN;
    }

    // フレーム上の表示場所の y 座標
    // 座標 0 がフレーム下端になるように上下反転
    private int yInFrame() {
      return HEIGHT - BOTTOM_MARGIN - y /SCALE;
    }

    public String toString() {
      return "(" + x + "," + y + ")";
    }

    // 以下は凸多角形生成用
    // この点が、有向直線 p->q の左側にあるかを判定する。
    private boolean isLeft(Point p, Point q) {
      long x1 = q.x - p.x;
      long y1 = q.y - p.y;
      long x2 = x - p.x;
      long y2 = y - p.y;

      long a = x1 * y2 - x2 * y1;
      return a > 0;
    }

    // この点を凸多角形に加えて凸性が保たれるかを判定する。
    private boolean isInsertable(Point[] p, int k) {
      for (int i = 0; i < k; i++) {
        if (isInsertableAt(i, p, k)) return true;
      }
      return false;
    }

    private boolean isInsertableAt(int i, Point[] p, int k) {
      int i1 = (i + k - 1) % k;
      int i2 = (i + 1) % k;
      int i3 = (i + 2) % k;
      return isLeft(p[i1], p[i]) &&
          p[i2].isLeft(p[i], this) &&
          p[i3].isLeft(this, p[i2]);
    }

    // 凸性を保つ場所に挿入する
    private void insertInto(Point[] p, int k) {
      for (int i = 0; i < k; i++) {
        if (isInsertableAt(i, p, k)) {
          for (int j = k; j > i + 1; j--) {
            p[j] = p[j - 1];
          }
          p[i + 1] = this;
          return;
        }
      }
      throw new RuntimeException("Point that must be insertable is not indeed");
    }
  }

  // 対角線を表すクラス
  private class Diagonal {
    int i1, i2; // 端点の番号
    int weight; // この辺の重さ（長さを切り捨てによって整数にした値）

    private Diagonal(int i1, int i2) {
      this.i1 = i1;
      this.i2 = i2;
      weight = weight(i1, i2);
    }

    // 対角線thisが、対角線d[0], ..., d[k - 1]のどれとも交わらなければ
    // true, どれかと交わればfalseを返す。
    private boolean crossesNone(Diagonal[] d, int k) {
      /** 上の仕様を満たすためのコードを書いてください**/
      /** コードの各部に、自分の言葉による説明が必要です。**/
	
	    //* i=0,...,k-1に対してcrosses(d[i])がtrue、つまり対角線thisが対角線d[i]と交わるのならfalseを返す
		for(int i=0;i<k;i++)
			if(crosses(d[i])) {
				return false;
			}
		//* つまり対角線thisが対角線d[i]と交わらないのならtrueを返す
      return true;
    }

    // 対角線 d と交わるか？
    // 座標の計算は必要ない。
    // 凸多角形なので、頂点の番号の並びから判定できる。
    private boolean crosses(Diagonal d) {
      // 頂点 i1, d.i1, i2, d.i2 が反時計まわりに
      // この順であらわれるか、または
      // 頂点 i1, d.i2, i2, d.i1 が反時計まわりに
      // この順であらわれるならば、交差する。

      /** 上の仕様を満たすためのコードを書いてください
       ** コードの各部に、自分の言葉による説明が必要です。
       ** ヒント：上のふたつの場合のそれぞれに対して、
       ** 4つの番号のどれが最小であるかによって
       ** 答えがtrueとなる場合が４通りあることに注意する。
       **/
	 
	 //* まず、配列allへの対角線の格納方法よりthis.i1<this.i2、d.i1<d.12である
	 //* したがって対角線dと対角線thisが交わるのは
	 //* 頂点 i1 , d.i1 , i2 , d.i2 が反時計回りにこの順であるか、
	 //* 頂点 d.i1 , i1 , d.i2 , i2 が反時計回りにこの順であるときである
	 if(this.i1<d.i1&&d.i1<this.i2&&this.i2<d.i2)
		 return true;
	 else if(d.i1<this.i1&&this.i1<d.i2&&d.i2<this.i2)
		 return true;
	 else
      return false;
    }
  }

  private class WeightComparator implements Comparator<Diagonal> {
    @Override
    public int compare(Diagonal d, Diagonal e) {
      // 対角線の重みによる比較
      // dの重み < e の重みならば負の値、
      // dの重み > e の重みならば正の値、
      // dの重み = e の重みならば0を返す

      return d.weight - e.weight;
    }
  }

  public static void main(String[] args) {
    // TriangulationRecursive オブジェクトの生成
    new TriangulationDP();
  }
}
