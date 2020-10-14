package cn.wp2app.ui.fragment.categories

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import cn.wp2app.R
import cn.wp2app.adapter.category.CategoriesAdapter
import cn.wp2app.adapter.category.CategorySection
import cn.wp2app.base.BaseVMFragment
import cn.wp2app.config.Constant
import cn.wp2app.decoration.GridSectionAverageGapItemDecoration
import cn.wp2app.entity.HomeBarUnit
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.categories_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

class CategoriesFragment : BaseVMFragment<CategoriesViewModel>(false) {
    private val cateAdapter by lazy {
        CategoriesAdapter(
            R.layout.category_section,
            R.layout.category_section_header
        )
    }

    var cur:Int = -1  //当前页面的parent id
    var stack:MutableList<Int> = mutableListOf()

    companion object {
        fun newInstance() = CategoriesFragment()
    }
    override fun getLayoutResId() = R.layout.categories_fragment

    override fun initData() {
        cur = 0
        mViewModel.getCategoriesByPrent(0)
    }

    override fun initVM(): CategoriesViewModel {
        return ViewModelProvider(this)[CategoriesViewModel::class.java]
    }

    override fun initView() {
        initRecyclerView()
    }

    // 只调用两层
    override fun startObserve() {
        mViewModel.apply {
            category.observe(viewLifecycleOwner, Observer {
                cateAdapter.run {
                    data.add(CategorySection(true, it))

                    mViewModel.getCategoriesByPrent(cur)
                }
            })

            categories.observe(viewLifecycleOwner, Observer {
                cateAdapter.run {
                    addData(it)
                }
            })
        }
    }

    private fun initRecyclerView() {
        categories_list.run {
            val grid = GridLayoutManager(activity, 3)
            layoutManager =grid
            //addItemDecoration(GridSectionAverageGapItemDecoration(10F, 10F, 20F, 0F))
            adapter = cateAdapter
        }
        
        cateAdapter.setOnItemClickListener { adapter, view, position -> var item : CategorySection = adapter.data.get(position) as CategorySection
                //refresh
            stack.add(cur)
            cur = item.category.id
            cateAdapter.replaceData(mutableListOf())
            cateAdapter.data.add(CategorySection(true,item.category))

            mViewModel.getCategoriesByPrent(cur)
        }

        cateAdapter.setOnItemChildClickListener(OnItemChildClickListener { adapter, view, position ->
            when(view.id) {
                R.id.category_back -> {
                    var last = stack.last()
                    stack.removeAt(stack.size-1)
                    cateAdapter.replaceData(mutableListOf())

                    if( last > 0) {
                        cur = last
                        mViewModel.getCategoryById(last)
                    }
                    else {
                        cur = 0
                        mViewModel.getCategoriesByPrent(0)
                    }
                }
                R.id.add_to_home -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        var fn = activity?.filesDir.toString() + "/options"
                        var file = File(fn)
                        if( !file.exists()) file.mkdirs()

                        file = File(fn+"/"+Constant.HOME_TOP_BAT_FILE)
                        var content : String  = ""
                        if( file.exists()) content = file.readText()
                        var list:MutableList<HomeBarUnit> = mutableListOf()

                        if( content.isNotEmpty() ) {
                            list = Gson().fromJson(content, object:TypeToken<MutableList<HomeBarUnit>>(){}.type)
                        }

                        var category = adapter.data[position] as CategorySection
                        var unit:HomeBarUnit = HomeBarUnit(category.category.id, category.category.name)

                        if( !list.contains(unit)) {
                            list.add(unit)

                            var json_str = Gson().toJson(list)

                            if( !file.exists() ) file.createNewFile()
                            file.writeText(json_str)

                            withContext(Dispatchers.Main) {
                                Toast.makeText(activity, "添加成功", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(activity, "已存在", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            }
        })
    }

}
