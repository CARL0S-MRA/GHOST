
package proyecto;
import java.util.Random;
import java.util.Scanner;
public class GhostGame {
    Random random=new Random();
    Scanner lea=new Scanner(System.in);
    
    private static final int MAXPLAYERS=20;
    private Player[] players;
    private int contarPlayer;
    private Player actualUser;
    private char[][] tablero;
    private char[][] pieza;
    private String mode;
    private int dificultad;
    
    public GhostGame(){
        players=new Player[MAXPLAYERS];
        contarPlayer=0;
        actualUser=null;
        tablero=new char[6][6];
        pieza=new char[2][8];
        mode="ALEATORIO";
        dificultad=8;
        iniciarTablero();
    }
    
    public void iniciarTablero(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tablero[i][j] ='#';
            }
        }
        tablero[0][0] = tablero[0][5] = tablero[5][0] = tablero[5][5] = 'S';
    }
    
    public boolean registrarPlayer(String username, String password){
        if (contarPlayer >= MAXPLAYERS)
            return false;
        for (int i = 0; i < contarPlayer; i++) {
            if (players[i].getUsername().equals(username))
            return false;
        }
        players[contarPlayer++]=new Player(username, password);
        return true;
    }
    
    public boolean login(String username, String password){
        for (int i = 0; i < contarPlayer; i++) {
            if (players[i].getUsername().equals(username) && players[i].verificarPassword(password)) {
                actualUser = players[i];
                return true;
            }
        }
        return false;
    }
    
    public Player getActualUser(){
        return actualUser;
    }
    
    public boolean existePlayer(String username) {
    for (int i = 0; i < contarPlayer; i++) {
        if (players[i].getUsername().equals(username)) {
            return true; // El jugador existe.
        }
    }
    return false; // No se encontró el jugador.
}
    
    public void configurarDificultad(int nivelDificultad){
        switch(nivelDificultad){
            case 1:
                dificultad=8;
                System.out.println("Dificultad NORMAL seleccionada:  8 fantasmas por jugador (4 buenos y 4 malos)");
                break;
            case 2:
                dificultad=4;
                System.out.println("Dificultad EXPERT seleccionada: 4 fantasmas por jugador (2 buenos y 2 malos)");
                break;
            case 3:
                dificultad=2;
                System.out.println("Dificultad GENIUS seleccionada: 2 fantasmas buenos y 4 trampas por jugador");
                break;
            default:
                System.out.println("OPCION INVALIDA");
        }
        System.out.println("LA DIFICULTADA SE HA ACTUALIZADO");
    }
    
    public void configurarMode(String nuevoMode){
        if (nuevoMode.equalsIgnoreCase("ALEATORIO") || nuevoMode.equalsIgnoreCase("MANUAL")) {
            mode=nuevoMode;
        }
    }
    
    public String getMode(){
        return mode;
    }
    
    public void ponerPiezasRandom(int playerIndex){
        int ghostBueno= dificultad/2;
        int ghostMalo= dificultad/2;
        
        for (int i = 0; i < dificultad; i++) {
            boolean esBueno = i < ghostBueno;
            
            char piece = esBueno ? 'G' : 'M';
            
            while(true){
                int fila=playerIndex== 0 ? random.nextInt(2) : 4 + random.nextInt(2);
                int columna=random.nextInt(6);
                
                if(tablero[fila][columna] == '#'){
                    tablero[fila][columna] =piece;
                    break;
                }
            }
        }
    }
    
    public void ponerPiezasManual(int playerIndex){
        
        int contarBueno= dificultad/2;
        int contarMalo= dificultad/2;
        
        for (int i = 0; i < dificultad; i++) {
            char piece=(i < contarBueno) ? 'G' : 'M';
            boolean validar=false;
            
            while(!validar){
                System.out.println("Jugador "+(playerIndex+1)+", coloca un fantasma"+(piece=='G' ? "bueno" : "malo")+":");
                
                System.out.println("Fila: ");
                int fila=lea.nextInt();
                
                System.out.println("Columna: ");
                int columna=lea.nextInt();
                
                if (validarMovimiento(fila,columna,playerIndex)) {
                    tablero[fila][columna]=piece;
                    System.out.println("Fantasma colocado en (" + fila+ ", " + columna+ ").");

                    validar=true;
                }else{
                    System.out.println("MOVIMIENTO INVALIDO | INTENTA OTRA POSISCION");
                }
            }
        }
    }
    
    private boolean validarMovimiento(int fila, int columna, int playerIndex){
        if (columna < 0 || columna>=6) 
            return false;
        if (playerIndex==0 && (fila < 0 || fila >=2)) 
            return false;
        if (playerIndex==1 && (fila < 4 || fila>=6)) 
            return false;
            
        return tablero[fila][columna] == '#';
    }
    


    
    public void mostrarTablero(){
        for (int i=0;i<tablero.length; i++) {
            for (int j =0; j<tablero[i].length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public void empezarJuego(){
        System.out.println("INICIANDO.....");
        if (mode.equalsIgnoreCase("MANUAL")) {
            System.out.println("Modo manual seleccionado");
            ponerPiezasManual(0);
            ponerPiezasManual(1);
        }else{
            System.out.println("Modo aleatorio seleccionado");
            ponerPiezasRandom(0);
            ponerPiezasRandom(1);
        }
        
        System.out.println("LISTO COMIENZEN !!!!!!!");
        
        int turno=0;
        boolean jugando=true;
        
        while(jugando){
            mostrarTablero();
            System.out.println("Turno del jugador "+(turno+1));
            System.out.println("Ingrese fila y la columna del fantasma que desea mover");
            System.out.println("Fila: ");
            int filaOG=lea.nextInt();
            System.out.println("Columna: ");
            int columnaOG=lea.nextInt();
            
            if(!validarSeleccion(filaOG, columnaOG, turno)){
                System.out.println("Seleccion invalida. Intentelo de nuevo");
                continue;
            }
            
            System.out.println("Ingrese la fila y la columna del movimiento: ");
            System.out.println("Fila: ");
            int filaMover=lea.nextInt();
            System.out.println("Columna: ");
            int columnaMover=lea.nextInt();
            
            if (validarMovimiento(filaMover, columnaMover, turno) && tablero[filaOG][columnaOG] != '#') {
                tablero[filaMover][columnaMover]=tablero[filaOG][columnaOG];
                tablero[filaOG][columnaOG]='#';
                System.out.println("MOVIMIENTO ECHO");
                mostrarTablero();
            }else{
                System.out.println("MOVIMIENTO INVALIDO | INTENTE OTRAS VEZ");
                mostrarTablero();
                continue;
            }
            if (validarVictoria(turno)) {
                System.out.println("PLAYER "+(turno+1)+" ha ganado!!!!");
                jugando=false;
            }else{
                 turno=1-turno;
            }
           
        }
    }
    
    private boolean validarSeleccion(int fila, int columna, int turno){
        if (fila<0 || fila>=6 || columna < 0 || columna >=6) 
            return false;
        char pieza=tablero[fila][columna];
        return pieza =='G' || pieza == 'M';
    }
    
    private boolean validarVictoria(int turno){
        int buenos=0, malos=0;
        for (char[] fila : tablero) {
            for (char cell : fila) {
                if (cell == 'G') 
                    buenos++;
                if (cell == 'M') 
                    malos++;
            }
        }
        if(buenos==0 || malos==0)
            return true;
        return tablero[0][0]=='G' || tablero[0][5]=='G' || tablero[5][0]=='G' || tablero[5][5]=='G';
    }
    
    public void eliminarUser(){
        for (int i = 0; i < contarPlayer; i++) {
            if (players[i] == actualUser) {
                
                for (int j = 0; j < contarPlayer-1; j++) {
                    players[j]=players[j+1];
                }
                players[contarPlayer-1]=null;
                contarPlayer--;
                actualUser=null;
                break;
            }
        }
    }
    
    public Player[] getPlayers(){
        return players;
    }
    
    public int getContarPlayer(){
        return contarPlayer; 
    }
}

