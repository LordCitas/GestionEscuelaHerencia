package gestionescuela;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
public class GestionEscuela {
    private static final String RUTA = "./data";
    
    
    public static void main(String[] args) {
       
        
        //Defino los cambios de color de texto.
        final String VERDE = "\u001B[32m";
        final String ROJO = "\u001B[31m";
        final String RESET = "\u001B[0m";
        
        //Definimos las variables que van a ir controlando el flujo del programa
        int respuesta = 2, respuesta2, longitud, numCursos;
        
        //Unos arrays para generar nombres automáticamente cuando no queramos introducirlos a mano
        String[] nombres = {"Daniel", "David", "Martina", "Blanca", "Carlos",
                            "Álvaro", "Pablo", "Inés", "Ana", "Alejandro",
                            "Diego", "Pepe", "Desiree", "Juanjo", "Érica",
                            "Lucía", "Pedro", "Andrés", "Teresa"};
        String[] apellidos = {"Aybar", "Soto", "Terreu", "González", "López",
                              "Aguado", "Hermoso", "Flores", "Zurita", "Gallego",
                              "Portero", "Montoya", "Aguilera", "Pelta", "Pérez",
                              "Ayuso", "Rajoy", "Zapatero", "Sánchez"};

        //Otro string para mostrar los meses del año
        String[] meses = {"Septiembre", "Octubre", "Noviembre", "Diciembre", "Enero",
                          "Febrero", "Marzo", "Abril", "Mayo", "Junio"};
        
        //Definimos los arrays que van a almacenar toda la información de la escuela
        Curso[] cursos;
        Asignatura[] asignaturas;
        Profesor[] profesores;
        Alumno[] alumnos;
        
        //Creamos la dirección de la carpeta de datos
        File ruta = new File(RUTA);

        if(ruta.exists()){ //Si la carpeta ya existe:
            //Ofrecemos la opción de cargar los datos de esa carpeta
            System.out.print("¿Quieres cargar los datos de la carpeta data?\n\t1- Sí\n\t2- No\nTu respuesta: ");
            respuesta = seleccionarOpcionInt(1,2);
        }
        
        //Definimos el scaner
        Scanner scan = new Scanner(System.in);
            
        if(respuesta == 1){
            numCursos = cargarNumAulas();
            cursos = new Curso[numCursos];
            asignaturas = new Asignatura[numCursos*5];
            profesores = new Profesor[numCursos*5];
            alumnos = new Alumno[numCursos*30];
            cargarProfesores(profesores);
            cargarAlumnos(alumnos);
            cargarAsignaturas(asignaturas, profesores);
            cargarCursos(cursos, asignaturas, profesores, alumnos);             
        } else { //Si la carpeta no existe:
            //Pedimos el número de aulas (cursos) con los que vamos a trabajar (como máximo)
            System.out.print("Introduce el número de aulas con las que cuenta la escuela: ");
            numCursos = scan.nextInt();
            while(numCursos < 1){
                System.out.print("La escuela debe tener como mínimo un aula. Por favor, introduce otro número: ");
                numCursos = scan.nextInt();
            }

            //Limpiamos el buffer
            scan.nextLine();
            
            //Definimos los arrays que van a almacenar toda la información de la escuela
            cursos = new Curso[numCursos];
            asignaturas = new Asignatura[numCursos*5];
            profesores = new Profesor[numCursos*5];
        }

        //Empezamos con el bucle que va a servir de interfaz para los métodos
        do{
            //Pedimos que se elija una función del programa a ejecutar
            System.out.print("\n¿Qué operación quieres realizar?"
                                    + "\n\t1- Crear un curso"
                                    + "\n\t2- Crear una asignatura"
                                    + "\n\t3- Asignar una asignatura a un curso"
                                    + "\n\t4- Registrar un profesor"
                                    + "\n\t5- Asignar un profesor a una asignatura"
                                    + "\n\t6- Registrar un alumno"
                                    + "\n\t7- Mostrar toda la información de la escuela"
                                    + "\n\t8- Calcular el margen de ganancia bruto de la escuela"
                                    + "\n\t9- Guardar los datos de la escuela"
                                    + "\n\t10- Salir"
                             + "\nTu respuesta: ");
            //Debemos comprobar que la opción elegida esté recogida entre las que hemos definido, y volver a pedirla si no es el caso
            respuesta = seleccionarOpcionInt(1, 10);
            
            //Dependiendo de la respuesta que hayamos recogido, ejecutamos una parte del programa u otra
            switch(respuesta){
                case 1: //Crear un curso
                    //Si ya hemos llenado todas las aulas, no podemos hacer más cursos
                    if(Curso.getNumCursos() == numCursos) System.out.println("Ya no se pueden añadir más cursos.");
                    else{ //Si todavía nos queda espacio, permitimos crear el curso
                        
                        //Pedimos los datos con los que vamos a construir el curso
                        System.out.print("Nombre del curso: ");
                        String nCurso = scan.nextLine();

                        System.out.print("Año del curso (1 o 2): ");
                        int aCurso = seleccionarOpcionInt(1,2);

                        System.out.print("Precio del curso: ");
                        int pCurso = scan.nextInt();
                        
                        //Limpiamos el buffer
                        scan.nextLine();
                        
                        //Creamos el curso con los datos recogidos
                        cursos[Curso.getNumCursos()] = new Curso(nCurso, aCurso, pCurso);
                        
                        //Un mensaje para anunciar el fin del proceso
                        System.out.println(VERDE + "Se ha añadido el curso " + ROJO + cursos[Curso.getNumCursos()-1] + RESET);
                    }
                break;
                
                case 2: //Crear una asignatura
                    //Si ya hemos definido un número de asignaturas igual a 5 veces el de cursos, no podemos crear otra
                    if(Asignatura.getNumAsig() == asignaturas.length) System.out.println("No se pueden crear más asignaturas.");
                    else{ //Si todavía nos queda espacio, permitimos crear la asignatura
                        
                        //Pedimos el nombre que se le va a dar a la asignatura
                        System.out.print("Nombre de la asignatura: ");
                        String nAsignatura = scan.nextLine();
                        
                        //Pedimos el número de horas que se van a impartir cada semana, y comprobamos que el valor sea válido
                        System.out.print("Número de horas semanales de la asignatura (1-20): ");
                        int hAsignatura = seleccionarOpcionInt(1, 20);
                        
                        //Creamos la asignatura
                        asignaturas[Asignatura.getNumAsig()] = new Asignatura(nAsignatura, hAsignatura);
                        
                        //Un mensaje para anunciar el fin del proceso
                        System.out.println(VERDE + "Se ha añadido la asignatura " + ROJO + asignaturas[Asignatura.getNumAsig()-1] + RESET);
                    }
                break;
                
                case 3: //Asignar una asignatura a un curso
                    //Si todavía no hemos definido ningún curso/asignatura, no podemos hacer la asignación
                    if(Curso.getNumCursos() == 0) System.out.println("No hay ningún curso registrado.");
                    else if(Asignatura.getNumAsig() == 0) System.out.println("No hay ninguna asignatura registrada.");
                    else{ //Si ya tenemos al menos uno de cada, permitimos asignar una asignatura
                        
                        //Mostramos la información de los cursos, pedimos que se seleccione uno y comprobamos que la elección es válida
                        System.out.println("Elige el curso al que quieres asignarle una asignatura: ");
                        imprimirLista(cursos, 1);
                        longitud = Curso.getNumCursos()+1;
                        System.out.print("\t" + longitud + "- Salir\nTu respuesta: ");
                        do{
                            respuesta2 = seleccionarOpcionInt(1, longitud);
                        } while (respuesta2 != longitud && cursos[respuesta2-1].estaLlenoAsig());
                        
                        //Sólo continuamos con el proceso si no hemos elegido la opción "Salir"
                        if(respuesta2 != longitud){
                            //Mostramos la información de las asignaturas, pedimos que se seleccione una y comprobamos que la elección es válida
                            System.out.println("Elige la asignatura que quieres asignarle a este curso: ");
                            imprimirLista(asignaturas, 1);
                            longitud = Asignatura.getNumAsig()+1;
                            System.out.print("\t" + longitud + "- Salir\nTu respuesta: ");
                            do{
                                respuesta = seleccionarOpcionInt(1, longitud);
                            } while (cursos[respuesta2-1].asignaturaIncluida(asignaturas[respuesta-1]) && respuesta != longitud);
                            
                            //Sólo continuamos con el proceso si no hemos elegido la opción "Salir" y si 
                            if(respuesta != longitud){
                                cursos[respuesta2-1].insertaAsignatura(asignaturas[respuesta-1]);
                                
                                //Un mensaje para anunciar el fin del proceso
                                System.out.println(VERDE + "Se ha asignado la asignatura " + ROJO + asignaturas[respuesta-1] + VERDE + " al curso " + ROJO + cursos[respuesta2-1] + RESET);
                            }
                        }
                    }
                break;
                
                case 4: //Registrar un profesor
                    //Si ya hemos definido un número de profesores igual a 5 veces el de cursos, no podemos crear otro más
                    if(Profesor.getNumProf() == profesores.length) System.out.println("No se pueden registrar más profesores.");
                    else{ //Si todavía nos queda espacio, permitimos registrar al nuevo profesor
                        //Damos la opción de generar automáticamente al profesor
                        System.out.print("¿Quieres introducir los datos del profesor manualmente?\n\t1- Sí\n\t2- No\nTu respuesta: ");
                        respuesta = seleccionarOpcionInt(1, 2);
                        
                        //Defino variables que voy a usar en ambos casos
                        String nProfesor, dniProfesor;
                        float sProfesor;
                        
                        if(respuesta == 1){
                            //Pedimos el nombre del profesor
                            System.out.print("Nombre y apellidos del profesor: ");
                            nProfesor = scan.nextLine();

                            //Pedimos el dni del profesor
                            System.out.print("DNI del profesor: ");
                            dniProfesor = scan.nextLine();
                            
                            //Pedimos el salario del profesor
                            System.out.print("Sueldo por horas del profesor: ");
                            sProfesor =  seleccionarOpcionFloat(10, 100);
                        } else { //Vamos a generar un profesor automáticamente
                            //Generamos un nombre aleatorio usando los arrays de nombres que hemos definido al principio
                            nProfesor = nombres[(int)(Math.random()*19)] + " " + apellidos[(int)(Math.random()*19)] + 
                                               " " + apellidos[(int)(Math.random()*19)];
                            
                            //Generamos un DNI aleatorio
                            dniProfesor = generaDNI();
                            
                            //Generamos un sueldo aleatorio asegurándonos de que sea mayor que 10 (le pongo de máximo 40€ por poner algo)
                            sProfesor = (float)((10 + Math.random()*30));
                            //Truncamos pa que quede bonito
                            sProfesor -= sProfesor%0.01;
                        }
                        
                        //Construimos el profesor y lo introducimos en el array
                        profesores[Profesor.getNumProf()] = new Profesor(dniProfesor, nProfesor, sProfesor);
                        
                        //Un mensaje para anunciar el fin del proceso
                        System.out.println(VERDE + "Se ha añadido al profesor " + ROJO + profesores[Profesor.getNumProf()-1] + RESET);
                    }
                break;
                
                case 5: //Asignar un profesor a una asignatura
                    //Si todavía no hemos definido ningún profesor/asignatura, no podemos hacer la asignación
                    if(Profesor.getNumProf() == 0) System.out.println("No hay ningún profesor registrado.");
                    else if(Asignatura.getNumAsig() == 0) System.out.println("No hay ninguna asignatura registrada.");
                    else{ //Si ya tenemos por lo menos uno de cada registrado, continuamos con el proceso
                        //Mostramos la lista de profesores y pedimos que se seleccione uno, comprobando que pueda apuntarse a otra asignatura sin superar su límite de horas
                        System.out.println("Elige al profesor que quieres asignar a una asignatura:");
                        imprimirLista(profesores, 1);
                        longitud = Profesor.getNumProf()+1;
                        System.out.print("\t" + longitud + "- Salir\nTu respuesta: ");
                        do{
                            respuesta2 = seleccionarOpcionInt(1, longitud);
                        } while (respuesta2 != longitud && profesores[respuesta2-1].estaSobreexplotado());
                        
                        //Sólo continuamos con el proceso si no hemos elegido la opción "Salir"
                        if(respuesta2 != longitud){
                            //Mostramos la información de las asignaturas, pedimos que se seleccione una y comprobamos que la elección es válida
                            System.out.println("Elige la asignatura que quieres asignarle a " + profesores[respuesta2-1].getNombre() + ": ");
                            imprimirLista(asignaturas, 1);
                            longitud = Asignatura.getNumAsig()+1;
                            System.out.print("\t" + longitud + "- Salir\nTu respuesta: ");
                            do{
                                respuesta = seleccionarOpcionInt(1, longitud);
                            } while (respuesta != longitud && profesores[respuesta2-1].sePasaria(asignaturas[--respuesta]));
                            
                            //Sólo continuamos con el proceso si no hemos elegido la opción "Salir"
                            if(respuesta != longitud){
                                //Una vez nos hemos asegurado de que los datos son válidos, pasamos a asignar el profesor a la asignatura
                                asignaturas[respuesta].setProfesor(profesores[respuesta2-1]);
                                
                                //Un mensaje para anunciar el fin del proceso
                                System.out.println(VERDE + "Se ha asignado al profesor " + ROJO + profesores[respuesta2-1] + VERDE + " a la asignatura " + 
                                        ROJO + asignaturas[respuesta].getNombre() + " (ID:" + asignaturas[respuesta].getCodigo() + ")" +  RESET);
                            }
                        }
                                
                    }
                break;
                case 6: //Registrar un alumno
                    //Si no hemos registrado ningún curso, no vamos a permitir introducir alumnos
                    if(Curso.getNumCursos() == 0) System.out.println("No hay ningún curso registrado, crea uno para registrar un alumno.");
                    else{
                        //Mostramos la información de los cursos, pedimos que se seleccione uno y comprobamos que la elección es válida
                        System.out.println("Elige el curso al que se va a matricular el alumno: ");
                        imprimirLista(cursos, 1);
                        longitud = Curso.getNumCursos()+1;
                        System.out.print("\t" + longitud + "- Salir\nTu respuesta: ");
                        do{
                            respuesta2 = seleccionarOpcionInt(1, longitud);
                        } while (respuesta2 != longitud && cursos[respuesta2-1].estaLlenoAlum());

                        //Sólo continuamos con el proceso si no elegimos la opción de "Salir"
                        if(respuesta2 != longitud){
                            //Igual que con los profesores, vamos a dar la opción de introducir automáticamente datos de los alumnos
                            System.out.print("¿Quieres introducir los datos del alumno manualmente?\n\t1- Sí\n\t2- No\nTu respuesta: ");
                            respuesta = seleccionarOpcionInt(1, 2);
                            
                            //Defino variables que voy a usar en ambos casos
                            String nAlumno, dniAlumno;
                            boolean plazos;
                            
                            if(respuesta == 1){
                                //Pedimos el nombre del alumno
                                System.out.print("Nombre y apellidos del alumno: ");
                                nAlumno = scan.nextLine();
    
                                //Pedimos el dni del alumno
                                System.out.print("DNI de " + nAlumno + ": ");
                                dniAlumno = scan.nextLine();
                                
                                //Pedimos el método de pago del alumno
                                System.out.print("¿Cómo va a pagar el curso?\n\t1- A plazos\n\t2- Completo\nTu respuesta: ");
                                respuesta = seleccionarOpcionInt(1, 2);
                                plazos = (respuesta == 1)? true : false;

                                //Una vez lo tenemos todo, creamos y asignamos el alumno al curso elegido
                                cursos[respuesta2-1].insertaAlumno(new Alumno(dniAlumno, nAlumno, plazos));
                                
                                //Un mensaje para anunciar el fin del proceso
                                System.out.println(VERDE + "Se ha asignado al alumno " + ROJO + cursos[respuesta2-1].getAlumnos()[cursos[respuesta2-1].getNumAlumnos()-1] + VERDE + " al curso " + 
                                        ROJO + cursos[respuesta2-1] +  RESET);
                            } else { //Vamos a generar un alumno automáticamente (o varios)
                                //Damos la opción de rellenar el curso automáticamente
                                System.out.print("¿Quieres llenar el curso automáticamente?\n\t1- Sí\n\t2- No\nTu respuesta: ");
                                boolean auto = (seleccionarOpcionInt(1, 2) == 1)? true : false;
                                
                                //Definimos el número de veces que vamos a generar alumnos dentro del curso
                                int veces = (auto)? 30 - cursos[respuesta2-1].getNumAlumnos() : 1;
                                
                                //Vamos con el bucle de generación de alumnos
                                for(int i=0; i<veces; i++){
                                    //Generamos un nombre aleatorio usando los arrays de nombres que hemos definido al principio
                                    nAlumno = nombres[(int)(Math.random()*19)] + " " + apellidos[(int)(Math.random()*19)] + 
                                    " " + apellidos[(int)(Math.random()*19)];
                
                                    //Generamos un DNI aleatorio
                                    dniAlumno = generaDNI();
                                    
                                    //Ponemos un tipo de pago al azar
                                    plazos = (Math.random() < 0.5)? true : false;

                                    //Una vez lo tenemos todo, creamos y asignamos el alumno al curso elegido
                                    cursos[respuesta2-1].insertaAlumno(new Alumno(dniAlumno, nAlumno, plazos));
                                }
                                
                                //Un mensaje para anunciar el fin del proceso
                                if(veces == 1){
                                    System.out.println(VERDE + "Se ha asignado al alumno " + ROJO + cursos[respuesta2-1].getAlumnos()[cursos[respuesta2-1].getNumAlumnos()-1] + VERDE + " al curso " + 
                                            ROJO + cursos[respuesta2-1] +  RESET);
                                } else {
                                    System.out.println(VERDE + "Se han asignado " + veces + " alumnos al curso " + ROJO + cursos[respuesta2-1] +  RESET);
                                }
                            }
                        }
                    }
                break;

                case 7: //Mostrar toda la información de la escuela
                    //Mostramos la lista de cursos registrados hasta ahora (si no hemos definido ninguno, avisamos al usuario)
                    System.out.println("CURSOS:");
                    longitud = Curso.getNumCursos();
                    if(longitud == 0) System.out.println("\tNo se ha registrado ningún curso por ahora.");
                    else{
                        //Para cada curso, mostramos su lista de asignaturas y alumnos asociados
                        for(int i=0; i<Curso.getNumCursos(); i++){
                            System.out.println("\t" + (i+1) + "- " + cursos[i]);
                            if(cursos[i].getNumAsignaturas() != 0){
                                System.out.print("\n\t\tASIGNATURAS:\n");
                                imprimirLista(cursos[i].getAsignaturas(), 3);
                            }
                            if(cursos[i].getNumAlumnos() != 0){
                                System.out.print("\t\tALUMNOS:\n");
                                imprimirLista(cursos[i].getAlumnos(), 3);
                            }
                        }
                    }
                    
                    //Mostramos la lista de asignaturas registradas hasta ahora (si no hemos definido ninguna, avisamos al usuario)
                    longitud = Asignatura.getNumAsig();
                    System.out.println("\n\nASIGNATURAS:");
                    if(longitud == 0) System.out.println("\tNo se ha registrado ninguna asignatura por ahora.");
                    else imprimirLista(asignaturas, 1);
                    
                    //Mostramos la lista de profesores registrados hasta ahora (si no hemos definido ninguno, avisamos al usuario)
                    longitud = Profesor.getNumProf();
                    System.out.println("\n\nPROFESORES:");
                    if(longitud == 0) System.out.println("\tNo se ha registrado ningún profesor por ahora.");
                    else imprimirLista(profesores, 1);
                break;
                case 8: //Calcular el margen de ganancia bruto de la escuela
                    longitud = Curso.getNumCursos();
                    //No podemos calcular el margen de ganancia si todavía no hemos definido ningún curso
                    if(longitud == 0) System.out.println("Todavía no se ha definido ningún curso.");
                    else{
                        //Pedimos que se introduzca el mes hasta el que se quiere calcular, y comprobamos que la selección es correcta
                        System.out.print("Elige el mes hasta el que quieres calcular el margen de ganancia de la escuela:\n");
                        imprimirLista(meses, 1);
                        System.out.print("Tu respuesta: ");
                        respuesta = seleccionarOpcionInt(1, 10);
                        
                        //Definimos variables que vamos a ir usando en los cálculos
                        int numPlazos, numCompletos, ganancia = 0;
                        
                        //Vamos con el bucle para el cálculo del margen de beneficio de la escuela
                        for(int i=0; i<longitud; i++){
                            if(cursos[i].getNumAlumnos() >= 10 && cursos[i].tieneProfesores(cursos[i].getAsignaturas())){ //Sólo vamos a tener en cuenta en el cálculo a los cursos que tengan 10 o más alumnos registrados Y TENGAN PROFESOR EN TODAS SUS ASIGNATURAS
                                //Inicializamos aquí las variables que van a variar dependiendo del curso en el que estemos
                                numPlazos = 0;
                                numCompletos = 0;
                                
                                //Un bucle que comprueba el tipo de pago del alumno (a plazos o completo) y va llevando la cuenta del tamaño de ambos grupos
                                for(int j=0; j<cursos[i].getNumAlumnos(); j++){
                                    if(cursos[i].getAlumnos()[j].getPlazos() == true) numPlazos++;
                                    else numCompletos++;
                                }
                                
                                //Un bucle que va a calcular el sueldo correspondiente a los profesores EN las asignaturas que imparten DENTRO de ese curso 
                                for(int j=0; j<cursos[i].getNumAsignaturas(); j++){
                                    if(cursos[i].getAsignaturas()[j].getProfesor() != null) //Sólo accedemos si hay un profesor impartiendo la asignatura 
                                        ganancia -= cursos[i].getAsignaturas()[j].getHoras() * cursos[i].getAsignaturas()[j].getProfesor().getSueldoHora() * 4 * respuesta;
                                }
                                
                                //Sumamos al total los ingresos de las matrículas (dependiendo de los tipos de pago)
                                ganancia += (numPlazos*respuesta*cursos[i].getPrecio()/10) + numCompletos*cursos[i].getPrecio();
                                
                                //Un cout informativo para facilitar el recuento de los tipos de pago
                                System.out.println("En " + cursos[i].getNombre() + " hay " + numPlazos + " pagos en plazos, y " + numCompletos + " completos");
                            }
                        }
                        //Devolvemos a pantalla el resultado de los cálculos
                        System.out.println(VERDE + "Los ingresos de la escuela son de " + ganancia + "€" + RESET);
                    }
                break;
                
                case 9: //Guardar los datos de la escuela
                    try{
                        File data = new File(RUTA);
                        if(data.mkdir()) System.out.println("La carpeta data se ha creado con éxito.");
                        else System.out.println("La carpeta data ya existe.");
                    }catch(SecurityException e){
                        System.out.println("No tienes permisos para crear directorios.");
                    }
                    guardarEscuela(numCursos, cursos, asignaturas, profesores);
                break;

                case 10: //Salir
                    respuesta = -1;
                break;
            }
        } while(respuesta != -1);
        scan.close();
    }
   
