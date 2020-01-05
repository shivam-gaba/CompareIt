#include <iostream>
#include <stdlib.h>
using namespace std;
struct array
{
    int A[100];
    int length;
    int size;
};

void display(struct array arr)
{
    int i;
    for(i=0;i<arr.length;i++)
    {
    cout<<arr.A[i]<<endl;
    }
}

struct array* merge(struct array arr1,struct array arr2)
{
    int i,j,k;
    i=j=k=0;
    struct array *arr3=new struct array[sizeof(struct array)];
    while(i<arr1.length && j<arr2.length)
    {
        if(arr1.A[i]<arr2.A[j])
        {
            arr3->A[k++]=arr1.A[i++];
        }
        else
        {
            arr3->A[k++]=arr2.A[j++];
        }
    }
    for(;i<arr1.length;i++)
    {
        arr3->A[k++]=arr1.A[i];
    }

    for(;j<arr2.length;j++)
    {
        arr3->A[k++]=arr2.A[j];
    }
    arr3->length=arr1.length+arr2.length;
    arr3->size=20;
    return arr3;
}
struct array* unions(struct array arr1,struct array arr2)
{
    int i,j,k;
    i=j=k=0;
    struct array *arr3=new struct array[sizeof(struct array)];
    while(i<arr1.length && j<arr2.length)
    {
        if(arr1.A[i]<arr2.A[j])
        {
            arr3->A[k++]=arr1.A[i++];
        }
        else if(arr1.A[i]>arr2.A[j])
        {
            arr3->A[k++]=arr2.A[j++];
        }
        else{
            arr3->A[k++]=arr1.A[i++];
            j++;
        }
    }
    for(;i<arr1.length;i++)
    {
        arr3->A[k++]=arr1.A[i];
    }

    for(;j<arr2.length;j++)
    {
        arr3->A[k++]=arr2.A[j];
    }
    arr3->length=k;
    arr3->size=20;
    return arr3;
}
struct array* intersection(struct array arr1,struct array arr2)
{
    int i,j,k;
    i=j=k=0;
   struct array *arr3=new struct array[sizeof(struct array)];
    while(i<arr1.length && j<arr2.length)
    {
        if(arr1.A[i]<arr2.A[j])
        {
     i++;
        }
        else if(arr1.A[i]>arr2.A[j])
        {
            j++;
        }
        else{
            arr3->A[k++]=arr1.A[i++];
            j++;
        }
    }
    for(;i<arr1.length;i++)
    {
        arr3->A[k++]=arr1.A[i];
    }

    for(;j<arr2.length;j++)
    {
        arr3->A[k++]=arr2.A[j];
    }
    arr3->length=k;
    arr3->size=20;
    return arr3;
}
int main()
{
    struct array arr1={{1,2,3,4,5,6},6,10};
    struct array arr2{{1,4,7,8,9,10},6,10};
    struct array *arr3;
    arr3=intersection(arr1,arr2);
    display(*arr3);
}
