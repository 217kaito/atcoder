#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_CHAR   10000000

int char_top = 0;
char char_heap[MAX_CHAR];

struct cell {
    char *id;
    int info;
    struct cell *next;
};
struct cell *head;

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

struct cell *new_cell(void)
{
  struct cell *p;
  
  p = (struct cell *)malloc(sizeof(struct cell));
  if (p == NULL) {
    printf("memory allocation failed\n");
    exit(1);
  }
  return (p);
}

void initialize_table(void)
{
    head = NULL;
}

void print_list(void){
  struct cell *p;

  for(p=head;p!=NULL;p=p->next)
    printf("%s, %d\n",p->id,p->info);
}

void insert_table(char *id, int info)
{
   struct cell *p;
 p = new_cell();
 p->id=save_string(id);
 p->info=info;
 p->next=head;
 head=p;
 print_list();
}

struct cell *search_table(char *id)
{
    struct cell *p;

    p = head;
    while (p != NULL) {
        if (strcmp(id, p->id) == 0)
            return (p);
        p = p->next;
    }
    return (NULL);
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
