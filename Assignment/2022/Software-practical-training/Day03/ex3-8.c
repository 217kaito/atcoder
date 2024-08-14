#include<stdio.h>
#include<stdlib.h>

int main(void){
  FILE *f;
  int sum,a[100],i,r;

  f=fopen("arg6.txt","r");
   if(f==NULL){
   fprintf(stderr,"arg6.txt cannot be opened\n");
    exit(1);
  }

   sum=0;

   i=0;
   r=fscanf(f,"%d,%d",&a[i],&a[i+1]);
   
   while(r!=EOF){
     if(r!=2){
       printf("Error\n");
       exit(1);
     }
    
     printf("%d %d\n",a[i],a[i+1]);
     printf("sum = %d\n",(a[i]+a[i+1]));
     i=i+2;
     r=fscanf(f,"%d,%d",&a[i],&a[i+1]);
   }
   fclose(f);
   return 0;
}
	    
