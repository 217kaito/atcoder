/*
次のような仕様の個人時間割管理シ
ステムを実現するクラス TimeTable を作成してください。

次の部品を持つ。また、各部品の左側には必要に応じて説明のラベルを付す。
1. 曜日を指定するための選択欄
2. 時限を指定するための選択欄
3. 科目名を入力するためのテキスト入力欄
4. 科目を設定するための Set ボタン： このボタンを押すと、指定された曜日
の指定された時限に科目名テキスト欄に入力された科目を設定する。ただ
し、科目名テキスト欄が空である場合は、指定された曜日の指定された時
限の科目が未設定の状態になるものとする。
5. 科目を表示するための Show ボタン： このボタンを押すと、指定された曜
日の指定された時限に設定された科目名が科目名テキスト入力欄に表示さ
れる。その時限の科目が未設定の場合は「未設定」と表示される。表示場
所が、別に用意されたラベルではないことに注意する。
6. 実行を終了するための　 Close ボタン
*/

package task02;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Choiceクラス：曜日などを月曜日、火曜日と選択肢を複数登録することができる、登録された選択肢や、それに割り振られた番号を取り出せる
//TextFieldクラス；入力したテキストを表示、表示可能な文字数の決定、表示するテキストの取り出しなどができる
public class TimeTable extends Frame
implements ActionListener{
	
    // 	フレームの幅と高さを表す定数
	private static final int WIDTH = 500;
	private static final int HEIGHT = 400;
	
	// 曜日名を選択するためのChoiceオブジェクトを参照する変数
	private Choice WdaySelection;
	
	// 時限名を選択するためのChoiceオブジェクトを参照する変数
	private Choice PeriodSelection;
	
	// 科目名を入力するためのTextFieldオブジェクトを参照する変数
	private TextField SubjectInput;
	
	// 入力への応答を表示するためのLabelオブジェクトを
	// 参照するための変数
	private Label response;
	
	// 曜日、次元の科目名を入れる配列
	private String table[][];
	
	public TimeTable() {
		// このフレームの大きさを設定する。
		setSize(WIDTH, HEIGHT);
				
	    // 配置の方式をFlowLayout に設定
		setLayout(new FlowLayout());
		
		//Choiceオブジェクトの生成と追加
		WdaySelection = new Choice();
		add(WdaySelection);
		
		//曜日名を選択肢として追加
        WdaySelection.addItem("Sunday");
        WdaySelection.addItem("Monday");
        WdaySelection.addItem("Tuesday");
        WdaySelection.addItem("Wedenesday");
        WdaySelection.addItem("Thursday");
        WdaySelection.addItem("Friday");
        WdaySelection.addItem("Saturday");
        
        //Choiceオブジェクトの生成と追加
        PeriodSelection = new Choice();
        add(PeriodSelection);
        
        //時限を選択肢として追加
        PeriodSelection.addItem("1");
        PeriodSelection.addItem("2");
        PeriodSelection.addItem("3");
        PeriodSelection.addItem("4");
        PeriodSelection.addItem("5");
        PeriodSelection.addItem("6");
        
        // コメント入力欄のTextField（3０文字分）
        SubjectInput = new TextField(30);
    	SubjectInput.setText("ここにコメントを入力してください");
    	add(SubjectInput);
    	
    	//科目を設定するためのボタン
        Button SetButton = new Button("Set");
        add(SetButton);
        SetButton.addActionListener(this);
        
        // 表示を指示するボタン
     	Button showButton = new Button("Show");
     	add(showButton);
     	showButton.addActionListener(this);
     	
        // 応答を表示するためのラベル（長さを十分にとっておく）
     	response = new Label("This is the response area.                      ");
     	add(response);
     	
        // 終了ボタン
     	Button closeButton = new Button("Close");
     	add(closeButton);
    	closeButton.addActionListener(this);
        

		// コメントを憶える配列オブジェクトを生成
		table = new String[12][12];
		
		//　フレームを可視化する
		setVisible(true);

	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		String commandName = ae.getActionCommand();
		// コマンド名によってそれぞれの処理をする。
		
		if (commandName.equals("Set")) {
			// 選択された曜日名
			String Wday = (String) WdaySelection.getSelectedItem();
           	// 選択された項目の番号
			int WdayIndex = WdaySelection.getSelectedIndex();
			// 選択された時限
			String Period = (String) PeriodSelection.getSelectedItem();
            // 選択された項目の番号
			int PeriodIndex = PeriodSelection.getSelectedIndex();
			// コメント入力欄の内容を配列に憶える
		    table[WdayIndex][PeriodIndex] = SubjectInput.getText();
		    // 科目の設定状況を確認
			if("".equals(SubjectInput.getText())) {
		    // 応答用のラベルに追加内容を表示
				response.setText(Wday + Period + ": " + "未設定" );
			 }
			else {
			// 応答用のラベルに追加内容を表示
		    	response.setText(Wday + Period + ": " + table[WdayIndex][PeriodIndex]);
		    }
		}

		else if (commandName.equals("Show")) {
			// 選択された曜日名
			String Wday = (String) WdaySelection.getSelectedItem();
			// 選択された項目の番号
			int WdayIndex = WdaySelection.getSelectedIndex();
			// 選択された時限
		    String Period = (String) PeriodSelection.getSelectedItem();
			// 選択された項目の番号
			int PeriodIndex = PeriodSelection.getSelectedIndex();
			 // 科目の設定状況を確認
			 if("".equals(SubjectInput.getText())) {
			 // 応答用のラベルに追加内容を表示
				 response.setText(Wday + Period + ": " + "未設定" );
			 }
			// 科目の設定状況を確認
			 else {
			// 応答用のラベルに追加内容を表示
				 response.setText(Wday + Period + ": " + table[WdayIndex][PeriodIndex]);
			 }
		}

		else if (commandName.equals("Close")) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		// TimeTable オブジェクトの生成
        TimeTable ce = new TimeTable();
	}
}