    //Un método genérico que permite imprimir en una lista los elementos de un array (de cualquier tipo de objeto), con una sangría de "num" tabulaciones
    //Antes de usarlo, habría que asegurarse de que los elementos del array tengan definido un método toString()
    public static void imprimirLista(Object[] lista, int num){
        for (int i = 0; i < lista.length; i++) {
            if(lista[i] != null){
                for(int j=0; j<num; j++) System.out.print("\t");
                System.out.println((i+1) + "- " + lista[i]); 
            } else break;            
        }
    }
    
    //Método que devuelve un valor float comprendido entre un valor máximo y mínimo dados, repitiendo la petición si no está comprendido entre ellos
    public static float seleccionarOpcionFloat(int min, int max){
        Scanner scan = new Scanner(System.in);
        
        float respuesta = 0;
        boolean terminado;
        do{
            try{
                respuesta = scan.nextFloat();
                terminado = (respuesta  < min || respuesta > max)? false : true;
            }catch(InputMismatchException e){
                terminado = false;
            }
            if(!terminado){
                System.out.print("El valor introducido no es válido. Por favor, introduce otro: ");
                scan.nextLine();
            }
        }while(!terminado);
        
        //scan.close();
        return respuesta;
    }
    
    //Lo mismo que seleccionarOpcionFloat(), pero casteando a int
    public static int seleccionarOpcionInt(int min, int max){
        return (int) seleccionarOpcionFloat(min, max);
    }
    
