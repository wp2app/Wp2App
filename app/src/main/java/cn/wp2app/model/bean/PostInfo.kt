package cn.wp2app.model.bean

data class PostInfo(val id:Int,
                    val slug:String,
                    val date:String,
                    val modified:String,
                    val link:String,
                    val categories: Any,
                    val title: PostTitleInfo,
                    val content: PostContentInfo,
                    val excerpt: PostExcerptInfo,
                    val extras_post_info:ExtrasPostInfo
)