package com.naky.todoapp.ui.page.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.naky.todoapp.R
import com.naky.todoapp.database.model.Priority
import com.naky.todoapp.ui.component.PriorityDropDown
import com.naky.todoapp.ui.theme.LARGE_PADDING
import com.naky.todoapp.ui.theme.MEDIUM_PADDING


@Composable
fun TaskContent(
    title : String,
    onTitleChange  : (String) -> Unit,
    description : String,
    onDescriptionChange : (String) -> Unit,
    priority: Priority,
    onPriorityChange : (Priority) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            modifier = modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.title))
            },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Divider(
            modifier = modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )

        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPriorityChange
        )

        OutlinedTextField(
            value = title,
            onValueChange = {
                onDescriptionChange(it)
            },
            modifier = modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.description))
            },
            textStyle = MaterialTheme.typography.body1,
        )

    }
}

@Composable
@Preview
fun PreviewTaskContent(
){
    TaskContent(
        title = "Ini Title",
        onTitleChange = {

        },
        description = "ini Description",
        onDescriptionChange = {

        },
        priority = Priority.HIGH,
        onPriorityChange = {

        })
}