    //Un método que nos va a devolver un DNI generado automáticamente
    public static String generaDNI(){
        int numero, suma = 0;
        String codigo = "";
        
        //Generamos 8 enteros aleatorios y se los concatenamos a "código"
        for(int i=0; i<8; i++){
            numero = (int)(Math.random()*10);
            codigo += numero;
            suma += numero;
        }
        
        //A través de "suma", seleccionamos una letra y la concatenamos también
        char[] lista = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        suma %= 23;
        codigo += lista[suma];
        
        return codigo;
    }
    
    //Método para encapsular todo el proceso de guardado de datos de la escuela
    public static void guardarEscuela(int numCursos, Curso[] cursos, Asignatura[] asignaturas, Profesor[] profesores){
        guardarCursos(numCursos, cursos);
        guardarAsignaturas(asignaturas);
        guardarProfesores(profesores);
        guardarAlumnos(cursos);
    }
    
    //Método para guardar la información de los cursos en el archivo cursos.txt
    public static void guardarCursos(int numAulas, Curso[] lista){
        //Concatenamos y creamos una nueva dirección para el archivo de escritura de datos
        File cursos = new File(RUTA + "/cursos.txt");
        try{
            //Mostramos mensajes tanto si el archivo ya existe como si lo acabamos de crear
            if(cursos.createNewFile()){
                System.out.println("Archivo cursos.txt creado con éxito");
            }else System.out.println("El archivo cursos.txt ya existe.");
            try{
                //Definimos un flujo de escritura para gusrdar los datos de los cursos
                FileWriter escritura = new FileWriter(cursos);
                escritura.append(numAulas + "\n");
                int num = Curso.getNumCursos();

                //Controlamos que haya datos que podamos guardar 
                if(num == 0) System.out.println("No hay cursos que guardar.\n");
                else{
                    //Sólo llamamos al método de volcado del curso cuando encontremos uno para guardar
                    for(int i = 0; i < num; i++){
                        //Debemos tener cuidado de no meter un \n al final del archivo
                        if(i != num-1) escritura.append(lista[i].volcado() + "\n");
                        else escritura.append(lista[i].volcado());
                    }
                    System.out.println("Los datos de los cursos han sido guardados con éxito.");
                }

                //Cerramos el flujo de escritura
                escritura.close();

            //Errores que vamos a controlar
            }catch(IOException e){
                System.out.println("No tienes permisos de escritura");
            }
        }catch(IOException e){
            System.out.println("La ruta ./cursos.txt no es válida");
        }
    }
    
