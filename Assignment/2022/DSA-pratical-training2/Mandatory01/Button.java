/*
次のような仕様の GUI プログラムを実現するクラス Buttons を作
成してください
1. 次のようなボタンを２つ持つ。　
2. ボタン１は、初期状態として Reset というラベルを持つ。
3. ボタン２は、初期状態として Swap というラベルを持つ。 　
4. Reset というラベルのボタンを押したときには、両方のボタンのラベルが初
期状態に戻る。
5. Swap というラベルのボタンを押したときには、 ボタン１とボタン２のラ
ベルが入れ替わる。
6. さらに、実行を終了するための Close ボタンを持つ
*/

package task01;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Buttons extends Frame implements ActionListener{
	public static void main(String [] args) {
		new Buttons();
}
	private static final int WIDTH = 500;
	private static final int HEIGHT = 400;
	private  Button button1;
	private Button button2; 
	private Button button3;
	
	public Buttons(){
		
		setSize(WIDTH,HEIGHT);
		setLayout(new FlowLayout());
		
		button1 = new Button("Reset");
		add(button1);
		button1.addActionListener(this);
	    button2 = new Button("Swap");
		add(button2);
		button2.addActionListener(this);
		button3 = new Button("Close");
		add(button3);
		button3.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		String commandName = ae.getActionCommand();
		System.out.println(commandName);
		Object source = ae.getSource();
		Button sourceButton = (Button) source;
		System.out.println("is button1:" + (sourceButton == button1) + 
			    ", is button2:" + (sourceButton == button2) + 
		        ", is button3:" + (sourceButton == button3) + 
		        ", label:" + sourceButton.getLabel());
	
		//*Resetというラベルのボタンが押されたときの動作
		if(commandName.equals("Reset")){
			//*button1のラベルを初期状態に戻す。
			button1.setLabel("Reset");
			//*button2のラベルを初期状態に戻す。
			button2.setLabel("Swap");
		}
		//*Swapというラベルのボタンが押されたときの動作
		else if(commandName.equals("Swap")) {
			String button;
			//*変数buttonにbutton1のラベルを保存する。
			button = button1.getLabel();
			//*button1のラベルをbutton2のラベルと同じものに変更する。
			button1.setLabel(button2.getLabel());
			//*button2のラベルを変数buttonに保存しておいたbutton1のラベルと同じものに変更する。
			button2.setLabel(button);
		}
		//*Closeが押されたときの動作
		else if(commandName.equals("Close")) {
			//*実行を終了する。
			System.exit(0);
		}
	}
}