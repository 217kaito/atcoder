#include<stdio.h>
#include<stdlib.h>

int main(int argc,char *argv[]){
  FILE *f1,*f2;
  int c;

  if(argc!=3){
    fprintf(stderr,"number of command arguments should be 2\n");
    exit(1);
  }
  
  f1=fopen(argv[1],"r");
 if(f1==NULL){
   fprintf(stderr,"%s cannot be opened\n",argv[1]);
    exit(1);
  }

  f2=fopen(argv[2],"w");
 if(f2==NULL){
   fprintf(stderr,"%s cannot be opened\n",argv[2]);
    exit(1);
  }

  c=getc(f1);
  while(c!=EOF){
    putc(c,f2);
    c=getc(f1);
  }

  fclose(f1);
  fclose(f2);
  return 0;
}

  
  
