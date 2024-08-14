#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define M 257
#define TABLE_SIZE 200
#define MAX_CHAR 10000

struct cell {
    char *id;
    int info;
    struct cell *next;
};
struct cell *hashtable[M];

struct cell table[TABLE_SIZE];
int next_cell;

int char_top = 0;
char char_heap[MAX_CHAR];

char *save_string(char *s)
{
    int result;

    result = char_top;
    for (;;) {
        if (char_top >= MAX_CHAR) {
            printf("string buffer overflow\n");
            exit(1);
        }
        char_heap[char_top] = *s;
        char_top++;
        if (*s == 0)
            break;
        s++;
    }
    return (&char_heap[result]);
}

int hash(char *v)
{
    int x;
    x = 0;
    while (*v != 0)
        x = 256 * x + (*v++);
    x = x % M;
    if (x < 0)
        x = (-x);
    return(x);
}

void initialize_table(void)
{
    int i;
    for (i = 0; i < M; i++)
        hashtable[i] = NULL;
    next_cell = 0;
}

struct cell *new_cell(void)
{
  struct cell *p;

  if(next_cell>=TABLE_SIZE){
    printf("table overflow\n");
    exit(1);
  }
  p=&table[next_cell];
  next_cell++;
  return p;
}

void insert_table(char *id, int info)
{
  int h;
  struct cell *p;

  h = hash(id);
  p = new_cell();
  p->id = save_string(id);
  p->info = info;
  p->next = hashtable[h];
  hashtable[h] = p;
}

struct cell *search_table(char *id)
{
    int h;
    struct cell *p;

    h = hash(id);
    p = hashtable[h];

    while(p!=NULL){
      if(strcmp(id, p->id)==0)
	return p;
      p=p->next;
}
    return NULL;
}

int main(void){
  char command[100],name[100];
  int year;
   struct cell *i;

  initialize_table();

  while(1){
    if(scanf("%s %s",command,name)==EOF)
      break;

    if(command[0]=='I'){
      i=search_table(name);
      if(i==NULL){
	scanf("%d",&year);
	insert_table(name,year);
      }
      else
	printf("二重登録です。\n");
    }
    else if(command[0]=='S'){
      i=search_table(name);
      if(i==NULL)
	printf("未登録です。\n");
      else
	printf("%d\n",i->info);
    }
  }
  return 0;
}
