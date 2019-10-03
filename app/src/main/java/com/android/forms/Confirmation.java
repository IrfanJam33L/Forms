package com.android.forms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Confirmation extends Activity {

    DatabaseHelper myDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_confirm);
        myDb = new DatabaseHelper( this);

        Intent intent = getIntent();

        final Context conetext = this;

        final String fName = intent.getStringExtra(InputInfo.E_FNAME);
        final String lName = intent.getStringExtra(InputInfo.E_LNAME);
        final String email = intent.getStringExtra(InputInfo.E_MAIL);
        final String p_num = intent.getStringExtra(InputInfo.E_PNUM);
        final String address = intent.getStringExtra(InputInfo.E_ADD);

        TextView F_NAME= findViewById(R.id.Fname);
        TextView L_NAME= findViewById(R.id.Lname);
        TextView EMAIL= findViewById(R.id.Email);
        TextView PNUM= findViewById(R.id.Phone);
        TextView ADDRESS= findViewById(R.id.Address);

        F_NAME.setText(fName);
        L_NAME.setText(lName);
        EMAIL.setText(email);
        PNUM.setText(p_num);
        ADDRESS.setText(address);

        Button confirm = findViewById(R.id.cnfrm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted= myDb.insertData(fName, lName, email, p_num,address);
                if(isInserted == true)
                    Toast.makeText(Confirmation.this,"Data Inserted!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Confirmation.this,"Data not Inserted/Already Exist!",Toast.LENGTH_LONG).show();
                Intent i= new Intent(conetext, MainActivity.class);
                startActivity(i);
            }

        });
    }
}
