public class zad1 {
    public static void main(String[] args) {
        int[] A = {5, 4, 24, 4, 8};
        int n = A.length;

        int w = 0;
        int p = 0;
        int q = n - 1;

        while(p <= q)
        {
            int s = (p + q) / 2;
            if(A[s] % 2 == 0)
            {
                if(A[s - 1] % 2 == 1)
                {
                    w = A[s];
                    break;
                }
                else
                {
                    q = s - 1;
                }
            }
            else
            {
                p = s + 1;
            }
        }

        System.out.println(w);
    }
}
