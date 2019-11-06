#include <iostream>
using namespace std;

struct array
{
    int A[100];
    int length;
    int size;
};

//    order of n
void reverse1(struct array *arr)
{
    int i,j,temp;
    for(i=0,j=arr->length-1;i<j;i++,j--)
    {
        temp=arr->A[i];
        arr->A[i]=arr->A[j];
        arr->A[j]=temp;
    }
}


// order of n
void reverse(struct array *arr)
{
    int B[(arr->length)-1];
    int i,j;
    for(i=arr->length-1,j=0;i>=0;i--,j++)
    {
        B[j]=arr->A[i];
    }
    for(i=0;i<arr->length;i++)
    {
        arr->A[i]=B[i];
    }
}
void display(struct array arr)
{
    for(int i=0;i<arr.length;i++)
    {
    cout<<arr.A[i]<<endl;
    }
}

void insert(struct array *arr,int x)
{
    if(arr->size==arr->length)
    {
        return;
    }
    int i=arr->length-1;
    while(i>=0 && arr->A[i]>x)
    {
        arr->A[i+1]=arr->A[i];
        i--;
    }
    arr->length++;
    arr->A[i+1]=x;
}

int isSorted(struct array arr)
{
    int i;
    for(i=0;i<arr.length-1;i++)
    {
        if(arr.A[i]>arr.A[i+1])
        {
            return 0;
        }
    }
    return 1;
}
void reArrange(struct array *arr)
{
    int i=0,j=arr->length-1;
    while(i<j)
    {
        while(arr->A[i]<0){i++;}
        while(arr->A[j]>0){j--;}

        if(i<j)
        {
            int temp;
            temp=arr->A[i];
            arr->A[i]=arr->A[j];
            arr->A[j]=temp;
        }
    }
}
int main()
{
    struct array arr={{1,-2,3,-5,6,-7},6,10};
    reArrange(&arr);
    display(arr);

}
