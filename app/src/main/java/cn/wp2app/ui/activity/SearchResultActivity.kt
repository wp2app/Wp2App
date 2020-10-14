package cn.wp2app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.wp2app.R
import cn.wp2app.adapter.PostItemAdapter
import cn.wp2app.base.BaseActivity
import cn.wp2app.base.BaseVMActivity
import cn.wp2app.model.bean.post.WPost
import cn.wp2app.ui.fragment.search.SearchViewModel
import cn.wp2app.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_browser.*
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.title_layout.*

class SearchResultActivity : BaseVMActivity<SearchViewModel>(false) {
    private val searchAdapter by lazy { PostItemAdapter() }

    private fun initRecycleView() {
        search_list.run {
            layoutManager = LinearLayoutManager(context)
            //addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10)))
            adapter = searchAdapter
        }

        searchAdapter.run {
            isUseEmpty = true
            setEmptyView(R.layout.empty_view)
            //loadMoreModule?.loadMoreView = CustomLoadMoreView()
            //loadMoreModule?.isEnableLoadMore = true
            //loadMoreModule?.isEnableLoadMoreIfNotFullPage = true
            //loadMoreModule?.enableLoadMoreEndClick=true
            //loadMoreModule?.setOnLoadMoreListener { loadMore() }

            setOnItemClickListener { _, _, position ->
                val bundle = bundleOf(BrowserActivity.URL to searchAdapter.data[position].link)
                var intent: Intent = Intent(this@SearchResultActivity, BrowserActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
                //androidx.navigation.Navigation.findNavController(search_list).navigate(R.id.action_searchResultActivity_to_browserActivity, bundle)
            }
            //onItemChildClickListener = this@HomeFragment.onItemChildClickListener
        }


        //homeAdapter.loadMoreModule!!.isAutoLoadMore = true


    }

    override fun initVM(): SearchViewModel  = ViewModelProvider(this)[SearchViewModel::class.java]

    override fun getLayoutResId() = R.layout.activity_search_result

    override fun initView() {
        var s = intent?.extras?.getString("title")
        toolbar_title.text = s
        initRecycleView()

        toolbar_back.setOnClickListener { onBackPressed() }

        searchAdapter.setEmptyView(R.layout.loading_view)
    }

    override fun initData() {
        intent?.extras?.getInt("type").let { it ->
            when (it) {
                1 -> {
                    var key = intent.extras?.getString("key")
                    if (key != null) {
                        title = key
                        mViewModel.search(key)
                    }
                }
                2 -> {
                    var tag = intent.extras?.getInt("tag")
                    if (tag != null) {
                        mViewModel.getPostsByTag(tag)
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun startObserve() {
        mViewModel.apply {
            posts.observe(this@SearchResultActivity, Observer {
                if (it.isEmpty()) {
                    //withContext(Dispatchers.Main) {
                    Toast.makeText(this@SearchResultActivity, "啥都没找到，换个关键词吧", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    //}
                } else {
                    if( it.isEmpty()) {
                        searchAdapter.run {
                            loadMoreModule?.isEnableLoadMore = false
                            loadMoreModule?.loadMoreEnd(true)
                            setEmptyView(R.layout.empty_view)
                        }
                    } else {
                        searchAdapter.replaceData(it)

                        //searchAdapter.loadMoreModule?.isEnableLoadMore = true
                    }

                    searchAdapter.run {
                        loadMoreModule?.loadMoreComplete()
                    }
                }
            })
        }
    }
}


