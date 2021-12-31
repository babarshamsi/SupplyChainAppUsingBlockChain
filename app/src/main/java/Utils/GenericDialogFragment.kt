package Utils

import Utils.UiUtils.Companion.isValidDrawableRes
import Utils.UiUtils.Companion.isValidLayoutRes
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewStub
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.android.supplychainapp.R
import kotlinx.android.synthetic.main.generic_dialog_fragment_content.*

class GenericDialogFragment: DialogFragment() {

    @DrawableRes
    var iconResource = 0

    @LayoutRes
    var extraViewResource = 0

    @LayoutRes
    var secondaryExtraViewResource = 0

    @StringRes
    var descriptionFontStyle = 0

    @StringRes
    var titleFontStyle = 0

    @StringRes
    var descriptionSecondaryFontStyle = 0
    var dialogTitle: CharSequence? = null
    var dialogDescription: CharSequence? = null
    var dialogSecondaryDescription: CharSequence? = null
    var primaryButtonTitle: String? = null
    var primaryButtonClickListener: View.OnClickListener? = null
    var secondaryButtonTitle: String? = null
    var secondaryButtonClickListener: View.OnClickListener? = null
    var onCancelListener: DialogInterface.OnCancelListener? = null
    var onDismissListener: DialogInterface.OnDismissListener? = null

