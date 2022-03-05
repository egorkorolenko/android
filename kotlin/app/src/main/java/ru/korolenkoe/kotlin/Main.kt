package ru.korolenkoe.kotlin

fun main() {
    print(solution(200))
    print("\n")
    print(wave("This is a few words"))
    print("\n")
    print(wave("nigga"))
    print("\n")
    print(wave("a       b    "))
    print("\n")
    print(maxMultiple(2, 7))
}

fun solution(num: Int): Int {
    var count = 0
    return if (num < 0) {
        0
    } else {
        for (i in 0 until num) {
            if (i % 3 == 0 || i % 5 == 0) {
                count += i
            }
        }
        count
    }
}


fun wave(str: String): List<String> {
    val arr: ArrayList<String> = ArrayList()
    for (s in str.indices) {
        if (str[s] != ' ') {
            val c = str[s].uppercaseChar()
            val str2 = str.replaceRange(s..s, c.toString())
            arr.add(str2)
        }
    }
    return arr
}

fun maxMultiple(d: Int, b: Int): Int {
    var max = 0
    for (i in b downTo 1) {
        if (i % d == 0) {
            max = i
            break
        }
    }
    return max
}

fun encode(num: Int): String {
    if (num == 0) {
        return ""
    }else{
        
    }
    return ""
}