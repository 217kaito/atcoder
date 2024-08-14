/*
必須課題のクラスTimeTableに次のような部品を付け加えたクラスTimeTable2
を作成してください。
1. 指定された曜日の時間割をすべて表示するための ShowAll ボタン
2. ShowAll の結果を表示するための TextArea
１行目が曜日とし、以下 2 行目から各
時限の番号と設定された科目を一行にひとつずつ示すものとしてください。未設
定の場合は「未設定」と表示します。Show ボタンを押すたびに、TextArea 内に
ある既存のテキストはクリアされるようにしてください。
*/


package task02;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Choiceクラス：曜日などを月曜日、火曜日と選択肢を複数登録することができる、登録された選択肢や、それに割り振られた番号を取り出せる
//TextFieldクラス；入力したテキストを表示、表示可能な文字数の決定、表示するテキストの取り出しなどができる
public class TimeTable2 extends Frame
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
	
	// 指定された曜日の時間割をすべて表示するためのTextAreaオブジェクトを参照する変数
	private TextArea AllShow;
	
	// 入力への応答を表示するためのLabelオブジェクトを
	// 参照するための変数
	private Label response;
	
	// 曜日、次元の科目名を入れる配列
	private String table[][];
	
	public TimeTable2() {
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
    	
    	// ShowAllの結果を表示するためのTextArea
    	AllShow = new TextArea();
    	add(AllShow);
    	
    	//科目を設定するためのボタン
        Button SetButton = new Button("Set");
        add(SetButton);
        SetButton.addActionListener(this);
        
        // 表示を指示するボタン
     	Button showButton = new Button("Show");
     	add(showButton);
     	showButton.addActionListener(this);
     	
     	// 指定された曜日の時間割をすべて表示するためのボタン
     	Button ShowAllbutton = new Button("ShowAll");
     	add(ShowAllbutton);
     	ShowAllbutton.addActionListener(this);
     	
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
            // 科目の設定状況を確認
		    else {
			// 応答用のラベルに追加内容を表示
		    	response.setText(Wday + Period + ": " + table[WdayIndex][PeriodIndex]);
		    }
		    
		}

		else if (commandName.equals("Show")) {
			// TextAreaをクリアする
			AllShow.setText("");
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

		else if (commandName.equals("ShowAll")) {
			// 選択された曜日名
			String Wday = (String) WdaySelection.getSelectedItem();
			// 選択された項目の番号
			int WdayIndex = WdaySelection.getSelectedIndex();
		    // TextAreaに選択された曜日名を追加
			AllShow.append(Wday);
			// TextAreaに選択された曜日の時間割を追加
			for(int i=0;i<6;i++)
				// 科目の設定状況を確認
				if(table[WdayIndex][i]!=null) {
					AllShow.append("\n");
					AllShow.append(String.valueOf(i+1) + ":" + table[WdayIndex][i]);
				}
			    // 科目の設定状況を確認
				else {
					AllShow.append("\n");
					AllShow.append(String.valueOf(i+1) + ":" + "未設定");
				}
		    }
		
		
		else if (commandName.equals("Close")) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		// TimeTable オブジェクトの生成
        TimeTable2 ce = new TimeTable2();
	}
}

