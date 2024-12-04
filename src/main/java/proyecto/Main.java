
package proyecto;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner lea=new Scanner(System.in);
        GhostGame juego=new GhostGame();
        
        while(true){
            System.out.println("--MENU INICIO--"
                                        +"\n 1. LogIn"
                                        +"\n 2. Crear Player"
                                        +"\n 3. Salir");
            System.out.print("Ingrese una opcion: ");
            int opcion=lea.nextInt();
            
            switch(opcion){
                case 1:
                    System.out.print("Username :");
                    String username=lea.next();
                    System.out.println("Password: ");
                    String password=lea.next();
                    if(juego.login(username, password)){
                        System.out.println("--Login Existoso! "
                                                      +"\n *****BIENVENIDO AL JUEGO GHOST*****"
                                                      +"\n"+username);
                                                       mainMenu(juego);
                    }else{
                        System.out.println("ERROR: DATOS INCORRECTOS");
                    }
                    break;
                case 2:
                    System.out.print("Nuevo Username: ");
                    String nuevoUser=lea.next();
                    System.out.println("Nuevo Password:");
                    String nuevoPass=lea.next();
                    
                    if (juego.registrarPlayer(nuevoUser, nuevoPass)) {
                        System.out.println("PLAYER REGISTRADO");
                    }else{
                        System.out.println("ERROR: NO SE PUDO REGISTRAR AL JUGADOR");
                    }
                    break;
                    
                case 3:
                    System.out.println("!!!GRACIAS POR JUGAR GHOST!!!");
                    return;
                default:
                    System.out.println("OPCION INVALIDA");
            }
            
        }
    }
    public static void mainMenu(GhostGame juego){
                Scanner lea=new Scanner(System.in);
                while(true){
                System.out.println("---MENU PRINCIPAL---"
                                              +"\n 1. JUGAR"
                                              +"\n 2. Configuracion"
                                              +"\n 3. Reportes"
                                              +"\n 4. Mi Perfil"
                                              +"\n 5. Cerrar Sesion");
                System.out.println("Ingrese una opcion: ");
                int opcion=lea.nextInt();
                
                switch(opcion){
                    case 1:
                        System.out.println("Ingrese el username del player 2: ");
                        String player2=lea.next();
                        if(! juego.existePlayer(player2)){
                            System.out.println("El jugador no existe.");
                            break;
                        }
                        
                        juego.iniciarTablero();
                        if (juego.getMode().equalsIgnoreCase("MANUAL")) {
                            System.out.println("Modo manual seleccionado");
                            juego.ponerPiezasManual(0);
                            juego.ponerPiezasManual(1);
                        }else{
                            System.out.println("Modo aleatorio seleccionado");
                            juego.ponerPiezasRandom(0);
                            juego.ponerPiezasRandom(1);
                        }
                        juego.empezarJuego();
                        break;
                        
                    case 2:
                        configurar(juego);
                        break;
                    case 3:
                        reportes(juego)  ;
                        break;
                    case 4:
                        perfil(juego);
                        break;
                    case 5:
                        System.out.println("Sesion Cerrada");
                        return;
                    default:
                        System.out.println("OPCION INVALIDA");
                        
                }
              }
            }
    
    public static void configurar(GhostGame juego){
        Scanner lea=new Scanner(System.in);
        
        while(true){
            System.out.println("---CONFIGURACION---"
                                        +"\n 1. Dificultad"
                                        +"\n 2. Modo de Juego"
                                        +"\n 3. Regresar al Menu Principal");
            System.out.println("Ingrese una opcion: ");
            int opcion=lea.nextInt();
            
            switch(opcion){
                case 1:
                    System.out.println("--Selecione la dificultad del juego--"
                    +"\n 1. Normal - (8 fantasmas por jugador)"
                    +"\n 2. Expert - (4 fantasmas por jugador)"
                    +"\n 3. Genius - (2 fantasmas y 4 trampas)");
                    int dificultad=lea.nextInt();
                    juego.configurarDificultad(dificultad);
                    System.out.println("DIFICULTAD PUESTA");
                    break;
                    
                case 2:
                    System.out.println("--Selecione el modo de juego--"
                                               +"\n 1. Aleatorio"
                                               + "\n 2. Manual");
                    int mode=lea.nextInt();
                    if (mode==1) {
                        juego.configurarMode("ALEATORIO");
                    }else if(mode==2){
                        juego.configurarMode("MANUAL");
                    }else{
                        System.out.println("OPCION INVALIDA");
                    }
                    System.out.println("Modo Actualizado");
                    break;
                    
                case 3:
                    return;
                default:
                    System.out.println("OPCION INVALIDA");
            }
        }
    }
    
    public static void reportes(GhostGame juego){
        Scanner lea=new Scanner(System.in);
        
        while(true){
            System.out.println("---REPORTES---"
            +"\n 1. Ultimos 10 juegos"
            +"\n 2. Ranking de Jugadores"
            +"\n 3. Regresar al Menu Principal"
            +"\n Ingrese una opcion");
            int opcion=lea.nextInt();
            
            switch(opcion){
                case 1:
                    String[] logs=juego.getActualUser().getLogs();
                    System.out.println("Ultimos 10 juegos");
                    for (String log : logs) {
                        if (log !=null) {
                            System.out.println("- "+log);
                        }
                    }
                    break;
                case 2:
                    Player[]players=juego.getPlayers();
                    System.out.println("Ranking de Jugadores");
                    for (int i = 0; i < juego.getContarPlayer(); i++) {
                        Player player=players[i];
                        System.out.println(player.getUsername()+" - "+player.getPuntos()+" puntos");
                    }
                    break;
                    
                case 3:
                    return;
                    
                default:
                    System.out.println("OPCION INVALIDA");
            }
        }
    }
    
    public static void perfil(GhostGame juego){
        Scanner lea=new Scanner(System.in);
        Player actualUser=juego.getActualUser();
        
        while(true){
            System.out.println("--MI PERFIL--"
                                        +"\n 1. Ver Datos"
                                        +"\n 2. Cambiar Password"
                                        +"\n 3. Eliminar Cuenta"
                                        +"\n 4. Salir"
                                        +"\n Ingrese una opcion");
            int opcion=lea.nextInt();
            
            switch(opcion){
                case 1:
                    System.out.println("Perfil: "+actualUser.getUsername()
                                               +"\n Puntos: "+actualUser.getPuntos());
                    break;
                case 2:
                    System.out.println("Ingrese su nueva password: ");
                    String nuevoPassword=lea.next();
                    actualUser.actualizarPassword(nuevoPassword);
                    System.out.println("Password Actualizada!");
                    break;
                    
                case 3:
                    System.out.println("Seguro que desea eliminar su cuenta? (s | n)");
                    char confirmar=lea.next().toLowerCase().charAt(0);
                    if (confirmar == 's') {
                        juego.eliminarUser();
                        System.out.println("CUENTA ELIMINADA");
                        return;
                    }else{
                          System.out.println("OPERACON CANCELADA");  
                    }
                    break;
                case 4:
                    return ;
                default:
                    System.out.println("OPCION INVALIDA");
            }
        }
    }
}