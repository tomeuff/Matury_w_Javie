public class zad1 {
    public static boolean czy_k_podobne(int n, int[] A, int[] B, int k)
    {
        boolean czy_ok = true;
        if(k == 0)
        {
            for(int i = 1; i <= n; i++)
            {
                if(A[i] != B[i])
                {
                    czy_ok = false;
                }
            }
        }
        else
        {
            for(int i = 1; i <= k; i++)
            {
                if(A[i] != B[n - k + i])
                {
                    czy_ok = false;
                }
            }
            for(int i = k + 1; i <= n; i++)
            {
                if(A[i] != B[i - k])
                {
                    czy_ok = false;
                }
            }
        }
        return czy_ok;
    }
    public static void main(String[] args) {
        int[] A = {-1, 4, 2, 4, 4, 2, 6};
        int[] B = {-1, 4, 4, 2, 6, 4, 2};
        int n = A.length - 1;
        int k = 1;
        System.out.println(czy_k_podobne(n, A, B, k));
    }
}
