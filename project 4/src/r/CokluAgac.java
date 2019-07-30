/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r;

import java.util.ArrayList;
import r.Dugum;
import r.Kullanici;

/**
 *
 * @author hacib
 */
public class CokluAgac {

    Kategori root;
    ArrayList<Kategori> adresler;

    Kullanici[] kullaniciListeleri;

    public CokluAgac() {
        root = new Kategori();
        root.kategoriAdi = "Rezervasyon";
        root.kategoriYolu = "Rezervasyon";
        adresler = new ArrayList<Kategori>(100);

    }

    public boolean altDugumKontrol(int boyut, int indeks) {
        if (indeks >= (boyut / 2) && indeks <= boyut) {
            return true;
        }

        return false;
    }

    public int solDugum(int indeks) {
        return (2 * indeks);
    }

    public int sagDugum(int indeks) {
        return (2 * indeks) + 1;
    }

    public Kullanici[] maxHeapDuzenle(Kullanici[] dizi, int indeks) {
        if (altDugumKontrol(dizi.length, indeks)) {
            return null;
        }

        if (dizi[indeks].rezervasyonSayisi < dizi[solDugum(indeks)].rezervasyonSayisi) {

            if (dizi[solDugum(indeks)].rezervasyonSayisi > dizi[sagDugum(indeks)].rezervasyonSayisi) {
                degerDegistir(dizi, indeks, solDugum(indeks));
                maxHeapDuzenle(dizi, solDugum(indeks));
            } else {
                degerDegistir(dizi, indeks, sagDugum(indeks));
                maxHeapDuzenle(dizi, sagDugum(indeks));
            }
        }

        if (dizi[indeks].rezervasyonSayisi < dizi[sagDugum(indeks)].rezervasyonSayisi) {

            if (dizi[solDugum(indeks)].rezervasyonSayisi > dizi[sagDugum(indeks)].rezervasyonSayisi) {
                degerDegistir(dizi, indeks, solDugum(indeks));
                maxHeapDuzenle(dizi, solDugum(indeks));
            } else {
                degerDegistir(dizi, indeks, sagDugum(indeks));
                maxHeapDuzenle(dizi, sagDugum(indeks));
            }

        }
        return dizi;
    }

    public int parentDugum(int indeks) {
        return indeks / 2;
    }

    public void degerDegistir(Kullanici[] dizi, int indeks, int dugumIndeks) {

        int temp = dizi[indeks].rezervasyonSayisi;
        dizi[indeks].rezervasyonSayisi = dizi[dugumIndeks].rezervasyonSayisi;
        dizi[dugumIndeks].rezervasyonSayisi = temp;
    }

    public Kullanici kullaniciKontrolEt(Kullanici root, String id) {
//        if (k != null) {
//            if (k.kullaniciId.equals(id)) {
//                return k;
//            }
//            kullaniciKontrolEt(k.sol, id);
//            kullaniciKontrolEt(k.sag, id);
//        }
//
//        return null;

        Kullanici current, pre;
        Kullanici dondurulecek = null;
        int rezervasyonSayisi = 0;

        if (root == null) {
            return null;
        }

        current = root;
        while (current != null) {

            if (current.sol == null) {

                if (current.kullaniciId.equals(id)) {
                    dondurulecek = current;
                }
                current = current.sag;
            } else {

                pre = current.sol;
                while (pre.sag != null && pre.sag != current) {
                    pre = pre.sag;
                }

                if (pre.sag == null) {
                    pre.sag = current;
                    current = current.sol;
                } else {
                    pre.sag = null;

                    if (current.kullaniciId.equals(id)) {
                        dondurulecek = current;
                    }

                    current = current.sag;
                }

            }

        }

        return dondurulecek;
    }

    int sayac = 0;

    public int kullaniciSayisi(Kullanici k) {

        if (k != null) {
            sayac++;
            kullaniciSayisi(k.sol);
            kullaniciSayisi(k.sag);
        }

        return sayac;
    }

    public Kullanici kullaniciAra(Kategori k, String id) {

        Kullanici temp = k.kullanici;

        while (temp != null) {
            if (temp.kullaniciId.equals(id)) {
                return temp;
            } else {
                temp = temp.sag;
            }
        }

        return null;
    }

    public int kullaniciSayisiniGetir(Kategori k) {

        int sayi = 0;
        if (k.kullanici != null) {
            Kullanici temp = k.kullanici;

            while (temp != null) {
                sayi++;
                temp = temp.sag;
            }

        }

        return sayi;

    }

