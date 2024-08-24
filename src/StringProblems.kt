import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class StringProblems {}

fun main() {
//    countPalindromes("abbac")
//    longestPalindrome2("babad")
//    convert("PAYPALISHIRING", 3)
//    println(strStr("mississippi", "issip"))
//    isPalindrome3("race a car")
//    isSubsequence3("abc", "ahbgdc")
//    canConstruct3("bg", "efjbdfbdgfjhhaiigfhbaejahgfbbgbjagbddfgdiaigdadhcfcj")
//    isIsomorphic3("badc", "baba")
//    numDistinct("babgbag", "bag")
//    generate(5)
//    minWindow("ADOBECODEBANC", "ABC")
//    isValidPalindrome("race a car")
//    matchingStrings(arrayOf("aba", "baba", "aba","xzxb"), arrayOf("aba","xzxb","ab"))
    stringsRearrangement(mutableListOf("aba", "bbb", "bab"))
}

// https://app.codesignal.com/arcade/intro/level-7/PTWhv2oWqd6p4AHB9
// time O(n! * n * m)
fun stringsRearrangement(inputArray: MutableList<String>): Boolean {

    // time O(m) where m is the length of the string
    fun differByOne(str1: String, str2: String): Boolean {
        var diffCount = 0
        for(i in str1.indices){
            if(str1[i]!=str2[i]) diffCount++
            if(diffCount>1) return false
        }
        return diffCount == 1
    }

    fun backtrack(current: String, remaining: List<String>): Boolean {
        if(remaining.isEmpty()) return true

        for(i in remaining.indices){
            val next = remaining[i]
            if(differByOne(current, next)){
                val nextRemaining = remaining.toMutableList().apply { removeAt(i) }
                if(backtrack(next, nextRemaining)) return true
            }
        }
        return false
    }

    for(i in inputArray.indices){
        val remaining = inputArray.toMutableList().apply { removeAt(i) }
        if(backtrack(inputArray[i], remaining)) return true
    }
    return false
}

// partition labels
// You are given a string s. We want to partition this string into as many
// parts as possible so that each letter appears in at most one part,
// and return a list of integers representing the size of these parts.
//Input: s = "ababcbacadefegdehijhklij"
//Output: [9,7,8]
fun partitionLabels(s: String): List<Int> {
    val lastIndexMap = mutableMapOf<Char, Int>()
    val partitions = mutableListOf<Int>()

    for(i in s.indices){
        val c = s[i]
        lastIndexMap[c] = i
    }

    var start = 0
    var end = 0
    for(i in s.indices){
        end = maxOf(end, lastIndexMap[s[i]]!!)

        if(i == end){
            partitions.add(end-start+1)
            start = i+1
        }
    }
    return partitions
}


// https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
// time O(n)
// space O(n) since sb is an auxiliary data structure
fun minRemoveToMakeValid(s: String): String {
    val sb = StringBuilder()
    var open = 0

    for(c in s){
        if(c=='('){
            open++
            sb.append(c)
        } else if(c==')'){
            if(open==0){
                // skip as it is invalid )
            } else {
                open--
                sb.append(c)
            }
        } else
            sb.append(c)
    }

    var idx = sb.lastIndex
    while(open>0 && idx>=0){
        val c = sb[idx]
        if(c == '('){
            sb.deleteCharAt(idx)
            open--
        }
        idx--
    }
    return sb.toString()
}

fun matchingStrings(stringList: Array<String>, queries: Array<String>): Array<Int> {
    // Write your code here
    val res = mutableListOf<Int>()

    for (query in queries) {
        val count = stringList.count { it == query }
        res.add(count)
    }

    return res.toTypedArray()
}

// https://app.codesignal.com/arcade/intro/level-6/6Wv4WsrsMJ8Y2Fwno
fun solution(name: String): Boolean {
    if(name[0] in '0'..'9') return false

    for(c in name){
        if(!(c in '0'..'9' || c in 'a'..'z' ||
                    c in 'A'..'Z' || c == '_')) return false
    }
    return true
}

