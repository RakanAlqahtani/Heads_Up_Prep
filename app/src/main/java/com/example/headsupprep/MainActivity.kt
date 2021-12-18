package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headsupprep.API.APIClient
import com.example.headsupprep.API.APIInterface
import com.example.headsupprep.Model.Celebrity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var celebrity = Celebrity()
    private lateinit var adapter : RVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         adapter = RVAdapter(celebrity)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)

        requestAPI()
        btnGoAdd.setOnClickListener{

            goToAddCelebrity()
        }
btnSubmit.setOnClickListener{
    if (editTextTextPersonName.text.isNotEmpty()){
        var check = editTextTextPersonName.text.toString()
        print(check)

        searchForMatch()


    }


}
    }



    private fun requestAPI() {

        var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getCelebrities()?.enqueue(object : Callback<Celebrity>{
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                celebrity = response.body()!!

                adapter.update(celebrity)
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
            Toast.makeText(this@MainActivity,"some thing wrong",Toast.LENGTH_LONG).show()




            }


        })

    }

    private fun searchForMatch() {
        print("!!!!!  $celebrity")

        for (c in celebrity){
            if(editTextTextPersonName.text.toString().capitalize().equals(c.name)){

                print("!!!!! ${c.name}")
               var intent = Intent(this@MainActivity, UpdateDeleteCelebrity::class.java)
                    var celebrityID = c.pk
                    intent.putExtra("celebrityID",celebrityID)
//                   var taboo1 = c.taboo1
//                   intent.putExtra("taboo1",taboo1)
//                   var taboo2 = c.taboo2
//                   intent.putExtra("taboo2",taboo2)
//                   var taboo3 = c.taboo3
//                   intent.putExtra("taboo3",taboo3)


                startActivity(intent)
            }
        }

    }

    private fun goToAddCelebrity() {
        val intent = Intent(this,AddCelebrity::class.java)
        startActivity(intent)
    }



}