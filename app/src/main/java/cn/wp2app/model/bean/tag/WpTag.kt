package cn.wp2app.model.bean.tag

data class WpTag(
    val _links: Links,
    val count: Int,
    val description: String,
    val id: Int,
    val link: String,
    val meta: List<Any>,
    val name: String,
    val slug: String,
    val taxonomy: String
)