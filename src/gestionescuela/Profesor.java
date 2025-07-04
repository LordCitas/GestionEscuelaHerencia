package gestionescuela;
//Clase que guarda datos relativos al profesor: DNI, nombre, número de horas que imparte, su sueldo por horas 
//y el número total de profesores que se han definido en la escuela hasta ahora
public class Profesor extends Persona{
    
    //Atributos de clase:
    protected int numHoras;
    protected float sueldoHora;
    protected static int numProfesores = 0;
    protected int maxNumHoras;
    
    //Constructores:
    public Profesor(String d, String n, float s){
        super(d, n);
        sueldoHora = s;
        numProfesores++;
        numHoras = 0;
        maxNumHoras = 20;
    }
    
    //Métodos:
    public int getNumHoras(){
        return numHoras;
    }
    
    public float getSueldoHora(){
        return sueldoHora;
    }
    
    public static int getNumProf(){
        return numProfesores;
    }
    
    //Un método que nos permite incrementar el número de horas que imparte un profesor a la semana en una cantidad dada (int)
    public void sumarHoras(int n){
        numHoras += n;
    }
    
    //Un método que va a comprobar si el profesor ya ha alcanzado su límite de horas semanales y, en caso afirmativo, avisa con un mensaje
    public boolean estaSobreexplotado(){
        boolean lleno = (numHoras >= maxNumHoras)? true : false;
        if(lleno) System.out.println("Este profesor ya ha cubierto su máximo de horas. Por favor, elige otro: ");
        return lleno;
    }
    
    //Un método que va a comprobar si el profesor alcanzaría o superaría su límite de horas semanales si se le asignase la asignatura 'a' y, 
    //en caso afirmativo, avisa con un mensaje
    public boolean sePasaria(Asignatura a){
        boolean pasado = (numHoras + a.getHoras()*((a.getNumEntradas() == 0)? 1 : a.getNumEntradas()) > maxNumHoras)? true : false;
        if(pasado) System.out.println(super.getNombre() + " no podría impartir " + a.getNombre() + " porque superaría su máximo de horas semanales.");
        return pasado;
    }
    
    @Override
    public String toString(){
        return super.toString() + ", " + sueldoHora + "€/h, " + numHoras + " horas de " + maxNumHoras + " ya asignadas";
    }
    
    @Override
    public String volcado(){
        return super.volcado() + "-" + numHoras + "-" + sueldoHora;
    }
}
