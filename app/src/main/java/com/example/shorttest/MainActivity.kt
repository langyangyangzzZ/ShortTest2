package com.example.shorttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.shorttest.utlis.ShortCutUtil

class MainActivity : AppCompatActivity() {

    val shortUtil: ShortCutUtil by lazy {
        ShortCutUtil.getInstance().init(this)
    }
    val radioGroup by lazy {
        findViewById<RadioGroup>(R.id.radiogroup)
    }
    val blankFragment by lazy {
        BlankFragment()
    }

    val radioButton1 by lazy {
        findViewById<RadioButton>(R.id.radio1)
    }
    val radioButton2 by lazy {
        findViewById<RadioButton>(R.id.radio2)
    }
    val radioButton3 by lazy {
        findViewById<RadioButton>(R.id.radio3)
    }
    val radioButton4 by lazy {
        findViewById<RadioButton>(R.id.radio4)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 保存按钮
        val radiolist = listOf(radioButton1, radioButton2, radioButton3, radioButton4)


        //快捷方式打开
        initShort {
            val arg0 = intent?.extras?.getInt(ShortCutUtil.SHORTCUT_TAB_INDEX)
            if (arg0 != null) {
                val let = arg0.let {
                    radioGroup.getChildAt(it)
                }
                val bun = Bundle()
                bun["title"] = (let as RadioButton).text as String
                //传值
                blankFragment.setArguments(bun)

                radiolist[arg0].isChecked = true
            }
        }



        supportFragmentManager.beginTransaction().replace(R.id.framelayout, blankFragment).commit()


        radioGroup.setOnCheckedChangeListener { radio, it ->
            val radioButton = radio.findViewById<RadioButton>(radio.checkedRadioButtonId)

            blankFragment.setText(radioButton.text as String?)

            supportFragmentManager.beginTransaction().replace(R.id.framelayout, blankFragment)
                .commit()

        }


    }

    private fun initShort(arg0: () -> Unit) {
        arg0.invoke()
    }

    /**
     * 禁用快捷方式
     */
    fun onButtonClick3(view: View) {
        shortUtil.remove(2)
    }

    /**
     * 更改快捷方式
     */
    fun onButtonClick2(view: View) {
        shortUtil.updItem(
            this,
            ModifyActivity::class.java,
            2,
            R.drawable.ic_launcher_background,
            "修改快捷方式成功"
        )
    }

    /**
     * 添加快捷方式
     */
    fun onButtonClick1(view: View) {
        shortUtil.AddShortCut(
            this,
            MainActivity::class.java,
            MainActivity::class.java,
            2,
            R.drawable.ic_launcher_background
        )
    }
}

private operator fun Bundle.set(key: String, value: String) {
    this.putString(key, value)
}
