package com.jetpack.floatingbuttonshowhide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.floatingbuttonshowhide.ui.theme.FloatingButtonShowHideTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FloatingButtonShowHideTheme {
                Surface(color = MaterialTheme.colors.background) {
                    FloatingButtonShowHide()
                }
            }
        }
    }
}

@Composable
fun FloatingButtonShowHide() {
    var index = 1
    val fabHeight = 72.dp
    val fabHeightPx = with(
        LocalDensity.current
    ) {
        fabHeight.roundToPx().toFloat()
    }
    val fabOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = fabOffsetHeightPx.value + delta
                fabOffsetHeightPx.value = newOffset.coerceIn(-fabHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Floating Button Show/Hide",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }   ,
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Navigation Menu")
                    }
                }
            )
        },

        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                LazyColumn {
                    items(count = 15) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(10.dp, 5.dp, 10.dp, 5.dp)
                                .background(Color.White),
                            elevation = 10.dp,
                            shape = RoundedCornerShape(5.dp)
                        ) {
                           Column(
                               modifier = Modifier.padding(10.dp)
                           ) {
                               Row(
                                   verticalAlignment = Alignment.CenterVertically
                               ) {
                                   Image(
                                       painter = painterResource(id = R.drawable.cat),
                                       contentDescription = "Item Image",
                                       contentScale = ContentScale.Crop,
                                       modifier = Modifier
                                           .size(60.dp)
                                           .clip(CircleShape)
                                   )

                                   Spacer(modifier = Modifier.padding(5.dp))

                                   Column {
                                       Text(
                                           text = "Cat ${ index++ }",
                                           color = Color.Black,
                                           fontSize = 16.sp,
                                           fontWeight = FontWeight.Bold
                                       )

                                       Spacer(modifier = Modifier.padding(2.dp))

                                       Text(
                                           text = "Lorem Ipsum is Simply ${ index++ }",
                                           color = Color.Gray,
                                           fontSize = 14.sp
                                       )
                                   }
                               }
                           }
                        }
                    }
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                shape = RoundedCornerShape(50),
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .offset {
                        IntOffset(x = 0, y = -fabOffsetHeightPx.value.roundToInt())
                    }
            ) {
                Icon(
                    Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = "Add Items"
                )
            }
        }
    )
}























