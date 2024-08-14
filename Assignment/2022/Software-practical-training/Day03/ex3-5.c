#include<stdio.h>
#include<stdlib.h>

int main(int argc, char *argv[]){
  FILE *f;
  int a[256],i,c;

  if(argc!=2){
    fprintf(stderr,"number of command arguments should be 1\n");
    exit(1);
  }

  for(i=0;i<256;i++)
    a[i]=0;
  
  f=fopen(argv[1],"r");
  if(f==NULL){
   fprintf(stderr,"%s cannot be opened\n",argv[1]);
    exit(1);
  }

  while((c=getc(f))!=EOF)
    a[c]++;

   for(i=0;i<32;i++)
    if(a[i]!=0)
      printf("[%d] %d\n",i,a[i]);
   
  for(i=32;i<127;i++)
    if(a[i]!=0)
      printf("[%c] %d\n",i,a[i]);

  for(i=127;i<256;i++)
    if(a[i]!=0)
      printf("[%d] %d\n",i,a[i]);
  fclose(f);
  return 0;
}
