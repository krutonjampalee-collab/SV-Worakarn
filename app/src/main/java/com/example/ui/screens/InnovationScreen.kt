package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.InnovationProject
import com.example.data.KnowledgeItem
import com.example.data.PortfolioRepository
import com.example.ui.components.InnovationDetailDialog
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun InnovationScreen(
    onSelectProject: (InnovationProject) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedCategoryFilter by remember { mutableStateOf("ทั้งหมด") }
    var selectedProjectForModal by remember { mutableStateOf<InnovationProject?>(null) }
    val uriHandler = LocalUriHandler.current

    val tabs = listOf("8 นวัตกรรมหลัก", "Digital & AI Gallery", "Knowledge Hub (องค์ความรู้)")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(
                tag = "DIGITAL & INNOVATION",
                title = "นวัตกรรมและเทคโนโลยีดิจิทัล",
                subtitle = "ผลงานการวิจัยและพัฒนาระบบดิจิทัล AI เพื่อยกระดับการจัดการศึกษา สพม.กาฬสินธุ์"
            )

            // Tab bar
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = RoyalBlue,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        when (selectedTab) {
            0 -> {
                // 8 Innovation Cards
                item {
                    Text(
                        text = "ระบบดิจิทัลและนวัตกรรมที่พัฒนาขึ้น 8 ระบบ (พร้อมใช้งานจริง)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                }

                items(PortfolioRepository.innovationProjects) { project ->
                    InnovationCard(
                        project = project,
                        onViewDetails = { selectedProjectForModal = project },
                        onOpenSystem = {
                            try {
                                uriHandler.openUri(project.demoUrl)
                            } catch (_: Exception) {}
                        }
                    )
                }
            }

            1 -> {
                // Digital & AI Gallery Categories
                item {
                    DigitalAiGallerySection()
                }
            }

            2 -> {
                // Knowledge Hub
                item {
                    Text(
                        text = "ศูนย์รวมองค์ความรู้ สื่อ และคู่มือการพัฒนางาน",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                }

                items(PortfolioRepository.knowledgeArticles) { article ->
                    KnowledgeCard(
                        article = article,
                        onOpen = {
                            try {
                                uriHandler.openUri(article.linkUrl)
                            } catch (_: Exception) {}
                        }
                    )
                }
            }
        }
    }

    selectedProjectForModal?.let { proj ->
        InnovationDetailDialog(
            project = proj,
            onDismiss = { selectedProjectForModal = null }
        )
    }
}

@Composable
fun InnovationCard(
    project: InnovationProject,
    onViewDetails: () -> Unit,
    onOpenSystem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onViewDetails() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = RoyalBlue
                ) {
                    Text(
                        text = project.number,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = GoldAccent.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = project.category,
                        color = GoldDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = project.title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = project.solution,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Tech badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                project.technology.take(3).forEach { tech ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = tech,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = project.evidenceNote,
                    fontSize = 11.sp,
                    color = TextMuted
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    TextButton(onClick = onViewDetails) {
                        Text("ดูรายละเอียด", fontSize = 12.sp)
                    }
                    Button(
                        onClick = onOpenSystem,
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ดูผลงาน", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DigitalAiGallerySection() {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // AI for Education Category
        CategoryBox(
            title = "1. AI FOR EDUCATION",
            icon = Icons.Default.AutoAwesome,
            color = PurpleAI,
            items = listOf(
                "AI เพื่อการจัดการเรียนรู้ (สร้างสื่อนวัตกรรม, แผนการสอน Active Learning)",
                "AI เพื่อการบริหารจัดการ (Integrity Smart Office, สารสนเทศออนไลน์)",
                "AI Literacy (สร้างความตระหนักรู้และการใช้ AI อย่างปลอดภัย จริยธรรม)",
                "การอบรม AI (พัฒนาครูผู้สอน AI สสวท. 110 คน จาก 55 โรงเรียน)",
                "การให้คำปรึกษาด้าน AI (Coaching & Mentoring แก่ครูในสหวิทยาเขต)"
            )
        )

        // Digital Platform Category
        CategoryBox(
            title = "2. DIGITAL PLATFORM",
            icon = Icons.Default.Devices,
            color = RoyalBlue,
            items = listOf(
                "Online Learning (OBEC Content Center, NDLP, คลังสื่อการเรียนรู้)",
                "Digital Supervision (ระบบนิเทศการศึกษาออนไลน์ SPMKS Digital System)",
                "Digital Portfolio (ระบบ E-Portfolio SesaoKSN สำหรับครู)",
                "Online Assessment (ระบบ SesaoKSN Pretest O-Net และแบบทดสอบอัตโนมัติ)",
                "Digital Service (ระบบขอสำเนาเอกสารราชการ, ระบบสร้าง Smart QR Code)"
            )
        )

        // Google Technology Category
        CategoryBox(
            title = "3. GOOGLE TECHNOLOGY",
            icon = Icons.Default.CloudQueue,
            color = GoldDark,
            items = listOf(
                "Google Apps Script (พัฒนา Web App ราชการ, งานสารบรรณ, ระบบแจ้งเตือน)",
                "Google Workspace for Education (Gmail, Drive, Classroom, Docs, Slides)",
                "Google Sites (เว็บไซต์กลุ่มนิเทศฯ techno, เว็บไซต์ SV-Worakarn, E-Portfolio)",
                "Google Forms (แบบสำรวจความต้องการ, แบบประเมินความพึงพอใจ, คลังข้อสอบ)",
                "Google Sheets (ฐานข้อมูล Cloud Database เชื่อมโยง Real-time Dashboard)"
            )
        )
    }
}

@Composable
fun CategoryBox(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    items: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = color
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            items.forEach { itm ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = itm,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
fun KnowledgeCard(
    article: KnowledgeItem,
    onOpen: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = RoyalBlue.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = article.category,
                        color = RoyalBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = article.readTime,
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = article.summary,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    article.tags.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "#$tag",
                                fontSize = 10.sp,
                                color = TextMuted,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Text(
                    text = "อ่านเพิ่มเติม →",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBlue
                )
            }
        }
    }
}
