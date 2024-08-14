/*
２次元の有限点集合の凸包構築アルゴリズムを扱います。
単純化の仮定として、与えられた点集合中の３点が一直線上に並ぶことはないと
します。
今回は準備として、2 点を結ぶ線分が凸包の辺であるかどうかの判定法を実装
し、実験します。
今回は、下敷きプログラム HullEdges.java が与えられます。このファイルを
ダウンロードし、足りない部分を補って課題を完成してください。HullEdge クラ
スは、2 次元平面上の点を表現する内部クラス Point を含んでいます。HullEdges
クラスは、AWT の Frame クラスを拡張し、次の機能を提供します。　

1. 点集合選択欄：　プログラム中で用意されたテストケースや、ランダムな
点集合など、使用する点集合を選択する。
2. Refresh ボタン：　選択欄で指定された点集合を入力データとして設定す
る。設定された点集合の点は青く表示される。
3. Detect ボタン（未実装）： 点集合の凸包の辺をすべて検出し、赤い線分で
表示する。
4. Leftmost ボタン（不完全実装）：　点集合のなかで最も左の点をみつけて、
それを注目点に設定する。注目点は緑色で表示される。同時に、注目点の
文字列表現がコンソールに表示される。
5. Next ボタン（不完全実装）：　注目点が定義されていて、凸包の頂点である
とき、反時計まわりで次の凸包頂点に注目点を移動する。同時に、新しい
注目点の文字列表現がコンソールに表示される。ただし、凸包の辺が検出
されていない状態でこのボタンが押されたときは何も起きない。
6. Prev ボタン（不完全実装）：　注目点が定義されていて、凸包の頂点である
とき、時計まわりで次の凸包頂点に注目点を移動する。同時に、新しい注
目点の文字列表現がコンソールに表示される。ただし、凸包の辺が検出さ
れていない状態でこのボタンが押されたときは何も起きない。

十分に理解した後に、次の課題に取り組んでください。

1. Leftmost ボタンの処理を完成する。具体的には、 HullEdges クラスの leftmostPoint メソッドが未実装であるので、これを実装する。
2. Detect ボタンの処理を完成する。具体的には、 Point クラスの isLeft メ
ソッドと、HullEdges クラスの isHullEdge メソッドが未実装であるので、
これらを実装する。これを正しく行うと、凸包頂点に対しては「次の点」が
定義されるようになるので、Next ボタンも動くようになる。
3. Prev ボタンの処理を完成する。具体的には、HullEdges クラスの moveFocusToPrevious メソッドを実装する。頂点クラスは、時計回りで「次の点」
を表す変数を持っていないので、next 変数の値をたどって、凸包の外周を
一周する必要がある。（この課題では、頂点クラスのメンバー変数として時
計回りで「次の点」を表すものを付け加える解法は不可とする。）

Point クラスの中には、３点以上が一直線に並ぶときのために必要な未実装のメソッド isLeftExtended が含まれていますが、未
実装のままにしてください。
*/

