#include <iostream>
using namespace std;
int main()
{
    int i,duplicate=0;
    int A[10]={1,1,3,3,4,5,6,6,6,7};
    for(i=0;i<10;i++)
    {
        if(A[i]==A[i+1] && A[i]!=duplicate)
        {
            cout<<A[i]<<endl;
            duplicate =A[i];
        }
    }
}
