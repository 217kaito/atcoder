#include<stdio.h>
 int a[4000000],b[4000000];

int cmp(int a[],int b[]){
  int i=0,k=0;
  while(a[i]!='\n'|| (b[i]!='\n')){
    if(a[i]!=b[i])
      return 1;
    i++;
  }
  return 0;
}

int main(void){
  int i=0,j=0,k=0,l=0,r=0;
 
 
  a[i] = getchar();
  while(a[i]!='\n'){
      i++;
      a[i]=getchar();
    }
  
 while(1){

   j=0;
    b[j]=getchar();
    k=1;

    if(b[0]==EOF){
      printf("%d ",k);
      for(r=0;r<i+1;r++)
	printf("%c",a[r]);
        break;
    }

    while(b[j]!='\n' && b[j] != EOF){
      j++;
      b[j]=getchar();
    }
  
    while(cmp(a,b)==0){
      k++;

      for(j=0;b[j]!='\n' && b[j] != EOF;j++)
       b[j]=0;

      b[j]=0;

      j=0;

      b[j]=getchar();
      while(b[j]!='\n' && b[j] != EOF){
	j++;
	b[j]=getchar();
      }
    }
      printf("%d ",k);
      for(l=0;l<=i;l++)
	printf("%c",a[l]);

      if(cmp(a,b)!=0){

	for(i=0;a[i]!='\n';i++)
	  a[i]=0;

	a[i]=0;

	for(i=0;i<j+1;i++){
	  a[i]=b[i];
	  b[i]=0;
	}
      }
	}
 
  return 0;
}
    
