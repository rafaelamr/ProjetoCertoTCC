package com.example.testefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testefirebase.Modelo.Familia;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public EditText alias_id, aliasnome, aliascpf, aliasendereco;
    public Button aliasalvar, aliasapagar;
    public ListView aliaslista;

    private List<Familia> familias=new ArrayList<Familia>();
    private ArrayAdapter<Familia> familiaArrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Familia familiaSelecionada, familia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alias_id=(EditText) findViewById(R.id.editId);
        aliasnome=(EditText) findViewById(R.id.editNome);
        aliascpf=(EditText) findViewById(R.id.editCPF);
        aliasendereco=(EditText) findViewById(R.id.editEndereco);
        aliasalvar=(Button) findViewById(R.id.buttonSalvar);
        aliasapagar=(Button) findViewById(R.id.buttonApagar);
        aliaslista=(ListView) findViewById(R.id.listviewfamilia);
        familia=new Familia();
        aliaslista.setOnItemClickListener(this);

        inicializarFirebase();
        eventoDatabase();


        aliasalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                familia = new Familia();
                familia.set_id(Integer.parseInt(alias_id.getText().toString()));
                familia.setNome(aliasnome.getText().toString());
                familia.setCpf(aliascpf.getText().toString());
                familia.setEndereco(aliasendereco.getText().toString());
                databaseReference.child("Familia").child(String.valueOf(familia.get_id())).setValue(familia);
                Toast.makeText(getBaseContext(), "Dados Gravados com Sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        });

        aliasapagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Familia familia = new Familia();
                familia.set_id(familiaSelecionada.get_id());
                databaseReference.child("Familia").child(familia.get_id().toString()).removeValue();
                Toast.makeText(getBaseContext(), "Dados Excluídos com Sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
            }

        });
    }

    private void eventoDatabase() {
        databaseReference.child("Familia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                familias.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Familia familia=objSnapshot.getValue(Familia.class);
                    familias.add(familia);
                }
                familiaArrayAdapter= new ArrayAdapter<Familia>(MainActivity.this, android.R.layout.simple_list_item_1, familias);
                aliaslista.setAdapter(familiaArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        familiaSelecionada=(Familia) parent.getItemAtPosition(position);
        alias_id.setText(familiaSelecionada.get_id().toString());
        aliasnome.setText(familiaSelecionada.getNome().toString());
        aliascpf.setText(familiaSelecionada.getCpf().toString());
        aliasendereco.setText(familiaSelecionada.getEndereco().toString());
    }

    private void limparCampos() {
        alias_id.setText("");
        aliasnome.setText("");
        aliascpf.setText("");
        aliasendereco.setText("");
    }


}