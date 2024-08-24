import jdk.jfr.DataAmount
import java.util.PriorityQueue
import kotlin.math.abs

class Scratch {

}



class MedianFinder() {
    private val list = mutableListOf<Int>()

    fun addNum(num: Int){
        val insertionPoint = list.binarySearch(num)
        // if num is not found in list, the insertion point
        // would be where the number should be inserted but negative
        val index = if(insertionPoint<0) -insertionPoint-1 else insertionPoint
        list.add(index, num)
    }

    fun findMedian(): Double {
        if(list.isEmpty()) return 0.0

        val mid = list.lastIndex/2
        return if (list.size%2==0) (list[mid].toDouble() + list[mid+1])/2
        else list[mid].toDouble()
    }
}

class MySet<T>(){
    val set = mutableListOf<T>()

    fun add(element: T): Boolean{
        if(!set.contains(element)) {
            set.add(element)
            return true
        }
        return false
    }

    fun remove(element: T): Boolean{
        return set.remove(element)
    }

    fun contains(element: T): Boolean{
        return set.contains(element)
    }

    fun size(): Int{
        return set.size
    }

    fun isEmpty(): Boolean{
        return set.isEmpty()
    }

    fun clear(){
        set.clear()
    }

    fun toList(): List<T>{
        return set.toList()
    }
}

// https://leetcode.com/problems/range-sum-query-immutable/
// time O(n)
// space O(n)
class NumArray(val nums: IntArray) {
    private val prefixSum = IntArray(nums.size){0}
    init {
        prefixSum[0] = nums[0]
        for(i in 1..<nums.size){
            prefixSum[i] = prefixSum[i-1] + nums[i]
        }
    }
    fun sumRange(left: Int, right: Int): Int {
        return prefixSum[right] - prefixSum[left] + nums[left]
    }
}

class NumMatrix(val matrix: Array<IntArray>) {
    val rows = matrix.size
    val cols = if(rows>0) matrix[0].size else 0
    val prefixSum = Array(rows){IntArray(cols)}

    init {
        for(r in 0..<rows){
            var prefix = 0
            for(c in 0..<cols){
                prefix+=matrix[r][c]
                prefixSum[r+1][c+1] = prefix + prefixSum[r][c+1]
            }
        }
    }

    fun sumRegion(row1: Int, col1: Int, row2: Int, col2: Int): Int {
        // the last prefixSum being added is the top left and we remove that twice
        // so we add that one value
        return prefixSum[row2+1][col2+1] - prefixSum[row1][col2+1] -
                prefixSum[row2+1][col1] + prefixSum[row1][col1]
    }

}

fun main(){
//    shiftAtK(intArrayOf(1,2,3,4,5,6,7), 3)
//    canCompleteCircuit2(intArrayOf(1,2,3,4,5), intArrayOf(3,4,5,1,2))
//    lengthOfLIS(intArrayOf(0,1,0,3,2,3))

//    coinChange(intArrayOf(1, 2, 5), 11)
//    repeatedSubstringPattern("abab")
//    rotateLeft(2, intArrayOf(1,2,3,4,5))

//    jumping(mutableListOf(1,4,10,6,2))
//    containerMostWater(intArrayOf(1,8,6,2,5,4,8,3,7))
//    kthLargestItem(intArrayOf(3,2,3,1,2,4,5,5,6), 4)
//    findMaxLength(intArrayOf(0,1,1,0,1,1,1,0))

    interestRate(100, 20, 170)
}

// https://app.codesignal.com/arcade/intro/level-7/8PxjMSncp9ApA4DAb
fun interestRate(deposit: Int, rate: Int, threshold: Int): Int {
    var curr = deposit.toDouble()
    val r = rate/100.0
    var numYears = 0
    while(curr<threshold){
        curr += (curr * r)
        numYears++
    }
    return numYears
}

fun kthLargestItem(nums: IntArray, k: Int): Int{
       val minHeap = PriorityQueue<Int>()
       for(num in nums){
           minHeap.add(num)
           if(minHeap.size>k){
               minHeap.remove()
           }
       }
    return minHeap.peek()
}

// https://leetcode.com/problems/contiguous-array/
// time O(n)
// space O(n)
fun findMaxLength(nums:IntArray): Int{
    val mp = HashMap<Int, Int>()
    var sum = 0
    var subArrayLength = 0

    for(i in nums.indices){
        sum += if(nums[i] == 0) -1 else 1

        if(sum == 0){
            subArrayLength = i+1
        }else if(mp.contains(sum)){
            subArrayLength = maxOf(subArrayLength, i-mp[sum]!!)
        } else {
            mp[sum] = i
        }
    }

    return subArrayLength
}


