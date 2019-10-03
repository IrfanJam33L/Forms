package com.android.forms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class InputInfo extends Activity{

    public static final String E_FNAME="com.android.forms.E_FNAME";
    public static final String E_LNAME="com.android.forms.E_LNAME";
    public static final String E_MAIL="com.android.forms.E_MAIL";
    public static final String E_PNUM="com.android.forms.E_PNUM";
    public static final String E_ADD="com.android.forms.E_ADD";
    private EditText fName, lName, email, p_num, address;
    private Button BtnSubmit;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_info);


        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        p_num = findViewById(R.id.pnum);

        BtnSubmit = findViewById(R.id.Submit);

        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckData_Insert();

            }
        });
    }

    public void CheckData_Insert() {

        boolean validfName = true;
        boolean validlName = true;
        boolean validEmail = true;
        boolean validP_num = true;
        boolean validAddress = true;

        if (isEmpty(fName)) {
            fName.setError("First Name Required!!");
            validfName = false;
        }

        if (isEmpty(lName)){
            lName.setError("Last Name Required!");
            validlName = false;
        }
        if (isEmail(email) == false) {
            email.setError("Enter Valid Email!!");
            validEmail = false;
        }
        if (p_num.length() != 10) {
            p_num.setError("Phone no. Invalid!");
            validP_num = false;
        }
        if (isEmpty(address)) {
            address.setError("You Cannot Leave address Empty");
            validAddress = false;
        }
        if (validfName == true && validlName == true && validEmail == true && validP_num == true && validAddress == true) {


            final Context context=this;
            String FNAME=fName.getText().toString();
            String LNAME=lName.getText().toString();
            String EMAIL=email.getText().toString();
            String PNUM=p_num.getText().toString();
            String ADDRESS=address.getText().toString();


            Intent i = new Intent(context, Confirmation.class);
            i.putExtra(E_FNAME, FNAME);
            i.putExtra(E_LNAME, LNAME);
            i.putExtra(E_MAIL, EMAIL);
            i.putExtra(E_PNUM, PNUM);
            i.putExtra(E_ADD, ADDRESS);
            startActivity(i);

        }

    }
    private boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return (TextUtils.isEmpty(str));
        }

}