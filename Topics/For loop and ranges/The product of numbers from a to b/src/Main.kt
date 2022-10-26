fun main() {
    val a = readln()!!.toLong()
    val b = readln()!!.toLong() - 1

    var product: Long = 1
    for (i in a..b) product *= i
    println(product)
}
