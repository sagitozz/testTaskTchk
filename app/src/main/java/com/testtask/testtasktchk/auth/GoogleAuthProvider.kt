package com.testtask.testtasktchk.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject

/**
 * @autor d.snytko
 */
interface GoogleAuthProvider {

    fun getSignIntent(): Intent

    fun logout()
}

class GoogleAuthProviderImpl @Inject constructor(context: Context) : GoogleAuthProvider {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    private var mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    override fun getSignIntent(): Intent {
        return mGoogleSignInClient.signInIntent
    }

    override fun logout() {
        mGoogleSignInClient.revokeAccess()
    }
}

