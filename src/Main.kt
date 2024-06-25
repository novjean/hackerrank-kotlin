import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.NumberFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

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
//    maxArea(intArrayOf(1,8,6,2,5,4,8,3,7))
//    integerToRoman(3749)
//    threeSum(intArrayOf(1,8,6,2,5,4,8,3,7))
//    firstOccurenceString("sadbutsad","sad")
//    mergeTwoLists()
//    removeNthFromEnd()
//    romanToInt
//    longestCommonPrefix
//    threeSumClosest
//    fourSum(intArrayOf(-2,2,1,0,1,-1,2), 0)
//    generateParenthesis(3)
//        swapPairs()
//    nextPermutation(intArrayOf(1,2,3))
//    searchRange(intArrayOf(5,7,7,8,8,10), 8)
//    searchInsert(intArrayOf(3,4,5,9), 8)
//    isValidSudoku()
//    countAndSay(4)
//    combinationSum(intArrayOf(2,3,6,7), 7)
//    solveSudoku()
//    longestSubarray(intArrayOf(8, 2, 4, 7), 4)
//    majorityElement(intArrayOf(3,2,3))
//    rotate(intArrayOf(-1), 2)
//    isPalindrome2("A man, a plan, a canal: Panama")
//    minKBitFlips(intArrayOf(1,0,1),1)
//    maxProfit(intArrayOf(2,4,1))
//    maxProfit2(intArrayOf(2,4,1))
//    canJump(intArrayOf(1))
//    canConstructRansomNote("aa", "aab")
//    simplifyPath("/home/user/Documents/../Pictures")
//    addBinary("11", "1")
//    bstToGst()
//    evalRPN(listOf("3","11","+","5","-").toTypedArray())
//    wordPattern("abba", "dog cat cat dog")
//    canCompleteCircuit()
//    letterCombinations("23")
//    combine(5, 3)
//    twoSum2(intArrayOf(1,2,3,4), 3)
//    minSubArrayLen(2, intArrayOf(1,2,3,4,5))
}

// https://leetcode.com/problems/minimum-size-subarray-sum
// time O(n), the inner while loop does not make O(n2)
// since each element is added and removed once,
// so amortized operation O(n)
// space O(1)
fun minSubArrayLen(target: Int, nums: IntArray) : Int {
    var l=0
    var res = Int.MAX_VALUE
    var s =0

    for(r in nums.indices){
        s += nums[r]

        while(s>=target){
            res = minOf(res, r-l+1)
            s -= nums[l]
            l++
        }
    }
    return if(res!=Int.MAX_VALUE) res else 0
}

// https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
// time O(n)
// space O(1)
fun twoSum2(nums: IntArray, target: Int): IntArray {
    var i =0
    var j = nums.size -1

    while(j>i){
        val sum = nums[i] + nums[j]
        if(sum==target){
            return intArrayOf(i+1,j+1)
        } else if(sum>target){
            j--
        } else {
            i++
        }
    }
    return intArrayOf(0,0)
}

// https://leetcode.com/problems/is-subsequence/
// time O(n)
// space O(1)
fun isSubsequence(s: String, t: String): Boolean {
    var i = 0
    t.forEach {
        if(i<s.length && it == s[i]) i++
    }
    return i == s.length
}

fun isSubsequence2(s: String, t: String): Boolean {
    var i = 0
    var j = 0

    while(i<s.length && j<t.length){
        if(s[i] == t[j++]){
            i++
        }
    }
    return i == s.length
}

// https://leetcode.com/problems/insert-delete-getrandom-o1/
class RandomizedSet() {
    private val map: MutableMap<Int, Int> = hashMapOf()
    private val arr: MutableList<Int> = mutableListOf()

    fun insert(value: Int): Boolean {
        if(!map.containsValue(value)){
            arr.add(value)
            map[value] = arr.size-1
            return true
        } else
            return false
    }

    fun remove(value: Int): Boolean {
        if(map.containsValue(value)){
            val index = map[value] ?: 0
            val lastVal = arr.last()

            arr[index] = lastVal
            arr.removeAt(arr.lastIndex)

            map[lastVal] = index
            map.remove(index)

            return true
        }
        return false
    }

    fun getRandom(): Int {
        val randomIndex = (0..arr.lastIndex).random()
        return arr[randomIndex]
    }

}

//
fun combine(n: Int, k: Int): List<List<Int>> {
    var combination = (1..k).map{ it }.toIntArray()
    var result = mutableListOf<List<Int>>()

    do{
        result.add(combination.toList())
    } while(nextCombination(combination, n, k))
    return result
}

fun nextCombination(combination: IntArray, n: Int, k:Int) : Boolean{
    var index = k-1

    // comparing to ensure that we don't enter the ind
    while(index>=0 && combination[index] == n-k+1+index){
        index--
    }
    if(index == -1){
        return false
    }
    combination[index]++

    for(j in index+1 until k ) {
        combination[j] = combination[j-1] + 1
    }
    return true
}

// https://leetcode.com/problems/letter-combinations-of-a-phone-number/
// time O(m^k), where m is the avg of length of the letters in a button
// space O(m^k), k is the length of the digits string
fun letterCombinations(digits: String) : List<String> {
    val map = mapOf(
        "1" to "",
        "2" to "abc",
        "3" to "def",
        "4" to "ghi",
        "5" to "jkl",
        "6" to "mno",
        "7" to "pqrs",
        "8" to "tuv",
        "9" to "wxyz",
        "0" to "",
    )

    var combinations : MutableList<String> = mutableListOf()

    for(i in 0 until digits.length){
        val digit = digits[i]
        var letters = map.get(digit.toString())

        if(combinations.isEmpty()){
            letters!!.forEach { combinations.add(it.toString()) }
        } else {
            var tempComb : MutableList<String> = mutableListOf()
            combinations.forEach {
                val comb = it
                letters!!.forEach {
                    tempComb.add("$comb$it")
                }
            }
            combinations.clear()
            combinations = tempComb
        }
    }
    return combinations
}

