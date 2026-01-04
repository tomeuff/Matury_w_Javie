import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Math.ceil;

class Rekord
{
    public LocalDate data;
    public double temperatura_srednia;
    public double opady;
    public double zbiornik_rano;
    public double zbiornik_po_opadach;
    public double zbiornik_po_parowaniu;
    public double zbiornik_po_podlewaniu;

    public Rekord(LocalDate data, double temperatura_srednia, double opady, double zbiornik_rano, double zbiornik_po_opadach, double zbiornik_po_parowaniu, double zbiornik_po_podlewaniu) {
        this.data = data;
        this.temperatura_srednia = temperatura_srednia;
        this.opady = opady;
        this.zbiornik_rano = zbiornik_rano;
        this.zbiornik_po_opadach = zbiornik_po_opadach;
        this.zbiornik_po_parowaniu = zbiornik_po_parowaniu;
        this.zbiornik_po_podlewaniu = zbiornik_po_podlewaniu;
    }
}

public class zad5 {
    public static void main(String[] args) throws IOException {
        Scanner sc_pogoda = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Dane\\pogoda.txt"));
        BufferedWriter bw_5 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Wyniki\\wyniki5.txt"));
        ArrayList<Rekord> symulacja = new ArrayList<>();
        LocalDate data = LocalDate.of(2015, 4, 1);
        ArrayList<Double> wodociagi = new ArrayList<>();

        sc_pogoda.nextLine();

        while(sc_pogoda.hasNext())
        {
            String[] wiersz = sc_pogoda.nextLine().split("\t");
            if(symulacja.isEmpty())
            {
                symulacja.add(new Rekord(data, Double.parseDouble(wiersz[0].replace(',', '.')), Double.parseDouble(wiersz[1].replace(',', '.')), 25000, 0, 0, 0));
            }
            else
            {
                symulacja.add(new Rekord(data, Double.parseDouble(wiersz[0].replace(',', '.')), Double.parseDouble(wiersz[1].replace(',', '.')), symulacja.getLast().zbiornik_po_podlewaniu, 0, 0, 0));
            }

            symulacja.getLast().zbiornik_po_opadach = Math.min(symulacja.getLast().zbiornik_rano + 700.0 * symulacja.getLast().opady, 25000.0);

            if(symulacja.getLast().opady == 0.0)
            {
                symulacja.getLast().zbiornik_po_parowaniu = symulacja.getLast().zbiornik_po_opadach - ceil(0.0003 * Math.pow(symulacja.getLast().temperatura_srednia, 1.5) * symulacja.get(symulacja.size() - 2).zbiornik_po_podlewaniu);
            }
            else
            {
                symulacja.getLast().zbiornik_po_parowaniu = symulacja.getLast().zbiornik_po_opadach;
            }

            if(symulacja.getLast().temperatura_srednia > 15 && symulacja.getLast().opady <= 0.6)
            {
                double porcja_wymagana = 0.0;

                if(symulacja.getLast().temperatura_srednia <= 30)
                {
                    porcja_wymagana = 12000;
                }
                else
                {
                    porcja_wymagana = 24000;
                }

                if(porcja_wymagana > symulacja.getLast().zbiornik_po_parowaniu)
                {
                    wodociagi.add(25000 - symulacja.getLast().zbiornik_po_parowaniu);
                    symulacja.getLast().zbiornik_po_podlewaniu = 25000 - porcja_wymagana;
                }
                else
                {
                    wodociagi.add(0.0);
                    symulacja.getLast().zbiornik_po_podlewaniu = symulacja.getLast().zbiornik_po_parowaniu - porcja_wymagana;
                }
            }
            else
            {
                symulacja.getLast().zbiornik_po_podlewaniu = symulacja.getLast().zbiornik_po_parowaniu;
                wodociagi.add(0.0);
            }

            data = data.plusDays(1);
        }

        //Zadanie 5.1.
        bw_5.write("5.1.\n");
        bw_5.write(String.format("%s\n", symulacja.get(wodociagi.indexOf(wodociagi.stream().filter(c -> c > 0.0).findFirst().orElse(null))).data.toString()));
        bw_5.write(String.format("%s\n\n", String.valueOf(wodociagi.stream().filter(c -> c > 0.0).findFirst().orElse(null))));

        //Zadanie 5.2.
        bw_5.write("5.2.\n");
        for(Rekord r : symulacja)
        {
            bw_5.write(String.format("%s %f\n", r.data.toString(), r.zbiornik_po_podlewaniu));
        }
        bw_5.newLine();

        //Zadanie 5.3.
        bw_5.write("5.3.\n");
        ArrayList<Double> oplaty = new ArrayList<>();

        for(int m = 0; m < 12; m++)
        {
            oplaty.add(0.0);
            int miesiac = m + 1;
            for(int i = 0; i < symulacja.size(); i++)
            {
                Rekord r = symulacja.get(i);
                if(r.data.getMonthValue() == miesiac)
                {
                    oplaty.set(m, oplaty.getLast() + wodociagi.get(i));
                }
            }
            oplaty.set(m, ceil(oplaty.getLast() / 1000.0) * 11.74);
        }

        for(int m = 3; m < 9; m++)
        {
            bw_5.write(String.format("%d: %f\n", m + 1, oplaty.get(m)));
        }
        bw_5.newLine();

        //Zadanie 5.4.
        bw_5.write("5.4.\n");

        long odp_1 = symulacja.stream().filter(c -> c.temperatura_srednia <= 15).count();
        long odp_2 = symulacja.stream().filter(c -> c.temperatura_srednia > 15 && c.opady <= 0.6).count();
        long odp_3 = symulacja.stream().filter(c -> c.temperatura_srednia > 15 && c.opady > 0.6).count();

        bw_5.write(String.format("%d\n%d\n%d", odp_1, odp_2, odp_3));

        bw_5.close();
    }
}
