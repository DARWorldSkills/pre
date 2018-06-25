package com.davidpopayan.sena.guper.models;

public class Permiso {
    private String url;
    private String motivo;
    private String solicitoPermisoPor;
    private String permisoPorHora;
    private String permisoPorDias;
    private String horaSalida;
    private String fecha;

    public Permiso() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSolicitoPermisoPor() {
        return solicitoPermisoPor;
    }

    public void setSolicitoPermisoPor(String solicitoPermisoPor) {
        this.solicitoPermisoPor = solicitoPermisoPor;
    }

    public String getPermisoPorHora() {
        return permisoPorHora;
    }

    public void setPermisoPorHora(String permisoPorHora) {
        this.permisoPorHora = permisoPorHora;
    }

    public String getPermisoPorDias() {
        return permisoPorDias;
    }

    public void setPermisoPorDias(String permisoPorDias) {
        this.permisoPorDias = permisoPorDias;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
