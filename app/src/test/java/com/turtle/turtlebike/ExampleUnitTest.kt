package com.turtle.turtlebike

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testPrizeDrawn() {
        val prizes = mutableListOf(1, 2, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5)
        val probabilityMap = mapOf(1 to 0.001, 2 to 0.023, 3 to 0.13, 4 to 0.18, 5 to 0.25)

        println("原始獎品列表: $prizes")
        while (prizes.isNotEmpty()) {
            val drawnPrize = drawPrize(probabilityMap, prizes)
            println("抽取的獎品: $drawnPrize")
            println("抽取後剩餘獎品列表: $prizes")
        }
    }


    @Test
    fun testSumAlternating() {

        val num = 2147483646

        // 開始時間
        val startTime1 = System.currentTimeMillis()

        println("輸入值判斷加權sumSequence1：${sumSequenceCoroutines(num)}")

        // 結束時間
        val endTime1 = System.currentTimeMillis()

        // 計算執行時間
        val duration1 = endTime1 - startTime1

        println("函數執行時間：${duration1}毫秒")

    }
}


fun drawPrize(probabilityMap: Map<Int, Double>, prizes: MutableList<Int>): Int {

    //生成隨機數字，用來判斷哪個獎品被抽中
    var randomValue = Random.nextDouble()
    //將probabilityMap進行降續排序, 中獎概率最高的獎品將會首先被考慮。
    val sortedPrizes = probabilityMap.entries.sortedByDescending { it.value }

    for ((prize, probability) in sortedPrizes) {

        //檢查當前的獎品prize是否仍在獎品池prizes中。如果獎品已經被抽走，則它不應該再次被抽中
        //隨機生成的數字小於該獎品的中獎概率，則從列表中移除該獎品並返回它作為抽中的獎品
        if (prizes.contains(prize) && randomValue < probability) {
            prizes.remove(prize)
            return prize
        }
        randomValue -= probability
    }

    // 如果所有獎項都沒有中，則從剩餘獎項中隨機選一個
    return prizes.removeAt(Random.nextInt(prizes.size))

}

fun sumSequenceCoroutines(N: Int): Int = runBlocking {

    if (N > 1000000000) {

        var sum = 1
        for (i in 2..N) {
            if (i % 2 == 0) {
                sum += i
            } else {
                sum -= i
            }
        }
        return@runBlocking sum

    } else {

        val coroutineCount = 4  // 使用 4 個協程
        val chunkSize = N / coroutineCount
        val sums = IntArray(coroutineCount)
        val jobs = ArrayList<Job>()

        for (chunk in 0 until coroutineCount) {
            val job = launch(Dispatchers.Default) {
                val start = 2 + chunk * chunkSize
                val end = if (chunk == coroutineCount - 1) N + 1 else start + chunkSize
                for (i in start until end) {
                    sums[chunk] += if (i % 2 == 0) i else -i
                }
            }
            jobs.add(job)
        }

        jobs.forEach { it.join() }

        return@runBlocking 1 + sums.sum()

    }

}
