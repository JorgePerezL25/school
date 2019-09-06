//Fiboniacci Recursivo
/*val n = 10

def fibonacci1(n:Int) : Int ={
if (n<2){
return n
}
else{
    return fibonacci1(n-1) + fibonacci1(n-2)
}
}  

println(fibonacci1(n))

*/

/*
//Algoritmo 2 Versión con fórmula explícita (6) (Complejidad O  

val n = 10
var phi=((1+math.sqrt(5))/2)
var j=((math.pow(phi,n)-math.pow((1-phi),n))/(math.sqrt(5)))


def fibonacci2(n:Double) : Double ={
if (n<2){
return n
}
else {

    return j
}
}
println(fibonacci2(n))
*/


//Algoritmo 3 Versión iterativa
//(Complejidad O ( n)
/*

def fibonacci3(n:Int):Int={
var n : Int = 6
var a = 0
var b = 1
var c = 0
var k = 0 


    for(k <- 1 to n) {
        
        c = b + a
        a = b
        b = c 
    }
     return a
}
println(fibonacci3(n))
*/





//Algoritmo 4 Versión iterativa 2 variables (Complejidad (O(n))

/*
def fibonacci4(n:Int):Int={
var n : Int = 10
var a = 0
var b = 1
var k = 0 


    for(k <- 1 to n) {
        b = b + a
        a = b - a        
    
        }
     return a
}
println(fibonacci4(a))

*/



//Algoritmo 5 Versión iterativa vector (Complejidad O(n))
/*

var n = 10
def fibonacci4(n:Int):Int={
    var arreglo = Array (n+2)
    var i : Int
    arreglo (0,0)
    arreglo (1,1)

    for (i <- 1 to 2 )



}
println(fibonacci4(a))
*/

/*
  def fib(n: Int): Int = {
  	val n = 10
    val f: Array[Int] = Array.ofDim[Int](n + 2)
    

    f(0) = 0
    f(1) = 1

    for (i <- 2 to n) {
      
      f(i) = f(i - 1) + f(i - 2) //{ i += 1; i - 1 }
    }
    f(n)
  }
  println(fib(8))
*/

/*
//ALGORITMO 6
def fib6 (n : Int) : Double =
{
    if (n <= 0)
    {
        return 0
    }
    var i = n-1
    var auxOne = 0.0
    var auxTwo = 1.0
    var ab = Array(auxTwo,auxOne)
    var cd = Array(auxOne,auxTwo)
    while (i>0)
    {
        if (i % 2 != 0)
        {
            auxOne = cd(1) * ab(1) + cd(0) * ab(0)
            auxTwo = cd(1) * (ab(1)+ab(0)) + cd(0)*ab(1)
            ab(0) = auxOne
            ab(1) = auxTwo 
        } 
        auxOne = (math.pow(cd(0),2)) + (math.pow(cd(1),2))
        auxTwo = cd(1)* (2*cd(0) + cd(1))
        cd(0) = auxOne
        cd(1) = auxTwo
        i = i/2
    }
    return (ab(0) + ab(1))
}

*/


