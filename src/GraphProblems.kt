import java.util.LinkedList
import java.util.Queue
import java.util.Stack

class GraphProblems() {}

fun main(){

}

// https://leetcode.com/problems/clone-graph/
// time
// space
class NodeNeighbors(var `val`: Int) {
    var neighbors: ArrayList<NodeNeighbors?> = ArrayList<NodeNeighbors?>()
}

fun cloneGraph(node: NodeNeighbors?): NodeNeighbors? {
    val oldToNew = mutableMapOf<NodeNeighbors, NodeNeighbors>()

    fun dfs(node: NodeNeighbors?): NodeNeighbors?{
        if(node in oldToNew){
            return oldToNew[node]!!
        }

        val copy = NodeNeighbors(node!!.`val`)
        oldToNew[node] = copy
        for(nei in node.neighbors){
            copy.neighbors.add(dfs(nei))
        }
        return copy
    }

    return if(node!=null) dfs(node) else null
}

// https://leetcode.com/problems/longest-univalue-path/
// time O(n)
// space O(1)
fun longestUnivaluePath(root: TreeNode?): Int{
    var res = 0

    fun dfs(node: TreeNode?): Int{
        if(node == null) return 0

        val left = dfs(node.left)
        val right = dfs(node.right)

        val leftCheck = if(node.`val` == node.left?.`val`) left+1 else 0
        val rightCheck = if(node.`val` == node.right?.`val`) right+1 else 0
        res = maxOf(res, leftCheck+rightCheck)
        return maxOf(leftCheck, rightCheck)
    }

    dfs(root)
    return res
}

// https://leetcode.com/problems/kth-smallest-element-in-a-bst/
fun kthSmallest2(root: TreeNode?, k: Int): Int {
    val list = mutableListOf<Int>()
    if(root == null) return -1

    fun inOrderTraversal(node: TreeNode?){
        if(node == null)
            return

        inOrderTraversal(node.left)
        list.add(node.`val`)
        inOrderTraversal(node.right)
    }

    inOrderTraversal(root)

    return list.get(k-1)
}

// https://leetcode.com/problems/binary-tree-right-side-view/
fun rightSideView(root: TreeNode?): List<Int>{
    val result = mutableListOf<Int>()
    if(root == null) return result

    val queue: Queue<TreeNode> = LinkedList()
    queue.offer(root)
    //queue.add(root)

    while(queue.isNotEmpty()){
        val size = queue.size
        var rightMost = -1

        for(i in 1..size){
            val node = queue.poll()
            // poll and remove are same only in remove exception will be thrown
//            queue.remove()
            rightMost = node.`val`

            if(node.left!=null)
                queue.offer(node.left)
            if(node.right!=null)
                queue.offer(node.right)
        }
        result.add(rightMost)
    }

    return result
}

// https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/?e
fun deleteDuplicates(head: ListNode?): ListNode?{
    val dummy = ListNode(0)
    dummy.next = head
    var uniq = dummy
    var curr = head

    while(curr!=null){
        if(curr.next!=null && curr.`val` == curr.next?.`val`){
            while(curr?.next!=null && curr?.`val` == curr?.next?.`val`){
                curr = curr?.next
            }
            uniq.next = curr?.next
        } else {
            uniq = curr
        }
        curr = curr?.next
    }
    return dummy.next

}

fun sumNumbers(root: TreeNode?): Int {
    fun dfs(node: TreeNode?, pathSum: Int = 0): Int{
        if(node == null) return 0

        val nextPathSum = pathSum*10 + node.`val`

        if(node.left == null && node.right == null){
            return nextPathSum
        } else {
            return dfs(node.left, nextPathSum) + dfs(node.right, nextPathSum)
        }
    }

    return dfs(root)
}

// https://leetcode.com/problems/maximum-depth-of-binary-tree/
var resMaxDepth = 0
fun maxDepthIbm(root: TreeNode?): Int {
    maxDepthHelper(root, 0)
    return resMaxDepth
}

fun maxDepthHelper(root: TreeNode?, level: Int){
    if(root == null) return

    maxDepthHelper(root.left, level+1)
    resMaxDepth = maxOf(resMaxDepth, level+1)
    maxDepthHelper(root.right, level+1)
}

// https://leetcode.com/problems/same-tree/
fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
    if(p==null && q==null) return true
    if(p==null) return false
    if(q==null) return false
    if(p.`val` != q.`val`) return false

    return isSameTree(p?.left, q?.right)
}

// https://leetcode.com/problems/invert-binary-tree/description/?envType=study-plan-v2&envId=top-interview-150
fun invertTree(root: TreeNode?): TreeNode? {

    fun invertNode(node: TreeNode?) : TreeNode?{
        if(node == null) return null

        var temp = node?.left
        node.left = root?.right
        node.right = temp

        invertNode(node.left)
        invertNode(node.right)

        return node
    }
    return invertNode(root)
}

// https://leetcode.com/problems/symmetric-tree/
fun isTreeSymmetric(root: TreeNode?): Boolean {
    if(root == null) return true

    fun isNodeSymmetric(leftNode: TreeNode?, rightNode: TreeNode?): Boolean {
        if(leftNode == null && rightNode == null) return true
        if(leftNode == null) return false
        if(rightNode == null) return false
        if(leftNode.`val` != rightNode.`val`) return false

        return isNodeSymmetric(leftNode.left, rightNode.right) &&
                isNodeSymmetric(leftNode.right, rightNode.left)
    }

    return isNodeSymmetric(root.left, root.right)
}

// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
fun buildTreePreIn(preorder: IntArray, inorder: IntArray): TreeNode? {
    val map = mutableMapOf<Int, Int>()
    for(i in 0..inorder.size-1){
        map.put(inorder[i], i)
    }

    fun buildTree(preOrder: IntArray, inOrder: IntArray,
                    map: MutableMap<Int, Int>,
                    inStart: Int, inEnd: Int,
                    preStart: Int, preEnd: Int): TreeNode?{
        if(inStart>inEnd || preStart>preEnd) return null

        val rootValue = preOrder[preStart]
        var root = TreeNode(rootValue)
        val rootIndex = map[rootValue] ?: -1
        val leftSize = rootIndex - inStart

        root.left = buildTree(preOrder, inOrder, map,
            inStart, rootIndex-1,
            preStart+1, preStart+leftSize)
        root.right = buildTree(preOrder, inOrder, map,
            rootIndex+1, inEnd,
            preStart+leftSize+1, preEnd)

        return root
    }

    return buildTree(preorder, inorder, map,
        0, inorder.size-1,
        0, preorder.size-1)
}

fun buildTreeInPost(inorder: IntArray, postorder: IntArray): TreeNode? {
    val map = mutableMapOf<Int,Int>()
    inorder.forEachIndexed{index, value ->
        map[value] = index
    }

    fun buildTreeHelper(inOrder: IntArray, postOrder:IntArray, map:MutableMap<Int,Int>,
                        inStart:Int, inEnd: Int, postStart: Int, postEnd:Int): TreeNode?{
        if(inStart>inEnd || postStart>postEnd) return null

        val rootValue = postOrder[postEnd]
        var root = TreeNode(rootValue)

        val rootIndex = map[rootValue] ?: return null
        val leftSize = rootIndex-inStart

        root.left = buildTreeHelper(inOrder, postOrder, map,
            inStart, rootIndex-1,
            postStart, postStart+leftSize-1)
        root.right = buildTreeHelper(inOrder, postOrder, map,
            rootIndex+1, inEnd,
            postStart + leftSize, postEnd-1)

        return root
    }

    return buildTreeHelper(inorder, postorder, map,
            0, inorder.size-1, 0, postorder.size-1)
}

// if two binary trees are identical
fun areIdentical(tree1: TreeNode?, tree2: TreeNode?): Boolean {
    if(tree1 == null && tree2 == null) return true
    if(tree1 == null || tree2 == null) return false
    return (tree1.`val` == tree2.`val`) &&
            areIdentical(tree1.left, tree2.left) &&
            areIdentical(tree1.right, tree2.right)
}

// https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/
data class Node2(
    val value: Int,
    val left: Node2?,
    val right: Node2?,
    var next: Node2?
)
fun populateNextRight(root: Node2?): Node2? {
    var stop: Boolean
    var n: Node2? = null

    fun visit(node: Node2?, level: Int){
        if(node == null) return

        if(level==1){
            n?.next = node
            n = node
            stop = false
        } else {
            visit(node.left, level-1)
            visit(node.right, level-1)
        }
    }

    var level=1
    do{
        n = null
        stop = true
        visit(root, level++)
    } while(!stop)

    return root
}

fun flatten(root: TreeNode?): Unit {
    if(root == null) return

    var stack: Stack<TreeNode> = Stack()
    stack.push(root)

    while(!stack.isEmpty()){
        var node: TreeNode = stack.pop()

        if(node.right!=null){
            stack.push(node.right)
        }
        if(node.left!=null){
            stack.push(node.left)
        }

        if(!stack.isEmpty()){
            node.right = stack.peek()
        }
        node.left = null
    }
}

// https://leetcode.com/problems/path-sum/
// time O(n)
// space O(n)
fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
    if(root == null) return false

    val stackNodes: Stack<TreeNode> = Stack()
    val stackSum: Stack<Int> = Stack()

    stackNodes.add(root)
    stackSum.add(targetSum - root.`val`)

    while(!stackNodes.isEmpty()){
        val node = stackNodes.pop()
        val currentSum = stackSum.pop()

        if(node.left == null && node.right == null && currentSum == 0){
            return true
        }

        if(node.left!=null){
            stackNodes.add(node.left)
            val value = node.left?.`val` ?: 0
            stackSum.add(currentSum-value)
        }
        if(node.right!=null){
            stackNodes.add(node.right)
            val value = node.right?.`val` ?: 0
            stackSum.add(currentSum-value)
        }
    }
    return false
}

fun hasPathSum2(root: TreeNode?, targetSum: Int): List<List<Int>> {
    val res = mutableListOf<List<Int>>()
    val list = mutableListOf<Int>()

    fun dfs(root: TreeNode?, ts: Int, list: MutableList<Int>, res: MutableList<List<Int>>) {
        root?.let{
            list.add(root.`val`)
            if(root.left == null && root.right == null){
                if(ts-root.`val` == 0){
                    res.add(list.toMutableList())
                }
                return
            }

            root.left?.let{
                dfs(root.left, ts-root.`val`, list, res)
                list.removeLast()
            }
            root.right?.let{
                dfs(root.right, ts-root.`val`, list, res)
                list.removeLast()
            }
        }
    }

    dfs(root, targetSum, list, res)
    return res
}

// https://leetcode.com/problems/minimum-absolute-difference-in-bst/
fun getMinimumDifference(root: TreeNode?): Int {
    var prev = Int.MAX_VALUE
    var minDiff = Int.MAX_VALUE

    fun call(root: TreeNode?) {
        if(root == null) return

        call(root.left)

        if(prev!=Int.MAX_VALUE){
            minDiff = minOf(minDiff, Math.abs(prev - root.`val`))
        }
        prev = root.`val`

        call(root.right)
    }

    call(root)
    return minDiff
}



