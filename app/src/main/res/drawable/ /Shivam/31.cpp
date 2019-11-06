#include <iostream>
using namespace std;

class Matrix
{
    public:
    int arr[16];
    int n;
};
void set(Matrix *m,int i,int j,int x)
{
    if(i==j)
    {
     m->arr[i-1]=x;
    }
}
int get(Matrix m,int i,int j)
{
    if(i==j)
    {return m.arr[i-1];}

    else
        {return 0;}

}
void display(Matrix m)
{
    int i,j;
    for(i=0;i<m.n;i++)
    {
        for(j=0;j<m.n;j++)
        {
            if(i==j)
            {
                cout<<m.arr[i]<<"\t";
            }
            else
                {
                cout<<"0"<<"\t";
            }
        }
        cout<<endl;
    }
}

int main()
{
    Matrix m;
    m.n=4;
    set(&m,1,1,5);
    set(&m,2,2,10);
    set(&m,3,3,15);
    set(&m,4,4,20);

    display(m);

    cout<<"=============="<<endl;
    cout<<get(m,2,2);

}
