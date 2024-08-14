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
  p=save_string(id);
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
  int t;

  initialize_table();
  insert_table("Copernicus",1473);
  insert_table("Galilei",1564);
  insert_table("Newton",1643);
  insert_table("Maxwell",1831);
  insert_table("Einstein",1879);
  insert_table("Heisenberg",1901);
  t=search_table("Newton");
  if(t==-1)
    printf("not found.\n");
  else
    printf("%d\n",table[t].info);
  return 0;
}
