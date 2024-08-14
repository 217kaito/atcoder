/*
平面上の点集合に対してＭＳＴ（Minimum Spanning Tree、最小全域木）を求め
るバックトラックアルゴリズムを実装します。
最小全域木問題は、一般には辺に重みのついたグラフを入力としますが、この課題では、どの二つの点の間にも辺
があるようなグラフ（完全グラフ）を考え、辺の重みは平面上の距離（ユークリッ
ド距離）を整数に切り捨てたものであるとします。

課題は、再帰メソッド backtrack を完成することです。
*/

/*
 * MST.java
 * 
 * グラフのMST(最小全域木)
 * 平面上に与えられた点集合の最小全域木を求めます．
 * すべての点の対が辺で結ばれ、
 * その重みはユークリッド距離であるとします．
 * 
 * MST構築をバックトラックで行う makeMSTBackTrack
 * を途中まで実装してあります．これを完成してください．
 * 
 * ２０２１年１１月３０日改訂
 */
package task10;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MSTByBacktrack extends Frame 
implements ActionListener {

  // フレームの幅と高さ
  private static final int WIDTH = 900;
  private static final int HEIGHT = 700;

  // 描画領域の余白
  private static final int LEFT_MARGIN = 100;
  private static final int RIGHT_MARGIN = 100;
  private static final int TOP_MARGIN = 150; // ボタン配置のための余裕
  private static final int BOTTOM_MARGIN = 50; 

  // 頂点描画のための円の半径
  private static final int RADIUS = 3;

  // 表示座標の縮尺
  private static final int SCALE = 10;

  // 	頂点の個数
  private int nv;

  // 頂点の配列
  private Vertex[] vertex;

  // 辺の個数
  private int ne;

  // 辺の配列
  private Edge[] edge;

  // 頂点数選択欄
  private Choice chooseN;

  // 全域木構築途中の辺集合の配列（バックトラック法で使用）
  private Edge[] t;

  // 最良の辺集合の配列（バックトラック法で使用）
  private Edge[] best;

  // 重み和の最小値（バックトラック法で使用）
  private int bestWeight;

  public MSTByBacktrack() {
    // このフレームの大きさを設定する。
    setSize(WIDTH, HEIGHT);

    // 配置の方式をFlowLayout に設定
    setLayout(new FlowLayout());


    // 点の個数の選択ボックス生成
    Label label = new Label("number of points:");
    add(label);
    chooseN = new Choice();
    chooseN.addItem("2");
    chooseN.addItem("3");
    chooseN.addItem("4");
    chooseN.addItem("5");
    chooseN.addItem("6");
    chooseN.addItem("7");
    chooseN.addItem("8");
    chooseN.addItem("9");
    chooseN.addItem("10");
    chooseN.addItem("20");
    chooseN.addItem("30");
    chooseN.addItem("100");
    chooseN.addItem("1000");
    add(chooseN);

    // 初期化要求ボタン
    Button refreshButton = new Button("Refresh");
    add(refreshButton);
    refreshButton.addActionListener(this);

    // バックトラックによるMST構築ボタン
    Button backtrackButton = new Button("Backtrack");
    add(backtrackButton);
    backtrackButton.addActionListener(this);

    // クリアボタン
    Button clearButton = new Button("Clear");
    add(clearButton);
    clearButton.addActionListener(this);

    // 終了ボタン
    Button closeButton = new Button("Close");
    add(closeButton);
    closeButton.addActionListener(this);

    // フレームを可視化する
    setVisible(true);
  }

  // フレームの描画が必要になったときに呼ばれる。
  // グラフの描画をここで行う。
  public void paint(Graphics g) {
    // MSTの辺の描画。
    // 
    g.setColor(Color.RED);
    for (int i = 0; i < ne; i++) {
      if (edge[i].inMST) {
        drawEdge(edge[i].endVertex[0], 
            edge[i].endVertex[1], g);
      }
    }

    // 頂点の描画。Vertex は Point を継承しているので、
    // drawPoint を用いることができる．
    g.setColor(Color.BLUE);
    for (int i = 0; i < nv; i++) {
      drawPoint(vertex[i], g);
    }
  }

  private void drawPoint(Point p, Graphics g) {
    g.fillOval(p.xInFrame() - RADIUS,
        p.yInFrame() - RADIUS,
        RADIUS * 2, RADIUS * 2);
  }

  private void drawEdge(Point p, Point q,
      Graphics g) {
    g.drawLine(
        p.xInFrame(), 
        p.yInFrame(),
        q.xInFrame(), 
        q.yInFrame()
        );
  }

  public void actionPerformed(ActionEvent ae) {
    String commandName = ae.getActionCommand();
    if (commandName.equals("Refresh")) { 
      // 点群データの初期化
      refresh();
    }
    else if (commandName.equals("Backtrack")) {
      // バックトラックによるＭＳＴ作成
      clear();
      makeMSTByBacktrack();
    }

    else if (commandName.equals("Clear")) {
      clear();
    }
    else if (commandName.equals("Close")) 
      System.exit(0);

    // フレームの再描画を要求する。
    repaint();
  }

  // 点データの初期化
  private void refresh() {
    // 頂点の個数を選択ボックスから取得
    nv = Integer.parseInt(chooseN.getSelectedItem());
    vertex = new Vertex[nv];

    // 辺集合はクリア
    edge = null;
    ne = 0;

    // 描画領域の幅と高さ
    int width = WIDTH - LEFT_MARGIN - RIGHT_MARGIN;
    int height = HEIGHT - TOP_MARGIN - BOTTOM_MARGIN;

    // 点の（論理的な）重なりを避けるために、
    // 座標の範囲は SCALE 倍にとり、表示時に縮尺する。

    // 乱数生成器の生成
    // バグがとれるまでは、シード（種）を固定して
    // 毎回同じ系列が生成されるようにしておくと良い。

    Random random = new Random(111111);

    // 完成したら、下を使用すれば、起動ごとに違う
    // 例が得られる。
    // Random random = new Random();

    for (int i = 0; i < nv; i++) {
      int x = random.nextInt(width * SCALE);
      int y = random.nextInt(height * SCALE);
      vertex[i] = new Vertex(i, x, y);
    }
  }

  // 辺のマークのクリアして、MSTを空にする
  // また、連結成分管理の森を初期化する
  private void clear() {

    for (int i = 0; i < ne; i++) {
      edge[i].inMST = false;
    }
    for (int i = 0; i < nv; i++) {
      vertex[i].parent = null;
    }
  }

  // バックトラック法によるＭＳＴ構築：
  private void makeMSTByBacktrack() {
    // 点の対のすべてに対して辺を作る
    makeEdges();

    // 最良の辺集合： null で初期化
    best = null;

    // 木の辺集合を格納する配列

    t = new Edge[nv - 1];

    // 再帰メソッドを呼び出して、最良の辺集合を記録する
    backtrack(0, 0);
    
    // {best[0], best[1], .., best[nv - 1]}が最良の辺集合になっている

    // 最良の辺集合に属す辺をMSTの辺としてマーク（表示時に参照される）
    for (int i = 0; i < nv - 1; i++) {
      best[i].inMST = true;
    }

  }

  // 点の対のすべてに対して辺を作る
  private void makeEdges() {
    ne = nv * (nv - 1) / 2;
    edge = new Edge[ne];
    int k = 0;
    for (int i = 0; i < nv; i++) {
      for (int j = i + 1; j < nv; j++) {
        edge[k++] = new Edge(vertex [i], vertex[j]);
      }
    }
  }

  // バックトラック法の再帰メソッド
  // 採用を仮決めした辺は、大域配列tに順次格納して行く
  // i: 注目する辺の番号
  // k: これまでに採用された辺の本数
  /** 実装してください
   */
  
  private void backtrack(int i, int k) {
    //まず、再帰を止める条件（基底条件）を書く
    //木ができている場合には、最良解を更新する必要があるかを判断し、
    //必要があれば更新する。
	
	//* 採用した辺の本数が作成できる辺の最大数になった時
	if(k==nv-1) {
		int tmp=0;
		//* 採用を仮決めした辺の重みの合計を変数tmpに代入する
		for(int j=0;j<k;j++) {
			tmp += t[j].weight();
		}
		//* 初めてこのif文内に入る場合と採用を仮決めした辺の重みの合計が現在の最良の辺集合のものより小さい場合
		//* 採用を仮決めした辺集合を最良とする
		if(best == null || tmp < bestWeight) {
			if(best == null) { // 最初の時のみ　new Edge[]する
				best = new Edge[nv-1];
			}
			for(int m = 0;m < k;m++) {
				best[m] = t[m];
			}
			bestWeight = tmp;
		}
		return;
	}
	
	//* すべての辺に注目し終わったとき
	if(i == ne)
		return;
  
	 //以下は基底でない場合
    //edge[i]の両端点が異なる連結成分に属せば、この辺を採用して再帰する
    //edge[i]の第０端点の属す連結成分の代表頂点
    //（その連結成分を表す根付き木の根）は edge[i].endVertex[0].getRepresentative()
    //によって求めることができる。第１端点についても同様である。

    //edge[i]を採用しない場合の再帰はいずれにしても必要である。
    
    /** 以上はヒントのためのコメントであるので、自分の作成したコードに即して
     * コメントは書き直すこと。
     */
	
	Vertex[] vv = new Vertex[2];
	//* 用意した大きさ2の配列vvに注目した辺の両端点の属する連結成分の代表頂点をそれぞれ格納する
	//* これを以下のif文の条件に用いることで閉路を検出する
	vv[0] = edge[i].endVertex[0].getRepresentative();
	vv[1] = edge[i].endVertex[1].getRepresentative(); 
	//* 注目した辺の両端点が異なる連結成分の属すとき、この辺を採用して再帰
	//* ここで閉路を検出している
	//* 注目した辺の両端点の属する連結成分の代表頂点で親子関係を作っていくことで、
	//* 両端点の属する連結成分の代表頂点が同じだった場合
	//* その両端点がなす辺を採用してしまうと閉路になってしまうことがわかる
	if(vv[0] != vv[1]) {
		//* この辺の採用を仮決めする
		  t[k] = edge[i];
		  Vertex p = vv[0].parent;
		  //* この辺の第0端点の親を第1端点とする
		  vv[0].parent = vv[1];
		  backtrack(i+1,k+1);
		  vv[0].parent = p;
	}
		  // edge[i]を採用しない場合の再帰はいずれにしても必要である。
	     //* 閉路が検出された場合
		  backtrack(i+1,k);
  }

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
  }

  private class Vertex extends Point {
    /** ここで、閉路の検出の仕組みについて説明してください。考え方の説明と、
     * その仕組みのなかで、このクラスの変数やメソッドがどのように使われるかの説明を
     * 両方含むようにしてください。
     */
	  
	//* メソッドgetRepresentativeを用いることで
	//* 注目した辺の両端点の代表頂点を得る。
	//* 変数parentを用いて、採用した辺の両端点のそれぞれの
	//* 代表頂点が異なる場合、代表頂点をつないで連結成分を作っていき、
	//* 代表頂点が同じ場合、その辺を採用した場合、閉路ができることがわかる。
	
	// 頂点番号（配列の添え字と一致）
    private int index;

    // 連結成分を管理する森における親
    private Vertex parent;

    // コンストラクタ
    private Vertex(int index, int x, int y) {
      super(x, y);
      this.index = index;
    }

    // 頂点thisの属す連結成分の代表頂点を返す
    private Vertex getRepresentative() {
      if (parent == null) {
        return this;
      }
      return parent.getRepresentative();
    }

    public String toString() {
      return "(" + index + ":" + x + "," + y + ")";
    }
  }

  private class Edge {
    private Vertex[] endVertex;
    private boolean inMST;
    private int weight;

    private Edge (Vertex v1, Vertex v2) {
      endVertex = new Vertex[2]; 
      endVertex[0] = v1;
      endVertex[1] = v2;
      weight = weight();
    }

    private int weight() {
      int dx = endVertex[0].x - endVertex[1].x;
      int dy = endVertex[0].y - endVertex[1].y;
      return (int) Math.sqrt(dx * dx + dy * dy);
    }

    public String toString() {
      return "(" + endVertex[0] + "," + 
          endVertex[1] + ")";
    }
  }
  public static void main(String[] args) {
    // MST オブジェクトの生成
    new MSTByBacktrack();
  }
}
