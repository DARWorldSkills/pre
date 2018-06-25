package com.davidpopayan.sena.guper.models;

public class Constantes {

    public static final String NOM_BD ="ProyectoS.db";
    public static final  int VERSION_BD =1;


    public static final String NOM_TABLA = "CREATE TABLE LOGIN(NAME TEXT, PASSWORD TEXT)";

   //Urls para webservices
   public static final String urlLogin="https://guperproject.herokuapp.com/rest-auth/login/";
   public static final String urlAprendizFicha="https://guperproject.herokuapp.com/api/aprendiz_ficha/";
   public static final String urlAprendizPermiso="https://guperproject.herokuapp.com/api/aceptar_permiso/";
   public static final String urlFicha="https://guperproject.herokuapp.com/api/ficha/";
   public static final String urlPermiso="https://guperproject.herokuapp.com/api/solicitar_permiso/";
   public static final String urlPersona="https://guperproject.herokuapp.com/api/persona/";
   public static final String urlRol="https://guperproject.herokuapp.com/api/Rol/";
   public static final String urlRolPersona="https://guperproject.herokuapp.com/api/Rol_persona/";
   public static final String urlUser="https://guperproject.herokuapp.com/api/user/";
}