    public void kategorilerinKullaniciAgaclariniOlustur() {

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            if (temp2 != null) {
                //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi + " kategori yolu : " + temp2.kategoriYolu);
                if (temp2.kullanici != null) {
                    kullanicilariAgacaYerlestir(temp2);
                    rezervasyonSayilariniGuncelle(temp2, temp2.kullanici);
                }
                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];

                        if (temp3 != null) {
                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        adresler.add(temp3);
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                adresler.add(temp3);
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);
                                            if (temp3.kullanici != null) {
                                                kullanicilariAgacaYerlestir(temp3);
                                                rezervasyonSayilariniGuncelle(temp3, temp3.kullanici);
                                            }
                                            temp3.ziyaret = true;
                                            adresler.add(temp3);
                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {
                                            temp3 = temp3.listeSonraki[0];
                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null) {
                                            if (kontrol[k].ziyaret == false) {
                                                temp3 = kontrol[k];
                                                adresler.add(temp3);
                                                bulunduMu = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }

                    }
                }
            }

        }
    }

    public Kullanici[] kullanicilariDiziyeDoldur(Kategori kategori, Kullanici[] kullanicilar) {
        if (kategori.kullanici != null) {

            Kullanici temp = kategori.kullanici;

            int i = 1;

            while (temp != null) {
                kullanicilar[i] = temp;
                temp = temp.sag;
                i++;
            }

        }

        return kullanicilar;

    }

    public void kullanicilariAgacaYerlestir(Kategori kategori) {
        Kullanici[] dizi = new Kullanici[kullaniciSayisiniGetir(kategori) + 1];

        kullanicilariDiziyeDoldur(kategori, dizi);

        dizi[0] = new Kullanici();
        dizi[0].rezervasyonSayisi = Integer.MAX_VALUE;
        dizi[0].kullaniciId = "a";

        int yer;
        Kullanici enBuyuk;

        for (int i = 0; i < dizi.length; i++) {
            enBuyuk = dizi[i];
            yer = i;

            for (int j = i; j < dizi.length; j++) {
                if (dizi[j].rezervasyonSayisi > enBuyuk.rezervasyonSayisi) {
                    enBuyuk = dizi[j];
                    yer = j;
                }
            }

            Kullanici tut = dizi[i];
            dizi[i] = dizi[yer];
            dizi[yer] = tut;

        }

        for (int i = 0; i < dizi.length; i++) {
            dizi[i].sol = null;
            dizi[i].sag = null;
        }

        for (int i = 1; i < (dizi.length / 2) + 1; i++) {
            if (2 * i < dizi.length) {
                dizi[i].sol = dizi[2 * i];
            }
            if ((2 * i + 1) < dizi.length) {
                dizi[i].sag = dizi[2 * i + 1];
            }
        }

        kategori.kullanici = dizi[1];
        dizi = null;

    }

    public void kullaniciEkleMetod(Kategori kategori, String kategoriIsmi, Kullanici kullanici, String[] bagliListeElemanlari) {

        kullanici.rezervasyonSayisi = 1;

        if (kategori.kullanici == null) {
            kategori.kullanici = kullanici;
            LLekle(kullanici, bagliListeElemanlari);
        } else {

            Kullanici kontrol = kullaniciAra(kategori, kullanici.kullaniciId);

            if (kontrol == null) {
                Kullanici temp = kategori.kullanici;

                while (temp.sag != null) {
                    temp = temp.sag;
                }

                LLekle(kullanici, bagliListeElemanlari);
                temp.sag = kullanici;
            } else {
                kontrol.rezervasyonSayisi++;
                LLekle(kontrol, bagliListeElemanlari);
            }
        }

    }

    public void kullaniciDugumuEkle(Kategori kategori, String kullaniciId, String kategoriIsmi, String[] bagliListeElemanlari) {

        //System.out.println("\n\nKULLANICI ID : " + kullaniciId + "\n\n");
        Kullanici kullanici = new Kullanici(kullaniciId, kategoriIsmi);

        if (kategori.kullanici != null) {
            Kullanici kontrol = kullaniciKontrolEt(kategori.kullanici, kullaniciId);

            if (kontrol == null) {
                kullaniciEkleMetod(kategori, kategoriIsmi, kullanici, bagliListeElemanlari);
            } else {

                kontrol.rezervasyonSayisi++;
                LLekle(kontrol, bagliListeElemanlari);
            }
        } else {
            kullaniciEkleMetod(kategori, kategoriIsmi, kullanici, bagliListeElemanlari);
        }

    }

    public boolean ayniKategoriVarMi(Kategori[] kategori, String kategoriAdi) {
        if (kategori != null) {
            for (int i = 0; i < kategori.length; i++) {
                if (kategori[i].kategoriAdi.equals(kategoriAdi)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void kontrolEtVeEkle(Kategori kategori, Kategori eklenecek, String kategoriYolu) {

        if (!ayniKategoriVarMi(kategori.listeSonraki, eklenecek.kategoriAdi)) {
            if (kategori.listeSonraki == null) {
                kategori.listeSonraki = new Kategori[1];
                kategori.listeSonraki[0] = eklenecek;

                kategori.listeSonraki[0].onceki = kategori;

            } else if (kategori.listeSonraki.length == indeksGetir(kategori.listeSonraki)) {
                kategori.listeSonraki = boyutArttir(kategori.listeSonraki);
                kategori.listeSonraki[indeksGetir(kategori.listeSonraki)] = eklenecek;

                kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].onceki = kategori;
            }

            if (kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].onceki != null) {
                kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].kategoriYolu = kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].onceki.kategoriYolu + ":" + eklenecek.kategoriAdi;
            } else {
                kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].kategoriYolu = kategoriYolu;
            }

            //kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].kategoriYolu = kategoriYolu;
            kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].derinlik = kategori.listeSonraki[indeksGetir(kategori.listeSonraki) - 1].onceki.derinlik + 1;
            kategori.altKategoriSayisi = kategori.listeSonraki.length;
        } else {
            System.out.println("\n(!)Eklenmesi istenen kategori zaten mevcut");
        }

    }

    public void ekle(String yer, String eklenecek, String kategoriYolu, String kullaniciId, String kategoriIsmi, String kullaniciEklenecekYer, String[] bagliListeElemanlari) {

        ziyaretleriSifirla();

        boolean eklenecekYerBulundu = false;

        Kategori kategori = new Kategori(eklenecek);

        if (yer.equals("Rezervasyon")) {

            if (root.listeSonraki != null) {
                if (!ara2(root.listeSonraki, eklenecek)) {
                    kontrolEtVeEkle(root, kategori, kategoriYolu);

                }
            } else {

                kontrolEtVeEkle(root, kategori, kategoriYolu);
            }

            /// kullanici dugumu eklemek
            if (eklenecek.equals(kullaniciEklenecekYer)) {
                kullaniciDugumuEkle(kategori, kullaniciId, kategoriIsmi, bagliListeElemanlari);
            }

            eklenecekYerBulundu = true;

            return;
        } else {

            Kategori[] temp = root.listeSonraki;

            if (temp != null) {

                for (int i = 0; i < temp.length; i++) {
                    Kategori temp2 = temp[i];

                    if (temp2.kategoriAdi.equals(yer)) {
                        if (!ara2(temp2.listeSonraki, eklenecek)) {
                            kontrolEtVeEkle(temp2, kategori, kategoriYolu);
                        }
                        /// kullanici dugumu eklemek
                        if (eklenecek.equals(kullaniciEklenecekYer)) {
                            kullaniciDugumuEkle(kategori, kullaniciId, kategoriIsmi, bagliListeElemanlari);
                        }

                        eklenecekYerBulundu = true;
                        return;
                    } else {

                        Kategori[] temp3 = temp2.listeSonraki;

                        if (temp3 != null) {
                            for (int j = 0; j < temp3.length; j++) {

                                Kategori temp4 = temp3[j];

                                if (temp4.kategoriAdi.equals(yer)) {
                                    if (!ara2(temp4.listeSonraki, eklenecek)) {
                                        kontrolEtVeEkle(temp4, kategori, kategoriYolu);
                                    }
                                    /// kullanici dugumu eklemek
                                    if (eklenecek.equals(kullaniciEklenecekYer)) {
                                        kullaniciDugumuEkle(kategori, kullaniciId, kategoriIsmi, bagliListeElemanlari);
                                    }

                                    eklenecekYerBulundu = true;
                                    return;
                                } else {

                                    if (temp4.listeSonraki != null) {
                                        Kategori[] temp5 = temp4.listeSonraki;
                                        Kategori temp6 = temp5[0];

                                        while (!temp6.equals(temp4)) {

                                            if (temp6.kategoriAdi.equals(yer)) {
                                                if (!ara2(temp6.listeSonraki, eklenecek)) {
                                                    kontrolEtVeEkle(temp6, kategori, kategoriYolu);
                                                }
                                                /// kullanici dugumu eklemek
                                                if (eklenecek.equals(kullaniciEklenecekYer)) {
                                                    kullaniciDugumuEkle(kategori, kullaniciId, kategoriIsmi, bagliListeElemanlari);
                                                }

                                                eklenecekYerBulundu = true;
                                                return;
                                            }

                                            while (temp6.listeSonraki != null) {
                                                if (temp6.ekleZiyaret == false) {
                                                    temp6.ekleZiyaret = true;
                                                    adresler.add(temp6);

                                                    if (temp6.kategoriAdi.equals(yer)) {
                                                        if (!ara2(temp6.listeSonraki, eklenecek)) {
                                                            kontrolEtVeEkle(temp6, kategori, kategoriYolu);
                                                        }
                                                        /// kullanici dugumu eklemek
                                                        if (eklenecek.equals(kullaniciEklenecekYer)) {
                                                            kullaniciDugumuEkle(kategori, kullaniciId, kategoriIsmi, bagliListeElemanlari);
                                                        }

                                                        eklenecekYerBulundu = true;
                                                        return;
                                                    }

                                                    temp6 = temp6.listeSonraki[0];
                                                } else if (temp6.ekleZiyaret == true) {
                                                    if (temp6.listeSonraki != null) {
                                                        boolean ziyaretEdilmemisBul = false;

                                                        for (int k = 0; k < temp6.listeSonraki.length; k++) {
                                                            if (temp6.listeSonraki[k].ekleZiyaret == false) {
                                                                temp6 = temp6.listeSonraki[k];
                                                                temp6.ekleZiyaret = true;
                                                                adresler.add(temp6);
                                                                ziyaretEdilmemisBul = true;
                                                                break;
                                                            }
                                                        }
                                                        if (ziyaretEdilmemisBul) {
                                                            continue;
                                                        } else {
                                                            temp6 = temp6.onceki;
                                                        }
                                                    }
                                                }

                                            } /// burasi

                                            /// eger temp6 nın liste sonrakisi nullsa ve temp6 eklenecek yer ise
                                            if (temp6.kategoriAdi.equals(yer)) {
                                                if (!ara2(temp6.listeSonraki, eklenecek)) {
                                                    kontrolEtVeEkle(temp6, kategori, kategoriYolu);
                                                }
                                                /// kullanici dugumu eklemek
                                                if (eklenecek.equals(kullaniciEklenecekYer)) {
                                                    kullaniciDugumuEkle(kategori, kullaniciId, kategoriIsmi, bagliListeElemanlari);
                                                }

                                                eklenecekYerBulundu = true;
                                                return;
                                            }

                                            Kategori[] kontrol = temp6.onceki.listeSonraki;

                                            boolean ziyaretEdilmemisBul = false;

                                            for (int k = 0; k < kontrol.length; k++) {
                                                if (kontrol[k].ekleZiyaret == false) {
                                                    temp6 = kontrol[k];
                                                    kontrol[k].ekleZiyaret = true;
                                                    adresler.add(kontrol[k]);
                                                    ziyaretEdilmemisBul = true;
                                                    break;
                                                }
                                            }

                                            if (ziyaretEdilmemisBul) {
                                                continue;
                                            } else {
                                                temp6 = temp6.onceki;
                                            }

                                        }
                                    }

                                }

                            }
                        }

                    }

                }

            }

        }

        if (!eklenecekYerBulundu) {
            System.out.println(yer + " kategorisi bulunamadı. Ekleme işlemi yapılamadı");
        }

    }

    public void yazdir() {

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            if (temp2 != null) {
                System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi + " kategori yolu : " + temp2.kategoriYolu);

                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];

                        if (temp3 != null) {
                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        adresler.add(temp3);
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                adresler.add(temp3);
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);
                                            temp3.ziyaret = true;
                                            adresler.add(temp3);
                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {
                                            Kategori temp5 = temp3;

                                            for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                                temp3 = temp3.listeSonraki[k];
                                                break;
                                            }

                                            if (temp5.equals(temp3)) {
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null) {
                                            if (kontrol[k].ziyaret == false) {
                                                temp3 = kontrol[k];
                                                adresler.add(temp3);
                                                bulunduMu = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }

                    }
                }
            }

        }
    }

    public boolean ara(String aranacak) {

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi);

            if (temp2.kategoriAdi.equals(aranacak)) {
                return true;
            }

            if (temp2.listeSonraki != null) {
                for (int j = 0; j < temp2.listeSonraki.length; j++) {

                    Kategori temp3 = temp2.listeSonraki[j];
                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                    if (temp3.kategoriAdi.equals(aranacak)) {
                        return true;
                    }

                    if (temp2.listeSonraki[j].listeSonraki != null) {
                        for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                            if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                break;
                            }
                        }
                    }

                    while (!temp3.equals(temp2)) {

                        /// burasi
                        if (temp3.ziyaret == true) {
                            if (temp3.listeSonraki != null) {

                                for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                    if (temp3.listeSonraki[k].ziyaret == false) {
                                        temp3 = temp3.listeSonraki[k];
                                        break;
                                    }
                                }
                            }

                        }

                        if (temp3.ziyaret == false) {
                            //System.out.println(temp3.kategoriAdi);
                            Kategori temp4 = null;

                            while (temp3 != null) {

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                                    if (temp3.kategoriAdi.equals(aranacak)) {
                                        return true;
                                    }

                                    temp3.ziyaret = true;
                                    adresler.add(temp3);

                                }
                                temp4 = temp3;
                                if (temp3.listeSonraki != null) {
                                    temp3 = temp3.listeSonraki[0];
                                } else {
                                    break;
                                }
                            }

                            Kategori[] kontrol = temp3.onceki.listeSonraki;
                            boolean bulunduMu = false;

                            for (int k = 0; k < kontrol.length; k++) {
                                if (kontrol[k].ziyaret == false) {
                                    temp3 = kontrol[k];
                                    bulunduMu = true;
                                    break;
                                }
                            }

                            if (bulunduMu) {
                                continue;
                            } else {
                                temp3 = temp3.onceki;
                            }

                        } else {
                            temp3 = temp3.onceki;
                        }
                    }
                }
            }

        }
        return false;
    }

    public boolean ara2(Kategori[] kategori, String aranacak) {

        ziyaretleriSifirla();

        if (kategori != null) {
            for (int i = 0; i < kategori.length; i++) {
                if (kategori[i].kategoriAdi.equals(aranacak)) {
                    return true;
                }
            }
        }

        return false;
    }

    public Kategori kategoriAra(String aranacak) {

        ziyaretleriSifirla();

        if (root.kategoriAdi.equals(aranacak)) {
            return root;
        }

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi);
            if (temp2 != null) {

                if (temp2.kategoriAdi.equals(aranacak)) {
                    return temp2;
                }

                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];
                        //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                        if (temp3 != null) {
                            if (temp3.kategoriAdi.equals(aranacak)) {
                                return temp3;
                            }

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                                            if (temp3.kategoriAdi.equals(aranacak)) {
                                                return temp3;
                                            }

                                            temp3.ziyaret = true;
                                            adresler.add(temp3);

                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {
                                            temp3 = temp3.listeSonraki[0];
                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null && kontrol[k].ziyaret == false) {
                                            temp3 = kontrol[k];
                                            bulunduMu = true;
                                            break;
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }
                    }
                }
            }

        }
        return null;
    }

    public boolean altKategoriKontrolu(Kategori[] k, String aranacak) {
        for (int i = 0; i < k.length; i++) {
            if (k[i].kategoriAdi.equals(aranacak)) {
                return true;
            }
        }

        return false;
    }

    public void ziyaretleriSifirla() {
        for (int i = 0; i < adresler.size(); i++) {
            adresler.get(i).ziyaret = false;
            adresler.get(i).ekleZiyaret = false;
        }

    }

    int indeksGetir(Object[] liste) {
        int sayac = 0;

        for (int i = 0; i < liste.length; i++) {
            if (liste[i] != null) {
                sayac++;
            }
        }

        return sayac;
    }

    Kategori[] boyutArttir(Kategori[] liste) {
        Kategori[] k = new Kategori[liste.length + 1];

        for (int i = 0; i < liste.length; i++) {
            k[i] = liste[i];
        }

        return k;

    }

    Kullanici[] boyutArttir(Kullanici[] liste) {
        Kullanici[] k = new Kullanici[liste.length + 1];

        for (int i = 0; i < liste.length; i++) {
            k[i] = liste[i];
        }

        return k;

    }

    public void ekle2(String[] eklenecek, String kategoriYolu, String kullaniciId, String kategoriIsmi, String[] bagliListeElemanlari) {

        boolean varMi = false;
        if (root.listeSonraki == null) {
            ekle("Rezervasyon", eklenecek[0], "Rezervasyon", kullaniciId, kategoriIsmi, eklenecek[eklenecek.length - 1], bagliListeElemanlari);

            varMi = true;
        } else if (root.listeSonraki != null) {
            for (int i = 0; i < root.listeSonraki.length; i++) {

                if (eklenecek[0].equals(root.listeSonraki[i].kategoriAdi)) {
                    varMi = true;
                    break;
                }

            }
        }

        if (!varMi) {

            ekle("Rezervasyon", eklenecek[0], "", kullaniciId, kategoriIsmi, eklenecek[eklenecek.length - 1], bagliListeElemanlari);
            varMi = true;

        }

        if (varMi) {

            for (int i = 1; i < eklenecek.length; i++) {
                Kategori aranan = kategoriAra(eklenecek[i]);
                if (aranan != null) {
                    if (aranan.onceki.kategoriAdi.equals(eklenecek[i - 1]) && i == (eklenecek.length - 1)) {
                        // bir şey yapma
                        kullaniciDugumuEkle(aranan, kullaniciId, kategoriIsmi, bagliListeElemanlari);
                    } else {
                        //ekle(eklenecek[i - 1], eklenecek[i], kategoriYolu);

                        ekle(eklenecek[i - 1], eklenecek[i], kategoriYolu, kullaniciId, kategoriIsmi, eklenecek[eklenecek.length - 1], bagliListeElemanlari);
                    }
                } else {
                    //ekle(eklenecek[i - 1], eklenecek[i], kategoriYolu);

                    ekle(eklenecek[i - 1], eklenecek[i], kategoriYolu, kullaniciId, kategoriIsmi, eklenecek[eklenecek.length - 1], bagliListeElemanlari);
                }
            }

        }

    }

    public void preOrderDolas(Kullanici k) {
        if (k == null) {
            return;
        }

        System.out.println(k.kullaniciId);
        if (k.sol != null) {

            preOrderDolas(k.sol);
        }
        if (k.sag != null) {

            preOrderDolas(k.sag);
        }

    }

    public void birKategorininBilgileriniYazdir(String kategoriAdi) {
        Kategori aranan = kategoriAra(kategoriAdi);

        if (aranan != null) {
            sayac = 0;
            System.out.printf("%n%-25s %30s", "Kategori adi : ", aranan.kategoriAdi);
            System.out.printf("%n%-25s %30s", "Alt kategori sayısı : ", aranan.altKategoriSayisi);
            System.out.printf("%n%-25s %30s", "Kullanici sayisi : ", kullaniciSayisi(aranan.kullanici));
            System.out.printf("%n%-25s %30s", "Rezervasyon sayisi : ", aranan.rezervasyonSayisi);
            System.out.printf("%n%-25s %30s", "Kategori yolu : ", aranan.kategoriYolu);
            System.out.printf("%n%-25s %30s%n", "Derinlik : ", aranan.derinlik);

        }

    }

    public void kategoriSil(String aranacak) {

        ziyaretleriSifirla();

        boolean kategoriBulunduMu = false;

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi + " kategori yolu : " + temp2.kategoriYolu);

            if (temp2.kategoriAdi.equals(aranacak)) {
                // kullanıcıları silmek
                temp2.kullanici = null;

                Kategori[] tempDizi = temp2.onceki.listeSonraki;

                if (temp2.listeSonraki != null) {

                    temp2.onceki.listeSonraki = new Kategori[tempDizi.length + temp2.listeSonraki.length];

                } else {

                    temp2.onceki.listeSonraki = new Kategori[tempDizi.length - 1];

                }

                int w = 0;
                for (int j = 0; j < tempDizi.length; j++) {
                    if (tempDizi[j] != null && !tempDizi[j].kategoriAdi.equals(aranacak)) {
                        temp2.onceki.listeSonraki[w++] = tempDizi[j];
                    }
                }

                if (temp2.listeSonraki != null) {

                    for (int j = 0; j < temp2.listeSonraki.length; j++) {
                        temp2.onceki.listeSonraki[tempDizi.length + j] = temp2.listeSonraki[j];
                        if (temp2.onceki != null) {
                            temp2.onceki.listeSonraki[tempDizi.length + j].onceki = temp2.onceki;
                            if (temp2.onceki != null) {
                                temp2.onceki.listeSonraki[tempDizi.length + j].derinlik = temp2.onceki.derinlik + 1;
                            } else {
                                temp2.onceki.listeSonraki[tempDizi.length + j].derinlik = 1;
                            }

                            temp2.onceki.listeSonraki[tempDizi.length + j].kategoriYolu = temp2.onceki.kategoriYolu + ":" + temp2.onceki.listeSonraki[tempDizi.length + j].kategoriAdi;

                        }

                    }

                    int nullSayisi = 0;

                    for (int j = 0; j < temp2.onceki.listeSonraki.length; j++) {
                        if (temp2.onceki.listeSonraki[j] == null) {
                            nullSayisi++;
                        }
                    }

                    Kategori[] tut = temp2.onceki.listeSonraki;

                    temp2.onceki.listeSonraki = new Kategori[temp2.onceki.listeSonraki.length - nullSayisi];
                    int z = 0;
                    for (int j = 0; j < tut.length; j++) {
                        if (tut[j] != null) {
                            temp2.onceki.listeSonraki[z++] = tut[j];
                        }
                    }

                    temp2.listeSonraki = null;
                    temp2 = null;

                }

                derinlikleriTekrardanDuzenle();
                kategoriYolunuDuzenle();

                return;

            }

            if (temp2.listeSonraki != null) {
                for (int j = 0; j < temp2.listeSonraki.length; j++) {

                    Kategori temp3 = temp2.listeSonraki[j];

                    if (temp3 != null && temp3.kategoriAdi.equals(aranacak)) {
                        // kullanıcıları silmek
                        temp3.kullanici = null;

                        Kategori[] tempDizi = temp3.onceki.listeSonraki;

                        if (temp3.listeSonraki != null) {

                            temp3.onceki.listeSonraki = new Kategori[tempDizi.length + temp3.listeSonraki.length];

                        } else {

                            temp3.onceki.listeSonraki = new Kategori[tempDizi.length - 1];

                        }
                        int w = 0;
                        for (int k = 0; k < tempDizi.length; k++) {
                            if (tempDizi[k] != null && !tempDizi[k].kategoriAdi.equals(aranacak)) {
                                temp3.onceki.listeSonraki[w++] = tempDizi[k];
                            }
                        }

                        if (temp3.listeSonraki != null) {

                            for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                temp3.onceki.listeSonraki[tempDizi.length + k] = temp3.listeSonraki[k];
                                if (temp3.onceki != null) {
                                    temp3.onceki.listeSonraki[tempDizi.length + k].onceki = temp3.onceki;
                                    if (temp3.onceki != null) {
                                        temp3.onceki.listeSonraki[tempDizi.length + j].derinlik = temp3.onceki.derinlik + 1;
                                    } else {
                                        temp3.onceki.listeSonraki[tempDizi.length + j].derinlik = 1;
                                    }

                                    temp3.onceki.listeSonraki[tempDizi.length + j].kategoriYolu = temp3.onceki.kategoriYolu + ":" + temp3.onceki.listeSonraki[tempDizi.length + j].kategoriAdi;

                                }

                            }

                            int nullSayisi = 0;

                            for (int m = 0; m < temp3.onceki.listeSonraki.length; m++) {
                                if (temp3.onceki.listeSonraki[m] == null) {
                                    nullSayisi++;
                                }
                            }

                            Kategori[] tut = temp3.onceki.listeSonraki;

                            temp3.onceki.listeSonraki = new Kategori[temp3.onceki.listeSonraki.length - nullSayisi];
                            int z = 0;
                            for (int m = 0; m < tut.length; m++) {
                                if (tut[m] != null) {
                                    temp3.onceki.listeSonraki[z++] = tut[m];
                                }
                            }

                            temp3.listeSonraki = null;
                            temp3 = null;

                        }

                        derinlikleriTekrardanDuzenle();
                        kategoriYolunuDuzenle();

                        return;

                    }

                    if (temp3 != null && temp3.listeSonraki != null) {
                        //if (temp3.listeSonraki[j].listeSonraki != null) {
                        for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                            if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                adresler.add(temp3);
                                break;
                            }
                        }
                        //}
                    }

                    if (temp3 != null) {
                        while (!temp3.equals(temp2)) {

                            if (temp3.ziyaret == true) {
                                if (temp3.listeSonraki != null) {

                                    for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                        if (temp3.listeSonraki[k].ziyaret == false) {
                                            temp3 = temp3.listeSonraki[k];
                                            adresler.add(temp3);
                                            break;
                                        }
                                    }
                                }

                            }

                            if (temp3.ziyaret == false) {
                                Kategori temp4 = null;

                                while (temp3 != null) {

                                    if (temp3.ziyaret == false) {
                                        //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);
                                        if (temp3.kategoriAdi.equals(aranacak)) {
                                            // kullanıcıları silmek
                                            temp3.kullanici = null;

                                            Kategori[] tempDizi = temp3.onceki.listeSonraki;
                                            if (temp3.listeSonraki != null) {

                                                temp3.onceki.listeSonraki = new Kategori[tempDizi.length + temp3.listeSonraki.length];

                                            } else {

                                                temp3.onceki.listeSonraki = new Kategori[tempDizi.length - 1];

                                            }

                                            int w = 0;

                                            for (int k = 0; k < tempDizi.length; k++) {
                                                if (tempDizi[k] != null && !tempDizi[k].kategoriAdi.equals(aranacak)) {
                                                    temp3.onceki.listeSonraki[w++] = tempDizi[k];
                                                }
                                            }

                                            if (temp3.listeSonraki != null) {

                                                for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                                    temp3.onceki.listeSonraki[tempDizi.length + k] = temp3.listeSonraki[k];
                                                    if (temp3.onceki != null) {
                                                        temp3.onceki.listeSonraki[tempDizi.length + k].onceki = temp3.onceki;
                                                        if (temp3.onceki != null) {
                                                            temp3.onceki.listeSonraki[tempDizi.length + j].derinlik = temp3.onceki.derinlik + 1;
                                                        } else {
                                                            temp3.onceki.listeSonraki[tempDizi.length + j].derinlik = 1;
                                                        }
                                                    }

                                                }

                                                int nullSayisi = 0;

                                                for (int m = 0; m < temp3.onceki.listeSonraki.length; m++) {
                                                    if (temp3.onceki.listeSonraki[m] == null) {
                                                        nullSayisi++;
                                                    }
                                                }

                                                Kategori[] tut = temp3.onceki.listeSonraki;

                                                temp3.onceki.listeSonraki = new Kategori[temp3.onceki.listeSonraki.length - nullSayisi];
                                                int z = 0;
                                                for (int m = 0; m < tut.length; m++) {
                                                    if (tut[m] != null) {
                                                        temp3.onceki.listeSonraki[z++] = tut[m];
                                                    }
                                                }

                                                temp3.listeSonraki = null;
                                                temp3 = null;

                                            }

                                            derinlikleriTekrardanDuzenle();
                                            kategoriYolunuDuzenle();

                                            return;

                                        }

                                        temp3.ziyaret = true;
                                        adresler.add(temp3);
                                    }
                                    temp4 = temp3;
                                    if (temp3.listeSonraki != null) {
                                        temp3 = temp3.listeSonraki[0];
                                    } else {
                                        break;
                                    }
                                }

                                Kategori[] kontrol = temp3.onceki.listeSonraki;
                                boolean bulunduMu = false;

                                for (int k = 0; k < kontrol.length; k++) {
                                    if (kontrol[k] != null && kontrol[k].ziyaret == false) {
                                        temp3 = kontrol[k];
                                        adresler.add(temp3);
                                        bulunduMu = true;
                                        break;
                                    }
                                }

                                if (bulunduMu) {
                                    continue;
                                } else {
                                    temp3 = temp3.onceki;
                                }

                            } else {
                                temp3 = temp3.onceki;
                            }
                        }
                    }
                }
            }

        }
    }

