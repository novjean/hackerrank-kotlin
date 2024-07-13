fun main() {

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