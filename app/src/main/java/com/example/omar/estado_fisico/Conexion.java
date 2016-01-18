package com.example.omar.estado_fisico;

import android.app.Application;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseSession;
import com.parse.ParseUser;

import bolts.Task;


/**
 * Created by Omar on 10/01/2016.
 */
public class Conexion{
    private static final String YOUR_APPLICATION_ID = "ohGe5NCSShiJYNeAAFwezBNYB7vQii8wyqnE21LY";
    private static final String YOUR_CLIENT_KEY = "00gfPorhPo6HfdXPNhvvBSnvEFgBEWf8cLp6dKWZ" ;
    private MainActivity principal;
    private boolean estaConectado=false;
    public ParseUser usuarioActual;

    public Conexion(MainActivity principal) {
        Parse.initialize(principal, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        this.principal = principal;
    }

    public void conectar(final ActividadFisica af){
        ParseUser.logInInBackground("clase_gimnasia", "12345Abcde",new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    usuarioActual = ParseUser.getCurrentUser();
                    af.setNombre(usuarioActual.getUsername());
                    subirActividadFisica(af);
                    Toast toast = Toast.makeText(principal.getBaseContext(), "Enhorabuena "+usuarioActual.getUsername()+" te has conectado con total exito", Toast.LENGTH_SHORT);
                    toast.show();
                    estaConectado=true;
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Toast toast = Toast.makeText(principal, "Comprueba usuario y contraseña, no es correcto", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public boolean isEstaConectado() {
        return estaConectado;
    }

    public void subirActividadFisica(ActividadFisica af){
            ParseObject ActividadFisica = new ParseObject("ActividadFisica");
            ActividadFisica.put("Nombre", af.getNombre());
            ActividadFisica.put("Distancia_Estimada", af.getDistancia_estimada());
            ActividadFisica.put("Calorias", af.getCalorias());
            ActividadFisica.put("Pasos", af.getPasos());
            ActividadFisica.put("Duracion", af.getDuracion());
            ActividadFisica.saveInBackground();
            Toast toast = Toast.makeText(principal, "Actividad Fisica creada correctamente", Toast.LENGTH_SHORT);
            toast.show();
    }

    public void desconectar(){
        estaConectado=false;
        ParseUser.logOut();
    }
}
