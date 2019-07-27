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
public class SpiderRobot extends Gezgin implements Spider{
    private int bacakSayisi;
    private int gezinmeHizi;
    
    public SpiderRobot()
    {
        this.robotTipi = "Spider robot";
    }
    
    public void setBacakSayisi(int bacakSayisi)
    {
        this.bacakSayisi = bacakSayisi;
    }
    
    @Override
    public double engeldenGecmeSuresiniBul() {
        System.out.println("Spider robotlar engelden geçemezler");
        return -1;
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
