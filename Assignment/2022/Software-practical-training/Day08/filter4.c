#include<stdio.h>


 #define MAX_CHARACTER 5000000
  #define MAX_LINE 5000000

  char buffer[MAX_CHARACTER];
  char *line[MAX_LINE];
  int char_count;
  int line_count;

int compare_line(const void *a,const void *b){
  return (strcmp(*(char **)a,*(char **)b));
}

int main(void){
  int i=0;

  line_count=0;
  char_count=0;
  buffer[char_count]=getchar();
  line[line_count]=&buffer[char_count];
  char_count++;

  while((buffer[char_count]=getchar())!=EOF){
    if(buffer[char_count]=='\n'){
      buffer[char_count]='\0';
      char_count++;
      if((buffer[char_count]=getchar())!=EOF){
	line_count++;
      line[line_count]=&buffer[char_count];
      }
      else
	break;
    }
    
    char_count++;
  }


  qsort(line,line_count+1,sizeof(char *),compare_line);

  for(i=0;i<line_count+1;i++)
  printf("%s\n",line[i]);

  return 0;
}
    
