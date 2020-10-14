package cn.wp2app.ui.fragment.about


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import cn.wp2app.R
import cn.wp2app.base.BaseVMFragment
import cn.wp2app.db.entities.User
import cn.wp2app.ui.activity.ChangeBaseUrlActivity
import cn.wp2app.ui.fragment.home.HomeViewModel
import cn.wp2app.widget.LoadingDialog
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.about_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AboutFragment : BaseVMFragment<AboutViewModel>(false) {
    lateinit var myUser: User
    lateinit var loading: LoadingDialog

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun getLayoutResId() = R.layout.about_fragment
    override fun initView() {
        tv_website_url.setOnClickListener {
            androidx.navigation.Navigation.findNavController(tv_website_url).navigate(R.id.action_mainFragment_to_baseUrlActivity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_about_me).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_meActivity)
        }
    }

    override fun initData() {
        login_layout.setOnClickListener{
            androidx.navigation.Navigation.findNavController(login_layout).navigate(R.id.action_mainFragment_to_loginActivity)
        }

        tv_setting.setOnClickListener {
            androidx.navigation.Navigation.findNavController(tv_setting).navigate(R.id.action_mainFragment_to_settingActivity)
        }

        tv_logout.setOnClickListener {
           context?.let { mViewModel.delUser(myUser) }
        }

        tv_website_url.setOnClickListener { showChangeUrl() }

        loading=LoadingDialog.Builder(context).setMessage("请稍等...").setCancelable(false).create()
        loading.setOnCancelListener { showChangeUrl() }
    }

    override fun initVM():AboutViewModel = getViewModel()

    override fun startObserve() {
        mViewModel.apply {
            user.observe(viewLifecycleOwner, Observer {
                if( it.isNullOrEmpty() ) {
                    login_title.text = "登录"
                    login_layout.isClickable = true
                    tv_logout.visibility = View.GONE
                } else {
                    login_title.text = it[0].name
                    login_layout.isClickable = false
                    tv_logout.visibility = View.VISIBLE

                    myUser = it[0] as User
                }
            })

            doDelete.observe(viewLifecycleOwner, Observer {
                if( it ) {
                    loading.show()
                } else {
                    loading.dismiss()
                }

                refresh()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        context?.let { mViewModel.getUser(it) }
    }

    /*
     用getActivity方法发起调用，只有父Activity的onActivityResult会调用，Fragment中的onActivityResult不会被调用
     直接发起startActivityForResult调用，当前的Fragment的onActivityResult，和父Activity的onActivityResult都会调用
     用getParentFragment发起调用，则只有父Activity和父Fragment的onActivityResult会被调用，当前的Fragment的onActivityResult不会被调用。
     */
    private fun showChangeUrl() {
        val intent = Intent(context, ChangeBaseUrlActivity::class.java)
        val bundle  = Bundle()
        intent.putExtras(bundle)
        parentFragment?.startActivityForResult(intent, 1101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /*
        when(resultCode) {
            AppCompatActivity.RESULT_OK->{
                val refresh: Boolean? = data?.extras?.getBoolean("do_refresh", false)
                refresh?.let { if(refresh) {

                }}
            }
            else -> {}
        }
        */
        super.onActivityResult(requestCode, resultCode, data)
    }
}

