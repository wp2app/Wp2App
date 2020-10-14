package cn.wp2app.model.bean.category

import com.chad.library.adapter.base.entity.JSectionEntity

data class Category (
    val _links: Links,
    val count: Int,
    val description: String,
    val id: Int,
    val link: String,
    val meta: List<Int>,
    val name: String,
    val parent: Int,
    val slug: String,
    val taxonomy: String
)