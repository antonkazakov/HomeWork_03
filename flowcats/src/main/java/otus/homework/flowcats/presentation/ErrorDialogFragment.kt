package otus.homework.flowcats.presentation

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import otus.homework.flowcats.R

/**
 * Фрагмент диалога об ошибке
 */
class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog =
        AlertDialog.Builder(requireContext()).setTitle(R.string.error_dialog_title)
            .setMessage(requireArguments().getString(MESSAGE_KEY))
            .setNeutralButton(R.string.error_dialog_neutral_button) { _, _ -> dismiss() }.show()

    companion object {

        private const val MESSAGE_KEY = "MESSAGE_KEY"

        /** Создать экземпляр [ErrorDialogFragment] с сообщением об ошибке [message] */
        fun newInstance(message: String) = ErrorDialogFragment().apply {
            arguments = bundleOf(MESSAGE_KEY to message)
        }
    }
}