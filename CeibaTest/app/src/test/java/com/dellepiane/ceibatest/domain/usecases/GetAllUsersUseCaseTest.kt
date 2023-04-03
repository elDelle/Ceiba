package com.dellepiane.ceibatest.domain.usecases

import app.cash.turbine.test
import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.repository.UsersRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import kotlin.test.assertEquals

class GetAllUsersUseCaseTest {

    @RelaxedMockK
    private lateinit var usersRepository: UsersRepository

    private lateinit var objectUnderTest: GetAllUsersUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        setUpGetAllUsersUseCase()
    }

    @Test
    fun `Getting all users should emit Success when service finishes with success`() = runTest {
        val user = User(1, "name", "phone", "email")
        coEvery { usersRepository.getAllUsers() } returns flowOf(Result.success(listOf(user)))

        objectUnderTest.execute().test {
            val result = awaitItem()

            assertEquals(
                expected = Result.success(listOf(user)),
                actual = result
            )
            awaitComplete()
        }
    }

    @Test
    fun `Getting all users should emit Internet connection error when service finishes with internet connection error`() =
        runTest {
            val testException = IOException("Internet error message")
            val user = User(1, "name", "phone", "email")
            coEvery { usersRepository.getAllUsers() } throws testException andThen flowOf(
                Result.success(
                    listOf(user)
                )
            )

            assertThrows<IOException> {
                objectUnderTest.execute().test {
                    val errorResult = awaitItem()

                    assertEquals(
                        expected = Result.failure(testException),
                        actual = errorResult
                    )

                    val itemsResult = awaitItem()

                    assertEquals(
                        expected = Result.success(listOf(user)),
                        actual = itemsResult
                    )
                }
            }
        }

    @Test
    fun `Getting all users should emit error when service finishes with error`() = runTest {
        val testException = Exception("Error message")
        coEvery { usersRepository.getAllUsers() } throws testException

        assertThrows<Exception> {
            objectUnderTest.execute().test {
                val result = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = result
                )
            }
        }
    }

    private fun setUpGetAllUsersUseCase() {
        objectUnderTest = GetAllUsersUseCase(usersRepository)
    }

}