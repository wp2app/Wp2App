package cn.wp2app.ui.fragment.search

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.wp2app.R
import cn.wp2app.adapter.PostItemAdapter
import cn.wp2app.base.BaseVMFragment
import cn.wp2app.model.bean.post.WPost
import cn.wp2app.model.bean.tag.WpTag
import cn.wp2app.ui.activity.BrowserActivity
import cn.wp2app.utils.hideKeyboard
import com.cunoraz.tagview.Tag
import com.cunoraz.tagview.TagView.OnTagClickListener
import com.cunoraz.tagview.TagView.OnTagLongClickListener
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : BaseVMFragment<SearchViewModel>(false) {

    companion object {
        fun newInstance() = SearchFragment()
    }

    //private var key = ""
    override fun getLayoutResId() = R.layout.search_fragment

    override fun initData() {
        mViewModel.getTags()
    }

    override fun initVM() = ViewModelProvider(this)[SearchViewModel::class.java]

    override fun initView() {
        search_view.run {
            //            isIconified = false
//            onActionViewExpanded()
            setOnQueryTextListener(onQueryTextListener)
        }

        search_tag_group.setOnTagLongClickListener(OnTagLongClickListener { tag, position ->

        })

        search_tag_group.setOnTagClickListener(OnTagClickListener { tag, position ->
            val bundle = bundleOf("tag" to tag.id)
            bundle.putInt("type", 2)
            bundle.putString("title", tag.text)
            androidx.navigation.Navigation.findNavController(search_tag_group).navigate(R.id.action_mainFragment_to_searchResultActivity, bundle)
        })

    }

    override fun startObserve() {
        mViewModel.apply {
            wpTag.observe(viewLifecycleOwner, Observer {
                var  list:ArrayList<Tag>  = arrayListOf()

                for( wt:WpTag  in  it)  {
                    var tag : Tag  = Tag(wt.name)
                    tag.id  = wt.id
                    tag.radius = 10.0f

                    list.add(tag)
                }

                search_tag_group.addTags(list)
            })
        }
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String?) = false

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                ///startSearch(key)
                val bundle = bundleOf("key" to query)
                bundle.putInt("type", 1)
                bundle.putString("title", query)
                androidx.navigation.Navigation.findNavController(search_view)
                    .navigate(R.id.action_mainFragment_to_searchResultActivity, bundle)
            }

            return true
        }
    }
}


