#include<stdio.h>
#include<stdlib.h>

int main(void){
  FILE *f;
  int sum,a[100],i;

  sum=0;
  
  f=fopen("integers.txt","r");
  if(f==NULL){
   fprintf(stderr,"integers.txt cannot be opened\n");
    exit(1);
  }

  for(i=0;(fscanf(f,"%d",&a[i]))!=EOF;i++){
    printf("%d\n",a[i]);
    sum=sum+a[i];
  }
  printf("number = %d\n",i);
  printf("sum =  %d\n",sum);
  fclose(f);
  return 0;
}
