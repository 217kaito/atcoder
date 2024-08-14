#include<stdio.h>

int atoi1(const char *str){
  const char *p;
  int x,y;

  x=0;
  y=0;

  for(p=str;*p<58&&*p>47;p++){
    x=x*10;
   y=*p-48;
    x=x+y;
  }

  if(*p==45){
    p++;
 for(;*p<58&&*p>47;p++){
    x=x*10;
   y=*p-48;
    x=x+y;
  }
 x=-x;
  }

  return x;
}

int main(void){
  char *a,str1[100];
  scanf("%s",str1);
  a=&str1[0];
  printf("%d\n",atoi1(a));
  return 0;
}
