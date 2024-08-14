#include<stdio.h>

int main(void){

  int c;

  while((c=getchar())!=EOF){
    if(c<91&&c>64)
      c=c+32;
    putchar(c);
  }

  return 0;
}
