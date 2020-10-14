package cn.wp2app.model.bean.post

data class Links(
    val about: List<About>,
    val author: List<Author>,
    val collection: List<Collection>,
    val curies: List<Cury>,
    val replies: List<Reply>,
    val self: List<Self>,
    val version_history: List<VersionHistory>,
    val wp_attachment: List<WpAttachment>,
    val wp_term: List<WpTerm>
)