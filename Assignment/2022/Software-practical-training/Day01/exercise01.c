#include <stdio.h>
int main(void){
  int n,a[100],x,i,j;

  printf("n: "); scanf("%d",&n);

  for(x=0;x<n;x++){
     scanf("%d",&a[x]);
  
  printf("Divisors of %d are",a[x]);
  
    for(j=1;j<=a[x];j++){
      
      if(a[x]%j==0)
	printf(" %d",j);
    }
    printf(".");
    printf("\n");
    
  }
  return 0;
}
    