fun letterCombinationsBackTrack(digits: String) : List<String> {
    val map = mapOf(
        '2' to "abc",
        '3' to "def",
        '4' to "ghi",
        '5' to "jkl",
        '6' to "mno",
        '7' to "pqrs",
        '8' to "tuv",
        '9' to "wxyz"
    )

    val result = mutableListOf<String>()
    if (digits.isEmpty()) return result

    fun backtrack(index: Int, current: StringBuilder) {
        if (index == digits.length) {
            result.add(current.toString())
            return
        }

        val letters = map[digits[index]] ?: return
        for (letter in letters) {
            current.append(letter)
            backtrack(index + 1, current)
            current.deleteCharAt(current.length - 1)
        }
    }

    backtrack(0, StringBuilder())
    return result
}

// https://leetcode.com/problems/gas-station/
// time O(n)
// space O(1)
fun canCompleteCircuit(gas:IntArray, cost: IntArray) : Int{
    val n  = gas.size
    var totalGas = 0
    var currentGas = 0
    var startIdx = 0

    for(i in 0 until n){
        val diff = gas[i] - cost[i]
        totalGas += diff
        currentGas += diff

        if(currentGas<0){
            currentGas = 0
            startIdx = i+1
        }
    }
    return if(totalGas>=0) startIdx else -1
}

// https://leetcode.com/problems/word-pattern/
// time O(n^2) , because of checking in hashmap using contains()
// space O(n)
fun wordPattern(pattern: String, s: String): Boolean {
    val words = s.split(" ")

    if(pattern.length != words.size){
        return false
    }
    var map : MutableMap<Char, String> = mutableMapOf()

    for(i in 0 until pattern.length){
        val c = pattern[i]

        val mapWord= map.get(c)
        if(mapWord == null){
            //not mapped
            if(map.values.contains(words[i])){
                return false
            }
            map.put(c, words[i])
        } else {
            // mapped
            if(words[i] != mapWord){
                return false
            }
        }
    }

    return true
}


// https://leetcode.com/problems/evaluate-reverse-polish-notation/
// time O(n)
// space O(n/2)
fun evalRPN(tokens: Array<String>): Int {
    var stack = Stack<Int>()
    var res = 0
    var isResInit = true

    for(i in 0 until tokens.size){
        val s = tokens[i]

        val number = s.toIntOrNull()

        if(number!=null && number in -200..200){
            stack.push(s.toInt())
        } else {
            if(isResInit){
                isResInit = false
                val numb = stack.pop()
                val numa = stack.pop()

                when(s){
                    "+" -> res = numa+numb
                    "-" -> res = numa-numb
                    "*" -> res = numa*numb
                    "/" -> res = numa/numb
                    else -> res = 0
                }
            } else {
                val num = stack.pop()

                when(s){
                    "+" -> res = num+res
                    "-" -> res = res-num
                    "*" -> res = num*res
                    "/" -> res = num/res
                    else -> res = 0
                }
            }

        }
    }
    return res
}

fun evalRPN1(tokens: Array<String>): Int {
    val stack = mutableListOf<Int>()

    for (token in tokens) {
        val operation: (Int, Int) -> Int = when (token) {
            "+" -> Int::plus
            "-" -> Int::minus
            "*" -> Int::times
            "/" -> Int::div
            else -> {
                stack += token.toInt()
                continue
            }
        }

        val rhs = stack.removeLast()
        val lhs = stack.removeLast()
        stack += operation(lhs, rhs)
    }

    return stack.single()
}


// https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/
// time O(n) since it visits all the nodes only once, reverse in order traversal
// space
class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun bstToGst(root: TreeNode?) : TreeNode? {
    var accumulativeNodeSum = 0

    fun reverseInorderTraverse(node: TreeNode?){
        node ?: return

        reverseInorderTraverse(node.right)

        accumulativeNodeSum += node.`val`
        node.`val` = accumulativeNodeSum

        reverseInorderTraverse(node.left)
    }
    reverseInorderTraverse(root)
    return root
}

// https://leetcode.com/problems/add-binary/
// time O(n), where n = maxLength(a.length, b.length)
// space O(n), where n = maxLength(a.length, b.length)
fun addBinary(a: String, b: String): String {
    var aLen = a.length
    var bLen = b.length
    var result :String = ""

    var i = aLen-1
    var j = bLen -1
    var carry = false
    var str : StringBuilder = StringBuilder()

    while(i>=0 || j>=0){
        var ca: Char
        if(i<0){
            ca = '0'
        } else {
            ca = a[i]
        }

        var cb: Char
        if(j<0){
            cb = '0'
        } else {
            cb = b[j]
        }


        if(ca == '1' && cb=='1'){
            if(carry == true){
                str.append("1")
                carry = true
            } else {
                str.append("0")
                carry = true
            }
        } else if((ca == '1' && cb =='0') ||
            (ca == '0' && cb =='1')){
            if(carry == true){
                str.append("0")
                carry = true
            }  else {
                str.append("1")
                carry = false
            }
        } else {
            if(carry == true){
                str.append("1")
                carry = false
            } else {
                str.append("0")
            }
        }

        j--
        i--
    }

    if(carry){
        str.append("1")
    }

    result = str.toString()

    return result.reversed()
}

fun addBinary2(a:String, b: String): String {
    return (a.toBigInteger() + b.toBigInteger()).toString(2)
}

