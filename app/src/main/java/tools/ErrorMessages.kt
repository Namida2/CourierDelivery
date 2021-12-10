package tools

import com.example.courierdelivery.R
import entities.ErrorMessage

object ErrorMessages {
    val emptyFieldMessage = ErrorMessage(
        R.string.emptyFieldTitle,
        R.string.emptyFieldMessage
    )

    val invalidNumberMessage = ErrorMessage(
        R.string.invalidNumberTitle,
        R.string.invalidNumberMessage
    )

    val invalidConfirmationCodeMessage = ErrorMessage(
        R.string.invalidConfirmationCodeTitle,
        R.string.invalidConfirmationCodeMessage
    )

    val numberNotSentMessage = ErrorMessage(
        R.string.numberNotSentTitle,
        R.string.numberNotSentMessage
    )

    val quotaHasBeenExceededMessage = ErrorMessage(
        R.string.numberNotSentTitle,
        R.string.numberNotSentMessage
    )
}