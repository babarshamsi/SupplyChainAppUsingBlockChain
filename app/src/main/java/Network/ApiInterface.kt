package Network

import Model.Response.Chain
import Model.Response.SupplyResponse
import Model.Retailer
import Model.Supplier
import Model.WholeSeller
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {


    @GET("chain")
    fun getChain(): Call<Chain>

    @POST("transactions/new")
    fun postSupplierInfo(@Body supplier: Supplier): Call<SupplyResponse>

    @POST("transactions/new")
    fun postWholeSellerInfo(@Body manufacturer: WholeSeller): Call<SupplyResponse>

    @POST("transactions/new")
    fun postRetailerInfo(@Body retailer: Retailer): Call<SupplyResponse>

    @GET("mine")
    fun doMining(): Call<Void>

    companion object {

//        var BASE_URL = "https://840a-154-192-16-38.ngrok.io/"
//        var BASE_URL = "https://d1e1-154-192-16-38.ngrok.io"
//        var BASE_URL = "https://e05b-154-192-16-38.ngrok.io"
//        var BASE_URL = "https://14a3-111-119-183-44.ngrok.io"
        var BASE_URL = "http://192.168.0.102:3000/"

        fun create() : ApiInterface {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}