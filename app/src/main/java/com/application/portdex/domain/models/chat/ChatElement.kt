package com.application.portdex.domain.models.chat

import org.jivesoftware.smack.packet.ExtensionElement
import org.jivesoftware.smack.packet.XmlEnvironment

class ChatElement(val customBody: String) : ExtensionElement {

    private val NAMESPACE = "jabber:client"
    private val ELEMENT_NAME = "message"

    override fun toXML(xmlEnvironment: XmlEnvironment?): CharSequence {
        return String.format(customBody)
    }

    override fun getElementName(): String {
        return ELEMENT_NAME
    }

    override fun getNamespace(): String {
        return NAMESPACE
    }
}