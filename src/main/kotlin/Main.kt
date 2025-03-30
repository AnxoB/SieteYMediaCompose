import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication {
    SieteYMediaApp()
}

@Composable
fun SieteYMediaApp() {
    var sieteYMedia by remember { mutableStateOf(SieteYMedia()) }
    var jugadorCartas by remember { mutableStateOf(sieteYMedia.getCartasJugador().filterNotNull().toList()) }
    var bancaCartas by remember { mutableStateOf(sieteYMedia.getCartasBanca().filterNotNull().toList()) }
    var jugadorPuntuacion by remember { mutableStateOf(sieteYMedia.valorCartas(sieteYMedia.getCartasJugador())) }
    var bancaPuntuacion by remember { mutableStateOf(sieteYMedia.valorCartas(sieteYMedia.getCartasBanca())) }
    var mensaje by remember { mutableStateOf("") }
    var juegoFinalizado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Jugador", style = MaterialTheme.typography.h5)
        LazyColumn {
            items(jugadorCartas) { carta ->
                Text("${carta.numero} de ${carta.palo}")
            }
        }
        Text("Puntuación: $jugadorPuntuacion")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            sieteYMedia.turnoJugador()
            jugadorCartas = sieteYMedia.getCartasJugador().filterNotNull().toList()
            jugadorPuntuacion = sieteYMedia.valorCartas(sieteYMedia.getCartasJugador())

            if (sieteYMedia.jugadorSePaso()) {
                mensaje = "El jugador se ha pasado. ¡Gana la banca!"
                juegoFinalizado=true
            }
        }, enabled = !juegoFinalizado) {
            Text("Pedir")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            sieteYMedia.turnoBanca()
            bancaCartas = sieteYMedia.getCartasBanca().filterNotNull().toList()
            bancaPuntuacion = sieteYMedia.valorCartas(sieteYMedia.getCartasBanca())

            mensaje = when {
                sieteYMedia.bancaSePaso() -> "La banca se ha pasado. ¡Gana el jugador!"
                bancaPuntuacion >= jugadorPuntuacion -> "¡Gana la banca!"
                else -> "¡Gana el jugador!"
            }
            juegoFinalizado = true
        }, enabled = !juegoFinalizado) {
            Text("Plantarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(mensaje, style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Banca", style = MaterialTheme.typography.h5)
        LazyColumn {
            items(bancaCartas) { carta ->
                Text("${carta.numero} de ${carta.palo}")
            }
        }
        Text("Puntuación: $bancaPuntuacion")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            sieteYMedia = SieteYMedia()
            jugadorCartas = sieteYMedia.getCartasJugador().filterNotNull().toList()
            bancaCartas = sieteYMedia.getCartasBanca().filterNotNull().toList()
            jugadorPuntuacion = sieteYMedia.valorCartas(sieteYMedia.getCartasJugador())
            bancaPuntuacion = sieteYMedia.valorCartas(sieteYMedia.getCartasBanca())
            mensaje = ""
            juegoFinalizado = false
        }) {
            Text("Reiniciar")
        }
    }
}