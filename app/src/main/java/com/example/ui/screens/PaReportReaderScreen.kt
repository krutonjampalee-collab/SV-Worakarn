package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PaDocumentSection
import com.example.data.PortfolioRepository
import com.example.ui.theme.*

@Composable
fun PaReportReaderScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedSectionIndex by remember { mutableStateOf(0) }
    var showTocDrawer by remember { mutableStateOf(false) }
    val bookmarkedSections = remember { mutableStateListOf<Int>() }

    val sections: List<PaDocumentSection> = PortfolioRepository.paDocumentPages
    val currentSection = sections[selectedSectionIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Toolbar
        Surface(
            color = SlateDark,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "รายงานผลการพัฒนางานตามข้อตกลง (PA)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Text(
                            text = "ปีงบประมาณ พ.ศ. 2569 • เอกสาร 42 หน้า",
                            fontSize = 11.sp,
                            color = GoldAccent
                        )
                    }

                    IconButton(onClick = { showTocDrawer = !showTocDrawer }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "TOC",
                            tint = if (showTocDrawer) AmberGlow else Color.White
                        )
                    }
                }

                // In-document Search Input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    placeholder = { Text("ค้นหาข้อความในรายงาน PA...", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = CyanAccent,
                        unfocusedBorderColor = SlateBorder
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        // Table of Contents Drawer/Dropdown
        AnimatedVisibility(visible = showTocDrawer) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 280.dp),
                color = SlateCard,
                shadowElevation = 8.dp
            ) {
                LazyColumn(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    item {
                        Text(
                            text = "สารบัญเอกสารรายงาน PA (แตะเพื่อข้ามหน้า):",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmberGlow,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    itemsIndexed(sections) { index, sec ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedSectionIndex == index) RoyalBlue else Color.Transparent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedSectionIndex = index
                                    showTocDrawer = false
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "น.${sec.pageNumber}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedSectionIndex == index) AmberGlow else CyanAccent,
                                    modifier = Modifier.width(36.dp)
                                )
                                Text(
                                    text = sec.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    maxLines = 1,
                                    modifier = Modifier.weight(1f)
                                )
                                if (bookmarkedSections.contains(index)) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Bookmarked",
                                        tint = AmberGlow,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Content Area
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                // Page Indicator & Action bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RoyalBlue.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "หน้าที่ ${currentSection.pageNumber} จาก 42 หน้า",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = RoyalBlue,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = {
                                if (bookmarkedSections.contains(selectedSectionIndex)) {
                                    bookmarkedSections.remove(selectedSectionIndex)
                                    Toast.makeText(context, "ลบบุ๊กมาร์กแล้ว", Toast.LENGTH_SHORT).show()
                                } else {
                                    bookmarkedSections.add(selectedSectionIndex)
                                    Toast.makeText(context, "บันทึกบุ๊กมาร์กหน้านี้แล้ว", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (bookmarkedSections.contains(selectedSectionIndex)) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = GoldDark
                            )
                        }

                        IconButton(
                            onClick = {
                                clipboardManager.setText(
                                    AnnotatedString("${currentSection.title}\n\n${currentSection.content}")
                                )
                                Toast.makeText(context, "คัดลอกข้อความหน้านี้แล้ว", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = RoyalBlue)
                        }
                    }
                }
            }

            // Document Page Sheet Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = currentSection.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RoyalBlue,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = currentSection.content,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )

                        if (selectedSectionIndex == sections.size - 1) {
                            // Signature endorsement section
                            Spacer(modifier = Modifier.height(20.dp))
                            EndorsementBox()
                        }
                    }
                }
            }

            // Next / Previous Page Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (selectedSectionIndex > 0) selectedSectionIndex--
                        },
                        enabled = selectedSectionIndex > 0,
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
                    ) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("หน้าก่อนหน้า")
                    }

                    Button(
                        onClick = {
                            if (selectedSectionIndex < sections.size - 1) selectedSectionIndex++
                        },
                        enabled = selectedSectionIndex < sections.size - 1,
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
                    ) {
                        Text("หน้าถัดไป")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
fun EndorsementBox() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "การลงนามรับรองเอกสารรายงาน PA:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = RoyalBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Candidate Signature
            Column {
                Text(
                    text = "ลงชื่อ: นายวรการจักรี จำปาลี",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = "ผู้จัดทำข้อตกลงในการพัฒนางาน (ศึกษานิเทศก์ชำนาญการ)",
                    fontSize = 11.sp,
                    color = TextMuted
                )
                Text(
                    text = "วันที่ 1 ตุลาคม 2568",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))

            // Director Endorsement
            Column {
                Text(
                    text = "ความเห็นของผู้บังคับบัญชา: เห็นชอบ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = SuccessGreen
                )
                Text(
                    text = "ลงชื่อ: ผู้อำนวยการสำนักงานเขตพื้นที่การศึกษามัธยมศึกษากาฬสินธุ์",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
                Text(
                    text = "ผู้บังคับบัญชา / ประธานกรรมการประเมิน",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }
    }
}
