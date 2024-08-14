#include<stdio.h>
int fibonacci(int n){
  int i,f2,f1,f0;
  f0=0;
  f1=1;
  if(n==1)
    return 1;
  else
  for(i=1;i<n;i++){
    f2=f1+f0;
    f0=f1;
    f1=f2;
  }
  return f1;
}

int main(void){
  int n;
  printf("n: ");scanf("%d",&n);
  printf("%d\n",fibonacci(n));
}
