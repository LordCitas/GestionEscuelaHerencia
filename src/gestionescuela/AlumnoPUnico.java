package gestionescuela;
//Clase que guarda datos relativos al alumno: DNI, nombre y el pago completo
public class AlumnoPUnico extends Alumno{
    
    //Atributos de clase:
    
    //Constructores:
    public AlumnoPUnico(String d, String n){
        super(d, n, false);
    }
    
    //Métodos:    
    @Override
    public String toString(){
        return super.toString() + "completo";
    }
    
    @Override
    public String volcado(){
        return super.volcado() + "Completo";
    }
}
