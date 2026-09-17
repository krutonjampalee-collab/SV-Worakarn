package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.*
import com.example.ui.theme.*

@Composable
fun CommitteeQuickMenuDialog(
    onDismiss: () -> Unit,
    onNavigateToSection: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(GoldAccent.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = GoldDark
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "เมนูกรรมการประเมิน PA",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "เข้าถึงข้อมูลและหลักฐานสำคัญใน 1 คลิก",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val menuItems = listOf(
                    Triple("📘 ข้อมูล PA & ว PA", "ข้อมูลผู้รับการประเมิน ภาระงาน และสังกัด", "home"),
                    Triple("🎯 ตัวชี้วัดสำคัญ (KPIs)", "สถิติ 55 รร. 110 คน และผลสัมฤทธิ์ 95%", "stats"),
                    Triple("🔄 กระบวนการดำเนินงาน", "โมเดล TAMDEE & 2P2I และวงจร PDCA", "supervision"),
                    Triple("💡 นวัตกรรม 8 ระบบ", "ระบบนิเทศออนไลน์, QR Free, Pretest O-Net ฯลฯ", "innovation"),
                    Triple("📊 ผลลัพธ์และ Impact", "ผลสัมฤทธิ์ 4 ด้าน: ครู โรงเรียน ผู้เรียน เขตพื้นที่", "impact"),
                    Triple("🏆 รางวัลระดับภาคฯ", "รางวัลนวัตกรรมการนิเทศ ระดับดีเยี่ยม 2569", "awards"),
                    Triple("📂 คลังหลักฐาน & วุฒิบัตร", "วุฒิบัตร สดช. 6 ใบ, สพฐ., แผนนิเทศ", "evidence"),
                    Triple("📄 รายงาน PA ฉบับเต็ม", "เปิดอ่านรายงาน PA ทั้งเล่ม พร้อมสารบัญและค้นหา", "reader")
                )

                items(menuItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onDismiss()
                                onNavigateToSection(item.third)
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.first,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = item.second,
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = null,
                                tint = RoyalBlue,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("ปิด")
            }
        }
    )
}

