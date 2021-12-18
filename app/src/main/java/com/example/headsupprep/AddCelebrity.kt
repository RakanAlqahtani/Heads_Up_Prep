package com.example.headsupprep

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.headsupprep.API.APIClient
import com.example.headsupprep.API.APIInterface
import com.example.headsupprep.Model.Celebrity
import com.example.headsupprep.Model.CelebrityItem
import kotlinx.android.synthetic.main.addcelebrity.*
import retrofit2.Call
import retrofit2.Response

class AddCelebrity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcelebrity)

        btnBack.setOnClickListener{

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnAdd.setOnClickListener{
            addCelebrity()

        }

    }

    private fun addCelebrity() {
        var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.addCelebrity(
            CelebrityItem(
            edName_up.text.toString(),
            0,
            edTaboo1_up.text.toString(),
            edTaboo2_up.text.toString(),
            edTaboo3_up.text.toString()

            )
        )!!.enqueue(object : retrofit2.Callback<Celebrity> {
            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                Toast.makeText(this@AddCelebrity, "Something went wrong", Toast.LENGTH_LONG).show()

            }


            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {

                Toast.makeText(this@AddCelebrity, "User added!", Toast.LENGTH_LONG).show()
                var intent = Intent(this@AddCelebrity,MainActivity::class.java)
                startActivity(intent)

            }


        })
    }
}