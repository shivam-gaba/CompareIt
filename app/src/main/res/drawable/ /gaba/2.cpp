#include<iostream>
using namespace std;
int main()
{
	inta,b; 	
	cout<<"enter the numbers to swap:";
	cin>>a>>b;
	cout<<"a before swapping :"<<a;
	cout<<"\n b before swapping :"<<b;
	a=a+b;
	b=a-b;
	a=a-b;
	cout<<"\n after swapping a is:"<<a<<"and b is:"<<b; 
	return 0;

