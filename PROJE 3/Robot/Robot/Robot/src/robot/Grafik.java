/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ev
 */
public class Grafik extends JPanel {

    //static Graphics g;
    private int[][] engelKoordinatlari; // satırlar engel sayısı // sütunlar x ve y koordinatları
    private int diziBoyutu; //  engel sayısı
    private static int indeks = 0; // her engel koordinatı için bir satır aşağıya geçilecek
    private int[] robotKoordinatlari;
    private int[] kolKoordinatlari;
    protected int[] robotKonumuTut;
    private String durum = "";
    private Robot rb;
    private double sure;
    Graphics g;
    JFrame jf;

    public Grafik(int diziBoyutu, int[][] engelKoordinatlari) // initializing
    {
        this.diziBoyutu = diziBoyutu;
        this.engelKoordinatlari = new int[this.diziBoyutu][2]; // [engelsayisi][x,y]
        this.engelKoordinatlari = engelKoordinatlari;
    }

    public Grafik() {
        jf = new JFrame("Java grafik kütüphanesi");
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setSize(1000, 1000);
        //setJfSize();
        jf.setVisible(true);
        jf.add(this);

    }

    public void paintComponent(Graphics g) {

        this.g = g;
        super.paintComponent(this.g);
        this.setBackground(Color.WHITE);

        if (durum.equals("baslangic")) {
            engelCiz(g);
        } else if (durum.equals("robot")) {
            robotCiz(g);
        }

        if (this.rb instanceof Gezgin) {
            if (engelKoordinatlari != null) {
                engelCiz(g);
            }
            if (robotKoordinatlari != null) {
                robotCiz(g);
            }
        } else if (this.rb instanceof Gezmeyen) {
            if (robotKonumuTut != null) {
                manipulatorCiz(g);
            }
            if (kolKoordinatlari != null) {
                kolCiz(g);
            }
        }
        izgaralariCiz(g);

    }

    public void yenidenCiz() {
        jf.repaint();
    }

    protected void engelCiz(Graphics g) {

        if (engelKoordinatlari != null) {
            for (int i = 1; i <= 20; i++) {
                for (int j = 1; j <= 20; j++) {
                    for (int k = 0; k < this.diziBoyutu; k++) {
                        if (i == engelKoordinatlari[k][0] && j == engelKoordinatlari[k][1]) {
                            this.g.setColor(Color.RED);
                            //System.out.println("i : " + i + " j: " + j + " engelKoordinatlari[k][0] : " + engelKoordinatlari[k][0] + " engelKoordinatlari[k][1] : " + engelKoordinatlari[k][1]);
                            this.g.fillRect((engelKoordinatlari[k][0] * 40 + 100), (engelKoordinatlari[k][1] * 40), 40, 40);
                        }
                    }
                }
            }
        }

        yenidenCiz();

    }

    protected void robotCiz(Graphics g) {

        g.setColor(Color.ORANGE);
        for (int i = 1; i <= 20; i++) {
            for (int j = 1; j <= 20; j++) {
                if (i == robotKoordinatlari[0] && j == robotKoordinatlari[1]) {
                    g.fillRect((robotKoordinatlari[0] * 40 + 100), (robotKoordinatlari[1] * 40), 40, 40);
                }
            }
        }

        yenidenCiz();

    }

    protected void manipulatorCiz(Graphics g) {
        for (int i = 1; i <= 20; i++) {
            for (int j = 1; j <= 20; j++) {
                if (i == robotKonumuTut[0] && j == robotKonumuTut[1]) {
                    g.setColor(Color.ORANGE);
                    g.fillRect((robotKonumuTut[0] * 40 + 100), (robotKonumuTut[1] * 40), 40, 40);
                }
            }
        }
        yenidenCiz();
    }

