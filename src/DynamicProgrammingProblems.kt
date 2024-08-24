class DynamicProgrammingProblems {
}

fun main() {

}

//
fun isInterleave(s1: String, s2: String, s3: String): Boolean {
    if((s1+s2).length != s3.length) return false
    val dp = mutableMapOf<Pair<Int, Int>, Boolean>()

    fun dfs(i: Int, j: Int): Boolean {
        if(i == s1.length && j == s2.length) return true
        val key = Pair(i,j)
        if(dp.contains(key)) return dp[key]!!

        if(i<s1.length && s1[i] == s3[i+j] && dfs(i+1, j)) return true
        if(j<s2.length && s2[j] == s3[i+j] && dfs(i, j+1)) return true
        dp[key] = false
        return false
    }

    return dfs(0,0)
}

// https://leetcode.com/problems/house-robber/
// time O(n)
// space O(n)
fun rob(nums: IntArray): Int {
    if(nums.isEmpty()) return 0 // no house to rob
    if(nums.size == 1) return nums[0] // only one house to rob

    val dp = IntArray(nums.size+1)
    dp[0] = 0 // no house robbed
    dp[1] = nums[0] // 1st house robbed

    for(i in 1 until nums.size){
        // compare between last house cash or the cash before that with the current house cash
        dp[i+1] = maxOf(dp[i], dp[i-1] + nums[i])
    }

    return dp[nums.size]
}

// https://leetcode.com/problems/coin-change/
// time O(coins*amount)
// space O(coins*amount)
fun coinChange2(coins: IntArray, amount: Int): Int{

}

fun coinChange(coins: IntArray, amount: Int): Int {
    val dp = IntArray(amount+1) {amount+1}
    dp[0] = 0

    for(coin in coins){
        for(i in coin..amount){
            dp[i] = minOf(dp[i], dp[i-coin] + 1)
        }
    }
    return if(dp[amount] > amount) -1 else dp[amount]
}


// https://leetcode.com/problems/longest-increasing-subsequence
fun lengthOfLIS(nums: IntArray): Int {
    val dp = IntArray(nums.size){1}
    var max = 0

    for(i in 0..nums.size-1){
        for(j in 0..i-1){
            if(nums[j] <nums[i]){
                dp[i] = maxOf(dp[i], dp[j]+1)
            }
        }
        max = maxOf(max, dp[i])
    }
    return max
}


// https://leetcode.com/problems/climbing-stairs/
// time O(n)
// space O(1)
fun climbStairs(n: Int): Int {
    if(n<=2) return n

    var oneStepBefore = 2
    var twoStepsBefore = 1

    for(i in 3..n){
        val currStep = twoStepsBefore + oneStepBefore
        twoStepsBefore = oneStepBefore
        oneStepBefore = currStep
    }
    return oneStepBefore
}

fun climbStairs2(n: Int): Int {
    val nums = IntArray(n+1) {0}
    nums[0] = 1
    nums[1] = 1

    for(i in 2..n){
        nums[i] = nums[i-1] + nums[i-2]
    }

    return nums[n-1]
}

// https://leetcode.com/problems/word-break/
fun wordBreak(s: String, wordDict: List<String>): Boolean {
    val dp = BooleanArray(s.length+1) {false}
    dp[s.length] = true

    for(i in s.length downTo 0){
        for(w in wordDict){
            if(i + w.length <= s.length && w == s.substring(i, i+w.length))
                dp[i] = dp[i + w.length]
            if(dp[i]) break
        }
    }
    return dp[0]
}