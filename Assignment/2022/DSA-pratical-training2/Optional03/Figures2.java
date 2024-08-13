/*
必須課題のクラス Figures に次のような部品を付け加えたクラス Figures2 を
作成してください。
1. Add ボタン。このボタンを押すと、現在表示している図形を記憶する。
2. ShowAll ボタン。このボタンを押すと、これまでに Add ボタンで記憶した
図形がすべて表示される。
細かい仕様は以下の通りです。
1. 通常モードと ShowAll モードのふたつのモードが存在する。
2. 通常モードでは、必須３の仕様に基づいてただひとつの図形の描画ができる。
3. 通常モードで Add ボタンを押すと、そのとき描画されている図形が記憶さ
れる。
4. 通常モードで ShowAll ボタンを押すと、ShowAll モードに移行する。
5. ShowAll モードでは、記憶された図形が記憶された順に描画される。（描画
の順番は、重なりのあるときの結果に影響する。）
6. ShowAll モードで、図形描画のボタンを押すと、通常モードに戻り、その
上で押されたボタンの処理が行われる。
7. ShowAll モードで Add ボタンを押したときは何も起きない。
8. ShowAll モードで ShowAll ボタンを押したときには通常モードに戻る。
*/

package task03;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// 各図形を表示するためのボタンが押されたら、その図形に対応する論理変数Aがtrueになる。
// そのことでpaint内の対応するif文内が実行されることで図形が表示される。
// そのif文で論理変数Aがfalseになることで図形の描写がボタンが押されるごとに切り替わる。

