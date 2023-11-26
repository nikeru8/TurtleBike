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

    /**
    第 2 題.

    抽抽樂總共有五種獎項, 1,2 獎各只有一個, 3 獎有 2 個，4 獎有 5 個，5 獎有 11 個，請寫出一個程式可以「隨機」的取得「不重複」的禮物，

    且：
    1 獎中獎機率為 0.1%
    2 獎中獎機率為 2.3%
    3 獎中獎機率為 13%
    4 獎中獎機率為 18%
    5 獎中獎機率為 25%


    # 這一題要麻煩大家不要生成隨機陣列！！！
    重點請放在抽獎這件事情上
    抽獎的本質就是不重複的隨機抽取

    # 不重複：是指禮物被抽走之後，就不能再被抽了！！！
    例如 3 獎是 3 隻 iPhone，iPhone A 被抽走了，給人了，理所當然就不能再被抽，但是，抽獎箱內還有 iPhone B 跟 iPhone C 可以抽！！

    # 解題的方法很多種，但是，我們題目已經註明不要使用的方法就請大家不要使用~ 謝謝！

    # 所以，顯示結果的舉例：
    （這裡只是舉例，目的是避免大家花時間在生成隨機陣列，不代表您的答案只能長這樣）

    抽到 3 號獎 目前尚未抽取的獎勵為 [ 1 , 2 , 4 , 4 , 4 , 5 , 5 , 5 , 5]
    抽到 4 號獎 目前尚未抽取的獎勵為 [ 1 , 2 , 4 , 4 , 5 , 5 , 5 , 5]
    抽到 5 號獎 目前尚未抽取的獎勵為 [ 1 , 2 , 4 , 4 , 5 , 5 , 5]
    …..


    抽到禮物箱內沒有禮物為止。

     * */
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


    /**
    第 1 題.

    寫一個函式計算下列公式之總和：

    1+2-3+4-5+6-.....+ 或 -  N


    N = 面試現場提供（N 一定是正整數）

    有個小要求：
    面試現場會使用您的電腦代入值，代入 N 值之後程式現場運行出結果的時間不能超過 2 秒，且不會造成當機。
     * */
    @Test
    fun testSumAlternating() {

        val num = 2147483646

        // 開始時間
        val startTime = System.currentTimeMillis()

        println("輸入值判斷加權sumSequence：${sumSequenceCoroutines(num)}")

        // 結束時間
        val endTime = System.currentTimeMillis()

        // 計算執行時間
        val duration = endTime - startTime

        println("函數執行時間：${duration}毫秒")

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
