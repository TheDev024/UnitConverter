fun main() {
    // write your code here
    val min = readln()!!.toInt()
    val max = readln()!!.toInt()
    for (n in min..max) {
        if (n % 15 == 0) println("FizzBuzz")
        else if (n % 5 == 0) println("Buzz")
        else if (n % 3 == 0) println("Fizz")
        else println(n)
    }
}