// https://leetcode.com/problems/repeated-string-match/
fun repeatedStringMatch(a: String, b: String): Int {
    val s = StringBuilder(a)
    var count = 1

    while(s.length<b.length){
        s.append(a)
        count++
    }

    if(s.indexOf(b) != -1){
        return count
    }

    // this is if b is shorter than a and we try appending a
    // once and seeing it works ex: a = "aaaaaaab" b = "ba"
    s.append(a)
    count++

    if(s.indexOf(b) != -1){
        return count
    }

    return -1
}

fun multiply(num1: String, num2: String): String {
    if(num1 == "0" || num2 == "0") return "0"

    val n = num1.length
    val m = num2.length
    val res = IntArray(n+m){0}

    for(i in n-1 downTo 0){
        for(j in m-1 downTo 0){
            val a = num1[i] - '0'
            val b = num2[j] - '0'
            val mul = a * b
            val sum = mul + res[i+j+1]
            res[i+j+1] = sum%10
            res[i+j] += sum/10
        }
    }

    val r = StringBuilder()
    for(num in res){
        if(res.isEmpty() && num == 0) continue
        r.append(num)
    }

    return if(res.isEmpty()) "0" else res.toString()
}

// https://leetcode.com/problems/valid-number/
fun isNumber(s: String): Boolean {
    listOf("Infinity", "f", "D").forEach{
        if(s.contains(it)) return false
    }
    return s.toDoubleOrNull() != null
}

fun isValidPalindrome(str: String): Boolean{
    val s = str.lowercase().filter { it in 'a'..'z' || it in '0'..'9' }
    var i = 0
    var j = s.length-1

    while(i<j){
        if(s[i++]!=s[j--]) return false
    }
    return true
}

fun reverseCharsParantheses(inputString: String) : String {
    val stack = Stack<StringBuilder>()
    stack.push(StringBuilder())

    for(char in inputString){
        when(char){
            '(' -> stack.push(StringBuilder())
            ')' -> {
                val last = stack.removeAt(stack.size-1).reverse()
                stack[stack.size-1].append(last)
            }
            else -> stack[stack.size-1].append(char)
        }
    }
    return stack[0].toString()
}

fun luckyTicketNumber(n: Int): Boolean{
    val s = n.toString()
    val mid = s.length / 2

    return s.dropLast(mid).chars().sum() == s.drop(mid).chars().sum()
}

// https://leetcode.com/problems/minimum-window-substring/
fun minWindow(s: String, t: String): String {
    val charCount = mutableMapOf<Char, Int>()
    for(char in t){
        charCount[char] = charCount.getOrDefault(char, 0) +1
    }

    var left = 0
    var right = 0
    var minLength = Int.MAX_VALUE
    var minWindow = ""

    var requiredChars = t.length

    while(right<s.length){
        val charRight = s[right]
        if(charCount.containsKey(charRight)){
            charCount[charRight] = charCount[charRight]!!-1
            if(charCount[charRight]!! >= 0)
                requiredChars--
        }

        while(requiredChars == 0){
            if(right-left+1 < minLength){
                minLength = right-left+1
                minWindow = s.substring(left, right+1)
            }

            val charLeft = s[left]
            if(charCount.containsKey(charLeft)){
                charCount[charLeft] = charCount[charLeft]!! + 1
                if(charCount[charLeft]!! > 0)
                    requiredChars++
            }
            left++
        }
        right++
    }

    return minWindow
}

// O(n) time
fun kmpPatterMatching(text: String, pattern: String): List<Int>{
    val n = text.length
    val m = pattern.length
    val lps = computeLPS(pattern)

    var i = 0
    var j = 0
    val indices = mutableListOf<Int>()
    while (i<n){
        if(text[i] == pattern[j]){
            i++
            j++
        }
        if(j==pattern.length){
            indices.add(i-j)
            j = lps[j-1]
        } else if(i<n && text[i] != pattern[j]){
            if(j!=0){
                j=lps[j-1]
            } else {
                i++
            }
        }
    }
    return indices
}

