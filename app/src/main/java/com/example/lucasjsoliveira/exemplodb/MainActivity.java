package com.example.lucasjsoliveira.exemplodb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private boolean anyChecked = false;
    private boolean isInternal = false;

    public static final String KEY_TEXTO = "TEXTO";
    public static final String KEY_NUMERO = "NUMERO";
    public static final String IS_INTERNAL = "isInternal";
    public static final String FILE_NAME = "com.example.lucasjsoliveira.exemplodb";
    public static final String NUMERO_FILE_NAME = "arquivo_numero";
    public static final String TEXTO_FILE_NAME = "arquivo_texto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void saveSharedPrefs() {
        EditText txtTexto = (EditText) findViewById(R.id.txtTexto);
        EditText txtNumero = (EditText) findViewById(R.id.txtNumero);

        String str = txtTexto.getText().toString();
        int num = Integer.parseInt(txtNumero.getText().toString());

        SharedPreferences sharedPref = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_TEXTO, str);
        editor.putInt(KEY_NUMERO, num);
        boolean result = editor.commit();
        String msg = result ? "Salvo com sucesso" : "Erro ao salvar";
        showToast(msg);
    }

    private void saveInternalStorage() {
        EditText txtTexto = (EditText) findViewById(R.id.txtTexto);
        EditText txtNumero = (EditText) findViewById(R.id.txtNumero);

        String str = txtTexto.getText().toString();
        int num = Integer.parseInt(txtNumero.getText().toString());
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(TEXTO_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            outputStream = openFileOutput(NUMERO_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(String.valueOf(num).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openExibir() {
        if (!validateChecked())
            return;
        Intent intent = new Intent(this, ExibirActivity.class);
        intent.putExtra(IS_INTERNAL, isInternal);
        startActivity(intent);
    }

    private boolean validateChecked() {
        if (anyChecked)
            return true;

        CharSequence text = "Tipo de armazenamento não selecionado";

        showToast(text);
        return false;
    }

    private void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onRadioSelect(View view) {
        anyChecked = true;
        isInternal = view.getId() == R.id.radio_internal;
    }

    public void salvarClick(View view) {
        if (!validateChecked())
            return;

        if (!isInternal)
            saveSharedPrefs();
        else
            saveInternalStorage();
    }

    public void exibirClick(View view) {
        if (!validateChecked())
            return;

        openExibir();
    }
}
