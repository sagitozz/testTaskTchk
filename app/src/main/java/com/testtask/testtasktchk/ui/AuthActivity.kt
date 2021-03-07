package com.testtask.testtasktchk.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.material.snackbar.Snackbar
import com.testtask.testtasktchk.auth.GoogleAuthProvider
import com.testtask.testtasktchk.R
import com.testtask.testtasktchk.app.App
import javax.inject.Inject

/**
 * @autor d.snytko
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var signInButton: View

    @Inject
    internal lateinit var googleAuthProvider : GoogleAuthProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.activityComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_layout)

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
        val intent = googleAuthProvider.getSignIntent()

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
        finish()
    }

    private fun showError() {
        Snackbar.make(signInButton, "Ошибка авторизации", Snackbar.LENGTH_SHORT).show()
    }

    companion object {

        private const val SIGN_GOOGLE_REQUEST = 111

        fun createIntent(context: Context) : Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }
}
