
//   C++ code or classes and its member function and their calling using objects

#include <iostream>
using namespace std;

class rectangle
{
private:
    int length;
    int breadth;

public:
    rectangle():length(20),breadth(30){}
    rectangle(int l,int b);
    int getLength();
    int area();
    void setLength(int l);
    ~rectangle();
};

rectangle::rectangle(int l,int b):length(l),breadth(b)
{
}
int rectangle::area()
{
        return length*breadth;
}
void rectangle::setLength(int l)
{
    length=l;
}
int rectangle::getLength()
{
    return length;
}
rectangle::~rectangle()
{
}
int main()
{
    rectangle r(10,30);
    r.setLength(30);
    cout<<r.getLength()<<endl;
    cout<<r.area();

}
