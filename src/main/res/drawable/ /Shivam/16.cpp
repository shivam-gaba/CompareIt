
// arrays operations //

#include <iostream>
using namespace std;

struct arrays{
int A[20];
int sizee;
int length;
};

void append(struct arrays *arr,int x)
{
    if((arr->sizee)>(arr->length))
    {
        arr->A[arr->length++]=x;
    }
}
void display(struct arrays arr)
{
    for(int i=0;i<arr.length;i++)
    {
        cout<<arr.A[i]<<endl;
    }
}
void insertt(struct arrays *arr,int index,int x)
{
 if(index>=0 && index<arr->length)
 {
     arr->length++;

     for(int i=arr->length;i>index;i--)
     {
     arr->A[i]=arr->A[i+1];
 }
 arr->length++;
 arr->A[index]=x;

 }
}
void deletee(struct arrays *arr,int index)
{
    for(int i=index;i<arr->length-1;i++)
    {
        arr->A[i]=arr->A[i+1];
    }
    arr->length--;
}
int main()
{
    struct arrays arr={{1,2,3,4,5,6,7},10,7};

    display(arr);

    cout<<"\n \n \n";
    insertt(&arr,5,99);
    display(arr);
    cout<<"\n \n \n";
    deletee(&arr,5);
    display(arr);
}
