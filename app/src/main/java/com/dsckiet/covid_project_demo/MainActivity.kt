package com.dsckiet.covid_project_demo

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject


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

            var jsonObject1 = JSONObject()
            try {
                jsonObject1.put(
                    "patientsPoolForDoctor",
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmMTMzZWEzMTgxOTM2MWUxMDE2Y2U5NCIsIm5hbWUiOiJSb290IERvY3RvciIsImVtYWlsIjoicmF3aWI1NDQzMkBleHBsb3JheGIuY29tIiwicm9sZSI6ImRvY3RvciIsImlhdCI6MTU5NTA5NjkzOX0.Ci50Rfi8oW6BlxIS8IP4329JQEBMDdoFyWex1iq7_sM"
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            button.setOnClickListener {

                mSocket!!.emit(
                    "patientsPoolForDoctor", jsonObject1
                )

                mSocket!!.on("PATIENTS_POOL_FOR_DOCTOR") { args ->
                    val data = args[0] as JSONObject
                    //here the data is in JSON Format

                    Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Pikabooo Here you done.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        //Toast.makeText(this, "hey", Toast.LENGTH_SHORT).show();
                        //whatever your UI logic
                    }
                }



            }




    }
}