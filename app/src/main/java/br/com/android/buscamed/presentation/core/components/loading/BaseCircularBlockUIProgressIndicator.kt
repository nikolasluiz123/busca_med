package br.com.android.buscamed.presentation.core.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun BaseCircularBlockUIProgressIndicator(
    show: Boolean,
    label: String,
    modifier: Modifier = Modifier
) {
    if (show) {
        Popup(alignment = Alignment.Center) {
            ConstraintLayout(modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .pointerInput(Unit) { }
                .zIndex(999999F)) {

                val (loadingRef, textRef) = createRefs()
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.constrainAs(loadingRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                )
                Text(
                    label,
                    modifier = Modifier.constrainAs(textRef) {
                        start.linkTo(loadingRef.start)
                        top.linkTo(loadingRef.bottom, margin = 8.dp)
                        end.linkTo(loadingRef.end)
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

            }
        }
    }
}