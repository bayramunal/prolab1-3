/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

/**
 *
 * @author ev
 */
public class PaletliRobot extends Gezgin implements Paletli {

    private int paletSayisi;
    private int gezinmeHizi;

    public PaletliRobot() {
        this.robotTipi = "Paletli robot";
    }

    public void setPaletSayisi(int paletSayisi) {
        this.paletSayisi = paletSayisi;
    }

    @Override
    public double engeldenGecmeSuresiniBul() {
        return this.motorSayisi * 3;
    }

    @Override
    public void setGezinmeHizi(int gezinmeHizi) {
        this.gezinmeHizi = gezinmeHizi;
    }

    @Override
    public int getGezinmeHizi() {
        return this.gezinmeHizi;
    }

}
