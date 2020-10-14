package cn.wp2app.ui.fragment.posts

import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import cn.wp2app.R
import cn.wp2app.adapter.PostItemAdapter
import cn.wp2app.base.BaseVMFragment
import cn.wp2app.ui.activity.BrowserActivity
import cn.wp2app.ui.activity.PostActivity
import cn.wp2app.utils.getTimeISO8601
import kotlinx.android.synthetic.main.posts_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PostsFragment () : BaseVMFragment<PostsViewModel>(false) {
    private val postsAdapter by lazy { PostItemAdapter() }
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private var categoryId:Int = 0
    //private val categoryId:Int by lazy { arguments?.getInt(CATE_ID) }

    companion object {
        fun newInstance() = PostsFragment()
        val CATE_ID = "category_id"
    }
    override fun getLayoutResId() = R.layout.posts_fragment

    override fun initVM(): PostsViewModel = getViewModel()

    override fun initData() {
        val m = arguments?.getInt(CATE_ID)
        m?.let { categoryId = m }


        //(mBinding as HomeFragmentBinding).viewModel = mViewModel
        if( categoryId > 0 ) {
            mViewModel.getPost(categoryId)
        } else {
            mViewModel.getPost()
        }

        postsAdapter.setEmptyView(R.layout.loading_view)
    }

    override fun initView() {
        posts_refresh.setOnRefreshListener {
            refresh()
        }
        initRecycleView()
    }

    override fun startObserve() {
        mViewModel.apply {
            posts.observe(viewLifecycleOwner, Observer {
                    if( it.isEmpty() ) {
                        postsAdapter.loadMoreModule?.isEnableLoadMore = false
                        postsAdapter.loadMoreModule?.loadMoreEnd(true)
                        postsAdapter.setEmptyView(R.layout.empty_view)
                    } else {
                        postsAdapter.addData(it)
                        postsAdapter.loadMoreModule?.isEnableLoadMore = true
                        postsAdapter.loadMoreModule?.loadMoreComplete()
                    }
            })

            postsAfter.observe(viewLifecycleOwner, Observer {
                posts_refresh.isRefreshing = false
                postsAdapter.run {
                    if(it.isNotEmpty() )  {
                        addData(0,  it)
                    }

                    loadMoreModule?.isEnableLoadMore = true
                }
            })

        }
    }

    private fun initRecycleView() {
        posts_list.run {
            layoutManager = LinearLayoutManager(activity)
            //addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10)))
            adapter = postsAdapter
        }

        postsAdapter.run {
            isUseEmpty = true
            setEmptyView(R.layout.empty_view)
            //loadMoreModule?.loadMoreView = CustomLoadMoreView()
            loadMoreModule?.isEnableLoadMore = true
            //loadMoreModule?.isEnableLoadMoreIfNotFullPage = true
            loadMoreModule?.enableLoadMoreEndClick=true

            loadMoreModule?.setOnLoadMoreListener { loadMore() }

            setOnItemClickListener { _, _, position ->
                var bWebView = false
                var sp = context?.getSharedPreferences("wp_options", AppCompatActivity.MODE_PRIVATE)
                bWebView =sp?.getBoolean("post_use_web_view", true) as Boolean
                if( bWebView ) {
                    val bundle = bundleOf(BrowserActivity.URL to postsAdapter.data[position].link)
                    androidx.navigation.Navigation.findNavController(posts_list)
                        .navigate(R.id.action_mainFragment_to_browserActivity, bundle)
                } else {
                    val bundle = bundleOf(PostActivity.TITLE to postsAdapter.data[position].title.rendered)
                    bundle.putString(PostActivity.CONTENT, postsAdapter.data[position].content.rendered)

                    androidx.navigation.Navigation.findNavController(posts_list)
                        .navigate(R.id.action_mainFragment_to_postActivity, bundle)
                }
            }
        }

    }

    private fun loadMore() {
        var date = postsAdapter.data[postsAdapter.data.size-1].date

        if( categoryId == 0)  mViewModel.getPostBefore(date)
        else mViewModel.getPostBefore(categoryId, date)
    }

    // 下拉刷新
    private fun refresh() {
        var date:String = getTimeISO8601()
        if( postsAdapter.data.isNotEmpty() ) date = postsAdapter.data[0].date

        postsAdapter.run {
            loadMoreModule?.isEnableLoadMore = false
        }

        if( categoryId == 0)  mViewModel.getPostAfter(date)
        else mViewModel.getPostAfter(categoryId, date)

        posts_refresh.isRefreshing = true
    }

}