package com.example.omar.estado_fisico;


/**
 * Created by Omar on 07/01/2016.
 */
public class ActividadFisica{
    private String nombre;
    private double distancia_estimada;//km
    private int calorias;//calorias
    private int pasos;//numero de pasos
    private String duracion;//duracion del tiempo


    public ActividadFisica(double distancia_estimada, int calorias, int pasos,String duracion) {
        nombre="";
        this.distancia_estimada = distancia_estimada;
        this.calorias = calorias;
        this.pasos = pasos;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDistancia_estimada() {
        return distancia_estimada;
    }

    public void setDistancia_estimada(double distancia_estimada) {
        this.distancia_estimada = distancia_estimada;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public Integer getPasos() {
        return pasos;
    }

    public void setPasos(Integer pasos) {
        this.pasos = pasos;
    }

    public String getDuracion() {
        return duracion;
    }

    //tiempo transcurrido entre fechas
    /*public String tiempo_actividad(Date inicio_actividad, Date final_actividad){
        //nos devuelve la cantidad de milisegundos transcurridos desde el 1 de Enero de 1970, a las 00:00:00 GMT
        long milisegundos = final_actividad.getTime()-inicio_actividad.getTime();
        int horas ,minutos ,segundos;

        if (milisegundos>0) {
            horas = (int) (milisegundos /(3.6*Math.pow(10, 6)));
            milisegundos = (long) (milisegundos-(horas*(3.6*Math.pow(10, 6))));
            minutos = (int)(milisegundos / (6*Math.pow(10, 4)));
            milisegundos = (long) (milisegundos-minutos*(6*Math.pow(10, 4)));
            segundos = (int)(milisegundos /(1*Math.pow(10, 3)));
        }else{
            horas = 0;
            minutos =0;
            segundos=0;
        }
        return horas +"h, "+minutos+"min,"+segundos+"s";
    }*/
}
