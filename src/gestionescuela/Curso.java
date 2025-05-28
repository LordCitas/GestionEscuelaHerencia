package gestionescuela;
//Clase que guarda datos relativos al curso: nombre, año, lista y número de alumnos y asignaturas asignados, ID,
//número total de cursos que se han definido por el momento, y precio de la plaza
public class Curso {
    
    //Atributos de clase:
    private String nombre;
    private int anio;
    private Alumno[] alumnos;
    private int numAlumnos = 0;
    private Asignatura[] asignaturas;
    private int numAsignaturas = 0;
    private int id;
    private static int numCursos = 0;
    private int precio;
    
    //Constructores:
    public Curso(String n, int a, int p){
        nombre = n;
        anio = a;
        alumnos = new Alumno[30];
        asignaturas = new Asignatura[5];
        numCursos++;
        id = numCursos;
        precio = p;
    }
    
    //Métodos:
    public String getNombre(){
        return nombre;
    }
    
    public int getAnio(){
        return anio;
    }
    
    public Alumno[] getAlumnos(){
        return alumnos;
    }
    
    public int getNumAlumnos(){
        return numAlumnos;
    }
    
    public Asignatura[] getAsignaturas(){
        return asignaturas;
    }
    
    public int getNumAsignaturas(){
        return numAsignaturas;
    }
    
    public int getId(){
        return id;
    }
    
    public int getPrecio(){
        return precio;
    }
    
    public void setPrecio(int p){
        precio = p;
    }
    
    //Método que nos permite asociar una asignatura a un curso
    public void insertaAsignatura(Asignatura a){
        //Si la asignatura tiene asignado un profesor
        if(a.getProfesor() != null){
            //Si asignar la asignatura al curso hiciera que se superase el máximo de horas semanales del docente, a este lo desvinculamos de la materia
            if(a.getProfesor().sePasaria(a)) a.setProfesor(null);
            else{ //De otro modo, continuamos con el proceso
                a.getProfesor().sumarHoras(a.getHoras());
                asignaturas[numAsignaturas++] = a;
                //Un sout de aviso de que el proceso ha terminado con éxito
                System.out.println("La asignación de la asignatura se ha llevado a cabo con éxito.");
            }            
        }
        //Si la asignatura NO tiene asignado un profesor
        if(a.getProfesor() == null){
            //Comprobamos si la asignación haría que se superase el límite de horas de un profesor y, en caso afirmativo, avisamos con un mensaje
            if((a.getNumEntradas() + 1) * a.getHoras() > 20) System.out.println("Asignar esta asignatura haría que se excediese el límite de horas semanales del profesor que la fuese a impartir.");
            else{ //De otro modo, continuamos con el proceso
                a.nuevaEntrada();
                asignaturas[numAsignaturas++] = a;
                //Un sout de aviso de que el proceso ha terminado con éxito
                System.out.println("La asignación de la asignatura se ha llevado a cabo con éxito.");
            }
        } else if(a.getProfesor().getNumHoras() + a.getHoras()*a.getNumEntradas() > 20) 
            System.out.println("El profesor asignado a la asignatura no puede impartirla más veces sin exceder su máximo de horas semanal.");
    }
    
    //Método que va a insertar un alumno en el curso (ya en el main nos aseguramos de que no se pueda acceder a este proceso si no quedan plazas)
    public void insertaAlumno(Alumno a){
            alumnos[numAlumnos++] = a;
    }
    
    public static int getNumCursos(){
        return numCursos;
    }
    
    @Override
    public String toString(){
        return anio + "º de " + nombre + ", ID = " + id + ", " + (30-numAlumnos) + " plazas vacantes, a " + precio + "€ cada una";
    }
    
    //Método que va a comprobar si una asignatura 'a' está ya incluida en el curso y, en caso afirmativo, va a avisar con un mensaje 
    public boolean asignaturaIncluida(Asignatura a){
        boolean encontrada = false;
        for(int i=0; i<numAsignaturas && !encontrada; i++) encontrada = (asignaturas[i] == a)? true : false;
        if(encontrada) System.out.print(a.getNombre() + " ya está asignada a este curso. Por favor, introduce otra opción: ");
        return encontrada;
    }
    
    //Método que va a comprobar si el curso ya ha alcanzado el límite de 5 asignaturas asociadas y, en caso afirmativo, va a avisar con un mensaje 
    public boolean estaLlenoAsig(){
        boolean lleno = (numAsignaturas >= 5)? true : false;
        if(lleno) System.out.print("Este curso ya no admite más asignaturas. Por favor, elige otro: ");
        return lleno;
    }
    
    //Método que va a comprobar si el curso ya ha alcanzado el límite de 30 alumnos asociados y, en caso afirmativo, va a avisar con un mensaje 
    public boolean estaLlenoAlum(){
        boolean lleno = (numAlumnos >= 30)? true : false;
        if(lleno) System.out.print("Este curso ya no admite más alumnos. Por favor, elige otro: ");
        return lleno;
    }
    
    //Método que va a comprobar si todas las asignaturas de un curso tienen un profesor asignado
    public boolean tieneProfesores(Asignatura[] a){
        boolean valido = true;
        for(int i=0; i<numCursos && valido; i++){
            if(a[i].getProfesor() == null) valido = false;
        }
        return valido;
    }
    
    public String volcado(){
        String linea;
        linea = id + "-" + anio + "-" + nombre + "-" + precio + "-";
        for(int j = 0; j < numAsignaturas; j++){
            if(j != numAsignaturas - 1) linea += asignaturas[j].getCodigo() + ":";
            else linea += asignaturas[j].getCodigo();
        }
        if(numAsignaturas == 0) linea += " ";
        linea += "-";
        for(int j = 0; j < numAlumnos; j++){
            if(j != numAlumnos - 1) linea += alumnos[j].getDni() + ":";
            else linea += alumnos[j].getDni();
        }
        if(numAlumnos == 0) linea += " ";
        return linea;
    }
}

