// Binary Search Methods

// METHOD-1:
// s=0,e=n-1;
public static void function1(int n,int arr[],int s,int e)
{
  if(s>=e) return;
  int mid = s+(e-s)/2;
  function1(n,arr,s,mid);
  function1(n,arr,mid+1,e);
}

// METHOD-2:
// s=0,e=n-1;
public static void function2(int n,int arr[],int s,int e)
{
  if(s>=e) return;
  int mid = s+(e-s+1)/2;
  function1(n,arr,s,mid-1);
  function1(n,arr,mid,e);
}
