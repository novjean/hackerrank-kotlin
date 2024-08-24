import java.util.LinkedList
import java.util.Queue

fun main() {
//    threeSum2(intArrayOf(-1,0,1,2,-1,-4))
//    trailingZeroes(13)
//    twoSum4(intArrayOf(1,2,3,4), 3)
    // calcEquation()
}

// https://leetcode.com/problems/evaluate-division/
private val graph = mutableMapOf<String, MutableMap<String, Double>>()

// time O(E+Q * (V+E)) where Q is the num of queries
// space O(E+V)
fun calcEquation(equations: List<List<String>>,
                 values: DoubleArray,
                 queries: List<List<String>>): DoubleArray {
    buildGraph(equations, values)
    return queries.map { (numerator, denominator) ->
        bfs(numerator, denominator)
    }.toDoubleArray()
}

// time O(V+E) where V is num of nodes and E is num of edges
// space O(V)
private fun bfs(start: String, end: String): Double {
    if(!graph.containsKey(start) || !graph.containsKey(end)) return -1.0

    val queue: Queue<Pair<String, Double>> = LinkedList()
    queue.add(Pair(start, 1.0))
    val visited = mutableListOf<String>()
    visited.add(start)

    while(queue.isNotEmpty()){
        val (node, weight) = queue.poll()
        if(node == end) return weight
        graph[node]?.forEach{ (neighbor, value) ->
            if(!visited.contains(neighbor)){
                visited.add(neighbor)
                queue.add(Pair(neighbor, weight * value))
            }
        }
    }
    return -1.0
}

// time O(E) is the number of equations
// space O(E)
fun buildGraph(equations: List<List<String>>, values: DoubleArray){
    equations.forEachIndexed{ index, (numerator, denominator) ->
        val value = values[index]
        graph.getOrPut(numerator) { mutableMapOf() }[denominator] = value
        graph.getOrPut(denominator) { mutableMapOf() } [numerator] = 1.0/value
    }
}

// https://leetcode.com/problems/number-complement/
fun findComplementBitManipulation(num:Int): Int{
    var res: Long = 1
    while(res<=num) res = res shl 1
    return (res-1).toInt() xor num
}

// time O(n) n is length of num
// space O(n)
fun findComplement(num: Int): Int {
    val str = StringBuilder()
    for(d in num.toString(2)){
        str.append(if(d=='1')'0' else '1')
    }
    return str.toString().toInt(2) // convert the binary complement string back to an integer
}

// https://leetcode.com/problems/factorial-trailing-zeroes/
// time O(log5(n)) where base of the algorithm is 5
// space O(1)
fun trailingZeroes(n: Int): Int {
    var result = 0
    var currentFactor = 5

    while(currentFactor<=n){
        result+= n/currentFactor
        currentFactor *=5
    }
    return result
}

fun findMinArrowShots(points: Array<IntArray>): Int {
    points.sortBy { it[1] }
    var arrows = 1
    var prevEnd = points[0][1] // this denoted end point of balloon

    for(i in 1..points.size-1){
        if(points[i][0] > prevEnd){
            arrows++
            prevEnd = points[i][1]
        }
    }
    return arrows
}

fun insertInterval(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
    var ans = mutableListOf<IntArray>()
    var start = newInterval[0]
    var end = newInterval[1]
    var counter = 0

    while(counter<intervals.size && intervals[counter][1]<start)
        ans.add(intervals[counter++])

    while(counter<intervals.size && intervals[counter][0] <= end){
        start = minOf(intervals[counter][0], start)
        end = maxOf(intervals[counter][1], end)
        counter++
    }

    ans.add(intArrayOf(start, end))

    while(counter<intervals.size)
        ans.add(intervals[counter++])

    return ans.toTypedArray()
}

fun mergeIntervals3(intervals: Array<IntArray>): Array<IntArray> {
    intervals.sortBy{it[0]}
    val result : MutableList<IntArray> = mutableListOf()

    intervals.forEach {
        if(result.isEmpty() || result.last()[1] < it[0]){
            result.add(it)
        } else {
            result.last()[1] = maxOf(it[1], result.last()[1])
        }
    }
    return result.toTypedArray()
}

fun summaryRanges3(nums: IntArray): List<String>{
    if(nums.isEmpty()) return emptyList()
    val list: MutableList<String> = mutableListOf()
    var l = 0
    list.add(nums[0].toString())

    for(r in 1..nums.size-1){
        val diff = nums[r] - nums[r-1]
        if(diff==1){
            if(r-l == 1){
                list.removeLast()
            }
            if(r == nums.size-1){
                list.add("${nums[l]}->${nums[r]}")
            }
            continue
        } else {
            list.add("${nums[l]}->${nums[r-1]}")
            l = r
            list.add("${nums[r]}")
        }
    }
    return list
}

