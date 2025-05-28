package gestionescuela;
//Clase que guarda datos relativos a la asignatura: nombre, horas/semana, profesor que la imparte (si está asignado),
//un código identificador, el número total de asignaturas que se han definido y el número de cursos a los que se ha asociado la asignatura
public class Asignatura {
    
    //Atributos de clase:
    private int horas;
    private Profesor docente;
    private int codigo;
    private String nombre;
    private static int numAsignaturas = 0;
    private int numCursos;
    
    //Constructores:
    public Asignatura(String n, int h){
        horas = h;
        nombre = n;
        numAsignaturas++;
        codigo = numAsignaturas;
        numCursos = 0;
    }
    
    //Métodos:
    public int getHoras(){
        return horas;
    }
    
    public void setHoras(int h){
        horas = h;
    }
    
    public Profesor getProfesor(){
        return docente;
    }
    
    public void setProfesor(Profesor p){
        docente = p;
        //Sólo le sumamos horas al profesor si no es nulo
        if(p != null) p.sumarHoras(horas*numCursos);
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public static int getNumAsig(){
        return numAsignaturas;
    }
    
    public int getNumEntradas(){
        return numCursos;
    }
    
    public void nuevaEntrada(){
        numCursos++;
    }
        
    @Override
    public String toString(){
        String res = nombre + ", " + horas + " h/semana, (ID = " + codigo + ")";
        if(docente != null) res += " impartida por " + docente;
        
        return res;
    }
    
    public String volcado(){
        String linea = codigo + "-" + nombre + "-";
        if(docente != null) linea += docente.getDni();
        linea += "-" + horas + "-" + numCursos;
        return linea;
        //return codigo + "-" + nombre + "-" + (docente.getDni() == null)? "" : docente.getDni() + "-" + horas + "-" + numCursos;
    }
    
}
