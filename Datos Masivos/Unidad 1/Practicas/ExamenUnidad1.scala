/*Maria juega balnocesto universitario y quiere ser profesional. Cada temprada ella mantiene un registro de su juego.
Tabula la cantidad de veces que rompe su record de temprada para la mayoria de los puntos y la menor cantidad de puntos en un juego. 
LOs puntos anotados en el primer juego establecen su record para la temporada, y ella comienza a contar desde alli.

Por Ejemplo, suponga que sus puntajes para la temporada estan representados en la matriz. Las puntuaciones estan en el mismo orden que los juegos jugados.
Ella tabularia sus resultados de la siguiente manera : scores=[12,24,10,24]

count 
game    score   minumum     maximum     min max
0       12      12          12          0   0
1       24      12          24          0   1
2       10      10          24          1   1
3       24      10          24          1   1

Teniendo en cuenta los puntajes de Maria para una temporada, encuentra e imprime el numero de veces que rompe sus records 
para la mayoria y la menor cantidad de puntos anotados durante la temporada

*/
/*

object Examen {

    def score(args : Array[String]) {
        var n : Int = 5
        var puntajes = List (25,14,40,20,29)
        var his : Int = puntajes
        var lows : Int = puntajes
        var hi : Int = 0
        var low : Int = 0

            for(k <- o to n)
            {
                if (puntajes[k] > his)
                {               
                    hi = puntajes[k]
                    hi += 1
                }

                if (puntajes[k] < low)
                {
                    hi = puntajes[k]
                    hi += 1
                }
                    return his 
                    return lows
                }
    }
}

*/
object Solution {

    def main(args: Array[String]) {
       
        var n = 4
        
        var score = new Array[Int](4);
        score(0) = 25
        score(1) = 24
        score(2) = 26
        score(3) = 27

        for(score_i <- 0 to n) {
           score(score_i)
        }
        var min = score(0)
        var minCount = 0;
        var max = score(0)
        var maxCount = 0;
        for (s <- score) {
            if(s<min) {
                min = s
                minCount+=1
            } else if(s>max) {
                max = s
                maxCount+=1
            }
        }
        println(s"$maxCount" + " " + s"$minCount")
    }
    
}

