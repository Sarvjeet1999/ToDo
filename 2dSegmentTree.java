import java.io.*;
import java.util.*;
public class check1  {

    public static void construct_st1d(int n,int arr[][],int row,int s,int e,int st1d[][],int st1dind)
    {
        if(s>e) return;
        if(s==e)
        {
            st1d[row][st1dind]=arr[row][s];
            return;
        }
        int mid=s+(e-s)/2;
        construct_st1d(n,arr,row,s,mid,st1d,st1dind*2+1);
        construct_st1d(n,arr,row,mid+1,e,st1d,st1dind*2+2);
        st1d[row][st1dind] = st1d[row][st1dind*2+1]+st1d[row][st1dind*2+2];
    }
    public static void construct_st2d(int st1d[][],int s,int e,int st2d[][],int st2dind)
    {
        int m = st1d[0].length;
        if(s>e) return;
        if(s==e)
        {
            for (int i = 0; i < m; i++)
            {
                st2d[st2dind][i] = st1d[s][i];;
            }
            return;
        }
        int mid = s+(e-s)/2;
        construct_st2d(st1d,s,mid,st2d,st2dind*2+1);
        construct_st2d(st1d,mid+1,e,st2d,st2dind*2+2);
        for(int i=0;i<m;i++)
        {
            st2d[st2dind][i] = st2d[st2dind*2+1][i]+st2d[st2dind*2+2][i];
        }
    }
//    (1,2)->(2,4)
//
//    [69, 25, 44, 10, 15, 21, 23, 0, 0, 0, 0, 0, 0, 0, 0]
//    [36, 14, 22, 6, 8, 10, 12, 0, 0, 0, 0, 0, 0, 0, 0]
//    [33, 11, 22, 4, 7, 11, 11, 0, 0, 0, 0, 0, 0, 0, 0]
//    [10, 3, 7, 1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0]
//    [26, 11, 15, 5, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0]
//    [22, 8, 14, 1, 7, 5, 9, 0, 0, 0, 0, 0, 0, 0, 0]
//    [11, 3, 8, 3, 0, 6, 2, 0, 0, 0, 0, 0, 0, 0, 0]

//    [62, 25, 37, 10, 15, 14, 23, 0, 0, 0, 0, 0, 0, 0, 0]
//    [29, 14, 15, 6, 8, 3, 12, 0, 0, 0, 0, 0, 0, 0, 0]
//    [33, 11, 22, 4, 7, 11, 11, 0, 0, 0, 0, 0, 0, 0, 0]
//    [10, 3, 7, 1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0]
//    [19, 11, 8, 5, 6, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0]
//    [22, 8, 14, 1, 7, 5, 9, 0, 0, 0, 0, 0, 0, 0, 0]
//    [11, 3, 8, 3, 0, 6, 2, 0, 0, 0, 0, 0, 0, 0, 0]
    public static int query2(int n,int m,int y1,int y2,int x1,int x2,int st2d[][],int col,int row,int s,int e)
    {
        if(x2<s || x1>e) return 0;
        if(x1<=s && x2>=e)
        {
            return st2d[row][col];
        }
        int mid = s+(e-s)/2;
        int t1 = query2(n,m,y1,y2,x1,x2,st2d,col,2*row+1,s,mid);
        int t2 = query2(n,m,y1,y2,x1,x2,st2d,col,2*row+2,mid+1,e);
        return t1+t2;
    }
    public static int query(int n,int m,int y1,int y2,int x1,int x2,int st2d[][],int col,int s,int e)
    {
        if(y2<s || y1>e) return 0;
        if(y1<=s && y2>=e)
        {
            return query2(n,m,y1,y2,x1,x2,st2d,col,0,0,n-1);
        }
        int mid = s+(e-s)/2;
        int t1 = query(n,m,y1,y2,x1,x2,st2d,2*col+1,s,mid);
        int t2 = query(n,m,y1,y2,x1,x2,st2d,2*col+2,mid+1,e);
        return t1+t2;
    }
    public static void update2(int n,int m,int arr[][],int x,int y,int val,int st2d[][],int col,int row,int s,int e)
    {
        System.out.println(s+" "+e);
        if(s>x || e<x || s>e) return;
        if(s<=x && e>=x)
        {
            st2d[row][col] += val-arr[x][y];
            if(s>=e) return;
        }
        int mid = s+(e-s)/2;
        update2(n,m,arr,x,y,val,st2d,col,2*row+1,s,mid);
        update2(n,m,arr,x,y,val,st2d,col,2*row+2,mid+1,e);
    }
    public static void update(int n,int m,int arr[][],int x,int y,int val,int st2d[][],int col,int s,int e)
    {
        if(e<y || s>y) return;
        if(s<=y && e>=y)
        {
            update2(n,m,arr,x,y,val,st2d,col,0,0,n-1);
            if(s>=e) return;
        }
        int mid = s+(e-s)/2;
        update(n,m,arr,x,y,val,st2d,2*col+1,s,mid);
        update(n,m,arr,x,y,val,st2d,2*col+2,mid+1,e);

    }
    public static void main(String[] args) throws IOException{

        Reader sc=new Reader();
        PrintWriter out = new PrintWriter(System.out);

        int t=sc.nextInt();
//1
//4 4
//1 2 3 4
//5 6 7 8
//1 7 5 9
//3 0 6 2
//4
//2 3 2 3
//1 1 2 3
//0 0 3 3
//1 0 3 2
//1
//1 2 1
//4
//2 3 2 3
//1 1 2 3
//0 0 3 3
//1 0 3 2
        while(t-->0)
        {
            int n=sc.nextInt();
            int m=sc.nextInt();
            int arr[][]=new int[n][m];
            int st1d[][]=new int[n][4*m-1];
            int st2d[][]=new int[4*n-1][4*m-1];

            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    arr[i][j]=sc.nextInt();
                }
                construct_st1d(n,arr,i,0,m-1,st1d,0);
            }
            construct_st2d(st1d,0,n-1,st2d,0);
            for(int i=0;i<st2d.length;i++) out.println(Arrays.toString(st2d[i]));
            out.flush();

            while(true)
            {
                out.println("Press 'q' for query; \n Press 'u' for update; \n Press 'x' to finish");
                out.flush();
                char c = sc.next().charAt(0);
                if(c=='x') break;
                else if(c=='q')
                {
                    out.println("enter no. of queries in 1st line and for each query enter 4 nos.[x1,y1,x2,y2]");
                    out.flush();
                    int q=sc.nextInt();
                    while(q-->0)
                    {
                        int xy[]=new int[4];//[x1,y1,x2,y2]
                        for(int i=0;i<4;i++) xy[i]=sc.nextInt();
                        out.println(query(n,m,xy[1],xy[3],xy[0],xy[2],st2d,0,0,m-1));
                        out.flush();
                    }
                }
                else if(c=='u')
                {
                    out.println("enter x,y and value to be updated in that position");
                    out.flush();
                    int xy[]=new int[2];//[x,y]
                    for(int i=0;i<2;i++) xy[i]=sc.nextInt();
                    int val=sc.nextInt();
                    update(n,m,arr,xy[0],xy[1],val,st2d,0,0,m-1);
                }
            }
            out.println();
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
