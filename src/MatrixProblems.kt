import java.util.LinkedList
import kotlin.math.abs

class MatrixProblems {
}

fun main() {
//    spiralOrder2()
//    maximalSquare()
//    setZeroes()
//    gameOfLife(arrayOf(intArrayOf(1,1), intArrayOf(1,0)))
//    minimumTotal(listOf(listOf(-1), listOf(2,3), listOf(1,-1,-3)))
    solveNQueens(4)

}

// https://leetcode.com/problems/search-a-2d-matrix/
// time O(log mn)
// space O(1)
fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
    val m = matrix.size
    val n = matrix[0].size
    var left = 0
    var right = m * n -1

    while(left<=right){
        val mid = getMid(left, right)
        val row = mid/n
        val col = mid%n

        if(matrix[row][col] == target){
            return true
        } else if(matrix[row][col]<target){
            left = mid+1
        } else{
            right = mid-1
        }
    }
    return false
}
fun getMid(left: Int, right: Int): Int {
    val mid: Int = (left+right)/2
    return mid
}

fun searchMatrix2(matrix: Array<IntArray>, target: Int): Boolean {
    val rows = matrix.size
    val columns = matrix[0].size

    if(rows == 0 || columns ==0)
        return false

    if(target<matrix[0][0] || target > matrix[rows-1][columns-1])
        return false

    var i = 0
    var j = columns-1

    while(i<=rows-1 && j>=0){
        if(matrix[i][j]==target){
            return true
        }
        if(matrix[i][j] < target){
            i++
        } else {
            j--
        }
    }

    return false
}

// https://leetcode.com/problems/shortest-path-in-binary-matrix/
// time O(n^2)
// space O(n^2)
fun shortestPathBinaryMatrix(grid: Array<IntArray>): Int {
    if(grid[0][0] == 1) return -1
    val gridV = Array<BooleanArray>(grid.size) {
        BooleanArray(grid.size) {false}
    }

    val dir = intArrayOf(-1,0,1) // directions it can travel in
    val queue = LinkedList<Triple<Int,Int,Int>>()

    fun add(r: Int, c:Int, d:Int) {
        // edges
        if(r<0 || r>=grid.size) return
        if(c<0 || c>=grid.size) return
        if(grid[r][c] == 1) return //obstruction
        if(gridV[r][c]) return // visited
        queue.offer(Triple(r, c, d+1))
        gridV[r][c] = true
    }

    queue.offer(Triple(0,0,1))
    gridV[0][0] = true
    while(queue.isNotEmpty()) {
        val(x,y,d) = queue.poll()
        if(x==grid.size-1 && y==grid.size-1) {
            // reached the bottom right
            return d
        }
        for(r in dir) {
            for(c in dir){
                add(x+r, y+c, d)
            }
        }

    }
    // no path found
    return -1
}

fun uniquePathsWithObstacles(grid: Array<IntArray>): Int {
    val m = grid.size
    val n = grid[0].size

    if(grid[0][0] == 1) return 0

    val dp = Array(m) {IntArray(n)}
    dp[0][0] = 1

    for(i in 1 until m){
        dp[i][0] = if(grid[i][0] == 1) 0 else dp[i-1][0]
    }
    for(i in 1 until n){
        dp[0][i] = if(grid[0][i] == 1) 0 else dp[0][i-1]
    }
    for(i in 1 until m){
        for(j in 1 until n){
            dp[i][j] = if(grid[i][j] == 1) 0 else dp[i-1][j] + dp[i][j-1]
        }
    }
    return dp[m-1][n-1]
}

fun uniquePaths2(m: Int, n: Int): Int {
    val grid = Array(m){IntArray(n)}
    grid[0][0] = 1

    for(i in 1 until m){
        grid[i][0] = 1
    }
    for(i in 1 until n){
        grid[0][i] = 1
    }

    for(i in 1 until m){
        for(j in 1 until n){
            grid[i][j] = grid[i-1][j] + grid[i][j-1]
        }
    }
    return grid[m-1][n-1]
}

fun uniquePaths(m: Int, n: Int): Int {
    val grid = Array(m) {IntArray(n)}
    grid[m-1][n-1] = 1

    for(col in n-1 downTo  0){
        for(row in m-1 downTo 0){
            if(!(col == n-1 && row == m-1)){
                val fromRight = if(col<n-1) grid[row][col+1] else 0
                val fromBottom = if(row<m-1) grid[row+1][col] else 0
                grid[row][col] = fromRight + fromBottom
            }
        }
    }
    return grid[0][0]
}

