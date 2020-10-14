package cn.wp2app.ui.activity

import cn.wp2app.R
import cn.wp2app.base.BaseActivity
import cn.wp2app.widget.HtmlGlideImageGetter
import kotlinx.android.synthetic.main.content_post.*
import kotlinx.android.synthetic.main.content_post.view.*
import kotlinx.android.synthetic.main.title_layout.*

class PostActivity : BaseActivity() {
    private lateinit var sContent: String
    private lateinit var sTitle : String
    companion object {
        const val TITLE = "post_title"
        const val CONTENT = "post_content"
    }

    override fun getLayoutResId() = R.layout.activity_post

    override fun initData() {

    }

    override fun initView() {
        sContent = intent?.extras?.getString(CONTENT) as String
        sTitle = intent?.extras?.getString(TITLE) as String

        toolbar_title.text = sTitle

        //desTitleView.setText("详细介绍");
        tv_content_detail.post(Runnable {

            tv_content_detail.textSize = 16F
            //if (!bSysFont) {
            //    htvContent.setTypeface(fontBody)
            //}
            val width: Int =
                tv_content_detail.getWidth() - tv_content_detail.getPaddingRight() - tv_content_detail.getPaddingLeft()

            tv_content_detail.setHtml(
               sContent, HtmlGlideImageGetter(tv_content_detail, width)
            )
        })

        toolbar_back.setOnClickListener { onBackPressed() }
    }
}
