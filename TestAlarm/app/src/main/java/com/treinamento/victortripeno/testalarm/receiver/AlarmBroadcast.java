package com.treinamento.victortripeno.testalarm.receiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.treinamento.victortripeno.testalarm.dao.AlarmDAO;
import com.treinamento.victortripeno.testalarm.modelo.Alarme;

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
    int contador = 0;

    public static final String PREFS_NAME = "com.treinamento.victortripeno.testalarm.receiver.AlarmBroadcast";
    public static final String KEY_COUNT = "notificationCount";

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences values = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        contador = values.getInt(KEY_COUNT, 0);  //Sets to zero if not in prefs yet

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

        /* Regra:
            Se a quantidade de alarmes for maior que zero após a consulta na base de dados, então
            o Broadcast ficará ouvindo até que o alarme seja disparado, senão se a lista estiver vazia e
            o contador de chamadas do Broadcast for maior que 10 então o Broadcast deve ser encerrado
            até nova instancia, senão deve ser apenas acrescentado mais 1 para o contador geral de
            Broadcasts
         */
        if (alarmes.size() > 0) {
            for (Alarme alarme : alarmes) {
                if (alarme.getTempo().getTime().getHours() == calendar.getTime().getHours() &&
                        alarme.getTempo().getTime().getMinutes() == calendar.getTime().getMinutes()) {
                    alm = alarme;
                    flgAlm = true;
                    contador = 0;

                    break;
                }
            }

            if(flgAlm) {
                Toast.makeText(context, "TESTE " + alm.toString(), Toast.LENGTH_LONG).show();
                Log.d("TESTE Intent", "TESTE Intent Broadcast "  + alm.toString());
            }
        } else if(contador >= 10){
            Toast.makeText(context, "PAROU SERVIÇO", Toast.LENGTH_LONG).show();
            Log.d("TESTE Intent", "PAROU SERVIÇO");

            // Código para dar stop no Broadcast
            ComponentName receiver = new ComponentName(context, this.getClass());
            PackageManager pm = context.getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            Toast.makeText(context, "Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
            Log.d("Disabled broadcst receiver", "Disabled broadcst receiver");
            contador = 0;
        } else {
            contador++;
        }
        Toast.makeText(context, "CONTADOR: " + contador , Toast.LENGTH_LONG).show();
        Log.d("TESTE Intent", "CONTADOR: " + contador);
        //Do your magic work here

        //Write the value back to storage for later use
        SharedPreferences.Editor editor = values.edit();
        editor.putInt(KEY_COUNT,contador);
        editor.commit();

    }

}