// https://leetcode.com/problems/min-stack/
// time O(1) for all operations
// space O(n)
class MinStack() {
    private val list = mutableListOf<Pair<Int, Int>>()

    fun push(value:Int){
        list += value to minOf(value, list.lastOrNull()?.second ?: value)
    }

    fun pop(){
        list.removeAt(list.lastIndex)
    }

    fun top(): Int = list.last().first

    fun getMin(): Int = list.last().second
}

// https://leetcode.com/problems/simplify-path/
// time O(n)
// space O(n)
fun simplifyPath(path: String): String {
    var directories = path.split("/")
    var stack = Stack<String>()

    directories.forEach { dir ->
        when {
            dir == ".." -> {
                if (stack.isNotEmpty()) {
                    stack.pop()
                }
            }

            dir.isNotEmpty() && dir != "." -> {
                stack.push(dir)
            }
        }
    }
    return "/" + stack.joinToString("/")
}

// https://leetcode.com/problems/ransom-note/
// time O(n + m)
// space O(k) where k is the number of unique characters in magazine
fun canConstructRansomNote(ransomNote: String, magazine: String): Boolean {
    if (ransomNote.length > magazine.length) return false

    var rMap: MutableMap<Char, Int> = HashMap()

    for (i in 0 until magazine.length) {
        val c: Char = magazine[i]
        var cc = if (rMap.contains(c)) rMap.get(c)!! + 1 else 1
        rMap.put(c, cc)
    }

    for (i in 0 until ransomNote.length) {
        val c: Char = ransomNote[i]
        if (rMap.contains(c)) {
            var cc = rMap.get(c)!!
            if (cc > 0) {
                rMap.put(c, --cc)
            } else {
                return false
            }
        } else {
            return false
        }
    }
    return true
}

// https://leetcode.com/problems/jump-game/
// time O(n)
// space O(1)
fun canJump(nums: IntArray): Boolean {
    var targetIndex = nums.size - 1

    for (i in nums.size - 2 downTo 0) {
        if (i + nums[i] >= targetIndex) {
            targetIndex = i
        }
    }
    return targetIndex == 0
}

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
// time O(n)
// space O(1)
fun maxProfit2(prices: IntArray): Int {
    var profit = 0

    for (i in 1 until prices.size) {
        if (prices[i] > prices[i - 1]) {
            profit += prices[i] - prices[i - 1]
        }
    }
    return profit
}

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
// time O(n)
// space O(1)
fun maxProfit(prices: IntArray): Int {
    var maxP = 0
    var buy = prices[0]

    for (i in 1 until prices.size) {
        if (prices[i] < buy) {
            buy = prices[i]
        } else {
            if (maxP < prices[i] - buy) {
                maxP = prices[i] - buy
            }
        }
    }
    return maxP
}

// https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/
// time O(n*k)
// space O(1)
fun minKBitFlips(nums: IntArray, k: Int): Int {
    var count = 0

    for (i in 0 until nums.size - k + 1) {
        if (nums[i] == 0) {
            count++
            var pointer = 0
            repeat(k) {
                nums[i + pointer] = if (nums[i + pointer] == 0) 1 else 0
                pointer++
            }
        }
    }
    return if (nums.contains(0)) -1 else count
}

// https://leetcode.com/problems/valid-palindrome/?envType=study-plan-v2&envId=top-interview-150
// time O(n)
// space O(1)
fun isPalindrome2(s: String): Boolean {
    val len = s.length
    var j = len - 1
    var ch = ' '
    var ch2 = ' '

    for (i in 0 until len) {
        ch = s[i]
        if (!(ch in 'a'..'z' ||
                    ch in 'A'..'Z' ||
                    ch in '0'..'9')
        )
            continue
        if (ch in 'A'..'Z')
            ch = ch.lowercaseChar()

        while (i <= j) {
            ch2 = s[j]
            if (!(ch2 in 'a'..'z' ||
                        ch2 in 'A'..'Z' ||
                        ch2 in '0'..'9')
            ) {
                j--
                continue
            }
            if (ch2 in 'A'..'Z')
                ch2 = ch2.lowercaseChar()
            j--
            break
        }

        if (i > j && ch == ch2)
            return true
        if (ch != ch2)
            return false
    }
    return true
}

// https://leetcode.com/problems/rotate-array/
// time O(n)
// space O(n)
fun rotate(nums: IntArray, k: Int): Unit {
    var n = nums.size
    var k1 = k
    var res = IntArray(nums.size)
    var j = 0

    if (k1 > n) {
        k1 = k1 % n
    }

    for (i in n - k1 until nums.size) {
        res[j++] = nums[i]
    }

    for (i in 0 until n - k1) {
        res[j++] = nums[i]
    }

    for (i in 0 until n) {
        nums[i] = res[i]
    }
}

// https://leetcode.com/problems/majority-element/s
// time O(n)
// space O(n)
fun majorityElement(nums: IntArray): Int {
    var occ = HashMap<Int, Int>()
    nums.forEach {
        occ.put(it, occ.getOrDefault(it, 0) + 1)
    }
    return occ.maxBy { it.value }.key
}


// time O(nlogn)
// spaceO(log n)
fun majorityElement2(nums: IntArray): Int {
    if (nums.isEmpty()) {
        return 0
    }

    Arrays.sort(nums)

    var num = nums[0]
    var count = 1
    var res = num
    var resCount = 1

    for (i in 1 until nums.size) {
        if (num == nums[i]) {
            count++
        } else {
            num = nums[i]
            count = 1
        }

        if (count > resCount) {
            resCount = count
            res = num
        }
    }
    return res
}


