package com.testtask.testtasktchk.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.testtask.testtasktchk.R
import com.testtask.testtasktchk.app.App
import com.testtask.testtasktchk.auth.GoogleAuthProvider
import com.testtask.testtasktchk.ui.auth.AuthActivity
import com.testtask.testtasktchk.ui.main.recycler.PaginationListener
import com.testtask.testtasktchk.ui.main.recycler.UsersAdapter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }
    private val usersListAdapter by lazy { UsersAdapter() }
    private val searchView: SearchView by lazy { findViewById(R.id.search_field) }
    private val flowableQuery: Flowable<String> by lazy { RxSearch.from(searchView) }

    @Inject
    internal lateinit var googleAuthProvider: GoogleAuthProvider

    @Inject
    internal lateinit var viewModelFactory: SearchViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.activityComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.retryButton).setOnClickListener {
            searchViewModel.searchFromQuery(flowableQuery)
        }

        initDrawer()
        initRecycler()
        initViewModel()
        initSearchView()
    }

    private fun initRecycler() {
        findViewById<RecyclerView>(R.id.users_recycler).apply {
            adapter = usersListAdapter
            addOnScrollListener(object : PaginationListener(layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                                searchViewModel.searchFromQueryNextPage(searchView.query.toString())
                                Log.d("XXX", "Load more items 2")
                }

                override fun isLastPage(): Boolean = false

                override fun isLoading(): Boolean = searchViewModel.recyclerViewPageLoading
            })
        }
    }

    private fun initViewModel() {
//        searchViewModel.userLiveData.observe(this, Observer { userList ->
//            usersListAdapter.update(userList)
//        })
//        searchViewModel.errorLiveData.observe(this, Observer { error ->
//            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
//        })
        searchViewModel.concLiveData.observe(this, Observer { result ->
            when (result) {
                is SearchViewModel.SearchState.Result ->  {
                    findViewById<View>(R.id.error_layout).isVisible = false
                    usersListAdapter.update(result.data)
                }
                is SearchViewModel.SearchState.Error -> {
                    findViewById<View>(R.id.error_layout).isVisible = true
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
                is SearchViewModel.SearchState.Empty -> Snackbar.make(searchView, "Нет пользователей с похожим именем", Snackbar.LENGTH_SHORT).show()
            }
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
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_baseline_person_24)
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
