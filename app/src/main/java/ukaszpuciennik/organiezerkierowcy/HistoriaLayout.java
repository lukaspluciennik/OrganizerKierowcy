package ukaszpuciennik.organiezerkierowcy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoriaLayout extends AppCompatActivity {


    ArrayList<String> historias;
    public int positionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_layout);


        historias = (ArrayList<String>) getIntent().getSerializableExtra("przenies");
        historiasListView();



    }






    private void historiasListView() {
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historias);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


            positionList=position;
            AlertDialog diaBox = AskOption();
            diaBox.show();


            return  false;
        }

    });
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


    private AlertDialog AskOption(){



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
                        historias.remove(positionList);

                        saveArrayList(historias);
                        Toast.makeText(HistoriaLayout.this, "Usunięto", Toast.LENGTH_SHORT).show();
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
