/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r;

import r.Kullanici;

/**
 *
 * @author hacib
 */
public class Kategori {

     Kullanici kullanici;
    
     int boyut;

     String kategoriAdi;
     int altKategoriSayisi;
     int kullaniciSayisi;
     int rezervasyonSayisi = 0;
     String kategoriYolu;
     int derinlik;

     Kategori[] listeSonraki;

     Kategori onceki;
     boolean ziyaret = false;
     boolean ekleZiyaret = false;



    public String getKategoriAdi() {
        return this.kategoriAdi;
    }

    public Kategori() {
    }

    public Kategori(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }
}
