#include <iostream>
using namespace std;
struct arrays
{
    int *A;
    int sizee;
    int length;
};
void Display(struct arrays arr)
{
    for(int i=0;i<arr.sizee;i++)
    {
        cout<<arr.A[i]<<endl;
    }
}
int main()
{
struct arrays arr;
cout<<"Enter size of array "<<endl;
cin>>arr.sizee;
arr.A=new int[10];
cout<<"Enter the number of numbers "<<endl;
cin>>arr.length;
cout<<"enter the elements of array "<<endl;
for(int i=0;i<arr.length;i++)
{
cin>>arr.A[i];
}
Display(arr);
}
