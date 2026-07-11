package com.example.navhigh.ui.email

import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.ui.theme.NavHighTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.DialogBackground
import com.example.navhigh.ui.theme.DialogMessage
import com.example.navhigh.ui.theme.DialogNegative
import com.example.navhigh.ui.theme.DialogPositive
import com.example.navhigh.ui.theme.DialogTitle

@Composable
fun AlreadyHaveAccountDialog(
    onDismiss: () -> Unit,
    onContinue: () -> Unit,
    onLogin: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimensions.DialogCornerRadius),
            colors = CardDefaults.cardColors(
                containerColor = DialogBackground
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = AppDimensions.DialogElevation
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.DialogPadding)
            ) {

                Text(
                    text = "Already have an account?",
                    color = DialogTitle,
                    fontSize = AppTypography.DialogTitleSize,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(
                        AppDimensions.DialogTitleBottomSpace
                    )
                )



                Spacer(
                    modifier = Modifier.height(
                        AppDimensions.DialogButtonTopSpace
                    )
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "LOG IN",
                        color = DialogPositive,
                        fontSize = AppTypography.DialogButtonSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onLogin()
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(
                            AppDimensions.DialogLoginToContinueSpacing
                        )
                    )

                    Text(
                        text = "CONTINUE CREATING ACCOUNT",
                        color = DialogNegative,
                        fontSize = AppTypography.DialogButtonSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onContinue()
                        }
                    )

                }

                    Spacer(
                        modifier = Modifier.width(
                            AppDimensions.DialogButtonSpacing
                        )
                    )


                }
            }
        }
    }



@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun AlreadyHaveAccountDialogPreview() {

    NavHighTheme {

        AlreadyHaveAccountDialog(

            onDismiss = {},

            onContinue = {},

            onLogin = {}

        )

    }

}