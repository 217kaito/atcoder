// トーンカーブによる補正を画像の一部の領域に対してのみ機能するようにプログラムを修正せよ．第 3 引数以降で，補正を行う領域の開始座標，縦幅，横幅を入力すること．

//第3、4、5、6引数でそれぞれ開始x座標、開始y座標、縦幅、横幅を入力する
#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in;
  gdImagePtr im,im_new;
  int width,height,i,j,color,r,g,b,pixel,R,G,B,x,y,w,h;

  if(argv[1]==NULL||argv[2]==NULL||argv[3]==NULL||argv[4]==NULL||argv[5]==NULL||argv[6]==NULL||!strcmp(argv[1],argv[2])){
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

  //第三引数以降を整数に変換
  x=atoi(argv[3]);
  y=atoi(argv[4]);
  w=atoi(argv[5]);
  h=atoi(argv[6]);

  //im に画像を読み込み
  im = gdImageCreateFromJpeg(in);

  //入力画像のサイズを取得
  width=gdImageSX(im);
  height=gdImageSY(im);

  //指定された領域が元の画像のサイズ外を含む場合はエラー処理
  if(x>width||x<0||y>height||y<0||x+w>width||w<0||y+h>height||h<0){
    printf("argument error\n");
    exit(-1);
  }

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

      //r,g,b 値から color を割り当て
      color=gdImageColorExact(im_new,r,g,b);

      //im_new の (i,j) におけるピクセル値を color で設定
      gdImageSetPixel(im_new,i,j,color);
    }
  }


  for(i=x;i<=x+w;i++){
    for(j=y;j<=y+h;j++){
      
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
