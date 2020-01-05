#include <iostream>
using namespace std;
int main()
{
    int A[10]={1,2,3,4,5,8,9,12,14,16};
    int i=0,j=9,sum=12;
    while(i<j)
    {
        {
            if((A[i]+A[j])>sum)
            {
                j--;
            }
            else if((A[i]+A[j])<sum)
            {
                i++;
            }
            else if((A[i]+A[j])==sum)
            {
                cout<<A[i]<<"+"<<A[j]<<"="<<sum<<endl;
                i++;
                j--;
            }
        }
    }
}
