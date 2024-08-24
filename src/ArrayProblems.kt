import java.util.*
import kotlin.collections.HashMap

fun main() {
//    splitArray(intArrayOf(7,2,5,10,8), 2)
    largestRectangle(intArrayOf(3,2,3))
}

// https://leetcode.com/problems/find-pivot-index/
// time o(n)
// space O(1)
fun pivotIndex2(nums: IntArray): Int {
    var rsum = nums.sum()
    var lsum = 0
    for(i in 0..nums.size-1){
        rsum -= nums[i]
        if (rsum == lsum) return i
        lsum += nums[i]
    }
    return -1
}

// time O(n)
// space O(n)
fun pivotIndex(nums: IntArray): Int {
    val prefixArrLeft = IntArray(nums.size)
    val prefixArrRight = IntArray(nums.size)
    var start = 0
    var end = nums.size-1
    prefixArrLeft[start] = nums[start++]
    prefixArrRight[end] = nums[end--]

    while(start<nums.size && end>=0){
        prefixArrLeft[start] = prefixArrLeft[start-1] +nums[start]
        prefixArrRight[end] = prefixArrRight[end+1] + nums[end]
        start++
        end--
    }
    for(i in nums.indices){
        if(prefixArrLeft[i] == prefixArrRight[i])
            return i
    }

    return -1
}

// https://www.hackerrank.com/challenges/largest-rectangle/
// time O(n)
// space O(n)
fun largestRectangle(h: IntArray): Long {
    val stack = Stack<Pair<Int, Int>>()
    var maxArea = 0
    var i = 0

    while(i<h.size){
        var start = i
        while(stack.isNotEmpty() && stack.last().second > h[i]){
            val (index, height) = stack.pop()
            maxArea = maxOf(maxArea, height * (i-index))
            start = index
        }
        stack.push(Pair(start, h[i]))
        i++
    }

    for((index, height) in stack){
        maxArea = maxOf(maxArea, height * (h.size - index))
    }
    return maxArea.toLong()
}

// https://leetcode.com/problems/subarray-sum-equals-k/
// time O(n)
// space O(n)
fun subarraySum(nums: IntArray, k: Int): Int {
    val prefixSums = HashMap<Int, Int>()
    var curSum = 0
    var res = 0

    for(num in nums){
        curSum += num
        val diff = curSum - k

        if(curSum == k) res += 1

        res += prefixSums.getOrDefault(diff, 0)
        prefixSums[curSum] = prefixSums.getOrDefault(curSum, 0) + 1
    }
    return res
}

// https://leetcode.com/problems/continuous-subarray-sum/
// time O(n)
fun checkSubarraySum(nums: IntArray, k: Int): Boolean {
    val remainder = mutableMapOf(0 to -1)
    remainder[0] = -1
    var total = 0

    for(i in nums.indices){
        val num = nums[i]
        total+=num
        val rem = total % k

        if(!remainder.contains(rem)){
            remainder[rem] = i
        } else if(i - remainder[rem]!! > 1){
            return true
        }
    }
    return false
}

// https://leetcode.com/problems/split-array-largest-sum/
fun splitArray(nums: IntArray, k: Int): Int {
    var start = nums.max()
    var end = nums.sum()

    while(start < end) {

        val middle = start + (end-start)/2

        var subArrayCount = 1
        var sum = 0

        for (num in nums) {
            if(sum + num > middle) {
                sum = num
                subArrayCount ++
            } else {
                sum += num
            }
        }

        if(subArrayCount <= k) {
            end = middle
        } else {
            start = middle + 1
        }
    }
    return end
}

fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
    var i = m-1
    var j = n-1
    var k = m+n-1

    while(j>=0){
        if(i<0 || nums2[j]>nums1[i]){
            nums1[k] = nums2[j]
            j--
            k--
        } else {
            nums1[k] = nums1[i]
            i--
            k--
        }
    }
}

fun removeElement3(nums: IntArray, `val`: Int): Int{
    var k =0
    nums.forEach {
        if(it != `val`){
            nums[k++] = it
        }
    }
    return k
}

fun removeDuplicates3(nums: IntArray): Int {
    var j = 0

    for(i in 1..nums.size-1){
        if(nums[i]!=nums[j]){
            j++
            nums[j] = nums[i]
        }
    }
    return j+1
}

fun removeDuplicates4(nums: IntArray): Int {
    if(nums.size<=2) return nums.size

    var slow = 2
    for(fast in 2..nums.size-1){
        if(nums[slow-2] != nums[fast]){
            nums[slow++] = nums[fast]
        }
    }
    return slow
}