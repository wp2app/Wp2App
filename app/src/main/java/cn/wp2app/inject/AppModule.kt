package cn.wp2app.inject

import cn.wp2app.base.BaseViewModel
import cn.wp2app.model.bean.PostInfo
import cn.wp2app.model.repository.UserRepository
import cn.wp2app.ui.fragment.about.AboutViewModel
import cn.wp2app.ui.fragment.home.HomeViewModel
import cn.wp2app.ui.fragment.posts.PostsViewModel
import cn.wp2app.ui.fragment.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule=  module{
    viewModel {AboutViewModel(get())}
    viewModel { PostsViewModel() }
    viewModel { HomeViewModel() }
    viewModel { SearchViewModel() }
}

val repositoryModule = module {
    single { UserRepository(get()) }
    single { BaseViewModel() }
}

val appModule = listOf(viewModelModule, repositoryModule)