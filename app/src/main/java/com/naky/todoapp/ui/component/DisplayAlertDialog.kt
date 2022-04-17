package com.naky.todoapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.naky.todoapp.R

@Composable
fun DisplayAlertDialog(
    title : String,
    message : String,
    openDialog : Boolean,
    closeDialog : () -> Unit,
    onYesClicked : ()->Unit
){
    if(openDialog){
        AlertDialog(
            onDismissRequest = { closeDialog()},
            title = {
                    Text(
                        text = title,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.Bold
                    )
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            closeDialog()
                            onYesClicked()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.yes))
                    }

                    OutlinedButton(
                        onClick = {
                            closeDialog()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.no))
                    }
                }

            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },

        )
    }

}