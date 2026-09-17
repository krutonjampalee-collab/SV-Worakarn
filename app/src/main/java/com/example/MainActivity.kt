package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.InnovationProject
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    var selectedNavIndex by remember { mutableStateOf(0) }
    var isReadingPaReport by remember { mutableStateOf(false) }
    var showCommitteeDialog by remember { mutableStateOf(false) }
    var showStoryModeDialog by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var selectedProjectForModal by remember { mutableStateOf<InnovationProject?>(null) }

    // Handle back button when report reader is open
    BackHandler(enabled = isReadingPaReport) {
        isReadingPaReport = false
    }

    if (isReadingPaReport) {
        PaReportReaderScreen(
            onBack = { isReadingPaReport = false }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, GoldAccent, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.hero_portrait_1789608205759),
                                    contentDescription = "Portrait",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "ศน.วรการจักรี จำปาลี",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "PA 2569 • สพม.กาฬสินธุ์",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { showSearchDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = RoyalBlue
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = GoldAccent.copy(alpha = 0.18f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldDark.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clickable { showCommitteeDialog = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = GoldDark,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "กรรมการ",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldDark
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    val navItems = listOf(
                        Triple("หน้าแรก", Icons.Default.Home, 0),
                        Triple("นวัตกรรม", Icons.Default.Lightbulb, 1),
                        Triple("การนิเทศ", Icons.Default.Psychology, 2),
                        Triple("ประเด็นท้าทาย", Icons.Default.Flag, 3),
                        Triple("หลักฐาน", Icons.Default.FolderSpecial, 4)
                    )

                    navItems.forEach { item ->
                        val isSelected = selectedNavIndex == item.third
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedNavIndex = item.third },
                            icon = {
                                Icon(
                                    imageVector = item.second,
                                    contentDescription = item.first,
                                    tint = if (isSelected) RoyalBlue else TextMuted
                                )
                            },
                            label = {
                                Text(
                                    text = item.first,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) RoyalBlue else TextMuted
                                )
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showCommitteeDialog = true },
                    containerColor = RoyalBlue,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.AssignmentTurnedIn, contentDescription = null, tint = AmberGlow)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "กรรมการประเมิน",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedNavIndex) {
                    0 -> HomeScreen(
                        onOpenStoryMode = { showStoryModeDialog = true },
                        onOpenReader = { isReadingPaReport = true },
                        onOpenInnovation = { selectedNavIndex = 1 },
                        onOpenSupervision = { selectedNavIndex = 2 },
                        onOpenChallenge = { selectedNavIndex = 3 }
                    )
                    1 -> InnovationScreen(
                        onSelectProject = { proj -> selectedProjectForModal = proj }
                    )
                    2 -> SupervisionScreen()
                    3 -> ChallengeScreen()
                    4 -> EvidenceScreen(
                        onOpenPaReader = { isReadingPaReport = true }
                    )
                }
            }
        }
    }

    // Committee Quick Access Modal
    if (showCommitteeDialog) {
        CommitteeQuickMenuDialog(
            onDismiss = { showCommitteeDialog = false },
            onNavigateToSection = { sectionKey ->
                when (sectionKey) {
                    "home" -> selectedNavIndex = 0
                    "stats" -> selectedNavIndex = 0
                    "supervision" -> selectedNavIndex = 2
                    "innovation" -> selectedNavIndex = 1
                    "impact" -> selectedNavIndex = 3
                    "awards" -> selectedNavIndex = 4
                    "evidence" -> selectedNavIndex = 4
                    "reader" -> isReadingPaReport = true
                }
            }
        )
    }

    // PA Story Mode Modal
    if (showStoryModeDialog) {
        PaStoryModeDialog(
            onDismiss = { showStoryModeDialog = false }
        )
    }

    // Global Search Modal
    if (showSearchDialog) {
        GlobalSearchDialog(
            onDismiss = { showSearchDialog = false },
            onSelectSection = { section ->
                when (section) {
                    "innovation" -> selectedNavIndex = 1
                    "supervision" -> selectedNavIndex = 2
                    "challenge" -> selectedNavIndex = 3
                    "evidence" -> selectedNavIndex = 4
                    else -> selectedNavIndex = 0
                }
            },
            onSelectProject = { proj ->
                selectedProjectForModal = proj
            }
        )
    }

    // Innovation Details Dialog
    selectedProjectForModal?.let { proj ->
        InnovationDetailDialog(
            project = proj,
            onDismiss = { selectedProjectForModal = null }
        )
    }
}
