package com.dsckiet.covid_project_demo

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class MainActivity : AppCompatActivity() {
    private var mSocket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app: SocketInstance = application as SocketInstance
        mSocket = app.getmSocket()

        mSocket?.connect()

        while (!mSocket!!.connected()) {
            progressBar.visibility = VISIBLE

        }
        progressBar.visibility = GONE
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()

        val jsonObject1 = JSONObject()
        try {
            jsonObject1.put(
                "token",
                "TOKEN"
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket!!.on("PATIENTS_POOL_FOR_DOCTOR") { args ->
            val data = args[0] as JSONArray
            //here the data is in JSON Format

          
            runOnUiThread {
                Log.d("test", "test success")
                for (i in 0 until data.length()) {
                    text.text =data[i].toString() + "\n\n\n"
                }
            }
        }
        button.setOnClickListener {
            try {
                mSocket!!.emit(
                    "patientsPoolForDoctor", jsonObject1
                )
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }
}
