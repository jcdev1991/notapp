package com.cursocompose.notapp.di

import android.app.Application
import androidx.room.Room
import com.cursocompose.notapp.data.local.NoteDatabase
import com.cursocompose.notapp.data.repository.NoteRepositoryImpl
import com.cursocompose.notapp.domain.repository.NoteRepository
import com.cursocompose.notapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, "notes_db").build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNoteListUseCase(repository),
            getNote = GetNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository)
        )
    }
}
