package com.maku.pombe.ui.components.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maku.logging.Logger
import com.maku.pombe.category.DrinkCategoryViewState

@Composable
fun PombeCategoryChip(
    setSelected: (String, Boolean) -> Unit,
    selected: Boolean,
    category: String,
    doSearch: () -> Unit,
){
    val color = MaterialTheme.colors
    Surface(
        modifier = Modifier.padding(end = 8.dp)
            .clip(RoundedCornerShape(20.dp)),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.small,
        color = if (selected) Color(0xFFFFA500) else color.primary,
    ) {
        Box(
            modifier = Modifier
                .toggleable(
                    value = selected,
                    onValueChange = {
                        setSelected(category, true)
                        doSearch()
                    }
                )
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                ),
                color = if (selected) color.primary else Color.White.copy(alpha = ContentAlpha.medium)
            )
        }
    }
}
