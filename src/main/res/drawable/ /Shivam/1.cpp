
//structures and functions

#include <iostream>
using namespace std;

//made a structure
struct rectangle
{
    int length;
    int breadth;
};
// assigned values to variables of structures using call by address
void initialize(struct rectangle *r1,int l,int b)
{
    r1->length=l;
    r1->breadth=b;
}
//changed length using call by ref
int changeLength(struct rectangle *r2,int l)
{
    r2->length=l;
}

//used call by value
int area(struct rectangle r)
{
    return r.length*r.breadth;
}
int main()
{

    struct rectangle r;

    //passed address of structure for calling by reference
    initialize(&r,10,20);
    changeLength(&r,30);
    cout<<area(r);
}
