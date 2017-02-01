package com.treinamento.victortripeno.testalarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.treinamento.victortripeno.testalarm.R;
import com.treinamento.victortripeno.testalarm.dao.AlarmDAO;
import com.treinamento.victortripeno.testalarm.modelo.Alarme;

import java.util.Calendar;
import java.util.List;

/**
 * Created by victor.tripeno on 31/01/2017.
 */
public class AlarmAdapter extends BaseAdapter {
    private final List<Alarme> alarmes;
    private final Context context;

    public AlarmAdapter(List<Alarme> alarmes, Context context) {
        this.alarmes = alarmes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alarmes.size();
    }

    @Override
    public Object getItem(int i) {
        return alarmes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alarmes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Alarme alarme = alarmes.get(i);

        LayoutInflater inflater = LayoutInflater.from(context);
        View viewInlfada = view;
        if(viewInlfada == null) {
            viewInlfada = inflater.inflate(R.layout.list_item, viewGroup, false); // usar o viewGroup, mas não colocar dentro da lista ainda (param false)
        }
        Button btnDeletar = (Button) viewInlfada.findViewById(R.id.deletar_horario);
        btnDeletar.setTag(i); // setar tag para o botão para indicar onde ele está
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer index = (Integer) view.getTag(); // pegar o valor referente à linha para poder excluir quando clicado
                AlarmDAO alarmDAO = new AlarmDAO(context);
                alarmDAO.deletar(alarme);
                Toast.makeText(context, "Alarme " + alarme.getId() + " deletado", Toast.LENGTH_SHORT).show();
                alarmes.remove(index.intValue());
                notifyDataSetChanged();
            }
        });

        String tempo = String.valueOf(alarme.getTempo().get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", alarme.getTempo().get(Calendar.MINUTE));
        TextView textView = (TextView) viewInlfada.findViewById(R.id.horario_estabelecido);
        textView.setText(tempo);

        return viewInlfada;
    }
}
