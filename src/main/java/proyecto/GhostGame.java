
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
                tablero[i][j] ='E';
            }
        }
        tablero[0][0] = tablero[0][5] = tablero[5][0] = tablero[5][5] = 'B';
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
                break;
            case 2:
                dificultad=4;
                break;
            case 3:
                dificultad=2;
                break;
        }
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
            
            char piece = esBueno ? 'G' : 'R';
            
            while(true){
                int fila=playerIndex== 0 ? random.nextInt(2) : 4 + random.nextInt(2);
                int columna=random.nextInt(6);
                
                if(tablero[fila][columna] == 'E'){
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
            char piece=(i < contarBueno) ? 'G' : 'R';
            
            while(true){
                System.out.println("Jugador"+(playerIndex+1)+", coloca un fantasma"+(piece=='G' ? "bueno" : "malo")+":");
                System.out.println("Fila: ");
                int fila=lea.nextInt();
                
                System.out.println("Columna: ");
                int columna=lea.nextInt();
                
                if (validarMovimiento(fila,columna,playerIndex)) {
                    tablero[fila][columna]=piece;
                    break;
                }else{
                    System.out.println("MOVIMIENTO INVALIDO | INTENTA OTRA POSISCION");
                }
            }
        }
    }
    
    private boolean validarMovimiento(int fila, int columna, int playerIndex){
        if (playerIndex==0 && (fila < 0 || fila >=2)) 
            return false;
        if (playerIndex==1 && (fila < 4 || fila>=6)) 
            return false;
        if (columna < 0 || columna >= 6 || tablero[fila][columna] !='E')
            return false;
        return true;
    }
    


    
    public void mostrarTablero(){
        for (char[] fila : tablero) {
            for (char celda : fila) {
                System.out.print(celda+" ");
            }
            System.out.println();
        }
    }
    
    public void empezarJuego(){
        System.out.println("INICIANDO.....");
        mostrarTablero();
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