    //Método para guardar la información de las asignaturas en el archivo asignaturas.txt
    public static void guardarAsignaturas(Asignatura[] lista){
        //Concatenamos y creamos una nueva dirección para el archivo de escritura de datos
        File asignaturas = new File(RUTA + "/asignaturas.txt");
        try{
            //Mostramos mensajes tanto si el archivo ya existe como si lo acabamos de crear
            if(asignaturas.createNewFile()){
                System.out.println("Archivo asignaturas.txt creado con éxito");
            }else System.out.println("El archivo asignaturas.txt ya existe.");
            try{
                int num = Asignatura.getNumAsig();

                //Definimos un flujo de escritura para guardar los datos de las asignaturas
                FileWriter escritura = new FileWriter(asignaturas);

                //Controlamos que haya datos que podamos guardar
                if(num == 0) System.out.println("No hay asignaturas que guardar.");
                else{
                    //Si hay datos que guardar, llamamos al método de volcado de la clase
                    for(int i = 0; i < num; i++){
                        //Debemos tener cuidadode no meter un \n al final del archivo
                        if(i != num-1) escritura.append(lista[i].volcado() + "\n");
                        else escritura.append(lista[i].volcado());
                    }
                    System.out.println("Los datos de las asignaturas han sido guardados con éxito.");
                }

                //Cerramos el flujo de escritura
                escritura.close();

            //Posibles errores que vamos a controlar
            }catch(IOException e){
                System.out.println("No tienes permisos de escritura");
            }
        }catch(IOException e){
            System.out.println("La ruta ./asignaturas.txt no es válida");
        }
    }
    
