package com.davidpopayan.sena.guper.models;

public class Ficha {
    private String url;
    private String numeroFicha;
    private String jornada;
    private String ambiente;
    private String lider;
    private String fechaFinEtapa;
    private String programa;

    public Ficha() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(String numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public String getFechaFinEtapa() {
        return fechaFinEtapa;
    }

    public void setFechaFinEtapa(String fechaFinEtapa) {
        this.fechaFinEtapa = fechaFinEtapa;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }
}
