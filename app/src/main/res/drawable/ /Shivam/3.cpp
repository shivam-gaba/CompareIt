#include <iostream>
using namespace std;
template <class v>
class action
{
private:
    v a;
    v b;

public:
    action(v a,v b);
    v add();
    v sub();
};
template <class v>
v action<v>::add()
{
    return a+b;
}
template <class v>
v action<v>::sub()
{
    return a-b;
}
int main()
{
 action<int> a(10,20);
 cout<<a.add()<<endl;
 action<double> b(20.42,78.00);
 cout<<a.add()<<endl;
}
