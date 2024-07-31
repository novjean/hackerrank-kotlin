import java.util.Stack

class GraphProblems() {}

fun main(){

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

// https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/
data class Node(
    val value: Int,
    val left: Node?,
    val right: Node?,
    var next: Node?
)
fun populateNextRight(root: Node?): Node? {
    var stop: Boolean
    var n: Node? = null

    fun visit(node: Node?, level: Int){
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



