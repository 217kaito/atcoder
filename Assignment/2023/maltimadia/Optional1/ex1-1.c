// 指定された領域とその他の領域に対して異なるトーンカーブによる補正が可能となるようにせよ．また，トーンカーブの種類を幾つか用意し，それぞれの領域に対してどのトーンカーブを適用するかを引数で選択可能とせよ．

//第3、4、5、6、7、8引数でそれぞれ開始x座標、開始y座標、縦幅、横幅、指定された領域のトーンカーブの指定、その他の領域のトーンカーブの指定を入力する
//トーンカーブの指定は1から4の数字で行う
#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in;
  gdImagePtr im,im_new;
  int width,height,i,j,color,r,g,b,pixel,R,G,B,x,y,w,h,tone1,tone2;

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
  tone1=atoi(argv[7]);
  tone2=atoi(argv[8]);

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

  //指定された領域のトーンカーブとその他の領域のトーンカーブの指定が同じだった場合はエラー処理
  if(tone1==tone2){
    printf("argument error\n");
    exit(-1);
  }

  //新しい画像を用意
  im_new= gdImageCreateTrueColor(width,height);

  if(tone2==1){
    //トーンカーブ1(y=3x)
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
  }
  else if(tone2==2){
    //トーンカーブ2(y=10x)
    for(i=0;i<width;i++){
      for(j=0;j<height;j++){
      
	//im の (i,j) におけるカラーインデックスの取得
	pixel=gdImageGetPixel(im,i,j);
      
	//im の (i,j) における r,g,b の値を取得
	r=gdImageRed(im,pixel);
	g=gdImageGreen(im,pixel);
	b=gdImageBlue(im,pixel);


	//y=10xのトーンカーブをかける
	R=10*r;
	G=10*g;
	B=10*b;
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
  }
  else if(tone2==3){
    //トーンカーブ3(y=100x)
    for(i=0;i<width;i++){
      for(j=0;j<height;j++){
      
	//im の (i,j) におけるカラーインデックスの取得
	pixel=gdImageGetPixel(im,i,j);
      
	//im の (i,j) における r,g,b の値を取得
	r=gdImageRed(im,pixel);
	g=gdImageGreen(im,pixel);
	b=gdImageBlue(im,pixel);


	//y=100xのトーンカーブをかける
	R=100*r;
	G=100*g;
	B=100*b;
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
  }
  else {
    //トーンカーブ4(y=255x)
    for(i=0;i<width;i++){
      for(j=0;j<height;j++){
      
	//im の (i,j) におけるカラーインデックスの取得
	pixel=gdImageGetPixel(im,i,j);
      
	//im の (i,j) における r,g,b の値を取得
	r=gdImageRed(im,pixel);
	g=gdImageGreen(im,pixel);
	b=gdImageBlue(im,pixel);


	//y=255xのトーンカーブをかける
	R=255*r;
	G=255*g;
	B=255*b;
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
  }

  if(tone1==1){
    //トーンカーブ1(y=3x)
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
  }
  else if(tone1==2){
    //トーンカーブ2(y=10x)
    for(i=x;i<=x+w;i++){
      for(j=y;j<=y+h;j++){
      
	//im の (i,j) におけるカラーインデックスの取得
	pixel=gdImageGetPixel(im,i,j);
      
	//im の (i,j) における r,g,b の値を取得
	r=gdImageRed(im,pixel);
	g=gdImageGreen(im,pixel);
	b=gdImageBlue(im,pixel);


	//y=10xのトーンカーブをかける
	R=10*r;
	G=10*g;
	B=10*b;
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
  }
  else if(tone1==3){
    //トーンカーブ3(y=100x)
    for(i=x;i<=x+w;i++){
      for(j=y;j<=y+h;j++){
      
	//im の (i,j) におけるカラーインデックスの取得
	pixel=gdImageGetPixel(im,i,j);
      
	//im の (i,j) における r,g,b の値を取得
	r=gdImageRed(im,pixel);
	g=gdImageGreen(im,pixel);
	b=gdImageBlue(im,pixel);


	//y=100xのトーンカーブをかける
	R=100*r;
	G=100*g;
	B=100*b;
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
  }
  else {
    //トーンカーブ4(y=255x)
    for(i=x;i<=x+w;i++){
      for(j=y;j<=y+h;j++){
      
	//im の (i,j) におけるカラーインデックスの取得
	pixel=gdImageGetPixel(im,i,j);
      
	//im の (i,j) における r,g,b の値を取得
	r=gdImageRed(im,pixel);
	g=gdImageGreen(im,pixel);
	b=gdImageBlue(im,pixel);


	//y=255xのトーンカーブをかける
	R=255*r;
	G=255*g;
	B=255*b;
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
  }
  gdImageJpeg(im_new,out,-1);

  fclose(in);
  fclose(out);

  return 0;
  
}
