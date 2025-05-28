package gestionescuela;
//Clase que guarda datos relativos al profesor: DNI, nombre, número de horas que imparte, su sueldo por horas 
//y el número total de profesores que se han definido en la escuela hasta ahora
public class Profesor extends Persona{
    
    //Atributos de clase: 
    private String dni;
    private String nombre;
    private int numHoras;
    private float sueldoHora;
    private static int numProfesores = 0;
    
    //Constructores:
    public Profesor(String d, String n, float s){
        dni = d;
        nombre = n;
        sueldoHora = s;
        numProfesores++;
        numHoras = 0;
    }
    
    //Métodos:
    public String getDni(){
        return dni;
    }
    
    public String getNombre(){
        return nombre;
    }
    
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
        boolean lleno = (numHoras >= 20)? true : false;
        if(lleno) System.out.println("Este profesor ya ha cubierto su máximo de horas. Por favor, elige otro: ");
        return lleno;
    }
    
    //Un método que va a comprobar si el profesor alcanzaría o superaría su límite de horas semanales si se le asignase la asignatura 'a' y, 
    //en caso afirmativo, avisa con un mensaje
    public boolean sePasaria(Asignatura a){
        boolean pasado = (numHoras + a.getHoras()*((a.getNumEntradas()==0)? 1 : a.getNumEntradas()) > 20)? true : false;
        if(pasado) System.out.println(nombre + " no podría impartir " + a.getNombre() + " porque superaría su máximo de horas semanales.");
        return pasado;
    }
    
    @Override
    public String toString(){
        return nombre + ", DNI: " + dni + ", " + sueldoHora + "€/h, " + numHoras + " horas ya asignadas";
    }
    
    public String volcado(){
        return dni + "-" + nombre + "-" + numHoras + "-" + sueldoHora;
    }
}