fun computeLPS(pattern: String): IntArray{
    val lps = IntArray(pattern.length)
    var len = 0
    var i = 1

    while(i<pattern.length){
        if(pattern[i] == pattern[len]){
            len++
            lps[i] = len
            i++
        } else {
            if(len != 0){
                len = lps[len-1]
            } else {
                lps[i] = 0
                i++
            }
        }
    }
    return lps
}

// https://leetcode.com/problems/letter-combinations-of-a-phone-number/
fun letterCombinations2(digits: String) : List<String> {
    val result = mutableListOf<String>()
    if(digits.isEmpty()) return result

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

    fun backtrack(index: Int, current: StringBuilder){

        if(index == digits.length){
            result.add(current.toString())
        }

        val letters = map.get(digits[index]) ?: return
        for(letter in letters){
            current.append(letter)
            backtrack(index+1, current)
            current.deleteCharAt(current.length-1)
        }
    }

    backtrack(0, StringBuilder())

    return result
}

// https://leetcode.com/problems/distinct-subsequences
fun numDistinct(s: String, t: String): Int {
    val m = s.length
    val n = t.length

    val dp = IntArray(n+1){0}
    dp[0] = 1

    for(i in 1..m){
        for(j in n downTo 1){
            if(s[i-1] == t[j-1]){
                dp[j] += dp[j-1]
            }
        }
    }
    return dp[n]
}

// pascal triangle
fun generatePascalTriangle(numRows: Int): List<List<Int>>{
    val result:  MutableList<List<Int>> = mutableListOf()

    repeat(numRows){
        val list = IntArray(it+1) {1}

        if(it>1){
            for(i in 1 until list.size-1){
                list[i] = result[it-1][i-1] + result[it-1][i]
            }
            result.add(list.toList())
        }
    }
    return result
}

fun generate(numRows: Int): List<List<Int>> {
    var rows = mutableListOf<List<Int>>()

    val pascaleLineMap = mutableMapOf<Int, List<Int>>()
    for(lineNumber in 1..numRows){
        rows.add(pascaleLine(lineNumber, pascaleLineMap))
    }
    return rows
}

private fun pascaleLine(lineNumber: Int,
                        pascaleLineMap: MutableMap<Int, List<Int>>): List<Int> {
    if(lineNumber == 1) return listOf(1)
    if(lineNumber == 2) return listOf(1,1)

    if(pascaleLineMap.containsKey(lineNumber))
        return pascaleLineMap[lineNumber]!!

    val row = mutableListOf<Int>()
    row.add(1)

    val previous = pascaleLine(lineNumber-1, pascaleLineMap)

    for(i in 1 until lineNumber-1){
        row.add(previous[i-1] + previous[i])
    }
    row.add(1)

    return row.also{
        pascaleLineMap[lineNumber] = it
    }
}


fun groupAnagrams3(strs: Array<String>): List<List<String>> {
    val map = HashMap<String, MutableList<String>>()

    for(str in strs){
        val key = String(str.toSortedSet().toCharArray())
        if(!map.contains(key)){
            map[key] = mutableListOf()
        }
        map[key]?.add(str)
    }
    return map.values.toList()
}

fun isAnagram3(s: String, t: String): Boolean {
    if(s.length != t.length) return false
    val map: MutableMap<Char, Int> = mutableMapOf()
    s.forEach {
        map.put(it, map.getOrDefault(it,0) + 1)
    }

    t.forEach {
        if(!map.contains(it)){
            return false
        } else {
            var count = map.get(it)
            if(count == 0) return false
            map.put(it, count!!-1)
        }
    }
    return true
}

fun wordPattern3(pattern: String, s: String): Boolean {
    var list = s.split(" ")
    if(list.size!=pattern.length) return false

    var map = mutableMapOf<Char, String>()
    var i = 0

    while(i<pattern.length){
        if(!map.contains(pattern[i])){
            if(map.containsValue(list[i])) return false
            map[pattern[i]] = list[i]
        } else {
            val w = map.get(pattern[i])
            if(w!=list[i]) return false
        }
        i++
    }
    return true
}


