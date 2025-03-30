import recursos.Baraja;
import recursos.Carta;


public class SieteYMedia {

    Baraja baraja;
    Carta[] cartasJugador;
    Carta[] cartasBanca;

    SieteYMedia(){
        baraja=new Baraja();
        baraja.barajar();
        cartasJugador = new Carta[15];
        cartasBanca = new Carta[15];
    }


    void turnoJugador() {
        Carta c = baraja.darCartas(1)[0];
        insertarCartaEnArray(cartasJugador, c);
    }

    public boolean jugadorSePaso() {
        return valorCartas(cartasJugador) > 7.5;
    }

    void turnoBanca() {
        double valorCartasJugador = valorCartas(cartasJugador);
        if (valorCartasJugador > 7.5) {
            return;
        }

        while (valorCartas(cartasBanca) < valorCartasJugador) {
            Carta c = baraja.darCartas(1)[0];
            insertarCartaEnArray(cartasBanca, c);
        }
    }


    public boolean bancaSePaso() {
        return valorCartas(cartasBanca) > 7.5;
    }



    double valorCartas(Carta[] cartas) {
        double total = 0.0;
        int val;
        int i = 0;
        while (cartas[i] != null) {
            val = cartas[i].getNumero();
            total += (val > 7) ? 0.5 : val;
            i++;
        }

        return total;
    }

    void insertarCartaEnArray(Carta[] cartas, Carta c) {
        // inserta al final detectando el primer null
        int i = 0;
        while (cartas[i] != null) {
            i++;
        }
        cartas[i] = c;

    }

    public Carta[] getCartasJugador() {
        return cartasJugador;
    }

    public Carta[] getCartasBanca() {
        return cartasBanca;
    }

}