// https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
// time O(n)
// space O(1)
fun removeDuplicates2(nums: IntArray): Int {
    if (nums.size == 0)
        return 0

    var j = 1

    for (i in 2 until nums.size) {
        if (nums[j] == nums[i]) {
            if (nums[j] == nums[j - 1]) {
                // two entered
                continue
            } else {
                nums[++j] = nums[i]
            }
        } else {
            nums[++j] = nums[i]
        }
    }
    return j + 1
}


// https://leetcode.com/problems/merge-sorted-array/
// time O(m+n)
// space O(1)
fun mergeSortedArray(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
    var i = m - 1
    var j = n - 1
    var k = m + n - 1

    while (j <= 0) {
        if (i < 0 || nums2[j] > nums1[i]) {
            nums1[k] = nums2[j]
            k--
            j--
        } else {
            nums1[k] = nums1[i]
            k--
            i--
        }
    }
}


// https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit
// time O(n), the inner loops perform at O(1) only since it checks for a condition
// space O(n)
fun longestSubarray(nums: IntArray, limit: Int): Int {
    val dec = LinkedList<Int>()
    val inc = LinkedList<Int>()
    val n = nums.size
    var i = 0
    var ans = 0

    for (j in 0 until n) {
        while (dec.isNotEmpty() && nums[j] > dec.last) {
            dec.removeLast()
        }
        dec.add(nums[j])

        while (inc.isNotEmpty() && nums[j] < inc.last) {
            inc.removeLast()
        }
        inc.add(nums[j])

        while (dec.first - inc.first > limit) {
            if (dec.first == nums[i]) {
                dec.removeFirst()
            }
            if (inc.first == nums[i]) {
                inc.removeFirst()
            }
            i++
        }

        ans = maxOf(ans, j - i + 1)
    }

    return ans
}


// sudoku solver
fun solveSudoku(board: Array<CharArray>): Unit {
    helper(board, 0, 0)
}

fun helper(board: Array<CharArray>, currentRow: Int, currentColumn: Int): Boolean {
    var row = currentRow
    var column = currentColumn

    if (column == 9) {
        // moving to the next row and starting from the beginning
        row++
        column = 0

        if (row == 9) {
            // finished solving the sudoku board
            return true
        }
    }

    if (board[row][column] == '.') {
        return tryPossibleDigits(board, row, column)
    }
    return helper(board, row, column + 1)
}

fun tryPossibleDigits(
    board: Array<CharArray>,
    row: Int = 0,
    column: Int = 0
): Boolean {
    for (digit in '1'..'9') {
        if (isValidAtPosition(board, row, column, digit)) {
            board[row][column] = digit
            if (helper(board, row, column + 1)) {
                return true
            }
        }
    }
    board[row][column] = '.'
    return false
}

fun isValidAtPosition(
    board: Array<CharArray>,
    row: Int,
    column: Int,
    digit: Char
): Boolean {
    var rowValid = !board[row].contains(digit)
    // map function is used to transform each element of the board
    // for each row bRow, extracts the element at the column index
    // lambda function takes each rown and returs element at column
    var columnValid = !board.map { bRow -> bRow[column] }.contains(digit)
    if (!rowValid || !columnValid) return false

    val subGridRow = row - row % 3
    val subGridCol = column - column % 3

    for (i in 0 until 3) {
        for (j in 0 until 3) {
            val sRow = subGridRow + i
            val sCol = subGridCol + j
            if (digit == board[sRow][sCol]) {
                return false
            }
        }
    }
    return true
}


// https://leetcode.com/problems/combination-sum/
// time O(2^n) // n is number of candidates, exponential number of combinations
// space O(target)
fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
    val currentSet: MutableList<Int> = mutableListOf<Int>()
    val combinations = mutableListOf<MutableList<Int>>()

    backtrack(candidates, target, 0, 0, currentSet, combinations)

    return combinations
}

fun backtrack(
    candidates: IntArray,
    target: Int,
    index: Int,
    runningSum: Int,
    currentSet: MutableList<Int>,
    combinations: MutableList<MutableList<Int>>
) {
    if (runningSum == target) {
        combinations.add(currentSet.toMutableList())
        return
    }

    if (index >= candidates.size || runningSum > target) {
        return
    }

    currentSet.add(candidates[index])
    backtrack(
        candidates, target, index,
        runningSum + candidates[index], currentSet, combinations
    )
    currentSet.removeLast()
    backtrack(
        candidates, target, index + 1,
        runningSum, currentSet, combinations
    )
}

// https://leetcode.com/problems/count-and-say/
// time O(2^n) // growing exponentially
// space O(2^n) // growing exponentially over each sequence
fun countAndSay(n: Int): String {
    fun next(s: String): String {
        var count = 0;
        var i = 0
        var res = ""

        while (i < s.length) {
            count = 1
            while (i + 1 < s.length && s[i] == s[i + 1]) {
                count++
            }
            res = "$count${s[i++]}"
        }

        return res
    }

    // takes upto n and returns the last
    return generateSequence("1", ::next).take(n).last()

}

// https://leetcode.com/problems/valid-sudoku/
// time O(1) since 9x9=81 O(81)
// space O(1) since there is only fixed number and n doesn't grow
fun isValidSudoku(board: Array<CharArray>): Boolean {
    var seen = HashSet<String>()

    for (i in 0 until 9) {
        for (j in 0 until 9) {
            val number = board[i][j]
            if (!seen.add("$number in row $i") ||
                !seen.add("$number in column $j") ||
                !seen.add("$number in block {$i/3}-{$j/3}")
            )
                return false
        }
    }
    return true
}


