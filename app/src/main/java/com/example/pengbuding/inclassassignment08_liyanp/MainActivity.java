package com.example.pengbuding.inclassassignment08_liyanp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText keyEdit;
    private EditText valueEdit;
    private Button writeButton;
    private Button readButton;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyEdit = (EditText) findViewById(R.id.key_edit_text);
        valueEdit = (EditText) findViewById(R.id.value_edit_text);
        writeButton = (Button) findViewById(R.id.write_button);
        readButton = (Button) findViewById(R.id.read_button);
        database = FirebaseDatabase.getInstance();

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = database.getReference(keyEdit.getText().toString());
                myRef.setValue(valueEdit.getText().toString());
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = database.getReference(keyEdit.getText().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String value = dataSnapshot.getValue(String.class);
                            valueEdit.setText(value);
                        } else {
                            valueEdit.setText("");
                            Toast.makeText(MainActivity.this, "Failed to read the value.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Failed to read the value.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}