# Examenes de la Unidad 1

# Contenido
<!--ts-->
   * [Practicas](#practicas-unidad-3)
      * [Examen 1 ](#examen-1)
      * [Examen 2 ](#examen-2)
   * [Contenido](#Contenido)
<!--te-->

--------------------------------------------------------------------------------------------------------------------------------
## Examen 1

##### Maria juega balnocesto universitario y quiere ser profesional. Cada temprada ella mantiene un registro de su juego. Tabula la cantidad de veces que rompe su record de temprada para la mayoria de los puntos y la menor cantidad de puntos en un juego. Los puntos anotados en el primer juego establecen su record para la temporada, y ella comienza a contar desde alli. Por Ejemplo, suponga que sus puntajes para la temporada estan representados en la matriz. Las puntuaciones estan en el mismo orden que los juegos jugados. Ella tabularia sus resultados de la siguiente manera : scores=[12,24,10,24]
count 
##### game    score   minumum     maximum     min max
##### 0       12      12          12          0   0
##### 1       24      12          24          0   1
##### 2       10      10          24          1   1
##### 3       24      10          24          1   1
##### Teniendo en cuenta los puntajes de Maria para una temporada, encuentra e imprime el numero de veces que rompe sus records para la mayoria y la menor cantidad de puntos anotados durante la temporada


```scala 
object Solution {

    def main(args: Array[String]) {
     
        var n = 10
        
        var score = new Array[Int](4);
        score(0) = 3
        score(1) = 4
        score(2) = 21
        score(3) = 36
	score(4) = 10
	score(5) = 28
        score(6) = 35
        score(7) = 5
        score(8) = 24
	score(9) = 42   

        for(score_i <- 0 to n-1) {
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
    }
}
println(maxCount + " " + minCount)

```

--------------------------------------------------------------------------------------------------------------------------------
## Examen 2

```scala 
//1.-Comienza una simple session de Spark


//2.-Cargue el archivo NEtflix Stock CSV, haga que Spark infiera los tipos de datos.
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")

//3.-Cuales son los nombres de las columnas?
df.columns

//4.-Como es el esquema?
df.printSchema()

//5.-Imprime las primeras 5 filas
df.show(5)

//6.-Usa descrbie () para aprender sobre el dataframe
df.describe().show()

//7.-Crear un nuevo dataframe con una columna nueva llamada "HV Ratio" que es la relacion entre el precio de la columma 
//"High" frente a la columna "volumen" de acciones negociadas por un dia
val df2 = df.withColumn("hv Ratio",df("High")/df("Volume"))
df2.select("hv Ratio").show()

//8.-que dia tuvo el pico mas alto en la columna "Price"?
//NO HAY NO EXISTE


//9.-cual es el significado de la columna cerrar "close"?
//el como cerro el precio del dia 

//10.-Cual es el maximo y minimo de la columna "volumen"
df.select(max(df("Volume"))).show()
df.select(min(df("Volume"))).show()

//11 con sintaxis scala/spark $ constete los siguiente:
//a. cuantos dias fue la columna "close" unferior a $600
//df.orderBy($"Close"< 600).show()
//df.select($"Close"< 600).show()
//df.filter($"Close"< 600).show()
df.filter($"Close"< 600).count()

//b.-que porcentaje del tiempo fue la columna "high" mayor $500
val num = df.filter($"High">500).count()
val por = (num * 1.0 / df.count() * 100)
//por.show()

//c.-cual es la correlacion de pearson entre columna "High" y la columna "volumen"?
val corre = df.select(corr($"Volume",$"High"))
corre.show()

//d.cual es el maximo de la columna "high" por anio
val yearm = df.withColumn("Year",year(df("Date")))
val yearmax = yearm.select($"Year", $"High").groupBy("Year").max()
val res = yearmax.select($"Year", $"max(High)")
res.show()

//e.cual es el prmedio de la columa "close" para cada mes del calendario
val monthp = df.withColumn("Month",month(df("Date")))
val monthprom = monthp.select($"Month",$"Close").groupBy("Month").mean()
monthprom.select($"Month",$"avg(Close)").show()

```

