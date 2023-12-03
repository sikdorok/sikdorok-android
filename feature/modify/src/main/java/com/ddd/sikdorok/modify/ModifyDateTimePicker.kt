package com.ddd.sikdorok.modify

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.ddd.sikdorok.shared.key.Keys
import org.joda.time.DateTime

class ModifyDateTimePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog {
        val argument = requireArguments().getString(Keys.MODIFY_DATE).orEmpty()
        val date = DateTime.parse(argument)

        return DatePickerDialog(requireActivity(), this, date.year, date.monthOfYear - 1, date.dayOfMonth).apply {
            datePicker.minDate = DateTime.now().millis
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        requireActivity().supportFragmentManager.setFragmentResult(
            Keys.MODIFY_DATE_FRAGMENT, bundleOf(
                Keys.MODIFY_DATE_RESULT to PickerData.Date(year, month + 1, day)
            )
        )
    }


    companion object {
        fun getInstance(date: String) = ModifyDateTimePicker().apply {
            arguments = bundleOf(Keys.MODIFY_DATE to date)
        }
    }
}
