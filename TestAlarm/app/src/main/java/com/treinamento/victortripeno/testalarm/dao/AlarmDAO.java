package com.treinamento.victortripeno.testalarm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.treinamento.victortripeno.testalarm.modelo.Alarme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by victor.tripeno on 31/01/2017.
 */
public class AlarmDAO extends SQLiteOpenHelper{

    public AlarmDAO (Context context) {
        super(context, "Alarms", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Alarmes (id INTEGER PRIMARY KEY, horario TEXT);";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void inserir (Alarme alarme) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosAlarme(alarme);

        db.insert("Alarmes", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosAlarme(Alarme alarme) {
        ContentValues dados = new ContentValues();

        dados.put("id", alarme.getId());
        dados.put("horario", alarme.getTempo().getTime().toString());

        return dados;
    }

    public List<Alarme> buscarAlarmes() throws ParseException {
        String sql = "SELECT * FROM Alarmes;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Alarme> alarmes = new ArrayList<Alarme>();
        while(cursor.moveToNext()) {
            Alarme alarme = new Alarme();
            alarme.setId(cursor.getLong(cursor.getColumnIndex("id")));
            String dateTime = cursor.getString(cursor.getColumnIndex("horario"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(dateFormat.parse(dateTime));
            alarme.setTempo(calendario);

            alarmes.add(alarme);
        }
        cursor.close();
        return alarmes;
    }

    public void deletar(Alarme alarme) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {alarme.getId().toString()};
        db.delete("Alarmes", "id = ?", params);
    }
}
