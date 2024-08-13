/*
• 「*」で囲むEmphasizeというクラス
• 「100年に一度の」をくっつけるCentury というクラス
• 「記憶に残る」をくっつけるMemorable というクラスを作る。
• mainで組み合わせてテキストを表示させる
*/

/*
ワイン
*ワイン*
記憶に残るワイン
記憶に残る*100年に一度のワイン*
*/

package oolec8;

abstract class Message {
	abstract String message();
};

class SimpleText extends Message{
	String text;
	SimpleText(String s) {
		text = s;
	}
	String message() {
		return text;
	}
}

class Decoration extends Message{
	Message content;
	Decoration(Message m) {
		content = m;
	}
	String message() {
		return content.message();
	}
}

class Emphasize extends Decoration{
	Emphasize(Message m) {
		super(m);
	}
	String message() {
		return "*" + super.message() + "*";
	}
}

class Century extends Decoration{
	Century(Message m) {
		super(m);
	}
	String message() {
		return "100年に一度の" + super.message();
	}
}

class Memorable extends Decoration{
	Memorable(Message m) {
		super(m);
	}
	String message() {
		return "記憶に残る" + super.message();
	}
}


public class DecoratorTest {
	public static void main(String[] args) {
		Message m = new SimpleText("ワイン");
		System.out.println(m.message());

		Message t1 = new Emphasize(m);
		System.out.println(t1.message()); // これで2行目の表示が出るはず

		// 3行目、4行目も表示するコードを書こう
		Message t2 = new Memorable(m);
		System.out.println(t2.message());
		
		Message t3 = new Memorable(new Emphasize(new Century(m)));
		System.out.println(t3.message());
	}
}
