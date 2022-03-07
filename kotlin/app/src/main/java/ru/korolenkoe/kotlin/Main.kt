package ru.korolenkoe.kotlin

import android.os.Build
import androidx.annotation.RequiresApi
import java.math.BigInteger

@RequiresApi(Build.VERSION_CODES.S)
fun main() {
    println(solution(200))
    println(wave("This is a few words"))
    println(wave("nigga"))
    println(wave("a       b    "))
    println(maxMultiple(2, 7))
    println(people(arrayOf(3 to 0,9 to 1,4 to 10,12 to 2,6 to 1,7 to 10)))
    println(duplicateCount("abcde"))
    println(lastDigit(BigInteger("4"), BigInteger("1")))
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

fun people(busStops: Array<Pair<Int, Int>>) : Int {
    var count = 0;
    for(a in busStops){
     count+=a.first
     count-=a.second
    }
    return count
}

fun duplicateCount(text: String) = text.groupBy(Char::toLowerCase).count { it.value.count() > 1 }

@RequiresApi(Build.VERSION_CODES.S)
fun lastDigit(base: BigInteger, exponent: BigInteger): Int {
    return base.modPow(exponent, BigInteger("10")).intValueExact()
}