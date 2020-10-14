package cn.wp2app.model.bean.category

data class Links(
    val about: List<About>,
    val collection: List<Collection>,
    val curies: List<Cury>,
    val self: List<Self>,
    val wp_post_type: List<WpPostType>
)