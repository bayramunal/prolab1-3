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
public class TekerlekliRobot extends Gezgin implements Tekerlekli{
    private int tekerlekSayisi;
    private int gezinmeHizi;
    
    public TekerlekliRobot()
    {
        this.robotTipi = "Tekerlekli robot";
    }

    public void setTekerlekSayisi(int tekerlekSayisi)
    {
        this.tekerlekSayisi = tekerlekSayisi;
    }
    
    @Override
    public double engeldenGecmeSuresiniBul() {
        return this.motorSayisi * 0.5;
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