    //Método para guardar la información de los profesores en el archivo profesores.txt
    public static void guardarProfesores(Profesor[] lista){
        //Concatenamos y creamos una nueva dirección para el archivo de escritura de datos
        File profesores = new File(RUTA + "/profesores.txt");
        try{
            //Mostramos mensajes tanto si el archivo ya existe como si lo acabamos de crear
            if(profesores.createNewFile()){
                System.out.println("Archivo profesores.txt creado con éxito");
            }else System.out.println("El archivo profesores.txt ya existe.");
            try{
                int num = Profesor.getNumProf();

                //Declaramos un flujo de escritura
                FileWriter escritura = new FileWriter(profesores);

                //Controlamos que haya datos que podamos guardar
                if(num == 0) System.out.println("No hay profesores que guardar.\n");
                else{
                    //Si hay datos que podamos guardar, llamamos al método de volcado
                    for(int i = 0; i < num; i++){
                        //Debemos tener cuidadode no meter un \n al final del archivo
                        if(i != num-1) escritura.append(lista[i].volcado() + "\n");
                        else escritura.append(lista[i].volcado());
                    }
                        
                    System.out.println("Los datos de los profesores han sido guardados con éxito.");
                }

                //Cerramos el flujo de escritura
                escritura.close();
            
            //Posibles errores que vamos a controlar
            }catch(IOException e){
                System.out.println("No tienes permisos de escritura");
            }
        }catch(IOException e){
            System.out.println("La ruta ./profesores.txt no es válida");
        }
    }
    
