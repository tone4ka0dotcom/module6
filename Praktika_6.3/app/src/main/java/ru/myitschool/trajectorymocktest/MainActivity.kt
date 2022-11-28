package ru.myitschool.trajectorymocktest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import ru.myitschool.trajectorymocktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txt = findViewById<EditText>(R.id.edit_text_login)
        val button = findViewById<Button>(R.id.button_send)
        val result = findViewById<TextView>(R.id.requested_data)

        val url = "https://gitee.com/api/v5/users/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: UserController = retrofit.create(UserController::class.java)

        button.setOnClickListener{

            val call: Call<User> =  service.getRepos(txt.text.toString())
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user: User? = response.body()
                    if (user != null) {
                        result.text = user.public_repos.toString()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(applicationContext,"fail",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

interface UserController {

    @GET("{userName}")
    fun getRepos(@Path("userName") userName:String):Call<User>
}
//class User(var firstName: String, var lastName: String)