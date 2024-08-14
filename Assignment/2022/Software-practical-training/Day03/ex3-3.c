#include<stdio.h>
#include<stdlib.h>

int main(void){
  FILE *f1,*f2,*f3;
  int c;
    

  f1=fopen("sample1.txt","r");
   if(f1==NULL){
    fprintf(stderr,"sample1.txt cannot be opened\n");
    exit(1);
  }

   f2=fopen("sample2.txt","w");
    if(f2==NULL){
    fprintf(stderr,"sample2.txt cannot be opened\n");
    exit(1);
  }

    f3=fopen("sample3.txt","w");
     if(f3==NULL){
    fprintf(stderr,"sample3.txt cannot be opened\n");
    exit(1);
  }

     c=getc(f1);

     while(c!=EOF){
       putc(c,f2);
       putc(c,f3);
       c=getc(f1);
     }

     fclose(f1);
     fclose(f2);
     fclose(f3);
     return 0;
}

     
