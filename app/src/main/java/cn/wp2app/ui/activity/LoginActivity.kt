package cn.wp2app.ui.activity

import android.provider.Telephony.Carriers.PASSWORD
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import cn.wp2app.R
import cn.wp2app.base.BaseActivity
import cn.wp2app.config.WebsiteInfo
import cn.wp2app.db.AppDataBase
import cn.wp2app.db.entities.User
import cn.wp2app.model.repository.UserRepository
import cn.wp2app.widget.LoadingDialog
import cn.wp2app.xmlrpc.WPConfig
import cn.wp2app.xmlrpc.WPConfigBuilder
import cn.wp2app.xmlrpc.WordPress
import cn.wp2app.xmlrpc.exception.WPClientException
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.title_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class LoginActivity : BaseActivity() {
    lateinit var btn : AppCompatButton
    lateinit var wp:WordPress
    lateinit var config: WPConfig
    lateinit var loading:LoadingDialog
    private val repository:UserRepository by inject()

    override fun getLayoutResId() = R.layout.activity_login;
    override fun initData() {
        toolbar_back.setOnClickListener { onBackPressed() }
    }

    override fun initView() {
       toolbar_title.text = "登  录"
    }

    fun onLogin(v: View) {
        login()
    }
    fun login() {
        var name = username_edit.text.toString()
        var pwd = password_edit.text.toString()

        if( name.isEmpty() || pwd.isEmpty() ) {
            Toast.makeText(this,"请填写用户名和密码", Toast.LENGTH_SHORT).show()
            return
        }

        val builder: LoadingDialog.Builder = LoadingDialog.Builder(this).setMessage("请稍等...").setCancelable(false)
        loading = builder.create()
        loading.show()

        GlobalScope.launch {
            try {
                config = WPConfigBuilder()
                    .username(name)
                    .password(pwd)
                    .xmlRpcUrl(WebsiteInfo.url)
                    .trustAll(true)
                    .connectTimeout(3 * 60 * 1000)
                    .readTimeout(3 * 60 * 1000)
                    .build()
                wp = WordPress(config)
                var author = wp.author

                // 保存到本地
                val user = User(author.displayName, pwd, name, author.userId)

                repository.addUser(user)
                loading.dismiss()
                finish()

            } catch (e: WPClientException) {
                Toast.makeText(this@LoginActivity, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                loading.dismiss()
            }
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.home ->{
                        onBackPressed()
                        return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