fun isIsomorphic3(s: String, t: String): Boolean {
    if(s.length != t.length) return false

    var map: MutableMap<Char, Char> = mutableMapOf()
    var i = 0

    while(i<s.length){
        if(map.contains(s[i])){
            val sc = map.get(s[i])
            if(sc != t[i]) return false
        } else {
            if(map.containsValue(t[i])) return false
            map.put(s[i], t[i])
        }
        i++
    }
    return true

}

fun canConstruct3(ransomNote: String, magazine: String): Boolean {
    if(ransomNote.length>magazine.length) return false

    var map: MutableMap<Char, Int> = mutableMapOf()

    magazine.forEach {
        map[it] = map.getOrDefault(it, 0)+1
    }

    var charsFound = 0
    ransomNote.forEach {
        if(map.contains(it)){
            val cv = map.getValue(it)
            if(cv == 0) return false
            else map.put(it, cv-1)
            charsFound++
        }
    }
    return charsFound == ransomNote.length
}


fun lengthOfLongestSubstring(s: String): Int {
    val set: MutableSet<Char> = HashSet()
    var left = 0
    var right = 0
    var result = 0

    while(right< s.length){
        if(!set.contains(s[right])){
            set.add(s[right])
            right++
            result = maxOf(result, set.size)
        } else {
            set.remove(s[left])
            left++
        }
    }
    return result
}

fun isSubsequence3(s: String, t: String): Boolean {
    var i =0
    t.forEach {
        if(i<s.length && it == s[i]) i++
    }
    return i==s.length
}

fun isPalindrome3(s: String): Boolean {
    if(s.isEmpty()) return false
    var str = StringBuilder("")

    for(c in s){
        if(c in 'a'..'z' || c in '0'..'9'){
            str.append(c)
        }
        if(c in 'A'..'Z') str.append(c.lowercase())
    }

    var i = 0
    var j = str.length-1

    while(j>i){
        if(str[i] != str[j]) return false
        i++
        j--
    }
    return true
}

// https://leetcode.com/problems/text-justification/
fun fullJustifyTest(words: Array<String>, maxWidth: Int): List<String>{
    val res = mutableListOf<String>()
    val line = mutableListOf<String>()
    var length = 0
    var i = 0

    while(i<words.size){
        if(length + line.size + words[i].length > maxWidth){
            //line complete
            val extraSpaces = maxWidth - length
            val spaces = extraSpaces/ maxOf(1, line.size-1)
            var remainder = extraSpaces % maxOf(1, line.size-1)

            for(j in 0..<maxOf(1, line.size-1)){
                line[j] += " ".repeat(spaces)
                if(remainder>0){
                    line[j] += " "
                    remainder--
                }
            }
            res.add(line.joinToString(""))
            line.clear()
            length = 0
        }

        line.add(words[i])
        length += words[i].length
        i++
    }
    // handline last line
    var lastLine = line.joinToString(" ")
    val trailSpaces = maxWidth - lastLine.length
    res.add(lastLine + " ".repeat(trailSpaces))
    return res
}

fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
    val res = mutableListOf<String>()
    var line = mutableListOf<String>()
    var length = 0
    var i = 0 // no of words counter

    while(i<words.size){
        if((length + line.size + words[i].length) > maxWidth){
            // line complete
            val extraSpace = maxWidth - length
            val spaces = extraSpace/(maxOf(1, line.size)-1)
            var remainder = extraSpace%(maxOf(1, line.size-1))

            for(j in 1..maxOf(1,line.size-1)){
                line[j] += " ".repeat(spaces)
                if(remainder>0){
                    line[j] += " "
                    remainder--
                }
            }
            res.add(line.joinToString(""))
            line.clear()
            length = 0
        }

        line.add(words[i])
        length += words[i].length
        i += 1
    }
    // handling last line
    var lastLine = line.joinToString(" ")
    val trailSpace = maxWidth - lastLine.length
    res.add(lastLine + " ".repeat(trailSpace))
    return res
}