///// SORGU İÇİN METODLAR
    // check
    public void kategoriyeGoreKullanicilariListele(String aranacak) {

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi);

            if (temp2.kategoriAdi.equals(aranacak)) {
                preOrderDolas(temp2.kullanici);
                return;
            }

            if (temp2.listeSonraki != null) {
                for (int j = 0; j < temp2.listeSonraki.length; j++) {

                    Kategori temp3 = temp2.listeSonraki[j];
                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                    if (temp3.kategoriAdi.equals(aranacak)) {
                        preOrderDolas(temp3.kullanici);
                        return;
                    }

                    if (temp2.listeSonraki[j].listeSonraki != null) {
                        for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                            if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                break;
                            }
                        }
                    }

                    while (!temp3.equals(temp2)) {

                        /// burasi
                        if (temp3.ziyaret == true) {
                            if (temp3.listeSonraki != null) {

                                for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                    if (temp3.listeSonraki[k].ziyaret == false) {
                                        temp3 = temp3.listeSonraki[k];
                                        break;
                                    }
                                }
                            }

                        }

                        if (temp3.ziyaret == false) {
                            //System.out.println(temp3.kategoriAdi);
                            Kategori temp4 = null;

                            while (temp3 != null) {

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                                    if (temp3.kategoriAdi.equals(aranacak)) {
                                        preOrderDolas(temp3.kullanici);
                                        return;
                                    }

                                    temp3.ziyaret = true;
                                    adresler.add(temp3);

                                }
                                temp4 = temp3;
                                if (temp3.listeSonraki != null) {
                                    temp3 = temp3.listeSonraki[0];
                                } else {
                                    break;
                                }
                            }

                            Kategori[] kontrol = temp3.onceki.listeSonraki;
                            boolean bulunduMu = false;

                            for (int k = 0; k < kontrol.length; k++) {
                                if (kontrol[k].ziyaret == false) {
                                    temp3 = kontrol[k];
                                    bulunduMu = true;
                                    break;
                                }
                            }

                            if (bulunduMu) {
                                continue;
                            } else {
                                temp3 = temp3.onceki;
                            }

                        } else {
                            temp3 = temp3.onceki;
                        }
                    }
                }
            }

        }
    }

    // check
    public void tumKategorilerdekiKullanicilariYazdir() {

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            if (temp2 != null) {
                System.out.println("\n---" + temp2.kategoriAdi + "---");

                if (temp2.kullanici != null) {
                    //kategoriyeGoreKullanicilariListele(temp2.kategoriAdi);
                    preOrderDolas(temp2.kullanici);
                }

                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];

                        if (temp3 != null) {
                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        adresler.add(temp3);
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                adresler.add(temp3);
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            System.out.println("\n---" + temp3.kategoriAdi + "---");
                                            if (temp3.kullanici != null) {
                                                //kategoriyeGoreKullanicilariListele(temp3.kategoriAdi);
                                                preOrderDolas(temp3.kullanici);
                                            }

                                            temp3.ziyaret = true;
                                            adresler.add(temp3);
                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {
                                            temp3 = temp3.listeSonraki[0];
                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null) {
                                            if (kontrol[k].ziyaret == false) {
                                                temp3 = kontrol[k];
                                                adresler.add(temp3);
                                                bulunduMu = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }

                    }
                }
            }

        }

    }

    /// doesnt work X
    // check
    public void birKullanicininRezervasyonlari(String kategoriAdi, String kullaniciId) {
        Kategori k = kategoriAra(kategoriAdi);
        if (k != null) {
            Kullanici k2 = kullaniciKontrolEt(k.kullanici, kullaniciId);
            if (k2 != null) {
                Dugum temp = k2.kullaniciRezervasyonlari;

                while (temp != null) {
                    System.out.println(temp.yerId + ", " + temp.zaman + ", " + temp.enlem + ", " + temp.boylam + ", " + temp.sehir);
                    temp = temp.sonraki;
                }
            }
        }

    }

    // check
    public void kullaniciyaGoreKategoriListele(String kullaniciId) {

        System.out.println("\n\n KULLANICININ REZERVASYON YAPTIGI KATEGORİLER \n");

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi + " kategori yolu : " + temp2.kategoriYolu);

            Kullanici kullanici = kullaniciKontrolEt(temp2.kullanici, kullaniciId);

            if (kullanici != null) {
                System.out.println(temp2.kategoriAdi);
            }

            if (temp2.listeSonraki != null) {
                for (int j = 0; j < temp2.listeSonraki.length; j++) {

                    Kategori temp3 = temp2.listeSonraki[j];
                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                    if (temp2.listeSonraki[j].listeSonraki != null) {
                        for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                            if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                break;
                            }
                        }
                    }

                    while (!temp3.equals(temp2)) {

                        /// burasi
                        if (temp3.ziyaret == true) {
                            if (temp3.listeSonraki != null) {

                                for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                    if (temp3.listeSonraki[k].ziyaret == false) {
                                        temp3 = temp3.listeSonraki[k];
                                        break;
                                    }
                                }
                            }

                        }

                        if (temp3.ziyaret == false) {
                            //System.out.println(temp3.kategoriAdi);
                            Kategori temp4 = null;

                            while (temp3 != null) {

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);
                                    kullanici = kullaniciKontrolEt(temp3.kullanici, kullaniciId);

                                    if (kullanici != null) {
                                        System.out.println(temp3.kategoriAdi);
                                    }
                                    temp3.ziyaret = true;
                                }
                                temp4 = temp3;
                                if (temp3.listeSonraki != null) {
                                    temp3 = temp3.listeSonraki[0];
                                } else {
                                    break;
                                }
                            }

                            Kategori[] kontrol = temp3.onceki.listeSonraki;
                            boolean bulunduMu = false;

                            for (int k = 0; k < kontrol.length; k++) {
                                if (kontrol[k].ziyaret == false) {
                                    temp3 = kontrol[k];
                                    bulunduMu = true;
                                    break;
                                }
                            }

                            if (bulunduMu) {
                                continue;
                            } else {
                                temp3 = temp3.onceki;
                            }

                        } else {
                            temp3 = temp3.onceki;
                        }
                    }
                }
            }

        }

    }

    /// doesnt work beceause of birKullanicininRezervasyonlari method X
    // check
    public void kullaniciyaGoreTumRezervasyonlariListele(String kullaniciId) {

        ArrayList<String> yazilanYerler = new ArrayList<String>();

        System.out.println("\n\n KULLANICININ REZERVASYON YAPTIGI TÜM KATEGORİLER \n");

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {
            yazilanYerler.clear();

            Kategori temp2 = temp[i];
            //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi + " kategori yolu : " + temp2.kategoriYolu);

            Kullanici kullanici = kullaniciKontrolEt(temp2.kullanici, kullaniciId);

            if (kullanici != null) {
                System.out.println("\n" + temp2.kategoriAdi);
                Kullanici k2 = kullaniciKontrolEt(temp2.kullanici, kullaniciId);
                if (k2 != null) {
                    Dugum tut = k2.kullaniciRezervasyonlari;

                    while (tut != null) {

                        boolean yazildiMi = false;

                        for (int j = 0; j < yazilanYerler.size(); j++) {
                            if (yazilanYerler.get(i).equals(tut.yerId)) {
                                yazildiMi = true;
                            }
                        }

                        if (!yazildiMi) {
                            System.out.println(tut.yerId + ", " + tut.zaman + ", " + tut.enlem + ", " + tut.boylam + ", " + tut.sehir);
                        }

                        yazilanYerler.add(tut.yerId);

                        tut = tut.sonraki;
                    }
                }
            }

            if (temp2.listeSonraki != null) {
                for (int j = 0; j < temp2.listeSonraki.length; j++) {

                    yazilanYerler.clear();

                    Kategori temp3 = temp2.listeSonraki[j];
                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                    if (temp2.listeSonraki[j].listeSonraki != null) {
                        for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                            if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                break;
                            }
                        }
                    }

                    while (!temp3.equals(temp2)) {

                        yazilanYerler.clear();
                        /// burasi
                        if (temp3.ziyaret == true) {
                            if (temp3.listeSonraki != null) {

                                for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                    if (temp3.listeSonraki[k].ziyaret == false) {
                                        temp3 = temp3.listeSonraki[k];
                                        break;
                                    }
                                }
                            }

                        }

                        if (temp3.ziyaret == false) {
                            //System.out.println(temp3.kategoriAdi);
                            Kategori temp4 = null;

                            while (temp3 != null) {

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);
                                    kullanici = kullaniciKontrolEt(temp3.kullanici, kullaniciId);

                                    if (kullanici != null) {
                                        System.out.println("\n" + temp3.kategoriAdi);
                                        Kullanici k2 = kullaniciKontrolEt(temp3.kullanici, kullaniciId);
                                        if (k2 != null) {
                                            Dugum tut = k2.kullaniciRezervasyonlari;

                                            while (tut != null) {
                                                boolean yazildiMi = false;

                                                for (int m = 0; m < yazilanYerler.size(); m++) {
                                                    if (yazilanYerler.get(m).equals(tut.yerId)) {
                                                        yazildiMi = true;
                                                    }
                                                }

                                                if (!yazildiMi) {
                                                    System.out.println(tut.yerId + ", " + tut.zaman + ", " + tut.enlem + ", " + tut.boylam + ", " + tut.sehir);
                                                }

                                                yazilanYerler.add(tut.yerId);
                                                tut = tut.sonraki;
                                            }
                                        }
                                    }
                                    temp3.ziyaret = true;
                                }
                                temp4 = temp3;
                                if (temp3.listeSonraki != null) {
                                    temp3 = temp3.listeSonraki[0];
                                } else {
                                    break;
                                }
                            }

                            Kategori[] kontrol = temp3.onceki.listeSonraki;
                            boolean bulunduMu = false;

                            for (int k = 0; k < kontrol.length; k++) {
                                if (kontrol[k].ziyaret == false) {
                                    temp3 = kontrol[k];
                                    bulunduMu = true;
                                    break;
                                }
                            }

                            if (bulunduMu) {
                                continue;
                            } else {
                                temp3 = temp3.onceki;
                            }

                        } else {
                            temp3 = temp3.onceki;
                        }
                    }
                }
            }

        }
    }

    Temp ayikla;

    public void ayiklaEkle(String kullaniciId, String yerId, String kategoriAdi) {
        Temp ekle = new Temp();
        ekle.kullaniciId = kullaniciId;
        ekle.yerId = yerId;
        ekle.kategoriAdi = kategoriAdi;
        if (ayikla == null) {
            ayikla = ekle;
        } else {

            Temp temp = ayikla;
            boolean kontrol = false;
            while (temp != null) {
                if (temp.kullaniciId.equals(ekle.kullaniciId) && temp.yerId.equals(ekle.yerId) && temp.kategoriAdi.equals(ekle.kategoriAdi)) {

                    kontrol = true;
                    break;
                }

                temp = temp.sonraki;
            }

            if (!kontrol) {
                Temp temp2 = ayikla;

                while (temp2.sonraki != null) {
                    temp2 = temp2.sonraki;
                }

                temp2.sonraki = ekle;

            }

        }
    }

    public void ayiklaZiyaretleriFalse() {
        Temp temp = ayikla;

        while (temp != null) {
            temp.ziyaretEdildiMi = false;
            temp = temp.sonraki;
        }
    }

    public void ayiklaYazdir(ArrayList<String> rezervasyonYerleri) {

        boolean baslikYaz = false;

        for (int i = 0; i < rezervasyonYerleri.size(); i++) {
            baslikYaz = false;
            Temp temp = ayikla;

            while (temp != null) {
                if (temp.yerId.equals(rezervasyonYerleri.get(i)) && temp.ziyaretEdildiMi == false) {
                    if (!baslikYaz) {
                        System.out.println("\n • " + rezervasyonYerleri.get(i) + " • ");
                        baslikYaz = true;
                    }

                    System.out.println("     ->" + temp.kullaniciId + " kategorisi : " + temp.kategoriAdi);
                    temp.ziyaretEdildiMi = true;
                }

                temp = temp.sonraki;
            }

        }

    }

    public void ayniYerdeRezervasyonYapmisTumKullanicilar(ArrayList<String> rezervasyonYerleri) {
        boolean yazildiMi = false;

        ziyaretleriSifirla();

        ArrayList<String> yazilanKullanicilar = new ArrayList<String>();
        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {
            Kategori temp2 = temp[i];
            if (temp2 != null) {

                if (temp2.kullanici != null) {

                    for (int z = 0; z < rezervasyonYerleri.size(); z++) {
                        yazilanKullanicilar.clear();

                        ArrayList<Kullanici> kullanicilar = new ArrayList<Kullanici>();
                        diziyeAt(temp2.kullanici, kullanicilar);
                        for (int m = 0; m < kullanicilar.size(); m++) {
                            boolean kullaniciVarMi = false;
                            Dugum tut = kullanicilar.get(m).kullaniciRezervasyonlari;
                            //kullaniciVarMi = false;
                            while (tut != null) {
                                yazildiMi = false;
                                //for (int z = 0; z < rezervasyonYerleri.size(); z++) {
                                for (int k = 0; k < yazilanKullanicilar.size(); k++) {
                                    if (kullanicilar.get(m).kullaniciId.equals(yazilanKullanicilar.get(k))) {
                                        kullaniciVarMi = true;
                                    }
                                }

                                if (!kullaniciVarMi) {
                                    if (tut.yerId.equals(rezervasyonYerleri.get(z))) {
                                        yazildiMi = true;
                                        yazilanKullanicilar.add(kullanicilar.get(m).kullaniciId);
                                        //System.out.println(kullanicilar.get(m).kullaniciId + "\t" + rezervasyonYerleri.get(i));
                                        //System.out.printf("%-15s %15s%n", kullanicilar.get(m).kullaniciId, rezervasyonYerleri.get(i));

                                        ayiklaEkle(kullanicilar.get(m).kullaniciId, rezervasyonYerleri.get(z), temp2.kategoriAdi);

                                        //System.out.printf("%-35s %20s %n", kullanicilar.get(m).kullaniciId, rezervasyonYerleri.get(z));
                                        break;
                                    }
                                }

                                //}
                                if (yazildiMi) {
                                    break;
                                }

                                tut = tut.sonraki;
                            }
                        }
                    }

                }

                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];

                        if (temp3 != null) {
                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        adresler.add(temp3);
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                adresler.add(temp3);
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                                            if (temp3.kullanici != null) {

                                                //System.out.println(temp3.kategoriAdi);
                                                for (int z = 0; z < rezervasyonYerleri.size(); z++) {
                                                    // onemli
                                                    yazilanKullanicilar.clear();

                                                    ArrayList<Kullanici> kullanicilar = new ArrayList<Kullanici>();
                                                    diziyeAt(temp3.kullanici, kullanicilar);
                                                    for (int m = 0; m < kullanicilar.size(); m++) {
                                                        boolean kullaniciVarMi = false;
                                                        Dugum tut = kullanicilar.get(m).kullaniciRezervasyonlari;
                                                        //kullaniciVarMi = false;
                                                        while (tut != null) {
                                                            yazildiMi = false;
                                                            //for (int z = 0; z < rezervasyonYerleri.size(); z++) {
                                                            for (int k = 0; k < yazilanKullanicilar.size(); k++) {
                                                                if (kullanicilar.get(m).kullaniciId.equals(yazilanKullanicilar.get(k))) {
                                                                    kullaniciVarMi = true;
                                                                }
                                                            }

                                                            if (!kullaniciVarMi) {
                                                                if (tut.yerId.equals(rezervasyonYerleri.get(z))) {
                                                                    yazildiMi = true;
                                                                    yazilanKullanicilar.add(kullanicilar.get(m).kullaniciId);
                                                                    //System.out.println(kullanicilar.get(m).kullaniciId + "\t" + rezervasyonYerleri.get(i));
                                                                    //System.out.printf("%-15s %15s%n", kullanicilar.get(m).kullaniciId, rezervasyonYerleri.get(i));

                                                                    ayiklaEkle(kullanicilar.get(m).kullaniciId, rezervasyonYerleri.get(z), temp3.kategoriAdi);

                                                                    //System.out.printf("%-35s %20s %n", kullanicilar.get(m).kullaniciId, rezervasyonYerleri.get(z));
                                                                    break;
                                                                }
                                                            }

                                                            //}
                                                            if (yazildiMi) {
                                                                break;
                                                            }

                                                            tut = tut.sonraki;
                                                        }
                                                    }
                                                }

                                            }

                                            temp3.ziyaret = true;
                                            adresler.add(temp3);
                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {
                                            temp3 = temp3.listeSonraki[0];
                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null) {
                                            if (kontrol[k].ziyaret == false) {
                                                temp3 = kontrol[k];
                                                adresler.add(temp3);
                                                bulunduMu = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }

                    }
                }
            }

        }

        ayiklaYazdir(rezervasyonYerleri);

    }

    public void derinlikDuzenlemesi(Kategori kategori) {
        if (kategori.listeSonraki != null) {

            for (int i = 0; i < kategori.listeSonraki.length; i++) {
                kategori.listeSonraki[i].derinlik = kategori.listeSonraki[i].onceki.derinlik + 1;
            }

        } else {
            if (kategori.onceki != null) {
                kategori.derinlik = kategori.onceki.derinlik + 1;
            } else {
                kategori.derinlik = 1;
            }
        }
    }

    public void kategoriYoluDuzenlemesi(Kategori kategori) {
        if (kategori.listeSonraki != null) {
            for (int i = 0; i < kategori.listeSonraki.length; i++) {
                kategori.listeSonraki[i].kategoriYolu = kategori.listeSonraki[i].onceki.kategoriYolu + ":" + kategori.listeSonraki[i].kategoriAdi;
            }
        } else {
            if (kategori.onceki != null) {
                if (kategori.onceki.kategoriYolu != null || !kategori.onceki.kategoriYolu.isEmpty()) {
                    kategori.kategoriYolu = kategori.onceki.kategoriYolu + ":" + kategori.kategoriAdi;
                } else {
                    kategori.kategoriYolu = kategori.kategoriAdi;
                }
            }
        }
    }

    public void kategoriYolunuDuzenle() {

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            if (temp2 != null) {
                //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi + " kategori yolu : " + temp2.kategoriYolu);

                kategoriYoluDuzenlemesi(temp2);

                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];

                        if (temp3 != null) {
                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        adresler.add(temp3);
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                adresler.add(temp3);
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                                            kategoriYoluDuzenlemesi(temp3);
                                            temp3.ziyaret = true;
                                            adresler.add(temp3);
                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {
                                            Kategori temp5 = temp3;

                                            for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                                temp3 = temp3.listeSonraki[k];
                                                break;
                                            }

                                            if (temp5.equals(temp3)) {
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null) {
                                            if (kontrol[k].ziyaret == false) {
                                                temp3 = kontrol[k];
                                                adresler.add(temp3);
                                                bulunduMu = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }

                    }
                }
            }

        }
    }

    public void derinlikleriTekrardanDuzenle() {

        ziyaretleriSifirla();

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            if (temp2 != null) {
                //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi + " kategori yolu : " + temp2.kategoriYolu);

                derinlikDuzenlemesi(temp2);

                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];

                        if (temp3 != null) {
                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        adresler.add(temp3);
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                adresler.add(temp3);
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi + " kategori yolu : " + temp3.kategoriYolu);

                                            derinlikDuzenlemesi(temp3);
                                            temp3.ziyaret = true;
                                            adresler.add(temp3);
                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {

                                            Kategori temp5 = temp3;

                                            for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                                temp3 = temp3.listeSonraki[k];
                                                break;
                                            }

                                            if (temp5.equals(temp3)) {
                                                break;
                                            }

                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null) {
                                            if (kontrol[k].ziyaret == false) {
                                                temp3 = kontrol[k];
                                                adresler.add(temp3);
                                                bulunduMu = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }

                    }
                }
            }

        }
    }

    public void kategoriyeKullaniciEkle(String id, Kategori k) {

        ArrayList<Kullanici> list = new ArrayList<Kullanici>();

        diziyeAt(k.kullanici, list);

        boolean kullaniciVarMi = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).kullaniciId.equals(id)) {
                kullaniciVarMi = true;
            }
        }

        if (kullaniciVarMi) {
            System.out.println("(!)Kullanıcı zaten mevcut");
            return;
        }

        Kullanici eklenecek = new Kullanici(id, k.kategoriAdi, 1);
        list.add(eklenecek);

        Kullanici[] dizi = new Kullanici[list.size() + 1];
        for (int i = 0; i < list.size(); i++) {
            dizi[i + 1] = list.get(i);
        }

        int yer;
        Kullanici enBuyuk;

        for (int i = 1; i < dizi.length; i++) {
            enBuyuk = dizi[i];
            yer = i;

            for (int j = i; j < dizi.length; j++) {
                if (dizi[j].rezervasyonSayisi > enBuyuk.rezervasyonSayisi) {
                    enBuyuk = dizi[j];
                    yer = j;
                }
            }

            Kullanici tut = dizi[i];
            dizi[i] = dizi[yer];
            dizi[yer] = tut;

        }

        for (int i = 1; i < dizi.length; i++) {
            dizi[i].sol = null;
            dizi[i].sag = null;
        }

        for (int i = 1; i < (dizi.length / 2) + 1; i++) {
            if (2 * i < dizi.length) {
                dizi[i].sol = dizi[2 * i];
            }
            if ((2 * i + 1) < dizi.length) {
                dizi[i].sag = dizi[2 * i + 1];
            }
        }

        k.kullanici = dizi[1];
        dizi = null;

        rezervasyonSayilariniGuncelle(k, k.kullanici);

    }

    /// bir kullanıcıyı alt kategoriler dahil silmek
    /// 31 aralık 2018 23:12 forum cevabına göre rework edildi
    public void kategoriVeAltKategorilerdenSil(Kategori kategori, String id) {
        ArrayList<Kullanici> list = new ArrayList<Kullanici>();

        diziyeAt(kategori.kullanici, list);

        boolean kullaniciVarMi = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).kullaniciId.equals(id)) {
                kullaniciVarMi = true;
            }
        }
