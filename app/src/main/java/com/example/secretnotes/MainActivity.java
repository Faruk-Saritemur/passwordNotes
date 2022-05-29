package com.example.secretnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name, password;
    Button save, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.txtName);
        password = findViewById(R.id.txtPassword);
        save = findViewById(R.id.btnSave);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                String passwordTxt = password.getText().toString();

                Boolean checkSavedData = DB.insertpassworddata(nameTxt, passwordTxt);
                if(checkSavedData) {
                    Toast.makeText(MainActivity.this, "Şifre Kaydedildi", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Şifre Kaydedilemedi -- Hata", Toast.LENGTH_SHORT).show();
                }
                name.setText(null);
                password.setText(null);

                System.out.println("tested");

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTxt = name.getText().toString();
                String passwordTxt = password.getText().toString();

                Boolean checkUpdatedData = DB.updateuserdata(nameTxt, passwordTxt);
                Toast.makeText(MainActivity.this,"Şifre Güncellendi", Toast.LENGTH_SHORT).show();

                name.setText(null);
                password.setText(null);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                String passwordTxt = password.getText().toString();

                Boolean checkDeletedData = DB.deleteData(nameTxt);
                Toast.makeText(MainActivity.this,"Şifre Başarıyle silindi", Toast.LENGTH_SHORT).show();

                name.setText(null);
                password.setText(null);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getData();
                if(res.getCount() == 0) {
                    Toast.makeText(MainActivity.this,"Kayıtlı şifre yok !", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("Name :  "+res.getString(0)+"\n");
                    buffer.append("Password :  "+res.getString(1)+"\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Kayıtlı Şifreler"+"\n");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}