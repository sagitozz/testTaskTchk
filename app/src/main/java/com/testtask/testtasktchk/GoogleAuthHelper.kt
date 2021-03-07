package com.testtask.testtasktchk

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * @autor d.snytko
 */
class GoogleAuthHelper(context: Context) {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    private var mGoogleSignInClient : GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    fun getIntent () : Intent {
        return mGoogleSignInClient.signInIntent
    }
    fun logout() {
        mGoogleSignInClient.revokeAccess()
    }

}