//
//        if (!kullaniciVarMi) {
//            System.out.println("(!)Silinmesi istenen kullanıcı bulunamadı");
//            return;
//        }

        if (kullaniciVarMi) {
            Kullanici[] dizi = new Kullanici[list.size()];
            int w = 1;
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).kullaniciId.equals(id)) {
                    dizi[w++] = list.get(i);
                }
            }

            int yer;
            Kullanici enBuyuk;

            for (int i = 1; i < dizi.length; i++) {
                enBuyuk = dizi[i];
                yer = i;

                for (int j = i; j < dizi.length; j++) {
                    if (dizi[j].rezervasyonSayisi > enBuyuk.rezervasyonSayisi) {
                        enBuyuk = dizi[j];
                        yer = j;
                    }
                }

                Kullanici tut = dizi[i];
                dizi[i] = dizi[yer];
                dizi[yer] = tut;

            }

            for (int i = 1; i < dizi.length; i++) {
                dizi[i].sol = null;
                dizi[i].sag = null;
            }

            for (int i = 1; i < (dizi.length / 2) + 1; i++) {
                if (2 * i < dizi.length) {
                    dizi[i].sol = dizi[2 * i];
                }
                if ((2 * i + 1) < dizi.length) {
                    dizi[i].sag = dizi[2 * i + 1];
                }
            }

            if (dizi.length == 1) {

                kategori.kullanici = dizi[0];
            } else {

                kategori.kullanici = dizi[1];
            }
            dizi = null;
        }

        if (kategori.listeSonraki != null) {

            for (int k = 0; k < kategori.listeSonraki.length; k++) {

                list.clear();

                diziyeAt(kategori.listeSonraki[k].kullanici, list);

                kullaniciVarMi = false;

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).kullaniciId.equals(id)) {
                        kullaniciVarMi = true;
                    }
                }

                if (kullaniciVarMi) {
                    Kullanici[] dizi = new Kullanici[list.size()];
                    int w = 1;
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).kullaniciId.equals(id)) {
                            dizi[w++] = list.get(i);
                        }
                    }

                    int yer;
                    Kullanici enBuyuk;

                    for (int i = 1; i < dizi.length; i++) {
                        enBuyuk = dizi[i];
                        yer = i;

                        for (int j = i; j < dizi.length; j++) {
                            if (dizi[j].rezervasyonSayisi > enBuyuk.rezervasyonSayisi) {
                                enBuyuk = dizi[j];
                                yer = j;
                            }
                        }

                        Kullanici tut = dizi[i];
                        dizi[i] = dizi[yer];
                        dizi[yer] = tut;

                    }

                    for (int i = 1; i < dizi.length; i++) {
                        dizi[i].sol = null;
                        dizi[i].sag = null;
                    }

                    for (int i = 1; i < (dizi.length / 2) + 1; i++) {
                        if (2 * i < dizi.length) {
                            dizi[i].sol = dizi[2 * i];
                        }
                        if ((2 * i + 1) < dizi.length) {
                            dizi[i].sag = dizi[2 * i + 1];
                        }
                    }

                    if (dizi.length == 1) {

                        kategori.listeSonraki[k].kullanici = dizi[0];
                    } else {

                        kategori.listeSonraki[k].kullanici = dizi[1];
                    }
                    dizi = null;
                }
            }

        }

    }

    ///
    public void kategoridenKullaniciSil(Kategori kategori, String id) {
        ArrayList<Kullanici> list = new ArrayList<Kullanici>();

        diziyeAt(kategori.kullanici, list);

        boolean kullaniciVarMi = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).kullaniciId.equals(id)) {
                kullaniciVarMi = true;
            }
        }
