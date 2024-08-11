// y 軸方向に傾けるスキューのプログラムをバイリニア補間を用いて実現せよ．第1 引数に入力画像，第 2 引数に出力画像，第 3 引数に傾ける角度 θ を取るものとする．

//第三引数には度数法で傾ける角度θを入力してください
//元の画像での左下（0,height)の点が傾けた後は(height*tanθ,height)となるように角度をとりました
#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in;
  gdImagePtr im,im_new;
  int width,height,i,j,color,r,g,b,pixel,pixel1,pixel2,pixel3,pixel4;
  double double_x,double_y,x_fraction,y_fraction,angle,tangent,ratio,rad;
  int int_x,int_y;

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
  //第三引数を数に変換
  angle=atof(argv[3]);
  //度をラジアンに変換
  rad=angle*(M_PI/180);

  //im に画像を読み込み
  im = gdImageCreateFromJpeg(in);

  //入力画像のサイズを取得
  width=gdImageSX(im);
  height=gdImageSY(im);

  tangent=tan(rad);
  
  //新しい画像を用意
  im_new= gdImageCreateTrueColor(width,height+width*tangent);

  ratio=(width+height*tangent)/width;

  for(i=0;i<width;i++){
    for(j=0;j<(int)height+width*tangent;j++){
      
      double_x=i;
      double_y=j-i*tangent;
      int_x=(int)double_x;
      int_y=(int)double_y;

      pixel1=gdImageGetPixel(im,int_x,int_y);
      pixel2=gdImageGetPixel(im,int_x+1,int_y);
      pixel3=gdImageGetPixel(im,int_x,int_y+1);
      pixel4=gdImageGetPixel(im,int_x+1,int_y+1);

      x_fraction=double_x-int_x;
      y_fraction=double_y-int_y;

      //im の (x,y) における r,g,b の値を取得
      r=(int)((1-x_fraction)*(1-y_fraction)*gdImageRed(im,pixel1)+x_fraction*(1-y_fraction)*gdImageRed(im,pixel2)+(1-x_fraction)*y_fraction*gdImageRed(im,pixel3)+x_fraction*y_fraction*gdImageRed(im,pixel4));
      g=(int)((1-x_fraction)*(1-y_fraction)*gdImageGreen(im,pixel1)+x_fraction*(1-y_fraction)*gdImageGreen(im,pixel2)+(1-x_fraction)*y_fraction*gdImageGreen(im,pixel3)+x_fraction*y_fraction*gdImageGreen(im,pixel4));
    b=(int)((1-x_fraction)*(1-y_fraction)*gdImageBlue(im,pixel1)+x_fraction*(1-y_fraction)*gdImageBlue(im,pixel2)+(1-x_fraction)*y_fraction*gdImageBlue(im,pixel3)+x_fraction*y_fraction*gdImageBlue(im,pixel4));
      
      if(r<0){
	r=0;
      }
      if(g<0){
	g=0;
      }
      if(b<0){
	b=0;
      }
      if(r>=256){
	r=255;
      }
      if(g>=256){
	b=255;
      }
      if(b>=256){
	b=255;
      }

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
