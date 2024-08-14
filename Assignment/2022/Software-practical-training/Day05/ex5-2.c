#include<stdio.h>
void hanoi(int n,char *from,char *to,char *tmp);

int main(void){
  hanoi(3,"a","b","c");
return 0;
}

void hanoi(int n,char *from,char *to,char *tmp)
{
  if(n==0) return;

  hanoi(n-1,from,tmp,to);
  printf("move disk %d from %s to %s\n",n,from,to);
  hanoi(n-1,tmp,to,from);
}
