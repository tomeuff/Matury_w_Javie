import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Rekord
{
    public int dzien;
    public double Temperatura;
    public double Opad;
    public String Kategoria_chmur;
    public int Wielkosc_chmur;
    public String Kategoria_chmur_progn;
    public int Wielkosc_chmur_progn;

    public Rekord(int dzien, double temperatura, double opad, String kategoria_chmur, int wielkosc_chmur, String kategoria_chmur_progn, int wielkosc_chmur_progn) {
        this.dzien = dzien;
        Temperatura = temperatura;
        Opad = opad;
        Kategoria_chmur = kategoria_chmur;
        Wielkosc_chmur = wielkosc_chmur;
        Kategoria_chmur_progn = kategoria_chmur_progn;
        Wielkosc_chmur_progn = wielkosc_chmur_progn;
    }
}
public class zad5 {
    public static void main(String[] args) throws IOException {
        Scanner sc_pogoda = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Dane\\pogoda.txt"));
        ArrayList<Rekord> dane = new ArrayList<>();
        BufferedWriter bw_5 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Wyniki\\wyniki5.txt"));

        sc_pogoda.nextLine();

        while (sc_pogoda.hasNextLine())
        {
            String[] wiersz = sc_pogoda.nextLine().split(";");
            dane.add(new Rekord(Integer.parseInt(wiersz[0]), Double.parseDouble(wiersz[1].replace(',', '.')), Double.parseDouble(wiersz[2].replace(',', '.')), wiersz[3], Integer.parseInt(wiersz[4]), "", 0));
        }

        //Zadanie 5.1.
        bw_5.write("5.1.\n");
        bw_5.write(String.format("%d\n\n", dane.stream().filter(c-> c.Temperatura >= 20 && c.Opad <= 5).count()));

        //Zadanie 5.2.
        bw_5.write("5.2.\n");
        ArrayList<Integer> ciag = new ArrayList<>();
        ciag.add(0);

        for(int i = 1; i < dane.size(); i++)
        {
            if(dane.get(i).Temperatura > dane.get(i - 1).Temperatura)
            {
                ciag.add(ciag.getLast() + 1);
            }
            else
            {
                ciag.add(0);
            }
        }
        int max_dl = Collections.max(ciag);
        int max_koniec = ciag.indexOf(max_dl) + 1;
        int max_poczatek = max_koniec - max_dl + 1;

        bw_5.write(String.format("%d\n%d\n\n", max_poczatek, max_koniec));

        //zadanie 5.3.
        bw_5.write("5.3.\n");
        ArrayList<String> kategorie = new ArrayList<>(List.of(new String[]{"C", "S"}));

        for(String k : kategorie)
        {
            for(int w = 1; w <= 5; w++)
            {
                double suma_opadow = 0.0;
                int ilosc = 0;

                for(Rekord r : dane)
                {
                    if(r.Kategoria_chmur.equals(k) && r.Wielkosc_chmur == w)
                    {
                        ilosc++;
                        suma_opadow += r.Opad;
                    }
                }

                bw_5.write(String.format("%s%d %.2f\n", k, w, suma_opadow / ilosc));
            }
        }
        bw_5.newLine();

        //Zadanie 5.4.
        bw_5.write("5.4.\n");

        for(int i = 0; i < 20; i++)
        {
            dane.get(i).Kategoria_chmur_progn = dane.get(i).Kategoria_chmur;
            dane.get(i).Wielkosc_chmur_progn = dane.get(i).Wielkosc_chmur;
        }

        for(int i = 20; i < dane.size(); i++)
        {
            if(dane.get(i - 1).Wielkosc_chmur_progn == 0)
            {
                dane.get(i).Wielkosc_chmur_progn = 1;
                if(dane.get(i).Temperatura >= 10)
                {
                    dane.get(i).Kategoria_chmur_progn = "C";
                }
                else
                {
                    dane.get(i).Kategoria_chmur_progn = "S";
                }
            }
            else
            {
                if(dane.get(i - 1).Wielkosc_chmur_progn == 5)
                {
                    if(dane.get(i - 1).Opad >= 20)
                    {
                        dane.get(i).Wielkosc_chmur_progn = 0;
                        dane.get(i).Kategoria_chmur_progn = "0";
                    }
                    else
                    {
                        dane.get(i).Wielkosc_chmur_progn = 5;
                        dane.get(i).Kategoria_chmur_progn = dane.get(i - 1).Kategoria_chmur_progn;
                    }
                }
                else if(dane.get(i - 3).Wielkosc_chmur_progn == dane.get(i - 2).Wielkosc_chmur_progn &&
                        dane.get(i - 2).Wielkosc_chmur_progn == dane.get(i - 1).Wielkosc_chmur_progn &&
                        dane.get(i - 1).Wielkosc_chmur_progn < 5)
                {
                    dane.get(i).Wielkosc_chmur_progn = dane.get(i - 1).Wielkosc_chmur_progn + 1;
                    dane.get(i).Kategoria_chmur_progn = dane.get(i - 1).Kategoria_chmur_progn;
                }
                else
                {
                    dane.get(i).Wielkosc_chmur_progn = dane.get(i - 1).Wielkosc_chmur_progn;
                    dane.get(i).Kategoria_chmur_progn = dane.get(i - 1).Kategoria_chmur_progn;
                }
            }

        }
        bw_5.write("a)\n");
        for(int w = 0; w <= 5; w++)
        {
            int finalW = w;
            bw_5.write(String.format("%d: %d\n", w, (int) dane.stream().filter(c -> c.Wielkosc_chmur_progn == finalW).count()));
        }

        bw_5.write("b)\n");
        bw_5.write(String.format("%d\n", dane.subList(0, 300).stream().filter(c -> c.Wielkosc_chmur_progn == c.Wielkosc_chmur).count()));

        bw_5.write("c)\n");
        bw_5.write(String.format("%d\n", dane.subList(0, 300).stream().filter(c -> c.Kategoria_chmur_progn.equals(c.Kategoria_chmur)).count()));

        bw_5.close();
    }
}
