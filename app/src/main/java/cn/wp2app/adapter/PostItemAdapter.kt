package cn.wp2app.adapter

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.wp2app.R
import cn.wp2app.model.bean.PostInfo
import cn.wp2app.model.bean.post.WPost
import cn.wp2app.utils.getImgUrlFromString
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostItemAdapter: BaseQuickAdapter<WPost, BaseViewHolder>,
    LoadMoreModule {

    constructor():super(R.layout.item_postinfo_layout)


    override fun convert(helper: BaseViewHolder, item: WPost) {
        var img = getImgUrlFromString(item.content.rendered)
        if( !img.isNullOrEmpty() ) {
            Glide.with(this.context).load(img).into(helper.getView(R.id.iv_item_post_thumb) as ImageView)
        } else {
            var v =  helper.getView(R.id.iv_item_post_thumb) as ImageView
            v.visibility = View.GONE
        }

        helper.setText(R.id.post_item_title,item.title.rendered.toString())
        helper.getView<TextView>(R.id.post_item_content).text = Html.fromHtml(item.excerpt.rendered.toString())
    }
}