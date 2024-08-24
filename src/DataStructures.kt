class DataStructures {
}

data class DoublyLinkedListNode(
    var data: Int,
    var prev: DoublyLinkedListNode? = null,
    var next: DoublyLinkedListNode? = null
)


class Node(var `val`: Int) {
    var next: Node? = null
    var random: Node? = null
}

fun main() {
//    arrayManipulation2()
    waiter(intArrayOf(2,3,4,5,6,7), 3)
}

// https://www.hackerrank.com/challenges/waiter/
// time O(q*p) // q is num of iterations and p is number of plates
// space O(q+p)
fun waiter(number: IntArray, q: Int): Array<Int> {
    // Write your code here
    val answers = mutableListOf<Int>()
    var currentPlates = number

    // generate first q prime numbers
    val primes = mutableListOf<Int>()
    var numb = 2
    while(primes.size<q){
        if(isPrime(numb)){
            primes.add(numb)
        }
        numb++
    }

    for(prime in primes){
        val divisible = mutableListOf<Int>()
        val nonDivisible = mutableListOf<Int>()

        for(plate in currentPlates.reversed()) {
            if(plate%prime == 0){
                divisible.add(plate)
            } else{
                nonDivisible.add(plate)
            }
        }
        answers.addAll(divisible.reversed())
        currentPlates = nonDivisible.toIntArray()
    }
    answers.addAll(currentPlates.reversed())

    return answers.toTypedArray()
}

fun isPrime(num: Int): Boolean {
    // time O(n log log n)
    var n = num-1
    while(n>1){
        if(num%n == 0) return false
        n--
    }
    return true
}

//
fun copyRandomList(node: Node?): Node? {
    val oldToNew = HashMap<Node, Node>()
    var curr: Node? = node
    var copy: Node? = null

    // first pass to create the nodes and create the mapping of nodes
    while (curr != null) {
        copy = Node(curr.`val`)
        oldToNew[curr] = copy
        curr = curr.next
    }

    // second pass to map the next and random
    curr = node
    while(curr!=null){
        copy = oldToNew[curr]
        copy?.next = oldToNew[curr.next]
        copy?.random = oldToNew[curr.random]
        curr = curr.next
    }

    // since this is the first node in original LL,
    // which will result to first node in copy LL
    return oldToNew[node]
}

// https://www.hackerrank.com/challenges/insert-a-node-into-a-sorted-doubly-linked-list?isFullScreen=true
fun sortedInsert(head: DoublyLinkedListNode?, data: Int): DoublyLinkedListNode? {
    val newNode = DoublyLinkedListNode(data)

    // case 1: list is empty
    if (head == null) return newNode

    //case 2: new node should be inserted before the head
    if (data <= head.data) {
        newNode.next = head
        head.prev = newNode
        return newNode
    }

    // case 3: traverse and find proper location
    var current = head
    while (current?.next != null && current.next!!.data < data) {
        current = current.next
    }
    newNode.next = current?.next
    if (current?.next != null) {
        current.next!!.prev = newNode
    }
    current?.next = newNode
    newNode.prev = current

    return head
}

// https://www.hackerrank.com/challenges/crush/problem?isFullScreen=true
// difference array technique
fun arrayManipulation2(n: Int, queries: Array<Array<Int>>): Long {
    val arr = IntArray(n + 1) { 0 }

    for (op in queries) {
        val start = op[0] - 1
        val end = op[1]
        val value = op[2]

        arr[start] += value
        if (end < n) arr[end] -= value
    }

    var maxVal = 0
    var currentVal = 0
    for (i in 0 until n) {
        currentVal += arr[i]
        maxVal = maxOf(maxVal, currentVal)
    }
    return maxVal.toLong()
}

fun arrayManipulation(n: Int, queries: Array<Array<Int>>): Long {
    // Write your code here
    val res = mutableListOf<IntArray>()
    var retVal: Long = 0
    res.add(IntArray(n) { 0 })

    for (query in queries) {
        val start = query[0]
        val end = query[1]
        val v = query[2]
        val arr = IntArray(n) { 0 }

        val prev = res.get(res.size - 1)
        for (i in start..end) {
            val idx = i - 1
            arr[idx] = prev[idx] + v
            retVal = maxOf(retVal, arr[idx].toLong())
        }
        res.add(arr)
    }
    return retVal
}

fun dynamicArray(n: Int, queries: Array<Array<Int>>): Array<Int> {
    // Write your code here
    var lastAnswer = 0
    val answers = mutableListOf<Int>()
    val arr = mutableListOf<MutableList<Int>>()

    for (i in 0..<n) {
        arr.add(mutableListOf())
    }

    for (q in queries) {
        val x = q[1]
        val y = q[2]
        val idx = ((x xor lastAnswer) % n)
        if (q[0] == 1) {
            arr[idx].add(y)
        } else {
            lastAnswer = arr.get(idx).get(y % (arr[idx].size))
            answers.add(lastAnswer)
        }
    }
    return answers.toTypedArray()
}