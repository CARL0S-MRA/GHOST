
package proyecto;

public class Player {
    private String username;
    private String password;
    private int puntos;
    private String[] logs;
    private int logIndex;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntos = 0;
        this.logs = new String[10];
        this.logIndex = 0;
    }

    public String getUsername() {
        return username;
    }

    public boolean verificarPassword(String password) {
        return this.password.equals(password);
    }

    public void actualizarPassword(String newPassword) {
        this.password = newPassword;
    }

    public void addLog(String log) {
        logs[logIndex] = log;
        logIndex=(logIndex+1)%10;
    }

    public void addPuntos(int points) {
        this.puntos += points;
    }

    public int getPuntos() {
        return puntos;
    }

    public String[] getLogs() {
        return logs;
    }
}

