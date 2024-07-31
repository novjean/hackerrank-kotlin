import 'dart:math';

main() {
  // pathSum()
  // letterCombinations("23");
  myPow(-3,-5);
}

// https://leetcode.com/problems/candy/
int candy(List<int> ratings){
  List<int> candies = List.filled(ratings.length, 1);
  int index = 1;
  while(index<ratings.length){
    if(candies[index] > candies[index-1]){
      candies[index] = candies[index-1]+1;
    }
    index++;
  }
  index = ratings.length-1;
  while(index>0){
    if(candies[index-1] > candies[index]){
      candies[index-1] = max(candies[index-1], candies[index]+1);
    }
    index--;
  }
  return candies.reduce((a,b)=>a+b);
}

String reverseWords(String s) {
  List<String> words = s.split(' ')
      .where((word) => word.isNotEmpty).toList();
  int len = words.length;
  StringBuffer res = StringBuffer();

  for(int i=len-1;i>=0;i--){
    String word = words[i];
    res.write(word+ ' ');
  }
  return res.toString().trim();
}
String reverseWords2(String s) {
  return s.split(" ")
      .where((element)=> element.isNotEmpty)
      .toList().reversed.join(" ");
}

bool isValidParantheses(String s){
  List<String> stack = [];

  for(int i = 0;i<s.length;i++){
    if(s[i] == "{" || s[i] == "(" || s[i] == "["){
      stack.add(s[i]);
    } else if(s[i] == ")"){
      String last = stack.last;
      stack.removeLast();
      return last == "(";
    } else if(s[i] == "}"){
      String last = stack.last;
      stack.removeLast();
      return last == "{";
    } else if(s[i] == "]"){
      String last = stack.last;
      stack.removeLast();
      return last == "[";
    }
  }

  return stack.isEmpty;
}

int minPathSum(List<List<int>> grid) {
  int rows = grid.length;
  int columns = grid[0].length;

  for(int i=0; i<rows; i++){
    for(int j = 0; j<columns; j++){
      if(i==0 && j==0) continue;
      if(i==0) grid[i][j] += grid[i][j-1];
      if(j==0) grid[i][j] += grid[i-1][j];
      else grid[i][j] += min(grid[i-1][j], grid[i][j-1]);
    }
  }
  return grid[rows-1][columns-1];
}

bool isInterleave(String s1, String s2, String s3) {
  if((s1+s2).length != s3.length) return false;

  Map<Map<int, int>, bool> dp = Map();

  bool dfs(int i, int j){
    if(i==s1.length && j == s2.length) return true;
    Map<int, int> key = {i:j};
    if(dp.containsKey(key)) return dp[key]!;

    if(i<s1.length && s1[i] == s3[i+j] && dfs(i+1,j)) return true;
    if(j<s2.length && s2[j] == s3[i+j] && dfs(i,j+1)) return true;
    dp[key] = false;
    return false;
  }

  return dfs(0,0);
}

double myPow(double x, int n) {
  double signBias = n<0 ? 1/x : x;
  double result = exponent(signBias, n.abs());
  print("result: " + result.toString());
  return result;
}

double exponent(double base, int exp){
  switch(exp){
    case 0: return 1;
    case 1: return base;
    default: {
      double res = exponent(base, (exp ~/ 2));
      if(exp%2 == 0){
        return res * res;
      } else {
        return base * res * res;
      }
    }
  }
}

List<List<int>> merge(List<List<int>> intervals) {
  List<List<int>> res = [];
  intervals.sort((a,b) => a[0].compareTo(b[0]));

  for(int i=0; i<intervals.length; i++){
    List<int> it = intervals[i];

    if(res.isEmpty || res.last[1]<it[0]){
      res.add(it);
    } else {
      res.last[1] = max(res.last[1], it[1]);
    }
  }
  return res;
}

