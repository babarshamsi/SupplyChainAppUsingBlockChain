package com.supplychainapp

import Model.Response.Chain
import Network.ApiInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.supplychainapp.R
import kotlinx.android.synthetic.main.activity_consumer.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer)
        getScannedData()
    }

    private fun getScannedData() {
        val intent = intent
        val supplierInfo = intent.getStringExtra("QRImage")
        convertJSONData(supplierInfo)
    }

    private fun convertJSONData(supplierInfo: String?) {
        try {
            val obj = JSONObject(supplierInfo)
            val index = obj.getInt("index")

            getBlockChainInfo(index)

        } catch (t: Throwable) {
            Log.e("MyApp", "Could not parse malformed JSON: \"" + supplierInfo.toString() + "\"")
        }

    }

    private fun getBlockChainInfo(index: Int) {
        val apiInterface = ApiInterface.create().getChain()

        apiInterface.enqueue( object : Callback<Chain> {
            override fun onResponse(call: Call<Chain>?, response: Response<Chain>?) {

                if(response?.body() != null)
                    getCurrentTransactions(index, response.body() as Chain)

            }

            override fun onFailure(call: Call<Chain>?, t: Throwable?) {
                Log.e("MyApp", "Could not parse malformed JSON: \"" + t.toString() + "\"")
            }
        })
    }

    private fun getCurrentTransactions(index: Int, chain: Chain) {
        try {
            val transactions = chain.chain.get(index - 1).transactions
            supplierInfo?.apply {
                addInfoCards(transactions)
            }
        }
        catch (ex: IndexOutOfBoundsException) {
            Toast.makeText(this, "Some Issue with Indexing", Toast.LENGTH_SHORT).show()
        }
    }
}