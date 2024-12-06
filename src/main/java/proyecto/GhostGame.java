
package proyecto;
import java.util.Random;
import java.util.Scanner;

public class GhostGame {
    Random random = new Random();
    Scanner lea = new Scanner(System.in);

    private static final int MAXPLAYERS = 20;
    private Player[] players;
    private int contarPlayer;
    private Player actualUser;
    private char[][] tablero;
    private char[][] pieza; 
    private String mode;
    private int dificultad;

    public GhostGame() {
        players = new Player[MAXPLAYERS];
        contarPlayer = 0;
        actualUser = null;
        tablero = new char[6][6];
        pieza = new char[2][8];
        mode = "ALEATORIO";
        dificultad = 8; // Default NORMAL
        iniciarTablero();
    }

    public void iniciarTablero() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tablero[i][j] = '#';
            }
        }
        tablero[0][0] = tablero[0][5] = tablero[5][0] = tablero[5][5] = 'S';
    }

    public boolean registrarPlayer(String username, String password) {
        if (contarPlayer >= MAXPLAYERS)
            return false;
        
        for (int i = 0; i < contarPlayer; i++) {
            if (players[i].getUsername().equals(username))
                return false;
        }
        if (password.length() !=8) {
            System.out.println(" ERROR | La password debe ser de 8 caracteres");
            return false;
        }
        players[contarPlayer++] = new Player(username, password);
        return true;
    }

    public boolean login(String username, String password) {
        for (int i = 0; i < contarPlayer; i++) {
            if (players[i].getUsername().equals(username) && players[i].verificarPassword(password)) {
                actualUser = players[i];
                return true;
            }
        }
        return false;
    }

    public Player getActualUser() {
        return actualUser;
    }

    public boolean existePlayer(String username) {
        for (int i = 0; i < contarPlayer; i++) {
            if (players[i].getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void configurarDificultad(int nivelDificultad) {
        switch (nivelDificultad) {
            case 1: // NORMAL
                dificultad = 8;
                System.out.println("Dificultad NORMAL seleccionada: 8 fantasmas por jugador (4 buenos y 4 malos).");
                break;
            case 2: // EXPERT
                dificultad = 4;
                System.out.println("Dificultad EXPERT seleccionada: 4 fantasmas por jugador (2 buenos y 2 malos).");
                break;
            case 3: // GENIUS
                dificultad = 6;
                System.out.println("Dificultad GENIUS seleccionada: 2 fantasmas buenos y 4 trampas por jugador.");
                break;
            default:
                System.out.println("OPCIÓN INVALIDA.");
        }
        System.out.println("LA DIFICULTAD SE HA ACTUALIZADO.");
    }

    public void configurarMode(String nuevoMode) {
        if (nuevoMode.equalsIgnoreCase("ALEATORIO") || nuevoMode.equalsIgnoreCase("MANUAL")) {
            mode = nuevoMode;
        }
    }

    public String getMode() {
        return mode;
    }

    public void ponerPiezasRandom(int playerIndex) {
        int ghostBueno = (dificultad > 4) ? 2 : dificultad / 2;
        int ghostMalo = (dificultad > 4) ? 2 : dificultad / 2;
        int trampas = (dificultad == 6) ? 4 : 0;

        for (int i = 0; i < ghostBueno + ghostMalo + trampas; i++) {
            char piece;
            if (i < ghostBueno) {
                piece = 'G'; // Fantasma bueno
            } else if (i < ghostBueno + ghostMalo) {
                piece = 'M'; // Fantasma malo
            } else {
                piece = 'T'; // Trampa
            }

            while (true) {
                int fila = playerIndex == 0 ? random.nextInt(2) : 4 + random.nextInt(2);
                int columna = random.nextInt(6);

                if (tablero[fila][columna] == '#') {
                    tablero[fila][columna] = piece;
                    break;
                }
            }
        }
    }

    public void ponerPiezasManual(int playerIndex) {
        int ghostBueno = (dificultad > 4) ? 2 : dificultad / 2;
        int ghostMalo = (dificultad > 4) ? 2 : dificultad / 2;
        int trampas = (dificultad == 6) ? 4 : 0;

        for (int i = 0; i < ghostBueno+ ghostMalo+ trampas; i++) {
            char piece;
            if (i < ghostBueno) {
                piece = 'G';
            } else if (i < ghostBueno + ghostMalo) {
                piece = 'M';
            } else {
                piece = 'T';
            }

            boolean validar = false;
            while (!validar) {
                System.out.println("Jugador " + (playerIndex + 1) + ", coloca un " +
                        (piece == 'G' ? "fantasma bueno" : (piece == 'M' ? "fantasma malo" : "trampa")) + ":");
                System.out.println("Fila (-1 para retirar): ");
                int fila = lea.nextInt();
                System.out.println("Columna (-1 para retirar): ");
                int columna = lea.nextInt();

                if (fila == -1 && columna == -1) {
                    System.out.println("Seguro que deseas retirarte? (s | n)");
                    char confirmar = lea.next().toLowerCase().charAt(0);
                    if (confirmar == 's') {
                        int otroJugador = (playerIndex == 0) ? 1 : 0;
                        System.out.println("Jugador " + (playerIndex + 1) + " se ha retirado. ¡Jugador " + (otroJugador + 1) + " ha ganado automáticamente!");
                        return;
                    } else {
                        System.out.println("Continúa con tu movimiento.");
                        continue;
                    }
                }

                if (validarMovimiento(fila, columna, fila, columna)) {
                    tablero[fila][columna] = piece;
                    System.out.println("Pieza colocada en (" + fila + ", " + columna + ").");
                    validar = true;
                } else {
                    System.out.println("Movimiento inválido. Intenta otra posición.");
                }
            }
        }
    }

    private boolean validarMovimiento(int filaMover, int columnaMover,  int filaOG, int columnaOG) {
        if (filaMover<0 || filaMover >=6 || columnaMover< 0 || columnaMover>=6) 
            return false;
        if (tablero[filaMover][columnaMover] != ' ')
            return false;
            
           int distanciaFila = (filaMover> filaOG) ? (filaMover - filaOG) : (filaOG - filaMover);
           
           int distanciaColumna=(columnaMover>columnaOG) ? (columnaMover - columnaOG) : (columnaOG - columnaMover);
           if (distanciaFila > 1 || distanciaColumna >1) 
            return false;
        
        return true;
    }

    public void mostrarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void eliminarUser() {
        for (int i = 0; i < contarPlayer; i++) {
            if (players[i] == actualUser) {
                for (int j = i; j < contarPlayer - 1; j++) {
                    players[j] = players[j + 1];
                }
                players[contarPlayer - 1] = null;
                contarPlayer--;
                actualUser = null;
                break;
            }
        }
    }
    public void empezarJuego() {
    System.out.println("INICIANDO.....");

    if (mode.equalsIgnoreCase("MANUAL")) {
        System.out.println("Modo manual seleccionado");
        ponerPiezasManual(0);
        ponerPiezasManual(1);
    } else {
        System.out.println("Modo aleatorio seleccionado");
        ponerPiezasRandom(0);
        ponerPiezasRandom(1);
    }

    System.out.println("LISTO COMIENZEN !!!");

    int turno = 0;
    boolean jugando = true;

    while (jugando) {
        mostrarTablero();
        System.out.println("Turno del jugador " + (turno + 1));
        System.out.println("Seleccione el fantasma que desea mover:");
        System.out.print("Fila de origen: ");
        int filaOG = lea.nextInt();
        System.out.print("Columna de origen: ");
        int columnaOG = lea.nextInt();

        if (!validarSeleccion(filaOG, columnaOG, turno)) {
            System.out.println("Selección inválida. Inténtelo nuevamente.");
            continue;
        }

        System.out.println("Ingrese la fila y la columna del movimiento:");
        System.out.print("Fila destino: ");
        int filaMover = lea.nextInt();
        System.out.print("Columna destino: ");
        int columnaMover = lea.nextInt();

        if (validarMovimiento(filaMover, columnaMover, filaOG, columnaOG)) {
            tablero[filaMover][columnaMover] = tablero[filaOG][columnaOG];
            tablero[filaOG][columnaOG] = '#';
            System.out.println("Movimiento realizado.");
        } else {
            System.out.println("Movimiento invalido. Intentelo nuevamente.");
            continue;
        }

        if (validarVictoria(turno)) {
            System.out.println("¡Jugador " + (turno + 1) + " ha ganado!");
            jugando = false;
        } else {
            turno = 1 - turno; // Cambia el turno.
        }
    }
}

     private boolean validarSeleccion(int fila, int columna, int turno){
        if (fila<0 || fila>=6 || columna < 0 || columna >=6) 
            return false;
        char pieza=tablero[fila][columna];
         if (pieza == 'T') 
             return false;
         
         if (turno == 0 && fila >=0 && fila < 2) 
             return pieza == 'G' || pieza =='M';
         
         if(turno== 1 && fila >= 4 && fila<6)
             return pieza=='G' || pieza == 'M';
         
         return false;
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
        if(buenos==0 || malos==0){
            System.out.println("VICTORIA todo los fantasmas fueron capturados");
            return true;
         }
            if (tablero[0][0]=='G' || tablero[0][5]=='G' || tablero[5][0]=='G' || tablero[5][5]=='G') {
                    System.out.println("VICTORIA | un fantasma bueno llego a la salida");
                    return true;
            }
                 return false;
    }


    public Player[] getPlayers() {
        return players;
    }

    public int getContarPlayer() {
        return contarPlayer;
    }
}
