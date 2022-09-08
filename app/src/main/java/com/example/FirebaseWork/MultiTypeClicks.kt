package com.example.FirebaseWork

interface MultiTypeClicks {
    fun delete(item:Int)
    fun rename(oldName:String,position:Int)
    fun edit()
    fun openNewClass(name:String)
}