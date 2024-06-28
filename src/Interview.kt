class Interview {

}

fun main() {
    countPalindromes("abbac")
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

// what is difference between Any and Any?
// what is diff of scheduler and thread
// what is scheduler computation used for and what difference with IO if they both just background

