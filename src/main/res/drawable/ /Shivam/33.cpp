#include <iostream>
using namespace std;

class Element
{
public:
    int i,j,x;

};
class Sparse
{
public:
    int m,n,num;
    Element *e;
};
void create(Sparse *s)
{
    cout<<"Enter the number of row"<<endl;
    cin>>s->m;
    cout<<"Enter the number of cols"<<endl;
    cin>>s->n;
    cout<<"Enter the number of non-zero elements"<<endl;
    cin>>s->num;
    s->e=new Element[s->num*sizeof(Element)];

    for(int i=0;i<s->num;i++)
    {
       cout<<"Enter row and column number along with elements"<<endl;
       cin>>s->e[i].i;
       cin>>s->e[i].j;
       cin>>s->e[i].x;
    }
}
void display(Sparse *s)
{
    int i,j,k=0;
    for(i=1;i<=s->m;i++)
    {
        for(j=1;j<=s->n;j++)
        {
          if(i==s->e[k].i && j==s->e[k].j)
          {
              cout<<s->e[k++].x<<"\t";
          }
          else
          {
              cout<<"0"<<"\t";
          }
        }
        cout<<endl;
    }
}

Sparse * add(Sparse *s1,Sparse *s2)
{
    Sparse *sum;
    sum=new Sparse[sizeof(Sparse)];
    sum->e=new Element[(s1->num+s2->num)*sizeof(Element)];

    int i,j,k;
    i=j=k=0;

    while(i<s1->num && j<s2->num)
    {
        if(s1->e[i].i<s2->e[j].i)
        {
            sum->e[k++]=s1->e[i++];
        }
        else if(s1->e[i].i>s2->e[j].i)
        {
            sum->e[k++]=s2->e[j++];
        }
        else
            {
            if(s1->e[i].j<s2->e[j].j)
        {
            sum->e[k++]=s1->e[i++];
        }
        else if(s1->e[i].j>s2->e[j].j)
        {
            sum->e[k++]=s1->e[j++];
        }
        else{
            sum->e[k]=s1->e[i];    // copied row and column numbers
            sum->e[k++].x=s1->e[i++].x+s2->e[j++].x;         // copied sum of elements
        }
            }
    }
    for(;i<s1->num;i++){sum->e[k++]=s1->e[i++];}
    for(;j<s2->num;j++){sum->e[k++]=s2->e[j++];}


    // giving dimensions to the resultant matrix

    sum->m=s1->m;
    sum->n=s1->n;
    sum->num=k;
}

int main()
{
    Sparse s1,s2,*sum;
    create(&s1);
    create(&s2);
    sum=add(&s1,&s2);
    cout<<"\n ================================"<<endl;
    cout<<"First Matrix is "<<endl;
    display(&s1);
        cout<<"Second Matrix is "<<endl;
    display(&s2);
        cout<<"Third Matrix is "<<endl;
    display(sum);
}