public class Figures2 extends Frame 
	implements ActionListener {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	// 図形を記憶するためのリスト
	private ArrayList<Integer> list = new ArrayList<>();
	
	// ShowAllで表示する図形の色を判断するための論理変数
	private boolean red=false;
	private boolean green=false;
	private boolean blue=false;
	
	// 表示する図形を判断するための論理変数
	private boolean drawline;
	private boolean drawrectangle;
	private boolean drawoval;
	
	// 現在表示されている図形を判断するための整数
	//(表示されている図形がないときは0、線分のときは1、長方形の時は2、楕円の時は3)
	private int figure = 0;
	
	// 現在表示されている図形の色を判断するための整数
	//(表示されている図形がないときは0、赤色のときは1、緑色の時は2、青色の時は3)
	private int color = 0;
	
	// モードを判断するための論理変数
	private boolean ShowAll=false;
	
	private int x = 50; //デフォルトのx座標
	private int y = 100; //デフォルトのy座標
	private int width = 50;  // デフォルトの幅
    private int height = 50;  // デフォルトの高さ
	
	// 色を選択するためのChoiceオブジェクトを参照する変数
	private Choice ColorSelection;
	
	// 図形の座標値、幅と高さの入力欄を参照する変数
	private TextField xField;
	private TextField yField;
	private TextField wField;
	private TextField hField;

	public Figures2() {
		// このフレームの大きさを設定する
		setSize(WIDTH, HEIGHT);
		
		// 配置の方式をFlowLayout に設定する
		setLayout(new FlowLayout());
		
		// Choiceオブジェクトの生成と追加
		ColorSelection = new Choice();
		add(ColorSelection);
		
		// 色を選択肢を追加
		ColorSelection.addItem("Red");
		ColorSelection.addItem("Green");
		ColorSelection.addItem("Blue");
		
		// 図形の座標、幅と高さを指定する入力欄のオブジェクトを生成して配置する
		Label label = new Label("x:");
		label.setVisible(true);
		add(label);
	    xField = new TextField(10);
	    add(xField);
	    
	    label = new Label("y:");
		label.setVisible(true);
		add(label);
	    yField = new TextField(10);
	    add(yField);
	    
	    label = new Label("w:");
		label.setVisible(true);
		add(label);
	    wField = new TextField(10);
	    add(wField);
	    
	    label = new Label("h:");
		label.setVisible(true);
		add(label);
	    hField = new TextField(10);
	    add(hField);
	    
		// 線分を表示するボタン
		Button lineButton = new Button("Line");
		add(lineButton);
		lineButton.addActionListener(this);
		
		// 長方形を表示するボタン
		Button rectangleButton = new Button("Rectangle");
		add(rectangleButton);
		rectangleButton.addActionListener(this);
		
		// 楕円を表示するボタン
		Button ovalButton = new Button("Oval");
		add(ovalButton);
		ovalButton.addActionListener(this);
		
		// 現在表示している図形を記憶するボタン
		Button addButton = new Button("Add");
		add(addButton);
		addButton.addActionListener(this);
		
		// これまでにAddボタンで記憶した図形をすべて表示するボタン
		Button showallButton = new Button("ShowAll");
		add(showallButton);
		showallButton.addActionListener(this);
		
		// 終了ボタン
		Button closeButton = new Button("Close");
		add(closeButton);
		closeButton.addActionListener(this);
		
		// フレームを可視化する
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		// 入力欄が記入されていれば、座標、幅と高さをそこから設定する
		// trimは、文字列の両端にある空白文字を（あるだけ）削除するメソッドである
		String xString = xField.getText().trim();
			if(!xString.equals(""))
				x = Integer.parseInt(xString);
				
		String yString = yField.getText().trim();
			if(!yString.equals(""))
			    y= Integer.parseInt(yString);
				
		String widthString = wField.getText().trim();
		     if (!widthString.equals(""))
				width = Integer.parseInt(widthString);
							
		String heightString = hField.getText().trim();
			 if (!heightString.equals(""))
				height = Integer.parseInt(heightString);		
			 
	    // 選択された色
		if(ColorSelection.getSelectedItem().equals("Red")) {
			g.setColor(Color.red);
			color = 1;
		}
		else if(ColorSelection.getSelectedItem().equals("Green")) {
			g.setColor(Color.green);
			color = 2;
		}
		else if(ColorSelection.getSelectedItem().equals("Blue")) {
			g.setColor(Color.blue);
			color = 3;
        }

		if(drawline) {
			// 線分
			
			if(red==true)
				g.setColor(Color.red);
			else if(green==true)
				g.setColor(Color.green);
			else if(blue==true)
				g.setColor(Color.blue);
			
			g.drawLine(x, y, x + width, y + height);
			drawline = false;
			figure = 1;
			
			red=false;
			green=false;
			blue=false;

		}
		if(drawoval) {
			// 楕円
			
			if(red==true)
				g.setColor(Color.red);
			else if(green==true)
				g.setColor(Color.green);
			else if(blue==true)
				g.setColor(Color.blue);
			
			g.drawOval(x, y, width, height);
			drawoval = false;
			figure = 3;
			
			red=false;
			green=false;
			blue=false;
		}
		if(drawrectangle) {
			// 長方形
			
			if(red==true)
				g.setColor(Color.red);
			else if(green==true)
				g.setColor(Color.green);
			else if(blue==true)
				g.setColor(Color.blue);
			
			g.drawRect(x, y, width, height);
			drawrectangle = false;
			figure = 2;
			
			red=false;
			green=false;
			blue=false;
		}
	}
	
    @Override
	public void actionPerformed(ActionEvent ae) {
		String commandName = ae.getActionCommand();
		if (commandName.equals("Line")) {
			drawline = true;
		}
		else if (commandName.equals("Rectangle")) {
		    drawrectangle = true;
		}
		else if (commandName.equals("Oval")){
			drawoval = true;
		}
		else if (commandName.equals("Add")){
			if(ShowAll != true) {
				// 記憶する図形を再び描画するために必要な値を記憶する。
				list.add(figure);
				list.add(color);
				list.add(x);
				list.add(y);
				list.add(width);
				list.add(height);
			}
		}
		else if (commandName.equals("ShowAll")) {
			if(ShowAll != true) {
				ShowAll = true;
				// 変数sizeにlistの要素数を記憶する。
				int size = list.size();
				
				for(int i = 0 ; i < size/6 ; i++ ) {
					if(list.get(i*6+1)==1)
						red = true;
					else if(list.get(i*6+1)==2)
				    	green = true;
					else if(list.get(i*6+1)==3)
				    	blue = true;
				    
				    x = list.get(i*6+2);
				    y = list.get(i*6+3);
				    width = list.get(i*6+4);
				    height = list.get(i*6+5);
				    
				    if(list.get(i*6)==1)
						drawline = true;
				    else if(list.get(i*6)==2)
						drawoval = true;
				    else if(list.get(i*6)==3)
						drawrectangle = true;
				}
				
			}
			else
				ShowAll = false;
		}
		else if(commandName.equals("Close")){
			System.exit(0);
		}
		// フレームの再描画を要求する。
		repaint();
	}
	
	public static void main(String[] args) {
		// Figure2 オブジェクトの生成
		new Figures2();
		
	}
}