// https://leetcode.com/problems/search-insert-position/description/
// time O(log n)
// space O(1)
fun searchInsert(nums: IntArray, target: Int): Int {
    var l = 0
    var r = nums.lastIndex

    while (l <= r) {
        var m = l + (r - l) / 2

        if (target == nums[m]) {
            return m
        } else if (target < nums[m]) {
            r = m - 1
        } else {
            l = m + 1
        }
    }
    return l
}


// https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/
// time O(n logn) this is if worst case and we end up doing binary search on all indexes
// space (O(1)
fun searchRange(nums: IntArray, target: Int): IntArray {
    var result = intArrayOf(-1, -1)

    var first = nums.binarySearch(target, 0, nums.size)
    var last = first

    while (first >= 0) {
        result[0] = first
        first = nums.binarySearch(target, 0, first)
    }

    while (last >= 0) {
        result[1] = last
        last = nums.binarySearch(target, last + 1, nums.size)
        println("last : $last")
    }

    return result
}


// https://leetcode.com/problems/search-in-rotated-sorted-array/description/
// time O(log n) because of binary search
// space O(1)
fun binarySearchOnPivotSortedArray(nums: IntArray, target: Int): Int {
    var l = 0
    var r = nums.lastIndex

    while (l < r) {
        var m = l + (r - l) / 2

        if (nums[m] == target) return m

        if (nums[m] <= nums[r]) { //checking if the array is sorted half
            if (target > nums[m] && target <= nums[r]) {
                l = m + 1
            } else {
                r = m - 1
            }
        } else {
            if (target >= nums[l] && target < nums[m]) {
                r = m - 1
            } else {
                l = m + 1
            }
        }
    }
    return -1
}


// https://leetcode.com/problems/longest-valid-parentheses/
fun longestValidParentheses(s: String): Int {
    var left = 0
    var right = 0
    var bleft = 0
    var bright = 0
    var leftMax = 0
    var rightMax = 0

    for (i in 0 until s.length) {
        if (s[i] == '(') {
            left++
        } else {
            right++
        }

        if (s[s.length - 1 - i] == '(') {
            bleft++
        } else {
            bright++
        }

        if (left == right) {
            leftMax = Math.max(leftMax, left * 2)
        }
        if (bleft == bright) {
            rightMax = Math.max(rightMax, bleft * 2)
        }

        if (right > left) {
            left = 0
            right = 0
        }

        if (bleft > bright) {
            bleft = 0
            bright = 0
        }
    }
    return Math.max(leftMax, rightMax)
}

// https://leetcode.com/problems/next-permutation/
// time O(n2)
// space O(1)
fun nextPermutation(nums: IntArray): Unit {
    for (i in nums.lastIndex - 1 downTo 0) {
        for (j in nums.lastIndex downTo i + 1) {
            if (nums[j] > nums[i]) {
//                nums.swap(i,j)
                nums.sort(i + 1)
                return
            }
        }
    }
    nums.sort()
}

//fun IntArray.swap(i:Int, j:Int) : Unit{
//    val temp = this[i]
//    this[i]=this[j]
//    this[j]=temp
//}


// subsutring

fun findSubstring(s: String, words: Array<String>): List<Int> {
    var perms = allStringPermutations(words)
    var set: MutableSet<Int> = HashSet()
    var result = mutableListOf<Int>()

    for (p in perms) {
        if (s.contains(p)) {
            //var indexWord = s.indexOf(p)
            //set.add(indexWord)

            result.addAll(findAllOccurrences(s, p))
        }
    }
    result.addAll(set)

    return result
}

fun findAllOccurrences(mainString: String, subString: String): List<Int> {
    val indices = mutableListOf<Int>()
    var index = mainString.indexOf(subString)

    while (index >= 0) {
        indices.add(index)
        index = mainString.indexOf(subString, index + 1)
    }

    return indices
}

fun allStringPermutations(array: Array<String>): Array<String> {
    // Initialize an empty list to store permutations
    val permutations = mutableListOf<String>()

    // Function to generate permutations of indices
    fun permuteIndices(indices: IntArray, depth: Int) {
        if (depth == indices.size) {
            // Create permutation based on current indices order
            val permutation = indices.map { array[it] }.joinToString("")
            permutations.add(permutation)
            return
        }

        // Generate permutations recursively
        for (i in depth until indices.size) {
            swap(indices, i, depth)
            permuteIndices(indices, depth + 1)
            swap(indices, i, depth) // backtrack
        }
    }

    // Initialize indices array [0, 1, 2, ..., array.size - 1]
    val indices = IntArray(array.size) { it }

    // Generate permutations starting with indices array
    permuteIndices(indices, 0)

    // Convert permutations list to array and return
    return permutations.toTypedArray()
}

// Helper function to swap elements in an array
fun swap(arr: IntArray, i: Int, j: Int) {
    val temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp
}

// https://leetcode.com/problems/remove-element/
// time O(n)
// space O(1)
fun removeElement(nums: IntArray, `val`: Int): Int {
    var k = 0

    for (i in 0 until nums.size) {
        if (nums[i] != `val`) {
            nums[k] = nums[i]
            k++
        }
    }
    return k
}

// https://leetcode.com/problems/remove-duplicates-from-sorted-array/
// time O(n)
// space O(n) with distinct as distinct creates hashset
// space O(1) with two pointer
fun removeDuplicates(nums: IntArray): Int {
//    return nums.distinct().let{
//        it.forEachIndexed{index, value -> nums[index] = value}
//        it.size
//    }

    if (nums.isEmpty()) {
        return 0
    }
    var j = 0
    for (i in 1 until nums.size) {
        if (nums[i] != nums[j]) {
            j++
            nums[j] = nums[i]
        }
    }
    return j + 1
}

