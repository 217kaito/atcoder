#include<stdio.h>

int strlen1(const char *s){
  const char *p;
  int i;
  for(p=s;*p!=0;p++)
    i++;
  return i;
}

int main(void){
  char *a,s[100];
  scanf("%s",s);
  a=&s[0];
  printf("%d\n",strlen1(a));
  return 0;
}