    protected void kolCiz(Graphics g) {

        for (int i = 1; i <= 20; i++) {
            for (int j = 1; j <= 20; j++) {
                if (i == kolKoordinatlari[0] && j == kolKoordinatlari[1]) {
                    g.setColor(Color.GREEN);
                    g.fillRect((kolKoordinatlari[0] * 40 + 100), (kolKoordinatlari[1] * 40), 40, 40);
                }
            }
        }

        yenidenCiz();

    }

    protected void izgaralariCiz(Graphics g) {

        for (int i = 1; i <= 20; i++) {
            for (int j = 1; j <= 20; j++) {
                this.g.setColor(Color.BLUE);
                this.g.drawRect(i * 40 + 100, j * 40, 40, 40);
            }
        }

    }

    public double sureyiArttir() {

        double sure = 0;

        if (this.rb instanceof Gezgin) {
            if (this.rb instanceof TekerlekliRobot) {
                sure = ((TekerlekliRobot) rb).getGezinmeHizi();
            } else if (this.rb instanceof PaletliRobot) {
                sure = ((PaletliRobot) rb).getGezinmeHizi();
            } else if (this.rb instanceof SpiderRobot) {
                sure = ((SpiderRobot) rb).getGezinmeHizi();
            }
        }

        return sure;
    }

