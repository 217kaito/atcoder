#include<stdio.h>

char *strcpy1(char *dest, const char *src){
  const char *p;
  char *q;

  q=dest;
  
  for(p=src;*p!=0;p++){
    *q = *p;
    q++;
  }
  *q = 0;

  return dest;
}

int main(void){
  char *a,*b,dest[100],src[100];

  scanf("%s",dest);
  scanf("%s",src);
  a=&dest[0];
  b=&src[0];
  printf("%s\n",strcpy1(a,b));

  return 0;
}
