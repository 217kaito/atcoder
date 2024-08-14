/*
必須課題のクラス RecursiveSearch に次の機能を付け加えたクラス RecursiveSearch2 を作成してください。
探索後に、探索順に加えて探索の深さを頂点の右肩に表示する。文字
の色は、濃い緑（Color.green.darker()) とする。探索の深さは、探索
の親子関係で定義される木における深さで、再帰呼び出しの深さに一
致する。根の深さを０とする。
*/

package task04;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RecursiveSearch2 extends Frame 
    implements ActionListener {
  // フレームの幅と高さ
  private static final int WIDTH = 900;
  private static final int HEIGHT = 700;
    
  // グラフファイルフォルダ名
  private static final String GRAPH_FOLDER = "graph";
    
  // 描画領域の余白
  private static final int LEFT_MARGIN = 100;
  private static final int TOP_MARGIN = 150; // ボタン配置のための余裕
    
  // 頂点描画のための円の半径
  private static final int RADIUS = 12;
    
  // 	頂点の個数
  private int nv;
    
  // 頂点の配列
  private Vertex vertex[];
    
  // ファイル名入力欄
  private TextField fileNameInput;
    
  // 探索の開始頂点番号入力欄
  private TextField startVertexInput;
  
  // 頂点の訪問順の番号を覚えるための変数
  private int count = 1;
  
  // 探索の深さを覚えるための変数
  private int depth = 0;
   
  public RecursiveSearch2() {
    // このフレームの大きさを設定する。
    setSize(WIDTH, HEIGHT);
        
    // 配置の方式をFlowLayout に設定
    setLayout(new FlowLayout());
        
    // ファイル名入力欄
    Label label1 = new Label("Graph file name:");
    add(label1);
        
    fileNameInput = new TextField(10);
    add(fileNameInput);
        
    // グラフ読み込みボタン
    Button loadButton = new Button("Load Graph");
    add(loadButton);
    loadButton.addActionListener(this);
        
    // 探索開始頂点番号入力欄
    Label label2 = new Label("  Start vertex number:");
    add(label2);
        
    startVertexInput = new TextField(2);
    add(startVertexInput);
        
    // 探索実行ボタン
    Button goButton = new Button("Go");
    add(goButton);
    goButton.addActionListener(this);
        
    // 初期化
    Button clearButton = new Button("Clear");
    add(clearButton);
    clearButton.addActionListener(this);
    
    // 隣接頂点の順番を循環的にずらすボタン
    Button RotateButton = new Button("Rotate");
    add(RotateButton);
    RotateButton.addActionListener(this);
        
    // 終了ボタン
    Button closeButton = new Button("Close");
    add(closeButton);
    closeButton.addActionListener(this);
        
    // フレームの可視化
    setVisible(true);
  }
    
  // フレームの描画が必要になったときに呼ばれる。
  // グラフの描画をここで行う。
  @Override
  public void paint(Graphics g) {
    // 辺の描画。各頂点に、その頂点から出る辺の描画を
    // まかせる。
    for (int i = 0; i < nv; i++) {
      vertex[i].drawEdges(g);
    }
        
    // 頂点の描画。各頂点にまかせる。
    for (int i = 0; i < nv; i++) {
      vertex[i].draw(g);
    }
        
    //頂点のから親頂点への辺の描画。各頂点にまかせる。
        
    for (int i = 0; i < nv; i++) {
      if (vertex[i].parent != null) {
        vertex[i].drawBlueArrowToParent(g);
      }
    }
  }
    
  @Override
  public void actionPerformed(ActionEvent ae) {
    String commandName = ae.getActionCommand();
    if (commandName.equals("Load Graph")) {
    	count = 1;
    	depth = 0;
      // グラフの読み込み
      load();
    }
    else if (commandName.equals("Go")) {
    	
    	// 探索の実行
            
      int start = 0;
      // 探索開始頂点番号の取得
      String vertexNumberString = startVertexInput.getText();
      if (!vertexNumberString.equals("")) {
        start = Integer.parseInt(vertexNumberString);
        recursiveSearch(vertex[start]);
      }
    }

    else if (commandName.equals("Clear")) {
    	count = 1;
    	depth = 0;
      // 頂点の状態の初期化
      for (int i = 0; i < nv; i++) {
        vertex[i].clear();
      }
    }
    
    else if (commandName.equals("Rotate")) {
    	rotateNeighbors();
    }
        
    else if (commandName.equals("Close")) 
      System.exit(0);

    // フレームの再描画を要求する。
    repaint();
  }
    
  // グラフの読み込み
  private void load() {
    String fileName = fileNameInput.getText();
    if (fileName.equals("")) return;
        
    File file = new File(GRAPH_FOLDER + File.separator + fileName);
        
    String line;
        
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
            
      try {
        line = br.readLine().trim();
            
        nv = Integer.parseInt(line);
            
        vertex = new Vertex[nv];
        for (int i = 0; i < nv; i++) {
          vertex[i] = new Vertex(i);
        }
            
        for (int i = 0; i < nv; i++) {
          line = br.readLine().trim();
          String s[] = line.split(" ");
          if (s == null ||
              s.length < 2)
            throw new RuntimeException("vertex coordinates missing");
          vertex[i].x = Integer.parseInt(s[0]);
          vertex[i].y = Integer.parseInt(s[1]);
                
          vertex[i].degree = s.length - 2;
          vertex[i].neighbor = new Vertex[vertex[i].degree];
                
          for (int j = 0; j < vertex[i].degree; j++) {
            int neighborIndex = Integer.parseInt(s[2 + j]); 
            vertex[i].neighbor[j] = vertex[neighborIndex];
          }
        }
      } finally {
        br.close();
      }
    }
        
    catch(Exception e) {
      e.printStackTrace();
    }
  }
    
  // グラフ中の探索。開始頂点が与えられる。
  private void recursiveSearch(Vertex vs) {
    Vertex v = vs;
   
    if(!v.visited) {
    	v.visited=true; // 訪問済みのマーク
    	v.label=count++; // 訪問順序
    	v.Label=depth; // 探索の深さ
    	
    	// 訪問済みでないすべてのv.neighborの親をvとし、再帰呼び出しする。
    	for(int i=0;i<v.degree;i++) {
    		if(!v.neighbor[i].visited) {
    			v.neighbor[i].parent=v;
    			depth++;
    			recursiveSearch(v.neighbor[i]);
    			// 再帰呼び出しによってdepthの値が変わってしまっているので、再帰呼び出し前の値に戻す
    			depth=v.Label;
    		}
    	}
    	
    }
      else {
    	  return;
      }
  }
  
  private void rotateNeighbors() {
	  // グラフの各頂点のneighbor配列に格納された隣接頂点の順番を循環的にずらず
	  for(int i = 0;i < vertex.length;i++) {
		  int j = vertex[i].degree-2;
		  Vertex v = vertex[i].neighbor[j+1];
		  for(;j >= 0;j--) {
			  vertex[i].neighbor[j+1] = vertex[i].neighbor[j];
		  }
		  vertex[i].neighbor[0] = v;
	  }
  }
    
  private class Vertex {
    // 頂点番号（配列の添え字と一致）
    private int index;
        
    // 次数（接する辺の本数）
    private int degree;
        
    // 隣接頂点の配列
    private Vertex neighbor[];
        
    // 訪問済みのマーク
    private boolean visited;
        
    // 親の頂点
    private Vertex parent;
        
    // 探索順などを示すラベル
    private int label;
    
    // 探索の深さを示すラベル
    private int Label;
        
    // 描画用の座標
    private int x;
    private int y;
        
    // コンストラクタ
    public Vertex(int index) {
      this.index = index;
    }
        
    // 状態の初期化
        
    private void clear() {
      label = 0;
      Label = 0;
      visited = false;
      parent = null;
    }
        
    // thisの表す頂点の描画
    private void draw(Graphics g) {
      // 円の描画
      // まず、白くクリア
      g.setColor(Color.white);
      g.fillOval(x + LEFT_MARGIN - RADIUS,
                 y + TOP_MARGIN - RADIUS,
                 RADIUS * 2, RADIUS * 2);
            
            
      // もしマークされていれば赤で、そうでなければ黒で描画
      g.setColor(Color.black);
      if (visited) g.setColor(Color.red);
            
      g.drawOval(x + LEFT_MARGIN - RADIUS,
                 y + TOP_MARGIN - RADIUS,
                 RADIUS * 2, RADIUS * 2);
            
      // 円内に、頂点番号を表示
      g.setFont(new Font("HELVETICA", Font.PLAIN, 16));
      int offset = RADIUS /2;
      if (index >= 10) offset = RADIUS * 2 / 3;
      g.drawString(Integer.toString(index),
                   x + LEFT_MARGIN - offset,
                   y + TOP_MARGIN + RADIUS / 2);
            
      // 円の右下に、訪問順を覚えたラベルを表示
      g.setColor(Color.blue);
      g.drawString(Integer.toString(label),
                   x + LEFT_MARGIN + RADIUS  * 4 /3,
                   y + TOP_MARGIN + RADIUS + 1);
      
      // 円の右上に探索の深さを覚えたラベルを表示
      g.setColor(Color.green.darker());
      g.drawString(Integer.toString(Label),
    		  x + LEFT_MARGIN + RADIUS * 4 /3,
    		  y + TOP_MARGIN - 1);
    }
        
    // thisの表す頂点から出る辺の描画
    private void drawEdges(Graphics g) {
      for (int i = 0; i < degree; i++) {
        g.setColor(Color.orange);
                
        g.drawLine(x + LEFT_MARGIN, y + TOP_MARGIN,
                   neighbor[i].x + LEFT_MARGIN,
                   neighbor[i].y + TOP_MARGIN
                   );
      }
    }

    // 親頂点への青い矢印の描画
    public void drawBlueArrowToParent(Graphics g) {
      g.setColor(Color.blue);
      int x1 = x + LEFT_MARGIN;
      int y1 = y + TOP_MARGIN;
      int x2 = parent.x + LEFT_MARGIN;
      int y2 = parent.y + TOP_MARGIN;
      double length = Math.sqrt((double) ((x2 - x1) * (x2 - x1)  + (y2 - y1) * (y2 - y1)));
      double cos = (double) (x2 - x1) / length;
      double sin = (double) (y2 - y1) / length;
      int x1s = x1 + (int) (cos * (double) RADIUS);
      int y1s = y1 + (int) (sin * (double) RADIUS);
      int x2s = x2 - (int) (cos * (double) RADIUS);
      int y2s = y2 - (int) (sin * (double) RADIUS);
      double theta = Math.asin(sin);
      if (x2 < x1) {
        theta = Math.PI - theta;
      }
      double theta1 = theta + 5.0 / 6.0 * Math.PI;
      double theta2 = theta - 5.0 / 6.0 * Math.PI;
      int xa1 = (int) (x2s + (double) RADIUS * Math.cos(theta1));
      int ya1 = (int) (y2s + (double) RADIUS * Math.sin(theta1));
      int xa2 = (int) (x2s + (double) RADIUS * Math.cos(theta2));
      int ya2 = (int) (y2s + (double) RADIUS * Math.sin(theta2));
      g.drawLine(x1s, y1s, x2s, y2s);
      g.drawLine(x2s, y2s, xa1, ya1);
      g.drawLine(x2s, y2s, xa2, ya2);
    }
  }
    
  public static void main(String[] args) {
    // RecursiveSearch2 オブジェクトの生成
    new RecursiveSearch2();
        
    // フレームを可視化する
  }
}
