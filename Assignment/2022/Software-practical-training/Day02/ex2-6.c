#include<stdio.h>

      char *strstr1(const char *s1,const  char *s2){
  const char *p,*q,*r;

	p=s1;
	q=s2;
  
  if(*q==0)
    return ((char *)p);
  
  while(*p!=0&&*q!=0){
    q=s2;
    if(*p==*q){
    r=p;
    while(*p!=0||*q!=0){
      if(*p==*q){
    p++;
    q++;
    if(*q==0)
      return ((char *)r);
      }
      else{
	p=r+1;
	break;
      }
    }
    }
  else
    p++;
  }

      
  return NULL;
}
  
  void test_strstr1(const char *s1,const char *s2){
    char *p;
    p = strstr1(s1,s2);
    if(p==NULL)
      printf("[%s], [%s] -> [NULL]\n",s1,s2);
    else
      printf("[%s], [%s] -> [%s]\n",s1,s2,p);
  }
  
  int main(void){
    test_strstr1("abcdddef","dde");
    test_strstr1("abcabc","abc");
    test_strstr1("print","int");
    test_strstr1("abc","");
    test_strstr1("","");
    test_strstr1("age:20, name:tom","name:");
    test_strstr1("This is a pen","never");
    return 0;
  }