// time O(n)
// space O(n)
fun longestConsecutive3(nums: IntArray): Int {
    val numSet = mutableSetOf<Int>()
    nums.forEach { numSet.add(it) }

    var maxLength = 0
    for(i in nums.indices){
        var length = 1
        var num = nums[i]

        if(!numSet.contains(num-1)){
            while(numSet.contains(num+1)){
                num++
                length++
            }
        }
        maxLength = maxOf(maxLength, length)
    }
    return maxLength
}

// time O(n)
// space O(n)
fun containsNearbyDuplicate3(nums: IntArray, k: Int): Boolean {
    val map: MutableMap<Int, Int> = mutableMapOf()

    nums.forEachIndexed { index, it->
        if(!map.contains(it)){
            map[it] = index
        } else {
            val prevIndex = map.getValue(it)
            if(index - prevIndex <= k) return true
            map[it] = index
        }
    }
    return false
}

fun isHappy(number: Int): Boolean {
    var num = number
    var sum = 0L

    var i = 0

    while(i<10){
        while(num>0){
            val n = num % 10
            sum += n*n
            num /= 10
        }

        if(sum == 1L) return true
        if(sum>=Int.MAX_VALUE) break
        num = sum.toInt()
        sum = 0
        i++
    }
    return false
}

fun threeSum3(numbers: IntArray): List<List<Int>> {
    if(numbers.size<=2) return emptyList()
    numbers.sort()
    var i = 0
    var j = 1
    var k = numbers.size-1
    var sum = 0
    var result: MutableSet<List<Int>> = HashSet()

    while(i<numbers.size-2){
        j=i+1
        k=numbers.size-1
        while(j<k){
            sum = numbers[i]+numbers[j]+numbers[k]
            if(sum==0){
                result.add(listOf(numbers[i], numbers[j], numbers[k]))
                j++
                k--
            } else if(sum>0) k--
            else j++
        }
        i++
    }

    return result.toList()
}

fun maxArea3(heights: IntArray): Int {
    var i = 0
    var j = heights.size-1
    var maxArea = 0

    while(j>i){
        val w = j-i
        val h = Math.min(heights[j],heights[i])
        var area = h*w

        maxArea = maxOf(area, maxArea)

        if(heights[i]<heights[j]) i++
        else if(heights[i] > heights[j]) j--
        else {
            i++
            j--
        }
    }
    return maxArea
}

fun twoSum3(nums: IntArray, target: Int): IntArray {
    var i = 0
    var j = nums.size-1
    var sum = 0

    while(j>i){
        sum = nums[i]+nums[j]
        if(sum == target) {
            return intArrayOf(i+1, j+1)
        } else if(sum>target){
            j--
        } else {
            i++
        }
    }
    return intArrayOf(0,0)
}

// square root of a number
fun mySqrt(x: Int): Int{
    if(x == 0 || x == 1) return x

    var left = 1
    var right = x
    var result = 0 //9, 4

    while(left<=right){
        val mid = left +(right-left)/2 //5, 2
        if(mid<= x/mid){ //5<4
            result = mid
            left = mid+1
        } else {
            right = mid-1 //4
        }
    }
    return result
}

// time O(1)
fun twoSum4(nums: IntArray, target: Int): IntArray{
    val map = HashMap<Int, Int>()
    for(i in 0..<nums.size){
        val complement = target - nums[i]
        if(map.containsKey(complement)){
            print("map contains key of $complement")
            return intArrayOf(map[complement]!!, i+1)
        }
        map[complement] = nums[i]
    }
    return intArrayOf(0,0)
}

fun threeSum2(numbers: IntArray): List<List<Int>> {
    var set: MutableSet<List<Int>> = HashSet()

    if(numbers.size<=2) return emptyList()

    numbers.sort()
    var i =0
    var j= 1
    var k = numbers.size-1

    while(i<numbers.size-2){
        j=i+1
        k = numbers.size-1

        while(j<k){
            val sum = numbers[i] + numbers[j] + numbers[k]

            if(sum == 0){
                val successList = listOf(numbers[i], numbers[j], numbers[k])
                set.add(successList)
                j++
                k--
            } else if(sum>0){
                k--
            } else {
                j++
            }
        }

        i++
    }

    return set.toList()
}

