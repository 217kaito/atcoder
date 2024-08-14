#include<stdio.h>

int main(void){

   int c;
  
  c=getchar();

  if(c=='\n')
    while(c=='\n')
      c=getchar();
  if(c!=EOF)
  putchar(c);

  while((c=getchar())!=EOF){
    if(c=='\n'){
      putchar(c);
      while(c=='\n')
	c=getchar();
    }
    if(c!=EOF)
    putchar(c);
    if(c==EOF)
      break;
	}

  return 0;
}
