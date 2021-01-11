import google.cloud.pubsub.PubSub
import google.cloud.pubsub.PubSubOptions
import google.cloud.pubsub.Subscription
import google.cloud.pubsub.Topic

// Options for the sample.
val options = PubSubOptions(
    projectId = "local-dev",
    apiEndpoint = "localhost:8085"
)
val topicName = "test-topic"
val subName = "test-sub"

// Async create a topic or gets an existing one.
suspend fun getTopic(pubSubClient: PubSub): Topic = try {
    pubSubClient.createTopic(topicName).await()[0]
} catch (e: Throwable) {
    println("Couldn't make topic, trying to get existing. ${e.message}")
    pubSubClient.topic(topicName)
}

// Async create a subscription or gets an existing one.
suspend fun getSubscription(pubSubClient: PubSub, topic: Topic): Subscription = try {
    pubSubClient.createSubscription(
        topic = topic,
        name = subName
    ).await()[0]
} catch (e: Throwable) {
    println("Couldn't make subscription, trying to get existing. ${e.message}")
    pubSubClient.subscription(subName)
}

suspend fun testPubSub() {
    // Get all of our clients set up.
    val pubSubClient = PubSub(options)
    val topicClient = getTopic(pubSubClient)
    val subClient = getSubscription(pubSubClient, topicClient)

    // Subscribe to incoming messages on the test subscription.
    subClient.on("message") { m ->
        println("Received: ${m.data}")
        m.ack()

        // The process module is slightly mistyped by default.
        js("process.exit(0)")
    }

    // Publish a message to the test topic.
    topicClient.publish(Buffer.from ("This is a test!"))
}

fun main() =
    launchAsync { testPubSub() }