// https://leetcode.com/problems/reverse-nodes-in-k-group/
fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
    var temp = head

    for (i in 0 until k) {
        if (temp == null) {
            return head
        } else {
            temp = temp.next
        }
    }

    var nextNewStart = reverseKGroup(temp, k)

    var prev: ListNode? = ListNode(0)
    prev = head
    var curr = prev

    for (i in 0 until k) {
        var next = curr!!.next
        curr.next = prev
        prev = curr
        curr = next
    }
    head!!.next = nextNewStart
    return prev

}

// https://leetcode.com/problems/swap-nodes-in-pairs/
// time O(n)
// space O(1)
fun swapPairs(head: ListNode?): ListNode? {
    if (head == null || head.next == null) {
        return head
    }

    val res = ListNode(0)
    res.next = head
    var curr: ListNode? = res

    while (curr?.next != null && curr.next?.next != null) {
        var t1 = curr.next
        var t2 = curr.next?.next

        curr.next = t2
        t1?.next = t2?.next
        t2?.next = t1
        curr = curr.next?.next
    }
    return res.next
}

// https://leetcode.com/problems/merge-k-sorted-lists/
// time O(n log k)
// space O(log k)
fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) {
        return ListNode(0).next
    }

    return mergeKListsHelper(lists, 0, lists.size - 1)
}

fun mergeKListsHelper(lists: Array<ListNode?>, start: Int, end: Int): ListNode? {
    if (start == end) {
        return lists[start]
    }

    val mid = (start + end) / 2
    var left = mergeKListsHelper(lists, start, mid)
    var right = mergeKListsHelper(lists, mid + 1, end)
    return merge(left, right)
}

fun merge(list1: ListNode?, list2: ListNode?): ListNode? {
    var head: ListNode? = ListNode(0)
    var curr: ListNode? = head
    var l1 = list1
    var l2 = list2

    while (l1 != null && l2 != null) {
        if (l1.`val` < l2.`val`) {
            curr!!.next = l1
            l1 = l1.next
        } else {
            curr!!.next = l2
            l2 = l2.next
        }
        curr = curr!!.next
    }

    if (l1 != null) {
        curr!!.next = l1
    }
    if (l2 != null) {
        curr!!.next = l2
    }
    return head!!.next
}

// https://leetcode.com/problems/generate-parentheses/
// time complexity: since this is recursive, it is catalan number of n
fun generateParenthesis(n: Int): List<String> {
    var res: MutableList<String> = mutableListOf()
    recurse(res, 0, 0, "", n)
    return res
}

fun recurse(res: MutableList<String>, left: Int, right: Int, s: String, n: Int) {
    if (s.length == 2 * n) {
        res.add(s)
        return
    }

    if (left < n) {
        recurse(res, left + 1, right, s + "(", n)
    }

    if (right < left) {
        recurse(res, left, right + 1, s + ")", n)
    }
}

// https://leetcode.com/problems/valid-parentheses/
// time : O(n) where n is the length of the string
// space: O(n) as the worse case iss all characters could be open brackets
// and thereby the length of the string
fun isValidParanthese(s: String): Boolean {
    val stack = mutableListOf<Char>()

    for (char in s) {
        when (char) {
            '(', '{', '[' -> stack.add(char)
            ')' -> if (stack.isEmpty() || stack.removeAt(stack.size - 1) != '(')
                return false

            '}' -> if (stack.isEmpty() || stack.removeAt(stack.size - 1) != '{')
                return false

            ']' -> if (stack.isEmpty() || stack.removeAt(stack.size - 1) != '[')
                return false
        }
    }
    return stack.isEmpty()
}

// https://leetcode.com/problems/4sum/
// time complexity O(n3)
// space complexity O(k) where k is the number of unique quadruplets found, worst case O(n4)
fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
    Arrays.sort(nums)

    var set: MutableSet<List<Int>> = HashSet()
    var result: MutableList<List<Int>> = ArrayList()

    for (a in 0..nums.size - 1) {
        var d = nums.size - 1

        while (d > a + 2) {
            var b = a + 1
            var c = d - 1;

            while (b < c) {
                val sumL = nums[a].toLong() + nums[b].toLong() + nums[c].toLong() + nums[d].toLong()

                if (sumL > Int.MAX_VALUE || sumL < Int.MIN_VALUE) {
                    b++
                    c--
                    continue
                }

                if (sumL.toInt() == target) {
                    val list: List<Int> = listOf(nums[a], nums[b], nums[c], nums[d])
                    set.add(list)
                    b++
                    c--
                } else if (sumL.toInt() < target) {
                    b++
                } else {
                    c--
                }
            }
            d--
        }
    }
    result.addAll(set)

    return result
}

// https://leetcode.com/problems/3sum-closest/
fun threeSumClosest(nums: IntArray, target: Int): Int {
    Arrays.sort(nums)

    var closestSum = nums[0] + nums[1] + nums[2]

    for (i in 0..nums.size - 2) {
        var j = i + 1
        var k = nums.size - 1

        while (j < k) {
            var sum = nums[i] + nums[j] + nums[k]

            if (Math.abs(target - sum) < Math.abs(target - closestSum)) {
                closestSum = sum
            }

            if (sum < target) {
                j++
            } else
                k--
        }
    }

    return closestSum
}


// https://leetcode.com/problems/longest-common-prefix/
private fun longestCommonPrefix(words: Array<String>): String {
    words.sort()

    var first = words[0]
    var last = words[words.size - 1]
    var idx = 0

    while (idx < first.length && idx < last.length) {
        if (first[idx] == last[idx]) {
            idx++
        } else {
            break
        }
    }
    return first.substring(0, idx)
}

