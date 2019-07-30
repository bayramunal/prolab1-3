/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

/**
 *
 * @author ev
 *//*
public class HibritRobot extends Robot implements GezginRobotlar, GezmeyenRobotlar {

    private int sabitlenmeSuresi;
    private int yukuKolaGecirmeSuresi;
    private int gezinmeHizi;

    @Override
    public double engeldenGecmeSuresiniBul() {

        double sure = 0;

        if (this.robotTipi.equals("SpiderRobot")) {
            sure = -1;
        } else if (this.robotTipi.equals("TekerlekliRobot")) {
            sure = this.motorSayisi * 0.5;
        } else if (this.robotTipi.equals("PaletliRobot")) {
            sure = this.motorSayisi * 3;
        }

        return sure;
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
*/