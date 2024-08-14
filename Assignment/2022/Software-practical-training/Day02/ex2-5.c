#include<stdio.h>

int strcmp1(const char *str1, const char *str2){
  const char *p,*q;
  int i,j;
  
  p=str1;
  q=str2;

  while(*p!=0||*q!=0){
    if(*p>*q)
      return 1;
    if(*p<*q)
      return -1;
    p++;
    q++;
  }
  
  return 0;
}
    
int main(void){
  char *a,*b,str1[100],str2[100];
  scanf("%s",str1);
   scanf("%s",str2);
   a=&str1[0];
   b=&str2[0];
   printf("%d\n",strcmp1(a,b));
   return 0;
}
