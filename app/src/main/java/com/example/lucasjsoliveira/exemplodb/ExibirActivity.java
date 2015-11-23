package com.example.lucasjsoliveira.exemplodb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExibirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir);

        Intent intent = getIntent();

        boolean isInternal = intent.getBooleanExtra(MainActivity.IS_INTERNAL, true);

        if (!isInternal)
            fillFromShared();
        else
            fillFromInternal();
    }

    private void fillFromShared() {
        TextView txtTexto = (TextView) findViewById(R.id.txtTexto);
        TextView txtNumero = (TextView) findViewById(R.id.txtNumero);

        SharedPreferences sharedPref = this.getSharedPreferences(MainActivity.FILE_NAME, Context.MODE_PRIVATE);
        int numero = sharedPref.getInt(MainActivity.KEY_NUMERO, 0);
        String texto = sharedPref.getString(MainActivity.KEY_TEXTO, "");
        txtTexto.setText(String.valueOf(texto));
        txtNumero.setText(String.valueOf(numero));
    }

    private void fillFromInternal() {
        TextView txtTexto = (TextView) findViewById(R.id.txtTexto);
        TextView txtNumero = (TextView) findViewById(R.id.txtNumero);

        File file = new File(getApplicationContext().getFilesDir(), MainActivity.TEXTO_FILE_NAME);
        StringBuilder strBuilder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                strBuilder.append(line);
                strBuilder.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            showToast("Erro ao ler dados de texto");
        }

        txtTexto.setText(strBuilder.toString());

        file = new File(getApplicationContext().getFilesDir(), MainActivity.NUMERO_FILE_NAME);
        strBuilder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                strBuilder.append(line);
                strBuilder.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            showToast("Erro ao ler dados de número");
        }

        txtNumero.setText(strBuilder.toString());
    }


    private void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
