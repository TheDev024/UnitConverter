fun main() {
    // write your code here
    val n = readLine()!!.toInt()
    val nums: MutableList<Int> = mutableListOf()
    for (i in 1..n) {
        val num = readLine()!!.toInt()
        nums.add(num)
    }
    if (n == 1) println(nums[0]) else {
        var max = nums[0] * nums[1]
        for (i in 0..nums.size - 1) {
            for (j in i + 1..nums.size - 1) {
                val product = nums[j] * nums[i]
                if (max < product) max = product
            }
        }
        println(max)
    }
}
