package com.example.guiasolucion_app;

import android.os.Parcel;
import android.os.Parcelable;

// Asegúrate de que implemente Parcelable
public class AccionBitacora implements Parcelable {
    private String hora;
    private String rol;
    private String descripcion;

    public AccionBitacora(String hora, String rol, String descripcion) {
        this.hora = hora;
        this.rol = rol;
        this.descripcion = descripcion;
    }

    // --- ESTA SECCIÓN ES CRUCIAL PARA GUARDAR EL ESTADO ---
    protected AccionBitacora(Parcel in) {
        hora = in.readString();
        rol = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<AccionBitacora> CREATOR = new Creator<AccionBitacora>() {
        @Override
        public AccionBitacora createFromParcel(Parcel in) {
            return new AccionBitacora(in);
        }

        @Override
        public AccionBitacora[] newArray(int size) {
            return new AccionBitacora[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hora);
        dest.writeString(rol);
        dest.writeString(descripcion);
    }
    // --- FIN DE LA SECCIÓN CRUCIAL ---

    // Getters
    public String getHora() { return hora; }
    public String getRol() { return rol; }
    public String getDescripcion() { return descripcion; }
}