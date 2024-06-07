import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.NumberFormat
import java.time.LocalDate
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
//    endOfFile()

//    val introBlock = IntroductionStaticInitBlock()
//    introBlock.staticInitializerBlock()

//    intToString()
//    findDay()
//    currencyFormatter()

    // strings
//    stringsBasics()

    // data structures
//    oneDArray()
//    twoDArray()
//    subArrayNegatives()

    // problem list
    println(twoSum(intArrayOf(1,3,1,4), 2).contentToString())

}

// https://leetcode.com/problems/two-sum/submissions/1280210991/
fun twoSum(nums: IntArray, target: Int) : IntArray? {
    for(i in nums.indices){
        for(j in i+1 until nums.size){
            if(nums[i] + nums[j] == target){
                return intArrayOf(i,j)
            }
        }
    }

    return null
}

fun subArrayNegatives() {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    val arr: MutableList<Int> = ArrayList()

    for(i in 0..n-1){
        arr.add(scanner.nextInt())
    }

    var negatives = 0

    for(i in 0..n-1){
        for(j in i..n-1){
            var currentSum = 0

            for(k in i..j){
                currentSum += arr.get(k)
            }

            if(currentSum<0){
                negatives++
            }
        }
    }

    println(negatives)
}

fun twoDArray() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val arr: MutableList<MutableList<Int>> = ArrayList()

    for (i in 0..5) {
        val arrRowTempItems =
            bufferedReader.readLine().replace("\\s+$".toRegex(), "").split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()

        val arrRowItems: MutableList<Int> = ArrayList()

        for (j in 0..5) {
            val arrItem = arrRowTempItems[j].toInt()
            arrRowItems.add(arrItem)
        }

        arr.add(arrRowItems)
    }

    bufferedReader.close()

    var maxSum = 0
    var maxI = 0
    var maxJ = 0

    for (i in 0..3) {
        for (j in 0..3) {
            val sum = arr[i][j] + arr[i][j + 1] + arr[i][j + 2] +
                    arr[i + 1][j + 1] +
                    arr[i + 2][j] + arr[i + 2][j + 1] + arr[i + 2][j + 2]

            if (sum > maxSum) {
                maxSum = sum
                maxI = i
                maxJ = j
            }
        }
    }

    println(maxSum)
}

fun oneDArray() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val a = IntArray(n)

    for(i in 0..n-1){
        a[i] = sc.nextInt()
    }

    for(i in 0..n-1){
        println(a[i])
    }
}

fun stringsBasics() {
    val scanner = Scanner(System.`in`)
    val a = scanner.next()
    val b = scanner.next()
    scanner.close()

    println(a.length + b.length)
    println(if (a.compareTo(b) > 0) "Yes" else "No")
    println("${a.substring(0,1).uppercase()}${a.substring(1)} ${b.substring(0,1).uppercase()}${b.substring(1)}")

}

fun currencyFormatter() {
    val scanner = Scanner(System.`in`)
    val payment = scanner.nextDouble()
    scanner.close()

    // Write your code here.
    val us = NumberFormat.getCurrencyInstance(Locale.US).format(payment)
    val india = NumberFormat.getCurrencyInstance(Locale("en","in")).format(payment)
    val china = NumberFormat.getCurrencyInstance(Locale.CHINA).format(payment)
    val france = NumberFormat.getCurrencyInstance(Locale.FRANCE).format(payment)

    System.out.println("US: " + us);
    System.out.println("India: " + india);
    System.out.println("China: " + china);
    System.out.println("France: " + france);
}

fun findDay() {
    val sc = Scanner(System.`in`)
    val month = sc.nextInt()
    val day = sc.nextInt()
    val year = sc.nextInt()

    val date = LocalDate.of(year, month, day)
    println(date.dayOfWeek.name)
}

fun intToString() {
    try{
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()
        sc.close()

        val s = n.toString()

        if(n == s.toInt()){
            println("Good job")
        } else {
            println("Wrong answer.")
        }
    } catch (e : Exception){
        println("Unsuccessful Termination!!")
    }
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
