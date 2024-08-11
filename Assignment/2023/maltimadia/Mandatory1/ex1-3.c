// 画像全体に対して，傾き 45 度の直線以外の何らかのトーンカーブによる補正を施し，結果をファイルとして出力するプログラムを作成せよ．ただし，RGB 全ての成分に対して同じトーンカーブを適用すること．これまでの課題と同様，第 1 引数で入力ファイル名を，第 2 引数で出力ファイル名を指定できるようにすること．

#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in;
  gdImagePtr im,im_new;
  int width,height,i,j,color,r,g,b,pixel,R,G,B;

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
      
      //im の (i,j) におけるカラーインデックスの取得
      pixel=gdImageGetPixel(im,i,j);
      
      //im の (i,j) における r,g,b の値を取得
      r=gdImageRed(im,pixel);
      g=gdImageGreen(im,pixel);
      b=gdImageBlue(im,pixel);

      //y=3xのトーンカーブをかける
      R=3*r;
      G=3*g;
      B=3*b;
      if(R>255){
	R=255;
      }
      if(G>255){
	G=255;
      }
      if(B>255){
	B=255;
      }


      //r,g,b 値から color を割り当て
      color=gdImageColorExact(im_new,R,G,B);

      //im_new の (i,j) におけるピクセル値を color で設定
      gdImageSetPixel(im_new,i,j,color);
    }
  }
  
  gdImageJpeg(im_new,out,-1);

  fclose(in);
  fclose(out);

  return 0;
  
}