package task06;
/*
 * HullEdge クラス：　凸包アルゴリズムの準備
 * 
 * 点の扱い、３点の位置関係の判定、
 * 凸包の辺の検出などを行う
 * 
 * 点を表すPointクラスを内部クラスとして持つ
 * 
 * ２０２１年１０月２６日改訂
 */

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class HullEdges extends Frame implements ActionListener {
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

  // 	点の個数
  private int np;

  // 点の配列
  private Point[] point;

  // 点集合の配列（テスト用）
  private Point[][] pointSet;

  // 現在の注目点
  private Point focus;

  // 点集合の選択欄
  private Choice choosePointSet;

  public HullEdges() {

    // このフレームの大きさを設定する。
    setSize(WIDTH, HEIGHT);

    // Point クラスの座標領域を設定する。
    Point.setBounds(
        new Rectangle(LEFT_MARGIN, TOP_MARGIN, 
            WIDTH - LEFT_MARGIN - RIGHT_MARGIN, 
            HEIGHT - TOP_MARGIN - BOTTOM_MARGIN));

    // 配置の方式をFlowLayout に設定
    setLayout(new FlowLayout());

    // 点集合の選択欄生成
    Label label = new Label("Select point set:");
    add(label);

    choosePointSet = new Choice();
    choosePointSet.addItem("Case1 (n = 1)");
    choosePointSet.addItem("Case2 (n = 2)");
    choosePointSet.addItem("Case3 (n = 3)"); 
    choosePointSet.addItem("Case4 (n = 3)");
    choosePointSet.addItem("Case5 (n = 4)");
    choosePointSet.addItem("Case6 (n = 4)");
    choosePointSet.addItem("Case7 (n = 4)");
    choosePointSet.addItem("Case8 (n = 4)");
    choosePointSet.addItem("Case9 (n = 5)");
    choosePointSet.addItem("Case10 (n = 5)");
    choosePointSet.addItem("Random1 (n = 10)");
    choosePointSet.addItem("Random2 (n = 100)");
    choosePointSet.addItem("Random3 (n = 1000)");
    choosePointSet.addItem("Random4 (n = 10000)");

    add(choosePointSet);


    // 初期化要求ボタン
    Button refreshButton = new Button("Refresh");
    add(refreshButton);
    refreshButton.addActionListener(this);

    // 凸包辺の検出ボタン
    Button detectButton = new Button("Detect");
    add(detectButton);
    detectButton.addActionListener(this);

    // 最左頂点ボタン
    Button leftmostButton = new Button("Leftmost");
    add(leftmostButton);
    leftmostButton.addActionListener(this);

    // 次の頂点ボタン
    Button nextButton = new Button("Next");
    add(nextButton);
    nextButton.addActionListener(this);

    // ひとつ前の頂点ボタン
    Button prevButton = new Button("Prev");
    add(prevButton);
    prevButton.addActionListener(this);

    // 終了ボタン
    Button closeButton = new Button("Close");
    add(closeButton);
    closeButton.addActionListener(this);

    // テスト用のポイントデータ
    pointSet = new Point[][] {
        // Case1
        {new Point(0, 200, 200)},
        //Case2
        {new Point(0, 200, 200), 
          new Point(1, 400, 400)
        }, 
        //Case3
        {new Point(0, 200, 400),
          new Point(1, 400, 400),
          new Point(2, 200, 200), 
        }, 
        //Case4
        {	new Point(0, 400, 500), 
          new Point(1, 300, 300),
          new Point(2, 100, 100) 
        }, 
        //Case5
        {new Point(0, 200, 200),
          new Point(1, 400, 200),
          new Point(2, 400, 400),
          new Point(3, 200, 400)
        },
        //Case6
        {	new Point(0, 500, 200),
          new Point(1, 200, 200),
          new Point(2, 300, 300),
          new Point(3, 200, 500),
        },
        //Case7
        {   new Point(0, 400, 200),
            new Point(1, 300, 250),
            new Point(2, 200, 200),
            new Point(3, 300, 500),
        },
        //Case8
        {   new Point(0, 500, 450),
          new Point(1, 300, 250),
          new Point(2, 200, 200),
          new Point(3, 300, 500),
        },
        //Case9
        {new Point(0, 200, 400),
          new Point(1, 400, 400),
          new Point(2, 200, 200),
          new Point(3, 300, 250),
          new Point(4, 250, 220)
        }, 
        //Case10
        { new Point(0, 400, 200),
          new Point(1, 300, 250),
          new Point(2, 200, 200),
          new Point(3, 300, 500),
          new Point(4, 400, 400)
        }
    };
    setVisible(true);
  }

  // フレームの描画が必要になったときに呼ばれる。
  // 点群や、凸包の辺の描画をここで行う。
  @Override
  public void paint(Graphics g) {
    // 凸包の辺の描画
    for (int i = 0; i < np; i++) {
      // point[i]から出る凸包の辺があれば、その辺を赤い線分で描く。
      if (point[i].next != null) {
        g.setColor(Color.red);
        g.drawLine(point[i].xInFrame(), point[i].yInFrame(),
            point[i].next.xInFrame(), point[i].next.yInFrame());
      }
    }

    // 点の描画
    for (int i = 0; i < np; i++) {
      g.setColor(Color.blue);

      int px = point[i].xInFrame();
      int py = point[i].yInFrame();
      g.fillOval(px - RADIUS, py - RADIUS, RADIUS * 2, RADIUS * 2);
    }

    // 注目点の描画
    if (focus != null) {
      g.setColor(Color.green);
      int px = focus.xInFrame();
      int py = focus.yInFrame();
      g.fillOval(px - RADIUS, py - RADIUS, RADIUS * 2, RADIUS * 2);
    }

  }

  // 選択欄 choosePointSet で指定された点集合を設定する
  private void refresh() {
    // ポイントクラスの縮尺パラメータをデフォルトの１にする。
    Point.setScale(1);

    //　注目点をクリア
    focus = null;

    // 選ばれた点集合の文字列による記述
    String choice = choosePointSet.getSelectedItem();
    
    // 選ばれた点集合のインデックス
    int px = choosePointSet.getSelectedIndex();

    // pointSet[] 配列にあるものはそれを選ぶ
    if (px < pointSet.length) {
      point = pointSet[px];
    }

    // ランダムな点集合の設定
    else if (choice.startsWith("Random1")) {
      point = randomPoints(10);
    }
    else if (choice.startsWith("Random2")) {
      point = randomPoints(100);
    }
    else if (choice.startsWith("Random3")) {
      // 点の数が大きいときは、座標値を大きくする。
      // x 座標が等しい２点は選ばないことにしているので、
      // こうしないと、点集合の生成が不可能になる。
      Point.setScale(10);
      point = randomPoints(1000);
    }
    else if (choice.startsWith("Random4")) {
      Point.setScale(50);
      point = randomPoints(10000);
    }
    
    if (point != null) np = point.length;
  }

  // ランダムな n 点の集合を生成し、配列に格納して返す。
  // 座標値は非負であり、Point クラスに設定された上限以内で
  // ランダムに選ぶ。x 座標の等しい２点は含まない。
  private Point[] randomPoints(int n) {
    // 乱数生成オブジェクト
    // シードを固定したのは、デバッグを容易にするため
    Random random = new Random(11111);

    // 結果の点集合を入れる配列
    Point p[] = new Point[n];

    // 各 x 座標が既に使われているかを示す配列
    boolean used[] = new boolean[Point.xMax()];

    for (int i = 0; i < n; i++) {
      int x = random.nextInt(Point.xMax());
      while (used[x]) {
        // x 座標の重複があるときは、選びなおす
        x = random.nextInt(Point.xMax());
      }
      used[x] = true;
      int y = random.nextInt(Point.yMax());
      p[i] = new Point(i, x, y);
    }
    return p;
  }
 
  private Point leftmostPoint() {
    Point p = point[0];

   // point 配列のなかで最も x 座標の小さい点を求める
   for(int i=1;i<point.length;i++)
	   if(p.x>point[i].x)
		   p = point[i];

    return p;
  }
  
  // 変数focusの参照を、現在参照されている点の次の点に移動する。
  // 次の点とは、凸包の外周を反時計回りで回るときの次の頂点を意味する。
  // focusの値がnullのときや、focus.nextの値がnullのときは何もしない
  // 移動できたときはtrue、できなかったときはfalseを返す
  private boolean moveFocusToNext() {
    if (focus == null || focus.next ==null) {
      return false;
    }
    else {
      focus = focus.next;
      return true;
    }
  }

  // 変数focusの参照を、現在参照されている点の凸包上で手前の点に移動する。
  // 手前の点とは、凸包の外周を時計回りで回るときの次の頂点を意味する。
  // 凸包上の点で、nextの値が定義されていないものがあるときは何もしない
  // 移動できたときはtrue、できなかったときはfalseを返す
 private boolean moveFocusToPrevious() {
    // Pointクラスは手前の点を参照する変数を持たないので、
    // next欄を繰り返したどって、凸包を一周する必要があります。
    // Pointクラスに手前の点を参照する変数（prev）などを付加する方法は不可です。	
	
	// 変数pの初期化
	Point p = focus;
	
	if(focus.next==null)
		return false;
	else
		p = focus.next;
	
	// 凸包上の点でnextの値が定義されていないものが無いかの確認と
	// nextの値がfocusのものの探索を同時に行う
	for(;p.next!=focus;p=p.next)
		if(p.next==null)
			return false;
	
	focus = p;
	
    return true;
  }

  // 凸包の辺をすべて検出して、検出された辺をpointオブジェクトの
  // メンバー変数 next によって記憶する。
  private void detectHullEdges() {
    for (int i = 0; i < np; i++) 
      for (int j = 0; j < np; j++) {
        // 同一ではない二点がなす有効線分 point[i] -> point[j]が凸包の頂点を反時計回りに
    	// 回る時の辺であるときpointオブジェクトのメンバー変数nextによってその編組記憶する
        if (i != j && isHullEdge(point[i], point[j]))
          point[i].next = point[j];
      }
  }

  // 有向線分 p -> q が凸包の頂点を反時計回りに回るときの辺であるか
  // どうかを判定する。
  private boolean isHullEdge(Point p, Point q) {
	// 変数rの初期化
	Point r = point[0];
	
	// 基本実装：　point クラスの isLeft メソッドを用い、
    //	　p, q 以外のすべての点が、有向直線p -> q の左に
    //    あれば　true と判定する。
	for(int i = 0;i<point.length;) {  
		if(r!=p&&r!=q) {
			if(r.isLeft(p,q))
				r = point[i++];
			else
				return false;
		}
		else
			r = point[i++];
	}
    // 任意課題のための実装：
    // isLeft メソッドのかわりに isLeftExtended メソッドを用い
    // p, q 以外のすべての点が、有向直線p -> q の左にあるか、
    // または線分pq の内部にあればtrue と判定する。

    return true;
  }

  // ボタンから発生するイベントの処理
  @Override
  public void actionPerformed(ActionEvent ae) {
    String commandName = ae.getActionCommand();

    if (commandName.equals("Refresh")) 
      // 点群データの初期化
      refresh();

    else if (commandName.equals("Detect")) 
      // 凸包の辺をすべて検出する
      detectHullEdges();

    else if (commandName.equals("Leftmost")) {
      // 最左の点を注目点にする
      focus = leftmostPoint();
      System.out.println("focus = " + focus);
    }

    else if (commandName.equals("Next")) {
      // 注目点を次の点にする
      if (moveFocusToNext()) {
        System.out.println("focus moved to " + focus);
      }
      else {
        System.out.println("focus not moved");
      }
    }
    else if (commandName.equals("Prev")) {
      // 注目点を手前の点にする
      if (moveFocusToPrevious()) {
        System.out.println("focus moved to " + focus);
      }
      else {
        System.out.println("focus not moved");
      }
    }

    else if (commandName.equals("Close"))
      System.exit(0);

    // フレームの再描画を要求する。
    repaint();
  }

  public static void main(String args[]) {
    new HullEdges();
  }

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

    // 点の連鎖をつくるための参照
    private Point next;

    // コンストラクタ
    private Point(int id, int x, int y) {
      this.id = id;
      this.x = x;
      this.y = y;
    }

    // この点が、有向直線 p->q の左側にあるかを判定する。
    private boolean isLeft(Point p, Point q) {
      // ベクトル p -> q とベクトル p -> this の成す平行四辺形の
      // 符号付面積が正ならば true そうでなければfalse を返す。
      // 符号付面積の計算には下のメソッド signedArea を用いる。
      if(signedArea((q.x-p.x),(q.y-p.y),(this.x-p.x),(this.y-p.y))>0)
    	  return true;
      
      return false;
    }

    // この点が、有向直線 p->q の左側にあるかを判定する。
    // この点が線分 pq 上（pとqの間）にあるときにあえて true を返す。
    // この有向直線上で、線分pqの外側にあるときは false を返す。
    // この点が有向直線 p->q の真に左側にある場合はもちろん
    // true を返す。
    // 凸包の外周の３点以上が一直線上にあるとき、
    // 一番長い辺を凸包の辺とみなすことになる。
    private boolean isLeftExtended(Point p, Point q) {
      // 3点以上が一直線上にある場合に対する任意課題を
      // 遂行するときは、実装して使ってください。
      return true;
    }

    // ベクトル (x1, y1) と (x2, y2) の成す平行四辺形の符号付面積を計算して返す。
    // (x1, y1) から (x2, y2) への角度 theta が 0 < theta < pi を満たすならば
    // 符号は正、pi < theta < 2pi ならば 符号は負である。
    // 座標値が大きいときに備えて、long (64ビット）で計算する。
    private long signedArea(int x1, int y1, int x2, int y2) {
      return ((long) x1) * ((long) y2) 
          - ((long) x2) * ((long) y1);
    }

    // 下の座標変換は描画のために使用する。凸包アルゴリズムでは必要ない。
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

}
