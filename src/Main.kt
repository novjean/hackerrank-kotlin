import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.NumberFormat
import java.time.LocalDate
import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("Lets go Europe!")
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
//    println(twoSum(intArrayOf(1,3,1,4), 2).contentToString())
//    lengthOfLongestSubstring("abccbacbb")
//    findMedianSortedArrays(intArrayOf(1,3), intArrayOf(1,2))
//    longestPalindrome("babad")
//    zigzagConversion("PAYPALISHIRING", 3)
//    reverseInteger(321)
    //		stdinStdout();
//		ifElse();
//		stdinStdout2();
//		outputFormatting();
//		loops1();
//		loops2();
//		datatypes();
//		endOfFile();

//		IntroductionStaticInitBlock initBlock = new IntroductionStaticInitBlock();
//		initBlock.staticInitializerBlock();

//		intToString();
//		dateAndTime();
//		currencyFormatter();

    // Strings
//		stringsIntro();

    // Data structures
//		OneDArray();
//		TwoDArray();
//		subArrayFindNegative();
//        arraylistSearch();

    // problem list
//        addTwoNumbers();
//        twoSum(new int[]{1,2,1,3}, 2);
//        lengthOfLongestSubstring("abcaabcc");
//        findMedianSortedArrays(new int[]{1,3}, new int[] {2});
//        longestPalindromicSubstring("bb");
//        longestPalindrome("babad");
//        zigzagConversion("PAYPALISHIRING",3);
//        reverseInteger(-123);
//        reverseInteger2(123);
//        stringToInteger(" -123")
//    isNumberPalindrome(12321)
    maxArea(intArrayOf(1,8,6,2,5,4,8,3,7))
}

// https://leetcode.com/problems/container-with-most-water/description/
fun maxArea(heights: IntArray): Int {
    var left = 0
    var right = heights.size-1
    var max = 0

    while(left<right){
        val width = right-left
        val height = Math.min(heights[left], heights[right])
        val area = width * height
        max = Math.max(max, area)

        if(heights[left]<heights[right]){
            left++
        } else if(heights[right]<heights[left]){
            right--
        }else {
            left++
            right--
        }
    }
    return max
}

// https://leetcode.com/problems/palindrome-number/
fun isNumberPalindrome(x: Int): Boolean {
    var num = x
    var reverse = 0

    while(x>0){
        val last = num%10
        reverse += reverse*10 +last

        num= num/10
    }
    return reverse==x
}

// https://leetcode.com/problems/string-to-integer-atoi/
fun stringToInteger(str: String): Int {
    var s:String = str.trim()
    var num:Long = 0
    var nums = StringBuilder()
    var isNeg = false


    for(i in 0..s.length-1){
        if(i==0){
            if(s[i] == '-'){
                isNeg = true;
            } else if(s[i] == '+'){
                isNeg = false;
            } else {
                if(s[i].isDigit()){
                    nums.append(s[i])
                } else {
                    break
                }
            }
        } else {
            if(s[i].isDigit()){
                nums.append(s[i])
            } else {
                break
            }
        }
    }

    if(nums.isNotEmpty()){
        try{
            num = nums.toString().toLong()
        } catch(e:NumberFormatException){
            if(isNeg){
                return Integer.MIN_VALUE
            } else {
                return Integer.MAX_VALUE
            }
        }
    } else {
        return 0
    }

    if(isNeg){
        num = num *-1
    }

    if(num>Integer.MAX_VALUE){
        return Integer.MAX_VALUE
    }
    if(num<Integer.MIN_VALUE){
        return Integer.MIN_VALUE
    }

    println("string to int: $num")
    return num.toInt()
}


///////////////////////////

// https://leetcode.com/problems/reverse-integer/
fun reverseInteger(num: Int):Int{
    var reversed = 0
    val sign = if (num < 0) -1 else 1
    var temp = num
    if (num < 0){
        temp = num * -1
    }

    while(temp>0){
        val last = temp%10
        temp = temp/10

        if(reversed>(Integer.MAX_VALUE-last)/10){
            return 0
        }
        reversed = reversed*10 + last
    }
    val result = reversed*sign
    println("reversed is $result")
    return result
}

fun reverseInteger2(x: Int) : Int{
    val value = if(x<0) (x*-1).toString() else x.toString()
    var reverse = (if(x<0) "-" else "") + value.reversed()
    return reverse.toIntOrNull()?: 0
}

////////////////////

fun zigzagConversion(s: String, numRows: Int) : String {
    if(numRows==1 || numRows>=s.length){
        return s
    }

    var rowIndex = 0
    var direction = 1
    val rows: Array<MutableList<Char>> = Array(numRows) { ArrayList() }

    for(i in 0..numRows-1){
        rows[i] = ArrayList<Char>()
    }

    for(c in s.toCharArray()){
        rows[rowIndex].add(c)

        if(rowIndex == 0){
            direction = 1
        }
        if(rowIndex == numRows-1){
            direction = -1
        }
        rowIndex += direction
    }

    var result = ""

    for(row in rows){
        for(c in row){
            result += c
        }
    }
    println("zigzag is $result")
    return result
}

// https://leetcode.com/problems/longest-palindromic-substring/
fun longestPalindrome(s: String) : String {
    var result = ""

    if(s.length==1){
        return s
    }

    for(i in 0..s.length-1){
        for(j in i..s.length){
            val sub = s.substring(i,j)

            if(isPalindrome(sub)){
                if(sub.length>result.length){
                    result= sub
                }
            }
        }
    }

    println("longest palindrome: $result")
    return result
}

fun isPalindrome(sub: String): Boolean {
    var left = 0
    var right = sub.length-1

    while(left<right){
        if(sub[left]==sub[right]){
            left++
            right--
        } else {
            return false
        }
    }
    return true
}

// https://leetcode.com/problems/median-of-two-sorted-arrays/description/
fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
    val mergedArray = nums1.plus(nums2)
    mergedArray.sort()

    var median = 0.0
    val half = mergedArray.size / 2

    median = if (mergedArray.size % 2 == 0) {
        (mergedArray[half - 1] + mergedArray[half]) / 2.0
    } else {
        mergedArray[half].toDouble()
    }

    return median
}


fun lengthOfLongestSubstring(s: String) : Int {
    val set: MutableSet<Char> = HashSet()
    var result = 0
    var left = 0
    var right = 0

    while (right < s.length) {
        if (set.contains(s[right])) {
            set.remove(s[left])
            left++
        } else {
            set.add(s[right])
            right++
            if(set.size>result){
                result = set.size
            }
        }
    }

    println("longest substring size: " + result)
    return result
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
