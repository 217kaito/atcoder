// SSD を用いてテンプレートマッチングを行うプログラムを作成せよ．マッチングの判断基準としては，輝度値から求められる 
// SSD をそのまま用いても良いし，RGB 値を用いて得られた SSD を足し合わせた値を用いても良い．
// 第 1 引数で検索対象の画像を，第 2 引数でテンプレート画像を，第 3 引数で出力画像を，第 4 引数で閾値を指定可能とし，
// マッチ結果を画像中に四角い枠として書き込み，第 3 引数で指定されるファイルに JPEG 画像として出力すること．
// マッチ結果としては閾値以下の全ての座標を採用し，SSD が最小となる座標については他と異なる色で枠を掛くこと．
// また，テンプレート画像の方が対象画像よりも大きい場合にはその旨を表示し，プログラムが終了するようにすること．
// libgd は領域外参照を行う場合に 0 を返すという性質を利用しても良い．

#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in,*in2;
  gdImagePtr im,im2,im_new;
  int width,height,width2,height2,i,j,color,r,r2,g,g2,b,b2,pixel,pixel2,x,y,black,red;
  double threshold,SSD,SSD_MIN,Y,Y2;
  int s_MIN[1][2];

  if(argv[1]==NULL||argv[2]==NULL||argv[3]==NULL||argv[4]==NULL||!strcmp(argv[1],argv[3])){
    printf("argument error\n");
    exit(-1);
  }

  //第一引数で指定されたファイルを読み出し用にオープン
  if((in=fopen(argv[1],"r"))==NULL){
    printf("file open error for %s\n",argv[1]);
    exit(-1);
  }
  //第二引数で指定されたファイルを読み出し用にオープン
  if((in2=fopen(argv[2],"r"))==NULL){
    printf("file open error for %s\n",argv[2]);
    exit(-1);
  }
  //第三引数で指定されたファイルを書き出し用にオープン
  if((out=fopen(argv[3],"wb"))==NULL){
    printf("file open error for %s\n",argv[3]);
    exit(-1);
  }

  //入力された閾値を数字に変換
  threshold=atof(argv[4]);

  //配列s_MINを初期化
  memset(s_MIN,-1,sizeof(s_MIN));
  
  //im,im2 に画像を読み込み
  im = gdImageCreateFromJpeg(in);
  im2 = gdImageCreateFromJpeg(in2);
  //入力画像のサイズを取得
  width=gdImageSX(im);
  height=gdImageSY(im);
  width2=gdImageSX(im2);
  height2=gdImageSY(im2);

  
  //テンプレート画像の方が対象画像よりも大きい場合のエラー処理
  if(width<width2||height<height2){
    printf("テンプレート画像の方が対象画像より大きいです\n");
    exit(-1);
  }

  //新しい画像を用意
  im_new= gdImageCreateTrueColor(width,height);

  //色の作成
  black=gdImageColorAllocate(im_new,0,0,0);
  red=gdImageColorAllocate(im_new,255,0,0);

  //SSDの最小値を初期化
  SSD_MIN=-1;
  
  for(x=0;x<width;x++){
    for(y=0;y<height;y++){
      SSD=0;

      //im のカラーインデックスの取得
      pixel=gdImageGetPixel(im,x,y);
      //imのr,g,b の値を取得
      r=gdImageRed(im,pixel);
      g=gdImageGreen(im,pixel);
      b=gdImageBlue(im,pixel);

      //r,g,b 値から color を割り当て
      color=gdImageColorExact(im_new,r,g,b);

      //im_new の (x,y) におけるピクセル値を color で設定
      gdImageSetPixel(im_new,x,y,color);

      for(i=0;i<width2;i++){
	for(j=0;j<height2;j++){

	  //im のカラーインデックスの取得
	  pixel=gdImageGetPixel(im,x+i,y+j);
	  //imのr,g,b の値を取得
	  r=gdImageRed(im,pixel);
	  g=gdImageGreen(im,pixel);
	  b=gdImageBlue(im,pixel);
      
	  //im2 のカラーインデックスの取得
	  pixel2=gdImageGetPixel(im2,i,j);
      
	  //im2の r,g,b の値を取得
	  r2=gdImageRed(im2,pixel2);
	  g2=gdImageGreen(im2,pixel2);
	  b2=gdImageBlue(im2,pixel2);

	  //RGB色空間からYUV色空間への変換
          Y=0.299*r+0.587*g+0.114*b;
	  Y2=0.299*r2+0.587*g2+0.114*b2;

	  //SSDの計算
	  SSD+=(Y2-Y)*(Y2-Y);
	}
      }
      //SSDが閾値を以下だった時の処理
      if(SSD<=threshold){
	//SSDの最小値の更新
	if(SSD<SSD_MIN||SSD_MIN<0){
	  SSD_MIN=SSD;
	  s_MIN[0][0]=x;
	  s_MIN[0][1]=y;
	}
	//マッチ結果を黒色の四角の枠で囲む
	gdImageRectangle(im_new,x,y,x+width2,y+height2,black);
      }
    }
  }

  //SSD最小となる座標は赤色の四角の枠で囲む
  if(s_MIN[0][0]!=-1){
    gdImageRectangle(im_new,s_MIN[0][0],s_MIN[0][1],s_MIN[0][0]+width2,s_MIN[0][1]+height2,red);
  }

  gdImageJpeg(im_new,out,-1);

  fclose(in);
  fclose(in2);
  fclose(out);

  return 0;
  
}
