import java.util.Scanner

class IntroductionStaticInitBlock {
    var b = 0
    var h = 0
    var flag = false

    init{
        val sc = Scanner(System.`in`)

        b = sc.nextInt()
        h = sc.nextInt()

        if(b<=0 || h<=0){
            println("Exception: Breadth and height must be positive")
            flag = false
        } else {
            flag = true
        }
    }

    fun staticInitializerBlock() {
        if(flag){
            val area = b*h
            print(area)
        }
    }
}