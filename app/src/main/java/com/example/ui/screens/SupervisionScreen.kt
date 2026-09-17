package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PortfolioRepository
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun SupervisionScreen(
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    var selectedDiagramStep by remember { mutableStateOf(0) }
    var selectedActiveStep by remember { mutableStateOf(0) }

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
                tag = "SUPERVISION MODEL",
                title = "ระบบนิเทศการศึกษาออนไลน์",
                subtitle = "WORAKARN AI SUPERVISION MODEL : ขับเคลื่อนวงจรคุณภาพ PDCA ผสานพลัง PLC & AI"
            )

            // Major Project Showcase Card
            SupervisionModelHeroCard(
                onOpenSystem = {
                    try {
                        uriHandler.openUri("https://sites.google.com/sesao24.go.th/techno")
                    } catch (_: Exception) {}
                }
            )
        }

        // PDCA Interactive Diagram
        item {
            SectionHeader(
                tag = "SUPERVISION PROCESS",
                title = "กระบวนการนิเทศแบบกัลยาณมิตร (PDCA Cycle)",
                subtitle = "แตะแต่ละขั้นตอนเพื่อดูการเชื่อมโยงกับ PLC, Coaching & Mentoring และเครื่องมือ AI"
            )
            PdcaInteractiveCard(
                selectedIndex = selectedDiagramStep,
                onSelectStep = { selectedDiagramStep = it }
            )
        }

        // Active Learning Interactive Diagram
        item {
            SectionHeader(
                tag = "ACTIVE LEARNING",
                title = "Digital Technology + AI + Active Learning",
                subtitle = "ห่วงโซ่การพัฒนาคุณภาพการเรียนรู้จากครูสู่ทักษะผู้เรียนแห่งศตวรรษที่ 21"
            )
            ActiveLearningChainCard(
                selectedIndex = selectedActiveStep,
                onSelectStep = { selectedActiveStep = it }
            )
        }

        // Student 7 Core Skills
        item {
            StudentSkillsGrid()
        }

        // 7 Target Schools in Area
        item {
            SupervisionAreaSchoolsCard()
        }
    }
}

