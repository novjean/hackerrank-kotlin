import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class StringProblems {

}

fun main() {
//    countPalindromes("abbac")
//    longestPalindrome2("babad")
//    convert("PAYPALISHIRING", 3)
//    println(strStr("mississippi", "issip"))
//    isPalindrome3("race a car")
//    isSubsequence3("abc", "ahbgdc")
//    canConstruct3("bg", "efjbdfbdgfjhhaiigfhbaejahgfbbgbjagbddfgdiaigdadhcfcj")
    isIsomorphic3("badc", "baba")
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

fun lengthOfLongestSubstring3(s: String): Int {
    var set: MutableSet<Char> = HashSet()
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

fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
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

fun longestPalindrome2(s: String) : String {
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

// what is diff of scheduler and thread
// what is scheduler computation used for and what difference with IO if they both just background