    public void robotHareketEttir(int adim, String yon) {
        // ileri geri sag sol icin ayri durumlar yazilacak

        boolean kontrol = false;
        boolean engeleCarptiMi = false;
        if (yon.equalsIgnoreCase("ileri")) {
            robotKoordinatlari[1] += adim;

            if (robotKoordinatlari[1] > 20) {

                if ((robotKoordinatlari[1] - adim) == 20) {

                    robotKoordinatlari[1] = 20;
                    System.out.println("robot sinirlarin disina cikti");
                    return;
                } else if ((robotKoordinatlari[1] - adim) < 20) {

                    robotKoordinatlari[1] -= adim;

                    for (int i = 0; i < adim; i++) {
                        robotKoordinatlari[1]++;

                        // sure arttirma
                        this.sure += sureyiArttir() * 10;

                        for (int j = 0; j < diziBoyutu; j++) {
                            if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {

                                engeleCarptiMi = true;

                                if (this.rb instanceof SpiderRobot) {
                                    robotKoordinatlari[1] = engelKoordinatlari[j][1] - 1;
                                    System.out.println("Spider robotlar engellerden geçemezler");
                                    this.sure -= sureyiArttir() * 10;
                                    return;

                                } else {
                                    this.sure += engeldenGec();
                                    robotKoordinatlari[1] = 20;
                                    kontrol = true;
                                    break;

                                }
                            }
                        }

                        if (kontrol) {
                            break;
                        }
                    }
                }

                if (!engeleCarptiMi) {
                    robotKoordinatlari[1] = 20;
                }

                System.out.println("Robot sinirlarin disina cikti");

            } else {

                robotKoordinatlari[1] -= adim;

                for (int i = 0; i < adim; i++) {
                    robotKoordinatlari[1]++;

                    // sure arttirma
                    this.sure += sureyiArttir() * 10;

                    for (int j = 0; j < diziBoyutu; j++) {
                        if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {
                            if (this.rb instanceof SpiderRobot) {
                                robotKoordinatlari[1] = engelKoordinatlari[j][1] - 1;
                                System.out.println("Spider robotlar engellerden geçemezler");
                                this.sure -= sureyiArttir() * 10;
                                return;

                            } else {
                                this.sure += engeldenGec();
                                kontrol = true;
                                robotKoordinatlari[1] += adim - i;
                                break;

                            }
                        }
                    }

                    if (kontrol) {
                        break;
                    }
                }
            }

        } else if (yon.equalsIgnoreCase("geri")) {

            robotKoordinatlari[1] -= adim;

            if (robotKoordinatlari[1] < 1) {

                if ((robotKoordinatlari[1] + adim) >= 1) {
                    if ((robotKoordinatlari[1] + adim) == 1) {
                        robotKoordinatlari[1] = 1;
                        System.out.println("robot sinirlarin disina cikti");
                        return;
                    } else if ((robotKoordinatlari[1] + adim) > 1) {
                        robotKoordinatlari[1] += adim;
                        for (int i = 0; i < adim; i++) {
                            robotKoordinatlari[1]--;

                            // sure arttirma
                            this.sure += sureyiArttir() * 10;

                            for (int j = 0; j < diziBoyutu; j++) {
                                if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {

                                    engeleCarptiMi = true;

                                    if (this.rb instanceof SpiderRobot) {
                                        robotKoordinatlari[1] = engelKoordinatlari[j][1] + 1;
                                        System.out.println("Spider robotlar engellerden geçemezler");
                                        this.sure -= sureyiArttir() * 10;
                                        return;
                                    } else {
                                        this.sure += engeldenGec();
                                        robotKoordinatlari[1] = 1;
                                        kontrol = true;
                                        break;
                                    }
                                }
                            }
                            if (kontrol) {
                                break;
                            }
                        }
                    }

                    if (!engeleCarptiMi) {
                        robotKoordinatlari[1] = 1;
                    }

                    System.out.println("Robot sinirlarin disina cikti");

                }

            } else {

                robotKoordinatlari[1] += adim;

                for (int i = 0; i < adim; i++) {
                    robotKoordinatlari[1]--;

                    // sure arttirma
                    this.sure += sureyiArttir() * 10;

                    for (int j = 0; j < diziBoyutu; j++) {
                        if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {
                            if (this.rb instanceof SpiderRobot) {
                                robotKoordinatlari[1] = engelKoordinatlari[j][1] + 1;
                                System.out.println("Spider robotlar engellerden geçemezler");
                                this.sure -= sureyiArttir() * 10;
                                return;
                            } else {
                                this.sure += engeldenGec();
                                kontrol = true;
                                robotKoordinatlari[1] -= adim - i;
                                break;
                            }
                        }
                    }
                    if (kontrol) {
                        break;
                    }
                }

            }

        } else if (yon.equalsIgnoreCase("sol")) {

            robotKoordinatlari[0] -= adim;

            if (robotKoordinatlari[0] < 1) {

                if ((robotKoordinatlari[0] + adim) >= 1) {
                    if ((robotKoordinatlari[0] + adim) == 1) {
                        robotKoordinatlari[0] = 1;
                        System.out.println("robot sinirlarin disina cikti");
                        return;
                    } else if ((robotKoordinatlari[0] + adim) > 1) {
                        robotKoordinatlari[0] += adim;
                        for (int i = 0; i < adim; i++) {
                            robotKoordinatlari[0]--;

                            // sure arttirma
                            this.sure += sureyiArttir() * 10;

                            for (int j = 0; j < diziBoyutu; j++) {
                                if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {

                                    engeleCarptiMi = true;

                                    if (this.rb instanceof SpiderRobot) {
                                        robotKoordinatlari[0] = engelKoordinatlari[j][0] + 1;
                                        System.out.println("Spider robotlar engellerden geçemezler");
                                        this.sure -= sureyiArttir() * 10;
                                        return;
                                    } else {
                                        this.sure += engeldenGec();
                                        robotKoordinatlari[0] = 1;
                                        kontrol = true;
                                        break;
                                    }
                                }
                            }
                            if (kontrol) {
                                break;
                            }
                        }
                    }

                    if (!engeleCarptiMi) {
                        robotKoordinatlari[0] = 1;
                    }

                    System.out.println("Robot sinirlarin disina cikti");
                }
            } else {

                robotKoordinatlari[0] += adim;
                for (int i = 0; i < adim; i++) {
                    robotKoordinatlari[0]--;

                    // sure arttirma
                    this.sure += sureyiArttir() * 10;

                    for (int j = 0; j < diziBoyutu; j++) {
                        if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {
                            if (this.rb instanceof SpiderRobot) {
                                robotKoordinatlari[0] = engelKoordinatlari[j][0] + 1;
                                System.out.println("Spider robotlar engellerden geçemezler");
                                this.sure -= sureyiArttir() * 10;
                                return;
                            } else {
                                this.sure += engeldenGec();
                                kontrol = true;
                                robotKoordinatlari[0] -= adim - i;
                                break;
                            }
                        }
                    }
                    if (kontrol) {
                        break;
                    }
                }

            }

        } else if (yon.equalsIgnoreCase("sag")) {

            robotKoordinatlari[0] += adim;
            ////            
            if (robotKoordinatlari[0] > 20) {

                if ((robotKoordinatlari[0] - adim) <= 20) {
                    if ((robotKoordinatlari[0] - adim) == 20) {
                        robotKoordinatlari[0] = 20;
                        System.out.println("robot sinirlarin disina cikti");
                        return;
                    } else if ((robotKoordinatlari[0] - adim) < 20) {
                        robotKoordinatlari[0] -= adim;
                        for (int i = 0; i < adim; i++) {
                            robotKoordinatlari[0]++;

                            // sure arttirma
                            this.sure += sureyiArttir() * 10;

                            for (int j = 0; j < diziBoyutu; j++) {
                                if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {

                                    engeleCarptiMi = true;

                                    if (this.rb instanceof SpiderRobot) {
                                        robotKoordinatlari[0] = engelKoordinatlari[j][0] - 1;
                                        System.out.println("Spider robotlar engellerden geçemezler");
                                        this.sure -= sureyiArttir() * 10;
                                        return;
                                    } else {
                                        this.sure += engeldenGec();
                                        robotKoordinatlari[0] = 20;
                                        kontrol = true;
                                        break;
                                    }
                                }
                            }
                            if (kontrol) {
                                break;
                            }
                        }
                    }

                    if (!engeleCarptiMi) {
                        robotKoordinatlari[0] = 20;
                    }

                    System.out.println("Robot sinirlarin disina cikti");
                }
            } /////
            else {

                robotKoordinatlari[0] -= adim;
                for (int i = 0; i < adim; i++) {
                    robotKoordinatlari[0]++;

                    // sure arttirma
                    this.sure += sureyiArttir() * 10;

                    for (int j = 0; j < diziBoyutu; j++) {
                        if (engelKoordinatlari[j][0] == robotKoordinatlari[0] && engelKoordinatlari[j][1] == robotKoordinatlari[1]) {
                            if (this.rb instanceof SpiderRobot) {
                                robotKoordinatlari[0] = engelKoordinatlari[j][0] - 1;
                                System.out.println("Spider robotlar engellerden geçemezler");
                                this.sure -= sureyiArttir() * 10;
                                return;
                            } else {
                                this.sure += engeldenGec();
                                kontrol = true;
                                robotKoordinatlari[0] += adim - i;
                                break;
                            }
                        }
                    }
                    if (kontrol) {
                        break;
                    }
                }

            }

        }

        System.out.println("Sure : " + this.sure);

    }

