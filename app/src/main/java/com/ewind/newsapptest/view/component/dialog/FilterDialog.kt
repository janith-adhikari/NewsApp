package com.ewind.newsapptest.view.component.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.ewind.newsapptest.R
import com.ewind.newsapptest.databinding.DialogFilterBinding
import com.ewind.newsapptest.util.Constant
import com.ewind.newsapptest.util.ext.getCompatColorState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

inline fun Activity.showFilter(
    preCountry: String,
    preLang: String,
    crossinline onApply: (country: String, lang: String) -> Unit
) {
    val dialogFragment = FilterDialog.newInstance(preCountry, preLang)

    dialogFragment.onApply { country, lang ->
        onApply.invoke(country, lang)
    }

    val fragmentTransaction = (this as AppCompatActivity).supportFragmentManager.beginTransaction()
    fragmentTransaction.let {
        val prefragment = this.supportFragmentManager.findFragmentByTag(FilterDialog.TAG)
        prefragment?.let { frag -> it.remove(frag) }
        dialogFragment.show(it, FilterDialog.TAG)
    }
}

class FilterDialog(var country: String, var lang: String) : BottomSheetDialogFragment(),
    View.OnClickListener {

    private var lastCountryCheckedId: Int = View.NO_ID
    private var lastLangCheckedId: Int = View.NO_ID
    private var mBinding: DialogFilterBinding? = null
    /* private var country = "us"
     private var lang = "en"*/

    var onApply: ((country: String, lang: String) -> Unit)? = null

    fun onApply(apply: (country: String, lang: String) -> Unit) {
        this.onApply = apply
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val from = BottomSheetBehavior.from(bottomSheet)
                from.skipCollapsed = true
                from.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogFilterBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Constant.LANGUAGE_ARRAY.forEach {
            mBinding?.cgLang?.setChips(it)
        }
        Constant.COUNTRY_ARRAY.forEach {
            mBinding?.cgCountry?.setChips(it)
        }

        mBinding?.cgCountry?.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == View.NO_ID) {
                // User tried to uncheck, make sure to keep the chip checked
                group.check(lastCountryCheckedId)
                return@setOnCheckedChangeListener
            }
            lastCountryCheckedId = checkedId
            country = group.findViewById<Chip>(checkedId).tag.toString()
        }

        mBinding?.cgLang?.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == View.NO_ID) {
                // User tried to uncheck, make sure to keep the chip checked
                group.check(lastLangCheckedId)
                return@setOnCheckedChangeListener
            }
            lastLangCheckedId = checkedId
            lang = group.findViewById<Chip>(checkedId).tag.toString()
        }

        mBinding?.btnApply?.setOnClickListener {
            onApply?.invoke(country, lang)
            dismiss()
        }

        val landId = mBinding?.cgLang?.findViewWithTag<Chip>(lang)?.id
        val countryId = mBinding?.cgCountry?.findViewWithTag<Chip>(country)?.id
        landId?.let { mBinding?.cgLang?.check(it) }
        countryId?.let { mBinding?.cgCountry?.check(it) }
    }

    private fun ChipGroup.setChips(label: String) {
        val chip = Chip(requireContext()).apply {
            this.text = label
            isCheckedIconVisible = false
            isCheckable = true
            tag = label
            chipBackgroundColor = requireContext().getCompatColorState(R.color.state_chip)
            setOnClickListener(this@FilterDialog)
        }
        this.addView(chip)
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        const val TAG = "filter_dialog"

        fun newInstance(country: String, lang: String): FilterDialog {
            val fragment = FilterDialog(country, lang)
            return fragment
        }
    }

    override fun onClick(v: View?) {
        if (v is Chip) {
            val tag = v.tag
        }
    }
}
