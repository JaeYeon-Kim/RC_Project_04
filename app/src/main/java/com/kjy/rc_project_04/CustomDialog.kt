package com.kjy.rc_project_04

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kjy.rc_project_04.databinding.DialogCustomBinding

class CustomDialog(context: AppCompatActivity) {

    val binding by lazy {
        DialogCustomBinding.inflate(context.layoutInflater)
    }

    private val cContext = context

    private val dialog = Dialog(context)        // 컨텍스트의 다이얼로그 변수로 지정

    fun showDialog() {
        // 만든 커스텀다이얼로그 레이아웃 리소스 가져옴
        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val editTitle = binding.editTitle
        val editPrice = binding.editPrice
        val okButton = binding.okButton
        val cancelButton = binding.cancelButton

        // 추가하기 버튼 클릭시 이벤트
        okButton.setOnClickListener {
            onClickListener.onClick(editTitle.text.toString(), editPrice.text.toString())
            dialog.dismiss()
        }

        // 취소하기 버튼 클릭시 이벤트
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        // 다이얼로그 띄워줌
        dialog.show()
    }

    // 버튼 이벤트를 위해 인터페이스를 사용해 함수 선언
    interface ButtonClickListener {
        fun onClick(titleText: String, priceText: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickListener(listener: ButtonClickListener) {
        onClickListener = listener
    }
}