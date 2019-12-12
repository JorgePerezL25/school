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
