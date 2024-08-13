/*
次のような仕様の 図形描画プログラムを実現するクラス Figures を作成してくださ
い。

1. 終了のための Close ボタンの他に、ボタンを３つ持つ。これらのボタンの名
前 (ラベル）は Line、Rectangle、Oval とする。ボタンはそれぞれ、線分、
長方形、楕円に対応していて、ボタンを押すと対応した図形が表示される。
（表示される図形は、常時ひとつまでとする。）長方形と楕円の場合、中は
塗りつぶさないものとする。
2. 表示される図形の大きさと位置は、座標値 x、y、幅 w、および高さ h の４
つの整数値をテキスト入力欄にタイプすることによって指定するものとす
る。線分の場合は、点 (x, y) と 点 (x + w, y + h) を端点とする。長方形
の場合は、点 (x, y) が両座標が最小の頂点であり、辺が軸に平行で幅が w
、高さが h であるようなものとする。楕円の場合は、これらの値で指定さ
れる長方形に内接するものとする。
3. 色名を選択要素とする選択ボックスをもち、 それによって選ばれた色で図
形は表示されるものとする。少なくとも、Red, Blue, Green の３色の選択
が可能とする。
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

// 各図形を表示するためのボタンが押されたら、その図形に対応する論理変数Aがtrueになる。
// そのことでpaint内の対応するif文内が実行されることで図形が表示される。
// そのif文で論理変数Aがfalseになることで図形の描写がボタンが押されるごとに切り替わる。

public class Figures extends Frame 
	implements ActionListener {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	// 表示する図形を判断するための論理変数
	private boolean drawline;
	private boolean drawrectangle;
	private boolean drawoval;
	
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

	public Figures() {
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
		if(ColorSelection.getSelectedItem().equals("Red"))
			g.setColor(Color.red);
		if(ColorSelection.getSelectedItem().equals("Green"))
			g.setColor(Color.green);
        if(ColorSelection.getSelectedItem().equals("Blue"))
			g.setColor(Color.blue);

		if(drawline) {
			// 線分
			g.drawLine(x, y, x + width, y + height);
			drawline = false;
		}
		if(drawoval) {
			// 楕円
			g.drawOval(x, y, width, height);
			drawoval = false;
		}
		if(drawrectangle) {
			// 長方形
			g.drawRect(x, y, width, height);
			drawrectangle = false;	
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
		else if(commandName.equals("Close")){
			System.exit(0);
		}
		// フレームの再描画を要求する。
		repaint();
	}
	
	public static void main(String[] args) {
		// Figure オブジェクトの生成
		new Figures();
		
	}
}
