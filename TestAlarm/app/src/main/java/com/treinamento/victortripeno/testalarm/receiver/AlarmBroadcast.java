package com.treinamento.victortripeno.testalarm.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.treinamento.victortripeno.testalarm.dao.AlarmDAO;
import com.treinamento.victortripeno.testalarm.modelo.Alarme;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by victor.tripeno on 03/02/2017.
 */
public class AlarmBroadcast extends BroadcastReceiver {
    PendingIntent pendingIntent;
    AlarmDAO dao;
    Alarme alm;

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        alm = new Alarme();
        dao = new AlarmDAO(context);
        boolean flgAlm = false;
        try {
            for (Alarme alarme : dao.buscarAlarmes()) {
                if(alarme.getTempo().getTime().getHours() == calendar.getTime().getHours() &&
                        alarme.getTempo().getTime().getMinutes() == calendar.getTime().getMinutes()) {
                    alm = alarme;
                    flgAlm = true;
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(flgAlm) {
            Toast.makeText(context, "TESTE " + alm.toString(), Toast.LENGTH_LONG).show();
            Log.d("TESTE Intent", "TESTE Intent Broadcast");
        }

    }

}