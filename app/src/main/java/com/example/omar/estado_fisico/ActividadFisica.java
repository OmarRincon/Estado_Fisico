package com.example.omar.estado_fisico;

import java.util.Date;

/**
 * Created by Omar on 07/01/2016.
 */
public class ActividadFisica {
    private Date inicio_actividad;//horas,minutos,segundos
    private Date final_actividad;
    private long duracion;
    private double distancia_estimada;//km
    private Integer calorias;//calorias


    public ActividadFisica(){

    }

    //tiempo transcurrido entre fechas
    public String tiempo_actividad(Date inicio_actividad, Date final_actividad){
        //nos devuelve la cantidad de milisegundos transcurridos desde el 1 de Enero de 1970, a las 00:00:00 GMT
        long milisegundos= final_actividad.getTime()-inicio_actividad.getTime();

        double tiempo = milisegundos/3.6 * Math.pow(10,6);
        if (milisegundos>0) {
            int horas = (int) tiempo;
        }else{

        }
        return "";
    }

}
