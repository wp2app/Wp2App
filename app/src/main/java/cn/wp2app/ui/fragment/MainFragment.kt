package cn.wp2app.ui.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

import cn.wp2app.R
import cn.wp2app.base.BaseFragment
import cn.wp2app.ui.fragment.about.AboutFragment
import cn.wp2app.ui.fragment.categories.CategoriesFragment
import cn.wp2app.ui.fragment.home.HomeFragment
import cn.wp2app.ui.fragment.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_fragment.*
/*
 功能：开始第一个Fragment，里面包含底栏对应的fragment

 日期：2020.3.20
 */
class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var fragments = mutableListOf<Fragment>()

    private lateinit var viewModel: MainViewModel

    /*
    init {
        fragments.run {
            add(home)
            add(categories)
            add(search)
            add(about)
        }
    }
    */

    override fun getLayoutResId() = R.layout.main_fragment

    override fun initView() {

    }

    override fun initData() {
        start()

        initViewPager()
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelected)
    }


    fun start() {
        var home = HomeFragment()
        var categories = CategoriesFragment()
        var search = SearchFragment()
        var about = AboutFragment()

        var list:MutableList<Fragment> = mutableListOf()
        list.add(home)
        list.add(categories)
        list.add(search)
        list.add(about)

        fragments = mutableListOf<Fragment>()
        fragments.addAll(list)

        mainViewpager.adapter = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initViewPager() {
        mainViewpager.isUserInputEnabled = false
        mainViewpager.offscreenPageLimit = 2
        mainViewpager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragments[position]

            override fun getItemCount() = fragments.size
        }
    }

    private val onNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                switchFragment(0)
            }
            R.id.category -> {
                switchFragment(1)
            }
            R.id.search -> {
                switchFragment(2)
            }
            R.id.about -> {
                switchFragment(3)
            }
        }
        true
    }

    private fun switchFragment(position: Int): Boolean {
//        mainViewpager.currentItem = position
        mainViewpager.setCurrentItem(position, false)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(resultCode) {
            AppCompatActivity.RESULT_OK->{
                val refresh: Boolean? = data?.extras?.getBoolean("do_refresh", false)
                refresh?.let { if(refresh) {
                    initData()

                    navView.selectedItemId = navView.menu.getItem(0).itemId;

                }}
            }
            else -> {}
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
