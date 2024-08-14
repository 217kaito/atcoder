// OpenCV を用いて何らかのアフィン変換あるいは射影変換を実行するプログラムを作成せよ．
// ただし，第 1 引数で入力ファイル名，第 2 引数で出力ファイル名を指定し，何も行わないという処理は不適当とする．
// プログラムの動作に関する仕様をコメントとしてソースコードの最初の箇所に記述すること．

//画像を縮小、回転させる
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <stdio.h>
using namespace cv;
using namespace std;
int main(int argc, const char **argv){

  double a,b,tx,c,d,ty;
 
  if(argv[1]==NULL||argv[2]==NULL||!strcmp(argv[1],argv[2])){
    printf("argument error\n");
    exit(-1);
  }

  a=0.3535339;
  b=0.3535339;
  tx=89.51456544;
  c=-0.35355339;
  d=0.35355339;
  ty=143.43592168;

  Mat src=imread(argv[1],CV_LOAD_IMAGE_COLOR);

  Mat mat=(Mat_<float>(2,3)<<a,b,tx,c,d,ty);

  Mat dst = Mat::zeros(src.rows,src.cols,src.type());
  
  warpAffine(src,dst,mat,dst.size(),INTER_LINEAR);

  imwrite(argv[2],dst);

  return 0;
}
