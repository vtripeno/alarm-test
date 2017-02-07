package com.treinamento.victortripeno.testalarm.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.treinamento.victortripeno.testalarm.receiver.AlarmBroadcast;

/**
 * Created by victor.tripeno on 07/02/2017.
 */
public class ServicoAlarme extends IntentService {

    public ServicoAlarme() {
        super("ServicoAlarme");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.e("ServicoAlarme", "ServicoAlarme");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(ServicoAlarme.this, "Entrou no serviço", Toast.LENGTH_SHORT).show();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent myIntent = new Intent(ServicoAlarme.this, AlarmBroadcast.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ServicoAlarme.this, 0, myIntent, 0);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                1*60*1000,
                pendingIntent);
    }

}
