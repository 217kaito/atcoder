#include<stdio.h>
#include<stdlib.h>

int main(void){
  FILE *f;
  int c,n;

  n=0;
  
  f=fopen("sample.txt","r");

  if(f==NULL){
    fprintf(stderr,"sample.txt cannot be opened\n");
    exit(1);
  }

  c=getc(f);
  
  while(c!=EOF){
    c=getc(f);
    n++;
  }

  printf("%d\n",n);

  fclose(f);
  return 0;
}