    //Método para guardar la información de los alumnos en el archivo alumnos6.txt
    public static void guardarAlumnos(Curso[] lista){
        //Concatenamos y creamos una nueva dirección para el archivo de escritura de datos
        File alumnos = new File(RUTA + "/alumnos.txt");
        try{
            //Mostramos mensajes tanto si el archivo ya existe como si lo acabamos de crear
            if(alumnos.createNewFile()){
                System.out.println("Archivo alumnos.txt creado con éxito");
            }else System.out.println("El archivo alumnos.txt ya existe.");
            try{
                int num, contador = 0;
                FileWriter escritura = new FileWriter(alumnos);

                //Debemos recorrer todos los cursos, donde tenemos guardadas las listas de alumnos
                for(int i=0; i<Curso.getNumCursos(); i++){
                    num = lista[i].getNumAlumnos();

                    //Controlamos que haya datos que poder guardar
                    if(num == 0) System.out.println("No hay alumnos que guardar en el curso " + (i+1));
                    else{
                        Curso aux = lista[i];

                        //Si hay datos que guardar, llamamos al método de volcado de la clase
                        for(int j = 0; j < num; j++){
                            //Debemos tener cuidadode no meter un \n al final del archivo
                            if(j != num-1) escritura.append(aux.getAlumnos()[j].volcado() + "\n");
                            else escritura.append(aux.getAlumnos()[j].volcado());
                            contador++;
                        }
                    }
                }
                //Un mensaje para avisar de que se han guardado correctamente los datos, y cerramos el flujo de escritura
                if(contador != 0) System.out.println("Los datos de " + contador + " alumnos han sido guardados con éxito.");
                escritura.close();

            //Los posibles errores que vamos a controlar
            }catch(IOException e){
                System.out.println("No tienes permisos de escritura");
            }
        }catch(IOException e){
            System.out.println("La ruta ./alumnos.txt no es válida");
        }
    }
    
