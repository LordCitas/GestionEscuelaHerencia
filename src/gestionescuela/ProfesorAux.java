package gestionescuela;
//Clase que añade a los datos del profesor el número máximo de horas
public class ProfesorAux extends Profesor{
    //Constructores:
    public ProfesorAux(String d, String n, float s){
        super(d, n, s);
        maxNumHoras = 10;
    }
}
