// OpenCV を用いて，USB カメラからの入力に対して何らか画像処理を施した結果を画面に表示するプログラムを作成せよ．

// カメラからキャプチャした映像をリアルタイムでグレースケールに変換する
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
using namespace cv;
using namespace std;
int main(int argc, const char **argv){
  VideoCapture cap(0);
  if(!cap.isOpened()) return -1;
  namedWindow("test");
  Mat frame;
  while(1){
    cap >> frame; 

    Mat dst,dst_y;
    Mat gry;
    cvtColor(frame,gry,CV_BGR2GRAY);
    char x[3][3] = {{-1,0,1},{-1,0,1},{-1,0,1}};
    char y[3][3] = {{1,0,1},{0,0,0},{-1,-2,-1}};
    Mat k_x = Mat(3, 3, CV_8S, x);
    Mat k_y = Mat(3, 3, CV_8S, y);
    filter2D(gry, dst, CV_8U,k_x);
    filter2D(gry, dst_y, CV_8U, k_y);

    if(waitKey(5)=='q') 
      break;
    imshow("test",gry);
  }

  return 0;
}
