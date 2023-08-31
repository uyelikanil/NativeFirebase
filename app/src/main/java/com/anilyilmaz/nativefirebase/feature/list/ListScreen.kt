package com.anilyilmaz.nativefirebase.feature.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anilyilmaz.nativefirebase.R
import com.anilyilmaz.nativefirebase.core.designsystem.theme.NativeFirebaseTheme

@Composable
fun ListRoute(viewModel: ListViewModel = hiltViewModel(), onProfileClick: () -> Unit) {
    val offlineContents by viewModel.offlineContents.collectAsStateWithLifecycle(
        initialValue = ListUiState()
    )
    val contentState by viewModel.contentState.collectAsStateWithLifecycle()

    ListScreen(
        offlineContents,
        contentState,
        viewModel::updateContent,
        viewModel::addOfflineContent,
        viewModel::deleteOfflineContent,
        onProfileClick)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ListScreen(
    offlineContents: ListUiState,
    contentState: ListContentState,
    updateContent: (String) -> Unit,
    addOfflineContent: () -> Unit,
    deleteOfflineContent: (Int) -> Unit,
    onProfileClick: () -> Unit
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    val onDismissRequest = {
        openAlertDialog = false
        updateContent("")
    }
    val onConfirmation = {
        addOfflineContent()
        openAlertDialog = false
    }
    val onFloatingActionButtonClick = { openAlertDialog = true }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.list)
                    )
                },
                actions = {
                    TextButton(onClick = { onProfileClick() }) {
                        Text(
                            text = stringResource(R.string.profile),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.End
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onFloatingActionButtonClick)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 32.dp)
        ) {
            items(
                offlineContents.offlineContents.size,
                key = {offlineContents.offlineContents[it].id}
            ) {
                val currentItem by rememberUpdatedState(offlineContents.offlineContents[it].id)
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToStart) {
                            deleteOfflineContent(currentItem)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        DismissBackground(dismissState = dismissState)
                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 16.dp),
                            text = offlineContents.offlineContents[it].content)
                    }
                )
                Divider(thickness = 1.dp)
            }
        }

        if(openAlertDialog) {
            Dialog(contentState, updateContent, onDismissRequest, onConfirmation)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (dismissState.dismissDirection == DismissDirection.EndToStart) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "delete",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
private fun FloatingActionButton(onFloatingActionButtonClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onFloatingActionButtonClick() },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Button")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Dialog(
    contentState: ListContentState,
    updateContent: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.add_content),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp),
                )

                val maxChar = 40

                TextField(
                    value = contentState.content,
                    onValueChange = {
                        if (it.length <= maxChar) updateContent(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    maxLines = 3,
                    supportingText = {
                        Text(
                            text = "${contentState.content.length} / $maxChar",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                        )
                    },
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.dismiss))
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.add))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    NativeFirebaseTheme {
        ListScreen(
            ListUiState(), ListContentState(),
            {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenDialogPreview() {
    NativeFirebaseTheme {
        Dialog(
            ListContentState(),
            {}, {}, {}
        )
    }
}