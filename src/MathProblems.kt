fun main() {
//    threeSum2(intArrayOf(-1,0,1,2,-1,-4))
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
        start = Math.min(intervals[counter][0], start)
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
    var result : MutableList<IntArray> = mutableListOf()

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
    var list: MutableList<String> = mutableListOf()
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

fun longestConsecutive3(nums: IntArray): Int {
    if(nums.isEmpty()) return 0
    if(nums.size == 1) return 1

    nums.sort()
    var list = nums.distinct()
    var count = 1
    var longest = 1

    for(r in 1..list.size-1){
        val diff = list[r] - list[r-1]
        if(diff==1)
            longest = maxOf(longest, count++)
        else count = 1
    }
    return longest
}

fun containsNearbyDuplicate3(nums: IntArray, k: Int): Boolean {
    var map: MutableMap<Int, Int> = mutableMapOf()

    nums.forEachIndexed {index, it->
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
    var sum:Long = 0

    var i = 0

    while(i<10){
        while(num>0){
            var n = num % 10
            sum += n*n
            num = num/10
        }

        if(sum == 1L) return true
        if(sum>=Int.MAX_VALUE) break
        num = sum.toInt()
        sum = 0
        i++
    }
    return false
}

fun minSubArrayLen3(target: Int, nums: IntArray): Int {
    var l = 0
    var res = Int.MAX_VALUE
    var s = 0

    for(r in nums.indices){
        s += nums[r]

        while(s>= target){
            res = minOf(res, r-l+1)
            s -= nums[l++]
        }
    }
    return if(res!=Int.MAX_VALUE) res else 0
}

fun minSubArrayLenBruteForce(target: Int, nums: IntArray): Int {
    if(nums.isEmpty()) return -1
    var i = 0
    var j = 1
    var sum = 0
    var min = nums.size+1

    for(i in 0.. nums.size-1){
        sum = 0
        for(j in i..nums.size-1){
            sum+= nums[j]
            if(sum>=target){
                min = minOf(min, j-i )
                break
            }
        }
    }
    return if(min == nums.size+1) 0 else min

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

