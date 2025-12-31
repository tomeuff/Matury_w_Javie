import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

class Rekord
{
    public LocalDate data;
    public LocalTime godzina;
    public ArrayList<Double> czujnik;

    public Rekord(LocalDate data, LocalTime godzina, ArrayList<Double> czujnik) {
        this.data = data;
        this.godzina = godzina;
        this.czujnik = czujnik;
    }
}

public class zad5 {
    public static void main(String[] args) throws IOException {
        Scanner sc_pomiary = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Dane\\pomiary.txt"));
        ArrayList<Rekord> dane = new ArrayList<>();

        sc_pomiary.nextLine();

        while(sc_pomiary.hasNextLine())
        {
            String[] wiersz = sc_pomiary.nextLine().split(";");
            LocalDate data = LocalDate.parse(wiersz[0]);
            LocalTime godzina = LocalTime.parse(wiersz[1]);
            ArrayList<Double> czujnik = Arrays.stream(wiersz).toList().subList(2, wiersz.length).stream().map(c -> Double.parseDouble(c.replace(',', '.'))).collect(Collectors.toCollection(ArrayList::new));
            dane.add(new Rekord(data, godzina, czujnik));
        }

        BufferedWriter bw_5 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Wyniki\\wyniki5.txt"));

        //Zadanie 5.1.
        double suma_temp = 0.0;
        int ile = 0;
        for(Rekord r : dane)
        {
            if(r.godzina.isAfter(LocalTime.parse("04:59")) && r.godzina.isBefore(LocalTime.parse("12:01")))
            {
                suma_temp += r.czujnik.get(4);
                ile++;
            }
        }
        bw_5.write("5.1.\n");
        bw_5.write(String.format("%.2f\n\n", suma_temp / ile));

        //Zadanie 5.2.
        ArrayList<Rekord> dane2 = new ArrayList<>();
        for(Rekord r : dane)
        {
            dane2.add(new Rekord(LocalDate.of(r.data.getYear(), r.data.getMonth(), r.data.getDayOfMonth()), LocalTime.of(r.godzina.getHour(), r.godzina.getMinute()), new ArrayList<>(r.czujnik)));
        }

        bw_5.write("5.2.\n");

        for(int i = 0; i < dane2.size(); i++)
        {
            for(int j = 0; j < 10; j++)
            {
                dane2.get(i).czujnik.set(j, Math.floor(dane2.get(i).czujnik.get(j) + 273.15));
            }
        }

        for(int i = 0; i < 10; i++)
        {
            ArrayList<Double> temperatury = new ArrayList<>();
            for(int j = 0; j < dane2.size(); j++)
            {
                temperatury.add(dane2.get(j).czujnik.get(i));
            }

            Double max_temp = Collections.max(temperatury, Comparator.comparing(c -> temperatury.stream().filter(d -> Objects.equals(d, c)).count()));
            bw_5.write(String.format("czujnik %d: %f\n", i + 1, max_temp));
        }
        bw_5.newLine();

        //Zadanie 5.3.
        bw_5.write("5.3.\n");

        for(int m = 1; m <= 12; m++)
        {
            int ile_w_mies = 0;
            double suma_w_mies = 0.0;
            for(int i = 0; i < dane.size(); i++) {
                if (dane.get(i).data.getMonthValue() == m) {
                    ile_w_mies++;
                    suma_w_mies += dane.get(i).czujnik.get(9);
                }
            }

            bw_5.write(String.format("%d %.2f\n", m, suma_w_mies / (double) ile_w_mies));
        }
        bw_5.newLine();

        //Zadanie 5.4.
        bw_5.write("5.4\n");
        int ile_minus_10_pietnascie = 0;
        int ile_pietnascie_dwadziescia = 0;

        for(Rekord r : dane)
        {
            for(double w : r.czujnik)
            {
                if(w > -10.0 && w <= 15.0)
                {
                    ile_minus_10_pietnascie++;
                }
                if(w > 15.0 && w <= 20.0)
                {
                    ile_pietnascie_dwadziescia++;
                }
            }
        }

        if(ile_minus_10_pietnascie > ile_pietnascie_dwadziescia)
        {
            bw_5.write(String.format("(-10, 15>: %d\n\n", ile_minus_10_pietnascie));
        }
        else
        {
            bw_5.write(String.format("(15, 20>: %d\n\n", ile_pietnascie_dwadziescia));
        }

        //Zadanie 5.5.
        ArrayList<Rekord> dane3 = new ArrayList<>();
        bw_5.write("5.5.\n");
        for(Rekord r : dane)
        {
            dane3.add(new Rekord(LocalDate.of(r.data.getYear(), r.data.getMonth(), r.data.getDayOfMonth()), LocalTime.of(r.godzina.getHour(), r.godzina.getMinute()), new ArrayList<>(r.czujnik)));
        }

        for(Rekord r1 : dane3)
        {
            r1.data = r1.data.plusYears(1);
            if(r1.data.getDayOfMonth() >= 5 && r1.data.getDayOfMonth() <= 10)
            {
                r1.czujnik.set(0, r1.czujnik.get(0) - 1.2);
                r1.czujnik.set(1, r1.czujnik.get(1) - 1.2);
                r1.czujnik.set(8, r1.czujnik.get(8) - 1.2);
            }

            if(r1.data.getMonthValue() == 7 || r1.data.getMonthValue() == 8)
            {
                r1.czujnik.set(7, r1.czujnik.get(7) + Double.parseDouble(String.format("%.2f", 0.07 * r1.czujnik.get(7)).replace(',', '.')));
            }

            if(r1.data.getMonthValue() == 5)
            {
                for(int i = 0; i < 10; i++)
                {
                    r1.czujnik.set(i, r1.czujnik.get(i) + 0.9);
                }
            }
        }
        bw_5.write("wszystkie wnioski:\n");
        for(Rekord r : dane3)
        {
            if(r.data.toString().equals("2017-05-05"))
            {
                bw_5.write("2017-05-05:\n");
                bw_5.write(String.format("czujnik1: %f\n", r.czujnik.get(0)));
                bw_5.write(String.format("czujnik2: %f\n", r.czujnik.get(1)));
                bw_5.write(String.format("czujnik8: %f\n", r.czujnik.get(7)));
                bw_5.write(String.format("czujnik9: %f\n", r.czujnik.get(8)));
                break;
            }
        }
        for(Rekord r : dane3)
        {
            if(r.data.toString().equals("2017-07-07"))
            {
                bw_5.write("2017-07-07:\n");
                bw_5.write(String.format("czujnik1: %f\n", r.czujnik.get(0)));
                bw_5.write(String.format("czujnik2: %f\n", r.czujnik.get(1)));
                bw_5.write(String.format("czujnik8: %f\n", r.czujnik.get(7)));
                bw_5.write(String.format("czujnik9: %f\n\n", r.czujnik.get(8)));
                break;
            }
        }

        bw_5.write("Dwa pierwsze:\n");
        for(Rekord r : dane3)
        {
            if(r.data.toString().equals("2017-05-05"))
            {
                bw_5.write("2017-05-05:\n");
                bw_5.write(String.format("czujnik1: %f\n", r.czujnik.get(0) - 0.9));
                bw_5.write(String.format("czujnik2: %f\n", r.czujnik.get(1) - 0.9));
                bw_5.write(String.format("czujnik8: %f\n", r.czujnik.get(7) - 0.9));
                bw_5.write(String.format("czujnik9: %f\n", r.czujnik.get(8) - 0.9));
                break;
            }
        }
        for(Rekord r : dane3)
        {
            if(r.data.toString().equals("2017-07-07"))
            {
                bw_5.write("2017-07-07:\n");
                bw_5.write(String.format("czujnik1: %f\n", r.czujnik.get(0)));
                bw_5.write(String.format("czujnik2: %f\n", r.czujnik.get(1)));
                bw_5.write(String.format("czujnik8: %f\n", r.czujnik.get(7)));
                bw_5.write(String.format("czujnik9: %f\n\n", r.czujnik.get(8)));
                break;
            }
        }
        bw_5.close();
    }
}
