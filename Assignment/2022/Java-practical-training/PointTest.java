/*
• 座標を表すPointクラスがある
• Pointクラスには、テスト用の点を出力するPoint.testPoint(int i)というメソッドがある
• testPoint(1)からtestPoint(10)までで得られる10点について、相異なる点はいくつあるだろうか？重複を考慮し、2回以上現れる点は1回のみ表示するようにして、全部の点を表示しよう。
    • 「点が等しい」とは、座標の値が等しいこと
    • Point.toString()が用意されているので、異なる点を1行に1つずつprintln()する

要求
• PointTestクラスのmainを呼び出したら、
    • まず、相異なる点の総数を整数で表示し、
    • 次の行から相異なる点を1行に1つずつ表示する
        • 表示順序は問わない
• ソースファイルはPointTest.java
• 同じファイルにPointクラスを入れておくこと
    • Pointクラスにもコードを書き加える必要あり
• ひな形は配布してある
• 今回はコレクションの使い方の練習なので、そう思って解いてください
*/

//6
//(2,1)
//(-2,-1)
//(0,2)
//(-2,1)
//(2,-1)
//(0,-2)

package javalec10;

import java.util.HashSet;
import java.util.Objects;

class Point {
	private int x;
	private int y;
	public Point(int x, int y) { this.x = x; this.y = y; }
	public String toString() { return "(" + x + "," + y + ")"; }

	private Point rotate(int theta) {
		double rad = Math.toRadians(theta);
		int rx = (int) Math.round(x * Math.cos(rad) - y * Math.sin(rad));
		int ry = (int) Math.round(x * Math.sin(rad) + y * Math.cos(rad));
		return new Point(rx, ry);
	}
	public static Point testPoint(int i) {
		Point p = new Point(2, 1);
		return p.rotate(60*i);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}
}

public class PointTest {

	public static void main(String[] args) {
		// Point.testPoint()を1から10までの整数を引数として呼び出して得られる10個の点について、同じ座標を表すものがないか調べながら保存する。同じ座標のものが現れたらそれは無視する。
		// 重複を排除した後で、点の総数を表示し、次の行から点を1行に1つずつ(toString()使って)出力する。
		String [] result = new String[10];
		
		HashSet<Point> hash = new HashSet<Point>();
		
		for(int i=1;i<11;i++) 
			hash.add(Point.testPoint(i));
			
			System.out.println(hash.size());
			
		    hash.forEach(System.out::println);
				
		}
}