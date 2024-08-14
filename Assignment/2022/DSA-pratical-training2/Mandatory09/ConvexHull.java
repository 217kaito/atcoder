/*
分割統治法アルゴリズムを実装します。
入力の３点が一直線上に乗ることはないと仮定してください。
れています。

課題は以下の通りです。
1. ConvexHull クラスの内部クラス XComparator の実装を完成してください。
このクラスは、２つの点を x 座標で比較するためのもので、凸包アルゴリズ
ムの最初に点集合を整列するところで用います。このクラスは、Comparator
インターフェイスを実装します。Comparator インターフェイスの定義する
compare メソッドを定義する必要があります。
2. ConvexHull クラスの再帰メソッド Point[] makeHull(int low, int high) を
実装してください。このメソッドは、x 座標によって整列された Point 配列
point[] の要素 point[low], .., point[high] の凸包を表す凸多角形を作成して、
戻り値として返します。凸包の表現は、前課題と同様、頂点を反時計回り
に格納した配列です。入力の点のうち、3 点が一直線に並ぶことはなく、ま
た２点の x 座標が等しいことはないと仮定します。
*/


package task09;
/*
 * ConvexHull クラス：　凸包分割統治アルゴリズム
 * 
 * ２０２０年１１月２４日改訂
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class ConvexHull extends Frame implements ActionListener {
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
	
	// 入力の点集合
	private Point[] point;
	
	// 点の個数
	private int np;
	
	// 最終的に得られた凸包（反時計回り）
	private Point[] hull;
	
	// 頂点数指定欄
	private TextField sizeInput;
	
	// 点生成のための乱数オブジェクト
	private Random random;
	
	public ConvexHull() {
		
		// このフレームの大きさを設定する。
		setSize(WIDTH, HEIGHT);
		
		// Point クラスの座標領域を設定する。
		Point.setBounds(
				new Rectangle(LEFT_MARGIN, TOP_MARGIN, 
						WIDTH - LEFT_MARGIN - RIGHT_MARGIN, 
						HEIGHT - TOP_MARGIN - BOTTOM_MARGIN));
		
		// 配置の方式をFlowLayout に設定
		setLayout(new FlowLayout());
    
    // 点の個数の選択欄
    Label label1 = new Label("点の個数:");
    add(label1);

    sizeInput = new TextField("       1");
    add(sizeInput);
    
    // 初期化要求ボタン
    Button refreshButton = new Button("Refresh");
    add(refreshButton);
    refreshButton.addActionListener(this);
    
    // 凸包作成ボタン
		Button makeHullButton = new Button("Make Hull");
		add(makeHullButton);
		makeHullButton.addActionListener(this);

		// 終了ボタン
		Button closeButton = new Button("Close");
		add(closeButton);
		closeButton.addActionListener(this);
		
		setVisible(true);
		
		// 乱数オブジェクトの生成：デバッグを容易にするためにシードを
		// 固定する
		random = new Random(11111);
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

	// 入力欄 sizeInput で指定された個数のランダムな点集合を設定する
	private void refresh() {
		np = Integer.parseInt(sizeInput.getText().trim());
		
		// 点の個数に応じて、ポイントクラスの縮尺パラメータを設定する。
		// x 座標が等しい２点は選ばないことにしているので、
		// こうしないと、点集合の生成が不可能になる。
		
		if (np >= 200)
			Point.setScale(np / 100);	
		else Point.setScale(1);	

		point = randomPoints(np);
	}

	// ランダムな n 点の集合を生成し、配列に格納して返す。
	// 座標値は非負であり、Point クラスに設定された上限以内で
	// ランダムに選ぶ。x 座標の等しい２点は含まない。
	private Point[] randomPoints(int n) {
		// 結果の点集合を入れる配列
		Point[] p = new Point[n];
		
		// 各 x 座標が既に使われているかを示す配列
		boolean[] used = new boolean[Point.xMax()];
		
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
	
	// フレームの描画が必要になったときに呼ばれる。
	// 点の描画と凸包の描画をここで行う。
	// 入力の頂点は青で、凸包は赤で描く。
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		if (point != null) {
			for (int i = 0; i < np; i++)
				paintPoint(point[i], g);
		}
		if (hull != null) {
	    g.setColor(Color.red);
			paintPolygon(hull, g);
		}
	}

	// 点を描画する。
	private void paintPoint(Point p, Graphics g) {
		int px = p.xInFrame();
		int py = p.yInFrame();
		g.fillOval(px - RADIUS, py - RADIUS, RADIUS * 2, RADIUS * 2);
	}

	// 凸多角形を描画する。
	private void paintPolygon(Point[] p, Graphics g) {
		// 頂点の描画
		for (int i = 0; i < p.length; i++) {
			paintPoint(p[i], g);
		}
		// 辺の描画
		for (int i = 0; i < p.length; i++) {
			int i1 = (i + 1) % p.length;
			int px = p[i].xInFrame();
			int py = p[i].yInFrame();
			int px1 = p[i1].xInFrame();
			int py1 = p[i1].yInFrame();
			g.drawLine(px, py, px1, py1);
		}
	}

	// 凸包の再帰アルゴリズムの入り口
	private void makeHull() {
		// 点集合を x 座標の小さい順にソートする
		// Arrays クラスの sort メソッドを用いる
		// ２つの点を比較するクラス XComparator を実装する必要がある。
		Arrays.sort(point, new XComparator());
		
		// 再帰アルゴリズム を呼び出す
		hull = makeHull(0, np - 1);
	}

	// 凸包の分割統治法再帰アルゴリズム
	// Point 配列 point[] （x 座標の小さい順に整列されている）
	// の point[low]、...、point[high] の範囲の点からなる点集合の凸包
	// を作成し、反時計回りの頂点配列として返す。
	// 点の数が２以下の場合を基底とする。
	// 点配列 point  を参照するために、インスタンスメソッドでなくてはならない。 
	/*** 実装してください
	　統合のステップには前回作成したメソッド combinePolyognsを用いてください
	　***/
	
	//* 再帰を用いているため、その繰り返し処理をできるだけ簡単にしてスタックオーバーフローを回避する
	private Point[] makeHull(int low, int high) {

		//* 点の数が2以下の場合、点をx座標の小さい順に並べたものを返す
		if(high-low+1<=2) {
			if(high-low+1==1) {
				Point [] make = new Point[1];
				make[0] = point[high];
				return make;			
			}
			else if(high-low+1==2) {
				Point [] make_L = new Point[1];
				Point [] make_R = new Point[1];
				
				make_L[0] = point[low];
				make_R[0] = point[high];

				return combinePolygons(make_L,make_R);
			}
			else {
				return point;
			}
		}
		else  {
			//* 再帰によって点集合を垂直線によって二分した左右の集合の凸包を求め,それらを統合する
			//* 点集合の点の個数が奇数の時、左側の図形を構成する点の個数が右側の図形のものよりも1つ多くなるようにする
			if((high-low+1)%2!=0) {
				return combinePolygons(makeHull(low,low+(high-low+1)/2),makeHull(low+(high-low+1)/2+1,high));	
			}
			else {
				return combinePolygons(makeHull(low,low+(high-low+1)/2-1),makeHull(low+(high-low+1)/2,high));	
			}
		}
	}

	// 左右の凸包の結合
	// 前回作成したCombinePolygons クラスからコピーして置き換える
	// compinePolygons の呼び出すメソッドも共にコピーする必要がある。
	private static Point[] combinePolygons(Point[] left, Point[] right) {
		   
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
		  // 作成済みの凸包をクリアする
			hull = null;
			// 頂点集合の生成
			refresh();
		}
		else if (commandName.equals("Make Hull")) {
			// 凸包を作成する
			if (point != null)
				makeHull();
		}
		
		else if (commandName.equals("Close"))
			System.exit(0);

		// フレームの再描画を要求する。
		repaint();
	}

	private class XComparator implements Comparator<Point> {
		// 	点を その x座標により比較する。
    @Override
		public int compare(Point p, Point q) {
			// p.x < q.x ならば 負の値
			// p.x > q.x ならば 正の値
			// p.x = q.x ならば 0 を返す
			/*** 正しいコードで置き換えてください
		  ***/
    		if(p.x<q.x) return -1;
    		else if(p.x>q.x) return 1;
    		else return 0;
		}
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
    @Override
    public String toString() {
      return "(" + id + ":" + x + "," + y + ")";
    }
  }

  public static void main(String[] args) {
		new ConvexHull();
	}
}
