fun MutableList<Int>.getMin(): Int {
    var min = this[0]
    for (i in 1..this.lastIndex) if (this[i] < min) min = this[i]
    return min
}

fun main() {
    // write your code here
    val n = readln().toInt()
    val list = mutableListOf<Int>()
    for (i in 0 until n) list.add(readln().toInt())
    println(list.getMin())
}
