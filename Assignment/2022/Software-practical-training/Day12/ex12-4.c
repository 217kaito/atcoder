#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define M 257
#define TABLE_SIZE 50000
#define MAX_CHAR 10000000

struct cell {
    char *id;
    int info;
    struct cell *next;
};
struct cell *hashtable[M];
struct cell table[TABLE_SIZE];
struct cell result[TABLE_SIZE];

int next_cell;
int count=0;

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

#define MAX_WORD   1024          /* 単語の文字数の上限 */

int compare_cell(const void * a,const void * b)
{
  return (strcmp(((struct cell *)a)->id, ((struct cell *)b)->id));
}

void print_table(void){
  int i;

  qsort(result,count,sizeof(struct cell),compare_cell);
  
  for(i=0;i<count;i++)
    printf("%5d %s\n",result[i].info,result[i].id);

}

void copy(){
  int i;

  for(i=0;i<next_cell;i++){
    result[count]=table[i];
      count++;
  }
}

int main(void){
  int  c, i;
  struct cell *p;
  char word[MAX_WORD+1];

  initialize_table();
  c = getchar();
  while (c != EOF)
    if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')))
      c = getchar();
    else {
      i = 0;
      while ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
	if (c >= 'A' && c <= 'Z')
	  c += 'a'-'A';
	if (i >= MAX_WORD) {
	  printf("too long word\n");
	  exit(1);
	}
	word[i] = c;
	i++;
	c = getchar();
      }
      word[i] = 0;
      p = search_table(word);
      if (p == NULL)
	insert_table(word, 1);
      else
	p->info++;
    }
  copy();
  print_table();
  return (0);
}
