#include<stdio.h>
int a[10],n;

void f(int x){
  int j=0,i=0,k=0,l=0;

  if(x==n){
    for(i=0;i<n;i++)
      for(j=0;j<n;j++){
	if(a[i]==a[j])
	  k++;
	if(a[i]>=a[j])
	  if(a[i]-a[j]==i-j)
	    l++;
	if(a[i]<a[j])
	  if(a[j]-a[i]==i-j)
	    l++;
      }
    if(k==n&&l==n){
    for(i=0;i<n;i++)
      printf("%d",a[i]);
    printf("\n");
    }
    return;
}
  else
    for(i=1;i<=n;i++){
      a[x]=i;
      f(x+1);
} 
}
  int main(void){
    printf("n: ");scanf("%d",&n);
    f(0);
    return 0;
  }
