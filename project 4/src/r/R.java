/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hacib
 */
public class R {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String line;
        String kullaniciId, yerId, zaman, enlem, boylam, sehir, kategori, altKategori;
        String[] splitDizisi = null;
        String[] kategoriDizisi = null;
        String[] LLEklenecekler = null;

        //Rezervasyon yerine göre kullanıcı listeleme
//Aynı yerde rezervasyon yaptırmış olan tüm kullanıcılar listelenmelidir (kullanıcı id). 
        ArrayList< String> altKategoriler = new ArrayList<String>();

        FileReader fr = null;

        CokluAgac co = new CokluAgac();

        try {
            fr = new FileReader(new File("C:\\Users\\hacib\\OneDrive\\Masaüstü\\rezervasyon3.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader br = new BufferedReader(fr);

        try {
            while ((line = br.readLine()) != null) {

                splitDizisi = line.split(",");

                kategoriDizisi = splitDizisi[splitDizisi.length - 1].split(":");

                altKategoriler.add(splitDizisi[1]);

                LLEklenecekler = new String[splitDizisi.length - 2];

                for (int i = 0; i < splitDizisi.length - 2; i++) {
                    LLEklenecekler[i] = splitDizisi[i + 1];
                }

                co.ekle2(kategoriDizisi, splitDizisi[splitDizisi.length - 1], splitDizisi[0], splitDizisi[splitDizisi.length - 1].split(":")[splitDizisi[splitDizisi.length - 1].split(":").length - 1], LLEklenecekler);

            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }

        co.kategorilerinKullaniciAgaclariniOlustur();

        int secim = 0;
        Scanner sc = new Scanner(System.in);
        //co.yazdir();
        while (secim != -1) {
            System.out.println("");
            System.out.println("Kategori işlemleri için 1\n"
                    + "Kullanıcı işlemleri için 2\n"
                    + "Sorgu ve listeleme işlemleri için 3\n"
                    + "Çıkmak için -1\n");

            secim = sc.nextInt();

            if (secim == 1) {

                //co.dene(co.kategoriAra("American").kullanicilar);
                System.out.println("--- Kategori işlemleri ---\n");

                System.out.println("\nAranacak kategorinin adını girin");

                sc.nextLine();
                String kategoriAdi = sc.nextLine();

                Kategori aranan = co.kategoriAra(kategoriAdi);

                if (aranan == null) {
                    System.out.println("(!) Aranan kategori bulunamadı.");
                    continue;
                } else {

                    co.birKategorininBilgileriniYazdir(kategoriAdi);

                    System.out.println("Alt kategori eklemek için 1\n"
                            + "Alt kategorilerde silme işlemi yapmak için 2 girin.\n");

                    int islem = sc.nextInt();

                    sc.nextLine();
                    if (islem == 1) {
                        System.out.println("Eklenecek alt kategorinin adını girin");
                        kategoriAdi = sc.nextLine();

                        co.kontrolEtVeEkle(aranan, new Kategori(kategoriAdi), "");

                        System.out.println("");
                    } else if (islem == 2) {

                        System.out.println("Silinecek alt kategorinin adını girin");
                        kategoriAdi = sc.nextLine();

                        co.kategoriSil(kategoriAdi);
                        

                        System.out.println("");

                    }

                }

            } else if (secim == 2) {
                System.out.println("--- Kullanıcı işlemleri ---\n");

                System.out.println("\n1) Kullanıcı ekleme işlemi için 1\n"
                        + "2) Kullanıcı silme işlemi için 2 giriniz:\n");

                int islem = sc.nextInt();

                if (islem == 1) {

                    System.out.println("\n\n - Kullanıcı eklenecek kategorinin adını girin\n");

                    sc.nextLine();
                    String kategoriAdi = sc.nextLine();

                    Kategori aranan = co.kategoriAra(kategoriAdi);

                    if (aranan == null) {
                        System.out.println("(!)Aranan kategori bulunamadı");
                    } else {

                        System.out.println("Eklenecek kullanıcının id'sini girin:\n");

                        String id = sc.nextLine();

                        co.kategoriyeKullaniciEkle(id, aranan);
                        System.out.println("");
                        co.kategoriyeGoreKullanicilariListele(aranan.kategoriAdi);
                    }

                } else if (islem == 2) {

                    System.out.println("\n-Bir kategori altındaki tüm kullanıcıları silmek için 1\n"
                            + "-Bir kategori altındaki bir kullanıcıyı silmek için 2\n"
                            + "-Bir kullanıcının tüm kategorilerdeki kaydını silmek için 3 girin:\n");

                    int silSecim = sc.nextInt();

                    if (silSecim == 1) {
                        System.out.println("\nKullanıcıları silinecek kategorinin adını girin:\n");
                        sc.nextLine();
                        String kategoriAdi = sc.nextLine();

                        Kategori aranan = co.kategoriAra(kategoriAdi);

                        aranan.kullanici = null;
                    } else if (silSecim == 2) {

                        System.out.println("\nKullanıcısı silinecek kategorinin adını girin:\n");
                        sc.nextLine();
                        String kategoriAdi = sc.nextLine();

                        Kategori aranan = co.kategoriAra(kategoriAdi);

                        if (aranan != null) {

                            if (aranan.kullanici != null) {
                                System.out.println("Silinecek kullanıcının id'sini girin");
                                String id = sc.nextLine();
                                Kullanici arananKullanici = co.kullaniciKontrolEt(aranan.kullanici, id);

                                if (arananKullanici != null) {
                                    co.kategoriVeAltKategorilerdenSil(aranan, arananKullanici.kullaniciId);
                                } else {
                                    System.out.println("(!)" + id + " kullanıcısı " + kategoriAdi + " kategorisinin altında bulunamadı.");
                                }

                            } else {
                                System.out.println("(!)" + kategoriAdi + " kategorisinin kullanıcı listesi bulunmamaktadır.");
                            }
                        } else {
                            System.out.println("(!)" + kategoriAdi + " kategorisi bulunamadı.");
                        }

                    } else if (silSecim == 3) {
                        System.out.println("Tüm kategorilerden silinmesi istediğiniz kullanıcının id'sini girin");
                        sc.nextLine();

                        String id = sc.nextLine();

                        co.kullaniciyiTumKategorilerdenSil(id);

                        //co.tumKategorilerdekiKullanicilariYazdir();
                    }

                }

            } else if (secim == 3) {

                System.out.println("\n-Tüm Kategorileri yazdırmak için 1\n"
                        + "-Tüm kullanıcıları yazdırmak için 2\n"
                        + "-Bir kategori bilgilerini yazdırmak için 3\n"
                        + "-Bir kategori altındaki kullanıcıları yazdırmak için 4\n"
                        + "-Kullanıcıya göre rezervasyon listelemek için 5 (yapılan rezervasyonları tekrarsız yazdırır)\n"
                        + "-Aynı yerde rezervasyon yapmış olan kullanıcıları listelemek için 6\n"
                        + "-Bir kullanıcıya ait tüm kategorilerdeki rezervasyon yerlerini tekrarsız yazdırmak için 7 girin\n");

                int islem = sc.nextInt();
                sc.nextLine();

                switch (islem) {
                    case 1:

                        co.yazdir();

                        break;

                    case 2:

                        co.tumKategorilerdekiKullanicilariYazdir();

                        break;

                    case 3:
                        System.out.println("\nBilgileri yazdırılacak kategorinin adını girin : \n");
                        String kategoriAdi = sc.nextLine();
                        co.birKategorininBilgileriniYazdir(kategoriAdi);

                        break;

                    case 4:

                        System.out.println("\n Kullanıcıları yazdırılacak kategorinin adını girin : \n");
                        kategoriAdi = sc.nextLine();
                        System.out.println("");
                        co.kategoriyeGoreKullanicilariListele(kategoriAdi);

                        break;

                    case 5:

                        System.out.println("\nRezervasyonları listelenecek kullanıcının id'sini girin: \n");
                        kullaniciId = sc.nextLine();

                        co.kullaniciyaGoreKategoriListele(kullaniciId);

                        break;

                    case 6:

                        System.out.println("\n -- Aynı yerde rezervasyon yapmış kullanıcılar ve kategorileri --- \n");
                        co.ayniYerdeRezervasyonYapmisTumKullanicilar(altKategoriler);

                        break;

                    case 7:

                        System.out.println("\nRezervasyonları listelenecek kullanıcının id'sini girin : \n");

                        kullaniciId = sc.nextLine();

                        co.kullaniciyaGoreTumRezervasyonlariListele(kullaniciId);

                        break;
                }
            }
        }
    }

}
