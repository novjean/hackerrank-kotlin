import jdk.jfr.DataAmount

class Scratch {
}

fun main(){
//    shiftAtK(intArrayOf(1,2,3,4,5,6,7), 3)
//    canCompleteCircuit2(intArrayOf(1,2,3,4,5), intArrayOf(3,4,5,1,2))
//    lengthOfLIS(intArrayOf(0,1,0,3,2,3))

    coinChange(intArrayOf(1, 2, 5), 11)
}

fun mergeIntervals5(intervals: Array<IntArray>): Array<IntArray> {
    intervals.sortBy{it[0]}
    val result = mutableListOf<IntArray>()

    intervals.forEach{
        if(result.isEmpty() || result.last()[1]>it[0]){
            result.add(it)
        } else {
            result.last()[1] = maxOf(result.last()[1], it[1])
        }
    }

    return result.toTypedArray()
}

fun closestNumbers2(nums: IntArray) {
    nums.sort()
    var minDiff = Int.MAX_VALUE

    for(i in 1..<nums.size){
        val diff = nums[i] - nums[i-1]
        minDiff = minOf(minDiff, diff)
    }

    for(i in 1..<nums.size){
        if(nums[i]-nums[i-1] == minDiff){
            println("${nums[i-1]} ${nums[i]}")
        }
    }
    return
}

fun maxProduct(nums: IntArray): Int {
    var res = nums.max()
    var min = 1
    var max = 1

    for(n in nums) {
        var temp = min * n

        // the min is required as the negative value to positive is easy with the
        // next number just being a negative
        min = minOf(n * max, n*min, n)
        max = maxOf(n * max, temp, n)
        res = maxOf(res, max)
    }

    return res
}

fun maxSubArrayIbm(nums: IntArray): Int {
    val dp = IntArray(nums.size)
    dp[0] = nums[0]

    for(i in 1..nums.size-1){
        dp[i] = maxOf(nums[i], nums[i] + dp[i-1])
    }
    return dp[nums.size-1]
}

fun lengthOfLongestSubstringIBM(s: String): Int {
    var set: MutableSet<Char> = mutableSetOf()
    var result = 0
    var l = 0
    var r = 0

    while(r < s.length){
        if(!set.contains(s[r])){
            set.add(s[r++])
            result = maxOf(result, set.size)
        } else {
            set.remove(s[l++])
        }
    }
    return result
}

fun minSubArrayLenIbm(target: Int, nums: IntArray): Int {
    var s = 0
    var res = Int.MAX_VALUE
    var sum = 0

    for(i in 0..nums.size-1){
        sum += nums[i]

        while(sum>=target){
            res = minOf(res, i-s+1)
            sum -= nums[s++]
        }
    }
    return if(res!=Int.MAX_VALUE) res else 0
}


fun maxArea33(heights: IntArray): Int {
    var i = 0
    var j = heights.size-1
    var maxArea = 0

    while(j>i){
        val w = j-i
        val h = minOf(heights[i],heights[j])
        val area = w*h
        maxArea = maxOf(maxArea, area)

        if(heights[i] == heights[j]){
            i++
            j--
        } else if(heights[i]<heights[j]){
            i++
        } else j--
    }
    return maxArea
}

fun isSubsequence4(s: String, t: String): Boolean {
    if(s.length>t.length) return false
    var idx = 0
    for(i in 0..t.length-1){
        if(s[idx] == t[i]){
            idx++
            if(idx == s.length) return true
        }
    }
    return false
}

fun canCompleteCircuit2(gas: IntArray, cost: IntArray): Int {
    val n = gas.size
    var totalGas = 0
    var curGas = 0
    var startIdx = 0

       for(i in 0..n-1){
           val cost = cost[i]
           val g = gas[i]
           val diff = g-cost

           totalGas += diff
           curGas += diff

           if(curGas<0){
               curGas=0
               startIdx = i+1
           }

       }
    return if(totalGas>=0) startIdx else -1
}

fun productExceptSelf1(nums: IntArray): IntArray {
    var res = IntArray(nums.size)

    var prefix = 1
    for(i in 0 .. nums.size-1){
        res[i] *= prefix
        prefix *= nums[i]
    }
    var postfix = 1
    for(i in nums.size-1 downTo 0){
        res[i] *= postfix
        postfix *= nums[i]
    }
    return res
}

fun canJump3(nums: IntArray): Boolean {
    var lastIndex = nums.size-1

    for(i in nums.size-2 downTo 0){
        if(nums[i] + i >= lastIndex){
            lastIndex = i
        }
    }
    return lastIndex == 0
}

fun maxProfit4(prices: IntArray): Int {
    var profit = 0

    for(i in 1..prices.size-1){
        if(prices[i] > prices[i-1]){
            profit+=prices[i]-prices[i-1]
        }
    }
    return profit
}

fun maxProfit3(prices: IntArray): Int {
    var maxP = 0
    var buy = prices[0]

    for(i in 1 until prices.size){
        if(prices[i]<buy){
            buy = prices[i]
        } else {
            val diff = prices[i]-  buy
            if(diff>maxP){
                maxP = diff
            }
        }
    }
    return maxP
}

fun shiftAtK(nums:IntArray, k:Int){
    var K= k % nums.size
    reverse(nums, 0, nums.size-1)
    reverse(nums, 0, K-1)
    reverse(nums, K, nums.size-1)
}

fun reverse(nums: IntArray, firstIndexPointer: Int, lastIndexPointer: Int){
    var f = firstIndexPointer
    var l = lastIndexPointer
    while (f<l) {
        val temp = nums[l]
        nums[l--] = nums[f]
        nums[f++] = temp
    }

}

fun majorityElement4(nums: IntArray): Int {
    val map: MutableMap<Int, Int> = HashMap()

    for(num in nums){
        if(!map.contains(num)){
            map.put(num,1)
        } else{
            map.put(num, map.getOrDefault(num, 0) + 1)
        }
    }

    var count = 0
    var res = 0
    for(key in map.keys){
        val v : Int = map.get(key)!!
        if(v > count){
            res = key
            count = v
        }
    }
    return res

}
