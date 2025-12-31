import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

class Rekord
{
    public LocalDate data;
    public int siano_rano;
    public int siano_po_karmieniu;
    public int siano_po_dostawie;
    public int zoledzie_rano;
    public int zoledzie_po_karmieniu;
    public int zoledzie_po_dostawie;
    public int dzien_tyg;

    public Rekord(LocalDate data, int siano_rano, int siano_po_karmieniu, int siano_po_dostawie, int zoledzie_rano, int zoledzie_po_karmieniu, int zoledzie_po_dostawie, int dzien_tyg) {
        this.data = data;
        this.siano_rano = siano_rano;
        this.siano_po_karmieniu = siano_po_karmieniu;
        this.siano_po_dostawie = siano_po_dostawie;
        this.zoledzie_rano = zoledzie_rano;
        this.zoledzie_po_karmieniu = zoledzie_po_karmieniu;
        this.zoledzie_po_dostawie = zoledzie_po_dostawie;
        this.dzien_tyg = dzien_tyg;
    }
}

public class zad4 {
    public static void main(String[] args) throws IOException {
        int liczba_zubrow = 90;
        LocalDate data = LocalDate.of(2012, 12, 1);
        int siano_start = 100000;
        int zoledzie_start = 5000;
        int dzien_tygodnia = 6;
        ArrayList<Rekord> symulacja = new ArrayList<>();

        BufferedWriter bw_4 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj 2013\\Wyniki\\wyniki4.txt"));

        while(!data.equals(LocalDate.of(2013, 3, 1)))
        {
            if(symulacja.isEmpty())
            {
                symulacja.add(new Rekord(data, siano_start, 0, 0, zoledzie_start, 0, 0, dzien_tygodnia));
            }
            else
            {
                symulacja.add(new Rekord(data, symulacja.getLast().siano_po_dostawie, 0, 0, symulacja.getLast().zoledzie_po_dostawie, 0, 0, dzien_tygodnia));
            }

            if(symulacja.getLast().siano_rano >= 50000)
            {
                symulacja.getLast().siano_po_karmieniu = symulacja.getLast().siano_rano -  40 * liczba_zubrow;
                symulacja.getLast().zoledzie_po_karmieniu = symulacja.getLast().zoledzie_rano;
            }
            else
            {
                symulacja.getLast().siano_po_karmieniu = symulacja.getLast().siano_rano;
                symulacja.getLast().zoledzie_po_karmieniu = symulacja.getLast().zoledzie_rano - 20 * liczba_zubrow;
            }

            if(dzien_tygodnia == 5)
            {
                symulacja.getLast().siano_po_dostawie = symulacja.getLast().siano_po_karmieniu + 15000;
            }
            else
            {
                symulacja.getLast().siano_po_dostawie = symulacja.getLast().siano_po_karmieniu;
            }

            if(dzien_tygodnia == 2)
            {
                symulacja.getLast().zoledzie_po_dostawie = symulacja.getLast().zoledzie_po_karmieniu + 4000;
            }
            else
            {
                symulacja.getLast().zoledzie_po_dostawie = symulacja.getLast().zoledzie_po_karmieniu;
            }


            data = data.plusDays(1);
            dzien_tygodnia = dzien_tygodnia % 7 + 1;
        }

        //Zadanie a)
        long l_dostaw_siana = symulacja.stream().filter(c -> c.dzien_tyg == 5).count();
        long l_dostaw_zoledzi = symulacja.stream().filter(c -> c.dzien_tyg == 2).count();
        bw_4.write("a)\n");
        bw_4.write(String.format("siano: %d\n", l_dostaw_siana));
        bw_4.write(String.format("żołędzie: %d\n\n", l_dostaw_zoledzi));

        //Zadanie b)
        bw_4.write("b)\n");
        for(Rekord r : symulacja)
        {
            if(r.siano_po_karmieniu == r.siano_rano)
            {
                bw_4.write(String.format("%s\n\n", r.data.toString()));
                break;
            }
        }

        //Zadanie c)
        long tylko_sianem = symulacja.stream().filter(c -> c.zoledzie_rano == c.zoledzie_po_karmieniu).count();
        long tylko_zoledziami = symulacja.stream().filter(c -> c.siano_rano == c.siano_po_karmieniu).count();

        bw_4.write("c)\n");
        bw_4.write(String.format("tylko siano: %d dni\n", tylko_sianem));
        bw_4.write(String.format("tylko żołędzie: %d dni\n\n", tylko_zoledziami));

        //Zadanie d)
        bw_4.write("d)\n");
        Rekord r1 = symulacja.stream().filter(c -> c.data.toString().equals("2012-12-31")).findFirst().orElse(null);
        Rekord r2 = symulacja.stream().filter(c -> c.data.toString().equals("2013-01-31")).findFirst().orElse(null);
        Rekord r3 = symulacja.stream().filter(c -> c.data.toString().equals("2013-02-28")).findFirst().orElse(null);

        bw_4.write(String.format("%s %.2f %.2f\n", r1.data, r1.siano_rano / 1000.0f, r1.zoledzie_rano / 1000.0f));
        bw_4.write(String.format("%s %.2f %.2f\n", r2.data, r2.siano_rano / 1000.0f, r2.zoledzie_rano / 1000.0f));
        bw_4.write(String.format("%s %.2f %.2f\n\n", r3.data, r3.siano_rano / 1000.0f, r3.zoledzie_rano / 1000.0f));

        //Zadanie e)
        bw_4.write("e)\n");
        int wynik = 0;

        for(int liczba_zubrow2 = 90; liczba_zubrow2 < 1000000; liczba_zubrow2++)
        {
            LocalDate data2 = LocalDate.of(2012, 12, 1);
            int siano_start2 = 100000;
            int zoledzie_start2 = 5000;
            int dzien_tygodnia2 = 6;
            ArrayList<Rekord> symulacja2 = new ArrayList<>();

            while(!data2.equals(LocalDate.of(2013, 3, 1)))
            {
                if(symulacja2.isEmpty())
                {
                    symulacja2.add(new Rekord(data2, siano_start2, 0, 0, zoledzie_start2, 0, 0, dzien_tygodnia2));
                }
                else
                {
                    symulacja2.add(new Rekord(data2, symulacja2.getLast().siano_po_dostawie, 0, 0, symulacja2.getLast().zoledzie_po_dostawie, 0, 0, dzien_tygodnia2));
                }

                if(symulacja2.getLast().siano_rano >= 50000)
                {
                    symulacja2.getLast().siano_po_karmieniu = symulacja2.getLast().siano_rano -  40 * liczba_zubrow2;
                    symulacja2.getLast().zoledzie_po_karmieniu = symulacja2.getLast().zoledzie_rano;
                }
                else
                {
                    symulacja2.getLast().siano_po_karmieniu = symulacja2.getLast().siano_rano;
                    symulacja2.getLast().zoledzie_po_karmieniu = symulacja2.getLast().zoledzie_rano - 20 * liczba_zubrow2;
                }

                if(dzien_tygodnia2 == 5)
                {
                    symulacja2.getLast().siano_po_dostawie = symulacja2.getLast().siano_po_karmieniu + 15000;
                }
                else
                {
                    symulacja2.getLast().siano_po_dostawie = symulacja2.getLast().siano_po_karmieniu;
                }

                if(dzien_tygodnia2 == 2)
                {
                    symulacja2.getLast().zoledzie_po_dostawie = symulacja2.getLast().zoledzie_po_karmieniu + 4000;
                }
                else
                {
                    symulacja2.getLast().zoledzie_po_dostawie = symulacja2.getLast().zoledzie_po_karmieniu;
                }


                data2 = data2.plusDays(1);
                dzien_tygodnia2 = dzien_tygodnia2 % 7 + 1;
            }
            long ile_ujemnych = symulacja2.stream().filter(c -> c.zoledzie_po_karmieniu < 0).count();
            if(ile_ujemnych > 0)
            {
                wynik = liczba_zubrow2 - 1;
                break;
            }
        }
        bw_4.write(String.format("%d", wynik - 90));
        bw_4.close();
    }
}
