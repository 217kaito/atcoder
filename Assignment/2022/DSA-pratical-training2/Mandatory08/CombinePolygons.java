/*
与えられた２つの凸多角形を上下の共通接線によって結合するアルゴリズムを実装します。
CommonTangentsで実装した、上側共通接線と下側共通接線を求めるアルゴリズムを使用します。
入力の３点が一直線上に乗ることはないと仮定してください。
課題は、クラス CombinePolygons のメソッド combinePolygons を実装する
ことです。
*/

package task08;
/*
 * CombinePolygons クラス：　凸包分割統治アルゴリズムの準備

 *
 * 左右２つの凸多角形を、上下の共通接線によって統合して、
 * ひとつの凸多角形にする。
 *
 * ２０２１年１１月１６日改訂
 * 
 */

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombinePolygons extends Frame implements ActionListener {
  // フレームの幅と高さ
  private static final int WIDTH = 900;
  private static final int HEIGHT = 700;

  // 描画領域の余白
  private static final int LEFT_MARGIN = 50;
  private static final int RIGHT_MARGIN = 50;
  private static final int TOP_MARGIN = 150; // ボタン配置のための余裕
  private static final int BOTTOM_MARGIN = 50;

  // 点描画のための円盤の半径
  private static final int RADIUS = 3;

  // 左の凸多角形の頂点配列（反時計回り）
  private Point[] left;

  // 左の凸多角形の頂点配列（反時計回り）
  private Point[] right;

  // 結合された凸多角形の頂点配列（反時計回り）
  private Point[] combined;

  // 左右の頂点数指定欄
  private TextField leftSizeInput;
  private TextField rightSizeInput;

  // テスト用の多角形の変形で用いる乱数
  private Random random;

  // 点の通し番号
  private int np;

  public CombinePolygons() {

    // このフレームの大きさを設定する。
    setSize(WIDTH, HEIGHT);

    // Point クラスの座標領域を設定する。
    Point.setBounds(
        new Rectangle(LEFT_MARGIN, TOP_MARGIN,
            WIDTH - LEFT_MARGIN - RIGHT_MARGIN,
            HEIGHT - TOP_MARGIN - BOTTOM_MARGIN));

    // 配置の方式をFlowLayout に設定
    setLayout(new FlowLayout());

    // 左右の多角形の点の数の選択欄生成
    Label label1 = new Label("左の頂点数:");
    add(label1);

    leftSizeInput = new TextField("1");
    add(leftSizeInput);

    Label label2 = new Label("右の頂点数:");
    add(label2);

    rightSizeInput = new TextField("1");
    add(rightSizeInput);

    // 初期化要求ボタン
    Button refreshButton = new Button("Refresh");
    add(refreshButton);
    refreshButton.addActionListener(this);

    // 左の凸多角形変形ボタン
    Button deformLeftButton = new Button("Deform Left");
    add(deformLeftButton);
    deformLeftButton.addActionListener(this);

    // 右の凸多角形変形ボタン
    Button deformRightButton = new Button("Deform Right");
    add(deformRightButton);
    deformRightButton.addActionListener(this);

    // 結合ボタン
    Button combineButton = new Button("Combine");
    add(combineButton);
    combineButton.addActionListener(this);

    // 終了ボタン
    Button closeButton = new Button("Close");
    add(closeButton);
    closeButton.addActionListener(this);

    setVisible(true);
  }

  // フレームの描画が必要になったときに呼ばれる。
  //凸多角形の頂点と辺の描画をここで行う。
  //左右の凸多角形は青で、
  //結合された凸多角形は赤で描く。
  public void paint(Graphics g) {
    if (left != null) {
      paintPolygon(left, g, Color.blue);
    }
    if (right != null) {
      paintPolygon(right, g, Color.blue);
    }

    g.setColor(Color.red);
    if (combined != null) {
      paintPolygon(combined, g, Color.red);
    }
  }
  


  // 凸多角形を描画する。
  private void paintPolygon(Point[] p, Graphics g, Color color) {
    // インデックスが0の点のマーク
    g.setColor(Color.green);
    int px = p[0].xInFrame();
    int py = p[0].yInFrame();
    g.fillOval(px - RADIUS * 2, py - RADIUS * 2, RADIUS * 4, RADIUS * 4);

    g.setColor(color);
    // 頂点の描画
    for (int i = 0; i < p.length; i++) {
      px = p[i].xInFrame();
      py = p[i].yInFrame();
      g.fillOval(px - RADIUS, py - RADIUS, RADIUS * 2, RADIUS * 2);
    }
    // 辺の描画
    for (int i = 0; i < p.length; i++) {
      int i1 = (i + 1) % p.length;
      px = p[i].xInFrame();
      py = p[i].yInFrame();
      int px1 = p[(i + 1) % p.length].xInFrame();
      int py1 = p[(i + 1) % p.length].yInFrame();
      g.drawLine(px, py, px1, py1);
    }
  }
  public static int[] findUpperTangent(Point[] left, Point[] right) {

	    // 左の多角形の最右の頂点に注目し、そのインデックスを i とする。
	    // メソッド rightmostPointIndex は、現在常に 0 を返すように
	    // なっているので、完成する必要がある。

	    int i = rightmostPointIndex(left); 

	    // 右の多角形の最左の頂点に注目し、そのインデックスを j とする。
	    // メソッド leftmostPointIndex も完成する必要がある。

	    int j = leftmostPointIndex(right); 

	    while (true) {
	      // i1 によって、左の凸多角形においてインデックスが i の頂点の反時計回りで次の点の
	      // インデックスを表す。
	      
	      // 元のコードでは、変数iの値が配列leftのインデックスの最大値と一致するときにエラーが生じてしまうので
	      // それを解消するためにその時を場合分けする必要がある。
	      // 具体的にはその時には変数i1に0を代入する。
	      int i1= 0;
	    	
	      if(i!=left.length-1)
	    	  i1 = i + 1;
	      else
	    	  i1 = 0;

	      // j1 によって、右の凸多角形においてインデックスが j の頂点の時計回りで次の点の
	      // インデックスを表す。
	     
	      // 元のコードでは、変数jの値が配列pのインデックスの最省値と一致するときにエラーが生じてしまうので
	  	  // それを解消するためにその時を場合分けする必要がある。
	  	  // 具体的にはその時には変数j1に配列rightのインデックスの最大値を代入する。
	      int j1 = 0;
	      
	      if(j!=0)
	    	  j1 = j-1;
	      else
	    	  j1 = right.length-1;

	      // left[i] と right[j] を結ぶ直線Lと left[i1] および
	      // right[j1] の位置関係を調べる。
	      // どちらかの点が直線の上方にあれば、点を移動する。
	      // なければwhile 分を抜ける。 
	      
	      // left[i1]がLの上方にある、つまり有向直線left[i]->right[j]の左側にあるかを判断するようにする
	      if (left[i1].isLeft(left[i], right[j])) {  
	        // left[i1] は　L の上方にある
	        i = i1;
	      }
	      // right[j1]がLの上方にある、つまり有向直線left[i]->right[j]の左側にあるかを判断するようにする
	      else if (right[j1].isLeft(left[i], right[j])) { 
	        // right[j1] は　L の上方にある
	        j = j1;
	      }
	      else break;
	    }
	    
	    // 接点のインデックスを配列に入れて返す。
	    return new int[] {i, j};
	  }

	  /* 
	   * findLowerTangent
	   * 頂点配列 left で表される凸多角形と、
	   * 頂点配列 right で表される凸多角形の下側共通接線を求める。
	   * 凸多角形の頂点は、配列のなかで反時計回りの順に並んでいると仮定する。
	   * また、第１の凸多角形は第２の凸多角形より完全に左にある、つまり
	   * 第１の凸多角形のどの頂点の x座標も第２の凸多角形のどの頂点の
	   * x 座標よりも小さい、と仮定する。

	   * 戻り値： int型の大きさ２の配列。その第０要素は、
	   * 下側共通接線が左の凸多角形と接する頂点の、配列 leftにおけるインデックス、
	   * 第１要素は、下側共通接線が右の凸多角形と接する頂点の、
	   * 配列 right におけるインデックスである。
	   */

	  public static int[] findLowerTangent(Point[] left, Point[] right) {
	    /*** 実装してください。このままだと、0 0 を要素とする配列を返します。***/
	    /* 注意（１）　条件 p.isLeft(q, r) を左右反転した条件で、pが直線qr上にあるときに
	     * trueになってしまわないように、特に注意が必要です。正しく反転した上で、説明してください。
	     *　注意（２）　上側から下側に変更する際に、コードの変更だけでなく、コメントも適切に変更してください。
	     *　上側の場合のコメントで不適切になったものがそのまま残っている場合は、大きな減点の対象となります。
	     */
		  
		    // 左の多角形の最右の頂点に注目し、そのインデックスを i とする。
		    int i = rightmostPointIndex(left); 

		    // 右の多角形の最左の頂点に注目し、そのインデックスを j とする。
		    int j = leftmostPointIndex(right);    
		    
		    while (true) {
		      // i1 によって、左の凸多角形においてインデックスが i の頂点の時計回りで次の点の
		      // インデックスを表す。
		    
		      int i1= 0;
		    	
		      if(i!=0)
		    	  i1 = i - 1;
		      else
		    	  i1 = left.length-1;

		      // j1 によって、右の凸多角形においてインデックスが j の頂点の反時計回りで次の点の
		      // インデックスを表す。
		 
		      int j1 = 0;
		      
		      if(j!=right.length-1)
		    	  j1 = j + 1;
		      else
		    	  j1 = 0;

		      // left[i] と right[j] を結ぶ直線Lと left[i1] および
		      // right[j1] の位置関係を調べる。
		      // どちらかの点が直線の下方にあれば、点を移動する。
		      // なければwhile 分を抜ける。 
		      
		      // left[i1]がLの下方にある、つまり有向直線right[j]->left[i]の左側にあるかを判断するようにする
		      if (left[i1].isLeft(right[j], left[i])) {  
		        // left[i1] は　L の下方にある
		        i = i1;
		      }
		      // right[j1]がLの下方にある、つまり有向直線right[j]->left[i]の左側にあるかを判断するようにする
		      else if (right[j1].isLeft(right[j], left[i])) { 
		        // right[j1] は　L の下方にある
		        j = j1;
		      }
		      else 
		    	  break;
		     }
		    
		    // 接点のインデックスを配列に入れて返す。
		    return new int[] {i, j};
	  }

	  /*
	   * 与えられたPoint 配列中で、 x 座標が最小の点の、配列中での番号（インデックス）
	   * を返す。
	   */
	  private static int leftmostPointIndex(Point[] p) {
	    int k = 0;
	    int result = 0;
	    
	    // 配列pのすべてのx座標を比較して、それが最少である配列のインデックスを変数resultに記憶させる。
	    for(;k<p.length;k++)
	    	if(p[result].x>p[k].x)
	    		result = k;

	    return result;
	  }

	  /*
	   * 与えられたPoint 配列中で、 x 座標が最大の点の、配列中での番号（インデックス）
	   * を返す。
	   */
	  private static int rightmostPointIndex(Point[] p) {
	    int k = 1;
	    int result = 0;
	    
	    // 配列pのすべてのx座標を比較して、それが最大である配列のインデックスを変数resultに記憶させる。
	    for(;k<p.length;k++)
	    	if(p[result].x<p[k].x)
	    		result = k;
	    
	    return result;
	  }

  // ここは、詳しく読む必要はない。
  // 左右の凸多角形を生成する。
  // 初期状態では、両方とも正 n 角形とする。
  // 頂点数は、入力欄の指定に従う。

  private void refresh() {
    // 結合された凸多角形はクリアする
    combined = null;

    // ポイントクラスの縮尺パラメータをデフォルトの１にする。
    Point.setScale(1);

    // 点の通し番号は 0 からはじめる
    np = 0;

    int nLeft = Integer.parseInt(leftSizeInput.getText());
    int nRight = Integer.parseInt(rightSizeInput.getText());

    int nMax = nLeft;
    if (nRight > nMax) nMax = nRight;

    // 頂点数に応じて、ポイントクラスの縮尺パラメータを決める
    if (nMax > 50)
      Point.setScale(nMax / 20);
    else
      Point.setScale(1);

    // 左の凸多角形の中心
    int x1 = Point.xMax()/4;
    int y1 = Point.yMax()/2;

    // 右の凸多角形の中心
    int x2 = 3 * Point.xMax()/4;
    int y2 = Point.yMax()/2;

    // 外接円の半径
    int r = Point.xMax()/6;

    left = regularPolygon(nLeft, x1, y1, r);
    right = regularPolygon(nRight, x2, y2, r);

    // 今後の変形に備えて乱数オブジェクトを生成しておく。
    // シードを固定にしておけば、同一手順での変形は同一結果になる。
    random = new Random(11111);
  }

  // ここは、詳しく読まなくてもよい
  // 正多角形を生成する
  private Point[] regularPolygon(int n, int x, int y, int radius) {
    Point[] p = new Point[n];
    double xd = (double) x;
    double yd = (double) y;
    double r = (double) radius;

    double theta = 2 * Math.PI / n;

    for (int i = 0; i < n; i++) {
      p[i] = new Point(np++,
          (int) (xd + r * Math.cos(theta * i)),
          (int) (yd + r * Math.sin(theta * i))
          );
    }
    return p;

  }

  // ここは、任意課題をやらない場合は読まなくてもよい
  // 凸多角形を変形する
  // x座標の範囲は変化がないようにする
  private void deformPolygon(Point[] p) {
    if (p.length <= 3) {
    // もとのPointオブジェクトの座標は変えないように、オブジェクトのコピーを作る。
      for (int i = 0; i < p.length; i++) {
        p[i] = new Point(p[i].id, p[i].x, p[i].y);
      }
    }
    
    if (p.length == 1) {
      // 一角形の場合は、y座標を振る
      p[0].perturbY(random);
      return;
    }
    if (p.length == 2) {
      // 二角形の場合は、両端のy座標を振る
      p[0].perturbY(random);
      p[1].perturbY(random);
      // 点の順番を入れ替える
      Point tmp = p[0];
      p[0] = p[1];
      p[1] = tmp;
      return;
    }
    if (p.length == 3)  {
      // 三角形形の場合は、一つの再生点のy座標を振る
      int r = random.nextInt(3);
      p[r].perturbY(random);
      while (!isConvex(p)) {
        p[r].perturbY(random);
      }
      // 点の順番を循環的に入れ替える
      Point tmp = p[0];
      p[0] = p[1];
      p[1] = p[2];
      p[2] = tmp;
      return;
    }
    // 三角形以上
    // x座標の最小値と最大値を求めておく。
    int minX = p[0].x;
    int maxX = p[0].x;

    for (int i = 1; i < p.length; i++) {
      if (p[i].x < minX) minX = p[i].x;
      if (p[i].x > maxX) maxX = p[i].x;
    }

    // 結果の多角形の座標を表す
    Point[] q = new Point[p.length];

    do {
      // 凸な変形結果となるまで、変形の試みを繰り返す

      // 配列内の頂点順をずらしながら、コピーする
      for (int i = 0; i < p.length; i++) {
        Point s = p[(i + 1)%p.length];
        q[i] = new Point(s.id, s.x, s.y);
      }

      // 固定する点の番号をランダムに選ぶ
      int k = random.nextInt(q.length);

      // 拡大率を0.9から1.1の範囲でランダムに選ぶ
      double rate = random.nextDouble()*0.2 + 0.9;

      // 途中までは、座標をdoubleで計算する
      double[] x = new double[q.length];
      double[] y = new double[q.length];

      for (int i = 0; i < q.length; i++) {
        int d = (i - k + q.length)% q.length;
        if (d == 0 || d == 1 || d == q.length - 1) {
          // q[k]とその隣接点の座標はそのまま
          x[i] = (double) q[i].x;
          y[i] = (double) q[i].y;
        }
        else {
          //それ以外の場合は、q[k]とベクトルq[k]->q[i]の方向を固定しつつ
          //その長さをrateの比で拡大する
          x[i] = (double) q[k].x + rate * (double) (q[i].x - q[k].x);
          y[i] = (double) q[k].y + rate * (double) (q[i].y - q[k].y);
        }
      }

      //x座標の最小値と最大値
      double minX1 = x[0];
      double maxX1 = x[0];
      for (int i = 1; i < q.length; i++) {
        if (x[i] < minX1) minX1 = x[i];
        if (x[i] > maxX1) maxX1 = x[i];
      }

      // x座標のもとの最小値、最大値に合わせるようにx座標の調整をする
      double r = ((double) (maxX - minX)) / (maxX1 - minX1);
      for (int i = 0; i < q.length; i++) {
        x[i] = (double) minX + r * (x[i] - minX1);
      }

      for (int i = 0; i < q.length; i++) {
        //　四捨五入で、qの点の座標を決める
        q[i].x = (int) (x[i] + 0.5);
        q[i].y = (int) (y[i] + 0.5);
      }
    }
    while (!isConvex(q));

    // 結果をpの参照する配列にコピーする
    for (int i = 0; i < p.length; i++) {
      p[i] = q[i];
    }
  }

  // 多角形が凸であるかを判定する
  // 多角形は、反時計回りに頂点を格納する配列で表現される
  private boolean isConvex(Point[] p) {
    if (p.length <= 2) return true;
    for (int i = 0; i < p.length; i++) {
      if (p[(i + 1)%p.length].isLeft(p[i], p[(i + 2)%p.length])) {
        return false;
      }
    }
    return true;
  }

  // 凸多角形 left と 凸多角形 right を、上下の共通接線によって
  // 結合してひとつの凸多角形を作成して返す
  // 凸多角形は、反時計回りに頂点を格納する配列で表現される
  // 頂点数は、配列の長さ（要素数）に等しい
  // 左の凸多角形は右の凸多角形よりも、完全に左にある
  // (左の凸多角形の頂点の x 座標はどれも、右の凸多角形のどの頂点の x座標よりも小さい）
  // と仮定する
  // 結果の凸多角形のどの頂点を配列の最初の要素とするかは自由とする
  /*** 実装してください
   * 前回作成したメソッド、findUpperTangent と findLowerTangentを用います
   ***/
  
  //* findUppertangentとfindLowerTangentメソッドを用いて上下の共通接線の接点を求め、
  //* 用意したリストに目的の凸多角形の頂点を順番に格納していく
  //* 最後にリストを配列に変換したものを返す
   private Point[] combinePolygons(Point[] left, Point[] right) {
	   
	   //* 目的の図形を表現するための配列を作るためのリスト
	   List<Point> list = new ArrayList<Point>();
	   
	   //* 上下の共通接線の接点のインデックス
	   int LL = findLowerTangent(left,right)[0];
	   int LR = findLowerTangent(left,right)[1];
	   int UL = findUpperTangent(left,right)[0];
	   int UR = findUpperTangent(left,right)[1];
	
	   //* リストの先頭が左の図形の上側共通接線の接点になるようにして
	   //* 反時計回りに目的の凸多角形の頂点を格納する
		if(UL<=LL) {
			for(int c=UL;c<=LL;c++)
				list.add(left[c]);
		}
		else {
			for(int c=UL;c<left.length;c++)
				list.add(left[c]);
			for(int c=0;c<=LL;c++)
				list.add(left[c]);
		}
		
		if(LR<=UR) {
			for(int c=LR;c<=UR;c++)
				list.add(right[c]);
		}
		else {
			for(int c=LR;c<right.length;c++)
				list.add(right[c]);
			for(int c= 0;c<=UR;c++)
				list.add(right[c]);
		}
		
		//* リストを配列に変換する
		Point[] combine = list.toArray(new Point[list.size()]);
		
		return combine;
  }

  // ボタンから発生するイベントの処理
  public void actionPerformed(ActionEvent ae) {
    String commandName = ae.getActionCommand();
    if (commandName.equals("Refresh")) {
      // 多角形の初期化
      refresh();
    }

    else if (commandName.equals("Deform Left")) {
      // 左の凸多角形の変形
      if (left != null)
        deformPolygon(left);
    }

    else if (commandName.equals("Deform Right")) {
      // 右の凸多角形の変形
      if (right != null)
        deformPolygon(right);
    }

    else if (commandName.equals("Combine")) {
      // 左右の凸多角形を結合する
      if (left != null && right != null)
        combined = combinePolygons(left, right);
    }

    else if (commandName.equals("Close"))
      System.exit(0);

    // フレームの再描画を要求する。
    repaint();
  }

  // ２次元の点を表すクラス
  private static class Point {
    // デフォルトの縮尺パラメータ
    private static final int DEFAULT_SCALE = 1;

    // 描画領域のデフォルトの位置とサイズ
    private static final Rectangle
    DEFAULT_BOUNDS = new Rectangle(50, 150, 800, 600);

    // 縮尺パラメータ
    private static int scale = DEFAULT_SCALE;

    // 描画領域の位置とサイズ
    private static Rectangle bounds = DEFAULT_BOUNDS;

    // 縮尺パラメータの設定
    private static void setScale(int s) {
      scale = s;
    }

    // 描画領域の位置とサイズの設定
    private static void setBounds(Rectangle r) {
      bounds = r;
    }

    // 点の番号
    private int id;

    // x座標と y座標
    private int x;
    private int y;

    // コンストラクタ
    private Point(int id, int x, int y) {
      this.id = id;
      this.x = x;
      this.y = y;
    }

    // y座標をランダムに変化させる
    private void perturbY(Random random) {
      double r = random.nextDouble();
      if (yMax() * r > y) {
        y = (y + yMax()) / 2;
      }
      else {
        y = y / 2;
      }
    }
    
    // この点が、有向直線 p->q の左側にあるかを判定する。
    private boolean isLeft(Point p, Point q) {
      // ベクトル p -> q のx成分とy成分
      long x1 = q.x - p.x;
      long y1 = q.y - p.y;

      // ベクトル p -> this のx成分とy成分
      long x2 = x - p.x;
      long y2 = y - p.y;

      // 第１のベクトルと第２のベクトルのなす平行四辺形の符号付面積
      long a = x1 * y2 - x2 * y1;

      // 点thisが有向直線 p->q の左側にある条件は、この符号が正であること
      return a > 0;
    }


    // この点のx座標を画素単位の座標に変換した値を返す。
    private int xInFrame() {
      return toXInFrame(x);
    }

    // この点のy座標を画素単位の座標に変換した値を返す。
    private int yInFrame() {
      return toYInFrame(y);
    }

    // 描画範囲に収まる論理x座標の上限
    private static int xMax() {
      return bounds.width * scale;
    }

    // 描画範囲に収まる論理y座標の上限
    private static int yMax() {
      return bounds.height * scale;
    }

    // 論理x座標から画素座標への変換
    private static int toXInFrame(int x) {
      return bounds.x + x / scale;
    }

    // 論理y座標から画素座標への変換
    private static int toYInFrame(int y) {
      return bounds.y + bounds.height - y / scale;
    }

    // この点の文字列表現
    public String toString() {
      return "(" + id + ":" + x + "," + y + ")";
    }
  }

  public static void main(String[] args) {
    new CombinePolygons();
  }
}
