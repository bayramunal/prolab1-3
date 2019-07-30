/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r;

import r.Dugum;

/**
 *
 * @author hacib
 */
public class Kullanici {

    int rezervasyonSayisi;
    String kullaniciId, kategoriIsmi;

    Dugum kullaniciRezervasyonlari;

    Kullanici sol, sag;

    Kullanici() {
    }

    public Kullanici(String kullaniciId, String kategoriIsmi) {
        this.kullaniciId = kullaniciId;
        this.kategoriIsmi = kategoriIsmi;
    }

    public Kullanici(String kullaniciId, String kategoriIsmi, int rezervasyonSayisi) {
        this.kullaniciId = kullaniciId;
        this.kategoriIsmi = kategoriIsmi;
        this.rezervasyonSayisi = rezervasyonSayisi;
    }
}
