package gestionescuela;
//Clase que guarda datos relativos al alumno: DNI, nombre y modalidad de pago (a plazos || completo)
abstract public class Alumno extends Persona{
    
    //Atributos de clase:
    private boolean plazos;
    private static int numAlumnos = 0;
    
    //Constructores:
    public Alumno(String d, String n, boolean p){
        super(d, n);
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
        return super.toString() + ", pago ";
    }
    
    public String volcado(){
        return super.volcado() + "-";
    }
}
