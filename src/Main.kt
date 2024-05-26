import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("HackerRank. lets do this!")
//    stdinStdout2()
//    outputFormatting()
//    loops1()
//    loops2()
//    datatypes()
    endOfFile()
}

fun endOfFile() {
    val sc = Scanner(System.`in`)
    var i = 1

    while (sc.hasNext()){
        println("$i ${sc.nextLine()}")
        i++
    }
    sc.close()
}

fun datatypes() {
    val sc = Scanner(System.`in`)
    val t = sc.nextInt()

    for(i in 1..t){
        try{
            val x = sc.nextLong()
            println("$x can be fitted in:");

            if(x>=Byte.MIN_VALUE && x<=Byte.MAX_VALUE){
                println("* byte");
            }
            if(x>=Short.MIN_VALUE && x<=Short.MAX_VALUE){
                println("* short");
            }
            if(x>=Integer.MIN_VALUE && x<=Integer.MAX_VALUE){
                println("* int");
            }
            if(x>=Long.MIN_VALUE && x<=Long.MAX_VALUE){
                println("* long");
            }
        } catch(e: Exception) {
            println("${sc.next()} can't be fitted anywhere.")
        }
    }
    sc.close()
}

fun loops2() {
    val sc = Scanner(System.`in`)

    val t = sc.nextInt()

    for(i in 1..t){
        val a = sc.nextInt()
        val b = sc.nextInt()
        val n = sc.nextInt()

        var sum = a
        for(j in 0..n-1){
            sum += (Math.pow(2.0, j.toDouble())).toInt() * b
            print("$sum ")
        }
        println()
    }
}

fun loops1(){
    val sc = Scanner(System.`in`)

    val n = sc.nextInt()

    for(i in 1..10){
        println("$n x $i = ${n*i}")
    }

}

fun outputFormatting() {
    val sc = Scanner(System.`in`)

    for (i in 1..3){
        val s1 = sc.next()
        val x = sc.nextInt()

        println(String.format("%-15s%03d", s1, x))
    }
}

fun stdinStdout2() {
    val scanner = Scanner(System.`in`)
    val i = scanner.nextInt()
    val d = scanner.nextDouble()
    scanner.nextLine()
    val s = scanner.nextLine()

    println("String: " + s)
    println("Double: " + d)
    println("Int: " + i)

}
