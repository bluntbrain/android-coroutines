package com.ishanlakhwani.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "Result #1"
    private val RESULT_2 = "Result #2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
//            CoroutineScope: Defines a scope for new coroutines. Every coroutine builder (like launch, async, etc) is an extension on CoroutineScope
//            and inherits its coroutineContext to automatically propagate all its elements and cancellation
            // Coroutines are thread independent

            //IO, Main, default
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private fun setNewText(input: String){
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResultFromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)

        val result2 = getResult2FromApi()
        setTextOnMainThread(result2)
    }


    // suspend keyword means it can be called in a coroutine and then executes on a background thread
    // coroutines are not threads, many coroutines can be inside a thread
    private suspend fun getResultFromApi(): String{
        logThread("getResultFromApi")
        delay(1000);
        return RESULT_1

    }

    private suspend fun getResult2FromApi():String{
        logThread("getResult2FromApi")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

}