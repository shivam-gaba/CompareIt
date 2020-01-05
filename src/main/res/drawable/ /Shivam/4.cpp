#include <iostream>
using namespace std;
int power(int m,int n)
{
if(n==0)
    return 1;
else
    return power(m,n-1)*m;
}
int main()
{
    int z=power(10,3);
    cout<<z;
}
