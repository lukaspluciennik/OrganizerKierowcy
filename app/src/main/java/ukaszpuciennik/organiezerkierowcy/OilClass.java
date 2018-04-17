package ukaszpuciennik.organiezerkierowcy;

import android.widget.TextView;

/**
 * Created by Łukasz on 01.11.2017.
 */

public class OilClass {

    private String data_sprawdzenia;
    private String data_wymiany;
    private String przegieg_sprawdzenia;
    private String przebieg_wymiany;
    private String rodzaj;
    private Double zuzycie;
    private String dolewka;




    public String getData_sprawdzenia() {
        return data_sprawdzenia;
    }

    public void setData_sprawdzenia(String data_sprawdzenia) {
        this.data_sprawdzenia = data_sprawdzenia;
    }

    public String getData_wymiany() {
        return data_wymiany;
    }

    public void setData_wymiany(String data_wymiany) {
        this.data_wymiany = data_wymiany;
    }

    public void setPrzegieg_sprawdzenia(String przegieg_sprawdzenia) {
        this.przegieg_sprawdzenia = przegieg_sprawdzenia;
    }

    public String getPrzegieg_sprawdzenia() {
        return przegieg_sprawdzenia+" km";
    }

    public String getPrzebieg_wymiany() {
        return przebieg_wymiany;
    }

    public void setPrzebieg_wymiany(String przebieg_wymiany) {
        this.przebieg_wymiany = przebieg_wymiany;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public Double getZuzycie() {

        return zuzycie;
    }

    public void setZuzycie(Double zuzycie) {
        this.zuzycie = zuzycie;
    }


    public String getDolewka() {
        return dolewka;
    }

    public void setDolewka(String dolewka) {
        this.dolewka = dolewka;
    }
}
