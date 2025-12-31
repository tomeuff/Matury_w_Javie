import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Samochod
{
    public String Nr_ew;
    public String Nr_firmowy;
    public String Miejscowosc;
    public String Nr_rej;

    public Samochod(String nr_ew, String nr_firmowy, String miejscowosc, String nr_rej) {
        Nr_ew = nr_ew;
        Nr_firmowy = nr_firmowy;
        Miejscowosc = miejscowosc;
        Nr_rej = nr_rej;
    }
}

class Cena_za_dobe
{
    public String Klasa;
    public double Cena;

    public Cena_za_dobe(String klasa, double cena) {
        Klasa = klasa;
        Cena = cena;
    }
}

class Klient
{
    public String Nr_klienta;
    public String Imie;
    public String Nazwisko;

    public Klient(String nr_klienta, String imie, String nazwisko) {
        Nr_klienta = nr_klienta;
        Imie = imie;
        Nazwisko = nazwisko;
    }
}

class Wypozyczenie
{
    public String Nr_ew;
    public String Nr_klienta;
    public LocalDate Wypozyczenie;
    public LocalDate Zwrot;

    public Wypozyczenie(String nr_ew, String nr_klienta, LocalDate wypozyczenie, LocalDate zwrot) {
        Nr_ew = nr_ew;
        Nr_klienta = nr_klienta;
        Wypozyczenie = wypozyczenie;
        Zwrot = zwrot;
    }
}

public class zad6 {
    public static void main(String[] args) throws IOException {
        Scanner sc_samochody = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Dane\\samochody.txt"));
        Scanner sc_ceny_za_dobe = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Dane\\ceny_za_dobe.txt"));
        Scanner sc_wypozyczenia = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Dane\\wypozyczenia.txt"));
        Scanner sc_klienci = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Dane\\klienci.txt"));

        ArrayList<Samochod> samochody = new ArrayList<>();
        ArrayList<Cena_za_dobe> ceny_za_dobe = new ArrayList<>();
        ArrayList<Wypozyczenie> wypozyczenia = new ArrayList<>();
        ArrayList<Klient> klienci = new ArrayList<>();

        sc_samochody.nextLine();
        while(sc_samochody.hasNextLine())
        {
            String[] wiersz = sc_samochody.nextLine().split(";");
            samochody.add(new Samochod(wiersz[0], wiersz[1], wiersz[2], wiersz[3]));
        }

        sc_ceny_za_dobe.nextLine();
        while(sc_ceny_za_dobe.hasNextLine())
        {
            String[] wiersz = sc_ceny_za_dobe.nextLine().split(";");
            ceny_za_dobe.add(new Cena_za_dobe(wiersz[0], Double.parseDouble(wiersz[1])));
        }

        sc_wypozyczenia.nextLine();
        while(sc_wypozyczenia.hasNextLine())
        {
            String[] wiersz = sc_wypozyczenia.nextLine().split(";");
            wypozyczenia.add(new Wypozyczenie(wiersz[0], wiersz[1], LocalDate.parse(wiersz[2]), LocalDate.parse(wiersz[3])));
        }

        sc_klienci.nextLine();
        while(sc_klienci.hasNextLine())
        {
            String[] wiersz = sc_klienci.nextLine().split(";");
            klienci.add(new Klient(wiersz[0], wiersz[1], wiersz[2]));
        }

        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Wyniki\\wyniki6.txt"));

        //Zadanie 6.1.
        bw_6.write("6.1.\n");
        ArrayList<String> wynik6_1 = new ArrayList<>();
        for(Klient k : klienci)
        {
            for(Wypozyczenie w : wypozyczenia)
            {
                for(Samochod s : samochody)
                {
                    for(Cena_za_dobe c : ceny_za_dobe)
                    {
                        if(w.Nr_ew.equals(s.Nr_ew) && c.Klasa.charAt(0) == s.Nr_firmowy.charAt(0))
                        {
                            if(k.Nr_klienta.equals(w.Nr_klienta))
                            {
                                wynik6_1.add(k.Nazwisko + " " + k.Imie + " " + s.Nr_rej + " " + String.format("%d", ChronoUnit.DAYS.between(w.Wypozyczenie, w.Zwrot)) + " " + ChronoUnit.DAYS.between(w.Wypozyczenie, w.Zwrot) * c.Cena);
                            }
                        }
                    }
                }
            }
        }

        Collections.sort(wynik6_1);
        bw_6.write(String.format("%s\n", wynik6_1.getFirst()));
        bw_6.write(String.format("%s\n\n", wynik6_1.getLast()));

        //Zadanie 6.2.
        bw_6.write("6.2.\n");
        for(Cena_za_dobe c : ceny_za_dobe)
        {
            int ile_sam = 0;
            for(Wypozyczenie w : wypozyczenia)
            {
                for(Samochod s : samochody)
                {
                    if(w.Nr_ew.equals(s.Nr_ew) && c.Klasa.charAt(0) == s.Nr_firmowy.charAt(0))
                    {
                        ile_sam++;
                    }
                }
            }
            bw_6.write(String.format("%s %d\n", c.Klasa, ile_sam));
        }

        bw_6.newLine();

        //Zadanie 6.3.
        bw_6.write("6.3.\n");
        int max_l_wyp = 0;
        for(Klient k : klienci)
        {
            int ile_wyp = 0;
            for(Wypozyczenie w : wypozyczenia)
            {
                if(w.Nr_klienta.equals(k.Nr_klienta))
                {
                    ile_wyp++;
                }
            }
            if(ile_wyp > max_l_wyp)
            {
                max_l_wyp = ile_wyp;
            }
        }

        for(Klient k : klienci)
        {
            int ile_wyp = 0;
            for(Wypozyczenie w : wypozyczenia)
            {
                if(w.Nr_klienta.equals(k.Nr_klienta))
                {
                    ile_wyp++;
                }
            }
            if(ile_wyp == max_l_wyp)
            {
                bw_6.write(String.format("%s %s %d\n", k.Imie, k.Nazwisko, max_l_wyp));
            }
        }
        bw_6.newLine();

        //Zadanie 6.4.
        bw_6.write("6.4.\n");
        HashMap<String, Integer> km = new HashMap<>();
        for(Samochod s : samochody)
        {
            boolean czy_byl = false;
            for(Wypozyczenie w : wypozyczenia)
            {
                if(Objects.equals(s.Nr_ew, w.Nr_ew))
                {
                    czy_byl = true;
                    break;
                }
            }
            if(!czy_byl)
            {
                String klucz = s.Nr_firmowy.charAt(0) + " " + s.Miejscowosc;
                if(km.containsKey(klucz))
                {
                    km.put(klucz, km.get(klucz) + 1);
                }
                else
                {
                    km.put(klucz, 1);
                }
            }
        }

        for(Map.Entry<String, Integer> e : km.entrySet())
        {
            bw_6.write(String.format("%s %d\n", e.getKey(), e.getValue()));
        }

        bw_6.newLine();

        //Zadanie 6.5.
        bw_6.write("6.5.\n");
        int ilu_nie_bylo = 0;
        for(Klient k : klienci)
        {
            boolean czy_byl = false;
            for(Wypozyczenie w : wypozyczenia)
            {
                if(w.Nr_klienta.equals(k.Nr_klienta))
                {
                    czy_byl = true;
                }
            }
            if(!czy_byl)
            {
                ilu_nie_bylo++;
            }
        }
        bw_6.write(String.format("%d", ilu_nie_bylo));

        bw_6.close();
    }
}
