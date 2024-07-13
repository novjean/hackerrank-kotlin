class MatrixProblems {
}

fun main() {
//    spiralOrder2()
//    maximalSquare()
//    setZeroes()

//    gameOfLife(arrayOf(intArrayOf(1,1), intArrayOf(1,0)))

    minimumTotal(listOf(listOf(-1), listOf(2,3), listOf(1,-1,-3)))
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
            val lives = countAliveNeighbors(board, rowMax, columnMax, row, column)
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
    var R = matrix[0].size
    var C = matrix[1].size
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