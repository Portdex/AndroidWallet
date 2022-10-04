package com.application.portdex.domain.models.chat

//0=sending,1=sent,2=delivered,3=seen,4=failed
enum class MessageStatus() {
    sending, sent, delivered, seen, failed
}