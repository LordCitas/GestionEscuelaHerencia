package gestionescuela;
//Clase que guarda datos relativos al alumno: DNI, nombre y el pago a plazos
public class AlumnoPPlazos extends Alumno{
    
    //Atributos de clase:
    
    //Constructores:
    public AlumnoPPlazos(String d, String n){
        super(d, n, true);
    }
    
    //Métodos:    
    @Override
    public String toString(){
        return super.toString() + "por plazos";
    }
    
    @Override
    public String volcado(){
        return super.volcado() + "Plazos";
    }
}
