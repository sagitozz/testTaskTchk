package com.testtask.testtasktchk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.material.snackbar.Snackbar

/**
 * @autor d.snytko
 */
class AuthActivity : AppCompatActivity() {
    private lateinit var googleAuthHelper: GoogleAuthHelper
    private lateinit var signInButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_layout)

        googleAuthHelper = GoogleAuthHelper(this)
        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener { signInGoogle() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_GOOGLE_REQUEST && resultCode == Activity.RESULT_OK) {
            handleSuccessResult(data)
        } else {
            showError()
        }
    }

    private fun signInGoogle() {
        val intent = googleAuthHelper.getIntent()

        startActivityForResult(intent, SIGN_GOOGLE_REQUEST)
    }

    private fun handleSuccessResult(data: Intent?) {
        val result: GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        val signInAccount: GoogleSignInAccount? = result?.signInAccount

        if (result != null && result.isSuccess && signInAccount != null) {
            openNewActivity(signInAccount)
        } else {
            showError()
        }
    }

    private fun openNewActivity(it: GoogleSignInAccount) {
        startActivity(
            MainActivity.createIntent(
                this,
                name = it.displayName,
                email = it.email,
                avatarUrl = it.photoUrl
            )
        )
    }

    private fun showError() {
        Snackbar.make(signInButton, "Ошибка авторизации", Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val SIGN_GOOGLE_REQUEST = 111
    }
}
