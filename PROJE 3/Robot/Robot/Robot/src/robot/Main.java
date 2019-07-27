/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import java.util.Scanner;

/**
 *
 * @author ev
 */
public class Main {

    public static void main(String[] args) {

        Grafik g = new Grafik();
        g.setDurum("baslangic");
        Scanner sc = new Scanner(System.in);

        System.out.println("Oluşturulacak robotun türünü girin;\nGezgin için \'G\',\nManipulator icin \'M\',\nGezgin Manipulator icin \'GM\' ");
        String robotTuru = sc.next();

        Robot rb;
        rb = robotOlustur(robotTuru, sc);
        g.setRobot(rb);

//        if (rb instanceof TekerlekliRobot) {
//            TekerlekliRobot tr = (TekerlekliRobot) rb;
//            //System.out.println(tr.motorSayisi + " " + tr.tekerlekSayisi + " " + tr.yukMiktari + " " + tr.robotTipi);
//        }
        if (rb instanceof Gezgin) {
            engelBilgisiniAl(sc, g);
        }

        robotBilgileriniAl(sc, g);

        if (rb instanceof Gezgin) {
            while (true) {
                robotuHareketEttir(sc, g);
            }
        } else if (rb instanceof Gezmeyen) {
            while (true) {
                kolHareketEttir(sc, g);
            }
        }

    }

    static void robotuHareketEttir(Scanner sc, Grafik g) {
        //System.out.println("ileri");
        String bilgi = sc.nextLine();

        int adim = -1;

        for (int i = 0; i < bilgi.length(); i++) {
            if (Character.isDigit(bilgi.charAt(i))) {
                adim = Integer.parseInt(Character.toString(bilgi.charAt(i)));
            }
        }

        if (bilgi.toLowerCase().contains("ileri")) {
            g.robotHareketEttir(adim, "ileri");
        } else if (bilgi.toLowerCase().contains("geri")) {
            g.robotHareketEttir(adim, "geri");
        } else if (bilgi.toLowerCase().contains("sag")) {
            g.robotHareketEttir(adim, "sag");
        } else if (bilgi.toLowerCase().contains("sol")) {
            g.robotHareketEttir(adim, "sol");
        }
    }

    static void kolHareketEttir(Scanner sc, Grafik g) {
        //System.out.println("ileri");
        String bilgi = sc.nextLine();

        int adim = -1;

        for (int i = 0; i < bilgi.length(); i++) {
            if (Character.isDigit(bilgi.charAt(i))) {
                adim = Integer.parseInt(Character.toString(bilgi.charAt(i)));
            }
        }

        if (bilgi.toLowerCase().contains("ileri")) {
            g.kolHareketEttir(adim, "ileri");
        } else if (bilgi.toLowerCase().contains("geri")) {
            g.kolHareketEttir(adim, "geri");
        } else if (bilgi.toLowerCase().contains("sag")) {
            g.kolHareketEttir(adim, "sag");
        } else if (bilgi.toLowerCase().contains("sol")) {
            g.kolHareketEttir(adim, "sol");
        }
    }

    static void robotBilgileriniAl(Scanner sc, Grafik g) {
        System.out.println("Robotun başlangıç konum bilgisini girin;");
        String robotKoordinati = sc.next();
        int[] baslangicKoordinati = engelKoordinatlariniVer(robotKoordinati);

        g.setRobotKoordinatlari(baslangicKoordinati);
        g.setKolKoordinatlari(baslangicKoordinati);

        g.setDurum("robot");
    }

    static void engelBilgisiniAl(Scanner sc, Grafik g) {
        /*
            
         ENGEL OLUŞTURMA
            
         */

        System.out.println("Izgara üzerine engel yerleştirmek istiyor musunuz? (Evet : 1, Hayıt : 0)");
        int engelBilgisi = sc.nextInt();

        engelOlustur(engelBilgisi, sc, g);
    }

