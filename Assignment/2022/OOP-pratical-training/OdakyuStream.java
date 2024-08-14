/*
• 小田急の駅ごとの乗降客数などを数年分まとめたファイルがある
• csv形式
(1) 2018年と2021年の乗降客数を比べて、2021年が2018年の60%未満の人数になっている駅をすべて表示せよ
    • 新宿,521160,317845 のような形式で、1行1駅
(2) 2018年と2021年の乗降客数の平均が、20万人以上である駅の数を表示せよ
    • 該当する駅の個数だけ表示すればよい

ファイル
• odakyu_2022_change.csv
• カンマで区切られたファイル形式
• 先頭の行は説明なので読み飛ばす
• 今回は「駅名」「人数(2018)」「人数(2021)」の列が必要
    • 1列目、3列目、5列目
*/

/*
(1): 
参宮橋,15423,8834
梅ヶ丘,33805,19535
玉川学園前,47990,26876
東海大学前,39974,18421
(2): 
3
 */
package oolec9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OdakyuStream {
	
	public static void main(String [] args) {
		System.out.println("(1): ");
		readfigs().stream().filter(s -> (double)s.passengers_2021() / (double)s.passengers_2018() < 0.6).forEach(s -> System.out.println(s.name() + "," + s.passengers_2018() + "," + s.passengers_2021()));
		long num = readfigs().stream().filter(s -> (s.passengers_2021() + s.passengers_2018()) / 2 >= 200000).count();
		System.out.println("(2): ");
		System.out.println(num);
	}

	public static class Station {
	
		String name;
		int passengers_2018;
		int passengers_2021;
	
		Station(String name, int passengers_2018, int passengers_2021){
			this.name = name;
			this.passengers_2018 = passengers_2018;
			this.passengers_2021 = passengers_2021;
		}
		public String name() {
			return name;
		}
	
		public int passengers_2018() {
			return passengers_2018;
		}
	
		public int passengers_2021() {
			return passengers_2021;
		}
	}

	public static List<Station> readfigs() {
		List<Station> ret = new ArrayList<Station>();
		String fname = "odakyu_2022_change.csv";
		String header = null;
		try (BufferedReader in = new BufferedReader(new FileReader(fname))) {
			String line;
			while ((line = in.readLine()) != null) {
				if (header == null) { // 先頭の行だけ読み飛ばす
					header = line;
					continue;
				}
				String[] elms = line.split(",", 0); // 1行をカンマでバラバラに
				// ここでelms使ってStationのオブジェクトを作り、retに追加
				Station s = new Station(elms[0],Integer.parseInt(elms[2]),Integer.parseInt(elms[4]));
				ret.add(s);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return ret;
	}
}