fun romanToInt(s: String): Int {
    val m = mutableMapOf<Char, Int>()
    m['I'] = 1
    m['V'] = 5
    m['X'] = 10
    m['L'] = 50
    m['C'] = 100
    m['D'] = 500
    m['M'] = 1000

    var ans = 0

    for (i in 0..s.length - 1) {
        if (i < s.length - 1 && m[s[i]]!! < m[s[i + 1]]!!) {
            ans -= m[s[i]]!!
        } else {
            ans += m[s[i]]!!
        }
    }

    return ans
}

// https://leetcode.com/problems/remove-nth-node-from-end-of-list/
// time complexity O(L) where L is the length of the linked list
// space complexity O(1), no additional data structures or recursive call
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    var fast = head
    var slow = head

    for (i in 1..n) {
        fast = fast!!.next
    }

    if (fast == null) {
        return head
    }

    while (fast!!.next != null) {
        fast = fast!!.next
        slow = slow!!.next
    }

    slow!!.next = slow!!.next!!.next
    return head
}

// https://leetcode.com/problems/merge-two-sorted-lists/
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
    if (l1 != null && l2 != null) {
        if (l1.`val` < l2.`val`) {
            l1.next = mergeTwoLists(l1.next, l2)
            return l1
        } else {
            l2.next = mergeTwoLists(l1, l2.next)
            return l2
        }
    }

    if (l1 == null) {
        return l2
    } else {
        return l1
    }
}


// https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
fun firstOccurenceString(haystack: String, needle: String): Int {
    for (i in 0..haystack.length - needle.length) {
        val w = haystack.substring(i, i + needle.length)
        if (w.equals(needle)) {
            return i
        }
    }
    return -1
}

// https://leetcode.com/problems/3sum/description/
fun threeSum(numbers: IntArray): List<List<Int>> {
    val target = 0
    var nums = numbers
    nums.sort()

    var s: MutableSet<List<Int>> = HashSet()
    var output: MutableList<List<Int>> = ArrayList()

    for (i in 0..nums.size - 2) {
        var j = i + 1

        var k = nums.size - 1

        while (j < k) {
            val sum = nums[i] + nums[j] + nums[k]
            if (sum == target) {
                s.add(Arrays.asList(nums[i], nums[j], nums[k]))
                j++
                k--
            } else if (sum < target) {
                j++
            } else {
                k--
            }
        }
    }

    output.addAll(s)
    return output
}

//
fun integerToRoman(number: Int): String {
    var num = number
    var result: StringBuilder = StringBuilder()

    var mCount = num / 1000;
    while (mCount > 0) {
        result = result.append("M")
        num = num - 1000
        mCount--
    }

    val dCount = num / 100
    if (dCount >= 1 && dCount <= 3) {
        for (i in 1..dCount) {
            result = result.append("C")
        }
    } else if (dCount == 4) {
        result = result.append("CD")
    } else if (dCount == 5) {
        result = result.append("D")
    } else if (dCount >= 6 && dCount <= 8) {
        result = result.append("D")
        for (i in 1..dCount - 5) {
            result = result.append("C")
        }
    } else if (dCount == 9) {
        result = result.append("CM")
    }

    num = num - (100 * dCount)

    val xCount = num / 10
    if (xCount >= 1 && xCount <= 3) {
        for (i in 1..xCount) {
            result = result.append("X")
        }
    } else if (xCount == 4) {
        result = result.append("XL")
    } else if (xCount == 5) {
        result = result.append("L")
    } else if (xCount >= 6 && xCount <= 8) {
        result = result.append("L")
        for (i in 1..xCount - 5) {
            result = result.append("X")
        }
    } else if (xCount == 9) {
        result = result.append("XC")
    }

    num = num - (10 * xCount)

    if (num >= 1 && num <= 3) {
        for (i in 1..num) {
            result = result.append("I")
        }
    } else if (num == 4) {
        result = result.append("IV")
    } else if (num == 5) {
        result = result.append("V")
    } else if (num >= 6 && num <= 8) {
        result = result.append("V")
        for (i in 1..num - 5) {
            result = result.append("I")
        }
    } else if (num == 9) {
        result = result.append("IX")
    }

    return result.toString()
}

