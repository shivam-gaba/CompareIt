

  // anagram

  #include <iostream>
using namespace std;
int main()
{
    int i;
char A[]="mavissh";
char B[]="shivasm";
int C[26]={0};

for(i=0;A[i]!='\0';i++)
{
    C[A[i]-97]++;
}
for(i=0;B[i]!='\0';i++)
{
    C[B[i]-97]--;

    if(C[B[i]-97]==-1)
    {
        cout<<"Not an anagram";
        break;
    }


}
if(B[i]=='\0')
{
cout<<"its an anagram"<<endl;
}
}

