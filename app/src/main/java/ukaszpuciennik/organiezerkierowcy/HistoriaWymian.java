package ukaszpuciennik.organiezerkierowcy;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HistoriaWymian extends AppCompatActivity {
    ArrayList<String> historias_wymian;
    public int positionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_wymian);


        historias_wymian = (ArrayList<String>) getIntent().getSerializableExtra("przenies2");
        historiasListView();


    }


    private void historiasListView() {
        ListAdapter buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historias_wymian);
        ListView buckysListView = (ListView) findViewById(R.id.list2);
        buckysListView.setAdapter(buckysAdapter);
        buckysListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                positionList = position;
                AlertDialog diaBox = AskOption();
                diaBox.show();


                return false;
            }

        });
    }


    private void saveArrayList(ArrayList<String> arrayList) {
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


    private AlertDialog AskOption() {


        AlertDialog myDialogBox = new AlertDialog.Builder(this)
                .setTitle("Usuwanie")
                .setMessage("Czy chcesz usunąć?")
                .setIcon(R.drawable.ic_oil)


                .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //   Adapter.remove(historias.get(pozycja));
                        //   Adapter.notifyDataSetChanged();
                        //   saveArrayList(historias);
                        historias_wymian.remove(positionList);

                        saveArrayList(historias_wymian);
                        Toast.makeText(HistoriaWymian.this, "Usunięto", Toast.LENGTH_SHORT).show();
                        historiasListView();
                        dialog.dismiss();

                    }
                })

                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();


        return myDialogBox;


    }
}

