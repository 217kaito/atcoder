#include<stdio.h>

int main(void){

  int c;

  while((c=getchar())!=EOF){
    if(c<65||c>122)
      c='\n';
    putchar(c);
  }

  return 0;
}
