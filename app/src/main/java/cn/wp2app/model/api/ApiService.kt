package cn.wp2app.model.api

import cn.wp2app.config.Constant
import cn.wp2app.model.bean.PostInfo
import cn.wp2app.model.bean.category.Category
import cn.wp2app.model.bean.post.WPost
import cn.wp2app.model.bean.tag.WpTag
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //for test
    @GET("tags?hide_empty=true&per_page=1")
    suspend fun getTagsForTest() : List<WpTag>

    // use filter
    @GET("posts?fields=${Constant.POST_FILTER}")
    suspend fun getLastPostsFilter() : Response<List<PostInfo>>

    @GET("posts?fields=${Constant.POST_FILTER}")
    suspend fun getLastPostsAfterFilter(@Query("after")  date:String) : Response<List<PostInfo>>

    @GET("posts?fields=${Constant.POST_FILTER}")
    suspend fun getLastPostsBeforeFilter(@Query("before")  date:String) : Response<List<PostInfo>>

    //don't use filter
    @GET("posts")
    suspend fun getPosts() : List<WPost>

    @GET("posts")
    suspend fun getPostsAfter(@Query("after")  date:String) : List<WPost>

    @GET("posts")
    suspend fun getPostsBefore(@Query("before")  date:String) : List<WPost>

    @GET("posts")
    suspend fun getPosts(@Query("categories") id:Int) : List<WPost>

    @GET("posts")
    suspend fun getPostsAfter(@Query("categories") id:Int, @Query("after")  date:String) : List<WPost>

    @GET("posts")
    suspend fun getPostsBefore(@Query("categories") id:Int, @Query("before")  date:String) : List<WPost>

    //  search
    @GET("posts")
    suspend fun search(@Query("search")  keyWord:String) : List<WPost>

    //  search
    @GET("posts")
    suspend fun getPostByTag(@Query("tags") id:Int) : List<WPost>

    //
    @GET("categories")
    suspend fun getCategoriesByParent(@Query("parent") parent:Int ) : List<Category>
    //
    @GET("categories?hide_empty=true")
    suspend fun getCategoryByID(@Query("id") id:Int ) : List<Category>

    @GET("tags?hide_empty=true&per_page=100")
    suspend fun getTags() : List<WpTag>



}