public class zad2 {
    public static void main(String[] args) {
        int k = 10;
        int n = 40;
        char[] S = {'N', 'K', 'I', '_', 'A', 'T', 'E', '_', 'U', 'S', 'G', 'A', 'C', 'Y', 'O', 'K', 'Z', 'Z', '_', 'Y', 'Y', 'S', 'J', 'T', 'C', 'W', 'E', 'K', 'I', '_', 'S', 'A', 'E', 'M', 'T', 'R', 'L', 'E', '_', 'P'};
        char[] T = {'N', 'K', 'I', '_', 'A', 'T', 'E', '_', 'U', 'S', 'G', 'A', 'C', 'Y', 'O', 'K', 'Z', 'Z', '_', 'Y', 'Y', 'S', 'J', 'T', 'C', 'W', 'E', 'K', 'I', '_', 'S', 'A', 'E', 'M', 'T', 'R', 'L', 'E', '_', 'P'};

        int ind1 = 0;
        int ind2 = 0;

        for(int i = 0; i < n / k; i++)
        {
            for(int j = 0; j < k; j++)
            {
                if(i % 2 == 0)
                {
                    ind1 = i + j * (n / k);
                    T[ind2] = S[ind1];
                    ind2 = ind2 + 1;
                }
                else
                {
                    ind1 = i + (k - j - 1) * (n / k);
                    T[ind2] = S[ind1];
                    ind2 = ind2 + 1;
                }
            }
        }
        System.out.println(new String(T));
    }
}
