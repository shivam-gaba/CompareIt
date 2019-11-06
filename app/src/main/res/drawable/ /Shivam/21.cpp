#include <iostream>
using namespace std;
int main()
{
int sum=0,s;
int A[10]={1,2,4,5,6,7,8,9,10,11};
for(int i=0;i<10;i++)
{
    sum=sum+A[i];
}
s=11*12/2;
cout<<"Missing element is "<<s-sum;

}
