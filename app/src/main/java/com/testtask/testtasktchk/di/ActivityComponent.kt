package com.testtask.testtasktchk.di

import com.testtask.testtasktchk.di.qualifiers.ActivityScope
import com.testtask.testtasktchk.ui.auth.AuthActivity
import com.testtask.testtasktchk.ui.main.SearchActivity
import dagger.Subcomponent

/**
 * @autor d.snytko
 */
@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun inject(authActivity: AuthActivity)

    fun inject(searchActivity: SearchActivity)
}
