package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.PortfolioRepository
import com.example.data.WorkAspect
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onOpenStoryMode: () -> Unit,
    onOpenReader: () -> Unit,
    onOpenInnovation: () -> Unit,
    onOpenSupervision: () -> Unit,
    onOpenChallenge: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            HeroCard(
                onOpenStoryMode = onOpenStoryMode,
                onOpenReader = onOpenReader,
                onOpenInnovation = onOpenInnovation
            )
        }

        // Philosophy Quote Banner
        item {
            QuoteBanner()
        }

        // Performance Dashboard Section
        item {
            SectionHeader(
                tag = "DASHBOARD",
                title = "Dashboard ผลการปฏิบัติงานเชิงประจักษ์",
                subtitle = "ตัวเลขสำคัญจากรายงาน PA 2569 ที่ผ่านการรับรองความถูกต้อง แตะการ์ดเพื่อดูแหล่งที่มา"
            )
        }

        // Stats Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        stat = PortfolioRepository.stats[0],
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        stat = PortfolioRepository.stats[1],
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        stat = PortfolioRepository.stats[2],
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        stat = PortfolioRepository.stats[3],
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        stat = PortfolioRepository.stats[4],
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        stat = PortfolioRepository.stats[5],
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // PA 2569 Timeline
        item {
            SectionHeader(
                tag = "PA 2569 TIMELINE",
                title = "รอบการประเมินผลการพัฒนางาน",
                subtitle = "วงจรการปฏิบัติงานตามข้อตกลง PA ปีงบประมาณ พ.ศ. 2569"
            )
            PaTimelineCard()
        }

        // Section: 3 ด้านของการปฏิบัติงาน
        item {
            SectionHeader(
                tag = "3 DOMAINS OF PA",
                title = "3 ด้านของการปฏิบัติงานตามมาตรฐานตำแหน่ง",
                subtitle = "โครงสร้างงานตามมาตรฐานตำแหน่งศึกษานิเทศก์ วิทยฐานะศึกษานิเทศก์ชำนาญการ แตะเพื่อดูรายละเอียดทุกตัวชี้วัด"
            )
        }

        // 3 Big Aspect Cards
        items(PortfolioRepository.workAspects) { aspect ->
            AspectBigCard(aspect = aspect)
        }

        // Target Schools Banner
        item {
            TargetSchoolsCard()
        }

        // Footer Card
        item {
            FooterCard(
                onOpenWebsite = {
                    try {
                        uriHandler.openUri("https://sites.google.com/sesao24.go.th/techno")
                    } catch (_: Exception) {}
                }
            )
        }
    }
}

@Composable
fun HeroCard(
    onOpenStoryMode: () -> Unit,
    onOpenReader: () -> Unit,
    onOpenInnovation: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SlateDark
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.radialGradient(
                        colors = listOf(SlateCard, SlateDark),
                        radius = 800f
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Tag
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = GoldAccent.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "DIGITAL PROFESSIONAL PORTFOLIO • PA 2569",
                        color = AmberGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Avatar / Portrait
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .border(3.dp, GoldAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_portrait_1789608205759),
                        contentDescription = "ศน.วรการจักรี จำปาลี",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "WORAKARN",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp,
                    color = CyanAccent
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = PortfolioRepository.ownerName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${PortfolioRepository.academicStanding} • ${PortfolioRepository.positionNumber}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GoldAccent,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${PortfolioRepository.department}\n${PortfolioRepository.office}",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = RoyalBlue.copy(alpha = 0.3f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RoyalBlue.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = PortfolioRepository.tagline,
                        color = CyanAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = PortfolioRepository.heroSubtitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = 19.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Hero action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onOpenStoryMode,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AutoStories, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("PA Story Mode", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onOpenReader,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("อ่านรายงาน PA", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    }
                }
            }
        }
    }
}

@Composable
fun QuoteBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(RoyalBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = PortfolioRepository.coreConceptThai,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBlue
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "From Supervision to Impact: บริบท → เป้าหมาย → กระบวนการ → นวัตกรรม → ผลลัพธ์",
                    fontSize = 11.sp,
                    color = TextMuted,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
fun PaTimelineCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            val stages = listOf(
                Pair("01 OCT 2025", "เริ่มรอบการประเมิน PA 2569 • ศึกษาบริบทและจัดทำข้อตกลง"),
                Pair("PA PERFORMANCE", "การปฏิบัติงานตามมาตรฐานตำแหน่ง 3 ด้าน 15 ตัวชี้วัด"),
                Pair("DIGITAL / AI / ACTIVE LEARNING", "นิเทศ พัฒนาสื่อนวัตกรรม อบรม AI 110 คน 55 รร."),
                Pair("CHALLENGE (PLC)", "ขับเคลื่อนประเด็นท้าทายด้วยโมเดล TAMDEE & 2P2I"),
                Pair("30 SEP 2026", "สิ้นสุดรอบการประเมิน • สรุปรายงานและรับรองผลงาน")
            )

            stages.forEachIndexed { index, stage ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(28.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(if (index == 0 || index == stages.size - 1) GoldAccent else RoyalBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                        }
                        if (index < stages.size - 1) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(38.dp)
                                    .background(RoyalBlue.copy(alpha = 0.3f))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.padding(bottom = 10.dp)) {
                        Text(
                            text = stage.first,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (index == 0 || index == stages.size - 1) GoldDark else RoyalBlue
                        )
                        Text(
                            text = stage.second,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AspectBigCard(aspect: WorkAspect) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
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
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(RoyalBlue.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "0${aspect.aspectNumber}",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = RoyalBlue
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = aspect.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = aspect.subtitle,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Toggle",
                        tint = RoyalBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = aspect.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Divider(color = MaterialTheme.colorScheme.surfaceVariant)

                    aspect.items.forEach { item ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = RoyalBlue
                                    ) {
                                        Text(
                                            text = item.code,
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.description,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 16.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.TrackChanges,
                                        contentDescription = null,
                                        tint = GoldDark,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = item.kpiText,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = GoldDark
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (isExpanded) "ย่อรายละเอียด" else "ดู ${aspect.items.size} หัวข้อย่อย",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBlue
                )
            }
        }
    }
}

@Composable
fun TargetSchoolsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Domain, contentDescription = null, tint = RoyalBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "สหวิทยาเขตเมืองกาฬสินธุ์ (7 โรงเรียนรับผิดชอบ)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "พื้นที่นำร่องการขับเคลื่อนการใช้เทคโนโลยี AI และ Active Learning สพม.กาฬสินธุ์",
                fontSize = 12.sp,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(10.dp))
            PortfolioRepository.targetSchools.forEachIndexed { i, school ->
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
                            .background(GoldAccent)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${i + 1}. $school",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun FooterCard(onOpenWebsite: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SlateDark
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WORAKARN",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = CyanAccent
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = PortfolioRepository.ownerName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "${PortfolioRepository.academicStanding} • ${PortfolioRepository.office}",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = PortfolioRepository.tagline,
                fontSize = 11.sp,
                color = AmberGlow,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Digital Professional Portfolio • PA Performance 2026",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onOpenWebsite,
                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Language, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("เยี่ยมชมเว็บไซต์กลุ่มนิเทศฯ สพม.กาฬสินธุ์", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "พัฒนาโดย ศน.วรการจักรี จำปาลี | สพม.กาฬสินธุ์",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}
