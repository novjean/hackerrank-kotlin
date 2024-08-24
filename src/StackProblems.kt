import java.util.*
import kotlin.collections.ArrayDeque

class StackProblems {

}

fun main(){
//    maxSlidingWindow(intArrayOf(1,3,-1,-3,5,3,6,7), 3)
    simpleTextEditor()
}


fun simpleTextEditor(){
    val ops = mutableListOf<String>()

    val sc = Scanner(System.`in`)
    val q = sc.nextInt()

    for(i in 0..q){
        ops.add(sc.nextLine())
    }

    val stack = Stack<StringBuilder>()
    stack.push(StringBuilder())

    for(i in 0..<ops.size){
        val input = ops[i].split(" ")
        val op = input[0]
        when(op){
            "1" -> {
                // append string W to end of S
                val s = stack.peek()
                val w = input[1]
                s.append(w)
                stack.push(s)
                break
            }
            "2" -> {
                // delete last k characters of S
                var s = stack.peek()
                val k = input[1].toInt()
                if(k>s.length){
                    s.clear()
                    stack.push(s)
                } else {
                    val end = s.length - k
                    val str = s.toString().substring(0, end)
                    val sb = StringBuilder(str)
                    stack.push(sb)
                }
            }
            "3" -> {
                // print the k-th character of S
                val s = stack.peek()
                val k = input[1].toInt()-1
                println(s[k])
            }
            "4" -> {
                // undo the last operation of type 1 or 2
                if(stack.isNotEmpty()) stack.pop()
            }
        }
    }
}

// https://www.hackerrank.com/challenges/game-of-two-stacks/problem
// time O(n+m)
// space O(1)
fun twoStacks(maxSum: Int, a: Array<Int>, b: Array<Int>): Int {
    var maxCount = 0
    var i = 0
    var sum = 0

    while(i<a.size && sum+a[i]<=maxSum){
        sum+=a[i++]
    }
    maxCount = i

    var j=0
    while(j<b.size && i>=0){
        sum+=b[j++]
        while(sum>maxSum && i>0){
            i--
            sum-=a[i]
        }
        if(sum<=maxSum){
            maxCount = maxOf(maxCount, i+j)
        }
    }
    return maxCount
}

fun equalStacks(h1: Array<Int>, h2: Array<Int>, h3: Array<Int>): Int {
    // Write your code here
    var h1Total = 0
    var h2Total = 0
    var h3Total = 0

    for(w in h1){ h1Total += w }
    for(w in h2){ h2Total += w }
    for(w in h3){ h3Total += w }

    var i = 0
    var j = 0
    var k = 0

    while(i<h1.size && j<h2.size && k<h3.size){
        if(h1Total == h2Total && h2Total == h3Total) return h1Total

        if(h1Total>=h2Total && h1Total>=h3Total){
            h1Total -= h1[i++]
        } else if(h2Total>=h1Total && h2Total>=h3Total){
            h2Total -= h2[j++]
        } else {
            h3Total -= h3[k++]
        }
    }
    return 0
}

// find the maximum value on every slide of the window
fun maxSlidingWindow(nums: IntArray, k: Int): IntArray{
    val result = mutableListOf<Int>()
    val deque = ArrayDeque<Int>()

    for(i in 0..<nums.size){
        while(deque.isNotEmpty() && nums[i]>deque.last()){
            deque.removeLast()
        }
        deque.addLast(nums[i])

        if(i >= k-1){
            result.add(deque.first())

            if(nums[i - k+1] == deque.first()){
                deque.removeFirst()
            }
        }
    }
    return result.toIntArray()
}

fun evalRPN3(tokens: Array<String>): Int = Stack<Int>().run {
    for(s in tokens)
        push(
            when(s) {
                "+" -> pop() + pop()
                "-" -> -pop() + pop()
                "*" -> pop() * pop()
                "/" -> pop().let{pop()/it}
                else -> s.toInt()
            }
        )
    pop()
}

data class Node1(
    val value: Int,
    val min:Int,
    var next: Node1? = null
)

class MinStack3() {
    var top: Node1? = null

    fun push(value: Int) {
        var min: Int = value

        top?.min?.let{
            if(value<it) min = value
            else min = it
        }

        val node = Node1(value, min)
        node.next = top
        top = node
    }

    fun pop() {
        top = top?.next
    }

    fun top(): Int {
        return top?.value ?: throw IllegalStateException("Stack is empty")
    }

    fun getMin(): Int {
        return top?.min ?: throw IllegalStateException("Stack is empty")
    }
}


fun simplifyPath3(path: String): String {
    val directories = path.split("/")
    val stack = Stack<String>()

    directories.forEach {
        when{
            it == ".." -> {
                if(stack.isNotEmpty())
                    stack.pop()
            }
            it!= "." -> {
                stack.push(it)
            }
        }
    }

    return "/"+stack.joinToString("/")

}

fun isValidPara3(s: String): Boolean {
    val stack: Stack<Char> = Stack()

    for(c in s){
        when(c){
            '{', '(', '[' ->stack.add(c)
            '}'-> if(stack.isEmpty() || stack.pop()!='{') return false
            ')'-> if(stack.isEmpty() || stack.pop()!='(') return false
            ']'-> if(stack.isEmpty() || stack.pop()!='[') return false
        }
    }
    return true
}