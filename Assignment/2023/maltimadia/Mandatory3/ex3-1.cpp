// libgd を用いて画像を開き，OpenCV の imwrite 関数を用いて画像を保存するプログラムを書け．
// 第 1 引数で入力ファイル名，第 2 引数で出力ファイル名を指定する．ただし，24bit カラー画像を入力とし，対応するフォーマットは JPEG のみで良い．

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <cstdio>
#include <iostream>
#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

using namespace cv;
using namespace std;

int main(int argc, const char **argv){

  FILE *out,*in;
  gdImagePtr im;
  int width,height,pixel;
  unsigned char r,g,b;

  if(argv[1]==NULL||argv[2]==NULL||!strcmp(argv[1],argv[2])){
    printf("argument error\n");
    exit(-1);
  }

  //第一引数で指定されたファイルを読み出し用にオープン
  if((in=fopen(argv[1],"r"))==NULL){
    printf("file open error for %s\n",argv[1]);
    exit(-1);
  }

  //im に画像を読み込み
  im=gdImageCreateFromJpeg(in);

  //入力画像のサイズを取得
  width=gdImageSX(im);
  height=gdImageSY(im);

  Mat src(height,width,CV_8UC3);

  for(int y=0;y<height;y++){
    for(int x=0;x<width;x++){
      Vec3b bgr;

      pixel=gdImageGetPixel(im,x,y);
      r=gdImageRed(im,pixel);
      g=gdImageGreen(im,pixel);
      b=gdImageBlue(im,pixel);

      bgr[0]=b;
      bgr[1]=g;
      bgr[2]=r;

      src.at<Vec3b>(y,x)=bgr;
    }
  }

  imwrite(argv[2],src);
  fclose(in)
  return 0;
}

