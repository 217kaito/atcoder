// OpenCV を用いて，USB カメラからの入力に対して何らかの文字列を描画した画像を画面に表示するプログラムを作成せよ．

// "cool"という文字を描画
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

    putText(frame,"cool",Point(50,50),FONT_HERSHEY_SIMPLEX,2,Scalar(255,0,0),2,CV_AA);

    if(waitKey(5)=='q') 
      break;
    imshow("test",frame);
  }

  return 0;
}
