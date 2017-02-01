package com.treinamento.victortripeno.testalarm.modelo;

import java.util.Calendar;

/**
 * Created by victor.tripeno on 31/01/2017.
 */
public class Alarme {

    private Long id;
    private Calendar tempo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getTempo() {
        return tempo;
    }

    public void setTempo(Calendar tempo) {
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        return "Alarme{" +
                "id=" + id +
                ", tempo=" + tempo +
                '}';
    }


}
