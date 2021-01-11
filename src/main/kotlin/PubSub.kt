package google.cloud.pubsub

// This is here instead of pubsub.d.kt, because it's easier to make it
// nicely idiomatic for Kotlin usage as a data class. (Member names are
// the same in any case.)
data class PubSubOptions(
    val projectId: String? = null,
    val apiEndpoint: String? = null
)
