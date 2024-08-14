#include<stdio.h>
#include<stdlib.h>

int main(int argc, char *argv[]){
  FILE *f;
  int c,i;
  i=1;

  while(i<argc){
  f=fopen(argv[i],"r");
    if(f==NULL){
      fprintf(stderr,"%s cannot be opened\n",argv[i]);
    exit(1);
  }
    c=getc(f);
    while(c!=EOF){
      putchar(c);
      c=getc(f);
    }
    i++;
  }
  fclose(f);
  return 0;
}
