import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

class Rekord
{
    LocalDate data;
    int trawa_rano;

    public Rekord(LocalDate data, int trawa_rano, int trawa_po_wywiezieniu, int trawa_po_koszeniu, int trawa_po_ubytku) {
        this.data = data;
        this.trawa_rano = trawa_rano;
        this.trawa_po_wywiezieniu = trawa_po_wywiezieniu;
        this.trawa_po_koszeniu = trawa_po_koszeniu;
        this.trawa_po_ubytku = trawa_po_ubytku;
    }

    int trawa_po_wywiezieniu;
    int trawa_po_koszeniu;
    int trawa_po_ubytku;
}

public class zad4 {
    public static void main(String[] args) throws IOException {
        ArrayList<Rekord> dane = new ArrayList<>();
        BufferedWriter bw_4 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2011\\Wyniki\\zadanie4.txt"));

        LocalDate data = LocalDate.of(2011, 4, 1);
        int liczba_samochodow = 30;

        while(!data.equals(LocalDate.of(2011, 10, 31)))
        {
            dane.add(new Rekord(data, 0, 0, 0, 0));

            if(data.equals(LocalDate.of(2011, 4, 1)))
            {
                dane.getLast().trawa_rano = 10000;
            }
            else
            {
                dane.getLast().trawa_rano = dane.get(dane.size() - 2).trawa_po_ubytku;
            }

            dane.getLast().trawa_po_wywiezieniu = dane.getLast().trawa_rano - liczba_samochodow * 15;

            dane.getLast().trawa_po_koszeniu = dane.getLast().trawa_po_wywiezieniu + 600;

            dane.getLast().trawa_po_ubytku = dane.getLast().trawa_po_koszeniu - (int)(0.03 * dane.getLast().trawa_po_koszeniu);

            data = data.plusDays(1);
        }

        //a)
        bw_4.write("a)\n");
        Rekord rekord_noc_9_10_04 = dane.stream().filter(c -> c.data.equals(LocalDate.of(2011, 4, 9))).findFirst().orElse(null);
        int ubytek_noc_9_10_04 = rekord_noc_9_10_04.trawa_po_koszeniu - rekord_noc_9_10_04.trawa_po_ubytku;
        bw_4.write(String.format("%d\n\n", ubytek_noc_9_10_04));

        //b)
        bw_4.write("b)\n");
        //1)
        boolean czy_ok_1 = false;
        for(int i = 0; i < dane.size() - 1; i++)
        {
            if(dane.get(i).trawa_rano < dane.get(i + 1).trawa_rano)
            {
                czy_ok_1 = true;
                bw_4.write(String.format("1. %d %s\n", i + 2, dane.get(i + 1).data.toString()));
                break;
            }
        }
        if(!czy_ok_1)
        {
            bw_4.write("1. Fałsz\n");
        }

        //2)
        boolean czy_ok_2 = false;
        for(int i = 0; i < dane.size() - 1; i++)
        {
            if(dane.get(i).trawa_rano == dane.get(i + 1).trawa_rano)
            {
                czy_ok_2 = true;
                bw_4.write(String.format("2. %d %s\n", i + 2 - 1, dane.get(i + 1).data.toString()));
                break;
            }
        }
        if(!czy_ok_2)
        {
            bw_4.write("2. Fałsz\n");
        }

        //3)
        boolean czy_ok_3 = false;
        for(int liczba_samochodow2 = 30; liczba_samochodow2 < 10000; liczba_samochodow2++)
        {
            ArrayList<Rekord> dane2 = new ArrayList<>();
            LocalDate data2 = LocalDate.of(2011, 4, 1);

            while(!data2.equals(LocalDate.of(2011, 10, 31)))
            {
                dane2.add(new Rekord(data2, 0, 0, 0, 0));

                if(data2.equals(LocalDate.of(2011, 4, 1)))
                {
                    dane2.getLast().trawa_rano = 10000;
                }
                else
                {
                    dane2.getLast().trawa_rano = dane2.get(dane2.size() - 2).trawa_po_ubytku;
                }

                dane2.getLast().trawa_po_wywiezieniu = dane2.getLast().trawa_rano - liczba_samochodow2 * 15;

                dane2.getLast().trawa_po_koszeniu = dane2.getLast().trawa_po_wywiezieniu + 600;

                dane2.getLast().trawa_po_ubytku = dane2.getLast().trawa_po_koszeniu - (int)(0.03 * dane2.getLast().trawa_po_koszeniu);

                data2 = data2.plusDays(1);
            }

            Rekord rekord_12_04 = dane2.stream().filter(c -> c.data.equals(LocalDate.of(2011, 4, 12))).findFirst().orElse(null);
            if(rekord_12_04.trawa_po_wywiezieniu <= 0)
            {
                czy_ok_3 = true;
                bw_4.write(String.format("3. %d\n\n", liczba_samochodow2));
                break;
            }
        }
        if(!czy_ok_3)
        {
            bw_4.write("3. Fałsz\n\n");
        }

        //Zadanie c)
        bw_4.write("c)\n");

        int liczba_samochodow3 = 30;
        for(int trawa = 10000; trawa >= 4000; trawa -= 3000)
        {
            int nr_dnia = 0;
            ArrayList<Rekord> dane3 = new ArrayList<>();
            LocalDate data3 = LocalDate.of(2011, 4, 1);

            while(!data3.equals(LocalDate.of(2011, 10, 31)))
            {
                nr_dnia++;

                dane3.add(new Rekord(data3, 0, 0, 0, 0));

                if(data3.equals(LocalDate.of(2011, 4, 1)))
                {
                    dane3.getLast().trawa_rano = trawa;
                }
                else
                {
                    dane3.getLast().trawa_rano = dane3.get(dane3.size() - 2).trawa_po_ubytku;
                }

                dane3.getLast().trawa_po_wywiezieniu = dane3.getLast().trawa_rano - liczba_samochodow3 * 15;

                dane3.getLast().trawa_po_koszeniu = dane3.getLast().trawa_po_wywiezieniu + 600;

                dane3.getLast().trawa_po_ubytku = dane3.getLast().trawa_po_koszeniu - (int)(0.03 * dane3.getLast().trawa_po_koszeniu);

                data3 = data3.plusDays(1);

                if(nr_dnia == 101)
                {
                    bw_4.write(String.format("%d\t%d\n", trawa, dane3.get(nr_dnia - 1).trawa_rano));
                }
            }
        }

        bw_4.close();
    }
}
