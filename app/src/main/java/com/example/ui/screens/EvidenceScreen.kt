package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.CertificateItem
import com.example.data.PortfolioRepository
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun EvidenceScreen(
    onOpenPaReader: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCertificate by remember { mutableStateOf<CertificateItem?>(null) }
    var selectedGalleryImage by remember { mutableStateOf<String?>(null) }
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Award Showcase Header
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(
                tag = "AWARDS & PRIDE",
                title = "รางวัลและความภาคภูมิใจ",
                subtitle = "รางวัลเกียรติยศระดับภูมิภาค ด้านนวัตกรรมการนิเทศการศึกษา"
            )

            AwardShowcaseCard()
        }

        // Full PA Report Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenPaReader() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
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
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(RoyalBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "อ่านรายงาน PA ฉบับเต็ม (42 หน้า)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = RoyalBlue
                        )
                        Text(
                            text = "เปิดอ่านรายงานผลงานพร้อมสารบัญ ค้นหา และลงนามรับรอง",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = RoyalBlue)
                }
            }
        }

        // Certificates Section
        item {
            SectionHeader(
                tag = "CERTIFICATES",
                title = "วุฒิบัตรและการพัฒนาตนเอง",
                subtitle = "6 วุฒิบัตรปัญญาประดิษฐ์จาก สดช. และวุฒิบัตรจาก สพฐ., Google, Canva (หน้า 22-23 รายงาน PA)"
            )
        }

        items(PortfolioRepository.certificates) { cert ->
            CertificateCard(
                cert = cert,
                onClick = { selectedCertificate = cert }
            )
        }

        // Media Gallery Section
        item {
            SectionHeader(
                tag = "MEDIA GALLERY",
                title = "คลังภาพกิจกรรมและสื่อการเผยแพร่",
                subtitle = "ภาพการนิเทศ กิจกรรมอบรมเชิงปฏิบัติการ และ Infographic สื่อความรู้ AI"
            )
            MediaGalleryCard(
                onSelectImage = { selectedGalleryImage = it }
            )
        }

        // Official Links Center
        item {
            SectionHeader(
                tag = "EXTERNAL LINKS",
                title = "ช่องทางเผยแพร่ผลงานออนไลน์",
                subtitle = "เชื่อมต่อไปยังเว็บไซต์กลุ่มนิเทศฯ ช่อง YouTube และแหล่งเรียนรู้"
            )
            OfficialLinksCard(
                onOpen = { url ->
                    try {
                        uriHandler.openUri(url)
                    } catch (_: Exception) {}
                }
            )
        }
    }

    // Certificate Dialog
    selectedCertificate?.let { cert ->
        AlertDialog(
            onDismissRequest = { selectedCertificate = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = GoldDark)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = cert.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(
                        text = "หน่วยงานที่ออกวุฒิบัตร:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                    Text(
                        text = cert.issuer,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "ช่วงเวลา: ${cert.dateOrYear} • หมวด: ${cert.category}",
                        fontSize = 12.sp,
                        color = GoldDark,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RoyalBlue.copy(alpha = 0.08f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "หลักฐานฉบับจริงปรากฏในเอกสารรายงาน PA ประจำปีงบประมาณ พ.ศ. 2569 หน้า 22-23",
                            fontSize = 12.sp,
                            color = RoyalBlue,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedCertificate = null }) {
                    Text("ปิด")
                }
            }
        )
    }

    // Lightbox Dialog
    selectedGalleryImage?.let { imgTitle ->
        Dialog(onDismissRequest = { selectedGalleryImage = null }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = imgTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        IconButton(onClick = { selectedGalleryImage = null }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SlateDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hero_portrait_1789608205759),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "ภาพกิจกรรมปรากฏในเอกสารรายงานผลการพัฒนางานตามข้อตกลง PA ปีงบประมาณ พ.ศ. 2569",
                        fontSize = 12.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun AwardShowcaseCard() {
    val award = PortfolioRepository.award

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SlateDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                    )
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(GoldAccent.copy(alpha = 0.2f))
                    .border(2.dp, GoldAccent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = AmberGlow,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = GoldAccent.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
            ) {
                Text(
                    text = "รางวัลอันทรงคุณค่า ${award.year}",
                    color = AmberGlow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = award.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "จาก: ${award.organization}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CyanAccent,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = award.description,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = SlateBorder.copy(alpha = 0.6f)
            ) {
                Text(
                    text = "หลักฐาน: หน้า 26 รายงานผลการปฏิบัติงานตามข้อตกลง PA",
                    fontSize = 11.sp,
                    color = AmberGlow,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CertificateCard(
    cert: CertificateItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(RoyalBlue.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = null,
                    tint = RoyalBlue,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cert.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${cert.issuer} • ${cert.dateOrYear}",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun MediaGalleryCard(
    onSelectImage: (String) -> Unit
) {
    val items = listOf(
        "ภาพการอบรมครู AI สสวท. (ม.ต้น / ม.ปลาย 110 คน)",
        "ภาพการลงพื้นที่นิเทศติดตาม สหวิทยาเขตเมืองกาฬสินธุ์",
        "ภาพวิทยากรอบรม รร.โนนศิลาพิทยาคม และ รร.ธัญญาพัฒนวิทย์",
        "Infographic คู่มือ Prompt + Gemini AI สำหรับการศึกษา",
        "Infographic นวัตกรรม Integrity Smart Office (ISO)",
        "ระบบนิเทศออนไลน์และเว็บไซต์กลุ่มนิเทศฯ สพม.กาฬสินธุ์"
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
            items.forEachIndexed { i, title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSelectImage(title) }
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = RoyalBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = "Zoom",
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
                if (i < items.size - 1) {
                    Divider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                }
            }
        }
    }
}

@Composable
fun OfficialLinksCard(onOpen: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { onOpen("https://sites.google.com/sesao24.go.th/techno") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Language, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("เว็บไซต์กลุ่มนิเทศฯ techno สพม.กาฬสินธุ์")
            }

            OutlinedButton(
                onClick = { onOpen("http://contentcenter.obec.go.th/") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Cloud, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("คลังสื่อ OBEC Content Center (สพฐ.)")
            }
        }
    }
}
