#include<stdio.h>
void f(int x,int b){
  if(x<b)
    printf("%d",x);
  else{
    f(x/b,b);
    printf("%d",x%b);
  }
}


int main(void){
  int x,b;
  printf("x: ");scanf("%d",&x);
 printf("b: ");scanf("%d",&b);
 f(x,b);
 printf("\n");
 return 0;
}
