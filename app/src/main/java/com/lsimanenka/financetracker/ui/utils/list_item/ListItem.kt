package com.lsimanenka.financetracker.ui.utils.list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.theme.LightColors
//import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel
import com.lsimanenka.financetracker.domain.viewmodel.AccountViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent

private const val paddingInListItem: Int = 8

@Composable
private fun GeneralListItem(
    height: Int,
    lead: String? = null,
    content: String,
    comment: String? = null,
    money: String? = null,
    trail: @Composable (() -> Unit)? = null,
    color: Color = Color.White,
    trailContent: String? = null,
    onClick: (() -> Unit)? = null,
    //viewModel: AccountViewModel = hiltViewModel(),
    modifier: Modifier? = null,
) {

    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: AccountViewModel = viewModel(factory = factory)
    val currencyFlow = remember { viewModel.currency }
    val currency by currencyFlow.collectAsState()
    val uiState by viewModel.state
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .background(color = color)
            .height(height.dp)
            .border(1.dp, Color(0xFFCAC4D0))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        if (lead != null) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = LightColors.secondary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = lead,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(paddingInListItem.dp))
        }

        if (comment == null) {
            Text(content, fontSize = 16.sp)
        } else {
            Column {
                Text(content, fontSize = 16.sp)
                Text(comment, fontSize = 14.sp, color = Color(0xFF49454F))
            }
        }

        Spacer(Modifier.weight(1f))

        if (money != null && trailContent != null) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$money" + currency,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = paddingInListItem.dp),
                    textAlign = TextAlign.End
                )
                Text(
                    text = trailContent,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = paddingInListItem.dp),
                    textAlign = TextAlign.End
                )

            }
        } else if (money != null) {
            Text(
                text = "$money" + currency,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = paddingInListItem.dp),

                )
        } else if (trailContent != null) {
            Text(
                text = trailContent,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = paddingInListItem.dp),

                )
        }

        if (trail != null) {
            trail()

        }
    }
}

@Composable
fun HeaderListItem(
    lead: String? = null,
    content: String,
    money: String? = null,
    trailContent: String? = null,
    trail: @Composable (() -> Unit)? = null,
    color: Color = LightColors.secondary,
    onClick: (() -> Unit)? = null,
    modifier: Modifier? = null
) {
    GeneralListItem(
        height = 56,
        lead = lead,
        content = content,
        money = money,
        trailContent = trailContent,
        trail = trail,
        color = color,
        onClick = onClick
    )
}

@Composable
fun ListItem(
    lead: String? = null,
    content: String,
    comment: String? = null,
    money: String? = null,
    trailContent: String? = null,
    trail: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier? = null
) {
    GeneralListItem(
        height = 72,
        lead = lead,
        content = content,
        money = money,
        trailContent = trailContent,
        trail = trail,
        comment = comment,
        onClick = onClick,
        modifier = modifier
    )
}
