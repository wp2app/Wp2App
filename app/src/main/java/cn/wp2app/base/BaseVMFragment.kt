package cn.wp2app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/*
 date: 2020.3.20
 author: wp2app
 function : base class of fragment for viewmodel
 */
abstract class BaseVMFragment<VM : BaseViewModel>(useDataBinding: Boolean = true) : Fragment() {

    private val bDataBinding:Boolean = useDataBinding
    protected lateinit var mBinding: ViewDataBinding
    protected lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (bDataBinding) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
            mBinding.root
        } else
            inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = initVM()
        if (bDataBinding) mBinding.lifecycleOwner = this
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun getLayoutResId(): Int
    abstract fun initVM(): VM
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}