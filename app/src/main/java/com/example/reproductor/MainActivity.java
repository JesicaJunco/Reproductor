package com.example.reproductor;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button play_pause, btn_repetir;
    MediaPlayer mp;
    ImageView iv;
    //variables que nos permiten determinar si se tienen que repetir o no dependiendo de la seleccion del usuario
    int repetir = 2, posicion = 0;
    //vector que guarda las pistas de audio
    MediaPlayer[] vectormp = new MediaPlayer[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_pause = findViewById(R.id.btn_play);
        btn_repetir = findViewById(R.id.btn_repetir);
        iv = findViewById(R.id.imageView);

       //Llama a la funcion para inicializar las canciones
        initializeSongs();

        updateAlbumArt(0);
    }
    // Función para inicializar y cargar todas las canciones en vectormp
    private void initializeSongs() {
        vectormp[0] = MediaPlayer.create(this, R.raw.race);
        vectormp[1] = MediaPlayer.create(this, R.raw.sound);
        vectormp[2] = MediaPlayer.create(this, R.raw.tea);
    }
    //Método para el botón PlayPause
    public void PlayPause(View view) {
        if (vectormp[posicion].isPlaying()) { //verifica si la pista se esta reproduciendo
            vectormp[posicion].pause(); //si la pista se esta reproduciendo se tiene que pausar
            play_pause.setBackgroundResource(R.drawable.reproducir); //setBackgroundResource cambia la apariencia del boton
            Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
        } else {
            vectormp[posicion].start(); //si no se esta reproduciendo tiene que arrancar
            play_pause.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }
    //Método para el botón Stop
    public void Stop(View view) {
        if (vectormp[posicion] != null) {
            if (vectormp[posicion].isPlaying()) {
                vectormp[posicion].stop();
                vectormp[posicion].release(); // Liberar el recurso del reproductor de medios
            }
            posicion = 0;
            vectormp[posicion] = MediaPlayer.create(this, R.raw.race); // Crear un nuevo MediaPlayer
            play_pause.setBackgroundResource(R.drawable.reproducir);
            iv.setImageResource(R.drawable.portada1);
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
        }
    }
    //Método repetir una pista
    public void Repetir(View view) {
        if (repetir == 2) {
            btn_repetir.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this, "Repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(true); // Habilitar repetición
            repetir = 1;
        } else {
            btn_repetir.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(this, "No repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(false); // Deshabilitar repetición
            repetir = 2;
        }
    }
    //Método para siguiente canción
    public void Siguiente(View view) {
        if (posicion < vectormp.length - 1) {
             vectormp[posicion].isPlaying();
                vectormp[posicion].stop();
                posicion++;
                vectormp[posicion].start();
                updateAlbumArt(posicion);

            } else {
                Toast.makeText(this, "No hay más canciones", Toast.LENGTH_SHORT).show();
            }
        }
    //Método para regresar a la canción anterior
    public void Anterior(View view) {
        if (posicion > 0) {
            if (vectormp[posicion].isPlaying()) {
                // Detener la pista que se está reproduciendo actualmente
                vectormp[posicion].stop();
            }
            // Actualizar la posición y reproducir la canción anterior
            posicion--;
            vectormp[posicion].start();
            updateAlbumArt(posicion);
        } else {
            Toast.makeText(this, "No hay más canciones", Toast.LENGTH_SHORT).show();
        }
    }


    // Actualizar las portadas segun la posicion
    private void updateAlbumArt(int position) {
        if (position == 0) {
            iv.setImageResource(R.drawable.portada1);
        } else if (position == 1) {
            iv.setImageResource(R.drawable.portada2);
        } else if (position == 2) {
            iv.setImageResource(R.drawable.portada3);
        }
    }
}