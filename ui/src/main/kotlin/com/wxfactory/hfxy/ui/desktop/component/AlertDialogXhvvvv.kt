package com.wxfactory.hfxy.ui.desktop.component

import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

class MessageRemind(
    var title: String = "",
    var text: String = "",
    var onDismissRequest: () -> Unit = {},
    var onConfirmation: ( )-> Unit ={} 
){
    val stateChange = mutableStateOf(false)
    val onDismissRequestProxy = {
        onDismissRequest()
        stateChange.value = false
    }
    val onConfirmationProxy = {
        onConfirmation()
        stateChange.value = false
    }
    private fun startAlertDialog(){
        stateChange.value = stateChange.value.not()
    }
    fun alert(  text:String ){
        this.text = text
        this.onConfirmation = { } 
        this.onDismissRequest = { }
        startAlertDialog()
    }
    fun alert( title:String , text:String ){
        this.title = title
        this.text = text
        this.onConfirmation = { }
        this.onDismissRequest = { }
        startAlertDialog()
    }
    fun confirm(title:String , text:String, onConfirmation:()->Unit ){
        this.title = title
        this.text = text
        this.onConfirmation = onConfirmation
        this.onDismissRequest = { }
        startAlertDialog()
    }

    fun confirm( text:String, onConfirmation:()->Unit ){
         
        this.text = text
        this.onConfirmation = onConfirmation
        this.onDismissRequest = { }
        startAlertDialog()
    }

    fun confirm( text:String, onConfirmation:()->Unit, onDismissRequest:()->Unit  ){
         
        this.text = text
        this.onConfirmation = onConfirmation
        this.onDismissRequest = onDismissRequest
        startAlertDialog()
    }


    fun confirm( title:String , text:String, onConfirmation:()->Unit, onDismissRequest:()->Unit  ){
        this.title = title
        this.text = text
        this.onConfirmation = onConfirmation
        this.onDismissRequest = onDismissRequest
        startAlertDialog()
    }


}

val LocalAlertDialog : ProvidableCompositionLocal<MessageRemind> = compositionLocalOf<MessageRemind>{ MessageRemind() }

@Composable
fun AlertDialogDefaults(
    content: @Composable () -> Unit
){
    val thisMess = MessageRemind();
    CompositionLocalProvider( LocalAlertDialog provides thisMess ) {
        content()
        if (thisMess.stateChange.value){
            AlertDialog(
                title = {
                    Text(text = thisMess.title)
                },
                text = {
                    Text(text = thisMess.text)
                },
                onDismissRequest = {
                    thisMess.onDismissRequestProxy()
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            thisMess.onConfirmationProxy()
                        }
                    ) {
                        Text("确认")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            thisMess.onDismissRequestProxy()
                        }
                    ) {
                        Text("取消")
                    }
                }
            )
        }
    }
}