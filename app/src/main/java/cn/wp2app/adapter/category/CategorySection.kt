package cn.wp2app.adapter.category

import cn.wp2app.model.bean.category.Category
import com.chad.library.adapter.base.entity.JSectionEntity

class CategorySection (isHeader:Boolean, obj: Category): JSectionEntity() {
    val category = obj

    override val isHeader: Boolean = isHeader
}