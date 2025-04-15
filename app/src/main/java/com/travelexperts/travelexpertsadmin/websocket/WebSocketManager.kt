package com.travelexperts.travelexpertsadmin.websocket

import android.util.Log
import com.google.gson.Gson
import com.travelexperts.travelexpertsadmin.data.ChatMessage
import com.travelexperts.travelexpertsadmin.di.BASE_URL
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketManager @Inject constructor() {
//    val wsUrl = "ws://10.187.148.205:8080/" //class wifi
    val wsUrl = "ws://192.168.1.67:8080/" //Taofeek house wifi
    private val client = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "${wsUrl}chat/websocket")
    private var isConnected = false

    fun connect(userEmail: String, onMessage: (ChatMessage) -> Unit) {


        Log.i("STOMP", "🌐 Connecting to WebSocket: $wsUrl")
        client.lifecycle().subscribe { event ->
            when (event.type) {
                LifecycleEvent.Type.OPENED -> {
                    isConnected = true
                    Log.i("STOMP", "✅ WebSocket opened")

                    client.topic("/user/$userEmail/queue/messages")
                        .subscribe({ topicMessage ->
                            val message = Gson().fromJson(topicMessage.payload, ChatMessage::class.java)
                            onMessage(message)
                        }, { error ->
                            Log.e("STOMP", "❌ Error subscribing to topic", error)
                        })
                }

                LifecycleEvent.Type.CLOSED -> {
                    isConnected = false
                    Log.i("STOMP", "❌ WebSocket closed")
                }

                LifecycleEvent.Type.ERROR -> {
                    isConnected = false
                    Log.e("STOMP", "❌ WebSocket error", event.exception)
                }

                else -> Unit
            }
        }

        client.connect()
    }

    fun sendMessage(message: ChatMessage) {
        if (!isConnected) {
            Log.e("STOMP", "❌ Cannot send message: WebSocket not connected")
            return
        }

        val payload = Gson().toJson(message)
        client.send("/app/chat.private", payload)
            .subscribe({}, { error ->
                Log.e("STOMP", "❌ Error sending message", error)
            })
    }

    fun disconnect() {
        isConnected = false
        client.disconnect()
    }
}
