package com.application.portdex.domain.models.chat

class Wrapper<DATA> internal constructor(var item: DATA) {
    var isSelected = false
}