@Composable
fun PaStoryModeDialog(
    onDismiss: () -> Unit
) {
    var currentStepIndex by remember { mutableStateOf(0) }
    val steps = PortfolioRepository.storySteps
    val currentStep = steps[currentStepIndex]

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.88f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(RoyalBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoStories,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "PA Story Mode",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "เรื่องเล่าผลงานเชิงประจักษ์ 7 ขั้นตอน",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Step progress bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    steps.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    if (index <= currentStepIndex) RoyalBlue else Color.LightGray.copy(alpha = 0.4f)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Content
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = GoldAccent.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${currentStep.stage} • ${currentStep.englishTag}",
                                color = GoldDark,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentStep.headline,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RoyalBlue,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentStep.detail,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "จุดเน้นและผลสัมฤทธิ์:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        currentStep.highlights.forEach { hl ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = hl,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom navigation controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            if (currentStepIndex > 0) currentStepIndex--
                        },
                        enabled = currentStepIndex > 0
                    ) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                        Text("ย้อนกลับ")
                    }

                    Text(
                        text = "${currentStepIndex + 1} / ${steps.size}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )

                    Button(
                        onClick = {
                            if (currentStepIndex < steps.size - 1) {
                                currentStepIndex++
                            } else {
                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
                    ) {
                        Text(if (currentStepIndex == steps.size - 1) "สิ้นสุดการนำเสนอ" else "ถัดไป")
                        if (currentStepIndex < steps.size - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InnovationDetailDialog(
    project: InnovationProject,
    onDismiss: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = RoyalBlue.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "นวัตกรรมลำดับที่ ${project.number} • ${project.category}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBlue,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = project.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 24.sp
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                item {
                    Text(
                        text = "สภาพปัญหา (Problem):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = WarningOrange
                    )
                    Text(
                        text = project.problem,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "แนวทางแก้ไขและพัฒนา (Solution):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = RoyalBlue
                    )
                    Text(
                        text = project.solution,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "เทคโนโลยีที่ใช้ (Technology):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = GoldDark
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        project.technology.forEach { tech ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = tech,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "ผลกระทบต่อการศึกษา (Impact):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = SuccessGreen
                    )
                    Text(
                        text = project.impact,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "หลักฐานอ้างอิง: ${project.evidenceNote}",
                            fontSize = 11.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    try {
                        uriHandler.openUri(project.demoUrl)
                    } catch (_: Exception) {}
                },
                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
            ) {
                Icon(imageVector = Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("เข้าสู่ระบบ / ดูผลงาน")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("ปิด")
            }
        }
    )
}

@Composable
fun GlobalSearchDialog(
    onDismiss: () -> Unit,
    onSelectSection: (String) -> Unit,
    onSelectProject: (InnovationProject) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ทั้งหมด") }

    val filters = listOf("ทั้งหมด", "นวัตกรรม", "การนิเทศ", "AI", "Active Learning", "หลักฐาน")

    // Filter projects and items based on search
    val filteredProjects = remember(searchQuery, selectedFilter) {
        PortfolioRepository.innovationProjects.filter { p ->
            val matchText = searchQuery.isEmpty() ||
                    p.title.contains(searchQuery, ignoreCase = true) ||
                    p.problem.contains(searchQuery, ignoreCase = true) ||
                    p.solution.contains(searchQuery, ignoreCase = true) ||
                    p.technology.any { it.contains(searchQuery, ignoreCase = true) }

            val matchFilter = when (selectedFilter) {
                "ทั้งหมด" -> true
                "นวัตกรรม" -> true
                "AI" -> p.technology.any { it.contains("AI", ignoreCase = true) } || p.title.contains("AI", ignoreCase = true)
                "การนิเทศ" -> p.category.contains("Supervision", ignoreCase = true)
                else -> true
            }
            matchText && matchFilter
        }
    }

    val filteredKnowledge = remember(searchQuery, selectedFilter) {
        PortfolioRepository.knowledgeArticles.filter { k ->
            val matchText = searchQuery.isEmpty() ||
                    k.title.contains(searchQuery, ignoreCase = true) ||
                    k.summary.contains(searchQuery, ignoreCase = true) ||
                    k.tags.any { it.contains(searchQuery, ignoreCase = true) }

            val matchFilter = when (selectedFilter) {
                "ทั้งหมด" -> true
                "AI" -> k.category.contains("AI", ignoreCase = true) || k.tags.any { it.contains("AI", ignoreCase = true) }
                "Active Learning" -> k.category.contains("Active", ignoreCase = true) || k.tags.any { it.contains("Active", ignoreCase = true) }
                else -> true
            }
            matchText && matchFilter
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("ค้นหาผลงาน, AI, นวัตกรรม, PA, โรงเรียน...") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = RoyalBlue)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Category chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    filters.forEach { f ->
                        FilterChip(
                            selected = selectedFilter == f,
                            onClick = { selectedFilter = f },
                            label = { Text(f, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Results list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (filteredProjects.isNotEmpty()) {
                        item {
                            Text(
                                text = "ระบบและนวัตกรรม (${filteredProjects.size} รายการ)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = RoyalBlue
                            )
                        }
                        items(filteredProjects) { project ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onDismiss()
                                        onSelectProject(project)
                                    },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${project.number}. ${project.title}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        Text(
                                            text = project.impact,
                                            fontSize = 11.sp,
                                            color = TextMuted,
                                            maxLines = 2
                                        )
                                    }
                                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = RoyalBlue)
                                }
                            }
                        }
                    }

                    if (filteredKnowledge.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "องค์ความรู้และคู่มือ (${filteredKnowledge.size} รายการ)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = GoldDark
                            )
                        }
                        items(filteredKnowledge) { article ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = article.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = article.summary,
                                        fontSize = 11.sp,
                                        color = TextMuted,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }

                    if (filteredProjects.isEmpty() && filteredKnowledge.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.SearchOff,
                                        contentDescription = null,
                                        tint = TextMuted,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "ไม่พบผลลัพธ์ที่ตรงกับคำค้นหา",
                                        color = TextMuted,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
