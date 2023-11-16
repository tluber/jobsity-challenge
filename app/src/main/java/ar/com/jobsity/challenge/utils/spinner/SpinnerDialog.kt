package ar.com.jobsity.challenge.utils.spinner

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.jobsity.challenge.R
import ar.com.jobsity.challenge.databinding.DialogSpinnerAlertBinding

class SpinnerDialog<T>(context: Context) : Dialog(context, R.style.DialogTheme) {

    private var param = SpinnerParam<T>()
    private var adapter: SpinnerDialogAdapter<T>? = null
    private lateinit var binding: DialogSpinnerAlertBinding

    val selectedItems: List<T>
        get() {
            return adapter?.selectedItems ?: listOf()
        }

    private val onClickListener = fun(position: Int) {
        if (param.dismissAfterSelect) {
            dismiss()
        }
    }

    init {
        val windowLayoutParams = WindowManager.LayoutParams()
        windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        windowLayoutParams.dimAmount = 0.3f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSpinnerAlertBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {

        adapter = SpinnerDialogAdapter(
            param.list ?: listOf(),
            param.multipleSelection,
            param.maxSelection,
            param.style,
            onClickListener
        )
        adapter?.spinnerDataSource = param.spinnerDataSource

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        setCancelable(param.cancelable)
        this.setOnCancelListener(param.onCancelListener)
        this.setOnDismissListener(param.onDismissListener)

        if (param.title != null) {
            binding.titleTextView.text = param.title
        }

        if (param.positiveButtonText != null) {
            binding.positiveButton.visibility = View.VISIBLE
            binding.positiveButton.text = param.positiveButtonText
        } else {
            binding.positiveButton.visibility = View.GONE
        }

        if (param.positiveButtonListener != null) {
            binding.positiveButton.setOnClickListener {
                param.positiveButtonListener?.onClick(this, DialogInterface.BUTTON_POSITIVE)
                this.dismiss()
            }
        }

        if (param.negativeButtonText != null) {
            binding.negativeButton.visibility = View.VISIBLE
            binding.negativeButton.text = param.negativeButtonText
        } else {
            binding.negativeButton.visibility = View.GONE
        }

        if (param.negativeButtonListener != null) {
            binding.negativeButton.setOnClickListener {
                param.negativeButtonListener?.onClick(this, DialogInterface.BUTTON_NEGATIVE)
                this.dismiss()
            }
        }

        if (binding.negativeButton.visibility == View.VISIBLE && binding.positiveButton.visibility == View.VISIBLE) {
            binding.buttonMargin.visibility = View.VISIBLE
        } else if (binding.negativeButton.visibility == View.GONE && binding.positiveButton.visibility == View.GONE) {
            binding.buttonMargin.visibility = View.GONE
            binding.buttons.visibility = View.GONE
        } else {
            binding.buttonMargin.visibility = View.GONE
        }

        if (param.showBackButton) {
            binding.backButton.visibility = View.VISIBLE
            binding.backButton.setOnClickListener {
                this.dismiss()
            }
        } else {
            binding.backButton.visibility = View.INVISIBLE
        }

    }

    private class SpinnerParam<T> {
        var title: CharSequence? = null
        var list: List<Triple<String, Boolean, T>>? = null
        var positiveButtonText: CharSequence? = null
        var negativeButtonText: CharSequence? = null
        var positiveButtonListener: DialogInterface.OnClickListener? = null
        var negativeButtonListener: DialogInterface.OnClickListener? = null
        var onCancelListener: DialogInterface.OnCancelListener? = null
        var onDismissListener: DialogInterface.OnDismissListener? = null
        var showBackButton: Boolean = true
        var cancelable: Boolean = true
        var multipleSelection: Boolean = false
        var dismissAfterSelect: Boolean = false
        var style: SpinnerStyle = SpinnerStyle.DEFAULT
        var maxSelection: Int? = null
        var spinnerDataSource: SpinnerDataSource? = null
    }

    @Suppress("unused")
    class Builder<T>(private var context: Context) {
        private var param =
            SpinnerParam<T>()

        fun setSpinnerDataSource(spinnerDataSource: SpinnerDataSource): Builder<T> =
            apply { param.spinnerDataSource = spinnerDataSource }

        fun setTitle(@StringRes textId: Int): Builder<T> =
            apply { param.title = context.getText(textId) }

        fun setShowBackButton(showBackButton: Boolean): Builder<T> =
            apply { param.showBackButton = showBackButton }

        fun setPositiveButton(
            @StringRes textId: Int,
            listener: DialogInterface.OnClickListener?
        ): Builder<T> {
            param.positiveButtonText = context.getText(textId)
            param.positiveButtonListener = listener
            return this
        }

        fun setNegativeButton(
            @StringRes textId: Int,
            listener: DialogInterface.OnClickListener?
        ): Builder<T> {
            param.negativeButtonText = context.getText(textId)
            param.negativeButtonListener = listener
            return this
        }

        fun setOnCancelListener(listener: DialogInterface.OnCancelListener?): Builder<T> {
            param.onCancelListener = listener
            return this
        }

        fun setOnDismissListener(listener: DialogInterface.OnDismissListener?): Builder<T> {
            param.onDismissListener = listener
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder<T> =
            apply { param.cancelable = cancelable }

        fun setStyle(style: SpinnerStyle): Builder<T> =
            apply { param.style = style }

        fun setDismissAfterSelect(dismissAfterSelect: Boolean): Builder<T> =
            apply { param.dismissAfterSelect = dismissAfterSelect }

        fun multipleSelection(
            list: List<Triple<String, Boolean, T>>,
            max: Int? = null
        ): Builder<T> =
            apply {
                param.list = list
                param.multipleSelection = true
                param.maxSelection = max
            }

        fun singleSelection(list: List<Triple<String, Boolean, T>>): Builder<T> =
            apply {
                param.list = list
                param.multipleSelection = false
            }

        fun create(): SpinnerDialog<T> {
            val dialog = SpinnerDialog<T>(context)
            dialog.param = param
            return dialog
        }
    }
}