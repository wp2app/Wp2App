package cn.wp2app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.wp2app.R
import cn.wp2app.base.BaseActivity
import kotlinx.android.synthetic.main.title_layout.*

class MeActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_me

    override fun initView() {
        toolbar_back.setOnClickListener { onBackPressed() }

        toolbar_title.text = "关于我"
    }

    override fun initData() {

    }
}
