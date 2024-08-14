#include<stdio.h>
char *strcat1(char *dest, const char *src){
  char *p;
  const char *q;

  p=dest;
  while(*p!=0)
    p++;

  for(q=src;*q!=0;q++){
    *p=*q;
    p++;
  }
  *p=0;

  return dest;
}

int main(void){
  char *a,*b,dest[100],src[100];
  scanf("%s",dest);
  scanf("%s",src);
  a=&dest[0];
  b=&src[0];
  printf("%s\n",strcat1(a,b));

  return 0;
 
}
