#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define M 257
#define MAX_CHAR 10000

struct item { char *id; int info; };
struct item table[M];
int mark[M];

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

  x=hash(id);

  if(mark[x]==0){
    table[x].id=save_string(id);
  table[x].info=info;
  mark[x]=1;
  }
  else
    for(i=x+1;i<M;i++)
      if(mark[i]==0){
	table[i].id=save_string(id);
	table[i].info=info;
	mark[i]=1;
	break;
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

int main(void)
{
    int t;
    initialize_table();

    insert_table("Copernicus", 1473);
    insert_table("Galilei",    1564);
    insert_table("Newton",     1643);
    insert_table("Maxwell",    1831);
    insert_table("Einstein",   1879);
    insert_table("Heisenberg", 1901);

    t = search_table("Newton");
    if (t == -1)
        printf("not found.\n");
    else
        printf("%d\n", table[t].info);
      
    return (0);
}
