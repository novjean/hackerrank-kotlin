import java.util.*

class StackProblems {
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
    var stack: Stack<Char> = Stack()

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