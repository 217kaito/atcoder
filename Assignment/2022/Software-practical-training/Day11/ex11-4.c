#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define M 50000
#define MAX_CHAR 10000000

struct item { char *id; int info; };
struct item table[M];
struct item result[M];
int mark[M];
int num_hash=0;
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
    mark[i] = 0;
}

void insert_table(char *id, int info)
{
  int x,i;
  
  if(num_hash==M){
    printf("これ以上データを入れることができません。\n");
    exit(1);
  }

  x=hash(id);

  if(mark[x]==0){
    table[x].id=save_string(id);
    table[x].info=info;
    mark[x]=1;
    num_hash++;
  }
  else{
    i=x+1;
    while(1){
      if(i==M)
	i=0;

      if(mark[i]==0){
	table[i].id=save_string(id);
	table[i].info=info;
	mark[i]=1;
	num_hash++;
      break;
      }
      i++;
    }
  }
}

int search_table(char *id)
{
  int x, ep;
  x = hash(id);
  ep = x;
  while (mark[x] == 1) {
    if (strcmp(table[x].id, id) == 0)
      return (x);
    x = (x + 1) % M;
    if (x == ep)
      return (-1);
  }
  return (-1);
}

#define MAX_WORD   1024          /* 単語の文字数の上限 */

int compare_item(const void * a,const void * b)
{
  return (strcmp(((struct item *)a)->id, ((struct item *)b)->id));
}

void print_table(void){
  int i;

  qsort(result,count,sizeof(struct item),compare_item);
  
  for(i=0;i<count;i++)
    printf("%5d %s\n",result[i].info,result[i].id);

}

void copy(){
  int i;

  for(i=0;i<M;i++)
    if(mark[i]==1){
      result[count]=table[i];
      count++;
    }
}

int main(void){
  int  c, i, p;
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
      if (p == -1)
	insert_table(word, 1);
      else
	table[p].info++;
    }
  copy();
  print_table();
  return (0);
}