// https://leetcode.com/problems/container-with-most-water/description/
fun maxArea(heights: IntArray): Int {
    var left = 0
    var right = heights.size - 1
    var max = 0

    while (left < right) {
        val width = right - left
        val height = Math.min(heights[left], heights[right])
        val area = width * height
        max = Math.max(max, area)

        if (heights[left] < heights[right]) {
            left++
        } else if (heights[right] < heights[left]) {
            right--
        } else {
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

    while (x > 0) {
        val last = num % 10
        reverse += reverse * 10 + last

        num = num / 10
    }
    return reverse == x
}

// https://leetcode.com/problems/string-to-integer-atoi/
fun stringToInteger(str: String): Int {
    var s: String = str.trim()
    var num: Long = 0
    var nums = StringBuilder()
    var isNeg = false


    for (i in 0..s.length - 1) {
        if (i == 0) {
            if (s[i] == '-') {
                isNeg = true;
            } else if (s[i] == '+') {
                isNeg = false;
            } else {
                if (s[i].isDigit()) {
                    nums.append(s[i])
                } else {
                    break
                }
            }
        } else {
            if (s[i].isDigit()) {
                nums.append(s[i])
            } else {
                break
            }
        }
    }

    if (nums.isNotEmpty()) {
        try {
            num = nums.toString().toLong()
        } catch (e: NumberFormatException) {
            if (isNeg) {
                return Integer.MIN_VALUE
            } else {
                return Integer.MAX_VALUE
            }
        }
    } else {
        return 0
    }

    if (isNeg) {
        num = num * -1
    }

    if (num > Integer.MAX_VALUE) {
        return Integer.MAX_VALUE
    }
    if (num < Integer.MIN_VALUE) {
        return Integer.MIN_VALUE
    }

    println("string to int: $num")
    return num.toInt()
}


///////////////////////////

// https://leetcode.com/problems/reverse-integer/
fun reverseInteger(num: Int): Int {
    var reversed = 0
    val sign = if (num < 0) -1 else 1
    var temp = num
    if (num < 0) {
        temp = num * -1
    }

    while (temp > 0) {
        val last = temp % 10
        temp = temp / 10

        if (reversed > (Integer.MAX_VALUE - last) / 10) {
            return 0
        }
        reversed = reversed * 10 + last
    }
    val result = reversed * sign
    println("reversed is $result")
    return result
}

fun reverseInteger2(x: Int): Int {
    val value = if (x < 0) (x * -1).toString() else x.toString()
    var reverse = (if (x < 0) "-" else "") + value.reversed()
    return reverse.toIntOrNull() ?: 0
}

////////////////////

fun zigzagConversion(s: String, numRows: Int): String {
    if (numRows == 1 || numRows >= s.length) {
        return s
    }

    var rowIndex = 0
    var direction = 1
    val rows: Array<MutableList<Char>> = Array(numRows) { ArrayList() }

    for (i in 0..numRows - 1) {
        rows[i] = ArrayList<Char>()
    }

    for (c in s.toCharArray()) {
        rows[rowIndex].add(c)

        if (rowIndex == 0) {
            direction = 1
        }
        if (rowIndex == numRows - 1) {
            direction = -1
        }
        rowIndex += direction
    }

    var result = ""

    for (row in rows) {
        for (c in row) {
            result += c
        }
    }
    println("zigzag is $result")
    return result
}

// https://leetcode.com/problems/longest-palindromic-substring/
fun longestPalindrome(s: String): String {
    var result = ""

    if (s.length == 1) {
        return s
    }

    for (i in 0..s.length - 1) {
        for (j in i..s.length) {
            val sub = s.substring(i, j)

            if (isPalindrome(sub)) {
                if (sub.length > result.length) {
                    result = sub
                }
            }
        }
    }

    println("longest palindrome: $result")
    return result
}

fun isPalindrome(sub: String): Boolean {
    var left = 0
    var right = sub.length - 1

    while (left < right) {
        if (sub[left] == sub[right]) {
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


fun lengthOfLongestSubstring(s: String): Int {
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
            if (set.size > result) {
                result = set.size
            }
        }
    }

    println("longest substring size: " + result)
    return result
}

// https://leetcode.com/problems/two-sum/submissions/1280210991/
fun twoSum(nums: IntArray, target: Int): IntArray? {
    for (i in nums.indices) {
        for (j in i + 1 until nums.size) {
            if (nums[i] + nums[j] == target) {
                return intArrayOf(i, j)
            }
        }
    }

    return null
}

fun subArrayNegatives() {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    val arr: MutableList<Int> = ArrayList()

    for (i in 0..n - 1) {
        arr.add(scanner.nextInt())
    }

    var negatives = 0

    for (i in 0..n - 1) {
        for (j in i..n - 1) {
            var currentSum = 0

            for (k in i..j) {
                currentSum += arr.get(k)
            }

            if (currentSum < 0) {
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

    for (i in 0..n - 1) {
        a[i] = sc.nextInt()
    }

    for (i in 0..n - 1) {
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
    println("${a.substring(0, 1).uppercase()}${a.substring(1)} ${b.substring(0, 1).uppercase()}${b.substring(1)}")

}

fun currencyFormatter() {
    val scanner = Scanner(System.`in`)
    val payment = scanner.nextDouble()
    scanner.close()

    // Write your code here.
    val us = NumberFormat.getCurrencyInstance(Locale.US).format(payment)
    val india = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(payment)
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
    try {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()
        sc.close()

        val s = n.toString()

        if (n == s.toInt()) {
            println("Good job")
        } else {
            println("Wrong answer.")
        }
    } catch (e: Exception) {
        println("Unsuccessful Termination!!")
    }
}

fun endOfFile() {
    val sc = Scanner(System.`in`)
    var i = 1

    while (sc.hasNext()) {
        println("$i ${sc.nextLine()}")
        i++
    }
    sc.close()
}

fun datatypes() {
    val sc = Scanner(System.`in`)
    val t = sc.nextInt()

    for (i in 1..t) {
        try {
            val x = sc.nextLong()
            println("$x can be fitted in:");

            if (x >= Byte.MIN_VALUE && x <= Byte.MAX_VALUE) {
                println("* byte");
            }
            if (x >= Short.MIN_VALUE && x <= Short.MAX_VALUE) {
                println("* short");
            }
            if (x >= Integer.MIN_VALUE && x <= Integer.MAX_VALUE) {
                println("* int");
            }
            if (x >= Long.MIN_VALUE && x <= Long.MAX_VALUE) {
                println("* long");
            }
        } catch (e: Exception) {
            println("${sc.next()} can't be fitted anywhere.")
        }
    }
    sc.close()
}

fun loops2() {
    val sc = Scanner(System.`in`)

    val t = sc.nextInt()

    for (i in 1..t) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        val n = sc.nextInt()

        var sum = a
        for (j in 0..n - 1) {
            sum += (Math.pow(2.0, j.toDouble())).toInt() * b
            print("$sum ")
        }
        println()
    }
}

fun loops1() {
    val sc = Scanner(System.`in`)

    val n = sc.nextInt()

    for (i in 1..10) {
        println("$n x $i = ${n * i}")
    }

}

fun outputFormatting() {
    val sc = Scanner(System.`in`)

    for (i in 1..3) {
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
