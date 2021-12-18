package com.example.headsupprep

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.headsupprep.API.APIClient
import com.example.headsupprep.API.APIInterface
import com.example.headsupprep.Model.CelebrityItem
import kotlinx.android.synthetic.main.addcelebrity.*
import kotlinx.android.synthetic.main.addcelebrity.edName_up
import kotlinx.android.synthetic.main.addcelebrity.edTaboo1_up
import kotlinx.android.synthetic.main.addcelebrity.edTaboo2_up
import kotlinx.android.synthetic.main.addcelebrity.edTaboo3_up
import kotlinx.android.synthetic.main.updatedeletecelebrity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteCelebrity:AppCompatActivity() {
private var celebrityID =0
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.updatedeletecelebrity)

        celebrityID = intent.extras!!.getInt("celebrityID", 0)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")

        getCelebrity()

        btnBack1.setOnClickListener{

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        btnUpdate.setOnClickListener{

            updateAPI(celebrityID)

        }
        btnDelete.setOnClickListener{

            deleteAPI(celebrityID)

        }
    }

    private fun deleteAPI(id: Int) {
        var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface?.deleteCelebrity(id)!!.enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@UpdateDeleteCelebrity,"Delete",Toast.LENGTH_LONG).show()

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@UpdateDeleteCelebrity,"Some thing wrong",Toast.LENGTH_LONG).show()
                }


            })

    }

    private fun updateAPI(celebrityID: Int) {

        var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.updateCelebrity(celebrityID, CelebrityItem(
            edName_up.text.toString(),
            0,

                    edTaboo1_up.text.toString(),
                    edTaboo2_up.text.toString(),
                    edTaboo3_up.text.toString()
        )
        )!!.enqueue(object : Callback<CelebrityItem>{
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {
                Toast.makeText(this@UpdateDeleteCelebrity,"Update",Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                Toast.makeText(this@UpdateDeleteCelebrity,"Some thing wrong",Toast.LENGTH_LONG).show()
            }


        })

    }

    private fun getCelebrity() {
        var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getCelebrity(celebrityID)?.enqueue(object : Callback<CelebrityItem>{
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {
                progressDialog.dismiss()
                val celebrity = response.body()!!

                edName_up.setText(celebrity.name)
                edTaboo1_up.setText(celebrity.taboo1)
                edTaboo2_up.setText(celebrity.taboo2)
                edTaboo3_up.setText(celebrity.taboo3)
            }

            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteCelebrity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }
}