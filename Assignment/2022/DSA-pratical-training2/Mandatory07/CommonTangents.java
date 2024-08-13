/*
ふたつの凸多角形の上側共通接線と下側共通接線を求めるアルゴリズムを実装します。
具体的な作業は CommonTangents クラスのメソッド findUpperTangent と
findLowerTangent を完成することです。この二つのメソッドの仕様は、CommonTangents のソースプログラムの中に記述してあります。メソッド findUpperTangent については、その手順もコメントで示してあり、細部を Java のコード
で埋めると完成するようになっています。findLowerTangent については、自力で
完成してください。
入力の３点が一直線上に乗ることはないと仮定してください。
*/

package task07;
/*
 *  CommonTangents クラス
 *  凸多角形の上側および下側共通接線
 *  ２０２１年１１月９日改訂
 */
import java.awt.Rectangle;

//* 上側共通接線と下側共通接線を求めるプログラムのfindUpperTangent,findLowerTangentを完成させた
//* この二つのメソッドを指示通り動くようにするためには注目頂点の初期状態(どの点に注目しているか）
//* や注目頂点のずらし方(時計回りか反時計回りか）の組み合わせが重要である

public class CommonTangents {
  /* 
   * findUpperTangent
   * 頂点配列 left で表される凸多角形と、
   * 頂点配列 right で表される凸多角形の上側共通接線を求める。
   * 凸多角形の頂点は、配列のなかで反時計回りの順に並んでいると仮定する。
   * また、第１の凸多角形は第２の凸多角形より完全に左にある、つまり
   * 第１の凸多角形のどの頂点の x座標も第２の凸多角形のどの頂点の
   * x 座標よりも小さい、と仮定する。

   * 戻り値： int型の大きさ２の配列。その第０要素は、
   * 上側共通接線が左の凸多角形と接する頂点の、配列 leftにおけるインデックス、
   * 第１要素は、上側共通接線が右の凸多角形と接する頂点の、
   * 配列 right におけるインデックスである。
   */

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