    static Robot robotOlustur(String robotTuru, Scanner sc) {

        String robotCesidi = "";

        System.out.println("Oluşturulacak robotun motor sayısını girin;");
        int motorSayisi = sc.nextInt();
        System.out.println("Oluşturulacak robotun yük miktarını girin;");
        int yukMiktari = sc.nextInt();

        if (robotTuru.toUpperCase().equals("G")) {

            System.out.println("Gezgin robotun gezinme hızını giriniz");
            int gezinmeHizi = sc.nextInt();

            System.out.println("Tekerlekli için \'T\', Paletli için \'P\', Spider için \'S\'");
            robotCesidi = sc.next();

            if (robotCesidi.toUpperCase().equals("T")) {

                TekerlekliRobot rb = new TekerlekliRobot();
                System.out.println("Tekerlekli robotun tekerlek sayısını girin;");
                int tekerlekSayisi = sc.nextInt();

                rb.setGezinmeHizi(gezinmeHizi);
                rb.motorSayisi = motorSayisi;
                rb.yukMiktari = yukMiktari;
                rb.setTekerlekSayisi(tekerlekSayisi);

                return rb;
            } else if (robotCesidi.toUpperCase().equals("P")) {

                PaletliRobot rb = new PaletliRobot();
                System.out.println("Paletli robotun palet sayısını girin;");
                int paletSayisi = sc.nextInt();

                rb.setGezinmeHizi(gezinmeHizi);
                rb.motorSayisi = motorSayisi;
                rb.yukMiktari = yukMiktari;
                rb.setPaletSayisi(paletSayisi);
                return rb;

            } else if (robotCesidi.toUpperCase().equals("S")) {

                SpiderRobot rb = new SpiderRobot();
                System.out.println("Spider robotun bacak sayısını girin;");
                int bacakSayisi = sc.nextInt();

                rb.setGezinmeHizi(gezinmeHizi);
                rb.motorSayisi = motorSayisi;
                rb.yukMiktari = yukMiktari;
                rb.setBacakSayisi(bacakSayisi);
                return rb;

            }
        } else if (robotTuru.toUpperCase().equals("M")) {
            System.out.println("Seri için \'S\', Paralel için \'P\'");
            robotCesidi = sc.next();

            System.out.println("Manipulator robotun yük taşıma kapasitesini girin;");
            int yukTasimaKapasitesi = sc.nextInt();

            System.out.println("Manipulator robotun kol uzunluğunu girin;");
            int kolUzunlugu = sc.nextInt();

            System.out.println("Manipulator robotun taşıma hızını girin;");
            int tasimaHizi = sc.nextInt();

            System.out.println("Robot kolunun taşıyacağı yükün ağırlığını girin");
            int yukAgirligi = sc.nextInt();

            if (yukAgirligi > yukTasimaKapasitesi) {
                while (yukAgirligi > yukTasimaKapasitesi) {
                    System.out.println("Robot kapasitesinden fazla yük kaldıramaz, yük ağırlığını tekrar girin;");
                    yukAgirligi = sc.nextInt();
                }
            }

            if (robotCesidi.toUpperCase().equals("S")) {
                SeriRobot rb = new SeriRobot();

                rb.yukTasimaKapasitesi = yukTasimaKapasitesi;
                rb.kolUzunlugu = kolUzunlugu;
                rb.tasimaHizi = tasimaHizi;

                rb.motorSayisi = motorSayisi;
                rb.yukMiktari = yukMiktari;

                return rb;

            } else if (robotCesidi.toUpperCase().equals("P")) {
                ParalelRobot rb = new ParalelRobot();

                rb.yukTasimaKapasitesi = yukTasimaKapasitesi;
                rb.kolUzunlugu = kolUzunlugu;
                rb.tasimaHizi = tasimaHizi;

                rb.motorSayisi = motorSayisi;
                rb.yukMiktari = yukMiktari;

                return rb;

            }
        } else if (robotTuru.toUpperCase().equals("GM")) { // HİBRİT SINIF SONRA BAKILACAK

        }

        return null;
    }

    static void engelOlustur(int engelBilgisi, Scanner sc, Grafik g) {
        if (engelBilgisi == 1) {
            int[] tempKoordinat = new int[2];
            System.out.println("Kaç engel koyulacak?");
            int engelSayisi = sc.nextInt();

            int[][] engelKoordinatlari = new int[engelSayisi][2];
            int sayac = 0;

            System.out.println("Sırayla engellerin koordinaylarını \"x,y\" şeklinde giriniz;");
            String engel = "";
            while (sayac < engelSayisi) {
                System.out.println(sayac + 1 + ". engelin koordinatı : ");

                engel = sc.next();
                tempKoordinat = engelKoordinatlariniVer(engel);
                engelKoordinatlari[sayac][0] = tempKoordinat[0];
                engelKoordinatlari[sayac][1] = tempKoordinat[1];
                sayac++;

            }

            engeliGoster(engelSayisi, engelKoordinatlari, g);

        }
    }

    static int[] engelKoordinatlariniVer(String okunan) {
        int[] koordinatlar = new int[2];
        String deger = "";
        for (int i = 0; i < okunan.length(); i++) {
            if (okunan.charAt(i) == ',') {
                break;
            } else {
                deger = deger + okunan.charAt(i);
            }
        }

        if (Integer.parseInt(deger) > 20 || Integer.parseInt(deger) < 0) {
            koordinatlar[0] = -1;
        } else {
            koordinatlar[0] = Integer.parseInt(deger);
        }

        deger = "";

        for (int i = 0; i < okunan.length(); i++) {
            if (okunan.charAt(i) == ',') {
                for (int j = i + 1; j < okunan.length(); j++) {
                    deger = deger + okunan.charAt(j);
                }
            } else {
                continue;
            }
        }

        if (Integer.parseInt(deger) > 20 || Integer.parseInt(deger) < 0) {
            koordinatlar[1] = -1;
        } else {
            koordinatlar[1] = Integer.parseInt(deger);
        }

        return koordinatlar;

    }

    static void engeliGoster(int engelSayisi, int[][] engelKoordinatlari, Grafik g) {

        g.setDiziBoyutu(engelSayisi);
        g.setEngelKoordinatlari(engelKoordinatlari);

    }

}
