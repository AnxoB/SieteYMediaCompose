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
    val sieteYMedia = remember { SieteYMedia() }
    var jugadorCartas by remember { mutableStateOf(sieteYMedia.getCartasJugador().toList()) }
    var bancaCartas by remember { mutableStateOf(sieteYMedia.getCartasBanca().toList()) }
    var jugadorPuntuacion by remember { mutableStateOf(0.0) }
    var bancaPuntuacion by remember { mutableStateOf(0.0) }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Jugador", style = MaterialTheme.typography.h5)
        LazyColumn {
            items(jugadorCartas) { carta ->
                if (carta != null) {
                    Text("${carta.numero} de ${carta.palo}")
                }
            }
        }
        Text("Puntuación: $jugadorPuntuacion")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            sieteYMedia.turnoJugador()
            jugadorCartas = sieteYMedia.getCartasJugador().toList()
            jugadorPuntuacion = sieteYMedia.valorCartas(sieteYMedia.getCartasJugador())

            if (sieteYMedia.jugadorSePaso()) {
                mensaje = "El jugador se ha pasado. ¡Gana la banca!"
            }
        }) {
            Text("Pedir")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            sieteYMedia.turnoBanca()
            bancaCartas = sieteYMedia.getCartasBanca().toList()
            bancaPuntuacion = sieteYMedia.valorCartas(sieteYMedia.getCartasBanca())

            mensaje = when {
                sieteYMedia.bancaSePaso() -> "La banca se ha pasado. ¡Gana el jugador!"
                bancaPuntuacion > jugadorPuntuacion -> "¡Gana la banca!"
                else -> "¡Gana el jugador!"
            }
        }) {
            Text("Plantarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(mensaje, style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Banca", style = MaterialTheme.typography.h5)
        LazyColumn {
            items(bancaCartas) { carta ->
                if (carta != null) {
                    Text("${carta.numero} de ${carta.palo}")
                }
            }
        }
        Text("Puntuación: $bancaPuntuacion")
    }
}