// increasing size of an array stored in heap

#include <iostream>
using namespace std;
int main()
{

    int *p;
    p=new int[5];

    p[0]=10;
    p[1]=20;
    p[2]=30;
    p[3]=40;
    p[4]=50;

    int *q;
    q=new int[10];
    for(int i=0;i<5;i++)
    {
      q[i]=p[i];
    }
    delete []p;
    p=q;
    q=NULL;
    delete []q;
    p[5]=60;
    for(int i=0;i<10;i++)
    {
     cout<<p[i]<<endl;
    }

}
