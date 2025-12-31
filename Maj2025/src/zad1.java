public class zad1 {
    public static long licznik = 0;
    public static long przestaw(long n)
    {
        licznik++;
        long r = n % 100;
        long a = r / 10;
        long b = r % 10;
        n = n / 100;
        long w = 0;
        if(n > 0)
        {
            w = a + 10 * b + 100 * przestaw(n);
        }
        else
        {
            if(a > 0)
            {
                w = a + 10 * b;
            }
            else
            {
                w = b;
            }
        }
        return w;
    }

    public static long przestaw2(long n)
    {
        long wynik = 0;
        long pot10 = 1;
        while(n > 9)
        {
            long reszta = n % 100;
            long c1 = reszta % 10;
            reszta /= 10;
            long c2 = reszta;
            wynik = wynik + pot10 * (10 * c1 + c2);
            pot10 *= 100;
            n /= 100;
        }
        if(n > 0)
        {
            wynik = wynik + pot10 * n;
            n /= 10;
        }
        return wynik;
    }

    public static void main(String[] args) {
//        double n = 998877665544321.0;
//        System.out.println(n);
//        System.out.println(przestaw((long)n));
//        System.out.println(licznik);

        System.out.println(przestaw2((long)998877665544321.0));

    }
}
