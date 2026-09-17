package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PortfolioRepository
import com.example.ui.components.ProgressRingMetric
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun ChallengeScreen(
    modifier: Modifier = Modifier
) {
    var selectedImpactTab by remember { mutableStateOf("TEACHER (ครู)") }
    val impactKeys = PortfolioRepository.impactItems.keys.toList()

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
                tag = "CHALLENGE & IMPACT",
                title = "ประเด็นท้าทายเพื่อพัฒนาคุณภาพ",
                subtitle = "ข้อตกลงในการพัฒนางานที่เป็นประเด็นท้าทาย (ว PA) ปีงบประมาณ พ.ศ. 2569"
            )

            ChallengeHeaderCard()
        }

        // 4 Stages of Challenge
        item {
            Text(
                text = "กระบวนการ 4 มิติของประเด็นท้าทาย (Problem → Process → Result → Impact)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
            )
        }

        items(PortfolioRepository.challengeSections) { section ->
            ChallengeSectionCard(title = section.first, body = section.second)
        }

        // TAMDEE & 2P2I Model Card
        item {
            TamdeeModelCard()
        }

        // AI Training Section
        item {
            SectionHeader(
                tag = "AI TRAINING RESULTS",
                title = "ผลงานการส่งเสริมพัฒนาทักษะ AI 110 บุคลากร",
                subtitle = "ผู้เข้ารับการอบรม 110 คน จาก 55 โรงเรียน ในสังกัด สพม.กาฬสินธุ์ (ม.ต้น 55 คน / ม.ปลาย 55 คน)"
            )
            AiTrainingMetricsCard()
        }

        // Impact Dashboard Section
        item {
            SectionHeader(
                tag = "IMPACT DASHBOARD",
                title = "ผลลัพธ์และผลกระทบ 4 มิติ",
                subtitle = "ผลสัมฤทธิ์ที่ส่งผลโดยตรงต่อครู สถานศึกษา ผู้เรียน และองค์กร"
            )

            // Tabs for 4 impacts
            ScrollableTabRow(
                selectedTabIndex = impactKeys.indexOf(selectedImpactTab),
                containerColor = MaterialTheme.colorScheme.surface,
                edgePadding = 8.dp,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                impactKeys.forEach { key ->
                    val isSelected = selectedImpactTab == key
                    Tab(
                        selected = isSelected,
                        onClick = { selectedImpactTab = key },
                        text = {
                            Text(
                                text = key,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        // Impact Items for selected tab
        item {
            ImpactItemsCard(
                category = selectedImpactTab,
                items = PortfolioRepository.impactItems[selectedImpactTab] ?: emptyList()
            )
        }
    }
}

@Composable
fun ChallengeHeaderCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SlateDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(SlateCard, SlateDark))
                )
                .padding(20.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = GoldAccent.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
            ) {
                Text(
                    text = "ประเด็นท้าทายตามมาตรฐานตำแหน่ง (ว PA)",
                    color = AmberGlow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "“${PortfolioRepository.challengeTitle}”",
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(CyanAccent)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "ขับเคลื่อนด้วยโมเดล: WORAKARN MODEL (TAMDEE & 2P2I)",
                    color = CyanAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ChallengeSectionCard(title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = body,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TamdeeModelCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.AllInclusive, contentDescription = null, tint = RoyalBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "โมเดลการปฏิบัติงาน TAMDEE & 2P2I",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = RoyalBlue
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "“คิดดี ทำดี ใช้ AI ขับเคลื่อนการศึกษาไทยสู่อนาคต”",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = GoldDark
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "TAMDEE FRAMEWORK:",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(6.dp))
            PortfolioRepository.tamdeeModel.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(RoyalBlue)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.first,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = RoyalBlue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "— ${item.second}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "2P2I FRAMEWORK:",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(6.dp))
            PortfolioRepository.twoPTwoIModel.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(GoldDark)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.first,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = GoldDark
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "— ${item.second}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun AiTrainingMetricsCard() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgressRingMetric(
                title = "ความรู้ (Knowledge)",
                percentage = 95f,
                label = "เข้าใจเทคโนโลยี AI",
                color = RoyalBlue,
                modifier = Modifier.weight(1f)
            )
            ProgressRingMetric(
                title = "ทักษะ (Skill)",
                percentage = 95f,
                label = "ใช้งานประยุกต์สอนได้",
                color = ElectricBlue,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgressRingMetric(
                title = "ทัศนคติ (Attitude)",
                percentage = 100f,
                label = "ตระหนักคุณค่า AI",
                color = SuccessGreen,
                modifier = Modifier.weight(1f)
            )
            ProgressRingMetric(
                title = "ความพึงพอใจ",
                percentage = 96.4f,
                label = "4.82/5.00 มากที่สุด",
                color = GoldDark,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ImpactItemsCard(category: String, items: List<String>) {
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
            Text(
                text = "ผลกระทบต่อ: $category",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(10.dp))
            items.forEachIndexed { i, itm ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircleOutline,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = itm,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
