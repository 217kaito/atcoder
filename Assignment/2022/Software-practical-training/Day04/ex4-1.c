#include<stdio.h>
#include<stdlib.h>
void print_file(FILE *fp,int n_option,int h_option,int t_option,int b_option)
{
  int c,i,n_h;

  n_h=1;

  if(n_option==1||b_option==1){
  i=h_option+1;

  c=getc(fp);

  if(b_option!=1)
  printf("%d ",h_option);

  while(n_h<h_option){
    if(c=='\n')
      n_h++;
    c=getc(fp);
  }

 if(b_option==1&&c!='\n')
    printf("%d ",h_option);

 if(t_option!=0){
    while(c!=EOF&&n_h<=t_option){
      putc(c,stdout);
      if(c=='\n'){
	if(c!=EOF){
	c=getc(fp);
	n_h++;
	if(c!=EOF&&n_h<=t_option&&b_option!=1)
	 printf("%d ",i);
	else if(c!=EOF&&n_h<=t_option&&b_option==1&&c!='\n')
	  printf("%d ",i);
      i++;
	}
      }
      else
      c=getc(fp);
    
    }
 }
 else{
    while(c!=EOF){
      putc(c,stdout);
      if(c=='\n'){
	if(c!=EOF){
	c=getc(fp);
	n_h++;
	if(c!=EOF&&b_option!=1)
	 printf("%d ",i);
	else if(c!=EOF&&b_option==1&&c!='\n')
	  printf("%d ",i);
      i++;
	}
      }
      else
      c=getc(fp);
    }
 }
  }
  
  else{
c=getc(fp);


while(n_h<h_option){
    if(c=='\n')
      n_h++;
    c=getc(fp);
  }

 if(t_option!=0){
 while(c!=EOF&&n_h<=t_option){
     if(c=='\n')
	n_h++;
      putc(c,stdout);
      c=getc(fp);
    }
 }
 else{
 while(c!=EOF){
     if(c=='\n')
	n_h++;
      putc(c,stdout);
      c=getc(fp);
    }
 }
}
}

int main(int argc, char *argv[])
{
FILE *fp;
 int i,n,n_option,h_option,t_option,b_option;

 n=0;
 n_option=0;
 b_option=0;
 h_option=1;
 t_option=0;

 for(i=0;i<argc;i++)
     if(argv[i][0]=='-'&&argv[i][1]!='t'&&argv[i][1]!='n'&&argv[i][1]!='h'&&argv[i][1]!='b')
       fprintf(stderr,"Only -n , -h , -t ,-b can be used.\n"); 

 for(i=0;i<argc;i++)
   if(argv[i][0]!='-')
     n++;

 for(i=0;i<argc;i++)
   if(strcmp(argv[i],"-n")==0)
     n_option=1;

for(i=0;i<argc;i++)
   if(strcmp(argv[i],"-b")==0)
     b_option=1;

for(i=0;i<argc;i++)
  if(strncmp(argv[i],"-h",2)==0)
     h_option=atoi(&argv[i][2]);

for(i=0;i<argc;i++)
  if(strncmp(argv[i],"-t",2)==0)
     t_option=atoi(&argv[i][2]);



if(n == 1)
  print_file(stdin,0,0,0,0);
else{
  for(i=1;argc>i;i++){
    if(argv[i][0]!='-'){
fp=fopen(argv[i],"r");
if(fp == NULL){
fprintf(stderr, "%s: %s: No such file or directory\n",argv[0],argv[1]);
exit(1);
}
 print_file(fp,n_option,h_option,t_option,b_option);
  }
  }
 }
fclose(fp);


return 0;
}
