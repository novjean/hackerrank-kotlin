import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.NumberFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
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
    combinationSum(intArrayOf(2,3,6,7), 7)
}

// sudoku solver
fun solveSudoku(board: Array<CharArray>) : Unit {
    helper(board,0,0)
}

fun helper(board: Array<CharArray>, currentRow : Int, currentColumn:Int) : Boolean{
    var row = currentRow
    var column = currentColumn

    if(column == 9){
        // moving to the next row and starting from the beginning
        row++
        column = 0

        if(row == 9){
            // finished solving the sudoku board
            return true
        }
    }

    if(board[row][column] == '.'){
        return tryPossibleDigits(board, row, column)
    }
    return helper(board, row, column+1)
}

fun tryPossibleDigits(board: Array<CharArray>,
                      row: Int = 0,
                      column: Int = 0): Boolean {
    for(digit in '1'..'9') {
        if(isValidAtPosition(board, row, column, digit)){
            board[row][column] = digit
            if(helper(board, row, column+1)) {
                return true
            }
        }
    }
    board[row][column] = '.'
    return false
}

fun isValidAtPosition(board: Array<CharArray>,
                      row: Int,
                      column: Int,
                      digit: Char): Boolean {
    var rowValid = !board[row].contains(digit)
    // map function is used to transform each element of the board
    // for each row bRow, extracts the element at the column index
    // lambda function takes each rown and returs element at column
    var columnValid = !board.map { bRow -> bRow[column] }.contains(digit)
    if(!rowValid || !columnValid) return false

    val subGridRow = row -row%3
    val subGridCol = column - column%3

    for(i in 0 until 3){
        for(j in 0 until 3){
            val sRow = subGridRow + i
            val sCol = subGridCol + j
            if(digit == board[sRow][sCol]) {
                return false
            }
        }
    }
    return true
}

//
// time
// space


// https://leetcode.com/problems/combination-sum/
// time O(2^n) // n is number of candidates, exponential number of combinations
// space O(target)
fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
    val currentSet: MutableList<Int> = mutableListOf<Int>()
    val combinations = mutableListOf<MutableList<Int>>()

    backtrack(candidates, target, 0, 0, currentSet, combinations)

    return combinations
}

fun backtrack(candidates: IntArray,
              target: Int,
              index: Int,
              runningSum: Int,
              currentSet: MutableList<Int>,
              combinations: MutableList<MutableList<Int>>) {
    if(runningSum == target){
        combinations.add(currentSet.toMutableList())
        return
    }

    if(index>=candidates.size || runningSum>target){
        return
    }

    currentSet.add(candidates[index])
    backtrack(candidates,target, index,
        runningSum+candidates[index], currentSet, combinations)
    currentSet.removeLast()
    backtrack(candidates, target, index+1,
        runningSum, currentSet, combinations)
}

// https://leetcode.com/problems/count-and-say/
// time O(2^n) // growing exponentially
// space O(2^n) // growing exponentially over each sequence
fun countAndSay(n:Int) :String{
    fun next(s: String): String{
        var count = 0;
        var i = 0
        var res = ""

        while(i<s.length){
            count = 1
            while(i+1<s.length && s[i] == s[i+1]){
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

    for(i in 0 until 9){
        for(j in 0 until 9){
            val number = board[i][j]
            if(!seen.add("$number in row $i") ||
                !seen.add("$number in column $j") ||
                !seen.add("$number in block {$i/3}-{$j/3}"))
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

    while(l<=r){
        var m = l+(r-l)/2

        if(target == nums[m]){
            return m
        } else if(target<nums[m]){
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
    var result = intArrayOf(-1,-1)

    var first = nums.binarySearch(target, 0, nums.size)
    var last = first

    while(first>=0){
        result[0] = first
        first = nums.binarySearch(target,0,first)
    }

    while(last>=0){
        result[1] = last
        last = nums.binarySearch(target, last+1, nums.size)
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

    while(l<r){
        var m = l + (r-l)/2

        if(nums[m]==target) return m

        if(nums[m]<= nums[r]){ //checking if the array is sorted half
            if(target> nums[m] && target<= nums[r]){
                l = m+1
            } else {
                r = m-1
            }
        } else {
            if(target>= nums[l] && target< nums[m]){
                r = m-1
            } else {
                l = m+1
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

    for(i in 0 until s.length){
        if(s[i] == '('){
            left++
        } else {
            right++
        }

        if(s[s.length-1-i] == '('){
            bleft++
        } else {
            bright++
        }

        if(left == right){
            leftMax = Math.max(leftMax, left*2)
        }
        if(bleft == bright){
            rightMax = Math.max(rightMax, bleft *2)
        }

        if(right>left){
            left = 0
            right = 0
        }

        if(bleft>bright){
            bleft = 0
            bright = 0
        }
    }
    return Math.max(leftMax, rightMax)
}

// https://leetcode.com/problems/next-permutation/
// time O(n2)
// space O(1)
fun nextPermutation(nums:IntArray) : Unit {
    for(i in nums.lastIndex-1 downTo 0){
        for(j in nums.lastIndex downTo i+1){
            if(nums[j] > nums[i]){
//                nums.swap(i,j)
                nums.sort(i+1)
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
