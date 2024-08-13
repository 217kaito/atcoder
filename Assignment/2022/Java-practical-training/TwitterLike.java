/*• TwitterのようなSNSを作ろうと考えている
    • public クラスTwitterLike
• 投稿メッセージをオブジェクトとし、配列で管
理する
    • クラスArticle
    • アクセス修飾子は何もつけなくてよい
• TwitterLikeとArticleの2つのクラスでモデル化する
• この設計方針で実装してみよう

Articleクラスへの要求
• 投稿は投稿者名と文章（いずれも文字列）が与え られたらコンストラクタで作られる
    • コンストラクタにString引数2つ
• 投稿には投稿時間が自動的に記憶される
    • インスタンスが作られるときに、その時刻を記憶するよ うに作る
• 投稿には「イイね」の数が付く（インスタンスが
イイねの数を覚えている）
• void iine()というメソッドがあり、呼ばれると「イ
イね」が1増える
• String toString()メソッドがあり、「名前, 投稿日時, 文章, イイねの数」のようにカンマでつないだ文字列を返す
    • 「現場猫, 2022/6/2 17:40, 何を見てヨシ！って言ったんですか？, 3」みたいに

TwitterLikeクラスへの要求
mainで、Articleクラスのテストをする。テスト内容は以下の通り。
• 大きさ3の配列を作り、3個の投稿に相当するオブジェクトを入れる
• 3個の投稿に適当に「イイね」を増やす
• 3個の投稿を順番に表示する
    • Article.toString()を呼んでその文字列を表示
• 3個の投稿の名前、文章、イイねの数は適当に決める
• 今回の課題は、TwitterLikeクラスは単なるArticleクラスのテスト用

要求される実装方針
• Articleクラスの外（今回の場合、TwitterLikeクラス）から呼ばれるメソッドはpublicとしよう
    • コンストラクタも該当しますね
• Articleクラスのフィールドはprivateとしよう
• Articleクラスはアクセス修飾子なし
• TwitterLikeクラスはpublicクラス
    • なので、提出ファイルはTwitterLike.java*/

// A,2022/06/07 13:20;08,あ,1
//B,2022/06/07 13:20;08,い,1
//C,2022/06/07 13:20;08,う,1

package javalec6;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TwitterLike {
public static void main(String args[]) {
	String [] a = {"あ","い","う"};
	String [] b = {"A","B","C"};
	Article [] c = new Article[3];
	
	for(int i=0;i<3;i++) {
	c[i] = new Article(a[i],b[i]);
	c[i].iine();
	System.out.println(c[i].toString());
	}
}
}

class Article{
	private String st;
	private String name;
	private int iine=0;
	private LocalDateTime nowDateTime = LocalDateTime.now();
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm;ss");
	private String time;
	
	public Article(String ist, String iname) {
		st=ist;
		name=iname;
	  time = nowDateTime.format(timeFormat);
		
	}
	
	public void iine(){
		iine++;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name);
		sb.append(",");
		sb.append(time);
		sb.append(",");
		sb.append(st);
		sb.append(",");
		sb.append(iine);
		
		String result = sb.toString();
		
		return (result);
	}
}