#include <iostream>
using namespace std;

struct arrays
{
    int A[20];
    int length;
    int sizee;
};
int linearSearch(struct arrays arr,int key)
{
    for(int i=0;i<arr.length;i++)
    {
        if(key==arr.A[i])
            return i;
    }
    return -1;
}

int linearSearchTransposition(struct arrays *arr,int key)
{
     int temp;
    for(int i=0;i<arr->length;i++)
    {
        if(key==arr->A[i])
        temp=arr->A[i];
        arr->A[i]=arr->A[i-1];
        arr->A[i-1]=temp;
        return i-1;
    }
    return -1;
}
int main()
{
    struct arrays arr={{10,20,30,40,50,60},6,20};
    cout<<linearSearchTransposition(&arr,50);

}
