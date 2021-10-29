package com.example.yassirtest.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.yassirtest.R

@Composable
public fun Toolbar() {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_logo_large),
                    contentDescription = "App logo"
                )
            }
            Icon(
                ImageBitmap.imageResource(id = R.mipmap.ic_launcher),
                modifier = Modifier.icon(),
                contentDescription = ""
            )
        }
    }
}

@Composable
@Preview("Toolbar preview")
private fun ToolbarPreview() {
    Toolbar()
}