    //Método para cargar el número de aulas que tiene disponibles la escuela
    public static int cargarNumAulas(){
        //Modificamos la ruta del archivo a leer y lo definimos
        String ruta = RUTA + "/cursos.txt";
        File archivo = new File(ruta);
        int numAulas = 0;
        try{
            //Creamos un flujo de lectura para el archivo
            Scanner lectura = new Scanner(archivo);

            //Leemos el dato
            numAulas = Integer.parseInt(lectura.nextLine());

            //Cerramos el flujo de lectura
            lectura.close();

        } catch(IOException e){
            System.out.println("El archivo no existe.");
        }
        
        //Un mensaje para avisar de que se han cargado los datos con éxito.
        System.out.println("Se ha cargado el número de aulas de la escuela.");
        
        //Devolvemos el valor recogido
        return numAulas;
    }
     
    //Método para cargar los profesores del archivo profesores.txt
    public static void cargarProfesores(Profesor[] profesores){
        //Modificamos la ruta del archivo a leer y lo definimos
        String ruta = RUTA + "/profesores.txt";
        File archivo = new File(ruta);
        
        try{
            //Creamos un flujo de lectura de datos
            Scanner lectura = new Scanner(archivo);
            
            //Creamos un array para almacenar los fragmentos del split
            String[] lineas;

            //Vamos con el bucle que va a ir leyendo datos y creando con ellos los profesores
            for(int i=0; i<profesores.length && lectura.hasNextLine(); i++){
                lineas = lectura.nextLine().split("-");
                profesores[i] = new Profesor(lineas[0], lineas[1], (float)(Float.parseFloat(lineas[3])));
            }

            //Un mensaje para avisar de que se han cargado los datos con éxito.
            System.out.println("Se han cargado los profesores de la escuela.");

            //Cerramos el flujo de lectura
            lectura.close();
        } catch(IOException e){
            System.out.println("El archivo no existe.");
        }  
    }
    
    //Método para cargar los alumnos del archivo alumnos.txt
    public static void cargarAlumnos(Alumno[] alumnos){
        //Modificamos la ruta del archivo a leer y lo definimos
        String ruta = RUTA + "/alumnos.txt";
        File archivo = new File(ruta);
        
        try{
            //Definimos un flujo de lectura de datos
            Scanner lectura = new Scanner(archivo);
            
            //Creamos un array para almacenar los fragmentos del split, y una booleana para interpretar el tipo de pago
            String[] lineas;
            boolean plazos;

            //Vamos con el bucle que va a ir leyendo y creando los alumnos
            for(int i=0; i<alumnos.length && lectura.hasNextLine(); i++){
                lineas = lectura.nextLine().split("-");
                plazos = (lineas[2].equals("Plazos"))? true : false;
                alumnos[i] = new Alumno(lineas[0], lineas[1], plazos);
            }

            //Un mensaje para avisar de que se han cargado los datos con éxito.
            System.out.println("Se han cargado los alumnos de la escuela.");

            //Cerramos el flujo de lectura
            lectura.close();
        } catch(IOException e){
            System.out.println("El archivo no existe.");
        }
    }
    
