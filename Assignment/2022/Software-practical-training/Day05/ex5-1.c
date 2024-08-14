#include<stdio.h>

int fact(int i){
  int f;
  f=i;
  if(i==0)
    return 1;
  else
    return(f*fact(f-1));
}

int main(void){
  int n;
  printf("n: ");
  scanf("%d",&n);
  printf("fact(%d) = %d\n",n,fact(n));
  return 0;
}