// https://leetcode.com/problems/minimum-path-sum/
fun minPathSum(grid: Array<IntArray>): Int {
    grid.forEachIndexed { rowIndex, ints ->
        ints.forEachIndexed { colIndex, i ->
            if(colIndex == 0 && rowIndex == 0){
                return@forEachIndexed
            } else if(rowIndex-1 < 0){
                grid[rowIndex][colIndex] += grid[rowIndex][colIndex-1]
            } else if(colIndex-1 < 0){
                grid[rowIndex][colIndex] += grid[rowIndex-1][colIndex]
            } else {
                grid[rowIndex][colIndex] = minOf(
                    grid[rowIndex-1][colIndex],
                    grid[rowIndex][colIndex-1]
                )
            }
        }
    }
    return grid[grid.size-1][grid[0].size-1]
}

fun minimumTotal(triangle: List<List<Int>>): Int {
    if(triangle.isEmpty()) return 0
    val dp = triangle.map { it.toMutableList() }

    for(row in triangle.size-2 downTo 0){
        for(col in triangle[row].indices){
            dp[row][col] += minOf(dp[row+1][col], dp[row+1][col+1])
        }
    }
    return dp[0][0]
}

// double traversing solution
fun setZeroes(matrix: Array<IntArray>): Unit {
    val rows = matrix.size
    val columns = matrix[0].size

    var row0s: MutableSet<Int> = HashSet()
    var col0s: MutableSet<Int> = HashSet()

    for(i in 0 until rows){
        for(j in 0 until columns){
            val num = matrix[i][j]
            if(num == 0){
                row0s.add(i)
                col0s.add(j)
            }
        }
    }

    for(i in 0 until rows){
        for(j in 0 until columns){
            if(row0s.contains(i) || col0s.contains(j)){
                matrix[i][j] = 0
            }
        }
    }
}


fun orderOfLargestPlusSign(n: Int, mines: Array<IntArray>): Int {
    val rows = n
    val cols = n
    val grid = Array(rows) {IntArray(cols) {n} }

    for(m in mines){
        grid[m[0]][m[1]] = 0
    }

    for(i in 0 until n){
        var j=0
        var k = n-1
        var left = 0
        var right = 0
        var up = 0
        var down = 0

        while(j<n){
            left = if(grid[i][j]==0) 0 else left+1
            grid[i][j] = minOf(grid[i][j], left)

            right = if(grid[i][k]==0) 0 else right+1
            grid[i][k] = minOf(grid[i][k], right)

            up = if(grid[j][i]==0) 0 else up+1
            grid[j][i] = minOf(grid[j][i], up)

            down = if(grid[k][i]==0) 0 else down+1
            grid[k][i] = minOf(grid[k][i], down)

            j++
            k--
        }
    }

    var res = 0
    for(i in 0 until n){
        for(j in 0 until n){
            res = maxOf(res, grid[i][j])
        }
    }
    return res
}


// https://leetcode.com/problems/maximal-square/
//time O(mxn)
// space O(n)
fun maximalSquare(matrix: Array<CharArray>): Int {
    val rows = matrix.size
    val cols = matrix[0].size
    val dp = Array(rows) {IntArray(cols) {0} }

    var res = 0
    for(i in 0 until rows){
        for(j in 0 until cols){
            if(matrix[i][j] == '1'){
                dp[i][j] =
                    if(i<1 || j<1) 1
                    else (minOf(dp[i-1][j-1], dp[i][j-1], dp[i-1][j]) +1 )
            }
            res = maxOf(res, dp[i][j] * dp[i][j])
        }
    }
    return res
}

fun gameOfLife(board: Array<IntArray>): Boolean {
    val rowMax = board.size
    val columnMax = board[0].size

    for(row in 0 until rowMax){
        for(column in 0 until columnMax){
            val lives = countAliveNeighbors(board, rowMax, columnMax,
                row, column)
            setNextState(lives, board, row, column)
        }
    }

    // decode new board
    for(row in 0 until rowMax){
        for(column in 0 until columnMax){
            board[row][column] = board[row][column] shr 1
        }
    }

    return true
}

fun setNextState(lives: Int, board: Array<IntArray>, row: Int, column: Int) {
    if(board[row][column] == 1 && (lives == 2 || lives == 3)){
        // set next state as alive
        board[row][column] = board[row][column] or 2
    } else if(board[row][column] == 0 && lives == 3){
        board[row][column] = board[row][column] or 2
    } else {
        board[row][column] = board[row][column] or 0
    }
}

fun countAliveNeighbors(board: Array<IntArray>, rowMax: Int, columnMax: Int, currentRow: Int, currentColumn: Int): Int {
    var count = 0
    val rowStart = Math.max(0, currentRow-1)
    val rowEnd = Math.min(rowMax-1, currentRow+1 )

    for(row in rowStart..rowEnd){
        val colStart = Math.max(0, currentColumn-1)
        val colEnd = Math.min(columnMax-1, currentColumn+1)

        for(column in colStart..colEnd){
            if(row==currentRow && column==currentColumn) continue
            count+= board[row][column] and 1
        }
    }
    return count
}


