package cn.wp2app.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.wp2app.R
import cn.wp2app.base.BaseVMFragment
import cn.wp2app.entity.HomeBarUnit
import cn.wp2app.ui.fragment.posts.PostsFragment
import cn.wp2app.ui.fragment.search.SearchViewModel
import com.cunoraz.tagview.Tag
import com.cunoraz.tagview.TagView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*

/*
  功能： 1 最新文章列表，默认展示的第一屏； 2 （设置项）展示分类


  日期：2020.3.20
 */
class HomeFragment : BaseVMFragment<HomeViewModel>(true) {
    private val fragmentList = arrayListOf<Fragment>()
    var titleList:MutableList<String> = mutableListOf()
    var topbar:MutableList<HomeBarUnit> = mutableListOf()

    private var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    init {
        var fragment = PostsFragment()
        val bundle = Bundle()
        bundle.putInt(PostsFragment.CATE_ID, 0)
        fragment.arguments = bundle
        fragmentList.add(fragment)
        titleList.add("最新")
    }
    companion object {
        fun newInstance() = HomeFragment()
    }
    override fun getLayoutResId() = R.layout.home_fragment

    override fun initVM(): HomeViewModel = getViewModel()

    override fun initData() {
       // retainInstance = true  //销毁，创建
        mViewModel.getTopBarData(activity)
    }

    override fun initView() {
        //initViewPager()
        iv_category.setOnClickListener {showCategoryEdit()}
    }

    override fun startObserve() {
        mViewModel.apply {            // 顶部tab栏
            topBar.observe(viewLifecycleOwner, Observer {
                topbar = it
                if( it.size > 0) iv_category.visibility = View.VISIBLE

                for (item: HomeBarUnit in it) {
                    var fragment = PostsFragment()
                    val bundle = Bundle()
                    bundle.putInt(PostsFragment.CATE_ID, item.id)
                    fragment.arguments = bundle
                    fragmentList.add(fragment)
                    titleList.add(item.title)
                }

                initViewPager()
            })
        }
    }

    private fun initViewPager() {
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragmentList[position]

            override fun getItemCount() = titleList.size
        }


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        if (mOnPageChangeCallback == null) mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                //if (position == 1) addFab.show() else addFab.hide()
            }
        }
        mOnPageChangeCallback?.let { viewPager.registerOnPageChangeCallback(it) }
    }

    override fun onStop() {
        super.onStop()
        mOnPageChangeCallback?.let { viewPager.unregisterOnPageChangeCallback(it) }
    }

    fun showCategoryEdit() {
        val dialog: AlertDialog? = context?.let { it1 -> AlertDialog.Builder(it1).create() }
        dialog?.show()
        val window: Window? = dialog?.window
        window?.setContentView(R.layout.category_edit_dialog_layout)
        val tagGroup: TagView? = window?.findViewById(R.id.tag_group_alert)
        val close: ImageView? = window?.findViewById(R.id.close)
        val title: TextView? = window?.findViewById(R.id.title)

        close?.setOnClickListener { dialog?.dismiss() }
        title?.text = "编辑首页显示的分类"

        val tags = ArrayList<Tag>()

        for (i in topbar.indices) {
            val tag = Tag(topbar.get(i).title)
            //tag.radius = 10f;
            tag.isDeletable = true
            tags.add(tag)
        }

        tagGroup!!.addTags(tags)

        tagGroup!!.setOnTagClickListener { tag, position -> }

        tagGroup!!.setOnTagDeleteListener { view, tag, position ->
            val builder: AlertDialog.Builder? =   context?.let { it1 -> AlertDialog.Builder(it1) };

            builder?.setMessage("\"删除" + tag.text.toString() + "确定吗\" ?")
            builder?.setPositiveButton(
                "确定"
            ) { dialog, which ->
                context?.let { mViewModel.delShowCategory(position, it) }
                tabLayout.removeTabAt(position +1)
                view.remove(position)
                Toast.makeText(
                    context,
                    "\"" + tag.text.toString() + "\" 已删除",
                    Toast.LENGTH_SHORT
                ).show()

                if( tabLayout.size == 1 ) iv_category.visibility = View.GONE
            }
            builder?.setNegativeButton("取消", null)
            builder?.show()
        }

        tagGroup!!.setOnTagLongClickListener { tag, position -> }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onPause() {
        super.onPause()
    }
}
