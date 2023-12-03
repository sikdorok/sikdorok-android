package com.ddd.sikdorok.modify

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.ddd.sikdorok.shared.key.Keys
import org.joda.time.DateTime

class ModifyTimePicker : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
        requireActivity().supportFragmentManager.setFragmentResult(
            Keys.MODIFY_TIME_FRAGMENT,
            bundleOf(Keys.MODIFY_TIME_RESULT to PickerData.Time(String.format("%02d", hour), minute))
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val argument = requireArguments().getString(Keys.MODIFY_TIME)
        val date = DateTime.parse(argument)

        return TimePickerDialog(requireActivity(), this, date.hourOfDay, date.minuteOfDay, true)
    }

    companion object {
        fun getInstance(time: String) = ModifyTimePicker().apply {
            arguments = bundleOf(Keys.MODIFY_TIME to time)
        }
    }
}