    public void kolHareketEttir(int adim, String yon) {
        // ileri geri sag sol icin ayri durumlar yazilacak

        boolean kontrol = false;
        boolean engeleCarptiMi = false;
        if (yon.equalsIgnoreCase("ileri")) {
            kolKoordinatlari[1] += adim;

            if (this.robotKonumuTut[0] == this.kolKoordinatlari[0]) {
                int uzaklik = (Math.abs((robotKonumuTut[1] - kolKoordinatlari[1])) * 10);

                if (uzaklik > (((Gezmeyen) (this.rb)).kolUzunlugu)) {
                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[1] -= adim;
                    return;
                }
            } else {
                double uzaklik = Math.sqrt(Math.pow(Math.abs(kolKoordinatlari[0] - robotKonumuTut[0]), 2) + Math.pow(Math.abs(kolKoordinatlari[1] - robotKonumuTut[1]), 2));
                System.out.println("kol 0 : " + kolKoordinatlari[0] + " kol 1 : " + kolKoordinatlari[1]);
                System.out.println("uzaklik : " + uzaklik);

                if (((int) (uzaklik))*10 > (((Gezmeyen) (this.rb)).kolUzunlugu)) {

                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[1] -= adim;
                    return;
                }

            }

            if (kolKoordinatlari[1] > 20) {

                if ((kolKoordinatlari[1] - adim) == 20) {

                    kolKoordinatlari[1] = 20;
                    System.out.println("robot sinirlarin disina cikti");
                    return;
                } else if ((kolKoordinatlari[1] - adim) < 20) {

                    kolKoordinatlari[1] -= adim;

                    for (int i = 0; i < adim; i++) {

                        if (kolKoordinatlari[1] >= 20) {
                            break;
                        } else {
                            kolKoordinatlari[1]++;
                        }

                    }
                }

                System.out.println("Robot sinirlarin disina cikti");

            }

        } else if (yon.equalsIgnoreCase("geri")) {

            kolKoordinatlari[1] -= adim;

            if (this.robotKonumuTut[0] == this.kolKoordinatlari[0]) {
                int uzaklik = (Math.abs((robotKonumuTut[1] - kolKoordinatlari[1])) * 10);

                if (uzaklik > (((Gezmeyen) (this.rb)).kolUzunlugu)) {
                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[1] += adim;
                    return;
                }
            } else {
                double uzaklik = Math.sqrt(Math.pow(Math.abs(kolKoordinatlari[0] - robotKonumuTut[0]), 2) + Math.pow(Math.abs(kolKoordinatlari[1] - robotKonumuTut[1]), 2));
                System.out.println("uzaklik : " + uzaklik);

                if (((int) (uzaklik))*10 > (((Gezmeyen) (this.rb)).kolUzunlugu)) {

                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[1] += adim;
                    return;
                }

            }

            if (kolKoordinatlari[1] < 1) {

                if ((kolKoordinatlari[1] + adim) >= 1) {
                    if ((kolKoordinatlari[1] + adim) == 1) {
                        kolKoordinatlari[1] = 1;
                        System.out.println("robot sinirlarin disina cikti");
                        return;
                    } else if ((kolKoordinatlari[1] + adim) > 1) {

                        kolKoordinatlari[1] += adim;

                        for (int i = 0; i < adim; i++) {

                            if (kolKoordinatlari[1] <= 1) {
                                break;
                            } else {
                                kolKoordinatlari[1]--;
                            }

                        }
                    }

                    System.out.println("Robot sinirlarin disina cikti");

                }

            }

        } else if (yon.equalsIgnoreCase("sol")) {

            kolKoordinatlari[0] -= adim;

            if (this.robotKonumuTut[1] == this.kolKoordinatlari[1]) {
                int uzaklik = (Math.abs((robotKonumuTut[0] - kolKoordinatlari[0])) * 10);

                if (uzaklik > (((Gezmeyen) (this.rb)).kolUzunlugu)) {
                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[0] += adim;
                    return;
                }
            } else {
                double uzaklik = Math.sqrt(Math.pow(Math.abs(kolKoordinatlari[0] - robotKonumuTut[0]), 2) + Math.pow(Math.abs(kolKoordinatlari[1] - robotKonumuTut[1]), 2));
                System.out.println("uzaklik : " + uzaklik);

                if (((int) (uzaklik))*10 > (((Gezmeyen) (this.rb)).kolUzunlugu)) {

                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[0] += adim;
                    return;
                }

            }

            if (kolKoordinatlari[0] < 1) {

                if ((kolKoordinatlari[0] + adim) >= 1) {
                    if ((kolKoordinatlari[0] + adim) == 1) {
                        kolKoordinatlari[0] = 1;
                        System.out.println("robot sinirlarin disina cikti");
                        return;
                    } else if ((kolKoordinatlari[0] + adim) > 1) {
                        kolKoordinatlari[0] += adim;
                        for (int i = 0; i < adim; i++) {

                            if (kolKoordinatlari[0] <= 1) {
                                break;
                            } else {
                                kolKoordinatlari[0]--;
                            }

                        }
                    }

                    System.out.println("Robot sinirlarin disina cikti");
                }
            }

        } else if (yon.equalsIgnoreCase("sag")) {

            kolKoordinatlari[0] += adim;
            System.out.println("deneme1");
            if (this.robotKonumuTut[1] == this.kolKoordinatlari[1]) {
            System.out.println("deneme2");
                int uzaklik = (Math.abs((robotKonumuTut[0] - kolKoordinatlari[0])) * 10);

                if (uzaklik > (((Gezmeyen) (this.rb)).kolUzunlugu)) {
                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[0] -= adim;
                    return;
                }
            } else {
            System.out.println("deneme3");
                double uzaklik = Math.sqrt(Math.pow(Math.abs(kolKoordinatlari[0] - robotKonumuTut[0]), 2) + Math.pow(Math.abs(kolKoordinatlari[1] - robotKonumuTut[1]), 2));
                System.out.println("uzaklik : " + uzaklik);
                if (((int) (uzaklik))*10 > (((Gezmeyen) (this.rb)).kolUzunlugu)) {
                        
                    System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
                    kolKoordinatlari[0] -= adim;
                    return;
                }

            }

            if (kolKoordinatlari[0] > 20) {

                if ((kolKoordinatlari[0] - adim) <= 20) {
                    if ((kolKoordinatlari[0] - adim) == 20) {
                        kolKoordinatlari[0] = 20;
                        System.out.println("robot sinirlarin disina cikti");
                        return;
                    } else if ((kolKoordinatlari[0] - adim) < 20) {
                        kolKoordinatlari[0] -= adim;
                        for (int i = 0; i < adim; i++) {
                            if (kolKoordinatlari[0] >= 20) {
                                break;
                            } else {
                                kolKoordinatlari[0]++;
                            }
                        }
                    }

                    System.out.println("Robot sinirlarin disina cikti");
                }
            }
        }

    }

