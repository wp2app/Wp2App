package cn.wp2app.adapter.category

import cn.wp2app.R
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class CategoriesAdapter(layoutResId:Int, sectionHeadResId:Int) :
    BaseSectionQuickAdapter<CategorySection, BaseViewHolder>(sectionHeadResId, layoutResId) {
    init {
        setNormalLayout(layoutResId)
        addChildClickViewIds(R.id.category_back)
        addChildClickViewIds(R.id.add_to_home)
    }

    override fun convertHeader(helper: BaseViewHolder, item: CategorySection) {
        helper.setText(R.id.category_title, item.category.name)
    }

    override fun convert(helper: BaseViewHolder, item: CategorySection) {
        helper.setText(R.id.post_title,item.category.name)
        helper.setText(R.id.post_count, " " + item.category.id)
    }

}