@Composable
fun SupervisionModelHeroCard(onOpenSystem: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SlateDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(SlateCard, SlateDark)
                    )
                )
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(ElectricBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "WORAKARN AI SUPERVISION MODEL",
                        fontWeight = FontWeight.Black,
                        fontSize = 17.sp,
                        color = Color.White
                    )
                    Text(
                        text = "SPMKS Digital System (Digital & AI Driven)",
                        fontSize = 12.sp,
                        color = CyanAccent
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "ระบบนิเทศการศึกษาออนไลน์ที่พัฒนาขึ้นเพื่อสนับสนุนการนิเทศ ติดตาม ประเมินผล และการให้คำปรึกษาแก่สถานศึกษา ครู และบุคลากรทางการศึกษา สำนักงานเขตพื้นที่การศึกษามัธยมศึกษากาฬสินธุ์",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "8 คุณลักษณะเด่นของระบบ:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = GoldAccent
            )

            Spacer(modifier = Modifier.height(8.dp))

            val features = listOf(
                "Online Supervision" to "นิเทศทางไกลผ่านระบบดิจิทัล",
                "Digital Evidence" to "คลังรวบรวมหลักฐานเชิงประจักษ์",
                "Monitoring" to "ติดตามผลสัมฤทธิ์อย่างต่อเนื่อง",
                "Evaluation" to "ประเมินผลตามมาตรฐาน ก.ค.ศ.",
                "Dashboard" to "แดชบอร์ดสารสนเทศ Real-time",
                "Data Information" to "ฐานข้อมูลกลางเพื่อการตัดสินใจ",
                "PLC Network" to "ชุมชนการเรียนรู้ทางวิชาชีพ",
                "Follow-up" to "ทบทวนหลังปฏิบัติงาน (AAR)"
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                features.chunked(2).forEach { rowPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowPair.forEach { f ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SlateBorder.copy(alpha = 0.5f),
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = CyanAccent,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(text = f.first, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                        Text(text = f.second, fontSize = 9.sp, color = TextMuted)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onOpenSystem,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Login, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("เข้าสู่ระบบนิเทศ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onOpenSystem,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ดูหลักฐานระบบ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }
        }
    }
}

@Composable
fun PdcaInteractiveCard(
    selectedIndex: Int,
    onSelectStep: (Int) -> Unit
) {
    val steps = listOf(
        Triple("PLAN (วางแผน)", "วิเคราะห์ปัญหา & ออกแบบแผนนิเทศ AI", "ศึกษา PA ผู้บังคับบัญชา สำรวจความต้องการใช้ AI ของ 7 โรงเรียน และจัดทำแผนนิเทศการประยุกต์ใช้ AI ในการบริหารจัดการเรียนการสอน ปีการศึกษา 2569"),
        Triple("DO (ปฏิบัติ)", "นิเทศแบบกัลยาณมิตร & อบรมปฏิบัติการ", "ลงพื้นที่นิเทศ On-site และ Online, อบรมเชิงปฏิบัติการ AI สำหรับครู สสวท. 110 คน และสนับสนุนการสร้างสื่อนวัตกรรม e-Book, AR/VR, Gamification"),
        Triple("CHECK (ตรวจสอบ)", "ติดตามและประเมินผลการจัดศึกษา", "สังเกตการณ์ในชั้นเรียน สัมภาษณ์ครู นักเรียน ผู้ปกครอง และติดตามผลสัมฤทธิ์ตามกรอบ IQA สพฐ. 4 องค์ประกอบ และนโยบาย 'เรียนดี มีความสุข'"),
        Triple("ACT (ปรับปรุง)", "AAR & พัฒนาสารสนเทศต่อเนื่อง", "ประชุมแลกเปลี่ยนเรียนรู้ PLC และการทบทวนหลังการปฏิบัติงาน (AAR) จัดทำรายงานสารสนเทศเสนอบริหาร และขยายผลนวัตกรรมสู่โรงเรียนอื่นๆ")
    )

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
            // Step Switcher Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("P", "D", "C", "A").forEachIndexed { index, letter ->
                    val isSelected = selectedIndex == index
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) RoyalBlue else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectStep(index) }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = letter,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = when (letter) {
                                    "P" -> "Plan"
                                    "D" -> "Do"
                                    "C" -> "Check"
                                    else -> "Act"
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) AmberGlow else TextMuted
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Step Detail
            val active = steps[selectedIndex]
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = RoyalBlue.copy(alpha = 0.08f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = active.first,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBlue
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = active.second,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = active.third,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Hub, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "บูรณาการ: PLC + Coaching + Mentoring + Digital Platform + AI",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
            }
        }
    }
}

@Composable
fun ActiveLearningChainCard(
    selectedIndex: Int,
    onSelectStep: (Int) -> Unit
) {
    val chain = PortfolioRepository.activeLearningFlow

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
            chain.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) RoyalBlue.copy(alpha = 0.1f) else Color.Transparent)
                        .clickable { onSelectStep(index) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) RoyalBlue else MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${item.first} (${item.second})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) RoyalBlue else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = item.third,
                            fontSize = 11.sp,
                            color = TextMuted,
                            lineHeight = 15.sp
                        )
                    }

                    if (index < chain.size - 1) {
                        Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun StudentSkillsGrid() {
    val skills = listOf(
        "การมีส่วนร่วมในห้องเรียน",
        "กล้าคิด กล้าทำ กล้าแสดงออก",
        "ทักษะการคิดวิเคราะห์",
        "การสังเคราะห์ข้อมูลสารสนเทศ",
        "ทักษะการแก้ปัญหาเชิงสร้างสรรค์",
        "การทำงานร่วมกับผู้อื่น (Teamwork)",
        "ทักษะดิจิทัลและ AI Literacy"
    )

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
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldDark)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ผลลัพธ์ทักษะผู้เรียน 7 ด้านที่รายงานเชิงประจักษ์",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            skills.forEachIndexed { i, skill ->
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
                        text = "${i + 1}. $skill",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun SupervisionAreaSchoolsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "การประสานงานและนิเทศติดตามสหวิทยาเขตเมืองกาฬสินธุ์",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = RoyalBlue
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "ปฏิบัติการนิเทศอย่างต่อเนื่องทั้ง On-site และ Online เสริมพลังครูผู้สอนเทคโนโลยีและทุกกลุ่มสาระการเรียนรู้ เพื่อผู้เรียนได้เรียนรู้อย่างมีความสุขและมีคุณภาพในศตวรรษที่ 21",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 17.sp
            )
        }
    }
}
