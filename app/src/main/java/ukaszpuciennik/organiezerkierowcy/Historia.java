package ukaszpuciennik.organiezerkierowcy;

/**
 * Created by Łukasz on 03.11.2017.
 */

public class Historia {

    private String data_sprawdzenia;
    private String przebieg_sprawdzenia;

    public Historia(String Data_spawdzenia, String Przebieg_sprawdzenia){
        super();
        this.data_sprawdzenia=Data_spawdzenia;
        this.przebieg_sprawdzenia=Przebieg_sprawdzenia;
    }


    public String getData_sprawdzenia(){return data_sprawdzenia;}
    public String getPrzebieg_sprawdzenia(){return przebieg_sprawdzenia;}

}
