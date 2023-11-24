package com.billion_dollor_company.notesapp.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.billion_dollor_company.notesapp.data.daily_tasks.TasksDatabase
import com.billion_dollor_company.notesapp.data.daily_tasks.TasksDatabaseDAO
import com.billion_dollor_company.notesapp.data.notes.NoteDatabaseDAO
import com.billion_dollor_company.notesapp.data.notes.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun providesNotesDAO(notesDatabase: NotesDatabase): NoteDatabaseDAO = notesDatabase.noteDAO()


    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase =
        Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes_df"
        ).fallbackToDestructiveMigration()
            .build()



    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun providesTasksDAO(tasksDatabase: TasksDatabase): TasksDatabaseDAO = tasksDatabase.taskDAO()

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase =
        Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks_df"
        ).fallbackToDestructiveMigration()
            .build()
}