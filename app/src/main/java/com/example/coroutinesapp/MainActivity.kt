package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var clMain: ConstraintLayout
    private lateinit var tv_advice: TextView
    private lateinit var bt_getAdvice: Button

    private var advice: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init vars
        initvars()

    }

    private fun initvars() {
        clMain = findViewById(R.id.clMain)
        tv_advice = findViewById(R.id.tv_advice)
        bt_getAdvice = findViewById(R.id.bt_getAdvice)

        bt_getAdvice.setOnClickListener { getAdvice() }
    }

    private fun getAdvice() {
        // init Coroutine
        CoroutineScope(IO).launch {
            val result = getResult()
            withContext(Main) {

            }
        }
    }

    private suspend fun getResult(){

        // API handler
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<adviceDetails?>? = apiInterface!!.doGetListResources()
        call?.enqueue(object : retrofit2.Callback<adviceDetails?> {
            override fun onResponse  (
                call: Call<adviceDetails?>?,
                response: Response<adviceDetails?>
            ) {
                val x: adviceDetails? = response.body()
                try {
                    advice = x?.slip?.advice.toString()
                    tv_advice.text = advice
                    Log.d("MainActivityAPI", "Alive $advice")
                } catch (e: Exception) {
                    Log.d("MainActivityAPI", "Caught an error in API ")
                }

            }
            override fun onFailure(call: Call<adviceDetails?>, t: Throwable) {
                Log.d("MainActivityAPI", "API failed!")
                Log.d("MainActivityAPI", "$t")
                call.cancel()
            }
        })
    }

}