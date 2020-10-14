package cn.wp2app.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

open class BaseDataBindingHolder <BD : ViewDataBinding>(view: View) : BaseViewHolder(view) {

    val dataBinding = DataBindingUtil.bind<BD>(view)
}