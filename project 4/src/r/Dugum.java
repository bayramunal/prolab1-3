/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r;

/**
 *
 * @author hacib
 */
public class Dugum {

    String yerId;
    String rezervasyonNumarasi, enlem, boylam, sehir, zaman;

    Dugum sonraki;

    public Dugum(String yerId, String zaman, String enlem, String boylam, String sehir) {
        this.yerId = yerId;
        this.zaman = zaman;
        this.enlem = enlem;
        this.boylam = boylam;
        this.sehir = sehir;
    }

}