List<String> letterCombinations(String digits) {
  List<String> result = [];
  if(digits.isEmpty) return result;

  Map<String, String> map = {
    "2" : "abc",
    "3" : "def",
    "4" : "ghi",
    "5" : "jkl",
    "6" : "mno",
    "7" : "pqrs",
    "8" : "tuv",
    "9" : "wxyz",
  };

  backtrack(int index, String current){
    if(index == digits.length){
      result.add(current.toString());
      return;
    }
    String? letters = map[digits[index]];

    if(letters == null) return;

    for(int i=0; i<letters.length; i++){
      current = current+ letters[i];
      backtrack(index+1, current);
      current = current.substring(0, current.length-1);
    }
  }

  backtrack(0, "");
  return result;
}

void rotate(List<List<int>> matrix) {
  int n = matrix.length;
  List<int> list = [];

  for(int i=0;i<n;i++){
    int row = n-1;
    for(int j=0;j<n;j++){
      list.add(matrix[row--][i]);
    }
  }

  int k = 0;
  for(int i=0;i<n;i++){
    for(int j=0;j<n;j++){
      matrix[i][j] = list[k++];
    }
  }
}

bool wordBreak(String s, List<String> wordDict) {
  List<bool> dp = List.filled(s.length+1, false);
  dp[s.length] = true;

  for(int i=s.length; i>=0; i--){
    for(String word in wordDict){
      if(i+word.length<=s.length && word == s.substring(i, i+word.length)){
        dp[i] = dp[i+word.length];
      }
      if(dp[i]) break;
    }
  }
  return dp[0];
}

List<List<String>> solveNQueens(int n) {
  List<List<String>> result = [];

  List<List<int>> positions = [];
  List<List<String>> board = List.generate(n, (i) => List.filled(n, '.', growable: false));

  void backtrack(int startLine){
    if(positions.length == n){
      result.add(board.map((row) => row.join()).toList());
      return;
    }

    for(int i = startLine;i<n; i ++){
      for(int j = 0;j<n; j++){
        if(canAdd(i, j, positions)){
          positions.add([i,j]);
          board[i][j] = "Q";

          backtrack(i+1);

          board[i][j] = ".";
          positions.removeLast();
        }
      }
    }
  }

  backtrack(0);

  return result;
}

bool canAdd(int i, int j, List<List<int>> positions){
  if(positions.isEmpty){
    return true;
  }

  return (rowFree(i, positions) && columnFree(j, positions)
    && diagonalFree(i,j, positions));
}

bool rowFree(int i, List<List<int>> positions){
  return positions.every((pos) => pos[0]!= i);
}

bool columnFree(int j, List<List<int>> positions){
  return positions.every((pos) => pos[1]!= j);
}

bool diagonalFree(int i, int j, List<List<int>> positions){
  return positions.every((pos) =>  (pos[0]-i).abs() != (pos[1]-j).abs());
}

class TreeNode{
  int val = 0;
  TreeNode? left;
  TreeNode? right;
}

// https://leetcode.com/problems/path-sum-ii/
List<List<int>> pathSum(TreeNode? root, int targetSum) {
  List<List<int>> res = [];
  List<int> list = [];
  dfs(root, targetSum, res, list);
  return res;
}

void dfs(TreeNode? root, int ts,
    List<List<int>> res, List<int> list) {
  if(root == null) return;

  list.add(root.val);

  if(root.left == null && root.right == null){
    if(ts-root.val == 0) {
      res.add(List.from(list));
      return;
    }
  }

  if(root.left!=null){
    dfs(root.left, ts-root.val, res, list);
    list.removeLast();
  }

  if(root.right!=null){
    dfs(root.right, ts-root.val, res, list);
    list.removeLast();
  }
}

void flatten(TreeNode? root) {
  List<TreeNode?> stack = [];
  stack.add(root);

  while(stack.isNotEmpty){
    TreeNode? curr = stack.removeLast();

    if(curr?.right!=null){
      stack.add(curr?.right);
    }
    if(curr?.left!=null){
      stack.add(curr?.left);
    }
    if(stack.isNotEmpty){
      curr?.right = stack.last;
    }
    curr?.left = null;
  }
}