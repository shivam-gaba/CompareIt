//      creating and displaying a linked list


#include <iostream>
using namespace std;
   static int count;

class Node
{
public:
    int data;
    Node *next;
}*first;

void create(int A[],int n)
{
    int i;
    Node *last,*temp;
    first=new Node;
    first->data=A[0];
    first->next=NULL;
    last=first;

    for(i=1;i<n;i++)
    {
        temp=new Node;
        temp->data=A[i];
        temp->next=NULL;
        last->next=temp;
        last=temp;
    }
}
void display(Node *p)
{
    while(p!=NULL)
    {
        cout<<p->data<<endl;
        p=p->next;
    }
}



// displaying recursively
void rDisplay(Node *p)
{
    if(p!=NULL)
    {
        cout<<p->data;
        rDisplay(p->next);
    }
}
int max(Node *p)
{
    int maxx=-30000;
    while(p!=0)
    {
        if(p->data>maxx)
        {
            maxx=p->data;

         }
          p=p->next;
}
  return maxx;
}
int rMax(Node *p)
{
    int x=0;
    if(p==0)
    {
        return 0;
    }
    else{
            x=rMax(p->next);
        if(x>p->data)
        {
            return x;
        }
        else{
            return p->data;
        }
    }
}

Node * search(Node *p,int key)
{
    while(p!=NULL)
    {
        if(key==p->data)
        {
            return p;
        }
        p=p->next;
        count++;
    }
    return NULL;
}
int countt(Node *p)
{
    int number=0;
    while(p!=0)
    {
        number++;
        p=p->next;
    }
    return number;
}
void insert(Node *p,int x,int index)
{
    if(index<0 || index>countt(p))
    {
        return;
    }
    else{
            Node *t=new Node;
        t->data=x;

            if(index==0)
            {
             t->next=first;
             first=t;
            }
            else{
        int i;
        p=first;
        for(i=0;i<index-1;i++)
        {
            p=p->next;
        }
        t->next=p->next;
        p->next=t;
            }
    }
}
void sortedInsert(Node *p,int x)
{
    Node *t;
    t=new Node;
    t->next=NULL;
    t->data=x;


        Node *q;
        q=new Node;
        q->next=NULL;

    if(first==NULL)
    {
        first=t;
    }
    else{
        while(p &&p->data<x)
        {
            q=p;
            p=p->next;
        }
        if(p==first)
        {
            t->next=first;
            first=t;
        }
        else{
            q->next=t;
            t->next=p;
        }
    }
}
int isSorted(Node *p)
{
    int x=-32768;   // for 2 bytes integer
   while(p!=0)
   {
       if(p->data<x)
       {
           return 0;
       }
       else{
            x=p->data;
        p=p->next;
       }

   }
   return 1;
}
int deleteNode(Node *p,int index)
{
           int i;
           Node *q=NULL;
           int x=-1;
    if(index<1 || index>countt(p))
    {
        return -1;
    }
    if (index==1)
    {
        q=first;
        x=first->data;
        first=first->next;
        delete q;
        return x;
    }
    else{
        for(i=0;i<index-1;i++)
        {
            q=p;
            p=p->next;
        }
        q->next=p->next;
        x=p->data;
        delete p;
        return x;
    }
}
void removeDuplicates(Node *p)
{
    Node *q=p->next;

    while(q!=NULL)
    {
        if(p->data!=q->data)
        {
            p=q;
            q=q->next;
        }
        else{
            p->next=q->next;
            delete q;
            q=p->next;
        }
    }
}
int main()
{
    int A[]={1,2,3,3,4,4,5};
    create(A,7);
    removeDuplicates(first);
    cout<<"====================="<<endl;
    display(first);
}
