object Examen {

    def main(args: Array[String]) {
        val ingresa = new java.util.Scanner (System.in);
        var n = ingresa.nextInt();
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
        println(contadormax + " " + contadormin)
    }
}
