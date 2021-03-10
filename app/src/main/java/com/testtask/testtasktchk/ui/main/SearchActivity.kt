package com.testtask.testtasktchk.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import com.testtask.testtasktchk.R
import com.testtask.testtasktchk.app.App
import com.testtask.testtasktchk.auth.GoogleAuthProvider
import com.testtask.testtasktchk.ui.auth.AuthActivity
import io.reactivex.Flowable
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }
    private val usersListAdapter by lazy { UsersAdapter() }
    private val flowableQuery: Flowable<String> by lazy { RxSearch.from(searchView) }
    private val searchView: SearchView by lazy { findViewById(R.id.search_field) }

    @Inject
    internal lateinit var googleAuthProvider: GoogleAuthProvider

    @Inject
    internal lateinit var viewModelFactory: SearchViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.activityComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawer()
        initRecycler()
        initViewModel()
        initSearchView()
    }

    private fun initRecycler() {
        findViewById<RecyclerView>(R.id.users_recycler).apply {
            adapter = usersListAdapter
            addOnScrollListener(object : PaginationListener(layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() = searchViewModel.searchFromQueryNextPage(searchView.query.toString())

                override fun isLastPage(): Boolean = false

                override fun isLoading(): Boolean = searchViewModel.recyclerViewPageLoading
            })
        }
    }

    private fun initViewModel() {
        searchViewModel.userLiveData.observe(this, Observer { userList ->
            usersListAdapter.update(userList)
        })
        searchViewModel.errorLiveData.observe(this, Observer { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
        })
    }

    private fun initDrawer() {
        findViewById<View>(R.id.logout_button).setOnClickListener { logout() }

        val navigationView = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0)

        with(navigationView) {
            findViewById<TextView>(R.id.user_name).text = intent.getStringExtra(EXTRA_NAME)
            findViewById<TextView>(R.id.user_email).text = intent.getStringExtra(EXTRA_EMAIL)

            Picasso.get()
                .load(intent.getStringExtra(EXTRA_AVATAR_URL))
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(findViewById<ImageView>(R.id.user_avatar_image))
        }
    }

    private fun initSearchView() {

        searchViewModel.searchFromQuery(flowableQuery)
    }

    private fun logout() {
        googleAuthProvider.logout()
        startActivity(AuthActivity.createIntent(this))
        finish()
    }

    companion object {

        private const val EXTRA_NAME = "name"
        private const val EXTRA_EMAIL = "email"
        private const val EXTRA_AVATAR_URL = "avatar_url"

        fun createIntent(context: Context, name: String?, email: String?, avatarUrl: Uri?): Intent {
            return Intent(context, SearchActivity::class.java)
                .putExtra(EXTRA_NAME, name)
                .putExtra(EXTRA_EMAIL, email)
                .putExtra(EXTRA_AVATAR_URL, avatarUrl)
        }
    }
}
