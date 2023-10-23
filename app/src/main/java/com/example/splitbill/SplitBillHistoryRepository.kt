package com.example.splitbill

object SplitBillHistoryRepository{

    private val splitBillHistoryList = mutableListOf<SplitBillHistory>()

    fun getSplitBillHistory() : List<SplitBillHistory>{
        return splitBillHistoryList
    }

    fun getSplitBillHistoryById(historyId: Int) : SplitBillHistory{
        return splitBillHistoryList[historyId]
    }

    fun addSplitBillHistory(history: SplitBillHistory){
        splitBillHistoryList.add(history)
    }

    fun updatePaidStatus(historyId: Int, participantId: Int, status: Boolean){
        splitBillHistoryList[historyId].participantList[participantId].paidStatus = status
    }

    fun updatePaidCount(historyId: Int){
        var count: Int = 0
        for (participant in splitBillHistoryList[historyId].participantList){
            if (participant.paidStatus == true) count++
        }
        splitBillHistoryList[historyId].paidCount = count
    }

    fun getSize(): Int{
        return splitBillHistoryList.size
    }
}