// https://leetcode.com/problems/minimum-size-subarray-sum/
// time O(n)
fun minSubArrayLen2(target: Int, nums: IntArray): Int {
    var minLength = Int.MAX_VALUE
    var left = 0
    var sum = 0

    // sliding window
    for(right in nums.indices){
        sum += nums[right] // prefixSum

        while(sum>=target){
            minLength = minOf(minLength, right-left+1)
            sum-=nums[left]
            left++
        }
    }
    return if(minLength == Int.MAX_VALUE) 0 else minLength
}


fun mergeThreeSortedArrays(arr1: IntArray, arr2: IntArray, arr3: IntArray): IntArray {
    var result = mutableListOf<Int>()
    var i =0
    var j =0
    var k = 0

    while(i<arr1.size || j<arr2.size || k<arr3.size){
        val va1 = if(i<arr1.size) arr1[i] else Int.MAX_VALUE
        val va2 = if(i<arr2.size) arr2[i] else Int.MAX_VALUE
        val va3 = if(i<arr3.size) arr3[i] else Int.MAX_VALUE

        if(va1<=va2 && va1<=va3){
            result.add(va1)
            i++
        } else if(va2<=va1 && va2<=va3){
            result.add(va2)
            j++
        }  else {
            result.add(va3)
            k++
        }
    }
    return result.toIntArray()
}

// https://leetcode.com/problems/powx-n/
fun myPow(x: Double, n: Int): Double {
    val signBiasX = if(n<0) 1.0/x else x

    fun exponent(base: Double, exp: Int): Double{
        when(exp){
            0 -> return 1.0
            1 -> return base
            else -> {
                val res = exponent(base, exp/2)
                return if(exp%2 == 0) res * res
                else base * res * res
            }
        }
    }

    return exponent(signBiasX, abs(n))
}

// replace element in array
fun solution(list: MutableList<Int>, element: Int, substitute: Int): MutableList<Int>{
    list.forEachIndexed{i, it -> if(it==element){list[i] = substitute} }
    return list
}

// https://app.codesignal.com/arcade/intro/level-5/XC9Q2DhRRKQrfLhb5
fun jumping2(inputArray: MutableList<Int>): Int {
    var i =0
    while(true){
        i++
        if(inputArray.all{ it%i != 0}){
            return i
        }
    }
}

fun jumping(inputArray: MutableList<Int>): Int {
    inputArray.sort()
    val high = inputArray.last()
    for(i in 2..<high){
        var cur = i
        var mul = 1
        while(cur<=high){
            if(inputArray.contains(cur)) break
            cur = i * ++mul
        }
        if(cur>high) return i
    }
    return high+1
}

fun rotateLeft2(d: Int, arr: IntArray): IntArray{
    val n = arr.size
    // To handle cases where d > n
    val rotations = d % n

    // Split and concatenate the array
    return arr.sliceArray(rotations until n) + arr.sliceArray(0 until rotations)
}

fun rotateLeft(d: Int, arr: IntArray): IntArray {
    // Write your code here
    val n = arr.size

    for(i in 0..n/2){
        var temp = arr[i]
        arr[i] = arr[n-i-1]
        arr[n-i-1] = temp
    }

    val firstHalfSize = n-d

    for(i in 0..<firstHalfSize/2){
        var temp = arr[i]
        arr[i] = arr[firstHalfSize-i-1]
        arr[firstHalfSize-i-1] = temp
    }

    println("1st:" + arr.toList().toString())

    val secondHalfSize = n - firstHalfSize

    for(i in 0..<secondHalfSize/2){
        var temp = arr[i+firstHalfSize]
        arr[i+firstHalfSize] = arr[n-i-1]
        arr[n-i-1] = temp
    }
    println("2nd:" + arr.toList().toString())

    return arr
}

// time O(n)
fun repeatedSubstringPatternLPS(s: String): Boolean {
    val n = s.length
    val lps = IntArray(n)
    var len = 0
    var i = 1

    while(i<n){
        if(s[i] == s[len]){
            len++
            lps[i] = len
            i++
        } else {
            if(len!=0){
                len = lps[len-1]
            } else{
                lps[i]=0
                i++
            }
        }
    }

    val lengthOfPattern = lps[n-1]
    return lengthOfPattern>0 && n % (n-lengthOfPattern)==0
}

fun repeatedSubstringPattern(s: String): Boolean {
    val len = s.length

    for(i in 1..len/2){
        val sub = s.substring(0,i)
        var j = i
        while(j<=len-i){
            val sub2 = s.substring(j, j+i)
            if(sub != sub2) break
            j = j+i
            if(j==len) return true
        }
    }
    return false
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
