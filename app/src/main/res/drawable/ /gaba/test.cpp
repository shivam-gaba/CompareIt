#include <iostream>
using namespace std;

void e(int x,int n)
{
    double s=1;
    double num=1;
    double den=1;
    int i;

    for(i=0;i<=n;i++)
        {
        num*=x;
        den*=i;
        s=1+num/den;
    }
    cout<<s;
}
int main()
{
e(1,10);
}

