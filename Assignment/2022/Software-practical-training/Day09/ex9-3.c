#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define TABLE_SIZE 1000000
#define MAX_CHAR 10000000

struct item{char *id ;int info;};

struct item table[TABLE_SIZE];
int count;

int char_top=0;
char char_heap[MAX_CHAR];

char *save_string(char *s){
  int result;

  result =char_top;
  for(;;){
    if(char_top>=MAX_CHAR){
      printf("string buffer overflow\n");
      exit(1);
    }
    char_heap[char_top]=*s;
    char_top++;
    if(*s==0)
      break;
    s++;
  }
  return (&char_heap[result]);
}

void initialize_table(void)
{
  count=0;
}
void insert_table(char *id,int info)
{
  int i;
  char *p;
  if(count>=TABLE_SIZE)
    exit(1);
  p= save_string(id);
  table[count].id = p;
  table[count].info = info;
  for(i=0;i<=count;i++){
    printf("table[%d].id = %s\n",i,table[i].id);
    printf("table[%d].info = %d\n",i,table[i].info);
  }
  count++;
  printf("count = %d\n",count);
}

int search_table(char *id)
{
  int i;

  for(i=0;i<count;i++)
    if(strcmp(id,table[i].id)==0)
      return (i);
      return (-1);
}

int main(void){
  char command[100],name[100];
  int year;
  int i;

  initialize_table();

  while(1){
    if(scanf("%s %s",command,name)==EOF)
      break;

    if(command[0]=='I'){
      i=search_table(name);
      if(i==-1){
	scanf("%d",&year);
	insert_table(name,year);
      }
      else
	printf("二重登録です。\n");
    }
    else if(command[0]=='S'){
      i=search_table(name);
      if(i==-1)
	printf("未登録です。\n");
      else
	printf("%d\n",table[i].info);
    }
  }
  return 0;
}
