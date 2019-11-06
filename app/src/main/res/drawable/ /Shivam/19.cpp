#include <iostream>
using namespace std;

class arrays
{
public:

    int length;
    int size;
    int A[length];
};

void merge(int *A[20],int *B[20],int lengthA,int lengthB)
{
    int i=0,j=0,k=0;
    int C[lengthA+lengthB];
    while(i<lengthA && j<lengthB)
    {
        if(A[i]<B[j]){C[k++]=A[i++];}
        else if(A[i]>B[j]){C[k++]=B[j++];}
    }
    for(;i<lengthA;i++)
    {
        C[k++]=A[i];
    }
    for(;j<lengthB;j++)
    {
        C[k++]=B[j];
    }
}
int main()
{
    arrays arrA;
    arrA.length=4;
    arrA.A[]={1,3,5,7};
    arrA.size=10;

    arrays arrB;
    arrB.A[]={2,4,6,8,9};
    arr.size=10;
    arr.length=5;

    arrays arrC;




}
