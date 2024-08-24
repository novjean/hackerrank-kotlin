import java.util.Deque
import java.util.LinkedList
import java.util.Queue
import java.util.Stack
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

// https://leetcode.com/problems/surrounded-regions/
// time
// space
fun solveSurroundedRegions(board: Array<CharArray>): Unit {
    val rows = board.size
    val cols = board[0].size
    val visit = Array(rows){BooleanArray(cols) {false} }
    val q: Queue<Pair<Int,Int>> = LinkedList()

    for(i in 0..<rows){
        if(board[i][0] == 'O'){
            q.offer(Pair(i,0))
            visit[i][0] = true
        }
        if(board[i][cols-1] == 'O'){
            q.offer(Pair(i,cols-1))
            visit[i][cols-1] = true
        }
    }

    for(i in 0..<cols){
        if(board[0][i] == 'O'){
            q.offer(Pair(0,i))
            visit[0][i] = true
        }
        if(board[rows-1][i] == 'O'){
            q.offer(Pair(rows-1, i))
            visit[rows-1][i] = true
        }
    }

    val directions = arrayOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
    while(q.isNotEmpty()){
        val (row, col) = q.poll()
        for((dr, dc) in directions){
            val r = row + dr
            val c = col + dc
            if(r in 0..<rows &&
                c in 0..<cols &&
                board[r][c] == '0' &&
                !visit[r][c]){
                q.offer(Pair(r,c))
                visit[r][c] = true
            }
        }
    }


    for(r in 0..<rows){
        for(c in 0..<cols){
            if(board[r][c] == 'O' && !visit[r][c]){
                board[r][c] = 'X'
            }
        }
    }
}

// https://leetcode.com/problems/number-of-islands/
// time O(mxn)
// space O(min(m,n))
fun numIslands(grid: Array<CharArray>): Int {
    if(grid.isEmpty()) return 0

    val rows = grid.size
    val cols = grid[0].size
    var islands = 0
    val visit = HashSet<Pair<Int, Int>>()

    fun bfs(r: Int, c: Int){
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.offer(Pair(r,c))
        visit.add(Pair(r,c))

        val directions = arrayOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

        while(queue.isNotEmpty()){
            val (row, col) = queue.poll()
            for((dr, dc) in directions){
                val r  = row + dr
                val c = col + dc
                if(r in 0..<rows-1 &&
                    c in 0..<cols-1 &&
                    grid[r][c] == '1' &&
                    !visit.contains(Pair(r,c))){
                    queue.offer(Pair(r,c))
                    visit.add(Pair(r,c))
                }
            }
        }
    }

    for(r in 0..<rows){
        for(c in 0..<cols){
            if(grid[r][c] == '1' && !visit.contains(Pair(r,c))){
                bfs(r,c)
                islands++
            }
        }
    }

    return islands
}

// cell color same
// https://app.codesignal.com/arcade/intro/level-6/t97bpjfrMDZH8GJhi
// time O(1)
// space O(1)
fun solution(cell1: String, cell2: String): Boolean {
    val charDiff = abs(cell1[0] - cell2[0])
    val numDiff = abs(cell1[1] - cell2[1])

    if(charDiff%2==0 && numDiff%2 == 0){
        return true
    } else if(charDiff%2==1 && numDiff%2 == 1){
        return true
    }
    else return false
}

// minesweeper
// https://app.codesignal.com/arcade/intro/level-5/ZMR5n7vJbexnLrgaM
// time O(m*n)
// space O(m*n)
fun minesweeper(matrix: MutableList<MutableList<Boolean>>): MutableList<MutableList<Int>> {
    val rows = matrix.size
    val cols = matrix[0].size
    val result = MutableList(rows){MutableList(cols){0} }

    fun isInBounds(r:Int, c:Int): Boolean{
        return r>=0 && r<rows && c>=0 && c<cols
    }

    for(r in 0..<rows){
        for(c in 0..<cols){
            if(matrix[r][c]){
                // found a mine, notify the neighbors
                for(i in -1..1){
                    for(j in -1..1){
                        val newRow = r+i
                        val newCol = c+j

                        if(isInBounds(newRow, newCol) && !(i==0 && j==0)){
                            result[newRow][newCol]++
                        }
                    }
                }
            }
        }
    }
    return result
}

