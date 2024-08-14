#include<stdio.h>
int fibonacci(int i){
  if(i<=2)
    return 1;
  else
    return (fibonacci(i-2)+fibonacci(i-1));
}

int main(void){
  int n;

  printf("n: ");scanf("%d",&n);
  printf("%d\n",fibonacci(n));

  return 0;
}
