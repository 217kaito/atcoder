// OpenCV を用いて何らかの空間フィルタを実行するプログラムを作成せよ．ただし，第 1 引数で入力ファイル名，第 2 引数で出力ファイル名を指定し，
// 何も行わないという処理は不適当とする．プログラムの動作に関する仕様をコメントとしてソースコードの最初に記述すること．

//画像をグレイ化する
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <stdio.h>
using namespace cv;
using namespace std;
int main(int argc, const char **argv){
 
  if(argv[1]==NULL||argv[2]==NULL||!strcmp(argv[1],argv[2])){
    printf("argument error\n");
    exit(-1);
  }

  Mat src=imread(argv[1],CV_LOAD_IMAGE_COLOR);
  Mat dst,dst_y;
  Mat gry;
  cvtColor(src,gry,CV_BGR2GRAY);
  char x[3][3] = {{-1,0,1},{-1,0,1},{-1,0,1}};
  char y[3][3] = {{1,0,1},{0,0,0},{-1,-2,-1}};
  Mat k_x = Mat(3, 3, CV_8S, x);
  Mat k_y = Mat(3, 3, CV_8S, y);
  filter2D(gry, dst, CV_8U,k_x);
  filter2D(gry, dst_y, CV_8U, k_y);

  imwrite(argv[2],gry);

  return 0;
}
