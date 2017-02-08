package com.treinamento.victortripeno.testalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.treinamento.victortripeno.testalarm.adapter.AlarmAdapter;
import com.treinamento.victortripeno.testalarm.dao.AlarmDAO;
import com.treinamento.victortripeno.testalarm.modelo.Alarme;
import com.treinamento.victortripeno.testalarm.receiver.AlarmReceiver;
import com.treinamento.victortripeno.testalarm.service.ServicoAlarme;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {
    private List<Alarme> alarmes;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AlarmActivity inst;
    private TextView alarmTextView;
    private Button btnAdicionar;
    private ListView listaHorarios;
    private Button btnMensagem;
    private Button btnCamera;
    private Button btnDesenho;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Intent msgIntent;

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        inst = this;
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Alarm Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.treinamento.victortripeno.testalarm/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        btnAdicionar = (Button) findViewById(R.id.novo_horario);
        listaHorarios = (ListView) findViewById(R.id.lista_horario);
        btnMensagem = (Button) findViewById(R.id.btn_mensagem);
        btnCamera = (Button) findViewById(R.id.btn_camera);
        btnDesenho = (Button) findViewById(R.id.btn_desenho);


        msgIntent = new Intent(this, ServicoAlarme.class);
        msgIntent.putExtra(ServicoAlarme.ALARM_SERVICE, "TESTE");

        startService(msgIntent);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AlarmDAO alarmeDao = new AlarmDAO(AlarmActivity.this);
                    Alarme alarme = new Alarme();
                    Calendar calendario = Calendar.getInstance();
                    int hora = alarmTimePicker.getCurrentHour();
                    int minuto = alarmTimePicker.getCurrentMinute();
                    calendario.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hora, minuto);
                    alarme.setTempo(calendario);
                    alarmeDao.inserir(alarme);
                    carregarHorarios();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(AlarmActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setColor(ContextCompat.getColor(AlarmActivity.this.getApplicationContext(), R.color.colorPrimaryDark))
                                .setContentTitle("My notification")
                                .setContentText("Hello World!")
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setDefaults(Notification.DEFAULT_ALL);
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                int mNotificationId = 001; // ID precisa ser alterado para que apareça mais de uma notificação
                mNotifyMgr.notify(mNotificationId, mBuilder.build());

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this, CameraActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AlarmActivity.this.startActivity(intent);
            }
        });

        btnDesenho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this, DesenhoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AlarmActivity.this.startActivity(intent);
            }
        });


    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("AlarmActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("AlarmActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Alarm Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.treinamento.victortripeno.testalarm/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            carregarHorarios();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void carregarHorarios() throws ParseException {
        AlarmDAO alarmeDao = new AlarmDAO(this);
        alarmes = alarmeDao.buscarAlarmes();
        alarmeDao.close();
        AlarmAdapter alarmAdapter = new AlarmAdapter(alarmes, AlarmActivity.this);
        listaHorarios.setAdapter(alarmAdapter);
    }

}
