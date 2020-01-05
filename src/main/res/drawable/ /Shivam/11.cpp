#include <iostream>
using namespace std;
int fact(int n)
{
    if (n<=1)
    {
        return 1;
    }
    else
    {
        return fact(n-1)*n;
    }
}

int NCR2(int n,int r)
{
    return fact(n)/(fact(n-r)*fact(r));
}

int NCR(int n,int r)
{
    if(n==r || r==0)
    {
        return 1;
    }
    else
    {
        return NCR(n-1,r-1)+NCR(n-1,r);
    }
}
int main()
{
    cout<<NCR2(10,1);
}
