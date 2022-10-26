import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    val numbers = mutableListOf<Int>()
    repeat(n) {
        numbers.add(scanner.nextInt())
    }
    for (i in 1..numbers.lastIndex) {
        if (numbers[i] < numbers[i - 1]) {
            println("NO")
            return
        }
    }

    println("YES")
}