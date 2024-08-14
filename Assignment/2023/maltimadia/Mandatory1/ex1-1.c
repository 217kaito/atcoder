// 入力された画像の縦と横を入れ換えた画像をファイルとして出力するプログラムを作成せよ．ただし，第 1 引数で入力ファイル名を，第 2 引数で出力ファイル名を指定できるようにすること．

#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in;
  gdImagePtr im,im_new;
  int width,height,i,j,color,color2,r,r2,g,g2,b,b2,pixel,pixel2;

  if(argv[1]==NULL||argv[2]==NULL||!strcmp(argv[1],argv[2])){
    printf("argument error\n");
    exit(-1);
  }

  //第一引数で指定されたファイルを読み出し用にオープン
  if((in=fopen(argv[1],"r"))==NULL){
    printf("file open error for %s\n",argv[1]);
    exit(-1);
  }
  //第二引数で指定されたファイルを書き出し用にオープン
  if((out=fopen(argv[2],"wb"))==NULL){
    printf("file open error for %s\n",argv[2]);
    exit(-1);
  }

  //im に画像を読み込み
  im = gdImageCreateFromJpeg(in);

  //入力画像のサイズを取得
  width=gdImageSX(im);
  height=gdImageSY(im);

  //新しい画像を用意
  im_new= gdImageCreateTrueColor(width,height);

  for(i=0;i<width;i++){
    for(j=0;j<height;j++){
      
      //im の (i,j)(j,i) におけるカラーインデックスの取得
      pixel=gdImageGetPixel(im,i,j);
      pixel2=gdImageGetPixel(im,j,i);
      
      //im の (i,j)(j,i) における r,g,b の値を取得
      r=gdImageRed(im,pixel);
      g=gdImageGreen(im,pixel);
      b=gdImageBlue(im,pixel);
      r2=gdImageRed(im,pixel2);
      g2=gdImageGreen(im,pixel2);
      b2=gdImageBlue(im,pixel2);

      //r,g,b 値から color を割り当て
      color=gdImageColorExact(im_new,r,g,b);
      color2=gdImageColorExact(im_new,r2,g2,b2);

      //im_new の (i,j) におけるピクセル値を color で設定
      gdImageSetPixel(im_new,i,j,color2);
      gdImageSetPixel(im_new,j,i,color);
    }
  }
  
  gdImageJpeg(im_new,out,-1);

  fclose(in);
  fclose(out);

  return 0;
  
}
