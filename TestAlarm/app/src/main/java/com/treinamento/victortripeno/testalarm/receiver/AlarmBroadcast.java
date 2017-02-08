package com.treinamento.victortripeno.testalarm.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.treinamento.victortripeno.testalarm.dao.AlarmDAO;
import com.treinamento.victortripeno.testalarm.modelo.Alarme;
import com.treinamento.victortripeno.testalarm.service.ServicoAlarme;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        List<Alarme> alarmes = new ArrayList<Alarme>();

        try {
            alarmes = dao.buscarAlarmes();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (alarmes.size() > 0) {
            for (Alarme alarme : alarmes) {
                if (alarme.getTempo().getTime().getHours() == calendar.getTime().getHours() &&
                        alarme.getTempo().getTime().getMinutes() == calendar.getTime().getMinutes()) {
                    alm = alarme;
                    flgAlm = true;
                    break;
                }
            }

            if(flgAlm) {
                Toast.makeText(context, "TESTE " + alm.toString(), Toast.LENGTH_LONG).show();
                Log.d("TESTE Intent", "TESTE Intent Broadcast "  + alm.toString());
            }
        } else {
            Intent serviceIntent = new Intent(context, ServicoAlarme.class);
            context.stopService(serviceIntent);
            Toast.makeText(context, "PAROU SERVIÇO", Toast.LENGTH_LONG).show();
            Log.d("TESTE Intent", "PAROU SERVIÇO");
        }



    }

}