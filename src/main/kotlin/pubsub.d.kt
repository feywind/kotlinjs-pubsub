// Ideally, these would be generated from Dukat. But it is having stack overflow errors
// for @google-cloud/pubsub currently. So this is a verrrry simple version.

@file:JsModule("@google-cloud/pubsub")
@file:JsNonModule
package google.cloud.pubsub

import Buffer
import kotlin.js.Promise

external class Topic {
    fun publish(message: Buffer)
}

external class Message {
    val data: String
    fun ack()
}

external class Subscription {
    fun on(message: String, callback: (message: Message) -> Unit)
}

external class PubSub {
    constructor(options: PubSubOptions)

    fun createTopic(name: String): Promise<Array<Topic>>
    fun createSubscription(topic: Topic, name: String): Promise<Array<Subscription>>

    fun topic(name: String): Topic
    fun subscription(name: String): Subscription
}