//
//        if (!kullaniciVarMi) {
//            System.out.println("(!)Silinmesi istenen kullanıcı bulunamadı");
//            return;
//        }

        if (kullaniciVarMi) {
            Kullanici[] dizi = new Kullanici[list.size()];
            int w = 1;
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).kullaniciId.equals(id)) {
                    dizi[w++] = list.get(i);
                }
            }

            int yer;
            Kullanici enBuyuk;

            for (int i = 1; i < dizi.length; i++) {
                enBuyuk = dizi[i];
                yer = i;

                for (int j = i; j < dizi.length; j++) {
                    if (dizi[j].rezervasyonSayisi > enBuyuk.rezervasyonSayisi) {
                        enBuyuk = dizi[j];
                        yer = j;
                    }
                }

                Kullanici tut = dizi[i];
                dizi[i] = dizi[yer];
                dizi[yer] = tut;

            }

            for (int i = 1; i < dizi.length; i++) {
                dizi[i].sol = null;
                dizi[i].sag = null;
            }

            for (int i = 1; i < (dizi.length / 2) + 1; i++) {
                if (2 * i < dizi.length) {
                    dizi[i].sol = dizi[2 * i];
                }
                if ((2 * i + 1) < dizi.length) {
                    dizi[i].sag = dizi[2 * i + 1];
                }
            }

            if (dizi.length == 1) {

                kategori.kullanici = dizi[0];
            } else {

                kategori.kullanici = dizi[1];
            }
            dizi = null;
        }

    }

    public Kategori kullaniciyiTumKategorilerdenSil(String aranacak) {

        ziyaretleriSifirla();

        if (root.kullanici != null) {
            kategoridenKullaniciSil(root, aranacak);
        }

        Kategori[] temp = root.listeSonraki;

        for (int i = 0; i < temp.length; i++) {

            Kategori temp2 = temp[i];
            //System.out.println(temp2.kategoriAdi + " derinlik : " + temp2.derinlik + " alt kategori sayısı : " + temp2.altKategoriSayisi);
            if (temp2 != null) {

                if (temp2.kullanici != null) {
                    Kullanici kontrol = kullaniciKontrolEt(temp2.kullanici, aranacak);
                    if (kontrol != null) {
                        kategoridenKullaniciSil(temp2, aranacak);
                    }
                }

                if (temp2.listeSonraki != null) {
                    for (int j = 0; j < temp2.listeSonraki.length; j++) {

                        Kategori temp3 = temp2.listeSonraki[j];
                        //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                        if (temp3 != null) {

                            if (temp3.kullanici != null) {

                                Kullanici kontrol = kullaniciKontrolEt(temp3.kullanici, aranacak);
                                if (kontrol != null) {
                                    kategoridenKullaniciSil(temp3, aranacak);

                                }
                            }

                            if (temp2.listeSonraki[j].listeSonraki != null) {
                                for (int k = 0; k < temp2.listeSonraki[j].listeSonraki.length; k++) {
                                    if (temp2.listeSonraki[j].listeSonraki[k].ziyaret == false) {
                                        temp3 = temp2.listeSonraki[j].listeSonraki[k];
                                        break;
                                    }
                                }
                            }

                            while (!temp3.equals(temp2)) {

                                /// burasi
                                if (temp3.ziyaret == true) {
                                    if (temp3.listeSonraki != null) {

                                        for (int k = 0; k < temp3.listeSonraki.length; k++) {
                                            if (temp3.listeSonraki[k].ziyaret == false) {
                                                temp3 = temp3.listeSonraki[k];
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (temp3.ziyaret == false) {
                                    //System.out.println(temp3.kategoriAdi);
                                    Kategori temp4 = null;

                                    while (temp3 != null) {

                                        if (temp3.ziyaret == false) {
                                            //System.out.println(temp3.kategoriAdi + " derinlik : " + temp3.derinlik + " alt kategori sayısı : " + temp3.altKategoriSayisi);

                                            if (temp3.kullanici != null) {

                                                Kullanici kontrol = kullaniciKontrolEt(temp3.kullanici, aranacak);
                                                if (kontrol != null) {
                                                    kategoridenKullaniciSil(temp3, aranacak);
                                                }
                                            }

                                            temp3.ziyaret = true;
                                            adresler.add(temp3);

                                        }
                                        temp4 = temp3;
                                        if (temp3.listeSonraki != null) {
                                            temp3 = temp3.listeSonraki[0];
                                        } else {
                                            break;
                                        }
                                    }

                                    Kategori[] kontrol = temp3.onceki.listeSonraki;
                                    boolean bulunduMu = false;

                                    for (int k = 0; k < kontrol.length; k++) {
                                        if (kontrol[k] != null && kontrol[k].ziyaret == false) {
                                            temp3 = kontrol[k];
                                            bulunduMu = true;
                                            break;
                                        }
                                    }

                                    if (bulunduMu) {
                                        continue;
                                    } else {
                                        temp3 = temp3.onceki;
                                    }

                                } else {
                                    temp3 = temp3.onceki;
                                }
                            }
                        }
                    }
                }
            }

        }
        return null;
    }

    public void LLekle(Kullanici kullanici, String[] eklenecekler) {
        if (eklenecekler != null) {
            Dugum dugum = new Dugum(eklenecekler[0], eklenecekler[1], eklenecekler[2], eklenecekler[3], eklenecekler[4]);

            if (kullanici.kullaniciRezervasyonlari == null) {
                kullanici.kullaniciRezervasyonlari = dugum;
            } else {
                Dugum temp = kullanici.kullaniciRezervasyonlari;

                while (temp.sonraki != null) {
                    temp = temp.sonraki;
                }

                temp.sonraki = dugum;
            }
        }

    }

    public void rezervasyonSayilariniGuncelle(Kategori k, Kullanici root) {
        Kullanici current, pre;

        int rezervasyonSayisi = 0;

        if (root == null) {
            return;
        }

        current = root;
        while (current != null) {

            if (current.sol == null) {

                Dugum temp = current.kullaniciRezervasyonlari;

                while (temp != null) {
                    rezervasyonSayisi++;
                    temp = temp.sonraki;
                }

                current = current.sag;
            } else {

                pre = current.sol;
                while (pre.sag != null && pre.sag != current) {
                    pre = pre.sag;
                }

                if (pre.sag == null) {
                    pre.sag = current;
                    current = current.sol;
                } else {
                    pre.sag = null;

                    Dugum temp = current.kullaniciRezervasyonlari;

                    while (temp != null) {
                        rezervasyonSayisi++;
                        temp = temp.sonraki;
                    }

                    current = current.sag;
                }

            }

        }

        k.rezervasyonSayisi = rezervasyonSayisi;

    }

    public void diziyeAt(Kullanici root, ArrayList<Kullanici> list) {
        Kullanici current, pre;

        if (root == null) {
            return;
        }

        current = root;
        while (current != null) {

            if (current.sol == null) {
                list.add(current);
                current = current.sag;
            } else {

                pre = current.sol;
                while (pre.sag != null && pre.sag != current) {
                    pre = pre.sag;
                }

                if (pre.sag == null) {
                    pre.sag = current;
                    current = current.sol;
                } else {
                    pre.sag = null;
                    list.add(current);
                    current = current.sag;
                }

            }

        }

    }

}
