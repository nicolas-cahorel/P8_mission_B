package com.openclassrooms.p8_vitesse.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.p8_vitesse.data.dao.CandidateDtoDao
import com.openclassrooms.p8_vitesse.data.entity.CandidateDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * The Room database for this app, containing the candidate entity.
 * Provides an abstract method to access the CandidateDao.
 */
@Database(entities = [CandidateDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Abstract method to get the DAO for CandidateDto.
     *
     * @return The DAO for accessing candidate data.
     */
    abstract fun candidateDtoDao(): CandidateDtoDao

    /**
     * Callback class to handle actions when the database is created.
     * It populates the database with initial data asynchronously.
     *
     * @property scope The CoroutineScope in which database operations are executed.
     */
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Launch a coroutine to populate the database on creation
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.candidateDtoDao())
                }
            }
        }
    }

    companion object {
        // Volatile variable to ensure the instance is updated across all threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Provides a singleton instance of the database.
         * Creates the database if it does not exist and sets up the Room configuration.
         *
         * @param context The application context.
         * @param coroutineScope The CoroutineScope for executing database operations.
         * @return The singleton instance of AppDatabase.
         */
        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            // If the INSTANCE is null, create it; otherwise, return the existing instance
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "VitesseDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope)) // Add the callback for populating the database
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Populates the database with initial sample data.
         * This method is executed asynchronously within a CoroutineScope.
         *
         * @param candidateDao The DAO used to perform database operations.
         */
        private suspend fun populateDatabase(candidateDao: CandidateDtoDao) {
            // Inserting sample candidates into the database

            // Candidate 1
            candidateDao.insertOrUpdateCandidate(
                CandidateDto(
                    id = 0, // auto-generated by Room
                    photoData = ByteArray(0),
                    firstName = "John",
                    lastName = "Doe",
                    phoneNumber = "1234567890",
                    emailAddress = "john.doe@example.com",
                    dateOfBirth = LocalDate.of(1985, 5, 15).atStartOfDay(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(),// Convert date to timestamp
                    expectedSalary = 50000,
                    informationNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.",
                    isFavorite = false
                )
            )

            // Candidate 2
            candidateDao.insertOrUpdateCandidate(
                CandidateDto(
                    id = 0, // auto-generated by Room
                    photoData = ByteArray(0),
                    firstName = "Jim",
                    lastName = "Nastik",
                    phoneNumber = "1234567891",
                    emailAddress = "jim.nastik@example.com",
                    dateOfBirth = LocalDate.of(1980, 4, 20).atStartOfDay(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(),// Convert date to timestamp
                    expectedSalary = 45000,
                    informationNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.",
                    isFavorite = false
                )
            )

            // Candidate 3
            candidateDao.insertOrUpdateCandidate(
                CandidateDto(
                    id = 0, // auto-generated by Room
                    photoData = ByteArray(0),
                    firstName = "Jane",
                    lastName = "Smith",
                    phoneNumber = "0987654321",
                    emailAddress = "jane.smith@example.com",
                    dateOfBirth = LocalDate.of(1990, 8, 22).atStartOfDay(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(),// Convert date to timestamp
                    expectedSalary = 60000,
                    informationNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.",
                    isFavorite = true
                )
            )

            // Candidate 4
            candidateDao.insertOrUpdateCandidate(
                CandidateDto(
                    id = 0, // auto-generated by Room
                    photoData = ByteArray(0),
                    firstName = "Camille",
                    lastName = "Honnete",
                    phoneNumber = "0987654322",
                    emailAddress = "camille.honnete@example.com",
                    dateOfBirth = LocalDate.of(1989, 1, 5).atStartOfDay(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(),// Convert date to timestamp
                    expectedSalary = 65000,
                    informationNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.",
                    isFavorite = true
                )
            )
        }
    }
}