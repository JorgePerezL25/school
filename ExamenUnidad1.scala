object Examen {

    def main(args: Array[String]) {
        val ingresa = new java.util.Scanner (System.in);
	println("Ingrese numero de juegos juegados:")
        var n = ingresa.nextInt();
println("Ingrese puntaje por juego, separado por espacios")
        var puntos = new Array[Int](n);
        for(i <- 0 to n-1) {
		puntos(i) = ingresa.nextInt();
        }
        var puntosmin = puntos(0)
        var contadormin = 0;
        var puntosmax = puntos(0)
        var contadormax = 0;
        for (s <- puntos) {
            if(s<puntosmin) {
                puntosmin = s
                contadormin+=1
            } else if(s>puntosmax) {
                puntosmax = s
                contadormax+=1
            }
        }
        println("\n"+"Su juego con mas puntos fue:"+contadormax + "\n" +"Su juego con menos puntos fue:"+ contadormin)
    }
}
