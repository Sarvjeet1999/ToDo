import java.io.*;
import java.util.*;
public class check100  {

    public static int segmentTree(int arr[],int stree[],int low,int high,int pos)
    {
//        System.out.println(low+" "+high);
        if(low>high) return Integer.MAX_VALUE;
        if(low==high)
        {
            stree[pos]=arr[low];
            return arr[low];
        }
        int mid=low+(high-low)/2;
        int t1=segmentTree(arr,stree,low,mid,2*pos+1);
        int t2=segmentTree(arr,stree,mid+1,high,2*pos+2);

//        System.out.println(pos);
        stree[pos]=Math.min(t1,t2);
//        System.out.println(pos);

        return stree[pos];
    }
    public static int queryStree(int arr[],int stree[],int low,int high,int pos,int qlow,int qhigh)
    {
        if(high<qlow || low>high) return Integer.MAX_VALUE;
        if(low>=qlow && high<=qhigh)
        {
            return stree[pos];
        }
        int mid=low+(high-low)/2;
        int t1 = queryStree(arr,stree,low,mid,2*pos+1,qlow,qhigh);
        int t2 = queryStree(arr,stree,mid+1,high,2*pos+2,qlow,qhigh);

        return Math.min(t1,t2);
    }
    public static void update(int arr[],int stree[],int low,int high,int pos,int ind,int val)
    {
        if(high<ind || low>ind) return;
        if(low==high)
        {
            arr[low]=val;
            stree[pos]=val;
            return;
        }
        int mid=low+(high-low)/2;
        update(arr,stree,low,mid,2*pos+1,ind,val);
        update(arr,stree,mid+1,high,2*pos+2,ind,val);
        stree[pos]=Math.min(stree[2*pos+1],stree[2*pos+2]);
    }
//    public static void updateRange()
    public static void main(String[] args) throws IOException{

        Reader sc=new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int t=sc.nextInt();

        while(t-->0)
        {
            int n=sc.nextInt();
            int arr[]=new int[n];
            for(int i=0;i<n;i++)
            {
                arr[i]=sc.nextInt();
            }
            // min segment tree
            int t1=(int)(Math.log(n)/Math.log(2));
            int t2=(int)(Math.pow(t1,2));

            if(t2<n) t2*=2;

            int len1=t2+(t2-1);

            int stree[]=new int[len1];
            Arrays.fill(stree,Integer.MAX_VALUE);
            segmentTree(arr,stree,0,n-1,0);

            int res1 = queryStree(arr,stree,0,n-1,0,1,3);
            out.println(res1);
            out.println(Arrays.toString(arr)+" "+Arrays.toString(stree));

            update(arr,stree,0,n-1,0,0,5);
            int res2 = queryStree(arr,stree,0,n-1,0,1,3);
            out.println(res2);
            out.println(Arrays.toString(arr)+" "+Arrays.toString(stree));
        }
        out.flush();
    }

    public static long power(long x,long power)
    {
        if(power==0) return 1;

        long ans1 = power(x,power/2);
        ans1=ans1*ans1;

        if(power%2==0) return ans1;
        return x*ans1;
    }

    //    (a*a_inverse) = 1(mod m)
//    finding a_inverse
    public static long modInverse(long a,long m)
    {
//        works only when m is prime
        long g=gcd(a,m);
        if(g==1) return power(a,m-2,m);
        else return -1;
    }

    //    (x^power)mod(m)
    public static long power(long x,long power,long m)
    {
        if(power==0) return 1;

        long p = power(x,power/2,m)%m;
        p = (p*p)%m;

        if(power%2==0) return p;
        return (x*p)%m;
    }
    public static long gcd(long small,long large)
    {
        if(small==0) return large;
        return gcd(large % small,small);
    }

    public static boolean isprime(long no)
    {
        if(no<=1) return false;
        if(no<=3) return true;
        if(no%2==0 || no%3==0) return false;
        // 6k+1 6k+5 can be prime
        for(long i=5;i*i<=no;i+=6)
        {
            if(no%(i)==0 || no%(i+2)==0)
                return false;
        }
        return true;
    }

    //    prime no smaller than or equal to n
    public static boolean[] prime_nos_till_no(int no)
    {
        boolean prime[]=new boolean[no+1];
//        prime[i]== true means prime
//        prime[i]== false means not a prime
//        initialize prime array as true

        Arrays.fill(prime,true);
        prime[0]=false;
        for(int i=2;i*i<=no;i++)
        {
            if(prime[i]==true)
            {
                for(int j=i*i;j<=no;j+=i)
                {
                    prime[j]=false;
                }
            }
        }
        return prime;

    }
    public static void shufflearray(long arr[])
    {


        int n=arr.length;
        Random rand=new Random();
        for(int i=0;i<n;i++)
        {
            long temp=arr[i];
            int randomPos=i+rand.nextInt(n-i);
            arr[i]=arr[randomPos];
            arr[randomPos]=temp;
        }
    }
    //       Arrays.sort(arr, new Comparator<pair>() {
//        //@Override
//        public int compare(pair o1, pair o2) {
//            long l1=o1.a-o2.a;
//            if(l1<0L) return -1;
//            if(l1==0) return 0;
//            return 1;
//        }
//    });
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String nextLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }
        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() throws IOException{
            int c = read();
            while (isSpaceChar(c)) c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }


        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }

    }

}
