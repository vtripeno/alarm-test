package com.treinamento.victortripeno.testalarm.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.treinamento.victortripeno.testalarm.dao.AlarmDAO;
import com.treinamento.victortripeno.testalarm.modelo.Alarme;

/**
 * Created by victor.tripeno on 03/02/2017.
 */
public class AlarmBroadcast extends BroadcastReceiver {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    AlarmDAO dao;
    Alarme alm;

    @Override
    public void onReceive(Context context, Intent intent) {
        /*alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        alm = new Alarme();
        try {
            for (Alarme alarme : dao.buscarAlarmes()) {
                if(alarme.getTempo().getTime().getHours() == 3) {
                    alm = alarme;
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeInMillis = 2000;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alm.getTempo().getTimeInMillis(), timeInMillis, pendingIntent);
        alarmManager.notifyAll();*/
        Toast.makeText(context, "TESTE", Toast.LENGTH_SHORT).show();
        Log.d("TESTE Intent", "TESTE Intent Broadcast");
        /*AlarmBroadcast alarmBroadcast = new AlarmBroadcast();
        IntentFilter intentFilter = new IntentFilter(intent.ACTION_TIME_TICK);*/

    }

}