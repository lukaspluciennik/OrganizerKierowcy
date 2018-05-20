package ukaszpuciennik.organiezerkierowcy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class Oil_layout extends AppCompatActivity {
    static final int DIALOG_ID = 0;
    ArrayList<String> historias = new ArrayList<String>();
    ArrayList<String> historiasWymiany = new ArrayList<String>();
    TextView rodzaj;
    TextView dataWymiany;
    TextView przebiegWymiany;
    TextView dataSprawdzenia;
    TextView przebiegSprawdzenia;
    TextView dolewka;
    OilClass oil = new OilClass();
    int year_x, month_x, day_x;
    Spinner s;
    private String[] arraySpinner;
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            String dateString = day_x + "-" + month_x + "-" + year_x;
            TextView data_dzis = findViewById(R.id.data_dzisiejsza_s);
            TextView data_dzis2 = findViewById(R.id.data_dzisiejsza_w);
            data_dzis.setText(dateString);
            data_dzis2.setText(dateString);
            oil.setData_sprawdzenia(dateString);
            oil.setData_wymiany(dateString);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spinnerdolewka();

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
//wczytanie ostatnich danych

        //historias = getSavedArrayList();

        dataSprawdzenia = findViewById(R.id.data_sprawdzenia);
        przebiegSprawdzenia = findViewById(R.id.przebieg_sprawdzenia);
        dataWymiany = findViewById(R.id.data_wymiany);
        przebiegWymiany = findViewById(R.id.przebieg_wymiany);
        rodzaj = findViewById(R.id.rodzaj_oleju);
        dolewka = findViewById(R.id.dolewka_oleju);


//ustawianie dzisiejszej daty i przypisanie
        aktualizujdate();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commonmenus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //przycisk aktualizuj

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mnu_historia) {

            //startActivity(new Intent(Oil_layout.this ,HistoriaLayout.class));
            wyswietl();
        }

        if (id == R.id.mnu_historia_wymiany) {

            //startActivity(new Intent(Oil_layout.this ,HistoriaLayout.class));
            wyswietlwymiane();
        } else if (id == R.id.mnu_data) {

            showDialog(DIALOG_ID);

        }
        return super.onOptionsItemSelected(item);
    }

    public void Spr_Aktualizuj(View view) {

        pobierz_przebieg();
        aktualizuj_spr();
        dolewka();
        historias = null;
        historias = getSavedArrayList();
        historias.add("Data: " + oil.getData_sprawdzenia() + " Przebieg: " + oil.getPrzegieg_sprawdzenia());


        saveArrayList(historias);


    }

    public void dolewka() {

        dolewka.setText(s.getSelectedItem().toString());
        oil.setDolewka(dolewka.toString());
    }

    public void wyswietl() {
        historias = getSavedArrayList();
        Intent intent = new Intent(Oil_layout.this, HistoriaLayout.class);
        intent.putExtra("przenies", historias);
        startActivity(intent);
    }

    public void aktualizujdate() {
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH) + 1;
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        String dateString = day_x + "-" + month_x + "-" + year_x;
        TextView data_dzis = findViewById(R.id.data_dzisiejsza_s);
        TextView data_dzis2 = findViewById(R.id.data_dzisiejsza_w);
        data_dzis.setText(dateString);
        data_dzis2.setText(dateString);
        oil.setData_sprawdzenia(dateString);
        oil.setData_wymiany(dateString);

    }

    public void aktualizuj_spr() {

        dataSprawdzenia = findViewById(R.id.data_sprawdzenia);
        dataSprawdzenia.setText(oil.getData_sprawdzenia());
        przebiegSprawdzenia = findViewById(R.id.przebieg_sprawdzenia);
        przebiegSprawdzenia.setText(oil.getPrzegieg_sprawdzenia());


    }

    public void pobierz_przebieg() {
        Double roznica;

        EditText Przebieg_sprawdzenia = findViewById(R.id.wprowadz_przebieg_sprawdzenia);
        EditText Przebieg_wymiany = findViewById(R.id.wprowadz_przebieg_wymiany);


        //przebieg set
        oil.setPrzegieg_sprawdzenia(Przebieg_sprawdzenia.getText().toString());
        oil.setPrzebieg_wymiany(Przebieg_wymiany.getText().toString());

        EditText Rodzaj = findViewById(R.id.wprowadz_rodzaj_oleju);
        oil.setRodzaj(Rodzaj.getText().toString());
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String data_wczytana = sharedPreferences.getString("data_spr", "");
        String przebieg_wczytany = sharedPreferences.getString("przebieg_spr", "");
        String data_wczytana_wym = sharedPreferences.getString("data_wym", "");
        String przebieg_wczytany_wym = sharedPreferences.getString("przebieg_wczy", "");
        String rodzaj_wczytany = sharedPreferences.getString("rodzaj_wczy", "");
        String dolewkaString = sharedPreferences.getString("dolewka", "");
        dataSprawdzenia.setText(data_wczytana);
        dataWymiany.setText(data_wczytana_wym);
        przebiegWymiany.setText(przebieg_wczytany_wym);
        przebiegSprawdzenia.setText(przebieg_wczytany);
        rodzaj.setText(rodzaj_wczytany);
        dolewka.setText(dolewkaString);


    }


    //przycisk dodaj

    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data_spr", dataSprawdzenia.getText().toString());
        editor.putString("przebieg_spr", przebiegSprawdzenia.getText().toString());
        editor.putString("data_wym", dataWymiany.getText().toString());
        editor.putString("przebieg_wczy", przebiegWymiany.getText().toString());
        editor.putString("rodzaj_wczy", rodzaj.getText().toString());
        editor.putString("dolewka", dolewka.getText().toString());
        editor.commit();


    }

    public void dodaj_wym(View view) {

        pobierz_przebieg();
        dataWymiany = findViewById(R.id.data_wymiany);
        dataWymiany.setText(oil.getData_wymiany());
        przebiegWymiany = findViewById(R.id.przebieg_wymiany);
        przebiegWymiany.setText(oil.getPrzebieg_wymiany() + " km");
        rodzaj = findViewById(R.id.rodzaj_oleju);
        rodzaj.setText("Rodzaj: " + oil.getRodzaj());
        historiasWymiany = getSavedArrayList2();
        historiasWymiany.add("Data: " + oil.getData_wymiany() + "\nPrzebieg: " + oil.getPrzebieg_wymiany() + " Rodzaj: " + oil.getRodzaj());
        saveArrayList2(historiasWymiany);


    }

    private ArrayList<String> getSavedArrayList() {
        ArrayList<String> savedArrayList = null;

        try {
            FileInputStream inputStream = openFileInput("zapis_historii");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedArrayList = (ArrayList<String>) in.readObject();
            in.close();
            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedArrayList;
    }

    private void saveArrayList(ArrayList<String> arrayList) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("zapis_historii", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(arrayList);
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getSavedArrayList2() {
        ArrayList<String> savedArrayList = null;

        try {
            FileInputStream inputStream = openFileInput("zapis_historii2");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedArrayList = (ArrayList<String>) in.readObject();
            in.close();
            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedArrayList;
    }

    private void saveArrayList2(ArrayList<String> arrayList) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("zapis_historii2", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(arrayList);
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x - 1, day_x);
        return null;
    }

    public void spinnerdolewka() {
        this.arraySpinner = new String[]{
                "Brak", "25 ml", "50 ml", "75 ml", "100 ml", "125 ml", "150 ml", "175 ml", "200 ml", "250 ml", "300 ml", "350 ml", "400 ml", "500 ml", "600 ml", "700 ml", "800 ml", "900 ml", "1000 ml",
        };
        s = findViewById(R.id.spinner_dolewka);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        s.setAdapter(adapter);

    }

    public void wyswietlwymiane() {
        historiasWymiany = getSavedArrayList2();
        Intent intent2 = new Intent(Oil_layout.this, HistoriaWymian.class);
        intent2.putExtra("przenies2", historiasWymiany);
        startActivity(intent2);
    }
}








