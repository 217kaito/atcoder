// ファイルからフィルタ係数を読み込み，任意の線形フィルタを画像に対して施すプログラムを libgd を用いて作成せよ．
// 第 1 引数に入力ファイル名，第 2 引数に出力ファイル名，第 3 引数にフィルタ係数のファイル名を取るものとする．
// フィルタ係数が奇数幅の正方形となっていない場合にはその旨を標準出力に表示し，プログラムが終了するようにせよ．

#include <gd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(const int argc,const char *argv[]){

  FILE *out,*in,*in2;
  gdImagePtr im,im_new;
  int width,height,i,j,m,n,color,x,x2,pixel,w,index,num_ds;
  int chr,chr_late;
  char str1[100],str2[2];
  //行数、列数を保存する変数
  int line,row,line_late;
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
  //第三引数で指定されたファイルを読み出し用にオープン
  if((in2=fopen(argv[3],"r"))==NULL){
    printf("file open error for %s\n",argv[3]);
    exit(-1);
  }

  //im に画像を読み込み
  im = gdImageCreateFromJpeg(in);

  //入力画像のサイズを取得
  width=gdImageSX(im);
  height=gdImageSY(im);

  //argv[3]で入力されたフィルタ係数を格納するための配列filterの要素を０で初期化する
  int filter[width][height];
  memset(filter,0,sizeof(filter));

  chr_late=-1;
  index=0;
  row=0;
  line=0;
  line_late=0;
  num_ds=0;
  str1[0]='\0';
  //str1の要素をヌル文字で初期化する
  memset(str1,'\0',sizeof(str1));
 
  //フィルタ係数を読み込み、配列filterに格納する
  //フィルタ係数を読み終わるまでの処理
  while((chr=getc(in2))!=EOF){
    line++;
    //フィルタ係数が書かれた文字ファイルの一行ずつの処理
    while(chr!='\n'){
      //読み込んだ文字がフィルタ係数の各要素の値を区切る文字,ではなかった場合の処理
      if(chr!=','){
	//読み込んだ文字が数字または.だった場合
	if((isdigit(chr))!=0||chr=='.'&&num_ds==0){
	  if(chr=='.'){
	    num_ds=1;
	  }
	  //フィルタ係数の各要素の値のそれぞれの最初の数字を読み込んだ場合の処理
	  if(str1[0]=='\0'){
	    str1[0]=(char)chr;
	  }
	  else{
	    str2[0]=chr;
	    str2[1]='\0';
	    //str1とstr2をこの順番で結合した文字列をstr1に格納する
	    strcat(str1,str2);
	    index++;
	  }
        }
        else{
	  chr=chr_late;
	}
      }
      else {
	//フィルタ係数の各要素の値を配列filterに格納する
	filter[row][line-1]=strtod((const char*)str1,0);
	//str1のすべての要素をヌル文字にする
	memset(str1,'\0',sizeof(str1));
	line++;
	index=0;
	num_ds=0;
      }
      chr_late=chr;
      chr=getc(in2);
      //フィルタ係数が書かれた文字ファイルの最後の行が開業されない場合の処理
      if(chr==EOF){
	row++;
	break;
      }
    }
    //フィルタ係数が書かれた文字ファイルの最後の行が開業されない場合の処理
    if(chr==EOF){
      break;
    }
    if(isdigit(chr_late)!=0){
	if(isdigit(str1[index])==0){
	  str1[index]='\0';
	}
      filter[row][line-1]=strtod((const char*)str1,0);
      memset(str1,'\0',sizeof(str1));
      index=0;
      num_ds=0;
    }
    //空白行ではない場合の処理
    if(chr_late!='\n'&&chr_late!=-1){
      row++;
      if(line!=0&&line_late!=0&&line_late!=line||line!=0&&line%2==0){
	printf("フィルタ係数が奇数幅の正方形となっていません\n");
	exit(-1);
      }
      line_late=line;
      line=0;
    }
  }
  if(line_late!=row){
    printf("フィルタ係数が正方形となっていません\n");
    exit(-1);
  }

  //新しい画像を用意
  im_new= gdImageCreateTrueColor(width,height);
  w=row-1;
  w=w/2;

 
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
	    r+=gdImageRed(im,pixel)*filter[m+w][n+w];
	    g+=gdImageGreen(im,pixel)*filter[m+w][n+w];
	    b+=gdImageBlue(im,pixel)*filter[m+w][n+w];
	  }
	}
      }    

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
  fclose(in2);
  fclose(out);

  return 0;
  
}
