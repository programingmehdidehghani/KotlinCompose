package com.example.kotlincompose.feature_note.domain.use_case

import com.example.kotlincompose.feature_note.domain.model.Note
import com.example.kotlincompose.feature_note.domain.repasitory.NoteRepasitory
import com.example.kotlincompose.feature_note.domain.util.NoteOrder
import com.example.kotlincompose.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repasitory: NoteRepasitory
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Dscending)
    ) : Flow<List<Note>>{
        return repasitory.getNotes().map { notes ->
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date ->  notes.sortedBy { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Dscending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date ->  notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}