    var fullScreen = false
    var cancelableDialog = true
    var hideTitle = false
    protected var rootView: View? = null
    var dismissable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayout(), container, false)
        setTransparentBackground()
        initView()
        applyCancelableFlag()
        return rootView
    }

    private fun setTransparentBackground() {
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun getLayout(): Int {
        return R.layout.generic_dialog_fragment
    }

    private fun applyCancelableFlag() {
        isCancelable = cancelableDialog
        dialog!!.setCancelable(cancelableDialog)
        dialog!!.setCanceledOnTouchOutside(cancelableDialog)
    }

    private fun initView() {
        if (isValidDrawableRes(context, iconResource)) {
            dialogFragmentIcon?.setImageResource(iconResource)
            dialogFragmentIcon?.setVisibility(View.VISIBLE)
        }
        if (isValidLayoutRes(context, extraViewResource)) {
            dialogFragmentExtraViewStub?.setLayoutResource(extraViewResource)
            dialogFragmentExtraViewStub?.setOnInflateListener(ViewStub.OnInflateListener { stub: ViewStub?, inflated: View? ->
                this.viewStubOnInflate(
                    stub,
                    inflated
                )
            })
            dialogFragmentExtraViewStub?.inflate()
        }
        if (isValidLayoutRes(context, secondaryExtraViewResource)) {
            dialogFragmentSecondaryExtraViewStub?.setLayoutResource(secondaryExtraViewResource)
            dialogFragmentSecondaryExtraViewStub?.setOnInflateListener(ViewStub.OnInflateListener { stub: ViewStub?, inflated: View? ->
                this.secondaryViewStubOnInflate(
                    stub,
                    inflated
                )
            })
            dialogFragmentSecondaryExtraViewStub?.inflate()
        }
        if (!TextUtils.isEmpty(dialogTitle)) {
            if (titleFontStyle != 0) {
                dialogFragmentTitle?.setTypeface(
                    Typeface.createFromAsset(
                        context?.assets,
                        resources.getString(titleFontStyle)
                    )
                )
            }
//            dialogFragmentTitle?.setText(replaceEmojiHelper.replaceEmoji(dialogTitle.toString()))
            dialogFragmentTitle?.setText((dialogTitle.toString()))
            dialogFragmentTitle?.setVisibility(View.VISIBLE)
        }
        if (!TextUtils.isEmpty(dialogDescription)) {
            if (descriptionFontStyle != 0) {
                dialogFragmentDescription?.setTypeface(
                    Typeface.createFromAsset(
                        context?.assets,
                        resources.getString(descriptionFontStyle)
                    )
                )
            }
            dialogFragmentDescription?.setText(dialogDescription.toString())
            dialogFragmentDescription?.setVisibility(View.VISIBLE)
        }
        if (!TextUtils.isEmpty(dialogSecondaryDescription)) {
            if (descriptionSecondaryFontStyle != 0) {
                dialogFragmentSecondaryDescription?.setTypeface(
                    Typeface.createFromAsset(
                        context?.assets,
                        resources.getString(descriptionSecondaryFontStyle)
                    )
                )
            }
            dialogFragmentSecondaryDescription.setText(dialogSecondaryDescription.toString())
            dialogFragmentSecondaryDescription?.setVisibility(View.VISIBLE)
        }
        if (!TextUtils.isEmpty(primaryButtonTitle)) {
            dialogFragmentPrimaryButton?.setText(primaryButtonTitle)
            dialogFragmentPrimaryButton?.setVisibility(View.VISIBLE)
        }
        dialogFragmentPrimaryButton?.setOnClickListener(
            AutoDismissOnClickListener(primaryButtonClickListener)
        )
        if (!TextUtils.isEmpty(secondaryButtonTitle)) {
            dialogFragmentSecondaryButton?.setText(secondaryButtonTitle)
            dialogFragmentSecondaryButton?.setVisibility(View.VISIBLE)
        }
        dialogFragmentSecondaryButton?.setOnClickListener(
            AutoDismissOnClickListener(
                secondaryButtonClickListener
            )
        )
        if (hideTitle) {
            dialogFragmentTitle?.setVisibility(View.GONE)
            val params = dialogFragmentIcon?.getLayoutParams() as MarginLayoutParams
            params.setMargins(0, 0, 0, 0)
        }
    }

    protected fun viewStubOnInflate(stub: ViewStub?, inflated: View?) {
        // nothing to do, should be overridden in sub class
    }

    protected fun secondaryViewStubOnInflate(stub: ViewStub?, inflated: View?) {
        // nothing to do, should be overridden in sub class
    }


    companion object {
        fun showAddMoreInfoErrorDialog(
            activity: AppCompatActivity,
            primaryButtonClickListener: View.OnClickListener,
            secondaryButtonClickListener: View.OnClickListener
        ): GenericDialogFragment? {
            val builder: GenericDialogFragment.Builder =
                GenericDialogFragment.Builder()
                    .setIconResource(
                        R.drawable.ic_error
                    ).setDialogTitle(activity.getString(R.string.some_info_still_left))
                    .setDialogDescription(
                        activity.getString(R.string.some_info_still_left_info)
                    )
                    .setPrimaryButtonTitle(
                        activity.getString(R.string.yes)
                    )
                    .setSecondaryButtonTitle(
                        activity.getString(R.string.no)
                    )
                    .setPrimaryButtonClickListener(primaryButtonClickListener)
                    .setTitleFontStyle(R.string.swansea_bold)
                    .setDescriptionFontStyle(R.string.swansea)
                    .setSecondaryButtonClickListener(secondaryButtonClickListener)
            val genericDialogFragment: GenericDialogFragment = builder.build()
            genericDialogFragment.show(
                activity.getSupportFragmentManager(),
                "incompleteProductInfoErrorDialog"
            )
            return genericDialogFragment
        }
    }

    inner class AutoDismissOnClickListener(listener: View.OnClickListener?) :
        View.OnClickListener {
        private val listener: View.OnClickListener?
        override fun onClick(v: View) {
            if (dialog != null && dialog!!.isShowing() && dismissable) {
                dismiss()
            }
            listener?.onClick(v)
        }

        init {
            this.listener = listener
        }
    }

     class Builder {

        @DrawableRes
        private var iconResource = 0

        @LayoutRes
        private var extraViewResource = 0

        @LayoutRes
        private var secondaryExtraViewResource = 0

        @StringRes
        var descriptionFontStyle = 0

        @StringRes
        var titleFontStyle = 0

        @StringRes
        var descriptionSecondaryFontStyle = 0
        public var dialogTitle: CharSequence? = null
        private var dialogDescription: CharSequence? = null
        private var dialogSecondaryDescription: CharSequence? = null
        private var primaryButtonTitle: String? = null
        private var primaryButtonClickListener: View.OnClickListener? = null
        private var secondaryButtonTitle: String? = null
        private var secondaryButtonClickListener: View.OnClickListener? = null
        private var fullScreen = false
        private var cancelable = true
        private var hideTitle = false
        private var dismissable = true
        private var onCancelListener: DialogInterface.OnCancelListener? = null
        private var onDismissListener: DialogInterface.OnDismissListener? = null
        fun setDialogTitle(dialogTitle: CharSequence?): Builder {
            this.dialogTitle = dialogTitle
            return this
        }

        fun setDialogDescription(dialogDescription: CharSequence?): Builder {
            this.dialogDescription = dialogDescription
            return this
        }

        fun setDialogSecondaryDescription(dialogSecondaryDescription: CharSequence?): Builder {
            this.dialogSecondaryDescription = dialogSecondaryDescription
            return this
        }

        fun setIconResource(@DrawableRes iconResource: Int): Builder {
            this.iconResource = iconResource
            return this
        }

        fun setPrimaryButtonTitle(primaryButtonTitle: String?): Builder {
            this.primaryButtonTitle = primaryButtonTitle
            return this
        }

        fun setPrimaryButtonClickListener(
            primaryButtonClickListener: View.OnClickListener?
        ): Builder {
            this.primaryButtonClickListener = primaryButtonClickListener
            return this
        }

        fun setSecondaryButtonTitle(secondaryButtonTitle: String?): Builder {
            this.secondaryButtonTitle = secondaryButtonTitle
            return this
        }

        fun setSecondaryButtonClickListener(
            secondaryButtonClickListener: View.OnClickListener?
        ): Builder {
            this.secondaryButtonClickListener = secondaryButtonClickListener
            return this
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): Builder {
            this.onCancelListener = onCancelListener
            return this
        }

        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?): Builder {
            this.onDismissListener = onDismissListener
            return this
        }

        fun setExtraViewResource(extraViewResource: Int): Builder {
            this.extraViewResource = extraViewResource
            return this
        }

        fun setSecondaryExtraViewResource(secondaryExtraViewResource: Int): Builder {
            this.secondaryExtraViewResource = secondaryExtraViewResource
            return this
        }

        fun setFullScreen(fullScreen: Boolean): Builder {
            this.fullScreen = fullScreen
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun setHideTitle(hideTitle: Boolean): Builder {
            this.hideTitle = hideTitle
            return this
        }

        fun setDismissable(dismissable: Boolean): Builder {
            this.dismissable = dismissable
            return this
        }

        fun setDescriptionFontStyle(descriptionFontStyle: Int): Builder {
            this.descriptionFontStyle = descriptionFontStyle
            return this
        }

        fun setSecondaryDescriptionFontStyle(descriptionSecondaryFontStyle: Int): Builder {
            this.descriptionSecondaryFontStyle = descriptionSecondaryFontStyle
            return this
        }

        fun setTitleFontStyle(titleFontStyle: Int): Builder {
            this.titleFontStyle = titleFontStyle
            return this
        }

        fun build(): GenericDialogFragment {
            return newInstance(this, GenericDialogFragment::class.java)!!
        }

        fun <T : GenericDialogFragment?> build(clazz: Class<T>): T? {
            return newInstance(this, clazz)
        }

        companion object {
            private fun <T : GenericDialogFragment?> newInstance(
                builder: Builder,
                clazz: Class<T>
            ): T? {
                val UI_TAG = "UI"
                var genericDialogFragment: T? = null
                try {
                    genericDialogFragment = clazz.newInstance()
                } catch (e: Exception) {
                    Log.e(UI_TAG, "Failed to create GenericDialogFragment")
                }
                if (genericDialogFragment == null) {
                    return null
                }
                genericDialogFragment.iconResource = builder.iconResource
                genericDialogFragment.extraViewResource = builder.extraViewResource
                genericDialogFragment.secondaryExtraViewResource =
                    builder.secondaryExtraViewResource
                genericDialogFragment.dialogTitle = builder.dialogTitle
                genericDialogFragment.dialogDescription = builder.dialogDescription
                genericDialogFragment.dialogSecondaryDescription =
                    builder.dialogSecondaryDescription
                genericDialogFragment.primaryButtonTitle = builder.primaryButtonTitle
                genericDialogFragment.primaryButtonClickListener =
                    builder.primaryButtonClickListener
                genericDialogFragment.secondaryButtonTitle = builder.secondaryButtonTitle
                genericDialogFragment.secondaryButtonClickListener =
                    builder.secondaryButtonClickListener
                genericDialogFragment.onCancelListener = builder.onCancelListener
                genericDialogFragment.fullScreen = builder.fullScreen
                genericDialogFragment.cancelableDialog = builder.cancelable
                genericDialogFragment.hideTitle = builder.hideTitle
                genericDialogFragment.onDismissListener = builder.onDismissListener
                genericDialogFragment.dismissable = builder.dismissable
                genericDialogFragment.descriptionFontStyle = builder.descriptionFontStyle
                genericDialogFragment.descriptionSecondaryFontStyle =
                    builder.descriptionSecondaryFontStyle
                genericDialogFragment.titleFontStyle = builder.titleFontStyle
                return genericDialogFragment
            }
        }
    }
}