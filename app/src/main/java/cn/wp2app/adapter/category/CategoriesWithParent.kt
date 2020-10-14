package cn.wp2app.adapter.category

import cn.wp2app.model.bean.category.Category

data class CategoriesWithParent (val parent:Int,
                                val categories:MutableList<CategorySection>)