  // 共通接線メソッドのテスト
  // いろいろなケースに対して共通接線のメソッドを呼び出して
  // その結果をテストする。
  private static void testTangents() {
    Point[] left; 
    Point[] right;
    int[] ut;
    int[] lt;

    int np = 0;

    System.out.println("共通接線のテスト");

    left = new Point[]{
        new Point(np++, 1, 1)
    };

    right = new Point[]{
        new Point(np++, 2, 2)
    };

    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[] {
        new Point(np++, 1, 2)
    };
    right = new Point[]{
        new Point(np++, 2, 3),
        new Point(np++, 3, 1)
    };

    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 1, 2),
        new Point(np++, 2, 1)
    };
    right = new Point[]{
        new Point(np++, 3, 3)        		
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 1, 2),
        new Point(np++, 2, 1)
    };
    right = new Point[]{
        new Point(np++, 3, 3),
        new Point(np++, 4, 2)
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 1, 3),
        new Point(np++, 2, 1),
        new Point(np++, 3, 2)
    };
    right = new Point[]{
        new Point(np++, 4, 5),
        new Point(np++, 5, 2),
        new Point(np++, 6, 2)
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 3, 2),
        new Point(np++, 1, 3),
        new Point(np++, 2, 1)
    };
    right = new Point[]{
        new Point(np++, 4, 5),
        new Point(np++, 5, 2),
        new Point(np++, 6, 2)
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 2, 1),
        new Point(np++, 3, 2),
        new Point(np++, 1, 3)
    };
    right = new Point[]{
        new Point(np++, 4, 5),
        new Point(np++, 5, 2),
        new Point(np++, 6, 2)        		
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 2, 1),
        new Point(np++, 3, 2),
        new Point(np++, 1, 3)
    };
    right = new Point[]{
        new Point(np++, 6, 2),
        new Point(np++, 4, 5),
        new Point(np++, 5, 2)
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 2, 1),
        new Point(np++, 3, 2),
        new Point(np++, 1, 3)
    };
    right = new Point[]{
        new Point(np++, 5, 2),
        new Point(np++, 6, 2),
        new Point(np++, 4, 5)
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);
    
    left = new Point[]{
        new Point(np++, 5, 11),
        new Point(np++, 0, 10),
        new Point(np++, 2, 3),
        new Point(np++, 6, 1),
        new Point(np++, 11, 2),
        new Point(np++, 13, 5),
        new Point(np++, 14, 7),
        new Point(np++, 13, 9),
        new Point(np++, 11, 10),
        new Point(np++, 8, 11)
    };
    right = new Point[]{
        new Point(np++, 20, 4),
        new Point(np++, 30, 5),
        new Point(np++, 32, 11),
        new Point(np++, 26, 17),
        new Point(np++, 23, 15),
        new Point(np++, 21, 13),
        new Point(np++, 19, 8),
        new Point(np++, 18, 5)
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

    left = new Point[]{
        new Point(np++, 13, 5),
        new Point(np++, 14, 7),
        new Point(np++, 13, 9),
        new Point(np++, 11, 10),
        new Point(np++, 8, 11),
        new Point(np++, 5, 11),
        new Point(np++, 0, 10),
        new Point(np++, 2, 3),
        new Point(np++, 6, 1),
        new Point(np++, 11, 2)
    };
    right = new Point[]{
        new Point(np++, 21, 13),
        new Point(np++, 19, 8),
        new Point(np++, 18, 5),
        new Point(np++, 20, 4),
        new Point(np++, 30, 5),
        new Point(np++, 32, 11),
        new Point(np++, 26, 17),
        new Point(np++, 23, 15)
    };
    printPolygons(left, right);
    ut = findUpperTangent(left, right);
    testUpperTangent(left, right, ut);
    lt = findLowerTangent(left, right);
    testLowerTangent(left, right, lt);

  }

  // 左右の凸多角形のプリント
  private static void printPolygons(Point[] left, Point[] right) { 
    System.out.print("左の凸多角形：");
    for (int i = 0; i < left.length; i++) {
      System.out.print(left[i]);
    }
    System.out.println();

    System.out.print("右の凸多角形：");
    for (int i = 0; i < right.length; i++) {
      System.out.print(right[i]);
    }
    System.out.println();

  }

  // 上側共通接線のテスト
  // 左側の凸多角形 left と、右側の凸多角形 right の
  // 上側共通接線が left[ut[0]] と right[ut[1]] を結ぶ
  // ものであることを確認する．
  private static void testUpperTangent(Point[] left, Point[] right, 
      int[] ut) {

    if (ut == null || ut.length != 2) {
      System.out.println("上側共通接線の答えが長さ２の配列でありません");
    }
    else if (ut[0] < 0 || ut[0] >= left.length) {
      System.out.println("上側共通接線の最初の番号" + ut[0] + 
          "が左の凸多角形の頂点番号の範囲にありません。");
    }
    else if (ut[1] < 0 || ut[1] >= right.length) {
      System.out.println("上側共通接線の二番目の番号" + ut[1] + 
          "が右の凸多角形の頂点番号の範囲にありません。");
    }
    else if (!isUpperTangent(ut, left, right)) {

      System.out.println("上側共通接線であるはずの" + 
          left[ut[0]] + "と" +
          right[ut[1]] + "を結ぶ直線の上方に点があります。");
    }
    else {
      System.out.println("上側共通接線OK" + left[ut[0]] + "-" + right[ut[1]]);
    } 
  }
  // 下側共通接線のテスト
  // 左側の凸多角形 left と、右側の凸多角形 right の
  // 下側共通接線が left[lt[0]] と right[lt[1]] を結ぶ
  // ものであることを確認する．
  private static void testLowerTangent(Point[] left, Point[] right, 
      int[] lt) {

    if(lt == null || lt.length != 2) {
      System.out.println("下側共通接線の答えが長さ２の配列でありません");
    } 
    else if (lt[0] < 0 || lt[0] >= left.length) {
      System.out.println("下側共通接線の最初の番号" + lt[0] + 
          "が左の凸多角形の頂点番号の範囲にありません。");
    }
    else if (lt[1] < 0 || lt[1] >= right.length) {
      System.out.println("下側共通接線の二番目の番号" + lt[1] + 
          "が右の凸多角形の頂点番号の範囲にありません。");
    }
    else if (!isLowerTangent(lt, left, right)) {

      System.out.println(
          "下側共通接線であるはずの" + 
              left[lt[0]] + "と" +
              right[lt[1]] + "を結ぶ直線の下方に点があります。");
    }
    else {
      System.out.println("下側共通接線OK" + left[lt[0]] + "-" + right[lt[1]]);
    }
  }

  // 上側共通接線であるか
  private static boolean isUpperTangent(int[] ut, Point[] left, Point[] right) {
    Point u1 = left[ut[0]];
    Point u2 = right[ut[1]];

    for (int i = 0; i < left.length; i++) {
      if (left[i].isLeft(u1, u2)) return false;
    }
    for (int i = 0; i < right.length; i++) {
      if (right[i].isLeft(u1, u2)) return false;
    }
    return true;
  }
  // 下側共通接線であるか
  private static boolean isLowerTangent(int[] lt, Point[] left, Point[] right) {
    Point l1 = left[lt[0]];
    Point l2 = right[lt[1]];

    for (int i = 0; i < left.length; i++) {
      if (left[i].isLeft(l2, l1)) {
    	  System.out.println(i);
    	  return false;
      }
    }
    for (int i = 0; i < right.length; i++) {
      if (right[i].isLeft(l2, l1)) {
    	  System.out.println(i);    	  
    	  return false;
      }
    }
    return true;
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
      // ベクトル v1 =  p -> q のx成分とy成分
      long x1 = q.x - p.x;
      long y1 = q.y - p.y;
      // ベクトル v2 =  p -> this のx成分とy成分
      long x2 = x - p.x;
      long y2 = y - p.y;
      // v1とv2のなす平行四辺形の符号付面積が正ならば
      //　thisは有向直線 p->q の左側にある。
      return x1 * y2 - x2 * y1 > 0;
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
    testTangents();
  }


}