    //Método para cargar las asignaturas del archivo asignaturas.txt
    public static void cargarAsignaturas(Asignatura[] asignaturas, Profesor[] profesores){
        //Modificamos la ruta del archivo a leer y lo definimos
        String ruta = RUTA + "/asignaturas.txt";
        File archivo = new File(ruta);
        
        try{
            //Definimos un flujo de lectura de datos
            Scanner lectura = new Scanner(archivo);
            
            //Creamos un array para almacenar los fragmentos del split
            String[] lineas;

            //Vamos con el bucle que va a ir leyendo y creando las asignaturas
            for(int i=0; i<asignaturas.length && lectura.hasNextLine(); i++){
                lineas = lectura.nextLine().split("-");

                //Creamos una asignatura con los datos del volcado
                asignaturas[i] = new Asignatura(lineas[1], Integer.parseInt(lineas[3]));

                //Si la asignatura tiene un profesor asignado en el volcado, tenemos que reflejarlo en la base de datos
                if(!lineas[2].equals("")){
                    //Una booleana para controlar el flujo de comprobaciones
                    boolean encontrado = false;

                    //Vamos comprobando coincidencias con los DNIs de los profesores de la escuela
                    for(int j=0; j<profesores.length && !encontrado; j++){
                        //Si encontramos una coincidencia, asignamos ese profesor a la asignatura y salimos del bucle
                        if(lineas[2].equals(profesores[j].getDni())){
                            encontrado = true;
                            asignaturas[i].setProfesor(profesores[j]);
                        }
                    }
                }
            }

            //Un mensaje para avisar de que se han cargado los datos con éxito.
            System.out.println("Se han cargado las asignaturas de la escuela.");

            //Cerramos el flujo de lectura
            lectura.close();
        } catch(IOException e){
            System.out.println("El archivo no existe.");
        }
    }

    //Método para cargar los cursos del archivo cursos.txt    
    public static void cargarCursos(Curso[] cursos, Asignatura[] asignaturas, Profesor[] profesores, Alumno[] alumnos){
        //Modificamos la ruta del archivo a leer y lo definimos
        String ruta = RUTA + "/cursos.txt";
        File archivo = new File(ruta);
        
        try{
            //Abrimos un flujo de lectura de datos
            Scanner lectura = new Scanner(archivo);
            
            //Quitamos la primera línea, que es el número de aulas de la escuela
            lectura.nextLine();
            
            //Definimos un array para almacenar los fragmentos del split
            String[] lineas, lineas2;
            
            //Vamos con el bucle que va a ir leyendo y creando los cursos
            for(int i=0; i<cursos.length && lectura.hasNextLine(); i++){
                lineas = lectura.nextLine().split("-");
                cursos[i] = new Curso(lineas[2], Integer.parseInt(lineas[1]), Integer.parseInt(lineas[3]));

                //Una booleana para controlar el flujo de las comprobaciones siguientes
                boolean encontrado;

                //Si el curso tiene asignaturas asignadas en el volcado, tenemos que reflejarlo en la base de datos
                if(!lineas[4].equals(" ")){
                    lineas2 = lineas[4].split(":");

                    //Vamos recorriendo los codigos de las asignaturas guardadas en el archivo cursos.txt
                    for(int j=0; j<lineas.length; j++){
                        encontrado = false;
                        for(int k=0; k<asignaturas.length && !encontrado; k++){
                            //Si encontramos una coincidencia con el código de una asignatura, asignamos esa asignatura al curso y salimos del bucle
                            if(asignaturas[k] != null && Integer.parseInt(lineas[j]) == asignaturas[k].getCodigo()){
                                encontrado = true;
                                cursos[i].insertaAsignatura(asignaturas[k]);
                            }
                        }
                    }
                }

                //Si el curso tiene alumnos asignados en el volcado, tenemos que reflejarlo en la base de datos
                if(!lineas[5].equals(" ")){
                    lineas2 = lineas[5].split(":");

                    //Vamos recorriendo los DNIs de los alumnos guardados en el archivo cursos.txt
                    for(int j=0; j<lineas2.length; j++){
                        encontrado = false;
                        for(int k=0; k<alumnos.length && !encontrado; k++){
                            //Si encontramos una coincidencia con un DNI de un alumno, asignamos ese alumno al curso y salimos del bucle
                            if(alumnos[k] != null && lineas2[j].equals(alumnos[k].getDni())){
                                encontrado = true;
                                cursos[i].insertaAlumno(alumnos[k]);
                            }
                        }
                    }
                }
            }

            //Un mensaje para avisar de que se han cargado los datos con éxito.
            System.out.println("Se han cargado los cursos de la escuela.");

            //Cerramos el flujo de lectura.
            lectura.close();
        }catch (IOException e){
            System.out.println("El archivo no existe.");
        }
    }
}
