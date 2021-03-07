package com.testtask.testtasktchk

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth

class MainActivity : AppCompatActivity() {
    private lateinit var googleAuthHelper: GoogleAuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googleAuthHelper = GoogleAuthHelper(this)

        findViewById<View>(R.id.sign_in_button).setOnClickListener {
            signInGoogle()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_GOOGLE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

                if (result != null) {
                    if (result.isSuccess) {
                        result.signInAccount?.let {
                            Toast.makeText(this, result.signInAccount!!.displayName, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
            //TODO обработать результат
            }
        }
    }

    private fun signInGoogle() {
        val intent = googleAuthHelper.getIntent()

        startActivityForResult(intent, SIGN_GOOGLE_REQUEST)
    }

    companion object {
        const val SIGN_GOOGLE_REQUEST = 111
    }
}
