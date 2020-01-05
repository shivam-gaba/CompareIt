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
    if(i>=j)
    {
     m->arr[(i*(i-1)/2)+j-1]=x;
    }
}
int get(Matrix m,int i,int j)
{
    if(i>=j)
    {return m.arr[(i*(i-1)/2)+j-1];}

    else
        {return 0;}

}
void display(Matrix m)
{
    int i,j;
    for(i=1;i<=m.n;i++)
    {
        for(j=1;j<=m.n;j++)
        {
            if(i>=j)
            {
                cout<<m.arr[(i*(i-1)/2)+j-1]<<"\t";
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
    set(&m,3,1,6);
    set(&m,2,1,4);
    set(&m,1,1,3);
    set(&m,2,2,4);
    set(&m,3,3,5);
    set(&m,4,4,7);
    set(&m,3,2,5);
    set(&m,4,3,8);
    set(&m,4,2,1);
    set(&m,4,1,5);
    display(m);

}
