object Solution {

    def main(args: Array[String]) {
        val sc = new java.util.Scanner (System.in);
        var n = sc.nextInt();
        var score = new Array[Int](n);
        for(i <- 0 to n-1) {
           score(i) = sc.nextInt();
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
        println(maxCount + " " + minCount)
    }
}
