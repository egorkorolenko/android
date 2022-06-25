import java.math.BigInteger
import java.util.*
import kotlin.collections.HashMap


fun main() {
//    println(solution(200))
//    println(wave("This is a few words"))
//    println(wave("nigga"))
//    println(wave("a       b    "))
//    println(maxMultiple(2, 7))
//    println(people(arrayOf(3 to 0,9 to 1,4 to 10,12 to 2,6 to 1,7 to 10)))
//    println(duplicateCount("abcde"))
//    println(lastDigit(BigInteger("4"), BigInteger("1")))
//    println(tribonacci(doubleArrayOf(0.0,0.0,1.0),10))
//    println(race(720, 850, 70).contentToString())
//    println(race(80, 91, 37).contentToString())
//    println(meeting("Alexis:Wahl;John:Bell;Victoria:Schwarz;Abba:Dorny;Grace:Meta;Ann:Arno;Madison:STAN;Alex:Cornwell;Lewis:Kern;Megan:Stan;Alex:Korn"))
//    println(orderWeightOld("2000 10003 1234000 44444444 9999 11 11 22 123"))
//    println( numberOfPairs( arrayOf("red","red").toList()))
//    println(numberOfPairs( arrayOf("red","green","blue","blue","red","green","red","red","red").toList()))
//    println(findMissingLetter(charArrayOf('a', 'b', 'c', 'd', 'f')))
//    println(findMissingLetter(charArrayOf('O', 'Q', 'R', 'S')))
    println(Num.persistence(999))
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

fun lastDigit(base: BigInteger, exponent: BigInteger): Int {
    return base.modPow(exponent, BigInteger("10")).intValueExact()
}

fun tribonacci(signature: DoubleArray, n: Int): DoubleArray {
    var newNumber : Double
    val doubleArray = signature.toMutableList()
    for(i in 0..n-3) {
        newNumber = doubleArray[i] + doubleArray[i + 1] + doubleArray[i + 2]
        doubleArray.add(newNumber)
    }
    return doubleArray.toDoubleArray()
}

fun race(v1:Int, v2:Int, g:Int):IntArray {
    if(v1>v2){
        return intArrayOf()
    }
    val v3 = v2-v1
    val time:Double = g.toDouble()/v3.toDouble() * 3600
    return intArrayOf(
        (time/3600).toInt(), ((time/60%60).toInt()),
        (time%60).toInt()
    )
}

fun meeting(s: String): String {
    return s.toUpperCase()
        .split(";")
        .map { it.split(":").let { name -> "(${name.last()}, ${name.first()})" } }
        .sorted()
        .joinToString("")
}

fun orderWeightOld(string:String):String {
    val arrWeight:List<String> = string.split(' ')
    val map: MutableMap<String, String> = HashMap()
    for(s in arrWeight){
        if(s.length!=1){
        var sum = 0
        var n = s.toInt()
        while(n != 0){
            sum += (n % 10);
            n/=10;
        }
        map[sum.toString()] = s
        }
    }
    val sortedMap = map.toList().sortedBy {
        (key, value) -> key.toInt()
    }.toMap()
    val sortedInt = sortedMap.keys.toList()
    var answer = ""
    for(i in 0 until sortedMap.size){
        val n2 = sortedInt[i]
        answer += sortedMap[n2]
        answer+=" "
    }
    return answer.trim()
}

fun numberOfPairs(gloves:List<String>) : Int {
    var sumGloves = 0
    val map:MutableMap<String,Int> = HashMap()
    var count = 0;
    for(glove in gloves){
        if(map.containsKey(glove)){
            count = map[glove]!!
        }
        map[glove] = count+1
}
    for (glove in map.keys){
        val c = map[glove]
        if(c!! >1){
            sumGloves += c/2
        }
    }
    return sumGloves
}

fun findMissingLetter(array: CharArray): Char {

    val alphabets = ('A'..'Z').toMutableList()
    for(c in 'a'..'z'){
        alphabets.add(c)
    }
    var flag = false
    var ch = ' '
    var num = 0
    for(c in alphabets){
        if (c==array[num]){
            num += 1
            flag = true
            continue
        }
        if(flag){
        ch = c
        break
        }
    }
    return ch
}

object Num{
    var count = 0
    fun persistence(num: Int) : Int {
        if(num<10)
            return 0
        count++
        var number = num
        var mul = 1
        var a: Int
        while (number>0){
            a = number%10
            if(a!=0)
                mul *= a
            number/=10
        }
        if(mul>9){
            persistence(mul)
        }
        return count
    }
}
