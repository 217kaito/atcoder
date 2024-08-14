// 窓の一辺の大きさを引数として取る平均フィルタを施すプログラムを libgd を用いて作成せよ．第 1 引数に入力ファイル名，第 2 引数に出力ファイル名，第 3 引数に奇数の窓サイズを取るものとする．

#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in;
  gdImagePtr im,im_new;
  int width,height,i,j,m,n,color,x,x2,pixel;
  //窓の一辺の大きさを表す変数
  int w;
  //画素の平均を保存する変数
  int r,g,b;

  if(argv[1]==NULL||argv[2]==NULL||argv[3]==NULL||!strcmp(argv[1],argv[2])){
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

  //第三引数を整数に返還
  w=atoi(argv[3]);

  //第三引数が偶数だった時のエラー処理
  if(w%2==0){
    printf("窓の一辺の大きさは奇数にしてください\n");
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
      //画素の平均を計算する
      r=0;
      g=0;
      b=0;
      for(n=-w;n<=w;n++){
	for(m=-w;m<=w;m++){
	  //特定の領域の中に入力画像外の領域があった場合、その領域を除いた平均を計算するようにする
	  if(i+n>=0&&i+n<width&&j+m>=0&&j+m<height){
	    pixel=gdImageGetPixel(im,i+n,j+m);
	    r+=gdImageRed(im,pixel);
	    g+=gdImageGreen(im,pixel);
	    b+=gdImageBlue(im,pixel);
	  }
	}
      }    
      x=2*w+1;
      x2=x*x;
      r=r/x2;
      g=g/x2;
      b=b/x2;

      //r,g,b 値から color を割り当て
      color=gdImageColorExact(im_new,r,g,b);

      //im_new の (i,j) におけるピクセル値を color で設定
      gdImageSetPixel(im_new,i,j,color);
    }
  }
  
  gdImageJpeg(im_new,out,-1);

  fclose(in);
  fclose(out);

  return 0;
  
}