    public double engeldenGec() {
        double sure = 0;

        if (this.rb instanceof Tekerlekli) {
            Tekerlekli rb = (Tekerlekli) this.rb;
            sure = rb.engeldenGecmeSuresiniBul();
            System.out.println("Sure : " + sure);
        } else if (this.rb instanceof Spider) {
            Spider rb = (Spider) this.rb;
            sure = rb.engeldenGecmeSuresiniBul();
            System.out.println("Sure : " + sure);
        } else if (this.rb instanceof Paletli) {
            Paletli rb = (Paletli) this.rb;
            sure = rb.engeldenGecmeSuresiniBul();
            System.out.println("Sure : " + sure);
        }

        return sure;

    }

    public void yukuTasi() {

        if (this.robotKonumuTut[0] == this.kolKoordinatlari[0]) {
            if ((robotKonumuTut[0] - kolKoordinatlari[0]) * 10 > ((Gezmeyen) (this.rb)).kolUzunlugu) {
                System.out.println("Robot kolu uzunlugundan fazla yere yuk tasiyamaz");
            }
        }
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public void setDiziBoyutu(int diziBoyutu) {
        this.diziBoyutu = diziBoyutu;
    }

    public void setEngelKoordinatlari(int[][] engelKoordinatlari) {
        this.engelKoordinatlari = new int[diziBoyutu][2];
        this.engelKoordinatlari = engelKoordinatlari;
    }

    public void setRobotKoordinatlari(int[] robotKoordinatlari) {
        this.robotKoordinatlari = robotKoordinatlari;
    }

    public void setRobot(Robot rb) {
        this.rb = rb;
    }

    public double getSure() {
        return this.sure;
    }

    public void setKolKoordinatlari(int[] kolKoordinatlari) {
        this.kolKoordinatlari = kolKoordinatlari;

        this.robotKonumuTut = new int[kolKoordinatlari.length];

        for (int i = 0; i < kolKoordinatlari.length; i++) {
            robotKonumuTut[i] = kolKoordinatlari[i];
        }

    }

}
