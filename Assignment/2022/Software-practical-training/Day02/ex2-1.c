#include <stdio.h>

char *strchr1(const char *s, int c){
  const char *p;
  for(p=s;*p!=0;p++)
    if(*p==c)
      return ((char *)p);
  return (NULL);
}

char *strchr2(const char s[], int c){
  int k;
  for(k=0;s[k]!=0;k++)
    if(s[k]==c)
      return ((char *)&s[k]);
  return (NULL);
}

int main(void){
  char *a,s[100];
  int c;

  c='c';
  scanf("%s",s);
   a = &s[0];
     
     if(strchr1(a,c) != NULL)
     printf("%s\n",strchr1(a,c));
     else
     printf("not found\n");
    
     if(strchr2(s,c) != NULL)
     printf("%s\n",strchr2(s,c));
     else
     printf("not found\n");

     return 0;
}

     