fun rotate(matrix: Array<IntArray>): Unit {
    var list = mutableListOf<Int>()
    val n = matrix.size

    for(i in 0..n-1){
        var row = n-1
        for(j in 0..n-1){
            list.add(matrix[row--][i])
        }
    }

    var k = 0
    for(i in 0..n-1){
        for(j in 0..n-1){
            matrix[i][j] = list[k++]
        }
    }
}

fun spiralOrder2(matrix: Array<IntArray>): List<Int> {
    val R = matrix[0].size
    val C = matrix[1].size
    var l = 0
    var r = matrix[1].size
    var t = 0
    var d = matrix[0].size
    var res = mutableListOf<Int>()

    while(true){
        for(i in l..r-1){
            res.add(matrix[t][i])
        }
        t++
        for(i in t..d-1){
            res.add(matrix[i][r-1])
        }
        r--
        if(res.size == C*R) break

        for(i in r-1 downTo l){
            res.add(matrix[d-1][i])
        }
        d--
        for(i in d-1 downTo t){
            res.add(matrix[i][l])
        }
        l++
        if(res.size == C*R) break
    }
    return res
}

fun isValidSudoku2(board: Array<CharArray>): Boolean {
    var seen: MutableSet<String> = HashSet()

    for(i in  0..9){
        for(j in 0..9){
            val c = board[i][j]
            if(c!='.') {
                if(!seen.add("$c in row $i")
                    || !seen.add("$c in column $j")
                    || !seen.add("$c in box ${i/3} ${j/3}")){
                    return false
                }
            }
        }
    }
    return true
}

fun maxSumSubmatrix(matrix: Array<IntArray>, k: Int): Int {
    val rows = matrix.size
    val cols = matrix[0].size

    val runningSum = Array(rows+1) {IntArray(cols+1)}
    for(row in 1..rows){
        for(col in 1..cols){
            runningSum[row][col] = matrix[row-1][col-1] + runningSum[row][col-1]
        }
    }
    for(col in 1..cols){
        for(row in 1..rows){
            runningSum[row][col] += runningSum[row-1][col]
        }
    }

    fun calculateRangeSum(startRow: Int, startColumn: Int,
                          endRow: Int, endColumn: Int): Int {
        val component1 = runningSum[endRow][endColumn]
        val component2 = runningSum[startRow-1][startColumn-1]
        val component3 = runningSum[startRow-1][endColumn]
        val component4 = runningSum[endRow][startColumn-1]
        return component1 + component2 - component3 - component4
    }

    var maxSum: Int? = null

    overallLoop@ for(startRow in 1..rows){
        for(startColumn in 1..cols){
            for(endRow in startRow..rows){
                for(endCol in startColumn..cols){
                    val currentSum = calculateRangeSum(startRow, startColumn,
                        endRow, endCol)
                    if(currentSum <= k && (maxSum == null || currentSum>maxSum)){
                        maxSum = currentSum
                    }
                    if(maxSum == k)
                        break@overallLoop
                }
            }
        }
    }

    return maxSum ?: throw IllegalStateException()
}

// https://leetcode.com/problems/n-queens/
// time O(n!) due to backtracking
// space O(n^2) for the board and O(n) for the recursion stack
fun solveNQueens(n: Int): List<List<String>> {
    val result = mutableListOf<List<String>>()

    val positions = mutableListOf<Pair<Int,Int>>()
    val board = mutableListOf<CharArray>()

    for(i in 0..<n){
        board.add(CharArray(n))

        for(j in 0..<n) {
            board[i][j] = '.'
        }
    }

    fun backtrack(startLine: Int) {
        if(positions.size == n){
            // ex: ".Q..,.."
            result.add(board.map { it.joinToString("") })
            return
        }
        for(i in startLine..<n){
            for(j in 0..<n){
                if(canAdd(i, j, positions)){
                    positions.add(Pair(i,j))
                    board[i][j] = 'Q'

                    backtrack(i+1)

                    board[i][j] = '.'
                    positions.removeLast()
                }
            }
        }
    }

    backtrack(0)

    return result
}

fun canAdd(i: Int, j: Int, positions: List<Pair<Int, Int>>): Boolean {
    if(positions.isEmpty()){
        return true
    }
    return freeRow(i, positions) &&
            freeColumn(j, positions) &&
            freeDiagonal(i, j, positions)
}
fun freeRow(i: Int, positions: List<Pair<Int,Int>>): Boolean{
    return positions.all { it.first != i }
}
fun freeColumn(j: Int, positions: List<Pair<Int,Int>>): Boolean{
    return positions.all { it.second != j }
}
fun freeDiagonal(i: Int, j: Int, positions: List<Pair<Int,Int>>): Boolean{
    return positions.all { abs(it.first-i) != abs(it.second-j) }
}