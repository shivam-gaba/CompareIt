// order of n^2

#include <iostream>
using namespace std;
int main()
{
    int A[10]={4,2,6,3,2,6,2,5,1,5};
    int i,j;
    static int count=0;
    int sum=5;
    for (i=0;i<9;i++)
    {
        for(j=i+1;j<10;j++)
        {
            if((A[i]+A[j])==sum)
            {
                cout<<A[i]<<" + "<<A[j]<<" = "<<sum<<endl;
                count++;
            }
        }
    }
    cout<<"count is "<<count;
}