fun fullJustify2(words: Array<String>, maxWidth: Int): List<String> {
    val result = mutableListOf<String>()
    val currentLineWords = mutableListOf<String>()
    var availableSpacePerLine = maxWidth

    words.forEach{ word ->
        availableSpacePerLine -= word.length

        when{
            (availableSpacePerLine == 0) -> {
                currentLineWords.add(word)
                result.add(toLineString(availableSpacePerLine, currentLineWords))

                // Start a new line
                currentLineWords.clear()
                availableSpacePerLine = maxWidth
            }
            (availableSpacePerLine < 0) -> {
                availableSpacePerLine += (word.length + 1) //remove the claimed space for current
                result.add(toLineString(availableSpacePerLine, currentLineWords))

                // Start a new line
                currentLineWords.clear()
                currentLineWords.add(word)
                availableSpacePerLine = maxWidth - (word.length + 1)
            }
            (availableSpacePerLine > 0) -> {
                currentLineWords.add(word)
                availableSpacePerLine--
            }
        }
    }

    if(currentLineWords.isNotEmpty()) {
        result.add(toLineString(availableSpacePerLine+1, currentLineWords, true))
    }
    return  result
}

private val SEPARATOR = " "
fun toLineString(noOfSpaceToBeDistributed: Int,
                 wordsInLine: MutableList<String>,
                 isLastLine: Boolean = false) : String {
    return if(wordsInLine.size ==1 || isLastLine){
        wordsInLine.joinToString(SEPARATOR) + SEPARATOR.repeat(noOfSpaceToBeDistributed)
    } else {
        val spaceToBeAddedToAllWords = Math.floorDiv(noOfSpaceToBeDistributed,
            wordsInLine.lastIndex) +1

        for(i in 0 until noOfSpaceToBeDistributed % wordsInLine.lastIndex) {
            wordsInLine[i] += SEPARATOR
        }
        wordsInLine.joinToString(SEPARATOR.repeat(spaceToBeAddedToAllWords))
    }
}

fun strStr(haystack: String, needle: String): Int {
    for(i in 0..haystack.length-needle.length){
        val w = haystack.substring(i, i+needle.length)
        if(w==needle) return i
    }
    return -1
}

fun convert(s: String, numRows: Int): String {
    if(numRows == 1 || numRows>=s.length) return s

    var rowIndex = 1
    var goingDown = true
    var result = StringBuilder("")

    var map: MutableMap<Int, MutableList<Char>> = mutableMapOf()

    for(c in s){

        val key = rowIndex
        var list = map.getOrDefault(key, mutableListOf())
        list.add(c)
        map.put(key, list)

        if(goingDown && rowIndex<numRows){
            rowIndex++
        } else {
            goingDown = false
            rowIndex--

            if(rowIndex == 0){
                rowIndex = 2
                goingDown = true
            }
        }
    }

    for(key in map.keys){
        val list = map.getOrDefault(key, listOf())
        list.forEach { result.append(it) }
    }
    return result.toString()
}

// https://leetcode.com/problems/longest-palindromic-substring/
fun longestPalindrome(s: String) : String {
    if(s.length==1){
        return s
    }
    var result = ""

    for(i in s.indices){
        var temp = centerAndExpand(s, i, i)
        result = if (temp.length>result.length) temp else result

        temp = centerAndExpand(s, i, i+1)
        result = if (temp.length>result.length) temp else result
    }
    return result
}

fun centerAndExpand(s: String, left: Int, right: Int) : String {
    var l = left
    var r = right
    var longest = ""

    while(l>=0 && r< s.length &&  s[l] == s[r]){
        val str = s.substring(l,r+1)
        longest = if(longest.length>str.length) longest else str
        l--
        r++
    }
    return longest
}


fun countPalindromes(s: String): Int{
    if(s.isEmpty()) return 0

    var count = 0
    for(i in s.indices){
        // odd length palindromes
        count += expandAroundCenterAndCount(s, i, i)
        // even length palindromes
        count += expandAroundCenterAndCount(s, i, i+1)
    }
    return count
}

fun expandAroundCenterAndCount(s: String, left: Int, right: Int): Int {
    var l = left
    var r = right
    var count = 0

    while(l>=0 && r<s.length && s[l] == s[r]){
        count++
        l--
        r++
    }
    return count
}



