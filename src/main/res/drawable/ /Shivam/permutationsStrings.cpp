#include <iostream>
using namespace std;

void perm(char S[],int k)
{
static int A[10]={0};
static char res[10];
if(S[k]=='\0')
{
    res[k]='\0';
    cout<<res;
    cout<<endl;

}
else
{
for(int i=0;S[i]!='\0';i++)
{
    if(A[i]==0)
    {
        res[k]=S[i];
        A[i]=1;
        perm(S,k+1);
        A[i]=0;
    }
}
}
}

int main()
{
    char S[]="ABCD";
    perm(S,0);
}