// https://app.codesignal.com/arcade/intro/level-5/5xPitc3yT3dqS7XkP
// time O(rows * cols)
// space O(rows * cols)
fun boxBlur(image: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
    val res = mutableListOf<MutableList<Int>>()
    val rows = image.size
    val cols = image[0].size

    for(r in 1..<rows-1){
        val blurRow = mutableListOf<Int>()
        for(c in 1..<cols-1){
            //println("image[r][c]: ${image[r][c]}")
            val sum = image[r-1][c-1] + image[r-1][c] + image[r-1][c+1] + image[r][c-1] + image[r][c] + image[r][c+1] + image[r+1][c-1] + image[r+1][c] + image[r+1][c+1]
            //println("sum: $sum")
            val avg = (sum/9).toInt()
            blurRow.add(avg)
        }
        res.add(blurRow)
    }
    return res
}

// https://leetcode.com/problems/word-search/
fun exist(board: Array<CharArray>, word: String): Boolean {
    val rows = board.size
    val cols = board[0].size

    val path = hashSetOf<Pair<Int, Int>>()

    fun dfs(r: Int, c: Int, i: Int): Boolean{
        if(i == word.length) return true

        if(r<0 || c<0 || r>= rows || c>= cols ||
            word[i] != board[r][c] ||
            path.contains(Pair(r,c)))
            return false

        path.add(Pair(r,c))
        val res = dfs(r+1, c,i+1) ||
                dfs(r-1, c,i+1) ||
                dfs(r, c+1,i+1) ||
                dfs(r, c-1,i+1)
        path.remove(Pair(r,c))
        return res
    }

    for(r in 0..<rows){
        for(c in 0..<cols){
            if(dfs(r, c, 0))
                return true
        }
    }
    return false
}

// https://app.codesignal.com/arcade/intro/level-4/xYXfzQmnhBvEKJwXP/drafts
fun swapCheckSimilarList(a: MutableList<Int>, b: MutableList<Int>): Boolean {
    if(a.size!=b.size) return false
    var swapa1 = -1
    var swapb1 = -1
    var swapa2 = -1
    var swapb2 = -1

    for(i in 0..<a.size){
        if(a[i]!=b[i]){
            if(swapa1 == -1){
                swapa1 = a[i]
                swapb1 = b[i]
            } else if(swapa2 == -1){
                swapa2 = a[i]
                swapb2 = b[i]
            } else {
                // more than two swaps
                return false
            }
        }
    }
    if(swapa1 == swapb2 && swapa2 == swapb1) return true
    else return false
}

// https://app.codesignal.com/arcade/intro/level-4/ZCD7NQnED724bJtjN
fun pictureFrame(picture: MutableList<String>): MutableList<String> {
    val rows:Int = picture.size +2
    val cols:Int = picture[0].length+2
    var res = mutableListOf<String>()

    for(i in 0..<rows){
        var sb = StringBuilder()
        for(j in 0..<cols){
            if(i==0 || j==0 || i == rows-1 || j==cols-1){
                sb.append("*")
            } else {
                sb.append(picture[i-1][j-1])
            }
        }
        res.add(sb.toString())
    }
    return res
}

// https://leetcode.com/problems/search-a-2d-matrix/
// time O(log mn)
// space O(1)
fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
    val rows = matrix.size
    val cols = matrix[0].size
    var left = 0
    var right = rows * cols -1

    while(left<=right){
        val mid = getMid(left, right)
        val row = mid / cols
        val col = mid % cols

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
    val gridV = MutableList(grid.size) {
        MutableList<Boolean>(grid.size) {false}
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

fun maxSumSubmatrix2(matrix: Array<IntArray>, k: Int): Int {
    val rows = matrix.size
    val cols = if(rows>0) matrix[0].size else 0

    val prefixSum = Array(rows+1){IntArray(cols+1)}

    for(r in 0..<rows){
        var prefix = 0
        for(c in 0..<cols){
            prefix += matrix[r][c]
            prefixSum[r+1][c+1] = prefix + prefixSum[r][c+1]
        }
    }

    fun calculateRangeSum(row1: Int, col1: Int,
                          row2: Int, col2: Int): Int {
        return prefixSum[row2+1][col2+1] - prefixSum[row1][col2+1] -
                prefixSum[row2+1][col1] + prefixSum[row1][col1]
    }

    var maxSum: Int? = null
    overallLoop@ for(startRow in 0..<rows){
        for(startCol in 0..<cols){
            for(endRow in startRow..<rows){
                for(endCol in startCol..<cols){
                    val currentSum = calculateRangeSum(startRow, startCol, endRow, endCol)
                    if(currentSum<=k && (maxSum==null || maxSum>currentSum)){
                        maxSum = currentSum
                    }
                    if(maxSum == k){
                        break@overallLoop
                    }
                }
            }
        }
    }
    return maxSum?: throw IllegalStateException()
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