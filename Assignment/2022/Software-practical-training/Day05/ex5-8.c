#include<stdio.h>
int sum;

int partition(int n,int x){

  if(x==1)
    sum++;
  else if(n==0)
    sum++;
  else{
    partition(n,x-1);
  if(n>=x)
    partition(n-x,x);
  }
  
  return sum;
}

int main(void){
  int n,x;

  printf("n: ");scanf("%d",&n);
 printf("x: ");scanf("%d",&x);
 printf("sum = %d\n",partition(n,x));

 return 0;
}
