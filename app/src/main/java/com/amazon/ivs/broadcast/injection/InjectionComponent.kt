package com.amazon.ivs.broadcast.injection

import com.amazon.ivs.broadcast.ui.activities.MainActivity
import com.amazon.ivs.broadcast.ui.fragments.BaseFragment
import com.amazon.ivs.broadcast.watchlive.activities.WatchMainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InjectionModule::class])
interface InjectionComponent {
    fun inject(target: MainActivity)
    fun inject(target: BaseFragment)
    fun inject(target: WatchMainActivity)

}
