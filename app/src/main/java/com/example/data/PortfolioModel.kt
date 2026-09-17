package com.example.data

data class StatItem(
    val title: String,
    val value: String,
    val numericValue: Float,
    val unit: String,
    val subtitle: String,
    val sourceDescription: String,
    val iconName: String = "School"
)

data class WorkAspectItem(
    val code: String,
    val title: String,
    val description: String,
    val kpiText: String,
    val subItems: List<String> = emptyList()
)

data class WorkAspect(
    val id: String,
    val aspectNumber: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val items: List<WorkAspectItem>
)

data class InnovationProject(
    val id: String,
    val number: String,
    val title: String,
    val category: String,
    val problem: String,
    val solution: String,
    val technology: List<String>,
    val impact: String,
    val demoUrl: String = "https://sites.google.com/sesao24.go.th/techno",
    val evidenceNote: String
)

data class KnowledgeItem(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val tags: List<String>,
    val readTime: String,
    val linkUrl: String = "https://sites.google.com/sesao24.go.th/techno"
)

data class AwardItem(
    val title: String,
    val level: String,
    val organization: String,
    val year: String,
    val description: String
)

data class CertificateItem(
    val title: String,
    val issuer: String,
    val dateOrYear: String,
    val category: String
)

data class EvidenceItem(
    val title: String,
    val category: String,
    val type: String, // PDF, System, Image, Doc
    val pagesOrDesc: String,
    val targetSection: String
)

data class StoryStep(
    val stage: String,
    val englishTag: String,
    val headline: String,
    val detail: String,
    val highlights: List<String>
)

data class PaDocumentSection(
    val pageNumber: Int,
    val title: String,
    val sectionHeader: String,
    val content: String,
    val tags: List<String>
)
