package gestionescuela;
//Clase padre que va a proporcionar a través de herencia métodos comunes en Alumno y Profesor
public class Persona {
    
    //Atributos de clase: 
    private String dni;
    private String nombre;
    
    //Constructores:
    public Persona(String d, String n){
        dni = d;
        nombre = n;
    }
    
    //Métodos:
    public String getDni(){
        return dni;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    @Override
    public String toString(){
        return nombre + ", DNI: " + dni;
    }
    
    public String volcado(){
        return dni + "-" + nombre;
    }
}
