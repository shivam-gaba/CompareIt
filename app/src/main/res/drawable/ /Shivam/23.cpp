// finding number of duplicates

#include <iostream>
using namespace std;
int main()
{
int i,j=0;
int A[10]={1,1,3,3,4,5,6,6,6,7};
for(i=0;i<10-1;i++)
{
    if(A[i]==A[i+1])
    {
        j=i+1;
        while(A[j]==A[i])
        {
            j++;
        }
        cout<<A[i]<<" is coming "<<j-i<<" times"<<endl;
        i=j-1;
    }
}
}
