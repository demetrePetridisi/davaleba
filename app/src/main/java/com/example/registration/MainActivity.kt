package com.example.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var emailText: EditText
    private lateinit var password: EditText
    private lateinit var passwordRepeat: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()



        signupButton.setOnClickListener{

            val mail = emailText.text.toString()
            val pass = password.text.toString()
            val passR = passwordRepeat.text.toString()
            mailCheck(mail)



            when {
                mail.isEmpty() || pass.isEmpty() || passR.isEmpty() -> {
                    Toast.makeText(this,"EMPTY!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                pass != passR -> {
                    Toast.makeText(this,"Passwords Match Error!",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                pass.length < 8 ->{
                    Toast.makeText(this,"Password Should Be At Least 8 Chars",Toast.LENGTH_LONG).show()
                }



                pass.length >= 8 && pass==passR && mailCheck(mail) == true -> {

                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                Toast.makeText(this,"Registration Successful !",Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this,"Registration Error",Toast.LENGTH_LONG).show()
                            }

                        }
                }

            }


        }
    }

    private fun init(){
        emailText = findViewById(R.id.emailText)
        password = findViewById(R.id.password)
        passwordRepeat = findViewById(R.id.passwordRepeat)
        signupButton = findViewById(R.id.signupButton)

    }
    private fun mailCheck(mail: String): Boolean{
        if(mail.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            return true
        }else{
            Toast.makeText(this,"Invalid e-mail address !", Toast.LENGTH_LONG).show()
            return false
        }
    }


}