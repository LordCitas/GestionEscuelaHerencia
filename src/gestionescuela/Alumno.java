package gestionescuela;
//Clase que guarda datos relativos al alumno: DNI, nombre y modalidad de pago (a plazos || completo)
public class Alumno extends Persona{
    
    //Atributos de clase:
    private String dni;
    private String nombre;
    private boolean plazos;
    private static int numAlumnos = 0;
    
    //Constructores:
    public Alumno(String d, String n, boolean p){
        dni = d;
        nombre = n;
        plazos = p;
        numAlumnos++;
    }
    
    //Métodos:
    public boolean getPlazos(){
        return plazos;
    }  
    
    public static int getNumAlumnos(){
        return numAlumnos;
    }  
    
    @Override
    public String toString(){
        String respuesta = nombre + ", DNI: " + dni + ", pago ";
        if(plazos) respuesta += "por plazos";
        else respuesta += "completo";
        return respuesta;
    }
    
    public String volcado(){
        String respuesta = dni + "-" + nombre + "-";
        if(plazos) respuesta += "Plazos";
        else respuesta += "Completo";
        return respuesta;
    }
}
